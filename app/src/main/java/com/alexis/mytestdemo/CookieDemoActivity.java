package com.alexis.mytestdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.mytestdemo.service.CookieService;
import com.alexis.mytestdemo.utils.ServiceUtil;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo
 * @ClassName: CookieDemoActivity
 * @Description: 获取cookie并防止失效
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/9 17:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/9 17:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CookieDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "zhz_"+CookieDemoActivity.class.getSimpleName();
    public static final String URL_XIECHENG_LOGIN = "https://accounts.ctrip.com/H5Login/Index";
    public static final String URL_XIECHENG = "http://m.ctrip.com/webapp/myctrip/";


    public static final String URL_TAOBAO_LOGIN = "https://login.m.taobao.com/login.htm";
    public static final String URL_TAOBAO = "https://main.m.taobao.com/mytaobao/index.html";
    public static final String URL_TAOBAO_COLLECT = "https://shoucang.taobao.com/item_collect_n.htm";

    public static final String URL_JD_LOGIN = "https://plogin.m.jd.com/user/login.action";
    public static final String URL_JD = "https://m.jd.com/";


    public static final String COOKIE = "";
    private Button btn_jd;
    private Button btn_xc;
    private Button btn_tb;
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
        initDatas();
        initViews();
    }

    private void initDatas(){
        //CookieSyncManager.createInstance(this);
    }
    private void initViews(){
        btn_jd = findViewById(R.id.btn_jd);
        btn_jd.setOnClickListener(this);

        btn_xc = findViewById(R.id.btn_xc);
        btn_xc.setOnClickListener(this);

        btn_tb = findViewById(R.id.btn_tb);
        btn_tb.setOnClickListener(this);

        mWebView = findViewById(R.id.webview);
        initWebView();

    }


    private void initWebView(){
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        mWebView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished! url="+url);
                CookieManager cookieManager = CookieManager.getInstance();

                String cookieStr = cookieManager.getCookie(url);
                Log.e(TAG, "setWebView Cookies = " + cookieStr);
                syncCookie(cookieStr);
            }


        });


    }

    private Intent serIntent;
    private void syncCookie(String cookie){
        serIntent = new Intent(this, CookieService.class);
        Bundle bundle = new Bundle();
        bundle.putString("cookie",cookie);
        serIntent.putExtras(bundle);
        startService(serIntent);
    }

    private void startLoad(String url){
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_xc:
                startLoad(URL_XIECHENG);
                break;
            case R.id.btn_jd:
                startLoad(URL_JD);
                break;
            case R.id.btn_tb:
                startLoad(URL_TAOBAO);
                break;
        }
    }
}
