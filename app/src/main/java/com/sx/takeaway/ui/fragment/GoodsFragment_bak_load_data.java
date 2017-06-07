package com.sx.takeaway.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;
import com.sx.takeaway.presenter.fragment.GoodsFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description 商品详情Fragment
 */

public class GoodsFragment_bak_load_data extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.slh)
    StickyListHeadersListView mSlh;
    @BindView(R.id.lv)
    ListView mLv;

    private MyGroupAdapter mAdapter;
    private MyHeaderAdapter headAdapter;

    @Inject
    GoodsFragmentPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进行Dagger注入
//        DaggerGoodsFragmentComponent
//                .builder()
//                .goodsFragmentModule(new GoodsFragmentModule(this))
//                .build()
//                .in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData(getArguments().getLong("seller_id"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 左侧头容器的点击事件
        headAdapter.setSelectPosition(position);
        //让分组置顶
        GoodsTypeInfo head = mHeads.get(position);
        mSlh.setSelection(head.groupFirstIndex);
    }

    //是否是用户在滚动
    private boolean isScrolle = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState:" + scrollState);
        //用户在滚动
        isScrolle = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //需要判断是用户滑动调用还是点击左侧头布局调用
        if (isScrolle) {
            System.out.println("firstVisibleItem:" + firstVisibleItem);
            //如果是用户在滚动
            GoodsInfo data = mDatas.get(firstVisibleItem);
            headAdapter.setSelectPosition(data.headIndex);
            //判断头布局是否可见，
            //获取第一个可见的和最后一个可见的，比第一个可见的小，比最后一个可见的大的都是不可见的
            int firstVisiblePosition = mLv.getFirstVisiblePosition();
            int lastVisiblePosition = mLv.getLastVisiblePosition();

            if (data.headIndex <= firstVisiblePosition || data.headId >= lastVisiblePosition) {
                //使其可见
                mLv.setSelection(data.headIndex);
            }
        }
    }

    /**
     * 添加测试普通数据
     */
    private ArrayList<GoodsInfo> mDatas = new ArrayList<>();


    /**
     * 头部数据
     */
    private ArrayList<GoodsTypeInfo> mHeads = new ArrayList<>();

    /**
     * 解析数据成功之后更新界面
     *
     * @param goodsTypeInfos
     */
    public void success(ArrayList<GoodsTypeInfo> goodsTypeInfos) {
        //按照数据结构处理数据
        mHeads = goodsTypeInfos;

        for (int hi = 0; hi < mHeads.size(); hi++) {
            GoodsTypeInfo head = mHeads.get(hi);
            //为每个头添加数据
            //普通条目
            for (int di = 0; di < head.list.size(); di++) {
                GoodsInfo data = head.list.get(di);
                data.headId = head.id;//可以使任意的
                data.headIndex = hi;
                if (di == 0)//获取分组第一条数据的下标
                    head.groupFirstIndex = mDatas.size();
                mDatas.add(data);
            }
        }


        mAdapter = new MyGroupAdapter();
        mSlh.setAdapter(mAdapter);

        headAdapter = new MyHeaderAdapter();
        mLv.setAdapter(headAdapter);

        mLv.setOnItemClickListener(this);
        mSlh.setOnScrollListener(this);

    }


    private class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MyApplication.getContext());
            textView.setBackgroundColor(Color.GRAY);
            GoodsInfo data = mDatas.get(position);
            GoodsTypeInfo head = mHeads.get(data.headIndex);
            System.out.println("HEAD::NAme;" + head.name);
            textView.setText(head.name);
            return textView;
        }

        @Override
        public long getHeaderId(int position) {
            GoodsInfo data = mDatas.get(position);

            return data.headId;
        }

        /////////////////////华丽的分割线/////////////////////////

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MyApplication.getContext());
            textView.setTextColor(Color.GRAY);
            textView.setText(mDatas.get(position).name);
            return textView;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 头布局适配器
     */
    class MyHeaderAdapter extends BaseAdapter {
        private int mItemSelection;

        @Override
        public int getCount() {
            return mHeads.size();
        }

        @Override
        public Object getItem(int position) {
            return mHeads.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView = new TextView(MyApplication.getContext());
            textView.setBackgroundColor(Color.GRAY);
            textView.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            textView.setTextSize(18);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            GoodsTypeInfo head = mHeads.get(position);
            textView.setText(head.name);
            if (position == mItemSelection) {
                textView.setBackgroundColor(Color.WHITE);
            } else {
                textView.setBackgroundColor(Color.GRAY);
            }
            return textView;
        }

        public void setSelectPosition(int itemSelection) {
            if (this.mItemSelection == itemSelection) {
                return;
            }
            mItemSelection = itemSelection;
            //修改背景颜色和文字颜色
            notifyDataSetChanged();

        }
    }
}
