package com.sx.takeaway.presenter;

import com.sx.takeaway.model.dao.DBHelper;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.api.ResponseInfoApi;
import com.sx.takeaway.utils.Constant;
import com.sx.takeaway.utils.ErrorMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author sunxin
 * @Date 2017/5/21 22:18
 * @Description 业务层公共部分代码抽取
 */

public abstract class BasePresenter {
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


    public class CallbackAdapter implements Callback<ResponseInfo>{
        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            //请求成功
            if (response != null && response.isSuccessful()) {
                ResponseInfo info = response.body();
                if ("0".equals(info.code)) {
                    //解析数据
                    parseData(info.data);
                } else {
                    //出现异常
                    failed(ErrorMsg.INFO.get(info.code));
                }
            }else {
                //请求失败
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //请求失败
            System.out.println("请求失败了..." + t.getMessage());
        }
    }

    /**
     * 处理异常
     * @param s
     */
    protected abstract void failed(String s);

    /**
     * 解析数据
     * @param data
     */
    protected abstract void parseData(String data);

}
