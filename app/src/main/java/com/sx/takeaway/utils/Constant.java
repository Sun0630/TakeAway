package com.sx.takeaway.utils;

/**
 * @Author sunxin
 * @Date 2017/5/19 22:49
 * @Description 常量类
 */

public interface Constant {
    //    http://localhost:8080/   TakeoutService    /login?username="itheima"&password="bj"
    //    http://169.254.253.32:8080/takeoutService/home
    String BASE_URL = "http://10.0.2.2:8080/";
    //    String BASE_URL = "http://172.16.32.3:8080/";
    String LOGIN = "TakeoutService/login";//登录
    String HOME = "TakeoutService/home";//获取首页数据
    //    http://localhost:8080/TakeoutService/goods?sellerId=1
    String GOODS = "TakeoutService/goods";//获取商品
    //    http://localhost:8080/TakeoutService/address?userid=3419
    String ADDRESS = "TakeoutService/address";

    String ORDER = "TakeoutService/order";//提交订单信息

    String PAY = "TakeoutService/pay";//获取支付信息

    //短信登录方式
    int LOGIN_TYPE_SMS = 2;
}
