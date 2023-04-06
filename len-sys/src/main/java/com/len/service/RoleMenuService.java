package com.len.service;

import java.util.List;

import com.len.base.BaseService;
import com.len.entity.SysRoleMenu;

public interface RoleMenuService extends BaseService<SysRoleMenu> {

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

    int selectCountByCondition(SysRoleMenu sysRoleMenu);

    int deleteByPrimaryKey(SysRoleMenu sysRoleMenu);
}
