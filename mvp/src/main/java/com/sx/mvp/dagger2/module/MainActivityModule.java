package com.sx.mvp.dagger2.module;

import com.sx.mvp.MainActivity;
import com.sx.mvp.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/5/19 18:19
 * @Description 用户存放对象创建的代码；将代码放到指定类的指定方法中
 */
@Module
public class MainActivityModule {

    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainActivityPresenter provideMainActivityPresenter(){

        return new MainActivityPresenter(activity);
    }
}
