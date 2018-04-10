package com.kerry.senior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.kerry.senior")
@EnableTransactionManagement //开启事务支持
//@EnableDubboConfig //开启dubbo微服务
public class SeniorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SeniorApplication.class, args);
        //WebSocket.setApplicationContext(context);
    }

    /**
     * 修改DispatcherServlet默认配置,只匹配*.do的url动态请求
     * 这里配置了之后默认的静态页面将无法访问
     */
    //@Bean
    //public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
    //    ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
    //    registration.getUrlMappings().add("*.do");
    //    return registration;
    //}

    //@Bean
    //public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
    //    return new ServletRegistrationBean(dispatcherServlet,"*.do");
    //}

    //添加静态路径
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
    //    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    //    registry.addResourceHandler("/META-INF/resources/**").addResourceLocations("classpath:/META-INF/resources/");
    //    registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
    //    super.addResourceHandlers(registry);
    //}
}
