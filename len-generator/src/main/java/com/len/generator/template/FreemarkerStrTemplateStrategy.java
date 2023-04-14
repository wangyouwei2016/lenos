package com.len.generator.template;

import com.len.generator.metadata.MetaData;

/**
 * freemarker 字符串模板策略
 */
public class FreemarkerStrTemplateStrategy extends AbstractFreemarkerTemplate {

    private String[] templateContent;

    /**
     * 元数据配置拿模板参考 @{@link MetaData#getTemplatePath()}
     */
    public FreemarkerStrTemplateStrategy() {}

    public FreemarkerStrTemplateStrategy(String... templateContent) {
        this.templateContent = templateContent;
    }

    @Override
    public String[] getTemplateContext() {
        return templateContent;
    }
}
