package com.len.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.UserLeave;
import com.len.mapper.UserLeaveMapper;
import com.len.service.UserLeaveService;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email lenospmiller@gmail.com
 */
@Service
public class UserLeaveServiceImpl extends BaseServiceImpl<UserLeaveMapper, UserLeave> implements UserLeaveService {

    @Autowired
    UserLeaveMapper userLeaveMapper;

    @Override
    public List<UserLeave> selectListByPage(UserLeave record) {
        return userLeaveMapper.selectListByPage(record);
    }
}
