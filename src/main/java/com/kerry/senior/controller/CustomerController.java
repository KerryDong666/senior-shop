package com.kerry.senior.controller;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.util.CurrentUser;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;
import com.kerry.senior.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author CP_dongchuan
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 注册
     * @param vo 会员注册vo
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@Valid @RequestBody CustomerRegisterVo vo){
        return Result.success(customerService.register(vo));
    }

    /**
     * 登录
     * @param vo loginVo
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@Valid @RequestBody LoginVo vo, HttpServletResponse response){
        Customer login = customerService.login(vo, response);
        //webSocket.sendMessage(login.getNickname() + "登录成功");
        //webSocket.sendMsg("10003");
        return Result.success(login);
    }


    /**
     * 查询用户信息
     */
    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public Result userInfo(@CurrentUser Customer customer){
        return Result.success(customer);
    }
}
