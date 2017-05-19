package com.sx.mvp.presenter;

import com.sx.mvp.MainActivity;
import com.sx.mvp.UserLoginNet;
import com.sx.mvp.model.User;

/**
 * @Author sunxin
 * @Date 2017/5/19 17:49
 * @Description  与登录相关的业务处理
 */

public class MainActivityPresenter {

    private MainActivity mActivity;

    public MainActivityPresenter(MainActivity activity) {
        mActivity = activity;
    }

    public void login(String username, String password){
        final User user = new User();
        user.username = username;
        user.password = password;

        //开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserLoginNet userLoginNet = new UserLoginNet();
                //判断是否登录成功
                if (userLoginNet.sendUserLoginInfo(user)){
                    //登录成功
                    mActivity.success();
                }else {
                    mActivity.failed();
                }
            }
        }).start();
    }
}
