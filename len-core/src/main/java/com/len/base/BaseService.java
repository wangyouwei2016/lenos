package com.len.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.len.util.ReType;

/**
 * 通用service层
 */
public interface BaseService<T> extends IService<T> {

    public ReType show(T t, int page, int limit);

    public ReType getList(T t, int page, int limit);

    public List<T> showAll(T t);
}
