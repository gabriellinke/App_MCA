package com.example.MCA;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity
{
    //BOTÕES
    Button botaoContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //DEFINE O LAYOUT

        //BOTÃO PARA INICIAR O APLICATIVO
        botaoContinuar  = (Button) findViewById(R.id.start_button);
        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SegundaActivity.class));
            }
        });

    }
}