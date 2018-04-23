package com.kerry.senior.util;

import java.util.UUID;

/**
 * @author Kerry Dong
 */
public class UUIDUtil {

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
