package com.kerry.senior.service.impl;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.mapper.OrderMapper;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
    private OrderMapper orderMapper;
	
	public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, Long goodsId) {
		return orderMapper.getSeckillOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
    @Override
	public OrderInfo createOrder(Customer user, GoodsVo goods) {
	    //TODO 待优化
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1); //商品数量,限定每人只能抢购一个
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSeckillPrice());
		orderInfo.setOrderChannel(1); //1pc,2android,3ios
		orderInfo.setStatus(0); //新建未支付
		orderInfo.setUserId(user.getId());
		Long orderId = orderMapper.insert(orderInfo);
		//创建抢购订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(user.getId());
		orderMapper.insertSeckillOrder(seckillOrder);
		return orderInfo;
	}

    @Override
    public SeckillOrder getSecKillOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return orderMapper.getSeckillOrderByUserIdGoodsId(userId, goodsId);
    }

}
