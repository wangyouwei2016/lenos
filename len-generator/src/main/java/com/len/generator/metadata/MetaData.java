package com.len.generator.metadata;

import java.util.Collection;
import java.util.Map;

/**
 * 元数据
 */
public interface MetaData {

    /**
     * 添加基础数据
     * 
     * @param key key
     * @param value value
     */
    MetaData putStrVar(String key, Object value);

    /**
     * 添加对象
     *
     * @param key key
     * @param value value extends Serializable
     */
    MetaData putObjVar(String key, Object value);

    /**
     * 添加集合
     * 
     * @param key key
     * @param value collection
     */
    MetaData putArrVar(String key, Collection<?> value);

    /**
     * 获取所有元数据信息
     * 
     * @return
     */
    Map<String, Object> getAll();

    /**
     * 获取模板路径
     * 
     * @return 模板路径
     */
    String[] getTemplatePath();

    /**
     * 输出路径
     * 
     * @return 输出路径
     */
    String getOutputPath();

}
