package com.sx.takeaway;

import android.app.Application;
import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:51
 * @Description
 */

public class MyApplication extends Application {

    private static Context context;

    //存储一些用户信息
//    public static int USERID = 0;
//    public static String phone = "";

    //测试数据，不用每次都登录
    public static int USERID = 3419;
    public static String phone = "13200000001";
    //定位坐标
    public static LatLonPoint LOCATION;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //初始化极光推送
        JPushInterface.setDebugMode(true);//设置为调试模式
        JPushInterface.init(this);
        //初始化友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //关闭Activity的默认统计方式，因为我们需要统计Activity+Fragment
        MobclickAgent.openActivityDurationTrack(false);
    }

    public static Context getContext() {
        return context;
    }
}
