package com.len.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class PageMenuController {

    @GetMapping("list")
    public String list() {
        return "new/menu/list";
    }

    @GetMapping("add")
    public String add() {
        return "new/menu/add";
    }

    @GetMapping("update")
    public String update() {
        return "new/menu/update";
    }

}
