package com.kerry.senior.util;

import java.util.UUID;

/**
 * @author CP_dongchuan
 * @date 2018/3/29
 */
public class UUIDUtil {

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
