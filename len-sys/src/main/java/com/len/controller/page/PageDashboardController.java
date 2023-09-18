package com.len.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard")
public class PageDashboardController {

    @GetMapping
    public String index(){
        return "new/main/dashboard";
    }
}
