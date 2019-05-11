package com.alexis.mytestdemo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vanniktech.rxpermission.RealRxPermission;
import com.vanniktech.rxpermission.RxPermission;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo
 * @ClassName: HookDemoActivity
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/8 15:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/8 15:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HookDemoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "HookDemoActivity";
    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        initDatas();
        initViews();
        requestPermissions();
    }


    private void initViews() {
        Button btn_get_contacts = (Button)findViewById(R.id.btn_get_contacts);
        Button btn_get_photo = (Button)findViewById(R.id.btn_get_photo);
        Button btn_get_phone_num = (Button)findViewById(R.id.btn_get_phone_num);
        Button btn_get_message = (Button)findViewById(R.id.btn_get_message);
        Button btn_get_calls = (Button)findViewById(R.id.btn_get_calls);
        content = (TextView)findViewById(R.id.content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        btn_get_contacts.setOnClickListener(this);
        btn_get_photo.setOnClickListener(this);
        btn_get_phone_num.setOnClickListener(this);
        btn_get_message.setOnClickListener(this);
        btn_get_calls.setOnClickListener(this);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = manager.getDeviceId();
                *//*new AlertDialog.Builder(MainActivity.this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //res = backup(thiz);
                        //return "haha";
                    }
                }).create().show();*//*
         *//*try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }*//*
                Log.d("zhz","deviceID:"+deviceId);
                //Toast.makeText(ContextUtil.getContext(),deviceId,Toast.LENGTH_SHORT).show();
                doSomething();
            }
        });*/
    }

    private void doSomething(){
        Log.d("zhz","doSomething");
    }
    private void initDatas(){
        //Log.d(TAG, "initDatas");
        //TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Log.d("zhz","deviceID:"+manager.getDeviceId());

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zhz","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zhz", "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zhz", "onResume: ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_calls:
                getCalls();
                break;
            case R.id.btn_get_contacts:
                getContracts();
                break;
            case R.id.btn_get_message:
                getMessage();
                break;
            case R.id.btn_get_phone_num:
                getPhoneNum();
                break;
            case R.id.btn_get_photo:
                getPhoto();
                break;
        }
    }

    private void getCalls(){
        Uri uri = CallLog.Calls.CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        List<String> list = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
                list.add(name + ":" + number);

            }
            cursor.close();
            String str = "通话记录：\n";
            for (String name : list) {
                str += name + "\n";
            }
            content.setText(str);
        }else{
            Toast.makeText(HookDemoActivity.this,"获取不到信息",Toast.LENGTH_SHORT).show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                upload();
            }
        }).start();


    }
    private  void getContracts(){
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        List<String> list = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                //获取联系人的ID
            /*String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                    null, null);
            while(phones.moveToNext())
            {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                list.add(name+":"+phoneNumber);
            }
            phones.close();*/
                list.add(name);

            }
            cursor.close();
            String str = "联系人：\n";
            for(String name:list){
                str += name+"\n";
            }
            content.setText(str);
        }else{
            Toast.makeText(HookDemoActivity.this,"获取不到信息",Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                upload();
            }
        }).start();

    }
    private void getMessage(){
        Uri uri = Uri.parse("content://sms/");
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        List<String> list = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                list.add(name);

            }
            cursor.close();
            String str = "短信：\n";
            for (String name : list) {
                str += name + "\n";
            }
            content.setText(str);
        }else{
            Toast.makeText(HookDemoActivity.this,"获取不到信息",Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                upload();
            }
        }).start();
    }
    private void getPhoneNum(){

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String num = telephonyManager.getLine1Number();
        if(num != null) {
            content.setText("本机号码：" + num);
        }else{
            Toast.makeText(HookDemoActivity.this,"获取不到信息",Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                upload();
            }
        }).start();
    }
    private void getPhoto(){
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        List<String> list = new ArrayList<>();
        int i=0;
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                list.add(name);
                i++;
                if(i>10){
                    break;
                }
            }
            cursor.close();
            String str = "照片信息：\n";
            for (String name : list) {
                str += name + "\n";
            }
            content.setText(str);
        }else{
            Toast.makeText(HookDemoActivity.this,"获取不到信息",Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                upload();
            }
        }).start();
    }

    private void requestPermissions(){
        RxPermission rxPermission = RealRxPermission.getInstance(this) ;
        rxPermission.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_SMS
        )
                .subscribe();
    }

    private void upload(){
        try {
            URL url = new URL("http://www.baidu.com");
            Log.d("zhz", "upload: "+url.toString());
            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();

            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e("zhz", "Get方式请求成功，result--->" + result);
            } else {
                Log.e("zhz", "Get方式请求失败");
            }
            //Log.e("zhz", "Get方式请求失败");
            // 关闭连接
            urlConn.disconnect();
        }catch (Exception e){
            Log.d("zhz", "upload: "+e.toString());
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
