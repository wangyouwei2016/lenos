package com.len.generator.metadata.source;

import com.len.generator.metadata.MetaData;

/**
 * 数据来源
 */
public abstract class MetaDataSource {

    /**
     * 获取元数据(入参)
     * 
     * @return 元数据
     */
   public abstract MetaData getMetaData();


    public static class Builder<T> {

        protected MetaDataSource metaDataSource;

        public Builder() {}

        public Builder<T> source(MetaDataSource metaDataSource) {
            this.metaDataSource = metaDataSource;
            return this;
        }

        public MetaData build() {
            return metaDataSource.getMetaData();
        }

    }
}
