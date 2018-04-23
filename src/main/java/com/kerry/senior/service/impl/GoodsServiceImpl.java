package com.kerry.senior.service.impl;

import com.kerry.senior.domain.SeckillGoods;
import com.kerry.senior.exception.ParamMissingException;
import com.kerry.senior.mapper.GoodsMapper;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CP_dongchuan
 */
@Service
@CacheConfig(cacheNames = "goods")
public class GoodsServiceImpl implements GoodsService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> listGoodsVo() {
        List<GoodsVo> goodsVos = goodsMapper.listGoodsVo();
        logger.info("查询商品信息成功, msg = {}", goodsVos);
        return goodsVos;
    }

    /**
     * 根据商品id查询商品详情
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        if (goodsId == null) {
            throw new ParamMissingException(CodeMsg.PARAM_ERROR);
        }
        GoodsVo goodsVo = goodsMapper.queryGoodsVoByGoodsId(goodsId);
        logger.info("查询产品信息成功, msg = {}", goodsVo);
        return goodsVo;
    }

    /**
     * 减少库存
     */
    @Override
    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goods.getId());
        int line = goodsMapper.reduceStock(seckillGoods);
        if (line < 1) {
            logger.error("更新库存信息失败, goodsId = {}", goods.getId());
            //throw new GlobalException(CodeMsg.SQL_ERROR);
            return false;
        }
        logger.info("更新库存信息成功, goodsId = {}", goods.getId());
        return true;
    }

    @Override
    @Cacheable(key = "'goods:stock:' + #goodsId")
    public Integer getGoodsVoStockByGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoStockByGoodsId(goodsId);
    }
}
