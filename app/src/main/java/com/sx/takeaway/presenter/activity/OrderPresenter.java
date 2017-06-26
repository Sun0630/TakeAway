package com.sx.takeaway.presenter.activity;

import android.util.Log;

import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.IView;

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

    @Override
    protected void parseData(String data) {

    }

    /**
     * 生成订单
     *
     * @param userid 用户id
     * @param addressId 地址信息id
     * @param type 支付类型  1，在线支付
     */
    public void create(int userid, int addressId, int type) {
        Log.i("Tag", "create: " + userid);
    }
}
