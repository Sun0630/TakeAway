package com.sx.takeaway.presenter.api;


import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author sunxin
 * @Date 2017/5/19 22:50
 * @Description Retrofit对请求参数的请求方式的拼装
 */

public interface ResponseInfoApi {

    //登录
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(
            @Query("username")
                    String username,
            @Query("password")
                    String password
    );

    //获取首页数据
    @GET(Constant.HOME)
    Call<ResponseInfo> home();


    /**
     * 获取商品数据
     * @param sellerId 商家ID
     * @return
     */
    @GET(Constant.GOODS)
    Call<ResponseInfo> goods(
            @Query("sellerId")
                    long sellerId
    );
}
