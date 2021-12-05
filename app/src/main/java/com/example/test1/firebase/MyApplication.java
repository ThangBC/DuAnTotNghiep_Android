package com.example.test1.firebase;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "push_notification_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotificationPolyDating",
                    NotificationManager.IMPORTANCE_MAX);
//            channel.setDescription("thang24");
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.setVibrationPattern(new long[]{0,1000,500,1000});
//            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }
    }
}
