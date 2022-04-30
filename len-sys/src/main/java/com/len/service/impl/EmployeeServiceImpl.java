package com.len.service.impl;

import org.springframework.stereotype.Service;

import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysEmployee;
import com.len.mapper.SysEmployeeMapper;
import com.len.service.EmployeeService;

/**
 * 员工 service
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<SysEmployeeMapper, SysEmployee> implements EmployeeService {

}
