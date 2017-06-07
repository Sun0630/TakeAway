package com.sx.takeaway.model.net.bean;

/**
 * @Author sunxin
 * @Date 2017/6/7 11:39
 * @Description 普通条目商品信息
 */

public class GoodsInfo {
    public boolean bargainPrice;//	true
    public String form;    //肉末烧汁茄子+千叶豆腐+小食+时蔬+含粗粮米饭)
    public String icon;    //http://172.16.32.3:8080/TakeoutService/imgs/goods/caiping_taocan.webp  图片
    public int id;    //1001
    public int monthSaleNum;    //53
    public String name;    //肉末烧汁茄子+千叶豆腐套餐(含粗粮米饭)
    public boolean isNew;    //false 是否是新产品
    public float newPrice;    //13.899999618530273
    public int oldPrice;    //30

    //进行联动的参数
    public int headId;//进行分组操作，同组数据该字段相同
    public int headIndex;//当前条目所在头所在容器的下标
    public int count;//商品数量
}
