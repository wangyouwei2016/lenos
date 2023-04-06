package com.len.service;

import java.util.List;

import com.len.base.BaseService;
import com.len.entity.ArticleCategory;

public interface ArticleCategoryService extends BaseService<ArticleCategory> {

    void delByIds(List<String> ids);
}
