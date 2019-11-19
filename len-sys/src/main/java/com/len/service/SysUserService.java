package com.len.service;


import com.len.base.BaseService;
import com.len.entity.SysRoleUser;
import com.len.entity.SysUser;
import com.len.util.Checkbox;
import com.len.util.LenResponse;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email 154040976@qq.com
 */
public interface SysUserService extends BaseService<SysUser, String> {

    SysUser login(String username);

    /**
     * 新增
     *
     * @param user
     * @return
     */
    int add(SysUser user);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    LenResponse delById(String id, boolean flag);

    int checkUser(String username);


    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    List<Checkbox> getUserRoleByJson(String id);

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    int rePass(SysUser user);


    List<SysUser> getUserByRoleId(String roleId);

    void setMenuAndRoles(String username);

    void updateCurrent(SysUser user);
}
