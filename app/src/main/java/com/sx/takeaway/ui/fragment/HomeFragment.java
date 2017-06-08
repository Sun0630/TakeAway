package com.sx.takeaway.ui.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sx.takeaway.R;
import com.sx.takeaway.dagger2.component.DaggerHomeFragmentComponent;
import com.sx.takeaway.dagger2.module.HomeFragmentModule;
import com.sx.takeaway.model.net.bean.HomeInfo;
import com.sx.takeaway.presenter.fragment.HomeFragmentPresenter;
import com.sx.takeaway.ui.adapter.HomeRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description
 */

/**
 * 工作内容：
 * 1、布局
 * 2、头容器的处理
 * a、需要侵入到状态栏中
 * b、状态栏为透明
 * c、随着RecyclerView的滑动，头的透明度会变动
 * 3、RecyclerView数据加载
 * a、简单数据加载
 * b、复杂数据加载
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;

    //使用Dagger2是业务层和View层分离
    @Inject
    HomeFragmentPresenter mPresenter;
    private HomeRecyclerViewAdapter mViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Dagger2
        DaggerHomeFragmentComponent.builder()
                .homeFragmentModule(new HomeFragmentModule(this))
                .build()
                .in(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        //为RecyclerView设置adapter
        mViewAdapter = new HomeRecyclerViewAdapter();
        mRvHome.setAdapter(mViewAdapter);
        mRvHome.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        //监听RecyclerView的滑动事件，改变头布局的透明度
        mRvHome.addOnScrollListener(listener);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    private int sumY = 0;
    //规定一个范围
    private float duration = 200;//在0-150之间改变头部的透明度

    private ArgbEvaluator mEvaluator = new ArgbEvaluator();//透明度颜色计算器
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            System.out.println("dy::" + dy);
            //记录y移动的距离
            sumY += dy;
            // 滚动的总距离相对0-150之间有一个百分比，头部的透明度也是从初始值变动到不透明，通过距离的百分比，得到透明度对应的值
            // 如果小于0那么透明度为初始值，如果大于150为不透明状态
            int bgColor = 0X553190E8;
            if (sumY < 0) {
                bgColor = 0X553190E8;
            } else if (sumY > 150) {
                bgColor = 0XFF3190E8;//变为不透明
            } else {
                bgColor = (int) mEvaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
            }
            //为头部设置背景
            mLlTitleContainer.setBackgroundColor(bgColor);
        }
    };

    /**
     * 请求失败
     *
     * @param msg
     */
    public void failed(String msg) {

    }

    /**
     * 请求成功
     *
     * @param homeInfo
     */
    public void success(HomeInfo homeInfo) {

    }

    /**
     * 提供Adapter
     *
     * @return
     */
    public HomeRecyclerViewAdapter getAdapter() {
        return mViewAdapter;
    }
}
