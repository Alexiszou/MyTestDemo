package com.alexis.mytestdemo;

import java.lang.reflect.Constructor;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo
 * @ClassName: TestReflection
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/23 14:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 14:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestReflection {

    public static void main(String[] args) {
        Class<?> strClass = String.class;
        try {
            Object object = strClass.getConstructor(String.class);
            ((Constructor) object).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
