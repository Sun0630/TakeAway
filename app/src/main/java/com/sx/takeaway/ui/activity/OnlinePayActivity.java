package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sx.takeaway.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author sunxin
 * @Date 2017/6/26 23:39
 * @Description 在线支付页面
 */

public class OnlinePayActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_residualTime)
    TextView mTvResidualTime;
    @BindView(R.id.tv_order_name)
    TextView mTvOrderName;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_order_detail)
    TextView mTvOrderDetail;
    @BindView(R.id.iv_triangle)
    ImageView mIvTriangle;
    @BindView(R.id.ll_order_toggle)
    RelativeLayout mLlOrderToggle;
    @BindView(R.id.tv_receipt_connect_info)
    TextView mTvReceiptConnectInfo;
    @BindView(R.id.tv_receipt_address_info)
    TextView mTvReceiptAddressInfo;
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;
    @BindView(R.id.ll_order_detail)
    LinearLayout mLlOrderDetail;
    @BindView(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @BindView(R.id.ll_pay_type_container)
    LinearLayout mLlPayTypeContainer;
    @BindView(R.id.bt_confirm_pay)
    Button mBtConfirmPay;
    private String mOrderid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        ButterKnife.bind(this);

        mOrderid = getIntent().getStringExtra("orderid");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaymentPresenter.getData(mOrderid);
    }

    @Override
    public void success(Object o) {
        if (o instanceof HashMap) {

        }
    }

    @OnClick({R.id.ib_back, R.id.ll_order_toggle, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                break;
            case R.id.ll_order_toggle:
                break;
            case R.id.bt_confirm_pay:
                break;
        }
    }
}
