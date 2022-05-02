package com.len.service;

import java.util.List;
import java.util.Map;

import com.len.entity.SysPanelOpt;

public interface DashboardService {

    /**
     * 获取个人panel配置列表
     * 
     * @return key 列， value 每列面板
     */
    Map<Integer, List<SysPanelOpt>> panelList();

    /**
     * 初始化个人面板
     */
    void initPanelOpt();

    /**
     * 保存 个人面板顺序配置
     *
     * @param sysPanelOpts
     */
    void savePanelOpt(List<SysPanelOpt> sysPanelOpts);
}
