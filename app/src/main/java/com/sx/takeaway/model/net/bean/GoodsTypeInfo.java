package com.sx.takeaway.model.net.bean;

import java.util.List;

/**
 * @Author sunxin
 * @Date 2017/6/7 11:37
 * @Description 商品类型信息
 */

public class GoodsTypeInfo {
    public int id;//商品id
    public String name;//商品名字
    public String info;//特价信息
    public List<GoodsInfo> list;//商品列表

    //进行联动的参数
    public int groupFirstIndex;//分组的第一个下标
}
