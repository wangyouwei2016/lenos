package com.len.core.annotation;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.len.base.CurrentUser;
import com.len.core.shiro.Principal;
import com.len.entity.SysLog;
import com.len.mapper.SysLogMapper;
import com.len.util.IpUtil;

/**
 *
 * 为增删改添加监控
 * 
 * @author star
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private final SysLogMapper logMapper;

    public LogAspect(SysLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Pointcut("@annotation(com.len.core.annotation.Log)")
    private void pointcut() { /* 织入点无需方法体 */ }

    @After("pointcut()")
    public void insertLogSuccess(JoinPoint jp) throws InterruptedException {
        addLog(jp, getDesc(jp));
    }

    private void addLog(JoinPoint jp, String text) throws InterruptedException {
        Log.LOG_TYPE type = getType(jp);
        SysLog sysLog = new SysLog();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 一些系统监控
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            String ip = IpUtil.getIp(request);
            sysLog.setIp(ip);
        }
        sysLog.setCreateTime(new Date());
        sysLog.setType(type.toString());
        sysLog.setText(text);

        Object[] obj = jp.getArgs();
        StringBuilder buffer = new StringBuilder();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                buffer.append("[参数").append(i + 1).append(":");
                Object o = obj[i];
                if (o instanceof Model) {
                    continue;
                }
                String parameter = null;
                try {
                    parameter = JSON.toJSONString(o);
                } catch (Exception e) {
                    TimeUnit.MILLISECONDS.sleep(0);
                }
                buffer.append(parameter);
                buffer.append("]");
            }
        }
        sysLog.setParam(buffer.toString());
        try {
            CurrentUser currentUser = Principal.getCurrentUse();
            if (currentUser != null) {
                sysLog.setUserName(currentUser.getUsername());
            }
        } catch (UnavailableSecurityManagerException e) {
            log.error(e.getMessage());
        }
        logMapper.insert(sysLog);
    }

    /**
     * 记录异常
     *
     * @param joinPoint 连接点对象
     * @param e 异常对象
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) throws InterruptedException {
        log.error("-----------afterException:{}", e.getMessage());
        addLog(joinPoint, getDesc(joinPoint) + e.getMessage());
    }

    private String getDesc(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature)joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).desc();
    }

    private Log.LOG_TYPE getType(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature)joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).type();
    }
}
