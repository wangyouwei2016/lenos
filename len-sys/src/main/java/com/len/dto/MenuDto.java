package com.len.dto;

import com.len.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto {

    private String id;
    private String name;
    private String pId;

    List<MenuDto> children;

    public void addChild(MenuDto menuDto) {
        if(children==null){
            children = new ArrayList<>();
        }
        children.add(menuDto);
    }
}
