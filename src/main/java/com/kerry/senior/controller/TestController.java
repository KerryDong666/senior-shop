package com.kerry.senior.controller;

import com.kerry.senior.domain.User;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.UserService;
import com.kerry.senior.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试使用
 * @author CP_dongchuan
 */
@RestController
public class TestController {

    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/hello.do")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/users")
    public List<User> list(){
        //int a = 1 / 0;
        return userService.list();
    }

    @RequestMapping("/val")
    public Result login(@Valid @RequestBody LoginVo vo){
        return Result.success(Boolean.TRUE);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody User user){
        userService.insert(user);
        return Result.success(true);
    }

}
