package com.len.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.len.base.BaseController;
import com.len.entity.BlogTag;
import com.len.service.BlogArticleService;
import com.len.service.BlogTagService;

/**
 * <p>
 * 标签
 */
@CrossOrigin
@RestController
@RequestMapping("/blog")
public class TagController extends BaseController {

    private static String[] color = {"primary", "success", "error", "warning"};
    @Autowired
    private BlogTagService blogLabelService;
    @Autowired
    private BlogArticleService articleService;

    /**
     * 获取标签 后期增加规则 颜色后期处理成浅色 好看
     *
     * @return
     */
    @GetMapping("/getTag")
    public JSONArray label() {
        List<BlogTag> blogLabels = blogLabelService.list();
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(blogLabels));
        int i = 0;
        JSONObject object;
        for (Object o : array) {
            i = i == 4 ? 0 : i;
            object = (JSONObject)o;
            object.put("color", color[i]);
            object.remove("id");
            i++;
        }
        return array;
    }
}
