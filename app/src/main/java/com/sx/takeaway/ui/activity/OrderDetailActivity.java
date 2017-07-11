package com.sx.takeaway.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.sx.takeaway.R;
import com.sx.takeaway.ui.observer.OrderObserver;
import com.sx.takeaway.utils.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author sunxin
 * @Date 2017/7/9 8:26
 * @Description 订单详情页
 */

public class OrderDetailActivity extends AppCompatActivity implements Observer {
    private static final String TAG = "OrderDetail";
    @BindView(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @BindView(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @BindView(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;
    /*
    * 修改页面的思路：
    *   1，确定展示的每个Type对应的Index信息
    *   2，根据Index信息去容器中找到对应的图片，文字控件
    *   3，修改对应的图片文字控件，恢复其他的
    * */

    String type = "";
    String orderId = "";
    private AMap aMap;
    private LatLng riderPos;
    private Marker markerRider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        posList = new ArrayList<>();
        mMap.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        orderId = extras.getString("orderId");
        Log.d("TAG", "onCreate: " + type + "--" + orderId);
        aMap = mMap.getMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加到观察者中
        OrderObserver.getObserver().addObserver(this);
        changeUI(type);
    }

    public void changeUI(String type) {
        int index = getIndex(type);
        //还原最初的状态
        for (int i = 0; i < mLlOrderDetailTypePointContainer.getChildCount(); i++) {
            TextView typeInfo = (TextView) mLlOrderDetailTypeContainer.getChildAt(i);
            ImageView typePoint = (ImageView) mLlOrderDetailTypePointContainer.getChildAt(i);

            //修改颜色
            typeInfo.setTextColor(getResources().getColor(R.color.grey));
            typePoint.setImageResource(R.drawable.order_time_node_normal);
        }

        TextView typeInfo = (TextView) mLlOrderDetailTypeContainer.getChildAt(index);
        ImageView typePoint = (ImageView) mLlOrderDetailTypePointContainer.getChildAt(index);

        //修改颜色
        typeInfo.setTextColor(getResources().getColor(R.color.colorAccent));
        typePoint.setImageResource(R.drawable.order_time_node_disabled);

    }

    /**
     * 根据type值获取索引index
     *
     * @param type
     * @return
     */
    public int getIndex(String type) {
        int index = -1;

        switch (type) {
            case OrderObserver.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                index = 0;
                break;
            case OrderObserver.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case OrderObserver.ORDERTYPE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case OrderObserver.ORDERTYPE_DISTRIBUTION:
//                typeInfo = "配送中";
                index = 2;
                break;
            case OrderObserver.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case OrderObserver.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }

        return index;
    }

    @Override
    public void update(Observable o, Object data) {
        type = ((HashMap<String, String>) data).get("type");
        orderId = ((HashMap<String, String>) data).get("orderId");

        if (orderId.equals(this.orderId)) {
            //需要更新界面
            changeUI(type);
            //如果状态是配送，需要在地图上做配送展示工作
            if (type.equals(OrderObserver.ORDERTYPE_DISTRIBUTION)) {
                initMap();
            }
            if (type.equals(OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE)){
                initRider((HashMap<String, String>) data);
            }
//            switch (type){
//                case OrderObserver.ORDERTYPE_DISTRIBUTION:
//                    //开始配送，展示买家卖家的在地图上的位置
//                    initMap();
//                    break;
//                case OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
//                    //骑手接单，展示骑手的位置，中心点设置为骑手，缩放级别设置为17
//                    initRider((HashMap<String, String>) data);
//                    break;
//                case OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_SEND_MEAL:
//                case OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
//                    changeRider((HashMap<String, String>) data);
//                    break;
//            }
        }

    }

    /**
     * 更新骑手的位置
     * @param data
     */
    private void changeRider(HashMap<String, String> data) {
        //修改骑手的位置，
        // 更改地图的中心，
        // 修改提示信息展示内容
        //绘制骑手的行进轨迹
        String lat = data.get(Constant.LAT);
        String lng = data.get(Constant.LNG);

        Log.d(TAG, "initRider:changeRider "+lat+"---"+lng);

        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)){
            return;
        }

        //构造一个点
        LatLng currPos = new LatLng(Double.valueOf(lat),Double.valueOf(lng));
        posList.add(currPos);
        markerRider.setPosition(currPos);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(currPos));


