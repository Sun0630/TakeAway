package com.sx.takeaway.presenter;

import com.sx.takeaway.model.dao.DBHelper;
import com.sx.takeaway.presenter.api.ResponseInfoApi;
import com.sx.takeaway.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:18
 * @Description 业务层公共部分代码抽取
 */

public class BasePresenter {
    protected static ResponseInfoApi mResponseInfoApi;
    //数据库
    protected  DBHelper dbHelper;

    //初始化网络请求公共部分
    public BasePresenter() {

        if (mResponseInfoApi == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mResponseInfoApi = retrofit.create(ResponseInfoApi.class);
        }

        //数据库
        dbHelper = DBHelper.getInstance();

    }
}
