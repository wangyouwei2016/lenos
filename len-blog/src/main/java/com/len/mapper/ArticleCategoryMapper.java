package com.len.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.len.base.BaseMapper;
import com.len.entity.ArticleCategory;

public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    void delByIds(@Param("ids") List<String> ids);
}