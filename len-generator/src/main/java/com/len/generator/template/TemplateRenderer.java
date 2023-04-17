package com.len.generator.template;

import java.io.IOException;

import com.len.generator.ErrConstant;
import com.len.generator.metadata.MetaData;

/**
 * 模板 render类
 */
public class TemplateRenderer {

    protected TemplateStrategy templateStrategy;

    public void setStrategy(TemplateStrategy templateStrategy) {
        this.templateStrategy = templateStrategy;
    }

    public String[] render(MetaData metaData) {
        if (templateStrategy == null) {
            throw new RuntimeException(ErrConstant.ENGINE_NOT_CONFIG);
        }
        try {
            return templateStrategy.render(metaData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造
     */
    public static class Builder {
        private TemplateStrategy templateStrategy;

        public Builder() {}

        /**
         * 根据模板对象渲染
         * 
         * @param templateStrategy 模板
         * @return builder
         */
        public Builder withTemplateStrategy(TemplateStrategy templateStrategy) {
            this.templateStrategy = templateStrategy;
            return this;
        }

        /**
         * 根据模板class渲染
         * 
         * @param templateStrategyClass 模板 class
         * @return builder
         */
        public Builder withTemplateStrategy(Class<? extends TemplateStrategy> templateStrategyClass) {
            try {
                this.templateStrategy = templateStrategyClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        /**
         * 构造一个模板 render
         * 
         * @return TemplateRenderer
         */
        public TemplateRenderer build() {
            TemplateRenderer renderer = new TemplateRenderer();
            renderer.templateStrategy = templateStrategy;
            return renderer;
        }

        /**
         * 生成
         * 
         * @param data 元数据
         * @return 生成的内容数组
         */
        public String[] render(MetaData data) {
            if (templateStrategy == null) {
                throw new RuntimeException(ErrConstant.ENGINE_NOT_CONFIG);
            }
            // 使用策略对象进行模板替换
            try {
                return templateStrategy.render(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
