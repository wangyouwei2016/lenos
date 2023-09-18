package com.len.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新主题重定向
 *
 * @author weiyi
 * @createTime 2023年09月18日
 */
@Controller
@RequestMapping("/user")
public class PageUserController {

    @GetMapping("list")
    public String list() {
        return "new/user/list";
    }

    @GetMapping("add")
    public String add() {
        return "new/user/add";
    }

    @GetMapping("update")
    public String update() {
        return "new/user/update";
    }
}
