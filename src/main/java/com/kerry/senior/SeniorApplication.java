package com.kerry.senior;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableTransactionManagement //开启事务支持
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

    /**
     * 使用druid数据源管理事务
     */
    @Bean
    public PlatformTransactionManager tx(DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}
