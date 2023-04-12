package com.len.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.model.Article;
import com.len.service.BlogArticleService;
import com.len.util.IpUtil;
import com.len.util.LenResponse;
import com.len.util.ReType;

/**
 * 文章管理（后台）
 *
 * @author JamesZBL
 */

@Controller
@RequestMapping("/article")
public class AdminArticleController {

    @Autowired
    private BlogArticleService articleService;

    @GetMapping("/articleList")
    public String articleListPage() {
        return "articleList";
    }

    /**
     * 获取 文章列表
     *
     * @param code
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/article/list")
    @ResponseBody
    public ReType getList(String code, Integer page, Integer limit) {
        limit = limit > 100 ? 100 : limit;
        Page<Object> startPage = PageHelper.startPage(page, limit);

        List<Article> articles;
        if (!StringUtils.isEmpty(code)) {
            articles = articleService.selectArticle(code);
        } else {
            articles = articleService.indexSelect();
        }

        return new ReType(startPage.getTotal(), startPage.getPageNum(), articles);
    }

    /**
     * 根据code获取文章内容
     *
     * @param code
     * @return
     */
    @GetMapping("/article/getDetail/{code}")
    @ResponseBody
    public LenResponse detail(@PathVariable("code") String code, HttpServletRequest request) {
        String ip = IpUtil.getIp(request);
        return articleService.detail(code, ip);
    }
}
