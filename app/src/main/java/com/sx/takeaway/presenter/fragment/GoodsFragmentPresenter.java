package com.sx.takeaway.presenter.fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @Author sunxin
 * @Date 2017/6/7 11:09
 * @Description 商家列表Presenter
 */

public class GoodsFragmentPresenter extends BasePresenter {

    private GoodsFragment mFragment;

    public GoodsFragmentPresenter(GoodsFragment fragment) {
        mFragment = fragment;
    }

    public void getData(long sellerId){
        Call<ResponseInfo> goods = mResponseInfoApi.goods(sellerId);
        goods.enqueue(new CallbackAdapter());
    }

    @Override
    protected void failed(String s) {

    }

    @Override
    protected void parseData(String data) {
        Gson gson = new Gson();
        //TypeToken实现对泛型的支持
        ArrayList<GoodsTypeInfo> goodsTypeInfos = gson.fromJson(data, new TypeToken<List<GoodsTypeInfo>>() {
        }.getType());
        //更新界面
        mFragment.success(goodsTypeInfos);

    }
}
