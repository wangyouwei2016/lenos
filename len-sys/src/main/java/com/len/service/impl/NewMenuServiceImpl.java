package com.len.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.len.dto.MenuDto;
import com.len.entity.SysMenu;
import com.len.mapper.SysMenuMapper;
import com.len.service.NewMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements NewMenuService {

    @Autowired
    SysMenuMapper menuMapper;
    /**
     * 获取菜单列表
     */
    public List<MenuDto> getMenuTree() {
        List<SysMenu> menuList = list();
        List<MenuDto> menuTrees = convertToTree(menuList);
        return menuTrees;
    }

    /**
     * 转换为树形
     *
     * @param menuList 菜单列表
     */
    private List<MenuDto> convertToTree(List<SysMenu> menuList) {
        Map<String, MenuDto> menuMap = new HashMap<>();
        for (SysMenu menu : menuList) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(menu.getId());
            menuDto.setName(menu.getName());
            menuDto.setPId(menu.getPId());
            menuMap.put(menu.getId(), menuDto);
        }

        List<MenuDto> tree = new ArrayList<>();
        for (SysMenu menu : menuList) {
            MenuDto menuDto = menuMap.get(menu.getId());
            if (StringUtils.isEmpty(menu.getPId())) {
                tree.add(menuDto);
            } else {
                MenuDto parentMenu = menuMap.get(menu.getPId());
                if (parentMenu != null) {
                    parentMenu.addChild(menuDto);
                }
            }
        }
        return tree;
    }


    @Override
    public MenuDto get(String id) {
        SysMenu menu = menuMapper.selectById(id);
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setPId(menu.getPId());
        return menuDto;
    }

}
