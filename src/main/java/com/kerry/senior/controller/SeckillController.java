package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.mq.MQSender;
import com.kerry.senior.mq.SeckillMessage;
import com.kerry.senior.redis.RedisKey;
import com.kerry.senior.redis.RedisUtil;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.service.SeckillService;
import com.kerry.senior.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀Controller
 */
@Controller
public class SeckillController {

    @Autowired
    private CustomerService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private MQSender sender;

    /**
     * 优化:秒杀页面静态化
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/seckill")
    @ResponseBody
    public Result list(Model model, @CurrentUser Customer user, Long goodsId)
    {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        /*GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.error(CodeMsg.SECKILL_STOCK_OVER);
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(user, goods);*/
        //优化1:商品库存写入redis
        Integer stock = goodsService.getGoodsVoStockByGoodsId(goodsId);
        if (stock == null || stock <= 0) {
            return Result.error(CodeMsg.SECKILL_STOCK_OVER);
        }
        //判断是否已经秒杀,不能重复秒杀
        SeckillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //优化2:预减库存
        redisUtil.decr(RedisKey.GOODS_STOCK + goodsId);
        //优化3:异步下单
        SeckillMessage sm = new SeckillMessage();
        sm.setGoodsId(goodsId);
        sm.setUser(user);
        sender.seckill(sm);
        return Result.success(0); //返回排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/seckill/result", method= RequestMethod.GET)
    @ResponseBody
    public Result miaoshaResult(Model model,@CurrentUser Customer user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Long result  =seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }
}

