package com.sx.takeaway.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.dagger2.component.DaggerAddressComponent;
import com.sx.takeaway.dagger2.module.AddressModule;
import com.sx.takeaway.model.dao.bean.AddressBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author sunxin
 * @Date 2017/6/23 10:41
 * @Description 收货地址界面
 */

public class ReceiptAddressActivity extends BaseActivity {
    private static final String TAG = "ReceiptAddressActivity";
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;

    private RecyclerView.Adapter adapter;

    /*
    * 功能描述：
    *   1，为RecyclerView添加数据
    *       获取地址数据：
    *           从网络，获取到之后存储到本地数据库
    *           从本地
    *   2，新增地址入口
    *
    * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        Log.e(TAG, "onCreate: 创建");
        ButterKnife.bind(this);
        //注入
        DaggerAddressComponent
                .builder()
                .addressModule(new AddressModule(this))
                .build()
                .in(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, EditReceiptAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getContext().startActivity(intent);
                break;
        }
    }

    /**
     * 查询数据成功，需要显示在界面上
     *
     * @param o
     */
    public void success(Object o) {
        Log.e(TAG, "success: " + o.toString());

        if (o instanceof Integer){

        }

        List<AddressBean> been = (List<AddressBean>) o;

        if (been.size() > 0) {
            //填充RecyclerView
            mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new MyAdapter(been);
            mRvReceiptAddress.setAdapter(adapter);
        }else {
            //没有地址，直接跳转到添加地址页面
            Intent intent = new Intent(this, EditReceiptAddressActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);
        }
    }

    class MyAdapter extends RecyclerView.Adapter {
        private List<AddressBean> mBeen;

        public MyAdapter(List<AddressBean> been) {
            mBeen = been;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MyApplication.getContext(), R.layout.item_receipt_address, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AddressBean addressBean = mBeen.get(position);
            viewHolder.setData(addressBean);
        }

        private int getlabelIndex(String label) {
            int index = 0;
            for (int i = 0; i < addressLabels.length; i++) {
                if (label.equals(addressLabels[i])) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        String[] addressLabels = new String[]{"家", "公司", "学校"};
        int[] bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色

        };


        @Override
        public int getItemCount() {
            return mBeen.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
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
            @BindView(R.id.iv_edit)
            ImageView mIvEdit;
            private AddressBean mData;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }

            public void setData(AddressBean data) {
                mData = data;
                mTvName.setText(data.name);
                mTvSex.setText(data.sex);

                mTvPhone.setText(data.phone);


                if (!TextUtils.isEmpty(data.label)) {
                    mTvLabel.setVisibility(View.VISIBLE);
                    int index = getlabelIndex(data.label);
                    mTvLabel.setText(addressLabels[index]);
                    mTvLabel.setBackgroundColor(bgLabels[index]);
                } else {
                    mTvLabel.setVisibility(View.GONE);
                }

                mTvAddress.setText(data.receiptAddress + data.detailAddress);
            }
        }
    }
}
