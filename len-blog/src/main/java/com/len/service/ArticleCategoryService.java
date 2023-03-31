package com.len.service;

import com.len.base.BaseService;
import com.len.entity.ArticleCategory;

import java.util.List;


public interface ArticleCategoryService extends BaseService<ArticleCategory> {

    void delByIds(List<String> ids);
}
