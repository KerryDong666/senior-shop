package com.kerry.senior.vo;

import com.kerry.senior.domain.OrderInfo;

/**
 * @author CP_dongchuan
 * @date 2018/4/23
 */
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;
    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public OrderInfo getOrder() {
        return order;
    }
    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
