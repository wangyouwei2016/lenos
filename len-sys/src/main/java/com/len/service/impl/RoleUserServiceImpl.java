package com.len.service.impl;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRoleUser;
import com.len.mapper.SysRoleUserMapper;
import com.len.service.RoleUserService;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2017/12/21.
 * @email lenospmiller@gmail.com
 */
@Service
public class RoleUserServiceImpl extends BaseServiceImpl<SysRoleUserMapper, SysRoleUser> implements
        RoleUserService {
}
