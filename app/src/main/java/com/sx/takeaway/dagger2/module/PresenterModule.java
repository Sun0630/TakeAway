package com.sx.takeaway.dagger2.module;

import com.sx.takeaway.presenter.activity.OrderPresenter;
import com.sx.takeaway.presenter.activity.PaymentPresenter;
import com.sx.takeaway.presenter.activity.ReceiptAddressPresenter;
import com.sx.takeaway.ui.IView;

import dagger.Module;
import dagger.Provides;

/**
 * @Author sunxin
 * @Date 2017/6/26 19:03
 * @Description 业务处理类
 */
@Module
public class PresenterModule {
    private IView mView;

    public PresenterModule(IView view) {
        this.mView = view;
    }

    @Provides
    public OrderPresenter providesOrderPresenter(){
        return new OrderPresenter(mView);
    }

    @Provides
    public ReceiptAddressPresenter providesReceiptAddress(){
        return new ReceiptAddressPresenter(mView);
    }

    @Provides
    public PaymentPresenter providesPaymentAddress(){
        return new PaymentPresenter(mView);
    }
}
