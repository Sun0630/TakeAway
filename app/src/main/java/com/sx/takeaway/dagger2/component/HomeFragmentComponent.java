package com.sx.takeaway.dagger2.component;

import com.sx.takeaway.dagger2.module.HomeFragmentModule;
import com.sx.takeaway.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/5/23 16:49
 * @Description 将创建好的业务对象设置给目标
 */

@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void in(HomeFragment fragment);
}
