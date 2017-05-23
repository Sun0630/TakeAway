package com.sx.takeaway.presenter.fragment;

import com.google.gson.Gson;
import com.sx.takeaway.model.net.bean.HomeInfo;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.fragment.HomeFragment;

import retrofit2.Call;

/**
 * @Author sunxin
 * @Date 2017/5/23 12:33
 * @Description 首页
 */

public class HomeFragmentPresenter extends BasePresenter {

    private HomeFragment mFragment;

    public HomeFragmentPresenter(HomeFragment fragment) {
        mFragment = fragment;
    }

    /**
     * 获取首页数据
     */
    public void getData() {
        Call<ResponseInfo> home = mResponseInfoApi.home();
        home.enqueue(new CallbackAdapter());
    }

    /**
     * 异常
     *
     * @param msg
     */
    @Override
    protected void failed(String msg) {
        mFragment.failed(msg);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    @Override
    protected void parseData(String data) {
        //解析数据
        Gson gson = new Gson();
        HomeInfo homeInfo = gson.fromJson(data, HomeInfo.class);

//        mFragment.success(homeInfo);//传递过去进行数据展示
        //直接调用RecyclerView的adapter进行数据的设置和展示
        mFragment.getAdapter().setData(homeInfo);
    }


}
