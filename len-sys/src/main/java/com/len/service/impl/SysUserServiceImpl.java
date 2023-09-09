package com.len.service.impl;

import static com.len.validator.ValidatorUtils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.len.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.base.CurrentMenu;
import com.len.base.CurrentRole;
import com.len.base.CurrentUser;
import com.len.base.impl.BaseServiceImpl;
import com.len.core.shiro.Principal;
import com.len.exception.ServiceException;
import com.len.mapper.SysRoleUserMapper;
import com.len.mapper.SysUserMapper;
import com.len.service.MenuService;
import com.len.service.RoleService;
import com.len.service.RoleUserService;
import com.len.service.SysUserService;
import com.len.util.*;
import com.len.validator.ValidatorUtils;
import com.len.validator.group.AddGroup;

/**
 * 账户 service
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final String ADMIN = "admin";
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleUserService roleUserService;
    @Autowired
    MenuService menuService;

    @Override
    public SysUser login(String username) {
        return sysUserMapper.login(username);
    }


    /**
     * 获取用户列表
     *
     * @param page    分页信息
     * @param sysUser 用户实体
     * @param sort 排序
     * @return 用户列表
     */
    @Override
    public IPage<SysUser> getAllUserByPage(Page<SysUser> page, SysUser sysUser, OrderItem sort) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (sysUser != null) {
            if (StringUtils.isNotBlank(sysUser.getUsername())) {
                queryWrapper.like("username", sysUser.getUsername());
            }
            if (StringUtils.isNotBlank(sysUser.getEmail())) {
                queryWrapper.like("email", sysUser.getEmail());
            }
        }

        if (sort != null && StringUtils.isNotBlank(sort.getColumn())) {
            page.addOrder(sort);
        }

        return sysUserMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser) {
        return sysRoleUserMapper.selectByCondition(sysRoleUser);
    }

    @Override
    public int count() {
        return sysUserMapper.count();
    }

    @Override
    public boolean add(SysUser user, List<String> role) {
        validateEntity(user, AddGroup.class);
        notNull(role, "role.not.null");
        int result = checkUser(user.getUsername());
        if (result > 0) {
            throw new ServiceException(MsHelper.getMsg("user.exists"));
        }
        // 密码加密
        user.setPassword(Md5Util.getMD5(user.getPassword(), user.getUsername()));
        boolean save = save(user);
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(user.getId());
        for (String r : role) {
            sysRoleUser.setRoleId(r);
            roleUserService.save(sysRoleUser);
        }
        return save;
    }

    @Override
    public boolean updateUser(SysUser user, List<String> role) {
        SysUser oldUser = getById(user.getId());
        BeanUtil.copyNotNullBean(user, oldUser);
        updateById(oldUser);

        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(oldUser.getId());
        List<SysRoleUser> keyList = selectByCondition(sysRoleUser);
        for (SysRoleUser currentRoleUser : keyList) {
            QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>(currentRoleUser);
            roleUserService.remove(queryWrapper);
        }

        if (role != null) {
            for (String r : role) {
                sysRoleUser.setRoleId(r);
                roleUserService.save(sysRoleUser);
            }
        }
        updateCurrent(user);
        return true;
    }

    @Override
    public boolean delById(String id, boolean realDel) {
        notEmpty(id, "failed.get.data");
        LenResponse j = new LenResponse();
        SysUser sysUser = sysUserMapper.selectById(id);
        if (ADMIN.equals(sysUser.getUsername())) {
            throw new ServiceException(MsHelper.getMsg("user.del.admin"));
        }
        SysRoleUser roleUser = new SysRoleUser();
        roleUser.setUserId(id);
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>(roleUser);
        int count = roleUserService.count(wrapper);
        if (count > 0) {
            throw new ServiceException(MsHelper.getMsg("user.bind.role"));
        }
        if (!realDel) {
            // 逻辑
            sysUser.setDelFlag(Byte.parseByte("1"));
            sysUserMapper.updateById(sysUser);
        } else {
            // 物理
            sysUserMapper.delById(id);
        }
        return true;
    }

    @Override
    public int checkUser(String username) {
        return sysUserMapper.checkUser(username);
    }

    @Override
    public List<Checkbox> getUserRoleList(String id) {
        List<SysRole> roleList = roleService.list();
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(id);
        List<SysRoleUser> kList = selectByCondition(sysRoleUser);

        List<Checkbox> checkboxList = new ArrayList<>();
        Checkbox checkbox;
        for (SysRole sysRole : roleList) {
            checkbox = new Checkbox();
            checkbox.setId(sysRole.getId());
            checkbox.setName(sysRole.getRoleName());
            for (SysRoleUser sysRoleUser1 : kList) {
                if (sysRoleUser1.getRoleId().equals(sysRole.getId())) {
                    checkbox.setCheck(true);
                    break;
                }
            }
            checkboxList.add(checkbox);
        }
        return checkboxList;
    }

    @Override
    public int rePass(String id, String newPwd) {
        ValidatorUtils.notEmpty(id, "failed.get.data");
        ValidatorUtils.notEmpty(newPwd, "failed.get.data");
        SysUser user = getById(id);
        newPwd = Md5Util.getMD5(newPwd, user.getUsername());
        if (newPwd.equals(user.getPassword())) {
            throw new ServiceException("newpass.not.eq.oldpass");
        }
        user.setPassword(newPwd);
        return sysUserMapper.rePass(user);
    }

    @Override
    public List<SysUser> getUserByRoleId(String roleId) {
        return sysUserMapper.getUserByRoleId(roleId);
    }

    @Override
    public void setMenuAndRoles(String username) {
        SysUser s = new SysUser();
        s.setUsername(username);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>(s);
        s = sysUserMapper.selectOne(userQueryWrapper);
        CurrentUser currentUser = new CurrentUser(s.getId(), s.getUsername(), s.getAge(), s.getEmail(), s.getPhoto());
        Subject subject = Principal.getSubject();
        /*角色权限封装进去*/
        // 根据用户获取菜单
        Session session = subject.getSession();

        List<SysMenu> menuList = menuService.getUserMenu(s.getId());
        JSONArray menuArr = menuService.getMenuJsonByUser(menuList);
        session.setAttribute("menu", menuArr);
        session.setAttribute("menuArr", menuArr.toString());

        List<CurrentMenu> currentMenuList = new ArrayList<>();
        Set<SysRole> roleList = new HashSet<>();
        for (SysMenu m : menuList) {
            CurrentMenu currentMenu = new CurrentMenu();
            BeanUtil.copyNotNullBean(m, currentMenu);
            currentMenuList.add(currentMenu);
            roleList.addAll(m.getRoleList());
        }

        List<CurrentRole> currentRoleList = new ArrayList<>();

        for (SysRole r : roleList) {
            CurrentRole role = new CurrentRole();
            BeanUtil.copyNotNullBean(r, role);
            currentRoleList.add(role);
        }
        currentUser.setCurrentRoleList(currentRoleList);
        currentUser.setCurrentMenuList(currentMenuList);
        session.setAttribute("currentPrincipal", currentUser);
    }

    /**
     * 更新session头像
     */
    @Override
    public void updateCurrent(SysUser sysUser) {
        CurrentUser principal = Principal.getPrincipal();
        if (principal.getId().equals(sysUser.getId())) {
            // 当前用户
            CurrentUser currentUse = Principal.getCurrentUse();
            Session session = Principal.getSession();
            currentUse.setPhoto(sysUser.getPhoto());
            session.setAttribute("currentPrincipal", currentUse);
        }
    }

    @Override
    public boolean updatePerson(SysUser user) {
        SysUser oldUser = getById(user.getId());
        BeanUtil.copyNotNullBean(user, oldUser);
        updateById(oldUser);
        updateCurrent(user);
        return true;
    }
}
