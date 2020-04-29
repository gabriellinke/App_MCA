package com.example.tutorial;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SegundaActivity extends AppCompatActivity  {

    //BOTÕES
    Button botaoConectar, botaoSolicitar, botaoZerar, botaoAtualizar, botaoConsultar, botaoFinalizar, botaoHistorico;
  /*
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
    MainActivity.ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();    //ORGANIZAR INFORMAÇÕES


    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda); //DEFINE O LAYOUT


        /** BOTÕES **/

        botaoAtualizar = findViewById(R.id.atualizar_button);
        botaoConsultar = findViewById(R.id.consultar_button);
        botaoFinalizar  = findViewById(R.id.finalizar_button);
        botaoHistorico  = findViewById(R.id.historico_button);

        botaoAtualizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //AÇÃO PARA ATUALIZAR CONSUMO

                Toast.makeText(getApplicationContext(), "Consumo atualizado", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getBaseContext(),AtualizarActivity.class));
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

                int data[];
                data = Testa_Metodo_Get_Calendar.main();
                Toast.makeText(getApplicationContext(),("Data: "+data[0]+"/"+data[1]+"/"+data[2]) , Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Dia finalizado", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(getBaseContext(), FinalizarActivity.class));
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
    }

    public static class Testa_Metodo_Get_Calendar{

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
