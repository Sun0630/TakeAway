package com.sx.mvp.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sx.mvp.model.dao.bean.AddressBean;
import com.sx.mvp.model.dao.bean.UserBean;

import java.sql.SQLException;

/**
 * @Author sunxin
 * @Date 2017/5/21 12:13
 * @Description 数据库操作类
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASENAME = "takeaway.db";
    private static final int DATABASEVERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
