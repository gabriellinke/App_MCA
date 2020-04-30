package com.example.MCA;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConsultarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        //SALVA O CONSUMO ATUAL NA VARIÁVEL CONSUMO
        int consumo = 0;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        consumo = sp.getInt("consumo_atual", 0);

        //ATUALIZA A TEXTVIEW QUE MOSTRA O CONSUMO COM O DADO ATUALIZADO
        TextView tv = (TextView) findViewById(R.id.consumo_number);
        tv.setText(consumo/1000.0f + " L");
    }
}