package com.kerry.senior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class SeniorApplication {

    public static void main(String[] args) {
		SpringApplication.run(SeniorApplication.class, args);
	}

    /**
     * 修改DispatcherServlet默认配置,只匹配*.do的url动态请求
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.do");
        return registration;
    }
}
