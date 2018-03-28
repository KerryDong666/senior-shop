package com.kerry.senior.controller;

import com.kerry.senior.domain.User;
import com.kerry.senior.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return userService.list();
    }

}
