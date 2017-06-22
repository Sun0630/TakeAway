package com.sx.takeaway.presenter.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.sx.takeaway.model.dao.bean.UserBean;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.activity.LoginActivity;
import com.sx.takeaway.utils.Constant;

import java.sql.SQLException;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * @Author sunxin
 * @Date 2017/6/22 13:39
 * @Description 用户管理业务逻辑处理
 */

public class LoginActivityPresenter extends BasePresenter {

    LoginActivity mActivity;

    public LoginActivityPresenter(LoginActivity activity) {
        mActivity = activity;
    }

    @Override
    protected void failed(String s) {
        mActivity.failed(s);
    }

    @Override
    protected void parseData(String data) {
        Log.i(TAG, "parseData: " + data);
        //将用户数据存入数据库
        //解析数据
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);

        try {
            Dao<UserBean, ?> dao = dbHelper.getDao(UserBean.class);
            userBean.login = true;//标记为已登录状态
            dao.create(userBean);//插入到数据库

            //在添加新的已经登录的用户时，需要检查本地数据库中的用户数据，要确保他们都是未登录的状态。
            //使用事务

        } catch (SQLException e) {
            e.printStackTrace();
        }

        mActivity.success();
    }

    /**
     * 获取用户数据
     *
     * @param phone
     */
    public void getData(String phone) {
        //电话  phone   登录类型  type
        Call<ResponseInfo> login = mResponseInfoApi.login(phone, Constant.LOGIN_TYPE_SMS);
        login.enqueue(new CallbackAdapter());
    }
}
