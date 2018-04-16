package com.kerry.senior.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author CP_dongchuan
 */
public class CookieUtil {

    /**
     * 获取HttpServletRequest中指定名称的cookie值
     * @param request 请求
     * @param cookieName cookie名称
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[]  cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}