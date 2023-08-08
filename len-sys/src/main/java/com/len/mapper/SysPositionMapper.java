package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysPositions;

import java.util.List;

public interface SysPositionMapper extends BaseMapper<SysPositions> {

    List<SysPositions> getAllPositions();

    SysPositions getPositionById(String id);

    void createPosition(SysPositions position);

    void updatePosition(SysPositions position);

    void deletePosition(String id);
}
