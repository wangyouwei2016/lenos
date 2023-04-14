package com.len.generator.metadata.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.len.generator.metadata.MetaData;
import com.len.generator.metadata.MetaDataHelper;
import org.apache.commons.io.IOUtils;

import com.len.generator.template.FreemarkerPathTemplateStrategy;

/**
 * 根据路径获取参数
 */
public class JsonPathSource extends MetaDataSource {

    protected final String path;

    public JsonPathSource(String path) {
        this.path = path;
    }

    @Override
    public MetaData getMetaData() {
        ClassLoader classLoader = FreemarkerPathTemplateStrategy.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new RuntimeException(java.lang.String.format("json参数配置%s, 未找到", path));
        }
        String jsonProperties;
        try {
            jsonProperties = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return MetaDataHelper.jsonConvertToMetaData(jsonProperties);
    }
}