package com.kerry.senior.mq;

import com.kerry.senior.domain.Customer;

/**
 * @author Kerry Dong
 */
public class SeckillMessage {

    private Customer user;
    private Long goodsId;

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
