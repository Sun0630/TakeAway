package com.sx.takeaway.model.dao.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Author sunxin
 * @Date 2017/5/21 12:13
 * @Description 用户实体
 */
@DatabaseTable(tableName = "t_user")
public class UserBean {
    @DatabaseField(columnName = "_id", id = true)
    public int _id;

    @DatabaseField()
    public String name;
    @DatabaseField()
    public float balance;
    @DatabaseField()
    public int discount;
    @DatabaseField()
    public int integral;
    @DatabaseField()
    public String phone;
    @DatabaseField
    public boolean login;


    //TODO 主外键关系的处理
    //需要有一个集合去装当前用户的所有地址列表信息
    @ForeignCollectionField(eager = true)
    public ForeignCollection<AddressBean> addressList;


}
