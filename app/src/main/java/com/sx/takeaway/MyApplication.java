package com.sx.takeaway;

import android.app.Application;
import android.content.Context;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:51
 * @Description
 */

public class MyApplication extends Application {

    private static Context context;

    //存储一些用户信息
    public static int USERID = 0;
    public static String phone = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
