package com.len.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRole;
import com.len.entity.SysRoleMenu;
import com.len.entity.SysRoleUser;
import com.len.exception.MyException;
import com.len.mapper.SysRoleMapper;
import com.len.service.RoleMenuService;
import com.len.service.RoleService;
import com.len.service.RoleUserService;
import com.len.util.BeanUtil;
import com.len.util.LenResponse;
import com.len.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole, String> implements RoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleUserService roleUserService;


    /*@Override
    public int insert(SysRole record) {
        record = super.addValue(record, true);
        return roleMapper.insert(record);
    }*/

    @Override
    public LenResponse addRole(SysRole sysRole, String[] menus) {
        LenResponse j = new LenResponse();
        try {
            roleMapper.insert(sysRole);
            //操作role-menu data

            if (menus != null) {
                for (String menu : menus) {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(sysRole.getId());
                    sysRoleMenu.setMenuId(menu);
                    roleMenuService.save(sysRoleMenu);
                }
            }
            j.setMsg("保存成功");
        } catch (MyException e) {
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }

    @Override
    public LenResponse updateUser(SysRole role, String[] menus) {
        LenResponse jsonUtil = new LenResponse();
        jsonUtil.setFlag(false);
        try {
            SysRole oldRole = roleMapper.selectById(role.getId());
            BeanUtil.copyNotNullBean(role, oldRole);
            roleMapper.updateById(oldRole);

            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(role.getId());
            List<SysRoleMenu> menuList = roleMenuService.selectByCondition(sysRoleMenu);
            for (SysRoleMenu sysRoleMenu1 : menuList) {
                roleMenuService.deleteByPrimaryKey(sysRoleMenu1);
            }
            if (menus != null) {
                for (String menu : menus) {
                    sysRoleMenu.setMenuId(menu);
                    roleMenuService.save(sysRoleMenu);
                }
            }
            jsonUtil.setFlag(true);
            jsonUtil.setMsg("修改成功");
        } catch (MyException e) {
            jsonUtil.setMsg("修改失败");
            e.printStackTrace();
        }
        return jsonUtil;
    }

    @Override
    public LenResponse del(String id) {
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(id);
        LenResponse j = new LenResponse();
        try {
            QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>(sysRoleUser);
            int count = roleUserService.count(wrapper);
            if (count > 0) {
                return LenResponse.error("已分配给用户，删除失败");
            }
            roleMapper.deleteById(id);
            j.setMsg("删除成功");
        } catch (MyException e) {
            j.setMsg("删除失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }
}
