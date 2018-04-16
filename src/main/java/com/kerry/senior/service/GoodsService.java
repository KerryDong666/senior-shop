package com.kerry.senior.service;

import com.kerry.senior.vo.GoodsVo;

import java.util.List;

/**
 * @author CP_dongchuan
 * @date 2018/4/10
 */
public interface GoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);

    void reduceStock(GoodsVo goods);
}
