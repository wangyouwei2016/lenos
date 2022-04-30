package com.len.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.core.shiro.Principal;
import com.len.entity.SysMenu;
import com.len.entity.SysRoleMenu;
import com.len.entity.SysShortcuts;
import com.len.exception.LenException;
import com.len.exception.ServiceException;
import com.len.mapper.ShortcutsMapper;
import com.len.mapper.SysMenuMapper;
import com.len.mapper.SysRoleMenuMapper;
import com.len.service.MenuService;
import com.len.service.RoleMenuService;
import com.len.util.MsHelper;
import com.len.util.TreeUtil;
import com.len.util.UuidUtil;
import com.len.validator.ValidatorUtils;

import cn.hutool.core.util.RandomUtil;

/**
 * 菜单 service
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements MenuService {

    static SysMenuMapper menuDao;

    @Autowired
    private ShortcutsMapper shortcutsMapper;

    public MenuServiceImpl(SysMenuMapper menuDao) {
        MenuServiceImpl.menuDao = menuDao;
    }

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public String addMenu(SysMenu sysMenu) {
        if (StringUtils.isEmpty(sysMenu.getPId())) {
            sysMenu.setPId(null);
        }
        if (StringUtils.isEmpty(sysMenu.getUrl())) {
            sysMenu.setUrl(null);
        }
        if (StringUtils.isEmpty(sysMenu.getPermission())) {
            sysMenu.setPermission(null);
        }
        if (sysMenu.getMenuType() == 2) {
            sysMenu.setMenuType((byte)0);
        }
        sysMenu.setCode(randomCode());
        save(sysMenu);
        return "";
    }

    @Override
    public List<SysMenu> getMenuNotSuper() {
        return menuDao.getMenuNotSuper();
    }

    @Override
    public List<SysMenu> getMenuChildren(String id) {
        return menuDao.getMenuChildren(id);
    }

    public SysMenu child(SysMenu sysMenu, List<SysMenu> sysMenus, Integer num) {
        List<SysMenu> childSysMenu =
            sysMenus.stream().filter(s -> s.getPId().equals(sysMenu.getId())).collect(Collectors.toList());
        sysMenus.removeAll(childSysMenu);
        for (SysMenu menu : childSysMenu) {
            ++num;
            child(menu, sysMenus, num);
            sysMenu.addChild(menu);
        }
        return sysMenu;
    }

    @Override
    public JSONArray getMenuJsonList() {
        List<SysMenu> sysMenus = list();
        List<SysMenu> supers =
            sysMenus.stream().filter(sysMenu -> StringUtils.isEmpty(sysMenu.getPId())).collect(Collectors.toList());
        sysMenus.removeAll(supers);
        supers.sort(Comparator.comparingInt(SysMenu::getOrderNum));
        JSONArray jsonArr = new JSONArray();
        for (SysMenu sysMenu : supers) {
            SysMenu child = child(sysMenu, sysMenus, 0);
            jsonArr.add(child);
        }
        return jsonArr;
    }

    @Override
    public JSONArray getMenuJsonByUser(List<SysMenu> menuList) {
        JSONArray jsonArr = new JSONArray();
        menuList.sort((o1, o2) -> {
            if (o1.getOrderNum() == null || o2.getOrderNum() == null) {
                return -1;
            }
            if (o1.getOrderNum() > o2.getOrderNum()) {
                return 1;
            }
            if (o1.getOrderNum().equals(o2.getOrderNum())) {
                return 0;
            }
            return -1;
        });
        for (SysMenu menu : menuList) {
            if (StringUtils.isEmpty(menu.getPId())) {
                SysMenu sysMenu = getChilds(menu, menuList);
                jsonArr.add(sysMenu);
            }
        }
        return jsonArr;
    }

    @Override
    public boolean del(String id) {
        ValidatorUtils.notEmpty(id, "failed.get.data");
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setMenuId(id);
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>(sysRoleMenu);
        int count = roleMenuService.count(sysRoleMenuQueryWrapper);
        // 存在角色绑定不能删除
        if (count > 0) {
            throw new ServiceException(MsHelper.getMsg("menu.bind.role"));
        }
        // 存在下级菜单 不能解除
        SysMenu sysMenu = new SysMenu();
        sysMenu.setPId(id);
        QueryWrapper<SysMenu> sysRoleMenuQueryWrapperTwo = new QueryWrapper<>(sysMenu);
        if (menuDao.selectCount(sysRoleMenuQueryWrapperTwo) > 0) {
            throw new ServiceException(MsHelper.getMsg("menu.exists.children"));
        }
        return removeById(id);
    }

    @Override
    public List<SysMenu> getShortCuts() {
        return menuDao.getUserShortCuts(Principal.getPrincipal().getId());
    }

    @Override
    public SysMenu addShortCuts(String code) {
        SysMenu menu = getMenuBNyCode(code);
        SysShortcuts shortcuts = new SysShortcuts();
        shortcuts.setId(UuidUtil.getUuid());
        shortcuts.setShortcutsMenuid(menu.getId());
        String userId = Principal.getPrincipal().getId();
        shortcuts.setShortcutsUserid(userId);
        shortcuts.setCreateBy(userId);
        shortcuts.setCreateDate(new Date());
        shortcuts.setShortcutsNum(8);
        menuDao.addShortcuts(shortcuts);
        return menu;
    }

    private SysMenu getMenuBNyCode(String code) {
        SysMenu menu = new SysMenu();
        menu.setCode(code);
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>(menu);
        menu = menuDao.selectOne(queryWrapper);
        if (menu == null) {
            throw new LenException("菜单不存在！");
        }
        return menu;
    }

    @Override
    public void delShortcuts(String code) {
        SysMenu menu = getMenuBNyCode(code);
        SysShortcuts shortcuts = new SysShortcuts();
        shortcuts.setShortcutsUserid(Principal.getPrincipal().getId());
        shortcuts.setShortcutsMenuid(menu.getId());
        QueryWrapper<SysShortcuts> queryWrapper = new QueryWrapper<>(shortcuts);
        shortcutsMapper.delete(queryWrapper);
    }

    @Override
    public void sortShortCuts(List<String> shortCutsIds) {
        int i = 0;
        for (String shortcutsId : shortCutsIds) {
            SysShortcuts shortcuts = new SysShortcuts();
            shortcuts.setId(shortcutsId);
            shortcuts.setShortcutsNum(i);
            shortcutsMapper.updateById(shortcuts);
            i++;
        }

    }

    public SysMenu getChilds(SysMenu menu, List<SysMenu> menuList) {
        for (SysMenu menus : menuList) {
            if (menu.getId().equals(menus.getPId()) && menus.getMenuType() == 0) {
                SysMenu m = getChilds(menus, menuList);
                menu.addChild(m);
            }
        }
        return menu;
    }

    @Override
    public List<SysMenu> getMenuChildrenAll(String id) {
        return menuDao.getMenuChildrenAll(id);
    }

    @Override
    public JSONArray getTreeUtil(String roleId) {
        TreeUtil treeUtil;
        List<SysMenu> sysMenus = list();
        List<SysMenu> supers =
            sysMenus.stream().filter(sysMenu -> StringUtils.isEmpty(sysMenu.getPId())).collect(Collectors.toList());
        sysMenus.removeAll(supers);
        supers.sort(Comparator.comparingInt(SysMenu::getOrderNum));
        JSONArray jsonArr = new JSONArray();
        for (SysMenu sysMenu : supers) {
            treeUtil = getChildByTree(sysMenu, sysMenus, 0, null, roleId);
            jsonArr.add(treeUtil);
        }
        return jsonArr;

    }

    @Override
    public List<SysMenu> getUserMenu(String id) {
        return menuDao.getUserMenu(id);
    }

    private TreeUtil getChildByTree(SysMenu sysMenu, List<SysMenu> sysMenus, int layer, String pId, String roleId) {
        layer++;
        List<SysMenu> childSysMenu =
            sysMenus.stream().filter(s -> s.getPId().equals(sysMenu.getId())).collect(Collectors.toList());
        sysMenus.removeAll(childSysMenu);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.setId(sysMenu.getId());
        treeUtil.setName(sysMenu.getName());
        treeUtil.setLayer(layer);
        treeUtil.setPId(pId);
        /*判断是否存在*/
        if (!StringUtils.isEmpty(roleId)) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(sysMenu.getId());
            sysRoleMenu.setRoleId(roleId);
            int count = roleMenuMapper.selectCountByCondition(sysRoleMenu);
            if (count > 0) {
                treeUtil.setChecked(true);
            }
        }
        for (SysMenu menu : childSysMenu) {
            TreeUtil m = getChildByTree(menu, sysMenus, layer, menu.getId(), roleId);
            treeUtil.getChildren().add(m);
        }
        return treeUtil;
    }

    /**
     * 获取菜单编码
     * 
     * @return 6位数 数字随机编码
     */
    public static String randomCode() {
        SysMenu sysMenu = new SysMenu();
        boolean exists = true;
        String code = null;
        while (exists) {
            code = RandomUtil.randomNumbers(4);
            sysMenu.setCode(code);
            QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>(sysMenu);
            exists = menuDao.selectCount(queryWrapper) > 0;
        }
        return code;
    }
}
