package com.sx.takeaway.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.ui.ShoppingCartManager;
import com.sx.takeaway.ui.views.RecycleViewDivider;
import com.sx.takeaway.utils.NumberFormatUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author sunxin
 * @Date 2017/6/8 1:40
 * @Description 购物车页面
 */

public class CartActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.cart_rv)
    RecyclerView mCartRv;
    private MyCartAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        //设置ToolBar
        initToolBar();
        MyCartAdapter adapter = new MyCartAdapter();
        mCartRv.setAdapter(adapter);
        mCartRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //绘制分割线
        mCartRv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1,0XE3E0DC));
        mTvMoney.setText(NumberFormatUtils.formatDigits(ShoppingCartManager.getInstance().getMoney()/100.0));
    }

    private void initToolBar() {
        mToolbar.setTitle("购物车");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button)
    public void onClick() {
        // TODO: 2017/6/8 登录第一入口 ，判断是否登录，如果登录跳转生成订单，如果没有登录，跳转登录页面
        //通过判断USERID是否等0
        Intent intent = null;
        if (MyApplication.USERID == 0){
            //跳转登录界面
            intent = new Intent(this,LoginActivity.class);
        }else {
            //跳转到结算中心界面
            intent = new Intent(this,SettleCenterActivity.class);
        }
        startActivity(intent);

    }

    class MyCartAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(View.inflate(MyApplication.getContext(), R.layout.item_cart, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setData(ShoppingCartManager.getInstance().mGoodsInfos.get(position));
        }

        @Override
        public int getItemCount() {
            return ShoppingCartManager.getInstance().mGoodsInfos.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv)
        ImageView mItemIv;
        @BindView(R.id.item_tv_name)
        TextView mItemTvName;
        @BindView(R.id.item_tv_price)
        TextView mItemTvPrice;
        @BindView(R.id.item_tv_num)
        TextView mItemTvNum;
        private GoodsInfo mGoodsInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, this.itemView);
        }

        public void setData(GoodsInfo goodsInfo) {
            mGoodsInfo = goodsInfo;
            //设置Icon
            Picasso
                    .with(CartActivity.this)
                    .load(mGoodsInfo.icon)
                    .into(mItemIv);

            mItemTvName.setText(mGoodsInfo.name);
            mItemTvPrice.setText(NumberFormatUtils.formatDigits(mGoodsInfo.newPrice));
            mItemTvNum.setText(mGoodsInfo.count+"");
        }
    }

}
