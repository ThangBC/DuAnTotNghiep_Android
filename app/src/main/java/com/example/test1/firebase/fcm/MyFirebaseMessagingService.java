package com.example.test1.firebase.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.test1.NameActivity;
import com.example.test1.R;
import com.example.test1.firebase.MyApplication;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.models.TokenDevice;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        TokenDevice tokenDevice = new TokenDevice();
        tokenDevice.setToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> stringMap = remoteMessage.getData();
        String strTitle = stringMap.get("title");
        String strMess = stringMap.get("content");

        sendNotification(strTitle, strMess);
    }

    private void sendNotification(String strTitle, String strMess) {
        Intent intent = new Intent(this, HomeFragment.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strMess)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
            Log.e( "sendNotification: ",notification.toString() );
        }
    }
}
