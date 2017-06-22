package com.sx.takeaway.dagger2.component.activity;

import com.sx.takeaway.dagger2.module.activity.LoginActivityModule;
import com.sx.takeaway.ui.activity.LoginActivity;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/6/22 13:52
 * @Description
 */

@Component(modules = LoginActivityModule.class)
public interface LoginActivityComponent {
    void in(LoginActivity activity);
}
