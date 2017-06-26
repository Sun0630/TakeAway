package com.sx.takeaway.presenter.activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sx.takeaway.model.net.bean.PaymentInfo;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.IView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @Author sunxin
 * @Date 2017/6/26 23:57
 * @Description 支付的业务类
 */

public class PaymentPresenter extends BasePresenter {

    private IView mView;

    public PaymentPresenter(IView view) {
        mView = view;
    }

    /**
     * 获取数据
     *
     * @param orderId
     */
    public void getData(String orderId) {
        Call<ResponseInfo> payment = mResponseInfoApi.payment(orderId);
        payment.enqueue(new CallbackAdapter());
    }

    @Override
    protected void failed(String s) {

    }

    @Override
    protected void parseData(String data) {
       /*
       {
            "code": "0",
            "data": {
                    "payDownTime": 15,
                    "money": 15,
                    "paymentInfo":
                     [
                        {
                                "id": 1,
                                "name": "支付宝",
                                "url": "http://10.0.2.2:8080/TakeoutService/imgs/payment/zfb.png"
                        },
                        {
                                "id": 2,
                                "name": "微信支付",
                                "url": "http://10.0.2.2:8080/TakeoutService/imgs/payment/wx.png"
                        }
                     ]
                 }
        }
        */

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            int time = jsonObject.getInt("payDownTime");
            double money = jsonObject.getDouble("money");
            String paymentInfo = jsonObject.getString("paymentInfo");

            Gson gson = new Gson();
            //把支付方式封装到bean
            List<PaymentInfo> paymentInfos = gson.fromJson(paymentInfo, new TypeToken<List<PaymentInfo>>() {
            }.getType());

            //使用Map将散数据整合传输
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("payDownTime", time);
            dataMap.put("money", money);
            dataMap.put("paymentInfos", paymentInfos);

            mView.success(dataMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
