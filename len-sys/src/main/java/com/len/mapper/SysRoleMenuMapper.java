package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单 mapper
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据条件查询角色菜单
     * 
     * @param sysRoleMenu
     * @return
     */
    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

    /**
     * count
     * 
     * @param sysRoleMenu
     * @return
     */
    int selectCountByCondition(SysRoleMenu sysRoleMenu);
}