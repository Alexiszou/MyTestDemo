package com.alexis.mytestdemo.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.alexis.mytestdemo.R;
import com.alexis.mytestdemo.service.CookieService;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo.utils
 * @ClassName: ServiceUtil
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/11 16:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/11 16:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServiceUtil {
    public static final int ID = 9527;
    public static void bindForeground(Context context, Service service) {
        /*Notification.Builder builder = NotificationChannelCompat.createBuilder(service.getApplicationContext(),
                NotificationChannelCompat.DAEMON_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        if (Build.VERSION.SDK_INT > 24) {
            builder.setContentTitle(context.getString(R.string.app_name)+"v24");
            builder.setContentText(context.getString(R.string.app_content_text)+"v24");
        } else {
            builder.setContentTitle(context.getString(R.string.app_name));
            builder.setContentText(context.getString(R.string.app_content_text));
            builder.setContentIntent(PendingIntent.getService(service, 0, new Intent(service, HiddenForeNotification.class), 0));
        }
        builder.setSound(null);
        service.startForeground(ID, builder.build());
        if (Build.VERSION.SDK_INT <= 24) {
            context.startService(new Intent(context, CookieService.class));
        }*/
    }
}
