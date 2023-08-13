package com.len.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.len.core.shiro.Principal;
import com.len.entity.SysPositions;
import com.len.handler.BusinessException;
import com.len.mapper.SysPositionsMapper;
import com.len.response.ResultCode;
import com.len.service.PositionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionsServiceImpl extends ServiceImpl<SysPositionsMapper, SysPositions> implements PositionsService {

    @Autowired
    private SysPositionsMapper positionsMapper;

    @Override
    public IPage<SysPositions> getAllPositionsByPage(Page<SysPositions> page, SysPositions positions,
                                                     OrderItem sort) {
        QueryWrapper<SysPositions> queryWrapper = new QueryWrapper<>();
        if (positions != null) {
            if (StringUtils.isNotBlank(positions.getCode())) {
                queryWrapper.like("code", positions.getCode());
            }
            if (StringUtils.isNotBlank(positions.getName())) {
                queryWrapper.like("name", positions.getName());
            }
        }

        if (sort != null && StringUtils.isNotBlank(sort.getColumn())) {
            page.addOrder(sort);
        }

        return positionsMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public boolean add(SysPositions position) {
        position.setId(RandomUtil.randomUUID());
        position.create(Principal.getPrincipal().getUsername());
        return save(position);
    }

    @Override
    @Transactional
    public boolean update(SysPositions position) {
        if (getById(position.getId()) == null) {
            throw new BusinessException(ResultCode.ARITHMETIC_EXCEPTION.getCode(),
                String.format("岗位id：%s，不存在！", position.getId()));
        }
        position.update(Principal.getPrincipal().getUsername());
        return updateById(position);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        if (getById(id) == null) {
            throw new BusinessException(ResultCode.ARITHMETIC_EXCEPTION.getCode(),
                String.format("岗位id：%s，不存在！", id));
        }
        return removeById(id);
    }

}
