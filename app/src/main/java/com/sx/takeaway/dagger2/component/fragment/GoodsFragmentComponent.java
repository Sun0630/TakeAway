package com.sx.takeaway.dagger2.component.fragment;

import com.sx.takeaway.dagger2.module.fragment.GoodsFragmentModule;
import com.sx.takeaway.ui.fragment.GoodsFragment;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/6/7 12:23
 * @Description
 */

@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentComponent {
    void in(GoodsFragment fragment);
}
