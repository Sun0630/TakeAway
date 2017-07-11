package com.sx.takeaway.ui.observer;

import java.util.HashMap;
import java.util.Observable;

/**
 * @Author sunxin
 * @Date 2017/7/9 9:07
 * @Description 抽象主题角色的子类
 */
//被观察者
public class OrderObserver extends Observable {
    /* 订单状态
     * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单*/
    public static final String ORDERTYPE_UNPAYMENT = "10";
    public static final String ORDERTYPE_SUBMIT = "20";
    public static final String ORDERTYPE_RECEIVEORDER = "30";
    public static final String ORDERTYPE_DISTRIBUTION = "40";
    //骑手状态：接单，取餐，送餐
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE = "43";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL = "46";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_SEND_MEAL = "48";


    public static final String ORDERTYPE_SERVED = "50";
    public static final String ORDERTYPE_CANCELLEDORDER = "60";

    private OrderObserver() {
    }

    private static OrderObserver observer = new OrderObserver();

    public static OrderObserver getObserver() {
        return observer;
    }

    /**
     * 获取服务器推送数据，并将数据发送给各个观察者
     * @param data
     */
    public void changeOrderInfo(HashMap<String, String> data) {
        //极光推送
        setChanged();//改变是否更新的标识为true
        notifyObservers(data);//循环容器中的所有观察者，并将数据data通过update方法传递给各个观察者
    }

}
