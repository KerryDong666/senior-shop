package com.kerry.senior.exception;

import com.kerry.senior.result.CodeMsg;

/**
 * @author CP_dongchuan
 * @date 2018/3/30
 */
public class LoginException extends GlobalException {

    public LoginException(CodeMsg cm) {
        super(cm);
    }
}
