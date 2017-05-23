package com.sx.takeaway.model.net.bean;

import java.util.List;

/**
 * @Author sunxin
 * @Date 2017/5/23 17:51
 * @Description
 */

public class HomeItem {
//    "recommendInfos": [],
//            "seller": {
//        "activityList": [],
//        "deliveryFee": "",
//                "distance": "",
//                "ensure": "",
//                "id": 1,
//                "invoice": "",
//                "name": "黑马程序员外卖项目",
//                "pic": "http://172.16.0.116:8080/TakeoutService/imgs/category/1.png",
//                "recentVisit": "",
//                "sale": "",
//                "score": "5",
//                "sendPrice": 0,
//                "time": ""
//    },
//            "type": 0

    public int type;//类型
    public Seller seller;
    public List<String> recommendInfos;//大家都在找

}
