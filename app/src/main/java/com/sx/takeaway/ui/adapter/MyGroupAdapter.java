package com.sx.takeaway.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;
import com.sx.takeaway.ui.ShoppingCartManager;
import com.sx.takeaway.utils.AnimationUtils;
import com.sx.takeaway.utils.NumberFormatUtils;
import com.sx.takeaway.utils.UiUtils;

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
    private FrameLayout mContainer;
    private TextView mCount;

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

            //通过递归获取到顶层父容器，然后通过顶层父容器找到购物车图片
            if (mContainer == null) {
                mContainer = (FrameLayout) UiUtils.getContainder(v, R.id.seller_detail_container);
            }
            //修改气泡显示
            if (mCount == null) {
                mCount = (TextView) mContainer.findViewById(R.id.fragment_goods_tv_count);
            }

            switch (v.getId()) {
                case R.id.ib_minus://减少
                    minusHandler(v);
                    break;
                case R.id.ib_add://添加
                    addHandler(v);
                    break;
            }
        }

        /**
         * 减少的动画显示
         *
         * @param v
         */
        private void minusHandler(View v) {
            Integer num = ShoppingCartManager.getInstance().minusGood(data);
            if (num == 0) {
                AnimationSet animation = AnimationUtils.getHideMinusAnimation();
                ibMinus.startAnimation(animation);
                tvCount.startAnimation(animation);
                //设置监听
                animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //当动画完毕
                        tvCount.setVisibility(View.GONE);
                        ibMinus.setVisibility(View.GONE);
                    }
                });
            }
            tvCount.setText(num.toString());

            //处理气泡的显示
            Integer totalNum = ShoppingCartManager.getInstance().getTotalNum();
            if (totalNum == 0) {
                mCount.setVisibility(View.INVISIBLE);
            }
            mCount.setText(totalNum.toString());
        }

        /**
         * 添加的动画显示
         *
         * @param v
         */
        private void addHandler(View v) {
            Integer num = ShoppingCartManager.getInstance().addGoods(data);//购物车中商品的数量
            if (num == 1) {
                AnimationSet animation = AnimationUtils.getShowMinusAnimation();
                tvCount.startAnimation(animation);
                ibMinus.startAnimation(animation);

                tvCount.setVisibility(View.VISIBLE);
                ibMinus.setVisibility(View.VISIBLE);
            }
            tvCount.setText(num.toString());

            //飞向购物车动画
            flyToCart(v);



            Integer totalNum = ShoppingCartManager.getInstance().getTotalNum();
            if (num > 0) {
                mCount.setVisibility(View.VISIBLE);
            }
            mCount.setText(totalNum.toString());

        }

        private void flyToCart(View v) {
            //获取该控件的位置
            int[] location = new int[2];
            v.getLocationOnScreen(location);//获取到控件所在屏幕的位置

            //获取目标位置

            int[] targetLocation = new int[2];
            mContainer.findViewById(R.id.cart).getLocationOnScreen(targetLocation);

            /*getLocationOnScreen()获取控件在屏幕上的高度，需要在y轴方向将状态栏高度减掉*/
            location[1] -= UiUtils.STATUE_BAR_HEIGHT;
            targetLocation[1] -= UiUtils.STATUE_BAR_HEIGHT;

            //创建一个控件，放到“+”按钮地方，执行动画
            final ImageView view = getImageView(location, v);
            //执行动画
            Animation animation = AnimationUtils.getAddAnimation(targetLocation, location);
            view.startAnimation(animation);
            animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mContainer.removeView(view);
                }
            });

        }

        private ImageView getImageView(int[] location, View v) {
            ImageView iv = new ImageButton(MyApplication.getContext());
            //设置iv的位置
            iv.setX(location[0]);
            iv.setY(location[1]);
            iv.setBackgroundResource(R.mipmap.food_button_add);
            //添加到容器中,放到何处
//            ((ViewGroup) itemView).addView(iv,v.getWidth(),v.getHeight());
            //添加到顶层的父容器中，保证动画能够被看见
            mContainer.addView(iv, v.getWidth(), v.getHeight());

            return iv;
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

            //判断购物车中是否有当前商品 ，如果有就获取到商品数量显示上去
            Integer num = ShoppingCartManager.getInstance().getGoodsIdNum(data.id);
            if (num > 0) {
                ibMinus.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(num.toString());
            }else {
                ibMinus.setVisibility(View.INVISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
            }
        }
    }
}