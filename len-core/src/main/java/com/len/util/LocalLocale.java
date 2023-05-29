package com.len.util;

import java.util.Locale;

/**
 * 区域设置
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 */
public class LocalLocale {

    private final static ThreadLocal<Locale> LOCAL = ThreadLocal.withInitial(() -> {
        // 语言的默认值
        return Locale.SIMPLIFIED_CHINESE;
    });

    public static Locale getLocale() {
        return LOCAL.get();
    }

    public static void setLocale(String locale) {
        setLocale(new Locale(locale));
    }

    public static void setLocale(Locale locale) {
        LOCAL.set(locale);
    }

    public static void clear() {
        LOCAL.remove();
    }

}
