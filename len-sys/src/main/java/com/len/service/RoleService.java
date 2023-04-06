package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysRole;

public interface RoleService extends BaseService<SysRole> {

    void addRole(SysRole sysRole, String[] menus);

    void updateUser(SysRole role, String[] menus);

    void del(String id);
}
