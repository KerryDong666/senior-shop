package com.kerry.senior.controller;

import com.kerry.senior.domain.User;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.UserService;
import com.kerry.senior.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author CP_dongchuan
 * @date 2018/3/28
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello.do")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/users")
    public List<User> list(){
        int a = 1 / 0;
        return userService.list();
    }

    @RequestMapping("/val")
    public Result<Boolean> login(@Valid LoginVo vo){
        int a = 1/0;
        return Result.success(Boolean.TRUE);

    }

}
