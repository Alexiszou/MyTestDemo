package com.alexis.mytestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo.service
 * @ClassName: BinderService
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/6/20 11:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 11:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BinderService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


