package com.sx.mvp.presenter;

import com.sx.mvp.MainActivity;
import com.sx.mvp.UserLoginNet;
import com.sx.mvp.model.User;
import com.sx.mvp.model.net.ResponseInfo;
import com.sx.mvp.presenter.api.ResponseInfoApi;
import com.sx.mvp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author sunxin
 * @Date 2017/5/19 17:49
 * @Description 与登录相关的业务处理
 */

public class MainActivityPresenter {

    private MainActivity mActivity;

    public MainActivityPresenter(MainActivity activity) {
        mActivity = activity;


    }

    public void login(String username, String password) {
        new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ResponseInfoApi.class)
                .login(username, password)
                .enqueue(new Callback<ResponseInfo>() {
                    @Override
                    public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                //登录成功
                                mActivity.success();
                            } else {
                                //错误提示

                            }
                        } else {
                            onFailure(call,new RuntimeException("服务器忙请稍后再试"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInfo> call, Throwable t) {
                        //登录失败
                        mActivity.failed();
                    }
                });
    }


    public void login1(String username, String password) {
        final User user = new User();
        user.username = username;
        user.password = password;
        //开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserLoginNet userLoginNet = new UserLoginNet();
                //判断是否登录成功
                if (userLoginNet.sendUserLoginInfo(user)) {
                    //登录成功
                    mActivity.success();
                } else {
                    mActivity.failed();
                }
            }
        }).start();
    }
}
