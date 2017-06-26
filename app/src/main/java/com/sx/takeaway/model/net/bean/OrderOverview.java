package com.sx.takeaway.model.net.bean;

import java.util.List;

/**
 * 提交到服务器的数据
 */
public class OrderOverview {
    /*
	{
        "addressId":1 ,
        "userId":1 ,
        "cart": [
        	{"id":1,"num":1},
        	{"id":2,"num":1},
        	{"id":3,"num":2}
        ],
        "sellerId":1 ,
        "type": 10
    }
    */
	
	public int addressId;
	public int userId;
	public List<Cart> cart;
	public long sellerid;
	public int type;
	
}