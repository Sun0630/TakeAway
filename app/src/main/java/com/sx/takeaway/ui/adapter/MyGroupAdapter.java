package com.sx.takeaway.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;
import com.sx.takeaway.utils.NumberFormatUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by itheima.
 */

public class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private final ArrayList<GoodsTypeInfo> headDataSet;
    private final ArrayList<GoodsInfo> itemDataSet;

    public MyGroupAdapter(ArrayList<GoodsTypeInfo> headDataSet, ArrayList<GoodsInfo> itemDataSet) {
        this.headDataSet = headDataSet;
        this.itemDataSet = itemDataSet;
    }

    //////////////////////////////////头管理/////////////////////////////////////////////
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        // 向下滚动：头数据加载的是每组的第一条
        // 向上滚动：头数据加载的是每组的最后一条

        TextView head = new TextView(parent.getContext());
        GoodsTypeInfo headData = headDataSet.get(itemDataSet.get(position).headIndex);
        head.setText(headData.name);
        head.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.colorItemBg));
        return head;
    }

    @Override
    public long getHeaderId(int position) {
        return itemDataSet.get(position).headId;
    }

    //////////////////////////////////普通条目管理/////////////////////////////////////////////
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsInfo data = itemDataSet.get(position);
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(), R.layout.item_goods, null);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        holder.setData(data);
        return convertView;
    }

    @Override
    public int getCount() {
        return itemDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ItemViewHolder {
        View itemView;
        private GoodsInfo data;

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_zucheng)
        TextView tvZucheng;
        @BindView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;
        @BindView(R.id.tv_newprice)
        TextView tvNewprice;
        @BindView(R.id.tv_oldprice)
        TextView tvOldprice;
        @BindView(R.id.ib_minus)
        ImageButton ibMinus;
        @BindView(R.id.tv_money)
        TextView tvCount;
        @BindView(R.id.ib_add)
        ImageButton ibAdd;


        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, this.itemView);
        }

        @OnClick({R.id.ib_minus, R.id.ib_add})
        public void onClick(View v) {
        }
        public void setData(GoodsInfo data) {
            this.data = data;

            //图片
            Picasso.with(MyApplication.getContext()).load(data.icon).into(ivIcon);
            tvName.setText(data.name);
            if (TextUtils.isEmpty(data.form)) {
                tvZucheng.setVisibility(View.GONE);
            } else {
                tvZucheng.setVisibility(View.VISIBLE);
                tvZucheng.setText(data.form);
            }
            tvYueshaoshounum.setText("月销售" + data.monthSaleNum + "份");
            tvNewprice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            if (data.oldPrice == 0) {
                tvOldprice.setVisibility(View.GONE);
            } else {
                tvOldprice.setVisibility(View.VISIBLE);
                tvOldprice.setText(NumberFormatUtils.formatDigits(data.oldPrice));
                //TextView出现中间的线
                tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}