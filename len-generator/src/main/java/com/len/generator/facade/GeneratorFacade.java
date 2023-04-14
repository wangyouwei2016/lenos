package com.len.generator.facade;

import com.len.generator.actuator.GeneratorActuator;
import com.len.generator.metadata.MetaData;

public class GeneratorFacade {

    /**
     * 根据元数据配置生成
     * 
     * @param metaData 元数据
     * @param templateContext 模板字符串
     * @return 生成信息
     */
    public static GeneratorActuator createActuator(MetaData metaData, String templateContext) {
        return new GeneratorActuator(metaData, templateContext);
    }

    /**
     * 根据json生成
     * 
     * @param json json配置
     * @param templateContext 模板字符串
     * @return 生成信息
     */
    public static GeneratorActuator createActuator(String json, String templateContext) {
        return new GeneratorActuator(json, templateContext);
    }

}
