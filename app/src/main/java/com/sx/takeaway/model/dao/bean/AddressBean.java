package com.sx.takeaway.model.dao.bean;

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
    public int _id;
    //不能为空
    @DatabaseField(canBeNull = false)
    public String name;
    @DatabaseField(canBeNull = false)
    public String sex;
    @DatabaseField(canBeNull = false)
    public String phone;
    @DatabaseField(canBeNull = false)
    public String receiptAddress;
    @DatabaseField(canBeNull = false)
    public String detailAddress;
    @DatabaseField(canBeNull = false)
    public String label;
    @DatabaseField(canBeNull = false)
    public long timeStamp;
    @DatabaseField(canBeNull = false)
    public double longitude;
    @DatabaseField(canBeNull = false)
    public double latitude;


    //指定外键
    @DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "_id", columnName = "user_id")
    private UserBean user;

}
