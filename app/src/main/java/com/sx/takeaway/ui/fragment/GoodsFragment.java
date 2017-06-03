package com.sx.takeaway.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;

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

public class GoodsFragment extends BaseFragment {

    @BindView(R.id.slh)
    StickyListHeadersListView mSlh;

    private MyGroupAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        ButterKnife.bind(this, view);
        mAdapter = new MyGroupAdapter();
        testData();
        mSlh.setAdapter(mAdapter);
        return view;
    }

    /**
     * 添加测试普通数据
     */
    private ArrayList<Data> mDatas = new ArrayList<>();

    class Data {
        String info;
        int headId;//进行分组操作，同组数据该字段相同
        int headIndex;//当前条目所在头所在容器的下标
    }

    /**
     * 头部数据
     */
    private ArrayList<Head> mHeads = new ArrayList<>();

    class Head {
        String info;
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
            //为每个头添加数据
            //普通条目
            for (int di = 0; di < 10; di++) {
                Data data = new Data();
                data.info = "数据:第" + hi + "组：" + di;
                data.headId = hi;//可以使任意的
                data.headIndex = hi;
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
}
