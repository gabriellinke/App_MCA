package com.example.tutorial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class SegundaActivity extends AppCompatActivity  {

    //BOTÕES
    Button botaoConectar, botaoZerar, botaoAtualizar, botaoConsultar, botaoFinalizar, botaoHistorico;

    //VARIÁVEIS PARA UTILIZAR O BLUETOOTH
    BluetoothAdapter meuBluetoothAdapter = null; //PONTO DE ENTRADA PARA TODA INTERAÇÃO BLUETOOTH
    BluetoothDevice meuDevice            = null; //REPRESENTA UM DISPOSITIVO REMOTO BLUETOOTH
    BluetoothSocket meuSocket            = null; //FAZ A TRANSMISSÃO DOS DADOS ENTRE OS APARELHOS
    UUID MEU_UUID                        = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //RFCOMM(Radio Frequency Communication), SIMILAR AO TCP
    private static String MAC            = null; //MAC DO DISPOSITIVO
    boolean conexao                      = false;//VARIÁVEL QUE INFORMA SE O APARELHO ESTÁ CONECTADO COM O DISPOSITIVO BLUETOOTH

    //CONSTANTES PARA SABER O QUE ESTÁ SENDO SOLICITADO
    private static final int SOLICITA_ATIVACAO  = 1;
    private static final int SOLICITA_CONEXAO   = 2;
    private static final int MESSAGE_READ       = 3;

    //USADOS PARA COMUNICAÇÃO ANDROID-ARDUINO
    ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();    //ORGANIZAR INFORMAÇÕES

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda); //DEFINE O LAYOUT


        /**---- BLUETOOTH ------------------------------------------------------------------------------------------------------------------------------------------------**/

        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(meuBluetoothAdapter == null) //NAÕ TEM BLUETOOTH
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui bluetooth", Toast.LENGTH_LONG).show();

        else if(!meuBluetoothAdapter.isEnabled()) //BLUETOOTH ESTÁ DESATIVADO
        {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);                         //ATIVA O BLUETOOTH
        }
        /**---------------------------------------------------------------------------------------------------------------------------------------------------------------**/

        /**---- BOTÕES ------------------------------------------------------------------------------------------------------------------------------------------------**/

        botaoConectar   = (Button) findViewById(R.id.parear_button);
        botaoZerar      = (Button) findViewById(R.id.zerar_button);
        botaoAtualizar  = (Button) findViewById(R.id.atualizar_button);
        botaoConsultar  = (Button) findViewById(R.id.consultar_button);
        botaoFinalizar  = (Button) findViewById(R.id.finalizar_button);
        botaoHistorico  = (Button) findViewById(R.id.historico_button);

        botaoConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!meuBluetoothAdapter.isEnabled()) //BLUETOOTH ESTÁ DESATIVADO
                {
                    if(conexao) //QUER DESCONECTAR, NÃO TEM PROBLEMA SE BT ESTÁ DESATIVADO
                    {
                        try
                        {
                            if(meuSocket.isConnected())
                                meuSocket.close();
                            Toast.makeText(getApplicationContext(), "Desconectado", Toast.LENGTH_SHORT).show();
                            conexao = false;
                            botaoConectar.setText(R.string.parear_button_conectar);
                        }
                        catch(IOException erro)
                        {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro" + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else //ESTÁ QUERENDO CONECTAR, PORTANTO, PRECISA LIGAR O BLUETOOTH
                    {
                        Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);                         //ATIVA O BLUETOOTH
                    }
                }
                else //BLUETOOTH ESTÁ LIGADO
                {
                    if(conexao) //QUER DESCONECTAR
                    {
                        try
                        {
                            if(meuSocket.isConnected())
                                meuSocket.close();
                            Toast.makeText(getApplicationContext(), "Desconectado", Toast.LENGTH_SHORT).show();
                            conexao = false;
                            botaoConectar.setText(R.string.parear_button_conectar);
                        }
                        catch(IOException erro)
                        {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro" + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else //QUER CONECTAR
                    {
                        Intent abreLista = new Intent(SegundaActivity.this, ListaDispositivos.class);
                        startActivityForResult(abreLista, SOLICITA_CONEXAO);
                    }
                }
            }
        });

        botaoZerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conexao)
                {
                    connectedThread.enviar("zerar");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoAtualizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //AÇÃO PARA ATUALIZAR CONSUMO
                if(conexao)
                {
                    connectedThread.enviar("solicitarDados");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoConsultar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), ConsultarActivity.class));
            }
        });

        botaoFinalizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //AÇÃO PARA FINALIZAR O DIA

                if(conexao)
                {
                    //ATUALIZAR CONSUMO
                    connectedThread.enviar("solicitarDados");
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    int consumoAtual = sp.getInt("consumo_atual", 0);

                    //CONSULTAR A DATA ATUAL
                    int data[];
                    data = Get_Calendar.main();
                    String date = +data[0]+"/"+data[1]+"/"+data[2];

                    //ATUALIZAR BANCO DE DADOS
                    Bundle bundle = new Bundle();
                    bundle.putInt("consumo", consumoAtual);
                    bundle.putString("data", date);
                    bundle.putInt("id", 1);

                    Intent intent = new Intent(getBaseContext(), CursoresActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    //ZERAR CONSUMO ATUAL
                    sp.edit().putInt("consumo_atual", 0).apply();

                    //ZERAR ARDUINO
                    connectedThread.enviar("zerar");
                }
                else
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado", Toast.LENGTH_SHORT).show();
            }
        });

        botaoHistorico.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), HistoricoActivity.class));
            }
        });
        /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/

        /**----- ASSSEGURAR QUE AS INFORMAÇÕES ESTÃO COMPLETAS ------------------------------------------------------------------------------------------------------------/
         **----- VERIFICA SE HÁ '{' NO INÍCIO E '}' NO FINAL, O QUE SIGNIFICA QUE OS DADOS RECEBIDOS ESTÃO COMPLETOS ----------------------------------------------------**/
        mHandler = new Handler()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(@NonNull Message msg)
            {
                if(msg.what == MESSAGE_READ)
                {
                    String recebidos = (String) msg.obj;

                    dadosBluetooth.append(recebidos);

                    int fimInformacao = dadosBluetooth.indexOf("}"); //VERIFICA A POSIÇÃO DE "}" NA STRING, PARA SABER EM QUE POSIÇÃO FICA O FIM DA MENSAGEM. OBS: SE RETORNAR -1, O CARACTERE NÃO FOI ENCONTRADO

                    if(fimInformacao > 0)   //VERIFICA SE O CARACTERE FOI ENCONTRADO
                    {
                        String dadosCompletos = dadosBluetooth.substring(0, fimInformacao); //PEGA A SUBSTRING DO ÍNICIO DA STRING ATÉ O CARACTERE DO FIM

                        int tamInformacao = dadosCompletos.length();
                        int inicioInformacao = dadosBluetooth.indexOf("{");  //VERIFICA A POSIÇÃO DE "{" NA STRING, PARA SABER EM QUE POSIÇÃO FICA O INÍCIO DA MENSAGEM

                        if(inicioInformacao >= 0) //VERIFICA SE O CARACTERE FOI ENCONTRADO, PARA NÃO ACESSAR UMA POSIÇÃO INVÁLIDA DO VETOR
                        {
                            if(dadosBluetooth.charAt(inicioInformacao) == '{')
                            {
                                String dadosFinais = dadosBluetooth.substring(inicioInformacao+1, tamInformacao); //PEGA OS DADOS QUE ESTÃO ENTRE "{" E "}"

                                //TRATAR A INFORMAÇÃO AQUI
                                Log.d("Recebidos: ", dadosFinais); //PARA DEBUGAR

                                TextView tv = (TextView) findViewById(R.id.estado_text);
                                tv.setText(dadosFinais + " ml");

                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                sp.edit().putInt("consumo_atual", Integer.parseInt(dadosFinais.trim())).apply();    //SALVA O CONSUMO ATUAL

                            }
                        }
                        dadosBluetooth.delete(0, dadosBluetooth.length()); //LIMPA OS DADOS, PARA RECEBER NOVA MENSAGEM
                    }
                }
            }
        };
        /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/
    }

