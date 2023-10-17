package com.len.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.len.dto.MenuDto;
import com.len.entity.SysMenu;

import java.util.List;

public interface NewMenuService extends IService<SysMenu> {

    List<MenuDto> getMenuTree();

    MenuDto get(String id);
}
