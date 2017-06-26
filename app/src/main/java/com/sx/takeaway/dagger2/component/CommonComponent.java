package com.sx.takeaway.dagger2.component;

import com.sx.takeaway.dagger2.module.PresenterModule;
import com.sx.takeaway.ui.activity.BaseActivity;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/6/26 19:05
 * @Description 通用的Component
 */
@Component(modules = PresenterModule.class)
public interface CommonComponent {
    void in(BaseActivity activity);
}
