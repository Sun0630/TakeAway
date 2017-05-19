package com.sx.mvp;

import android.os.SystemClock;

import com.sx.mvp.model.User;

/**
 * @Author sunxin
 * @Date 2017/5/19 18:02
 * @Description
 */

public class UserLoginNet {

    public boolean sendUserLoginInfo(User user){
        //休眠两秒钟
        SystemClock.sleep(2000);

        if ("sunxin".equals(user.username)&&"123".equals(user.password)){
            return true;
        }else {
            return false;
        }
    }
}
