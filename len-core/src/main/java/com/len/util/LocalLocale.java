package com.len.util;

import java.util.Locale;

/**
 * 区域设置
 * 
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 */
public class LocalLocale {

    /**
     * 静态工具 无需实例化。
     */
    private LocalLocale() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<Locale> LOCAL = ThreadLocal.withInitial(() -> Locale.SIMPLIFIED_CHINESE);

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
