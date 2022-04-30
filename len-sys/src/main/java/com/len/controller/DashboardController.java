package com.len.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.len.base.BaseController;
import com.len.service.MenuService;
import com.len.util.LenResponse;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/dashboard")
@Api(value = "仪表盘", tags = "仪表盘")
public class DashboardController extends BaseController {

    private final MenuService menuService;

    public DashboardController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public String main() {
        return "/main/dashboard";
    }

    @GetMapping("/shortCuts")
    @ResponseBody
    public LenResponse getShortCuts() {
        return succ(menuService.getShortCuts());
    }

    /**
     * 添加快捷菜单
     * 
     * @param code 菜单code
     * @return
     */
    @PostMapping("/shortCuts/add")
    @ResponseBody
    public LenResponse addShortCuts(String code) {
        return succ(menuService.addShortCuts(code));
    }

    /**
     * 根据编码删除菜单
     * 
     * @param code 菜单code
     * @return
     */
    @PostMapping("/shortCuts/del/{code}")
    @ResponseBody
    public LenResponse delShortCuts(@PathVariable("code") String code) {
        menuService.delShortcuts(code);
        return succ();
    }

    /**
     * 根据编码删除菜单
     *
     * @param shortCutsIds 快捷菜单id arr
     * @return
     */
    @PostMapping("/shortCuts/sort")
    @ResponseBody
    public LenResponse sortShortCuts(String shortCutsIds) {
        String[] shortCutsList = shortCutsIds.split(",", -1);
        menuService.sortShortCuts(Arrays.asList(shortCutsList));
        return succ();
    }

}
