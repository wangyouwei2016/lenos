package com.len.controller;

import com.len.base.BaseController;
import com.len.core.annotation.Log;
import com.len.core.annotation.Log.LOG_TYPE;
import com.len.core.quartz.JobTask;
import com.len.entity.SysJob;
import com.len.exception.MyException;
import com.len.service.JobService;
import com.len.util.LenResponse;
import com.len.util.ReType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhuxiaomeng
 * @date 2018/1/6.
 * @email 154040976@qq.com
 * <p>
 * 定时任务 controller
 */
@Controller
@RequestMapping("/job")
@Api(value = "定时任务",description="定时任务")
public class JobController extends BaseController<SysJob> {

    @Autowired
    JobService jobService;

    @Autowired
    JobTask jobTask;

    @GetMapping(value = "showJob")
    @RequiresPermissions("job:show")
    public String showUser(Model model) {
        return "/system/job/jobList";
    }

    @GetMapping(value = "showJobList")
    @ResponseBody
    @RequiresPermissions("job:show")
    public ReType showUser(Model model, SysJob job, String page, String limit) {
        return jobService.show(job, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @GetMapping(value = "showAddJob")
    public String addJob(Model model) {
        return "/system/job/add-job";
    }

    @ApiOperation(value = "/addJob", httpMethod = "POST", notes = "添加任务类")
    @Log(desc = "添加任务")
    @PostMapping(value = "addJob")
    @ResponseBody
    public LenResponse addJob(SysJob job) {
        LenResponse j = new LenResponse();
        String msg = "保存成功";
        job.setStatus(false);
        try {
            jobService.save(job);
        } catch (MyException e) {
            msg = "保存失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

    @GetMapping(value = "updateJob")
    public String updateJob(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            SysJob job = jobService.getById(id);
            model.addAttribute("job", job);
        }
        model.addAttribute("detail", detail);
        return "system/job/update-job";
    }


    @ApiOperation(value = "/updateJob", httpMethod = "POST", notes = "更新任务")
    @Log(desc = "更新任务", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "updateJob")
    @ResponseBody
    public LenResponse updateJob(SysJob job) {
        LenResponse lenResponse = new LenResponse();
        lenResponse.setFlag(false);
        if (job == null) {
            lenResponse.setMsg("获取数据失败");
            return lenResponse;
        }
        if (jobTask.checkJob(job)) {
            lenResponse.setMsg("已经启动任务无法更新,请停止后更新");
            return lenResponse;
        }
        if (jobService.updateJob(job)) {
            lenResponse.setFlag(true);
            lenResponse.setData("更新成功");
        } else {
            lenResponse.setData("更新失败");
        }
        return lenResponse;
    }

    @Log(desc = "删除任务", type = LOG_TYPE.DEL)
    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除任务")
    @PostMapping(value = "del")
    @ResponseBody
    @RequiresPermissions("job:del")
    public LenResponse del(String id) {
        return jobService.del(id);
    }


    @Log(desc = "启动任务")
    @PostMapping(value = "startJob")
    @ResponseBody
    @RequiresPermissions("job:start")
    public LenResponse startJob(String id) {
        LenResponse j = new LenResponse();
        String msg = null;
        if (StringUtils.isEmpty(id)) {
            j.setMsg("获取数据失败");
            j.setFlag(false);
            return j;
        }
        if (jobService.startJob(id)) {
            msg = "启动成功";
        } else {
            msg = "启动失败";
        }
        j.setMsg(msg);
        return j;
    }

    @Log(desc = "停止任务")
    @PostMapping(value = "endJob")
    @ResponseBody
    @RequiresPermissions("job:end")
    public LenResponse endJob(String id) {
        LenResponse j = new LenResponse();
        String msg;
        if (StringUtils.isEmpty(id)) {
            j.setMsg("获取数据失败");
            j.setFlag(false);
            return j;
        }
        if (jobService.stopJob(id)) {
            msg = "停止成功";
        } else {
            msg = "停止失败";
        }
        j.setMsg(msg);
        return j;
    }

}
