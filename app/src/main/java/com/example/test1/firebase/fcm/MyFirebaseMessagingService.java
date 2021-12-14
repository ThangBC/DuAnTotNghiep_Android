package com.example.test1.firebase.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.firebase.MyApplication;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.models.User;
import com.example.test1.ultilties.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    SharedPreferences sharedPreferences;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().get("title") != null && remoteMessage.getData().get("title").contains("Poly Dating")) {
            Map<String, String> stringMap = remoteMessage.getData();
            String strTitle = stringMap.get("title");
            String strMess = stringMap.get("content");
            Intent intent = new Intent(this, HomeFragment.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                    .setContentTitle(strTitle)
                    .setContentText(strMess)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            Notification notification = notificationBuilder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(1, notification);
                Log.e("sendNotification: ", notification.toString());
            }
        } else {
            User user = new User();
            user.id = remoteMessage.getData().get(Constants.KEY_USER_ID);
            user.name = remoteMessage.getData().get(Constants.KEY_NAME);
            user.token = remoteMessage.getData().get(Constants.KEY_FCM_TOKEN);

            int notificationId = new Random().nextInt();
            String channelId = "chat_message";

            Intent intent = new Intent(this, InChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Constants.KEY_USER, user);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);//Ấn thông báo hiện thị ra app

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setContentTitle(user.name);
            builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                    remoteMessage.getData().get(Constants.KEY_MESSAGE)
            ));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence channelName = "Chat message";
                String channelDescription = "This notification channel is user for chat message notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                channel.setDescription(channelDescription);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(notificationId, builder.build());
        }
    }

}