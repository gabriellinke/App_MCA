package com.example.MCA;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationActivity.class);
        //context.startActivity(service);
        context.startService(service);
    }
}
