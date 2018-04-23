package com.kerry.senior.mapper;

import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.domain.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author CP_dongchuan
 * @date 2018/4/16
 */
@Mapper
public interface OrderMapper {


    Long insert(OrderInfo orderInfo);

    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    int insertSeckillOrder(SeckillOrder seckillOrder);

    OrderInfo getOrderById(Long orderId);

}
