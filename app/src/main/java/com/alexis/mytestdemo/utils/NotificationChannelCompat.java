package com.alexis.mytestdemo.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;


public class NotificationChannelCompat {
    public static final String DAEMON_ID = "daemon";
    public static final String DEFAULT_ID = "default";

    public static boolean enable(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void checkOrCreateChannel(Context context, String channelId, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Compatibility of old versions");
                channel.setSound(null, null);
                channel.setShowBadge(false);
                try {
                    manager.createNotificationChannel(channel);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static Notification.Builder createBuilder(Context context, String channelId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(context, channelId);
        }else{
            return new Notification.Builder(context);
        }
    }
}
