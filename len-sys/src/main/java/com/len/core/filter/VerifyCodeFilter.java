package com.len.core.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.len.util.MsHelper;

/**
 * 验证码拦截
 */
public class VerifyCodeFilter extends AccessControlFilter {

    /**
     * 是否开启验证码验证 默认true
     */
    private boolean enableVerifyCode;

    /**
     * 前台提交的验证码name
     */
    private String jcaptchaParam = "code";

    /**
     * 验证失败后setAttribute key
     */
    private String failureKeyAttribute = "loginFailure";

    /**
     * 验证码校验拦截
     * 
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        request.setAttribute("verfitiCode", enableVerifyCode);
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        // 2、判断验证码是否禁用 或不是表单提交
        if (!enableVerifyCode || !"post".equalsIgnoreCase(httpRequest.getMethod())) {
            return true;
        }
        Object code = getSubject(request, response).getSession().getAttribute(jcaptchaParam);
        String storedCode;
        if (null != code) {
            storedCode = code.toString();
        } else {
            request.setAttribute(failureKeyAttribute, "code.timeout");
            return false;
        }
        // 表单提交，校验验证码的正确性
        String currentCode = httpRequest.getParameter(jcaptchaParam);

        return StringUtils.equalsIgnoreCase(storedCode, currentCode);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("message", MsHelper.getMsg("login.code.error"));
        WebUtils.issueRedirect(servletRequest, servletResponse, "/login", paramMap);
        return false;
    }

    public boolean isEnableVerifyCode() {
        return enableVerifyCode;
    }

    public void setEnableVerifyCode(boolean enableVerifyCode) {
        this.enableVerifyCode = enableVerifyCode;
    }

    public String getJcaptchaParam() {
        return jcaptchaParam;
    }

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }
}
