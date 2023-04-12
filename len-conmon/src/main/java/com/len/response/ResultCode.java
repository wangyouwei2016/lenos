package com.len.response;

public enum ResultCode {
	/* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    /**
     * 参数无效
     */
    PARAM_NOT_VALID(1001, "参数无效"),
    /**
     * 参数为空
     */
    PARAM_IS_BLANK(1002, "参数为空"),
    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(2001, "用户未登录"),
    /**
     * 账号已过期
     */
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    /**
     * 密码错误
     */
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    /**
     * 密码过期
     */
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    /**
     * 账号不可用
     */
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    /**
     * 账号被锁定
     */
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    /**
     * 账号不存在
     */
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    /**
     * 账号已存在
     */
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    /**
     * 账号下线
     */
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /*部门错误*/
    /**
     * 部门不存在
     */
    DEPARTMENT_NOT_EXIST(3007, "部门不存在"),
    /**
     * 部门已存在
     */
    DEPARTMENT_ALREADY_EXIST(3008, "部门已存在"),

    /* 业务错误 */
    /**
     * 没有权限
     */
    NO_PERMISSION(3001, "没有权限"),

    /*运行时异常*/
    /**
     * 算数异常
     */
    ARITHMETIC_EXCEPTION(9001,"算数异常"),
	/**
	 * 查询无果
	 */
	NO_SEARCH(4001,"查询无果");
	
	private Integer code;

	private String message;
	
	ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }
	
	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
