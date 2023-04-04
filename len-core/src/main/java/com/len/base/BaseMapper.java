package com.len.base;

import java.util.List;

import com.len.util.ReType;

/**
 * mapper封装 crud
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    public List<T> selectListByPage(T record);

    public ReType show(T t, int page, int limit);

    public ReType getList(T t, int page, int limit);

    public String showAll(T t);
}
