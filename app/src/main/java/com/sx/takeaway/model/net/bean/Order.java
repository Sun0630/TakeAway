package com.sx.takeaway.model.net.bean;

import java.util.List;

/**
 * 订单数据封装
 */
public class Order {
    public String id;
    public String type;
    public Rider rider;
    public Seller seller;
    public List<GoodsInfo> goodsInfos;
    public Distribution distribution;
    public OrderDetail detail;
}
