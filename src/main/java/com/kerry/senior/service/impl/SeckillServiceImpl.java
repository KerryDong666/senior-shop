package com.kerry.senior.service.impl;


import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.exception.ParamMissingException;
import com.kerry.senior.redis.RedisKey;
import com.kerry.senior.redis.RedisUtil;
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
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OrderInfo seckill(Customer user, GoodsVo goods) {
        if (user == null || goods == null) {
            throw new ParamMissingException(CodeMsg.PARAM_ERROR);
        }
        //减库存
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    /**
     * 设置减少库存失败标记
     *
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisUtil.set(RedisKey.IS_GOODS_OVER + goodsId, true);
    }

    /**
     * @param id
     * @param goodsId
     * @return
     */
    @Override
    public Long getSeckillResult(Long id, long goodsId) {
        SeckillOrder order = orderService.getSecKillOrderByUserIdGoodsId(id, goodsId);
        if (order != null) { //秒杀成功,返回订单id
            return order.getOrderId();
        } else { //秒杀失败
            boolean isOver = this.getGoodsOver(goodsId);
            if (isOver) {
                return -1L;
            } else {
                return 0L;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisUtil.exists(RedisKey.IS_GOODS_OVER + goodsId);
    }
}
