package com.len.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类型转换工具
 *
 * @author chenxing
 */
public class CastClassUtils {

    private CastClassUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将Object转为目标类型的List集合,如果无法转换则返回空集合(非null)
     * 
     * @param obj 需要转换的object
     * @param clazz 需要转换的类型
     * @return 结果
     * @param <T> 泛型控制
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        if (obj instanceof List<?>) {
            List<?> list = (List<?>)obj;
            return list.stream().map(clazz::cast).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 将Object转为目标类型的Map集合,如果无法转换则返回空集合(非null)
     * 
     * @param obj 需要转换的对象
     * @param kClass key 对应的类型
     * @param vClass value对应的类类型
     * @return 转换后的结果
     */
    public static <K, V> Map<K, V> castMap(Object obj, Class<K> kClass, Class<V> vClass) {
        HashMap<K, V> result = new HashMap<>();
        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>)(obj);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                result.put(kClass.cast(entry.getKey()), vClass.cast(entry.getValue()));
            }
        }
        return result;
    }
}