/** FUNÇÃO QUE REALIZA AS AÇÕES REQUISITADAS --------------------------------------------------------------------------------------------------------------------**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        switch (requestCode)
        {

    /**-------- FEEDBACK SOBRE ATIVAÇÃO DO BLUETOOTH ---------------------------------------------------------------------------------------------------------------**/

            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK)
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth foi ativado", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth não foi ativado", Toast.LENGTH_SHORT).show();
                }
                break;
    /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/

    /**-------- CONECTA O CELULAR AO DISPOSITIVO BLUETOOTH ----------------------------------------------------------------------------------------------------------**/

            case SOLICITA_CONEXAO:
                if(resultCode == Activity.RESULT_OK)
                {
                    if(data.getExtras().getString(ListaDispositivos.ENDERECO_MAC) != null)
                        MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);   //PEGA O ENDEREÇO MAC DO INTENT QUE FOI RECEBIDO DA LISTA DE DISPOSITIVOS
                    else
                        Toast.makeText(getApplicationContext(), "MAC Nullo", Toast.LENGTH_SHORT).show();

                    meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC); //CRIA UM DEVICE COM O ENDEREÇO MAC QUE FOI LIDO

                    try
                    {
                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID); //CRIA UM CANAL DE COMUNICAÇÃO COM O DEVICE

                        conexao = true;

                        if (!SegundaActivity.this.isFinishing())
                            meuSocket.connect();    //FAZ A CONEXÃO
                        else
                            Toast.makeText(getApplicationContext(), "Erro ao conectar", Toast.LENGTH_LONG).show();

                        if(meuSocket.isConnected()) {
                            connectedThread = new ConnectedThread(meuSocket);   //THREAD QUE FICA MONITORANDO AS ATIVIDADES DE ESCRITA/LEITURA
                            connectedThread.start();
                            Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Erro ao conectar2", Toast.LENGTH_LONG).show();

                        botaoConectar.setText(R.string.parear_button_desconectar);
                    }
                    catch(IOException erro)
                    {
                        Toast.makeText(getApplicationContext(), "Erro ao conectar", Toast.LENGTH_SHORT).show(); //PARA O USUÁRIO
                        //Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + erro, Toast.LENGTH_SHORT).show(); PARA DEBUGAR
                        conexao = false;
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_SHORT).show();
                }
                break;
    /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/
        }
