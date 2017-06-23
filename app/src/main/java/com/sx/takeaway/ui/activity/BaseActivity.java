package com.sx.takeaway.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.sx.takeaway.presenter.activity.ReceiptAddressPresenter;
import com.sx.takeaway.ui.IAddressView;

import javax.inject.Inject;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:16
 * @Description 基类
 */

public class BaseActivity extends AppCompatActivity implements IAddressView{
    @Inject
    ReceiptAddressPresenter mPresenter;

    @Override
    public void success(Object o) {

    }

    @Override
    public void failed(String msg) {

    }
}
