package com.sx.mvp.model.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Author sunxin
 * @Date 2017/5/21 12:14
 * @Description 收货地址实体
 */
@DatabaseTable(tableName = "t_address")
public class AddressBean {
    //主键
    @DatabaseField(id = true)
    private int _id;
    //不能为空
    @DatabaseField(canBeNull = false)
    private String goodsAddress;
    @DatabaseField(canBeNull = false)
    private String village;

    //指定外键
    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "_id", columnName = "user_id")
    private UserBean user;


    public AddressBean() {
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setGoodsAddress(String goodsAddress) {
        this.goodsAddress = goodsAddress;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
