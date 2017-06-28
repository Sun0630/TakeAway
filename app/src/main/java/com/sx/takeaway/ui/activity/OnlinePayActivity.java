package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.picasso.Picasso;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.PaymentInfo;
import com.sx.takeaway.utils.PayResult;
import com.sx.takeaway.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    //商户PID
    public static final String PARTNER = "2088011085074233";
    //商户收款账号
    public static final String SELLER = "917356107@qq.com";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL51jaxQhxW9PnWpW+nz6yJ76tp9eGFXmfGnuxMK+Pmx/qavdsewXOLBfI2OSCR39TzxwMYvCmUrnrt0fVSa7mblbNos2FnMM9ijnx8bsAAhm+i7BKhuaHMunJKH69L+D753zH3P1YIh0ly5DnAr3WPqHydp384qBvb8NS9Tay0HAgMBAAECgYB82PIVknP6fCMFXg8yPQJViIVa1ASlSpdPIXQv93FdvKABA+QI4kMBIXRUFoCT506KtK55OzzFNOLIXoQJgcXj69z0l6pmjJJgXMaBW/9rOzelot13CiGatrIrGngEZO+bCBTud/jQA598zjZ1g182tT+FLDL7GIftW2hC8GqtAQJBAN+XrYsyfL+uSmLdAVEz1vzziU1naGr10Msm1jMnnO/JYdB+84j7FSHxsQ4YOgsmeN5YVsJcVfc/CReOxknns38CQQDaEHnVPDt+Z7sqT7bN0UKh0/CrqkDTiIjhz1lJyIIoqVRoeJjJn1wlEKBV5R9gkTJutQTVU19XFtblMEnOy6p5AkEAw170rEmMSa0QoHw+d2bVtydR1QnDapqqO6kOx5oYfkm4J4eWYx4J5CQdMpSmuzF9scL85E3sa+NvnV8LEm7cHwJALtXzFPWG4bNt47yTSslzQka/Hl/G5Kginj1mtA44xnr4AihEyKlNpThY95nqj1cgOd7vVtI9W/sv1LH2aFAeIQJBAIqXbMc6xGVfuiFAJKtg+AFNMBP0UOEgMEoKo4RPFp21nBhFgL9/WYM4ZjyHUdr45rCySAqQovw4DCHLfQZC23I=";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+dY2sUIcVvT51qVvp8+sie+rafXhhV5nxp7sTCvj5sf6mr3bHsFziwXyNjkgkd/U88cDGLwplK567dH1Umu5m5WzaLNhZzDPYo58fG7AAIZvouwSobmhzLpySh+vS/g++d8x9z9WCIdJcuQ5wK91j6h8nad/OKgb2/DUvU2stBwIDAQAB";

    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        ButterKnife.bind(this);

        mOrderid = getIntent().getStringExtra("orderid");
        mTvOrderName.setText("第"+mOrderid+"号订单");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaymentPresenter.getData(mOrderid);
    }

//    private Handler mHandler = new Handler();

    @Override
    public void success(Object o) {
        if (o instanceof HashMap) {
            HashMap dataMap = (HashMap) o;
            int payDownTime = (int) dataMap.get("payDownTime");
            mHandler.post(new MyResidualTimerTask(payDownTime * 60));//处理倒计时
            mTvPayMoney.setText("￥" + dataMap.get("money").toString());
            List<PaymentInfo> payments = (List<PaymentInfo>) dataMap.get("paymentInfo");
            addPayment(payments);
        }
    }
    // 处理支付方式的选择：
    // 在支付条目添加时候，获取所有条目的CheckBox，将这些CheckBox引用添加到一个容器，当某个CheckBox被选中的时候需要循环该容器，其他的全部修改为没有选中状态

    ArrayList<CheckBox> cbs = new ArrayList<>();

    private void addPayment(List<PaymentInfo> payments) {
        for (PaymentInfo info :
                payments) {
            //分割线
            View view = new View(this);
            view.setBackgroundColor(0xfd9b9999);//设置背景颜色
            //设置高度为1dp
            int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
            mLlPayTypeContainer.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, h);

            //设置条目
            view = View.inflate(this, R.layout.item_online_pay, null);
            Picasso
                    .with(this)
                    .load(info.url)
                    .into((ImageView) view.findViewById(R.id.iv_pay_logo));

            ((TextView) view.findViewById(R.id.tv_pay_name)).setText(info.name);

            // 如何做到点击某个CheckBox知道其对应的支付信息
            CheckBox cb = (CheckBox) view.findViewById(R.id.cb_pay_selected);

            cb.setTag(info.id);//id就是对应的支付方式的type值
            cbs.add(cb);

            mLlPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setCheckBoxCheckedListener();
    }

    private int paymentType = -1;

    private void setCheckBoxCheckedListener() {
        for (CheckBox item :
                cbs) {
            item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (CheckBox cb :
                                cbs) {
                            if (cb != buttonView) {
                                cb.setChecked(false);
                            } else {
                                paymentType = (int) cb.getTag();
                            }
                        }
                    }
                }
            });
        }
    }

    @OnClick({R.id.ib_back, R.id.ll_order_toggle, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ll_order_toggle:
                break;
            case R.id.bt_confirm_pay:
                pay();
                break;
        }
    }

    /**
     * 处理倒计时支付时间
     */
    private class MyResidualTimerTask implements Runnable {

        private int time;

        public MyResidualTimerTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            time = time - 1;
            if (time == 0) {
                mBtConfirmPay.setEnabled(false);
            }
            int m = time / 60;
            int s = time % 60;
            mTvResidualTime.setText("支付剩余时间:" + m + "分" + s + "秒");
            mHandler.postDelayed(this, 999);
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OnlinePayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OnlinePayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OnlinePayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay() {
        // 订单
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OnlinePayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
