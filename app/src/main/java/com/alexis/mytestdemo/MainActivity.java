package com.alexis.mytestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import java.util.Arrays;

public class MainActivity extends Activity implements View.OnClickListener {
    private final static String CRITICAL = "常用通知(一定要打开哦)";
    private final static String BACK_DAEMON = "守护进程";
    private Button btn_device_screen;
    private Button btn_immersive;
    private Button btn_notification;
    private Button btn_hook_demo;
    private Button btn_cookie_demo;
    private TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();
    }

    private void initDatas(){
        Log.d("zhz","is root:"+AppUtils.isAppRoot());
        //AppUtils.r
    }

    private void initViews(){
        btn_device_screen = findViewById(R.id.btn_device_screen);
        btn_device_screen.setOnClickListener(this);

        btn_immersive = findViewById(R.id.btn_immersive);
        btn_immersive.setOnClickListener(this);

        btn_notification = findViewById(R.id.btn_notification);
        btn_notification.setOnClickListener(this);

        btn_hook_demo = findViewById(R.id.btn_hook_demo);
        btn_hook_demo.setOnClickListener(this);

        btn_cookie_demo = findViewById(R.id.btn_cookie_demo);
        btn_cookie_demo.setOnClickListener(this);

        tv_content = findViewById(R.id.tv_content);
    }

    private void getDeviceScreen(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels; // 屏幕宽度（像素）
        int height = displayMetrics.heightPixels; // 屏幕高度（像素）
        float density = displayMetrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = displayMetrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        String text = "屏幕宽度："+width+
                "px\n屏幕高度："+height+
                "px\n屏幕密度："+density+
                "\n屏幕密度DPI:"+densityDpi+
                "\n状态栏高度："+getStatusBarHeight()+
                "px\n导航栏高度："+getNavigationBarHeight()+"px";
        tv_content.setText(text);
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        //Log.v("dbw", "Status height:" + height);
        return height;
    }
    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        //Log.v("dbw", "Status height:" + height);
        return height;
    }
    private void startImmersive(){
        Intent intent = new Intent();
        intent.setClass(this,ImmersiveActivity.class);
        startActivity(intent);
    }
    private static void createAllNotificationChannels(Context context) {
        /**
         * @method createAllNotificationChannels
         * @description 创建NotificationChannels
         * @date: 2019/4/16 3:45 PM
         * @author: zhouchaoran
         * @param [context]
         * @return void
         */
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm == null) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel normalChannel = null;
            normalChannel = new NotificationChannel(
                    CRITICAL,
                    "常用通知(一定要打开哦)",
                    NotificationManager.IMPORTANCE_HIGH);
            normalChannel.setSound(null, null);
            normalChannel.setVibrationPattern(null);

            nm.createNotificationChannels(Arrays.asList(normalChannel,
                    new NotificationChannel(BACK_DAEMON,
                            "守护进程通知"
                            , NotificationManager.IMPORTANCE_HIGH)));
        }
    }
    private void sendNotification(){
        createAllNotificationChannels(this);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CRITICAL)
                .setContentTitle("This is content title")
                .setContentText("This is ticker text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("测试")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)

                ;

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    private void startHookDemo(){
        Intent intent = new Intent(this,HookDemoActivity.class);
        startActivity(intent);
    }

    private void startCookieDemo(){
        Intent intent = new Intent(this,CookieDemoActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_device_screen:
                getDeviceScreen();
                break;
            case R.id.btn_immersive:
                startImmersive();
                break;
            case R.id.btn_notification:
                sendNotification();
                break;
            case R.id.btn_hook_demo:
                startHookDemo();
                break;
            case R.id.btn_cookie_demo:
                startCookieDemo();
                break;
        }
    }
}
