package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.util.CurrentUser;
import com.kerry.senior.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author CP_dongchuan
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    //@Autowired
    //private CustomerService customerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 分布式session版本一:通过cookie在缓存中找到用户信息,但是每一个方法都需要用户信息,我们不能在每个controller方法中都添加CookieValue和RequestParam标签
     * 跳转到商品页面
     *    CookieValue注解用于在浏览器获取cookie的值
     *    RequestParam注解用于兼容手机端
     */
    //@RequestMapping("/to_list")
    //public String toList(Model model, HttpServletResponse response,
    //                     @CookieValue(value = RedisConstant.USER_COOKIE_NAME, required = false) String cookieToken,
    //                     @RequestParam(value = RedisConstant.USER_COOKIE_NAME, required = false) String paramToken){
    //    //参数校验,如果两个值都为空就跳转到登录页面
    //    if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)) {
    //        return "redirect:/login.html";
    //    }
    //    String token = StringUtils.isNotBlank(cookieToken) ? cookieToken : paramToken;
    //    Customer customer = customerService.getCustomerByToken(response, token);
    //    if (customer == null) {
    //        return "redirect:/login.html";
    //    }
    //    model.addAttribute("customer", customer);
    //    return "goods_list";
    //}

    /**
     * 分布式session版本二
     */
    @RequestMapping("/to_list")
    public String toList(Model model, @CurrentUser Customer customer){
        model.addAttribute("customer", customer);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /**
     * 跳转到商品详情页
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, @CurrentUser Customer user,
                         @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
