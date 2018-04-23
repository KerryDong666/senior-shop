package com.kerry.senior.service;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.vo.GoodsVo; /**
 * @author CP_dongchuan
 * @date 2018/4/16
 */
public interface SeckillService {

    OrderInfo seckill(Customer user, GoodsVo goods);

    Long getSeckillResult(Long id, long goodsId);
}
