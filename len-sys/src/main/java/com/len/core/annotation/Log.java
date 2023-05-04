package com.len.core.annotation;

import java.lang.annotation.*;

/**
 * 记录日志
 * @author star
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface Log {
    /**
     * 内容
     */
    String desc();

    /**
     * 类型 curd
     */
    LOG_TYPE type() default LOG_TYPE.ATHOR;

    enum LOG_TYPE {
        /**
         * 日志操作类型
         */
        ADD, UPDATE, DEL, SELECT, ATHOR
    }
}
