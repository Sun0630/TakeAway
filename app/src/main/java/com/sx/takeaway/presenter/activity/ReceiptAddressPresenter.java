package com.sx.takeaway.presenter.activity;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.sx.takeaway.MyApplication;
import com.sx.takeaway.model.dao.bean.AddressBean;
import com.sx.takeaway.model.dao.bean.UserBean;
import com.sx.takeaway.model.net.bean.ResponseInfo;
import com.sx.takeaway.presenter.BasePresenter;
import com.sx.takeaway.ui.IView;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;

/**
 * @Author sunxin
 * @Date 2017/6/23 10:51
 * @Description 地址管理业务类
 */

public class ReceiptAddressPresenter extends BasePresenter {

    IView view;
    static Dao<AddressBean, Integer> dao;

    public ReceiptAddressPresenter(IView view) {
        this.view = view;
        try {
            dao = dbHelper.getDao(AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        * 功能描述：
        *   1，为RecyclerView添加数据
        *       获取地址数据：
        *           从网络，获取到之后存储到本地数据库
        *           从本地
        *   2，新增地址入口
        *
        * */
    public void getData() {
        Call<ResponseInfo> address = mResponseInfoApi.address(MyApplication.USERID);
        address.enqueue(new CallbackAdapter());
    }


    @Override
    protected void failed(String s) {

    }

    @Override
    protected void parseData(String data) {
        //解析数据
        if (TextUtils.isEmpty(data)) {
            //从本地获取数据
            finbAllAddressByUserId(MyApplication.USERID);
        } else {
            //从网络获取数据,插入数据库
            Gson gson = new Gson();
            List<AddressBean> addressBean = gson.fromJson(data, new TypeToken<List<AddressBean>>() {
            }.getType());
            //循环插入数据库
            for (AddressBean bean :
                    addressBean) {
                create(bean);
            }
            //读取本地数据
            finbAllAddressByUserId(MyApplication.USERID);
        }
    }

    /**
     * 插入数据库
     * 将服务器的数据插入到本地数据库
     *
     * @param addressBean
     */
    public int create(AddressBean addressBean) {
        //指定地址是哪个用户的,建立关联关系
        UserBean user = new UserBean();
        user._id = MyApplication.USERID;
        addressBean.user = user;

        try {
            return dao.create(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 用户添加地址数据
     */
    public void create(String name, String sex, String phone, String receiptAddress, String detailAddress, String label) {
        //添加一条数据到数据库
        AddressBean addressBean = new AddressBean(name, sex, phone, receiptAddress, detailAddress, label);
        int i = create(addressBean);
        if (i == 1) {
            //写入本地数据库成功
            view.success(i);//添加地址界面

        } else {
            view.failed("添加数据失败");
        }
        //上传到服务器

    }


    /**
     * 根据用户id查询获取所有的收货地址
     *
     * @param userId
     */
    public void finbAllAddressByUserId(int userId) {
        try {
            List<AddressBean> addressBeen = dao.queryForEq("user_id", userId);
            view.success(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户ID查询出用户收货地址信息
     *
     * @param id
     */
    public void finbDataById(int id) {
        try {
            AddressBean addressBean = dao.queryForId(id);
            view.success(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改用户地址信息
     *
     * @param id
     * @param name
     * @param sex
     * @param phone
     * @param address
     * @param address1
     * @param label
     */
    public void update(int id, String name, String sex, String phone, String address, String address1, String label) {
        AddressBean bean = new AddressBean(name, sex, phone, address, address1, label);
        UserBean userBean = new UserBean();
        userBean._id = MyApplication.USERID;
        bean.user = userBean;
        bean._id = id;
        try {
            int update = dao.update(bean);
            if (update == 1)
                view.success(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依据id删除收货地址
     *
     * @param id
     */
    public void delete(int id) {
        try {
            int i = dao.deleteById(id);
            if (i == 1) {
                view.success(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
