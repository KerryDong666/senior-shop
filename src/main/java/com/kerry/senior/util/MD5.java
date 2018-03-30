package com.kerry.senior.util;

import com.kerry.senior.exception.GlobalException;
import com.kerry.senior.result.CodeMsg;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CP_dongchuan
 */
public class MD5 {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String md5(String src, String salt) {
        if (StringUtils.isBlank(src) || StringUtils.isBlank(salt)) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return DigestUtils.md5Hex(src.concat(salt));
    }

}
