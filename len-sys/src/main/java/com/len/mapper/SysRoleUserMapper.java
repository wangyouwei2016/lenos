package com.len.mapper;

import java.util.List;

import com.len.base.BaseMapper;
import com.len.entity.SysRoleUser;

/**
 * 账户角色 mapper
 */
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    int selectCountByCondition(SysRoleUser sysRoleUser);
}