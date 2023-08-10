package com.len.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BusinessException extends RuntimeException {
	private Integer code;
    private String errMsg;

    public BusinessException() {
    }

    public BusinessException(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