        String info = "";

        DecimalFormat format = new DecimalFormat(".00");//数字格式化到小数点后两位


        switch (type){
            case OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
                //取餐
                float ds = AMapUtils.calculateLineDistance(currPos, latLngSeller);
                info = "距离商家还有"+format.format(ds)+"米";
                break;
            case OrderObserver.ORDERTYPE_DISTRIBUTION_RIDER_SEND_MEAL:
                //送餐
                float db = AMapUtils.calculateLineDistance(currPos, latLngBuyer);
                info = "距离买家还有"+format.format(db)+"米";
                break;
        }

        markerRider.setSnippet(info);
        markerRider.showInfoWindow();

        //绘制轨迹
        drawLine(currPos,posList.get(posList.size()-2));

    }

    /**
     * 绘制骑手行进轨迹
     * @param currPos
     * @param pos
     */
    private void drawLine(LatLng currPos, LatLng pos) {
        aMap.addPolyline(new PolylineOptions().add(pos,currPos).color(Color.GREEN).width(2));
    }

    List<LatLng> posList;

    /**
     * 展示骑手
     * @param data
     */
    private void initRider(HashMap<String, String> data) {
        String lat = data.get(Constant.LAT);
        String lng = data.get(Constant.LNG);

        Log.d(TAG, "initRider: "+lat+"---"+lng);

        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)){
            return;
        }

        //构造一个点
        riderPos = new LatLng(Double.valueOf(lat),Double.valueOf(lng));
        posList.add(riderPos);//存放到一个点的集合中，用来绘制骑手行进路线

        //设置中心点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(riderPos));
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        //添加覆盖物
        markerRider = aMap.addMarker(
                new MarkerOptions()
                        .anchor(0.5f,1)
                        .position(riderPos)
                        .snippet("骑手已接单")
        );
        markerRider.showInfoWindow();
        ImageView markerRiderIcon = new ImageView(this);
        markerRiderIcon.setImageResource(R.drawable.order_rider_icon);
        markerRider.setIcon(BitmapDescriptorFactory.fromView(markerRiderIcon));


    }

    LatLng latLngBuyer;//买家的经纬度
    LatLng latLngSeller;//卖家

    /**
     * 初始化地图，在地图上展示卖家和买家的位置
     */
    private void initMap() {
        mMap.setVisibility(View.VISIBLE);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        //添加买家marker
        latLngBuyer = new LatLng(40.100519, 116.365828);
        //把买家位置设置成中心点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngBuyer));

        Marker markerLatLngBuyer = aMap.addMarker(
                new MarkerOptions()
                        .anchor(0.5f, 1)//锚点
                        .position(latLngBuyer)
        );
        ImageView markerBuyerIcon = new ImageView(this);
        markerBuyerIcon.setImageResource(R.drawable.order_buyer_icon);
        markerLatLngBuyer.setIcon(BitmapDescriptorFactory.fromView(markerBuyerIcon));

        //添加卖家marker
        latLngSeller = new LatLng(40.060244, 116.343513);
        Marker markerLatLngSeller = aMap.addMarker(
                new MarkerOptions()
                        .anchor(0.5f, 1)//锚点
                        .position(latLngSeller)
        );
        ImageView markerSellerIcon = new ImageView(this);
        markerSellerIcon.setImageResource(R.drawable.order_seller_icon);
        markerLatLngSeller.setIcon(BitmapDescriptorFactory.fromView(markerSellerIcon));

    }
}
