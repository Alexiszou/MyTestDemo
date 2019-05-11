package com.alexis.mytestdemo.utils;

import android.util.Log;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo.utils
 * @ClassName: LogUtil
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/10 17:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/10 17:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LogUtil {

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void e(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

}
