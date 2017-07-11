package com.sx.takeaway.presenter.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.model.net.bean.Cart;
import com.sx.takeaway.model.net.bean.GoodsInfo;
import com.sx.takeaway.model.net.bean.Order;
import com.sx.takeaway.model.net.bean.OrderOverview;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.IView;
import com.sx.takeaway.ui.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @Author sunxin
 * @Date 2017/6/26 11:03
 * @Description 订单业务类
 */

public class OrderPresenter extends BasePresenter {

    /*
    * 工作：
    *   1，生成订单 -- 结算中心
    *   2，查询订单信息 -- 订单列表
    *   3，订单详情查询 -- 详情展示
    *
    * */

    private IView mView;

    public OrderPresenter(IView view) {
        mView = view;
    }

    @Override
    protected void failed(String s) {

    }

    int operator = 0;//操作的表示

    @Override
    protected void parseData(String data) {
        Log.e("TAG", "parseData: " + data);
        switch (operator) {
            case 1:
                mView.success(data);
                break;
            case 2:
                //解析数据
                List<Order> orders = new Gson().fromJson(data, new TypeToken<List<Order>>() {
                }.getType());
                mView.success(orders);
                break;
        }
    }

    /**
     * 生成订单
     *
     * @param userid    用户id
     * @param addressId 地址信息id
     * @param type      支付类型  1，在线支付
     */
    public void create(int userid, int addressId, int type) {
        //生成订单设置标识为1
        operator = 1;
        Log.i("Tag", "create: " + userid);
        //向服务器发送订单json数据
        OrderOverview overview = new OrderOverview();
        overview.addressId = addressId;
        overview.userId = userid;
        overview.type = type;
        overview.sellerid = ShoppingCartManager.getInstance().sellerId;

        overview.cart = new ArrayList<>();

        for (GoodsInfo info :
                ShoppingCartManager.getInstance().mGoodsInfos) {
            Cart cart = new Cart();
            cart.count = info.count;
            cart.id = info.id;
            overview.cart.add(cart);
        }
        //将一个对象处理成json串，使用Gson
        Gson gson = new Gson();
        String json = gson.toJson(overview);

        Call<ResponseInfo> order = mResponseInfoApi.createOrder(json);
        order.enqueue(new CallbackAdapter());
    }

    /**
     * 获取订单数据
     */
    public void getData() {
        //获取订单数据设置标识为2
        operator = 2;
        Call<ResponseInfo> orderList = mResponseInfoApi.orderList(MyApplication.USERID);
        orderList.enqueue(new CallbackAdapter());
    }
}
