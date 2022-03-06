package com.len.service;

import com.alibaba.fastjson.JSONArray;
import com.len.base.BaseService;
import com.len.entity.SysMenu;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/12.
 * @email lenospmiller@gmail.com
 */
public interface MenuService extends BaseService<SysMenu> {

  List<SysMenu> getMenuNotSuper();

  List<SysMenu> getMenuChildren(String id);

  public JSONArray getMenuJsonList();

  List<SysMenu> getMenuChildrenAll(String id);

  public JSONArray getTreeUtil(String roleId);

  List<SysMenu> getUserMenu(String id);

  public JSONArray getMenuJsonByUser(List<SysMenu> menuList);

  public boolean del(String id);

}
