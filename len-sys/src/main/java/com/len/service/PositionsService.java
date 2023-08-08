package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysPositions;

import java.util.List;

public interface PositionsService extends BaseService<SysPositions> {

    List<SysPositions> getAllPositions();

    SysPositions getPositionById(String id);

    void createPosition(SysPositions position);

    void updatePosition(SysPositions position);

    void deletePosition(String id);

}
