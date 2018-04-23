package com.kerry.senior.exception;

import com.kerry.senior.result.CodeMsg;

/**
 * @author Kerry Dong
 */
public class LoginException extends GlobalException {

    public LoginException(CodeMsg cm) {
        super(cm);
    }
}
