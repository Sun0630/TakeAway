package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sx.takeaway.dagger2.component.DaggerCommonComponent;
import com.sx.takeaway.dagger2.module.PresenterModule;
import com.sx.takeaway.presenter.activity.OrderPresenter;
import com.sx.takeaway.presenter.activity.PaymentPresenter;
import com.sx.takeaway.presenter.activity.ReceiptAddressPresenter;
import com.sx.takeaway.ui.IView;

import javax.inject.Inject;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:16
 * @Description 基类
 */

public class BaseActivity extends AppCompatActivity implements IView {
    @Inject
    ReceiptAddressPresenter mAddressPresenter;

    @Inject
    OrderPresenter mOrderPresenter;

    @Inject
    PaymentPresenter mPaymentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCommonComponent
                .builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .in(this);
    }

    @Override
    public void success(Object o) {

    }

    @Override
    public void failed(String msg) {

    }
}
