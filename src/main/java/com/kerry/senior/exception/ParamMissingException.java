package com.kerry.senior.exception;

import com.kerry.senior.result.CodeMsg;

/**
 * @author CP_dongchuan
 * @date 2018/4/16
 */
public class ParamMissingException extends RuntimeException {

    private CodeMsg cm;

    public ParamMissingException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
