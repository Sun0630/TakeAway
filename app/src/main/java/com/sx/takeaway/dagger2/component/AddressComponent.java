package com.sx.takeaway.dagger2.component;

import com.sx.takeaway.dagger2.module.AddressModule;
import com.sx.takeaway.ui.activity.BaseActivity;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/6/23 12:50
 * @Description 依赖提供方和依赖注入方的桥梁
 */

@Component(modules = AddressModule.class)
public interface AddressComponent {
    void in(BaseActivity activity);
}
