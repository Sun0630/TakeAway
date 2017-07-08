package com.sx.takeaway.ui.fragment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.dagger2.component.fragment.DaggerHomeFragmentComponent;
import com.sx.takeaway.dagger2.module.fragment.HomeFragmentModule;
import com.sx.takeaway.model.net.bean.HomeInfo;
import com.sx.takeaway.presenter.fragment.HomeFragmentPresenter;
import com.sx.takeaway.ui.activity.SelectAddressActivity;
import com.sx.takeaway.ui.adapter.HomeRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class HomeFragment extends BaseFragment implements AMapLocationListener {


    private static final String TAG = "HomeFragment";
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
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;

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
//        mLlTitleContainer.setBackgroundColor(0XFF3190E8);
//        getActivity().getWindow().getDecorView().
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

//        mPresenter.getData();
        //无地图定位
        location();
    }

    /**
     * 无地图定位
     */
    private void location() {
        mLocationClient = new AMapLocationClient(this.getContext());
        //初始化定位参数
        mLocationClientOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationClientOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationClientOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null
                && location.getErrorCode() == 0){
            MyApplication.LOCATION = new LatLonPoint(location.getLatitude(),location.getLongitude());

            String address = location.getAddress();
            Log.e(TAG, "onLocationChanged: "+address);
            mHomeTvAddress.setText(address);
            //停止定位
            mLocationClient.stopLocation();
        }
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

    @OnClick(R.id.home_tv_address)
    public void onClick() {
        Intent intent = new Intent(this.getContext(), SelectAddressActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在Fragment中使用该方法时，需要去该Fragment所在的Activity中调用一下此方法
        mHomeTvAddress.setText(data.getStringExtra("title"));
    }


}
