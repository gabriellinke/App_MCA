package com.example.MCA;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** CLASSE ATIVADA QUANDO O ALARME É DISPARADO. SERVE PARA INICIAR UM SERVIÇO ---------------------------------------------------**/
public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        context.startService(service);
    }
}
