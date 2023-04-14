package com.len.generator.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.len.generator.metadata.MetaData;
import com.len.generator.util.TemplateUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * freemarker render 策略抽象类
 */
public abstract class AbstractFreemarkerTemplate implements TemplateStrategy {

    protected freemarker.template.Template template;
    protected Configuration cfg;

    {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * 暴露给自定义实现类
     * 
     */
    public Configuration getConfiguration() {
        return cfg;
    }

    @Override
    public String[] render(MetaData metaData) throws IOException {
        String[] templateContext = getTemplateContext();
        if (templateContext == null || templateContext.length == 0) {
            // 从元数据模板路径配置拿模板 互斥
            templateContext = TemplateUtil.getTemplateContentBypath(metaData.getTemplatePath());
        }
        String[] writerArr = new String[templateContext.length];
        int i = 0;
        for (String templateItem : templateContext) {
            template = new freemarker.template.Template("", new StringReader(templateItem), getConfiguration());

            StringWriter writer = new StringWriter();
            try {
                template.process(metaData.getAll(), writer);
                writerArr[i++] = writer.toString();
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        }
        return writerArr;

    }
}
