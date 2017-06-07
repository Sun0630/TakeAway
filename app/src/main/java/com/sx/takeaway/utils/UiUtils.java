package com.sx.takeaway.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunxin.
 */

public class UiUtils {

    //记录状态栏的高度
    public static int STATUE_BAR_HEIGHT = 0;

    /**
     * 依据Id查询指定控件的父控件
     *
     * @param v  指定控件
     * @param id 父容器标识
     * @return
     */
    public static ViewGroup getContainder(View v, int id) {
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent.getId() == id) {
            return parent;
        }
        return getContainder(parent, id);
    }
}
