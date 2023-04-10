package com.len.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.entity.SysLog;
import com.len.mapper.SysLogMapper;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import com.len.util.ReType;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志监控
 */
@Controller
@RequestMapping(value = "/log")
@Slf4j
@Api(value = "日志管理", tags = "操作日志记录")
public class LogController extends BaseController {

    private final SysLogMapper logMapper;

    public LogController(SysLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @GetMapping(value = "showLog")
    public String showMenu() {
        return "/system/log/logList";
    }

    /**
     * 日志监控
     *
     * @param sysLog 条件
     * @param page page
     * @param limit limit
     * @return log list
     */
    @GetMapping(value = "showLogList")
    @ResponseBody
    public ReType showLog(SysLog sysLog, int page, int limit) {
        Page<SysLog> tPage = PageHelper.startPage(page, limit);
        return new ReType(tPage.getTotal(), logMapper.selectListByPage(sysLog));
    }

    /**
     * 删除log
     *
     * @param ids 日志id列表
     * @return success
     */
    @PostMapping(value = "del")
    @ResponseBody
    public LenResponse del(String[] ids) {
        for (String id : ids) {
            logMapper.deleteById(Integer.valueOf(id));
        }
        return succ(MsHelper.getMsg("del.success"));
    }

}
