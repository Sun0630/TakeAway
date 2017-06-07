package com.sx.takeaway.ui;

import com.sx.takeaway.model.net.bean.GoodsInfo;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author sunxin
 * @Date 2017/6/7 15:13
 * @Description 单例模式购物车管理类
 */

public class ShoppingCartManager {
    //单例
    private static ShoppingCartManager instance;

    private ShoppingCartManager() {
    }

    public static ShoppingCartManager getInstance() {
        if (instance == null) {
            instance = new ShoppingCartManager();
        }
        return instance;
    }

    /**
     * 购物车记录如下数据：
     * 1，商品列表(所在分类的表示信息)
     * 增加和减少操作会频繁进行增删操作查操作  CopyOnWriteArrayList()
     * 2，商家信息：标识，名称，logo
     * 3，记录并计算商品总价和商品数量
     */

    //保存商品的容器
    private CopyOnWriteArrayList<GoodsInfo> mGoodsInfos = new CopyOnWriteArrayList<>();

    //记录商家信息
    private long sellerId;
    private String name;
    private String url;

    private Integer totalNum = 0;//商品数量
    private Integer money = 0;//总价,单位到分

    /**
     * 添加商品
     *
     * @param info
     * @return 数量
     */
    public Integer addGoods(GoodsInfo info) {
        int num = 0;
        //判断容器中是否含有该商品
        //如果有++
        //如果没有，添加一条记录
        boolean isContain = false;//是否包含
        for (GoodsInfo item :
                mGoodsInfos) {
            if (item.id == info.id) {
                //有
                item.count++;
                num = item.count;
                isContain = true;
                break;
            }
        }

        if (!isContain) {
            num = info.count = 1;
            mGoodsInfos.add(info);
        }

        return num;
    }

    /**
     * 减少商品
     *
     * @param
     * @return 减少的数量
     */
    public Integer minusGood(GoodsInfo info) {
        Integer num = 0;
        //数量--
        //判断：数量是否为0
        //不为0，--
        for (GoodsInfo item :
                mGoodsInfos) {
            if (item.id == info.id) {
                item.count--;
                num = item.count;
                break;
            }
        }
        return num;
    }


    /**
     * 获取购物车中商品的总数量
     * @return
     */
    public Integer getTotalNum() {
        totalNum = 0;
        for (GoodsInfo item :
                mGoodsInfos) {
            totalNum += item.count;
        }
        return totalNum;
    }
}
