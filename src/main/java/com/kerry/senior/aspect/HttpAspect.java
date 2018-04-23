package com.kerry.senior.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Kerry Dong
 * @version 2017/10/20
 */
@Aspect
@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //表示com.kerry.senior.controller包下的所有类的所有方法,必须这么写
    @Pointcut("execution(public * com.kerry.senior.controller.*.*(..))")
    public void log() {

    }

    //@Before("log()")
    //public void doBefore(JoinPoint joinPoint){
    //    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //    HttpServletRequest request = attributes.getRequest();
    //    //url.method,ip,类方法,参数
    //    logger.info("***切面log***,url={},method={},ip={},类方法={},参数={}",request.getRequestURL(),request.getMethod(),request.getRemoteAddr(),joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),joinPoint.getArgs());
    //}

    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("****************切面结束log********************");
        //Method[] methods = joinPoint.getTarget().getClass().getMethods();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
    }

    @AfterThrowing(throwing = "ex", pointcut = "log()")
    public void exception(JoinPoint joinPoint, Exception ex) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs(); //参数
        String methodName = signature.getName(); //方法名
        String className = signature.getDeclaringTypeName();
        logger.error("类 = {}, 方法={}, err = {}", className, methodName, ex.getStackTrace());

    }

    /**
     * 利用切面编程找到返回数据
     * @param object
     */
    //@AfterReturning(returning = "object",pointcut = "log()")
    //public void afterReturning(Object object){
    //    logger.info("response={}",object.toString());
    //}
}
