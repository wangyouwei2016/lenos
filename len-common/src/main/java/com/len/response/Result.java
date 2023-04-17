package com.len.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Result<T> {
	
    private Boolean success;

    private Integer code;

    private String message;
    
    private T data;
    
    /**
     * 构造方法私有化,里面的方法都是静态方法
     * 达到保护属性的作用
     */
    private Result(){

    }

    /**
     * 这里是使用链式编程
     */
    public static <T> Result<T> ok(){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * 自定义返回成功与否
     * @param success
     * @return
     */
    public Result<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    
    public static <T> Result<T> success(T data){
    	Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.COMMON_FAIL.getCode());
        result.setMessage(ResultCode.COMMON_FAIL.getMessage());
        return result;
    }
    
    
    
//    public Result data(String key,Object value){
//        this.data.put(key,value);
//        return this;
//    }
//
//    public Result data(Map<String,Object> map){
//        this.setData(map);
//        return this;
//    }
}
