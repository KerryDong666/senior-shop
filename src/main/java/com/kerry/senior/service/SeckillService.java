package com.kerry.senior.service;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.vo.GoodsVo;
/**
 * @author Kerry Dong
 */
public interface SeckillService {

    OrderInfo seckill(Customer user, GoodsVo goods);

    Long getSeckillResult(Long id, long goodsId);
}
