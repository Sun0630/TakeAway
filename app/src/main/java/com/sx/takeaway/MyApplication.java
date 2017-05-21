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

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
