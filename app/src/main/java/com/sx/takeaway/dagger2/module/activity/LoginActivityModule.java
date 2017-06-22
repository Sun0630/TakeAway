package com.sx.takeaway.dagger2.module.activity;

import com.sx.takeaway.presenter.activity.LoginActivityPresenter;
import com.sx.takeaway.ui.activity.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/6/22 13:49
 * @Description 登录模块
 */

@Module
public class LoginActivityModule {
    private LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    public LoginActivityPresenter provideLoginActivityPresenter(){
        return new LoginActivityPresenter(activity);
    }
}
