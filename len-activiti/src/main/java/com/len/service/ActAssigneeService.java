package com.len.service;

import java.util.List;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.len.base.BaseService;
import com.len.entity.ActAssignee;

/**
 * @author zhuxiaomeng
 * @date 2018/1/23.
 * @email lenospmiller@gmail.com
 */
public interface ActAssigneeService extends BaseService<ActAssignee> {
    int deleteByNodeId(String nodeId);

    public List<ActivityImpl> getActivityList(String deploymentId);

    public List<ActivityImpl> selectAllActivity(List<ActivityImpl> activities);

}
