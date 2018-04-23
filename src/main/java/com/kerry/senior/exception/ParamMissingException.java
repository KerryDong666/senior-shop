package com.kerry.senior.exception;

import com.kerry.senior.result.CodeMsg;

/**
 * @author Kerry Dong
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
