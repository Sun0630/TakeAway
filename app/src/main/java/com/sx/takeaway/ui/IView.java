package com.sx.takeaway.ui;

/**
 * @Author sunxin
 * @Date 2017/6/26 19:00
 * @Description 所有界面都需要实现该接口
 */

public interface IView {
    void success(Object o);
    void failed(String msg);
}
