package com.sx.takeaway.presenter.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.model.dao.bean.UserBean;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.activity.LoginActivity;
import com.sx.takeaway.utils.Constant;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

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
        AndroidDatabaseConnection connection = null;
        Savepoint savepoint = null;
        try {
            Dao<UserBean, ?> dao = dbHelper.getDao(UserBean.class);


            //在添加新的已经登录的用户时，需要检查本地数据库中的用户数据，要确保他们都是未登录的状态。
            //使用事务
            /*
            * 工作内容：
            *   1，查询所有用户数据，把登录状态改为false
            *   2，添加新用户，将登录状态改为true
            *
            * 事务操作的流程：
            *   1，开启事务
            *   2，进行一系列的数据库操作
            *   3，提交
            *   4，出错回滚
            *
            * 事务操作的问题：
            *   1，回滚到哪里（创建还原点）
            *   2，一系列数据库操作不能立即生效
            * */

            //开启事务，设置还原点
            connection = new AndroidDatabaseConnection(dbHelper.getReadableDatabase(),true);

            //设置还原点，开启事务
            savepoint = connection.setSavePoint("start");

            dao.setAutoCommit(connection,false);//false：是否立即提交

            //查询所有用户并遍历把登录状态设置为false
            List<UserBean> userBeen = dao.queryForAll();
            if (userBeen != null && userBeen.size()>0){
                //遍历
                for (UserBean item :
                        userBeen) {
                    item.login = false;
                    dao.update(item);
                }
            }
            //新用户标记为登录状态
            userBean.login = true;//标记为已登录状态
            dao.create(userBean);//插入到数据库

            MyApplication.phone = userBean.phone;
            MyApplication.USERID = userBean._id;

            //提交
            connection.commit(savepoint);

            mActivity.success();
        } catch (SQLException e) {
            e.printStackTrace();
            //回滚
            if (connection != null) {
                try {
                    connection.rollback(savepoint);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            failed("修改数据库异常");
        }


    }

    /**
     *
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
