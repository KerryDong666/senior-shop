package com.kerry.senior.service;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.vo.GoodsVo;
/**
 * @author Kerry Dong
 */
public interface OrderService {

    OrderInfo createOrder(Customer user, GoodsVo goods);


    SeckillOrder getSecKillOrderByUserIdGoodsId(Long userId, Long goodsId);

    OrderInfo getOrderById(Long orderId);

    Long getSeckillResult(Long id, long goodsId);
}
