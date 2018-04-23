package com.kerry.senior.controller;

import com.kerry.senior.domain.User;
import com.kerry.senior.mq.MQSender;
import com.kerry.senior.result.Result;
import com.kerry.senior.service.UserService;
import com.kerry.senior.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    MQSender sender;

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

    @RequestMapping(value = "/mq", method = RequestMethod.GET)
    public Result send(){
        sender.send("nihao, 我是串锅");
        return Result.success("success");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result topic() {
        sender.sendTopic("我是串锅");
        return Result.success("Hello，world");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result fanout() {
        sender.sendFanout("hello,imooc");
        return Result.success("Hello，world");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result header() {
        sender.sendHeader("hello,imooc");
        return Result.success("Hello，world");
    }
}
