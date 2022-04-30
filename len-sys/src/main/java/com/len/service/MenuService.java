package com.len.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.len.base.BaseService;
import com.len.entity.SysMenu;

/**
 * @author zhuxiaomeng
 * @date 2017/12/12.
 * @email lenospmiller@gmail.com
 */
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
     * 获取用户快捷菜单列表
     * 
     * @return
     */
    List<SysMenu> getShortCuts();

    /**
     * 添加快捷菜单
     * 
     * @param code 菜单编码
     * @return
     */
    SysMenu addShortCuts(String code);

    /**
     * 删除当前员工快捷菜单
     * 
     * @param code 菜单编码
     */
    void delShortcuts(String code);

}
