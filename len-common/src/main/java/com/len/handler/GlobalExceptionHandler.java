package com.len.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.len.response.Result;
import com.len.response.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常处理,没有指定异常的类型,不管什么异常都是可以捕获的
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result error(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        JSONArray arr = new JSONArray();
        for (FieldError fieldError : fieldErrors) {
            JSONObject obj = new JSONObject();
            obj.put("field", fieldError.getField());
            obj.put("msg", fieldError.getDefaultMessage());
            arr.add(obj);
        }
        return Result.error()
            .code(ResultCode.PARAM_NOT_VALID.getCode())
            .message(arr.toJSONString());
    }

    /**
     * 处理特定异常类型,可以定义多个,这里只以ArithmeticException为例
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        return Result.error().code(ResultCode.ARITHMETIC_EXCEPTION.getCode())
                .message(ResultCode.ARITHMETIC_EXCEPTION.getMessage());
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e){
        return Result.error().code(e.getCode())
                .message(e.getErrMsg());
    }

}
