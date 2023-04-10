package com.len.core.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.len.base.CurrentMenu;
import com.len.base.CurrentRole;
import com.len.base.CurrentUser;
import com.len.entity.SysUser;
import com.len.menu.LoginType;
import com.len.service.SysUserService;
import com.len.util.BeanUtil;
import com.len.util.JWTUtil;

/**
 * 普通登录 realm
 */
@Service
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    /**
     * 启动初始化
     */
    @Override
    protected void onInit() {
        super.onInit();
    }

    /**
     * 获取授权对象
     *
     * @param principalCollection 当前用户主体信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        CurrentUser user = (CurrentUser)principalCollection.getPrimaryPrincipal();
        Set<String> realmNames = principalCollection.getRealmNames();
        List<String> realmNameList = new ArrayList<>(realmNames);
        if (LoginType.BLOG.toString().equals(realmNameList.get(0))) {
            String[] roles = JWTUtil.getRoles(user.getUsername());
            assert roles != null;
            for (String role : roles) {
                info.addRole(role);
            }
        } else {
            // 根据用户获取角色 根据角色获取所有按钮权限
            CurrentUser cUser = (CurrentUser)Principal.getSession().getAttribute("currentPrincipal");
            for (CurrentRole cRole : cUser.getCurrentRoleList()) {
                info.addRole(cRole.getId());
            }
            for (CurrentMenu cMenu : cUser.getCurrentMenuList()) {
                if (!StringUtils.isEmpty(cMenu.getPermission())) {
                    info.addStringPermission(cMenu.getPermission());
                }
            }
        }

        return info;
    }

    /**
     * 获取认证
     *
     * @param authenticationToken 身份验证令牌
     * @return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        SysUser s = null;
        try {
            s = userService.login(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s == null) {
            throw new UnknownAccountException("账户密码不正确");
        }
        CurrentUser user = new CurrentUser();
        BeanUtil.copyNotNullBean(s, user);
        user.setPassword(null);
        userService.setMenuAndRoles(username);
        ByteSource byteSource = ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(user, s.getPassword(), byteSource, getName());
    }
}
