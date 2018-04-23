package com.kerry.senior.mapper;

import com.kerry.senior.domain.SeckillGoods;
import com.kerry.senior.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CP_dongchuan
 */
@Repository
@Mapper
public interface GoodsMapper {

    /**
     * 查询所有商品信息(包括秒杀信息)
     * @return
     */
    List<GoodsVo> listGoodsVo();

    /**
     * 根据goodsId查询商品信息(包括秒杀信息)
     * @param goodsId
     * @return
     */
    GoodsVo queryGoodsVoByGoodsId(Long goodsId);

    /**
     * 减少商品库存
     * @param seckillGoods
     */
    int reduceStock(SeckillGoods seckillGoods);

    /**
     * 获取秒杀商品库存
     * @param goodsId
     * @return
     */
    Integer getGoodsVoStockByGoodsId(Long goodsId);
}
