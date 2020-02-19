package com.example.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SegundaActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_segunda);

        /** BOTÕES QUE REDIRECIONARÃO PARA OS TREINOS ESPECÍFICOS */

        Button botaoTreinoPull = findViewById(R.id.pull_button);
        Button botaoTreinoPush = findViewById(R.id.push_button);
        Button botaoTreinoLeg  = findViewById(R.id.leg_button);
        Button botaoTreinoRun  = findViewById(R.id.run_button);

        botaoTreinoPull.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), TreinoPull.class));
            }
        });

        botaoTreinoPush.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), TreinoPush.class));
            }
        });

        botaoTreinoLeg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), TreinoLeg.class));
            }
        });

        botaoTreinoRun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), TreinoRun.class));
            }
        });
    }
}
