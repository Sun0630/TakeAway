package com.sx.takeaway.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.Category;
import com.sx.takeaway.model.net.bean.Head;
import com.sx.takeaway.model.net.bean.HomeInfo;
import com.sx.takeaway.model.net.bean.HomeItem;
import com.sx.takeaway.model.net.bean.Promotion;
import com.sx.takeaway.model.net.bean.Seller;
import com.sx.takeaway.ui.ShoppingCartManager;
import com.sx.takeaway.ui.activity.SellerDetailActivity;

import java.util.ArrayList;

/**
 * @Author sunxin
 * @Date 2017/5/23 11:29
 * @Description 主页列表适配器
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //三种布局类型
    private final int TYPE_HEAD = 0;//头部
    private final int TYPE_SELLER = 1;//商家
    private final int TYPE_RECOMMEND = 2;//推荐

    private HomeInfo mData;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEAD:
                holder =  new HeadHolder(View.inflate(MyApplication.getContext(), R.layout.item_title, null));
                break;
            case TYPE_SELLER:
                holder = new SellerHolder(View.inflate(MyApplication.getContext(), R.layout.item_seller, null));
                break;
            case TYPE_RECOMMEND:
                holder = new RecommendHolder(View.inflate(MyApplication.getContext(), R.layout.item_division, null));
                break;
        }

        return holder;
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
        notifyDataSetChanged();
        mData = data;
    }

    /**
     * 头布局容器
     */
    class HeadHolder extends RecyclerView.ViewHolder {
        SliderLayout mSliderLayout;
        LinearLayout mCategoryContainer;
        private Head mData;

        public HeadHolder(View itemView) {
            super(itemView);
            mSliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
            mCategoryContainer = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }

        public void setData(Head data) {
            mData = data;
            //清空
            mSliderLayout.removeAllSliders();
            if (data != null && data.promotionList.size() > 0) {
                ArrayList<Promotion> promotions = mData.promotionList;
                for (Promotion item : promotions) {
                    TextSliderView textSliderView = new TextSliderView(MyApplication.getContext());
//                    textSliderView.image("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=8a7d20c1b8b7d0a264c9039dfbef760d/9d82d158ccbf6c81a743609bb63eb13533fa4048.jpg");
                    textSliderView.image(item.pic);
                    System.out.println("PicUrl:" + item.pic);
                    textSliderView.description(item.info);
                    mSliderLayout.addSlider(textSliderView);


                }
            }

            //下方
            if (data != null && data.categorieList.size() > 0) {
                //先清空所有
                mCategoryContainer.removeAllViews();
                View item = null;
                for (int i = 0; i < data.categorieList.size(); i++) {
                    Category category = data.categorieList.get(i);
                    if (i % 2 == 0) {//每个条目中的第一个元素
                        item = View.inflate(MyApplication.getContext(), R.layout.item_home_head_category, null);

                        Picasso
                                .with(MyApplication.getContext())
                                .load(category.pic)
                                .into((ImageView) item.findViewById(R.id.top_iv));

                        ((TextView) item.findViewById(R.id.top_tv)).setText(category.name);
                        mCategoryContainer.addView(item);
                    }

                    if (i % 2 != 0) {
                        Picasso
                                .with(MyApplication.getContext())
                                .load(category.pic)
                                .into((ImageView) item.findViewById(R.id.bottom_iv));

                        ((TextView) item.findViewById(R.id.bottom_tv)).setText(category.name);
                    }

                }
            }
        }
    }

    /**
     * 商家
     */
    class SellerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Seller mData;
        TextView tvTitle;
        TextView tvCount;//红点

        public SellerHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        }

        public void setData(Seller data) {
            mData = data;
            tvTitle.setText(mData.name);

            //设置已经购买的商品数量，需要根据商家标识进行区分
            if (mData.id == ShoppingCartManager.getInstance().sellerId){
                Integer num = ShoppingCartManager.getInstance().getTotalNum();
                if (num > 0) {
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(num.toString());
                } else {
                    tvCount.setVisibility(View.GONE);
                }
            }else {
                tvCount.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SellerDetailActivity.class);
            intent.putExtra("seller_id", mData.id);
            intent.putExtra("name", mData.name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (ShoppingCartManager.getInstance().sellerId != mData.id){
                //进入购物车时更新商家标识
                ShoppingCartManager.getInstance().sellerId = mData.id;
                ShoppingCartManager.getInstance().url = mData.pic;
                ShoppingCartManager.getInstance().name = mData.name;
                ShoppingCartManager.getInstance().sendPrice = mData.sendPrice;//配送费
                //清除原来商家标识
                ShoppingCartManager.getInstance().clear();
            }
            MyApplication.getContext().startActivity(intent);
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
