package com.len.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.len.entity.SysPositions;

public interface PositionsService extends IService<SysPositions> {

    SysPositions get(String id);

    IPage<SysPositions> getAllPositionsByPage(Page<SysPositions> page, SysPositions positions, OrderItem sort);

    boolean add(SysPositions position);

    boolean update(SysPositions position);

    boolean delete(String id);
}
