package com.alexis.mytestdemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.webkit.CookieManager;

import androidx.annotation.Nullable;

import com.alexis.mytestdemo.CookieDemoActivity;
import com.alexis.mytestdemo.R;
import com.alexis.mytestdemo.utils.LogUtil;
import com.alexis.mytestdemo.utils.NotificationChannelCompat;
import com.alexis.mytestdemo.utils.ServiceUtil;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo.service
 * @ClassName: CookieService
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/10 10:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/10 10:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CookieService extends Service {


    private CookieManager mCookieManager;
    private OkHttpClient mOkHttpClicent;
    private static String COOKIE = "";

    private static final String TAG = "zhz_"+CookieService.class.getSimpleName();
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private String mCurCookie = COOKIE;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //mCookieManager.flush();
            //Log.d(TAG,"淘宝cookie="+mCookieManager.getCookie(CookieDemoActivity.URL_TAOBAO));
            getCookieFormUrl();
            //Log.d(TAG,"淘宝cookie="+mCookieManager.getCookie(CookieDemoActivity.URL_TAOBAO_COLLECT));
            mHandler.sendEmptyMessageDelayed(0,10000);

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCookieManager = CookieManager.getInstance();
        initData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCurCookie = intent.getExtras().getString("cookie");
        mHandler.sendEmptyMessage(0);
/*Notification.Builder builder = NotificationChannelCompat.createBuilder(getBaseContext(),
        NotificationChannelCompat.DAEMON_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(getString(R.string.app_content_text));
        builder.setSound(null);
        startForeground(ServiceUtil.ID, builder.build());*/
        return START_STICKY;
    }

    private class MyCookie implements CookieJar{

        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            List<Cookie> cookieList = new ArrayList<>();

            List<Cookie> parse = parse();
            for (Cookie cookie : parse){
                for (Cookie coo : cookies){
                    if (coo.name().equals(cookie.name())){
                        cookieList.add(coo);
                    }else{
                        cookieList.add(cookie);
                    }
                }
            }
            //System.out.println("cookieList:"+cookieList.toString());
            //mCurCookie =cookieList.toString();
            //mCookieManager.setCookie(CookieDemoActivity.URL_TAOBAO,mCurCookie);
            cookieStore.put(url.host(), cookieList);

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }

    }

    private List<Cookie> parse(){
        List<Cookie> cookies = new ArrayList<>();
        String[] strings = mCurCookie.split(";");
        for (String str : strings) {
            String[] split = str.split("=");
            Cookie.Builder builder = new Cookie.Builder();
            builder.name(split[0].trim());
            builder.value(split[1].trim());
            builder.domain("taobao.com");
            builder.path("/");
            cookies.add(builder.build());
        }

        return cookies;
    }

    private void initData(){
        mOkHttpClicent = new OkHttpClient()
                .newBuilder()
                .cookieJar(new MyCookie()).build();

    }

    private void getCookieFormUrl(){

        final Request request = new Request.Builder()
                .url(CookieDemoActivity.URL_TAOBAO_COLLECT)
                .addHeader("cookie", mCurCookie)
                .build();
        mOkHttpClicent.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println(response.body().string());
                String result = response.body().string();
                if(!result.contains("xiaofan0895的收藏夹")){
                    //Log.e(TAG,"onResponse result:"+result);
                    Log.e(TAG,"cookie failed at:"+new Date());
                    //mHandler.removeCallbacksAndMessages(null);
                }else{
                    Log.d(TAG,"cookie is successed:"+new Date());
                }
            }

        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"CookieService onDestroy!!!");
        mHandler.removeCallbacksAndMessages(null);

    }
}
