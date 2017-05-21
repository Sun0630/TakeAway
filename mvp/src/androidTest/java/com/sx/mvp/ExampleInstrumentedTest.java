package com.sx.mvp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.j256.ormlite.dao.Dao;
import com.sx.mvp.model.dao.DBHelper;
import com.sx.mvp.model.dao.bean.AddressBean;
import com.sx.mvp.model.dao.bean.UserBean;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sx.mvp", appContext.getPackageName());

    }

    /**
     * 测试创建数据库
     */
    @Test
    public void testCreateDB() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
    }

    /**
     * 测试创建用户
     */
    @Test
    public void testCreateUser() throws SQLException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        Dao<UserBean, Integer> dao = dbHelper.getDao(UserBean.class);
        UserBean userBean = new UserBean();
        userBean.set_id(1);
        dao.create(userBean);

        UserBean userBean1 = new UserBean();
        userBean1.set_id(2);
        dao.create(userBean1);
    }


    /**
     * 测试收货地址
     */
    @Test
    public void testAddress() throws SQLException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);

        Dao<AddressBean, Integer> dao = dbHelper.getDao(AddressBean.class);
        UserBean userBean = new UserBean();
        userBean.set_id(2);

        for (int i = 1; i < 10; i++) {
            AddressBean addressBean = new AddressBean();
            addressBean.setGoodsAddress("默认收货地址" + i);
            addressBean.setVillage("小区" + i);
            addressBean.set_id(i);
            addressBean.setUser(userBean);

            dao.create(addressBean);
        }

    }

    /**
     * 测试根据用户id 查询用户的所有收货地址
     */
    @Test
    public void testFindUserById() throws SQLException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        Dao<UserBean, Integer> dao = dbHelper.getDao(UserBean.class);
        UserBean userBean = dao.queryForId(2);
        System.out.println("userbean:"+userBean.toString());
    }


}
