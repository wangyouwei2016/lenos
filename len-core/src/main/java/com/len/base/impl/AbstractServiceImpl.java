package com.len.base.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseMapper;
import com.len.base.BaseService;
import com.len.exception.LenException;
import com.len.util.ReType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractServiceImpl<E extends com.baomidou.mybatisplus.core.mapper.BaseMapper, T>
    extends ServiceImpl<BaseMapper<T>, T> implements BaseService<T> {

    /**
     * 公共展示类
     *
     * @param t 实体
     * @param page 页
     * @param limit 行
     * @return
     */
    @Override
    public ReType show(T t, int page, int limit) {
        List<T> tList = null;
        Page<T> tPage = PageHelper.startPage(page, limit);
        try {
            tList = getBaseMapper().selectListByPage(t);
        } catch (LenException e) {
            log.error("class:BaseServiceImpl ->method:show->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }

    @Override
    public List<T> showAll(T t) {
        List<T> tList = null;
        try {
            tList = getBaseMapper().selectListByPage(t);
        } catch (LenException e) {
            log.error("class:BaseServiceImpl ->method:show->message:" + e.getMessage());
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public ReType getList(T t, int page, int limit) {
        List<T> tList = null;
        Page<T> tPage = PageHelper.startPage(page, limit);
        try {
            tList = getBaseMapper().selectListByPage(t);
        } catch (LenException e) {
            log.error("class:BaseServiceImpl ->method:getList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tPage.getPageNum(), tList);
    }
}
