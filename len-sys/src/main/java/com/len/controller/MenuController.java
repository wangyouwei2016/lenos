package com.len.controller;

import com.len.dto.MenuDto;
import com.len.entity.SysMenu;
import com.len.response.Result;
import com.len.service.NewMenuService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 */
@RestController
@RequestMapping("/api/menu")
@Api(value = "菜单管理", tags = "菜单业务处理")
public class MenuController {

    private final NewMenuService menuService;

    public MenuController(NewMenuService menuService) {
        this.menuService = menuService;
    }


    @GetMapping
    public Result<List<MenuDto>> getMenuTree() {
        return Result.ok(
            menuService.getMenuTree()
        );
    }


    @GetMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Result<MenuDto> get(@PathVariable("id") String id) {
        return Result.ok(
            menuService.get(id)
        );
    }




}
