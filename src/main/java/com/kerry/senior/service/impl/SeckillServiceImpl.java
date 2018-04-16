package com.kerry.senior.service.impl;


import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.exception.ParamMissingException;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.service.SeckillService;
import com.kerry.senior.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Override
    public OrderInfo seckill(Customer user, GoodsVo goods) {
        if (user == null || goods == null) {
            throw new ParamMissingException(CodeMsg.PARAM_ERROR);
        }
        //减库存
        goodsService.reduceStock(goods);
        //创建订单
        return orderService.createOrder(user, goods);
    }
}
