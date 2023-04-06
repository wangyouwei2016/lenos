package com.len.service;

import java.util.Date;
import java.util.List;

import com.len.base.BaseService;
import com.len.entity.ArticleDetail;
import com.len.entity.BlogArticle;
import com.len.model.Article;
import com.len.util.LenResponse;

public interface BlogArticleService extends BaseService<BlogArticle> {

    List<Article> indexSelect();

    public LenResponse getDetail(String code);

    public LenResponse detail(String code, String ip);

    List<Article> selectArticle(String code);

    List<Article> selectArticleByTag(String tagCode);

    BlogArticle selectPrevious(Date date);

    BlogArticle selectNext(Date date);

    boolean addArticle(ArticleDetail articleDetail);

    boolean updateArticle(Article article, List<String> categoryIds, List<String> tags);

}
