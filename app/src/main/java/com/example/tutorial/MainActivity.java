package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /** CONFIGURA O QUE VAI ABRIR AO INICIAR O APLICATIVO */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** CONFIGURA BOTÃO PARA INICIAR O TREINO         */

        Button botaoInicioTreino = (Button) findViewById(R.id.main_button);
        botaoInicioTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SegundaActivity.class));
            }
        });
    }

}