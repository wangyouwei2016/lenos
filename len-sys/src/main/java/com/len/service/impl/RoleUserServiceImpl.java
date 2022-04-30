package com.len.service.impl;

import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRoleUser;
import com.len.mapper.SysRoleUserMapper;
import com.len.service.RoleUserService;

/**
 * 用户角色 service
 */
@Service
public class RoleUserServiceImpl extends BaseServiceImpl<SysRoleUserMapper, SysRoleUser> implements RoleUserService {

}
