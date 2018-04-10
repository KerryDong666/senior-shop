package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CP_dongchuan
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private CustomerService customerService;

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
    public String toList(Model model, Customer customer){
        model.addAttribute("customer", customer);
        return "goods_list";
    }



}
