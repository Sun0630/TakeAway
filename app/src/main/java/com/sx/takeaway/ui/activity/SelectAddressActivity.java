package com.sx.takeaway.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author sunxin
 * @Date 2017/7/7 22:44
 * @Description 定位结果页面
 */

public class SelectAddressActivity extends AppCompatActivity implements AMapLocationListener,
        LocationSource, PoiSearch.OnPoiSearchListener {

    /*
    * 定位操作：
    *   1，MapView
    *   2，MapView管理工具的获取
    *   3，基本配置
    *   4，小蓝点的设置
    *   5，设置监听(定位，定位结果的通知)
    *   6，开始定位
    *   7，获取位置信息
    *   8，结束定位
    *
    * */

    private MapView mMapView;
    private AMap aMap;
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener;

    private RecyclerView rvPoiList;//展示兴趣点列表
    private MyPoiListAdapter adapter;
    private PoiSearch.Query mQuery;
    private ArrayList<PoiItem> mPoiItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        mMapView = (MapView) findViewById(R.id.map);
        rvPoiList = (RecyclerView) findViewById(R.id.rv_poi);
        rvPoiList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mMapView.onCreate(savedInstanceState);//必须重写

        //设置显示定位按钮

        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));//设置缩放级别


        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false


    }


    //位置变化
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                Log.e("AmapSuccess", "定位成功");
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

                // 停止定位
                mLocationClient.stopLocation();
                // 查询附近信息
                MyApplication.LOCATION = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                doSearchQuery(aMapLocation.getCity());

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }

    }

    /**
     * 开始进行poi搜索
     *
     * @param city
     */
    protected void doSearchQuery(String city) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery = new PoiSearch.Query("", "", city);
        mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(0);// 设置查第一页

        if (MyApplication.LOCATION != null) {
            PoiSearch poiSearch = new PoiSearch(this, mQuery);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(MyApplication.LOCATION, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        Log.e("TAG", "onPoiSearched: "+result+"----"+rcode);
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(mQuery)) {// 是否是同一条
                    mPoiItems = result.getPois();//获取兴趣点集合
                    Log.e("Tag", "onPoiSearched: "+mPoiItems.toString() );
                    //设置RecyclerView
                    adapter = new MyPoiListAdapter();
                    rvPoiList.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rcode) {

    }

    //开始定位
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    class MyPoiListAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SelectAddressActivity.this).inflate(R.layout.item_select_receipt_address, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PoiItem poiItem = mPoiItems.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setData(poiItem);
        }

        @Override
        public int getItemCount() {
            return mPoiItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv)
            ImageView mIv;
            @BindView(R.id.tv_title)
            TextView mTvTitle;
            @BindView(R.id.tv_snippet)
            TextView mTvSnippet;

            private PoiItem mData;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }

            public void setData(final PoiItem data) {
                mData = data;
                mTvTitle.setText(data.getTitle());
                mTvSnippet.setText(data.getSnippet());
                //为Item设置点击事件
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("title",data.getTitle());

                        LatLonPoint point = data.getLatLonPoint();//获取经纬度坐标点
                        intent.putExtra("latitude",point.getLatitude());
                        intent.putExtra("longitude", point.getLongitude());

                        //设置数据带回
                        SelectAddressActivity.this.setResult(200,intent);
                        SelectAddressActivity.this.finish();

                    }
                });
            }



        }


    }
}
