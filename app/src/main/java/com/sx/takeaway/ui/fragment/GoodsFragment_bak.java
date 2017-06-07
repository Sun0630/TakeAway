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
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description 商品详情Fragment
 */

public class GoodsFragment_bak extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.slh)
    StickyListHeadersListView mSlh;
    @BindView(R.id.lv)
    ListView mLv;

    private MyGroupAdapter mAdapter;
    private MyHeaderAdapter headAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        ButterKnife.bind(this, view);
        testData();
        mAdapter = new MyGroupAdapter();
        mSlh.setAdapter(mAdapter);

        headAdapter = new MyHeaderAdapter();
        mLv.setAdapter(headAdapter);

        mLv.setOnItemClickListener(this);

        mSlh.setOnScrollListener(this);
        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 左侧头容器的点击事件
        headAdapter.setSelectPosition(position);
        //让分组置顶
        Head head = mHeads.get(position);
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
            Data data = mDatas.get(firstVisibleItem);
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
     * 解析数据成功之后更新界面
     * @param goodsTypeInfos
     */
    public void success(GoodsTypeInfo goodsTypeInfos) {

    }
    /**
     * 添加测试普通数据
     */
    private ArrayList<Data> mDatas = new ArrayList<>();
    /**
     * 普通条目
     */
    class Data {
        String info;
        int headId;//进行分组操作，同组数据该字段相同
        int headIndex;//当前条目所在头所在容器的下标
    }

    /**
     * 头部数据
     */
    private ArrayList<Head> mHeads = new ArrayList<>();

    /**
     * 头部
     */
    class Head {
        String info;
        int groupFirstIndex;//分组的第一个下标
    }

    /**
     * 测试数据
     */
    public void testData() {

        //头部，10个一个分组
        for (int hi = 0; hi < 10; hi++) {
            Head head = new Head();
            head.info = "头部" + hi;
            mHeads.add(head);
        }


        for (int hi = 0; hi < mHeads.size(); hi++) {
            Head head = mHeads.get(hi);
            //为每个头添加数据
            //普通条目
            for (int di = 0; di < 10; di++) {
                Data data = new Data();
                data.info = "数据:第" + hi + "组：" + di;
                data.headId = hi;//可以使任意的
                data.headIndex = hi;
                if (di == 0)//获取分组第一条数据的下标
                    head.groupFirstIndex = mDatas.size();
                mDatas.add(data);
            }
        }

        //普通条目
//        for (int di = 0; di < 100; di++) {
//            Data data = new Data();
//            data.info = "数据:" + di;
//            mDatas.add(data);
//        }

    }

    private class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MyApplication.getContext());
            textView.setBackgroundColor(Color.GRAY);
            Data data = mDatas.get(position);
            Head head = mHeads.get(data.headIndex);
            textView.setText(head.info);
            return textView;
        }

        @Override
        public long getHeaderId(int position) {
            Data data = mDatas.get(position);

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
            textView.setText(mDatas.get(position).info);
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
            Head head = mHeads.get(position);
            textView.setText(head.info);
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
