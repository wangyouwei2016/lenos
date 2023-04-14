package com.len.generator.template;

import com.len.generator.metadata.MetaData;
import com.len.generator.util.TemplateUtil;

/**
 * 路径寻找模板
 */
public class FreemarkerPathTemplateStrategy extends AbstractFreemarkerTemplate {

    protected String[] templateContent;

    /**
     * 元数据配置拿模板参考 @{@link MetaData#getTemplatePath()}
     */
    public FreemarkerPathTemplateStrategy() {

    }

    /**
     *
     * @param path 模板路径
     */
    public FreemarkerPathTemplateStrategy(String... path) {
        templateContent = TemplateUtil.getTemplateContentBypath(path);
    }

    @Override
    public String[] getTemplateContext() {
        return templateContent;
    }
}
