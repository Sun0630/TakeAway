package com.sx.takeaway.model.net.bean;

import android.content.pm.ActivityInfo;

import java.util.ArrayList;

/**
 * @Author sunxin
 * @Date 2017/5/23 17:53
 * @Description
 */

public class Seller {
//      "seller": {
//        "activityList": [],
//        "deliveryFee": "",
//                "distance": "",
//                "ensure": "",
//                "id": 9,
//                "invoice": "",
//                "name": "传智播客第9家分店",
//                "pic": "",
//                "recentVisit": "",
//                "sale": "",
//                "score": "",
//                "sendPrice": 0,
//                "time": ""
//    }

    public long id;
    public String pic;
    public String name;

    public String score;
    public String sale;
    public String ensure;

    public String invoice;
    public int sendPrice;
    public String deliveryFee;

    public String recentVisit;
    public String distance;
    public String time;

    private ArrayList<ActivityInfo> activityList;



}
