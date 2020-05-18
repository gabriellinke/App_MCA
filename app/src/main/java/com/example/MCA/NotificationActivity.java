package com.example.MCA;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationActivity extends Service {
    private NotificationManagerCompat notificationManager;
/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);

        sendOnChannel1();
        finish();
    }
*/

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        notificationManager = NotificationManagerCompat.from(this);

        sendOnChannel1();
    }

    public void sendOnChannel1() {
        String title = "Finalizar";
        String message = "Não esqueça de finalizar o dia!";

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher_agua)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();
        notificationManager.notify(1, notification);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
