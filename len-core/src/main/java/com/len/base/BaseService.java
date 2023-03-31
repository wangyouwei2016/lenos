package com.len.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.len.util.ReType;

import java.util.List;

/**
 * 通用service层
 */
public interface BaseService<T> extends IService<T> {


    public ReType show(T t, int page, int limit);

    public ReType getList(T t, int page, int limit);

    public List<T> showAll(T t);
}
