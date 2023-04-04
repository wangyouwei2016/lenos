package com.len.service;

import java.util.List;

import com.len.base.BaseService;
import com.len.entity.UserLeave;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email lenospmiller@gmail.com
 */
public interface UserLeaveService extends BaseService<UserLeave> {

    public List<UserLeave> selectListByPage(UserLeave record);
}
