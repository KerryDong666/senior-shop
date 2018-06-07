package com.kerry.senior.config;

import com.kerry.senior.redis.RedisConstant;
import com.kerry.senior.util.CookieUtil;
import com.kerry.senior.util.RequiredLogin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验token的拦截器
 *
 * @author Kerry Dong
 */
@Component
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //handler转换为HandlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //判断是否有RequiredLogin注解
            if (hm.getMethodAnnotation(RequiredLogin.class) != null){
                String cookieToken = CookieUtil.getCookieValue(request, RedisConstant.USER_COOKIE_NAME);
                String paramToken = request.getParameter(RedisConstant.USER_COOKIE_NAME);
                if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
