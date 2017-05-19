package com.sx.mvp.dagger2.component;

import com.sx.mvp.MainActivity;
import com.sx.mvp.dagger2.module.MainActivityModule;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/5/19 18:22
 * @Description 第三步：通过接口将创建实例的代码和目标关联在一起“=”
 */

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void in(MainActivity activity);
}