/**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/
    }

/**- THREAD PARA CONEXÃO BLUETOOTH --------------------------------------------------------------------------------------------------------------------------------**/
    public class ConnectedThread extends Thread
    {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

    /**---- CONSTRUTOR ----------------------------------------------------------------------------------------------------------------------------------------------**/

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_SHORT).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

    /**-------------------------------------------------------------------------------------------------------------------------------------------------------------**/

    /**---- FUNÇÃO PARA RECEBER DADOS ------------------------------------------------------------------------------------------------------------------------------**/

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBt = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }

    /**-------------------------------------------------------------------------------------------------------------------------------------------------------------**/

    /**---- FUNÇÃO PARA ENVIAR DADOS -------------------------------------------------------------------------------------------------------------------------------**/

        public void enviar(String dadosEnviar)
        {
            try
            {
                byte[] msgBuffer = dadosEnviar.getBytes();
                mmOutStream.write(msgBuffer);
            }
            catch (IOException e) {}
        }

    /**-------------------------------------------------------------------------------------------------------------------------------------------------------------**/
    }
/**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/

    public static class Get_Calendar{

        public static int[] main() {
            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) +1; //retorna os meses de 0 a 11
            int ano = c.get(Calendar.YEAR);

            Log.d("Data: ", ("Data: "+dia+"/"+mes+"/"+ano));

            int data[];
            data = new int[3];
            data[0] = dia;
            data[1] = mes;
            data[2] = ano;

            return data;
        }
    }
}
