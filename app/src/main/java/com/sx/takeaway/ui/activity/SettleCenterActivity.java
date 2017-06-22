package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.ui.ShoppingCartManager;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author sunxin
 * @Date 2017/6/22 23:54
 * @Description 结算中心界面
 * <p>
 * 要做到：
 * 1，地址管理
 * 2，设置购物车数据
 */

public class SettleCenterActivity extends BaseActivity {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_selected_address_container)
    LinearLayout mLlSelectedAddressContainer;
    @BindView(R.id.tv_select_address)
    TextView mTvSelectAddress;
    @BindView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @BindView(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @BindView(R.id.tv_send_price)
    TextView mTvSendPrice;
    @BindView(R.id.tv_count_price)
    TextView mTvCountPrice;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_center);
        ButterKnife.bind(this);

        setData();
    }

    /*
    * 收货地址工作：
    *   1，到数据库中获取地址列表
    *   2，设置默认地址 ，在首页会有定位拿到经纬度，在地址中会存放经纬度信息，两个经纬度
    *       信息进行比对，计算两点之间的距离，取离当前最近的地址（小于200米）作为默认地址
    *   3，用户手动设置地址
    * */

    private void setData() {
        /*
        * 设置数据：
        *   1，商家数据
        *   2，购买商品数据
        *   3，配送费
        *   4，商品总额
        * */

        //商家数据
        mIvLogo.setImageResource(R.drawable.item_logo);
        mTvSellerName.setText(ShoppingCartManager.getInstance().name);

        //商品数据，一条条的添加到容器中
        //首先计算要添加多少条数据
        CopyOnWriteArrayList<GoodsInfo> goodsInfos = ShoppingCartManager.getInstance().mGoodsInfos;
        for (GoodsInfo goods :
                goodsInfos) {
            View v = View.inflate(this, R.layout.item_settle_center_goods, null);
            //查找控件
            ((TextView) v.findViewById(R.id.tv_name)).setText(goods.name);
            ((TextView) v.findViewById(R.id.tv_count)).setText("X" + goods.count);
            ((TextView) v.findViewById(R.id.tv_price)).setText("￥" + goods.newPrice);
            //设置宽高
            // android:layout_width="match_parent" android:layout_height="30dp"
            //单位转化
            int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            mLlSelectGoods.addView(v, ViewGroup.LayoutParams.MATCH_PARENT, h);
            //设置配送费
            mTvSendPrice.setText("￥" + ShoppingCartManager.getInstance().sendPrice);
            //设置支付总额 ，购物车的钱+配送费
            float money = ShoppingCartManager.getInstance().getMoney()/100.0f + ShoppingCartManager.getInstance().sendPrice;
            mTvCountPrice.setText("待支付￥" + money);
        }
    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back://后退
                break;
            case R.id.rl_location://选择地址
                // TODO: 2017/6/23 地址管理入口
                break;
            case R.id.tv_submit://提交订单
                break;
        }
    }
}