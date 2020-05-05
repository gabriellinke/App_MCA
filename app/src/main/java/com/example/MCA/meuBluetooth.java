package com.example.MCA;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class meuBluetooth extends AppCompatActivity
{
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
    SegundaActivity.ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();    //ORGANIZAR INFORMAÇÕES



}
