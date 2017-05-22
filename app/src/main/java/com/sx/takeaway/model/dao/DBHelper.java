package com.sx.takeaway.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.sx.takeaway.MyApplication;

/**
 * @Author sunxin
 * @Date 2017/5/21 12:13
 * @Description 数据库操作类
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASENAME = "takeaway.db";
    private static final int DATABASEVERSION = 1;

    /**
     * 单例模式
     * 双重校验机制
     */

    private static DBHelper instance;

    public static DBHelper getInstance() {
        if (instance == null){ //第一次校验：提高效率，不用排队
            synchronized (DBHelper.class){
                if (instance == null){ // 第二次校验：防止对象多次创建
                    instance =  new DBHelper(MyApplication.getContext());
                    instance.getWritableDatabase();
                }
            }
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}

