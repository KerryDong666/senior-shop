package com.kerry.senior.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kerry.senior.service.DubboDemo;

/**
 * @author Kerry Dong
 * @date 2018/3/30
 */
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultDemoService implements DubboDemo {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
