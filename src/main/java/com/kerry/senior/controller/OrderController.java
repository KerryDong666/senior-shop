package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.OrderInfo;
import com.kerry.senior.redis.RedisUtil;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.util.CurrentUser;
import com.kerry.senior.vo.GoodsVo;
import com.kerry.senior.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
    private CustomerService userService;
	
	@Autowired
    private RedisUtil redisService;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result info(@CurrentUser Customer user, @RequestParam("orderId") Long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
