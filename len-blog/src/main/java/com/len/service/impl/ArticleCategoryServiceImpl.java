package com.len.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.ArticleCategory;
import com.len.mapper.ArticleCategoryMapper;
import com.len.service.ArticleCategoryService;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryMapper, ArticleCategory>
    implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public void delByIds(List<String> ids) {
        articleCategoryMapper.delByIds(ids);
    }
}
