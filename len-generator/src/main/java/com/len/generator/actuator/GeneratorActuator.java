package com.len.generator.actuator;

import com.len.generator.metadata.MetaData;
import com.len.generator.metadata.MetaDataHelper;
import com.len.generator.template.FreemarkerStrTemplateStrategy;
import com.len.generator.template.TemplateRenderer;

/**
 * 支持普通生成入参
 */
public class GeneratorActuator {

    private final MetaData metaData;

    private final TemplateRenderer templateRenderer;

    /**
     * 根据json 配置生成
     * 
     * @param jsonTemplate json 配置
     * @param templateContext 模板字符串
     */
    public GeneratorActuator(String jsonTemplate, String templateContext) {
        this(MetaDataHelper.jsonConvertToMetaData(jsonTemplate), templateContext);
    }

    /**
     * 根据元数据生成
     * 
     * @param metaData 元数据
     * @param templateContext 模板字符串
     */
    public GeneratorActuator(MetaData metaData, String templateContext) {
        this.metaData = metaData;
        templateRenderer = new TemplateRenderer();
        templateRenderer.setStrategy(new FreemarkerStrTemplateStrategy(templateContext));
    }

    /**
     * 生成
     * 
     * @return
     */
    public String generator() {
        return templateRenderer.render(metaData)[0];
    }

}
