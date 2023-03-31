package com.len.service.impl;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.ArticleTag;
import com.len.mapper.ArticleTagMapper;
import com.len.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

}
