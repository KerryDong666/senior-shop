package com.kerry.senior.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于判断是否需要登录才能访问的注解,用在Controller中的方法上
 * @author Kerry Dong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredLogin {

    /**
     * 是否需要记录日志,默认不记录
     */
    boolean recordLog() default false;

    /**
     * 请求是否需要CSRFToken校验,默认不需要
     * @return
     */
    boolean CSRFToken() default false;
}
