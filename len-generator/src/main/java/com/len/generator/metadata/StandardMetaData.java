package com.len.generator.metadata;

import java.util.Collection;

/**
 * 默认元数据实现
 */
public class StandardMetaData extends AbstractMetaData {

    /**
     * 模板路径
     */
    private String[] templatePath;

    /**
     * 输出路径
     */
    private String outputPath;

    public StandardMetaData() {}

    /**
     *
     * @param templatePath 模板路径
     * @param outputPath 输出路径
     */
    public StandardMetaData(String[] templatePath, String outputPath) {
        this.templatePath = templatePath;
        this.outputPath = outputPath;
    }

    @Override
    public MetaData putStrVar(String key, Object value) {
        strVar.put(key, value);
        return this;
    }

    @Override
    public MetaData putObjVar(String key, Object value) {
        objVar.put(key, value);
        return this;
    }

    @Override
    public MetaData putArrVar(String key, Collection<?> value) {
        arrVar.put(key, value);
        return this;
    }

    @Override
    public String[] getTemplatePath() {
        return templatePath;
    }

    @Override
    public String getOutputPath() {
        return outputPath;
    }

}
