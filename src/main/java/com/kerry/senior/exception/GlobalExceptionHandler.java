package com.kerry.senior.exception;

import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value=Exception.class)
	public Result exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        //检查各种异常并返回对应的错误信息(可根据需要修改)
        if(e instanceof GlobalException) { //自定义全局校验
            GlobalException ex = (GlobalException)e;
            return Result.error(ex.getCm());
        }
        //参数校验,可能存在多个参数异常,这里取第一个
        else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if (e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
            BindingResult bindingResult = ex.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String msg = fieldErrors.get(0).getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if(e instanceof ParamMissingException){
            ParamMissingException ex = (ParamMissingException)e;
            return Result.error(ex.getCm());
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
