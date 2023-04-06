package com.len.util;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class LenResponse {

    // 默认成功
    private boolean flag = true;
    private String msg;
    private JSONObject jsonObj;
    private Integer status;
    private Object data;

    public LenResponse() {}

    public LenResponse(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public LenResponse(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public LenResponse(boolean flag, String msg, Integer status) {
        this.flag = flag;
        this.msg = msg;
        this.status = status;
    }

    /**
     * restful 返回
     */
    public static LenResponse error(String msg) {
        return new LenResponse(false, msg);
    }

    public static LenResponse sucess(String msg) {
        return new LenResponse(true, msg);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
