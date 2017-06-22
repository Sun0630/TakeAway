package com.sx.takeaway.dagger2.module.fragment;

import com.sx.takeaway.presenter.fragment.HomeFragmentPresenter;
import com.sx.takeaway.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/5/23 16:48
 * @Description
 */

@Module
public class HomeFragmentModule {

    private HomeFragment mFragment;

    public HomeFragmentModule(HomeFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    public HomeFragmentPresenter providerHomeFragmentPresenter() {
        return new HomeFragmentPresenter(mFragment);
    }
}
