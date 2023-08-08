package com.len.service.impl;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysPositions;
import com.len.mapper.SysPositionMapper;
import com.len.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionsServiceImpl extends BaseServiceImpl<SysPositionMapper, SysPositions> implements PositionsService {

    @Autowired
    private SysPositionMapper positionMapper;

    @Override
    public List<SysPositions> getAllPositions() {
        return positionMapper.getAllPositions();
    }

    @Override
    public SysPositions getPositionById(String id) {
        return positionMapper.getPositionById(id);
    }

    @Override
    public void createPosition(SysPositions position) {
        positionMapper.createPosition(position);
    }

    @Override
    public void updatePosition(SysPositions position) {
        positionMapper.updatePosition(position);
    }

    @Override
    public void deletePosition(String id) {
        positionMapper.deletePosition(id);
    }
}
