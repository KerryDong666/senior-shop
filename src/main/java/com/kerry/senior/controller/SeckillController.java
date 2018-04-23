package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.redis.RedisUtil;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.service.SeckillService;
import com.kerry.senior.util.CurrentUser;
import com.kerry.senior.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
        //model.addAttribute("user", user);
        //if(user == null) {
        //    return "login";
        //}
        ////判断库存
        //GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        //int stock = goods.getStockCount();
        //if(stock <= 0) {
        //    model.addAttribute("errmsg", CodeMsg.SECKILL_STOCK_OVER.getMsg());
        //    return "seckill_fail";
        //}
        ////判断是否已经秒杀到了
        //SeckillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        //if(order != null) { //不能重复秒杀
        //    model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
        //    return "seckill_fail";
        //}
        ////减库存 下订单 写入秒杀订单
        //OrderInfo orderInfo = seckillService.seckill(user, goods);
        //model.addAttribute("orderInfo", orderInfo);
        //model.addAttribute("goods", goods);
        //return "order_detail";
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
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
        OrderInfo orderInfo = seckillService.seckill(user, goods);
        return Result.success(orderInfo);
    }
}

