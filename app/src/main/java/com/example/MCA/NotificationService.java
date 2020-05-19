package com.example.MCA;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends Service {
    private NotificationManagerCompat notificationManager;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        notificationManager = NotificationManagerCompat.from(this);

        sendOnChannel1();
    }

    /** ENVIA UMA NOTIFICAÇÃO NO CANAL 1 ---------------------------------------------------------------------------------------**/
    public void sendOnChannel1() {
        //CONFIGURA O QUE ACONTECE AO CLICAR NA NOTIFICAÇÃO
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        //CONFIGURA A NOTIFICAÇÃO
        String title = "O dia está terminando...";
        String message = "Não esqueça de abrir o aplicativo e finalizar o dia!";

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        notificationManager.notify(1, notification);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
