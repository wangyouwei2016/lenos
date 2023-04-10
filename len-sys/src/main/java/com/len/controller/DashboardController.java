package com.len.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.len.base.BaseController;
import com.len.entity.SysPanelOpt;
import com.len.service.DashboardService;
import com.len.service.MenuService;
import com.len.util.LenResponse;

import io.swagger.annotations.Api;

/**
 * 仪表盘
 */
@Controller
@RequestMapping("/dashboard")
@Api(value = "仪表盘", tags = "仪表盘")
public class DashboardController extends BaseController {

    private final MenuService menuService;

    private final DashboardService dashboardService;

    public DashboardController(MenuService menuService, DashboardService dashboardService) {
        this.menuService = menuService;
        this.dashboardService = dashboardService;
    }

    /**
     * 面板列表
     * 
     * @return
     */
    @GetMapping("/panel/list")
    @ResponseBody
    public LenResponse paneList() {
        return succ(dashboardService.panelList());
    }

    /**
     * 保存个人面板顺序配置
     *
     * @param panelOpts
     * @return
     */
    @PostMapping("/panel/save")
    @ResponseBody
    public LenResponse savePanel(@RequestBody List<SysPanelOpt> panelOpts) {
        dashboardService.savePanelOpt(panelOpts);
        return succ();
    }

    /**
     * 展示面板
     * 
     * @return
     */
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
