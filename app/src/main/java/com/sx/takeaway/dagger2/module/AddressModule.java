package com.sx.takeaway.dagger2.module;

import com.sx.takeaway.presenter.activity.ReceiptAddressPresenter;
import com.sx.takeaway.ui.IAddressView;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/6/23 12:49
 * @Description
 */

@Module
public class AddressModule {
    private IAddressView mView;

    public AddressModule(IAddressView activity) {
        this.mView = activity;
    }

    @Provides
    public ReceiptAddressPresenter providesReceiptAddress(){
        return new ReceiptAddressPresenter(mView);
    }
}
