package com.kerry.senior.service;

import com.kerry.senior.vo.GoodsVo;

import java.util.List;

/**
 * @author Kerry Dong
 */
public interface GoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);

    boolean reduceStock(GoodsVo goods);

    Integer getGoodsVoStockByGoodsId(Long goodsId);
}
