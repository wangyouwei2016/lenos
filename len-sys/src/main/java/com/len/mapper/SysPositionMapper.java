package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysPositions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysPositionMapper extends BaseMapper<SysPositions> {

    Page<SysPositions> findByPage(Pageable pageable);
    List<SysPositions> getAllPositions();

    SysPositions getPositionById(String id);

    void createPosition(SysPositions position);

    void updatePosition(SysPositions position);

    void deletePosition(String id);
}
