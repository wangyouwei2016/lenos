package com.len.generator.metadata.source;

import com.len.generator.metadata.MetaData;
import com.len.generator.metadata.MetaDataHelper;

/**
 * json配置
 * 
 * @param <T>
 */
public class JsonSource<T extends String> extends MetaDataSource {

    protected final T jsonProperties;

    public JsonSource(T jsonProperties) {
        this.jsonProperties = jsonProperties;
    }

    @Override
    public MetaData getMetaData() {
        return MetaDataHelper.jsonConvertToMetaData(jsonProperties);
    }
}
