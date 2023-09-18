package com.len.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/positions")
public class PagePositionsController {

    @GetMapping("list")
    public String list() {
        return "new/positions/list";
    }

    @GetMapping("add")
    public String add() {
        return "new/positions/add";
    }

    @GetMapping("update")
    public String update() {
        return "new/positions/update";
    }

}
