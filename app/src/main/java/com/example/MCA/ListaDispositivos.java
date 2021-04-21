package com.example.MCA;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Set;

public class ListaDispositivos extends ListActivity {

    static String ENDERECO_MAC = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //USADO PARA MOSTRAR A LISTA DE DISPOSITIVOS PAREADOS
        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        BluetoothAdapter meuBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();

        //RECEBE OS DISPOSITIVOS PAREADOS
        Set<BluetoothDevice> dispositivosPareados = meuBluetoothAdapter2.getBondedDevices();

        if(dispositivosPareados.size() > 0) //SE TIVER DISPOSITIVOS PAREADOS
        {
            for(BluetoothDevice dispositivo : dispositivosPareados) //PERCORRE TODOS DISPOSITIVOS PAREADOS
            {
                String nomeBt = dispositivo.getName();      //PEGA O NOME DO DISPOSITIVO BLUETOOTH
                String macBt  = dispositivo.getAddress();   //PEGA O ENDEREÇO MAC DO DISPOSITIVO
                ArrayBluetooth.add(nomeBt + "\n" + macBt);  //SALVA NO ADAPTER
            }
        }
        setListAdapter(ArrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();   //PEGA AS INFORMAÇÕES DO ITEM CLICADO

        String enderecoMac = informacaoGeral.substring(informacaoGeral.length() - 17); //PEGA O ENDEREÇO MAC

        Intent retornaMac = new Intent();               //UMA PONTE PARA ENVIAR A INFORMAÇÃO PARA OUTRA ACTIVITY
        retornaMac.putExtra(ENDERECO_MAC, enderecoMac); //DADO QUE VAI RETORNAR
        setResult(RESULT_OK, retornaMac);               //CONFIRMA QUE O PROCESSO FOI OK
        finish();                                       //TERMINA ACTIVITY
    }
}
