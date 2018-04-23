package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.redis.RedisConstant;
import com.kerry.senior.redis.RedisKey;
import com.kerry.senior.redis.RedisUtil;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.util.CurrentUser;
import com.kerry.senior.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext appcxt;

    @Autowired
    private RedisUtil redisUtil;
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
    //@RequestMapping("/to_list")
    //public String toList(Model model, @CurrentUser Customer customer){
    //    model.addAttribute("customer", customer);
    //    List<GoodsVo> goodsList = goodsService.listGoodsVo();
    //    model.addAttribute("goodsList", goodsList);
    //    return "goods_list";
    //}

    /**
     * 优化一:商品页面缓存
     * @param model
     * @param customer
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toH5List(HttpServletRequest request, HttpServletResponse response, Model model, @CurrentUser Customer customer){
        //取数据
        String html = redisUtil.get(RedisKey.GOODS_PAGE);
        if (StringUtils.isNotBlank(html)) {
            return html;
        }
        model.addAttribute("customer", customer);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), appcxt);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (StringUtils.isNotBlank(html)) {
            redisUtil.setStringValue(RedisKey.GOODS_PAGE, html, RedisConstant.GOODS_PAGE_EXPIRE);
        }
        return html;
    }

    ///**
    // * 跳转到商品详情页
    // * @param model
    // * @param user
    // * @param goodsId
    // * @return
    // */
    //@RequestMapping("/to_detail/{goodsId}")
    //public String detail(Model model, @CurrentUser Customer user,
    //                     @PathVariable("goodsId")long goodsId) {
    //    model.addAttribute("user", user);
    //    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    //    model.addAttribute("goods", goods);
    //    long startAt = goods.getStartDate().getTime();
    //    long endAt = goods.getEndDate().getTime();
    //    long now = System.currentTimeMillis();
    //
    //    int seckillStatus = 0;
    //    int remainSeconds = 0;
    //    if (now < startAt) {//秒杀还没开始，倒计时
    //        seckillStatus = 0;
    //        remainSeconds = (int) ((startAt - now) / 1000);
    //    } else if (now > endAt) {//秒杀已经结束
    //        seckillStatus = 2;
    //        remainSeconds = -1;
    //    } else {//秒杀进行中
    //        seckillStatus = 1;
    //        remainSeconds = 0;
    //    }
    //    model.addAttribute("seckillStatus", seckillStatus);
    //    model.addAttribute("remainSeconds", remainSeconds);
    //    return "goods_detail";
    //}

    /**
     * 优化二:URL缓存
     *    跳转到商品详情页
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, @CurrentUser Customer user, @PathVariable("goodsId")long goodsId) {
        //取数据
        String html = redisUtil.get(RedisKey.GOODS_PAGE+goodsId);
        if (StringUtils.isNotBlank(html)) {
            return html;
        }
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
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), appcxt);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (StringUtils.isNotBlank(html)) {
            redisUtil.setStringValue(RedisKey.GOODS_PAGE + goodsId, html, RedisConstant.GOODS_PAGE_EXPIRE);
        }
        return html;
    }
}
