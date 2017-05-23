package com.sx.takeaway.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.Head;
import com.sx.takeaway.model.net.bean.HomeInfo;
import com.sx.takeaway.model.net.bean.HomeItem;
import com.sx.takeaway.model.net.bean.Seller;

/**
 * @Author sunxin
 * @Date 2017/5/23 11:29
 * @Description
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //三种布局类型
    private final int TYPE_HEAD = 0;//头部
    private final int TYPE_SELLER = 1;//商家
    private final int TYPE_RECOMMEND = 2;//推荐

    private HomeInfo mData;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return new HeadHolder(View.inflate(MyApplication.getContext(), R.layout.item_title, null));
            case TYPE_SELLER:
                return new SellerHolder(View.inflate(MyApplication.getContext(), R.layout.item_seller, null));
            case TYPE_RECOMMEND:
                return new RecommendHolder(View.inflate(MyApplication.getContext(), R.layout.item_division, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_HEAD:
                //设置数据
                ((HeadHolder) holder).setData(mData.head);
                break;
            case TYPE_SELLER:
                HomeItem item = mData.body.get(position - 1);
                ((SellerHolder) holder).setData(item.seller);
                break;
            case TYPE_RECOMMEND:
                ((RecommendHolder) holder).setData(mData.body.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData == null) {
            count = 0;
        } else {
            count = mData.body.size() + 1;//加上头布局
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            //头布局
            type = TYPE_HEAD;
        } else {
            HomeItem item = mData.body.get(position - 1);//减掉头布局
            type = item.type == 0 ? TYPE_SELLER : TYPE_RECOMMEND;
        }
        return type;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(HomeInfo data) {
        mData = data;
    }

    /**
     * 头布局容器
     */
    class HeadHolder extends RecyclerView.ViewHolder {

        private Head mData;

        public HeadHolder(View itemView) {
            super(itemView);
        }

        public void setData(Head data) {
            mData = data;
        }
    }

    /**
     * 商家
     */
    class SellerHolder extends RecyclerView.ViewHolder {

        private Seller mData;
        TextView tvTitle;

        public SellerHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(Seller data) {
            mData = data;
            tvTitle.setText(mData.name);
        }
    }

    /**
     * 推荐
     */
    class RecommendHolder extends RecyclerView.ViewHolder {

        private HomeItem mData;

        public RecommendHolder(View itemView) {
            super(itemView);
        }

        public void setData(HomeItem data) {
            mData = data;
        }
    }
}
