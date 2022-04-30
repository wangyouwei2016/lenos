package com.len.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.len.base.BaseMapper;
import com.len.entity.SysMenu;
import com.len.entity.SysShortcuts;

/**
 * 菜单 mapper
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取元节点
     */
    List<SysMenu> getMenuNotSuper();

    /**
     * 获取子节点
     *
     * @return
     */
    List<SysMenu> getMenuChildren(String id);

    List<SysMenu> getMenuChildrenAll(String id);

    /**
     * 根据用户获取所有菜单
     *
     * @param id
     * @return
     */
    List<SysMenu> getUserMenu(@Param("id") String id);

    /**
     * 获取用户快捷菜单列表
     * 
     * @param userId 用户id
     * @return
     */
    List<SysMenu> getUserShortCuts(@Param("userId") String userId);

    /**
     * 添加快捷方式
     * 
     * @param shortcuts
     */
    void addShortcuts(SysShortcuts shortcuts);

}