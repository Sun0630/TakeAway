package com.sx.takeaway.dagger2.component.fragment;

import com.sx.takeaway.dagger2.module.PresenterModule;
import com.sx.takeaway.ui.fragment.OrderFragment;

import dagger.Component;

/**
 * @Author sunxin
 * @Date 2017/7/9 8:39
 * @Description
 */
@Component(modules = PresenterModule.class)
public interface OrderFragmentComponent {
    void in(OrderFragment fragment);
}
