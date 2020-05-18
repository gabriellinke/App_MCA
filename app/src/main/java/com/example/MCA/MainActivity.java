package com.example.MCA;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity
{
    //BOTÕES
    private Button botaoContinuar;

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


        Intent broadcastIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND,0);

        long startUpTime = calendar.getTimeInMillis();

        if (System.currentTimeMillis() > startUpTime) {
            startUpTime = startUpTime + 20*60*1000; //24*60*60*1000
        }

        if(alarmManager!=null)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startUpTime, AlarmManager.INTERVAL_DAY, actionIntent);
    }
}