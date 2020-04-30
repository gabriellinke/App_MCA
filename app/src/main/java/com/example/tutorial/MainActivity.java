package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import helpers.DBHelper;

public class MainActivity extends AppCompatActivity
{
    //BOTÕES
    Button botaoContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //DEFINE O LAYOUT

    /**---- BOTÕES ------------------------------------------------------------------------------------------------------------------------------------------------**/

        botaoContinuar  = (Button) findViewById(R.id.start_button);
        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SegundaActivity.class));
            }
        });
    /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/

    /**---- BANCO DE DADOS ------------------------------------------------------------------------------------------------------------------------------------------------**/
        //Acho q é desnecessário

        //DBHelper db = new DBHelper(getBaseContext());
        // db.getWritableDatabase();
    /**--------------------------------------------------------------------------------------------------------------------------------------------------------------**/

        //startActivity(new Intent(getBaseContext(), CursoresActivity.class));
    }
}