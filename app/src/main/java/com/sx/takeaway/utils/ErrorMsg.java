package com.sx.takeaway.utils;

import java.util.HashMap;

/**
 * @Author sunxin
 * @Date 2017/5/23 17:35
 * @Description 错误信息类
 */

public class ErrorMsg {
    public static HashMap<String, String> INFO = new HashMap<>();

    static {
        INFO.put("0", "成功");
        INFO.put("1", "用户名或密码错误");
    }
}
