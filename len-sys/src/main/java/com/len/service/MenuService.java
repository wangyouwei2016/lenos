package com.len.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.len.base.BaseService;
import com.len.entity.SysMenu;


public interface MenuService extends BaseService<SysMenu> {

    /**
     * 添加菜单
     * 
     * @param menu
     * @return
     */
    String addMenu(SysMenu menu);

    List<SysMenu> getMenuNotSuper();

    List<SysMenu> getMenuChildren(String id);

    JSONArray getMenuJsonList();

    List<SysMenu> getMenuChildrenAll(String id);

    JSONArray getTreeUtil(String roleId);

    List<SysMenu> getUserMenu(String id);

    JSONArray getMenuJsonByUser(List<SysMenu> menuList);

    boolean del(String id);

    /**
     * 快捷菜单 获取用户列表
     * 
     * @return
     */
    List<SysMenu> getShortCuts();

    /**
     * 快捷菜单 添加
     * 
     * @param code 菜单编码
     * @return
     */
    SysMenu addShortCuts(String code);

    /**
     * 快捷菜单 根据当前员工删除
     * 
     * @param code 菜单编码
     */
    void delShortcuts(String code);

    /**
     * 快捷菜单 重排序
     * 
     * @param codeList
     */
    void sortShortCuts(List<String> codeList);

}
