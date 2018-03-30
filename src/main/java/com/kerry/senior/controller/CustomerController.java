package com.kerry.senior.controller;

import com.kerry.senior.result.Result;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author CP_dongchuan
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@Valid @RequestBody CustomerRegisterVo vo){
        return Result.success(customerService.register(vo));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@Valid @RequestBody LoginVo vo){
        return Result.success(customerService.login(vo));
    }
}
