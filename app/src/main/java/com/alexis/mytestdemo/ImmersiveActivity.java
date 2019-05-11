package com.alexis.mytestdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo
 * @ClassName: ImmersiveActivity
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/4/27 14:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/27 14:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImmersiveActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive);
    }
}
