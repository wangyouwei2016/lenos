package com.len.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.core.shiro.Principal;
import com.len.entity.SysPanelOpt;
import com.len.enums.PanelEnum;
import com.len.mapper.PanelOptMapper;
import com.len.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    PanelOptMapper panelOptMapper;

    @Override
    public Map<Integer, List<SysPanelOpt>> panelList() {
        SysPanelOpt sysPanelOpt = new SysPanelOpt();
        String userId = Principal.getPrincipal().getId();
        sysPanelOpt.setPaneloptUserid(userId);
        QueryWrapper<SysPanelOpt> queryWrapper = new QueryWrapper<>(sysPanelOpt);
        List<SysPanelOpt> sysPanelOpts = panelOptMapper.selectList(queryWrapper);
        if (sysPanelOpts.isEmpty()) {
            // 初始化
            initPanelOpt();
            sysPanelOpts = panelOptMapper.selectList(queryWrapper);
        }
        sysPanelOpts.sort(Comparator.comparing(SysPanelOpt::getPaneloptIndex));
        return sysPanelOpts.stream().sorted(Comparator.comparingInt(SysPanelOpt::getPaneloptColumn))
                .collect(Collectors.groupingBy(SysPanelOpt::getPaneloptColumn));
    }

    /**
     * 初始化面板 </br>
     * 默认 </br>
     * column 0 index 0 dynamic;</br>
     * column 1 index 0 shortcuts;</br>
     * column 1 index 1 backlogs</br>
     */
    @Override
    public void initPanelOpt() {
        String userId = Principal.getPrincipal().getId();
        SysPanelOpt sysPanelOpt = new SysPanelOpt();
        sysPanelOpt.setPaneloptColumn(0);
        sysPanelOpt.setPaneloptIndex(0);
        sysPanelOpt.setPaneloptUserid(userId);
        sysPanelOpt.setPaneloptCode(PanelEnum.DYNAMIC.name());
        panelOptMapper.insert(sysPanelOpt);

        sysPanelOpt = new SysPanelOpt();
        sysPanelOpt.setPaneloptColumn(1);
        sysPanelOpt.setPaneloptIndex(0);
        sysPanelOpt.setPaneloptUserid(userId);
        sysPanelOpt.setPaneloptCode(PanelEnum.SHORTCUTS.name());
        panelOptMapper.insert(sysPanelOpt);

        sysPanelOpt = new SysPanelOpt();
        sysPanelOpt.setPaneloptColumn(1);
        sysPanelOpt.setPaneloptIndex(1);
        sysPanelOpt.setPaneloptUserid(userId);
        sysPanelOpt.setPaneloptCode(PanelEnum.BACKLOG.name());
        panelOptMapper.insert(sysPanelOpt);

    }

    @Override
    public void savePanelOpt(List<SysPanelOpt> sysPanelOpts) {
        if (sysPanelOpts == null) {
            return;
        }
        String userId = Principal.getPrincipal().getId();
        for (SysPanelOpt panelOpt : sysPanelOpts) {
            panelOpt.setPaneloptUserid(userId);
            panelOptMapper.updateById(panelOpt);
        }

    }
}
