package com.chen.common;

import lombok.Data;

@Data
public class CommonResult {

    public static final Integer CODE_SUCCESS = 200;
    
    public static final Integer CODE_ERROR = 404;
    
    public static final String MESSAGE_DEFAULT_SUCCESS = "success";
    
    public static final String MESSAGE_DEFAULT_ERROR = "error";

    private Integer code;
    
    private String message;
    
    private Object data;

    public CommonResult() {
    }

    public CommonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult code(int code){
        this.code=code;
        return this;
    }

    public CommonResult message(String msg) {
        this.message=msg;
        return this;
    }

    public CommonResult data(Object o) {
        this.data=o;
        return this;
    }

    public static CommonResult success(Object o) {
        return new CommonResult(CODE_SUCCESS, MESSAGE_DEFAULT_SUCCESS, o);
    }

    public static CommonResult success() {
        return success(null);
    }

    public static CommonResult error(String message) {
        return new CommonResult(CODE_ERROR, message, null);
    }

    public static CommonResult error() {
        return error(MESSAGE_DEFAULT_ERROR);
    }

}
