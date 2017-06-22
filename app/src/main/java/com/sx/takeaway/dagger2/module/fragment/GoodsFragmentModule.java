package com.sx.takeaway.dagger2.module.fragment;

import com.sx.takeaway.presenter.fragment.GoodsFragmentPresenter;
import com.sx.takeaway.ui.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/6/7 12:21
 * @Description
 */

@Module
public class GoodsFragmentModule {

    private GoodsFragment mFragment;

    public GoodsFragmentModule(GoodsFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    GoodsFragmentPresenter provideGoodsFragmentPresenter(){
        return new GoodsFragmentPresenter(mFragment);
    }
}
