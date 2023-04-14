package com.len.generator.template;

import java.io.IOException;

import com.len.generator.metadata.MetaData;

/**
 * ，模板生成策略
 */
public interface TemplateStrategy {

    /**
     * 获取模板内容
     * 
     * @return 模板内容
     */
    String[] getTemplateContext();

    /**
     * 生成模板
     * 
     * @param metaData 元数据
     * @return 生成的信息(也可指代码)
     */
    String[] render(MetaData metaData) throws IOException;

}
