/**
 * Copyright 2018 lenos
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.len.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.HMProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.base.CurrentUser;
import com.len.core.shiro.Principal;
import com.len.entity.BaseTask;
import com.len.entity.LeaveOpinion;
import com.len.entity.SysRoleUser;
import com.len.entity.UserLeave;
import com.len.exception.LenException;
import com.len.service.RoleUserService;
import com.len.service.UserLeaveService;
import com.len.util.*;

/**
 * 请假流程 控制器
 * 
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email lenospmiller@gmail.com
 *        <p>
 *        请假流程示例
 *        </p>
 */
@Controller
@RequestMapping("/leave")
public class UserLeaveController extends BaseController {

    /**
     * 请假服务层
     */
    private final UserLeaveService leaveService;

    /**
     * activiti的运行时服务注入，用于查询实例等操作
     */
    private final RuntimeService runtimeService;

    /**
     * activiti的任务服务注入，用于查询任务相关变量
     */
    private final TaskService taskService;

    /**
     * activiti的仓库服务注入，用于获取流程详细定义
     */
    private final RepositoryService repositoryService;

    /**
     * activiti的流程引擎服务注入，用于获取引擎配置
     */
    private final ProcessEngineFactoryBean processEngine;

    /**
     * 用户与角色服务层，获取当前用户的角色列表
     */
    private final RoleUserService roleUserService;

    /**
     * activiti的历史流程/任务服务注入，用于查询历史数据变量等信息
     */
    private final HistoryService historyService;

    /**
     * 请假列表 变量名
     */
    private static final String LEAVE_OPINION_LIST = "leaveOpinionList";

    /**
     * 任务基础 变量名
     */
    private static final String BASE_TASK = "baseTask";

    public UserLeaveController(UserLeaveService leaveService, RuntimeService runtimeService, TaskService taskService,
        RepositoryService repositoryService, ProcessEngineFactoryBean processEngine, RoleUserService roleUserService,
        HistoryService historyService) {
        this.leaveService = leaveService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
        this.roleUserService = roleUserService;
        this.historyService = historyService;
    }

    /**
     * 请假流程主页面
     * 
     * @return 视图地址
     */
    @GetMapping(value = "showLeave")
    public String showUser() {
        return "/act/leave/leaveList";
    }

    /**
     * 分页查询请假列表数据
     * 
     * @param userLeave 查询条件包装
     * @param page 页码
     * @param limit 条数
     * @return 响应结果(json)
     */
    @GetMapping(value = "showLeaveList")
    @ResponseBody
    public ReType showLeaveList(UserLeave userLeave, String page, String limit) {
        // 获取当前用户id
        String userId = CommonUtil.getUser().getId();
        userLeave.setUserId(userId);
        List<UserLeave> tList = null;
        // 分页
        Page<UserLeave> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            // 查询请假列表
            tList = leaveService.selectListByPage(userLeave);
            // 回显任务名
            for (UserLeave leave : tList) {
                ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(leave.getProcessInstanceId()).singleResult();
                // 保证运行ing
                if (instance != null) {
                    Task task = this.taskService.createTaskQuery().processInstanceId(leave.getProcessInstanceId())
                        .singleResult();
                    leave.setTaskName(task.getName());
                }
            }
        } catch (LenException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }

    /**
     * 根据 执行对象id获取审批信息
     *
     * @param model 视图
     * @param processId 流程id
     * @return 流程详情页
     */
    @GetMapping("leaveDetail")
    public String leaveDetail(Model model, String processId) {
        // 根据流程id查询
        ProcessInstance instance =
            runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        List<LeaveOpinion> leaveList = null;
        // 保证运行ing
        if (instance != null) {
            // 获取当前任务
            Task task = this.taskService.createTaskQuery().processInstanceId(processId).singleResult();
            Map<String, Object> variables = taskService.getVariables(task.getId());
            Object o = variables.get(LEAVE_OPINION_LIST);
            if (Objects.nonNull(o)) {
                /*获取历史审核信息*/
                leaveList = CastClassUtils.castList(o, LeaveOpinion.class);
            }
        } else {
            // 未运行，从历史详情信息中获取
            leaveList = new ArrayList<>();
            List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processId).list();
            HistoricVariableUpdate variable;
            for (HistoricDetail historicDetail : list) {
                variable = (HistoricVariableUpdate)historicDetail;
                if (LEAVE_OPINION_LIST.equals(variable.getVariableName())) {
                    leaveList.clear();
                    leaveList.addAll(CastClassUtils.castList(variable.getValue(), LeaveOpinion.class));
                }
            }
        }
        // 传参
        model.addAttribute("leaveDetail", JSON.toJSONString(leaveList));
        return "/act/leave/leaveDetail";
    }

    /**
     * 去新增请假页面
     * 
     * @return 新增页面
     */
    @GetMapping("addLeave")
    public String addLeave() {
        return "/act/leave/add-leave";
    }

    /**
     * 去请假编辑页面
     * 
     * @param model 模型对象
     * @param taskId 任务ID
     * @return 编辑页面
     */
    @GetMapping("updateLeave/{taskId}")
    public String updateLeave(Model model, @PathVariable String taskId) {
        Map<String, Object> variables = taskService.getVariables(taskId);
        BaseTask baseTask = (BaseTask)variables.get(BASE_TASK);
        // 根据任务id获取请假详情
        UserLeave leave = leaveService.getById(baseTask.getId());
        // 传参
        model.addAttribute("leave", leave);
        model.addAttribute("taskId", taskId);
        return "/act/leave/update-leave";
    }

    /**
     * 编辑请假信息
     * 
     * @param leave 请假对象
     * @param taskId 任务id
     * @param id 请假id
     * @param flag true：重新提交请假 | false：取消请假
     * @return 响应操作结果
     */
    @PostMapping("updateLeave/updateLeave/{taskId}/{id}/{flag}")
    @ResponseBody
    public LenResponse updateLeave(UserLeave leave, @PathVariable String taskId, @PathVariable String id,
        @PathVariable boolean flag) {
        LenResponse j = new LenResponse();
        try {
            // 此处修改了id获取方式为路径中的id，实际传入的参数就是leave.getId()
            UserLeave oldLeave = leaveService.getById(id);
            // 赋值
            BeanUtil.copyNotNullBean(leave, oldLeave);
            // 更新Dao层
            QueryWrapper<UserLeave> userLeaveQueryWrapper = new QueryWrapper<>(oldLeave);
            leaveService.update(userLeaveQueryWrapper);

            Map<String, Object> map = new HashMap<>();
            // 重新提交请假
            if (flag) {
                map.put("flag", true);
            } else {
                // 取消请假
                map.put("flag", false);
            }
            taskService.complete(taskId, map);
            j.setMsg("保存成功");
        } catch (LenException e) {
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 新建请假
     * 
     * @param userLeave 请假实体
     * @return 响应结果
     */
    @PostMapping("addLeave")
    @ResponseBody
    public LenResponse addLeave(UserLeave userLeave) {
        LenResponse j = new LenResponse();
        if (userLeave == null) {
            return LenResponse.error("获取数据失败");
        }
        long beginTime = userLeave.getBeginTime().getTime();
        long endTime = userLeave.getEndTime().getTime();
        int days = 1;
        // 计算请假天数
        if (endTime != beginTime) {
            days += (int)((endTime - beginTime) / (1000 * 60 * 60 * 24));
        }
        if (days <= 0) {
            return LenResponse.error("时间输入有误");
        }

        userLeave.setDays(days);
        // 设置用户信息
        CurrentUser user = CommonUtil.getUser();
        userLeave.setUserId(user.getId());
        userLeave.setUserName(user.getUsername());
        // 设置实例id
        userLeave.setProcessInstanceId("2018");
        // 保存Dao层
        leaveService.save(userLeave);
        Map<String, Object> map = new HashMap<>();
        userLeave.setUrlpath("/leave/readOnlyLeave/" + userLeave.getId());
        map.put(BASE_TASK, userLeave);
        map.put("day", days);
        // 启动请假流程实例，保存实例的id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_leave", map);
        userLeave.setProcessInstanceId(processInstance.getId());
        userLeave.setUrlpath("/leave/readOnlyLeave/" + userLeave.getId());
        // 更新Dao
        leaveService.updateById(userLeave);
        j.setMsg("请假申请成功");
        return j;
    }

    /**
     * 读取请假实例，回显到编辑页面
     * 
     * @param model 模型层
     * @param billId 实例id
     * @return 请假编辑页面
     */
    @GetMapping("readOnlyLeave/{billId}")
    public String readOnlyLeave(Model model, @PathVariable String billId) {
        UserLeave leave = leaveService.getById(billId);
        model.addAttribute("leave", leave);
        return "/act/leave/update-leave-readonly";
    }

    /**
     * ---------我的任务---------
     */
    @GetMapping(value = "showTask")
    public String showTask() {
        return "/act/task/taskList";
    }

    /**
     * 分页查询任务列表
     * 
     * @param page 页码
     * @param limit 条数
     * @return 查询结果
     */
    @GetMapping(value = "showTaskList")
    @ResponseBody
    public String showTaskList(String page, String limit) {
        // 查询当前用户的角色
        CurrentUser user = CommonUtil.getUser();
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(user.getId());
        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>(sysRoleUser);
        List<SysRoleUser> userRoles = roleUserService.list(queryWrapper);
        List<String> roleString = new ArrayList<>();
        for (SysRoleUser sru : userRoles) {
            roleString.add(sru.getRoleId());
        }
        // 根据当前用户查询相关任务列表
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
        // 查询当前用户负责的任务列表
        List<Task> assigneeList = taskService.createTaskQuery().taskAssignee(user.getId()).list();
        // 根据当前用户角色查询任务列表
        List<Task> candidateGroup = taskService.createTaskQuery().taskCandidateGroupIn(roleString).list();
        taskList.addAll(assigneeList);
        taskList.addAll(candidateGroup);
        // 手动分页
        int count = taskList.size();
        Integer index = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        taskList = taskList.subList(index, taskList.size() > index + 10 ? index + 10 : taskList.size());

        List<com.len.entity.Task> tasks = new ArrayList<>();
        Map<String, Object> map;
        com.len.entity.Task taskEntity;

        Map<String, Map<String, Object>> mapMap = new HashMap<>();
        Map<String, Object> objectMap;
        Set<String> taskSet = new HashSet<>();
        for (Task task1 : taskList) {
            objectMap = new HashMap<>();
            String taskId = task1.getId();

            // 跳过重复任务
            if (taskSet.contains(taskId)) {
                continue;
            }

            // 查询任务的详细信息
            map = taskService.getVariables(taskId);
            BaseTask userLeave = (BaseTask)map.get(BASE_TASK);

            // 回显用户名，请假原因，详情url
            taskEntity = new com.len.entity.Task(task1);
            taskEntity.setUserName(userLeave.getUserName());
            taskEntity.setReason(userLeave.getReason());
            taskEntity.setUrlpath(userLeave.getUrlpath());
            /*如果是自己*/
            if (user.getId().equals(userLeave.getUserId())) {
                if (map.get("flag") != null) {
                    if (!(boolean)map.get("flag")) {
                        objectMap.put("flag", true);
                    } else {
                        objectMap.put("flag", false);
                    }
                } else {
                    objectMap.put("flag", true);
                }
            } else {
                objectMap.put("flag", false);
            }
            // 添加｛任务id，flag标记｝的map
            mapMap.put(taskEntity.getId(), objectMap);
            // 返回的任务列表
            tasks.add(taskEntity);
            // 跳过重复的任务
            taskSet.add(taskId);
        }
        return ReType.jsonStrng(count, tasks, mapMap, "id");
    }

    /**
     * 跳转任务审批页面
     * 
     * @param model 模型层
     * @param taskId 任务id
     * @return 审批页面
     */
    @GetMapping("agent/{id}")
    public String agent(Model model, @PathVariable("id") String taskId) {
        // 根据id获取任务详细信息
        Map<String, Object> variables = taskService.getVariables(taskId);
        BaseTask baseTask = (BaseTask)variables.get(BASE_TASK);
        // 设置请假详细的url,用在Iframe中
        model.addAttribute("leaveUrl", baseTask.getUrlpath());
        model.addAttribute("taskId", taskId);
        return "/act/task/task-agent-iframe";
    }

    /**
     * 审批动作处理
     * 
     * @param op 审批信息
     * @return 响应结果
     */
    @PostMapping("agent/complete")
    @ResponseBody
    public LenResponse complete(LeaveOpinion op) {
        // 获取任务详细
        Map<String, Object> variables = taskService.getVariables(op.getTaskId());

        // 获取当前用户，设置操作时间，用户id，用户名
        CurrentUser user = Principal.getCurrentUse();
        op.setCreateTime(new Date());
        op.setOpId(user.getId());
        op.setOpName(user.getRealName());
        LenResponse j = new LenResponse();
        Map<String, Object> map = new HashMap<>();
        // 是否通过审批
        map.put("flag", op.isFlag());

        // 判断节点是否已经拒绝过一次了
        Object needend = variables.get("needend");
        if (needend != null && (boolean)needend && (!op.isFlag())) {
            map.put("needfinish", -1); // 结束
        } else {
            if (op.isFlag()) {
                map.put("needfinish", 1);// 通过下一个节点
            } else {
                map.put("needfinish", 0);// 不通过
            }
        }
        // 审批信息叠加
        List<LeaveOpinion> leaveList = new ArrayList<>();
        Object o = variables.get(LEAVE_OPINION_LIST);
        if (o != null) {
            leaveList = CastClassUtils.castList(o, LeaveOpinion.class);
        }
        leaveList.add(op);
        // 封装审批数据
        UserLeave userLeave = (UserLeave)variables.get(BASE_TASK);
        map.put("day", userLeave.getDays());
        map.put(LEAVE_OPINION_LIST, leaveList);
        j.setMsg(
            "审核成功" + (op.isFlag() ? "<font style='color:green'>[通过]</font>" : "<font style='color:red'>[未通过]</font>"));
        // 完成任务，设置审批信息与请假天数
        taskService.complete(op.getTaskId(), map);
        return j;
    }

    /**
     * 追踪图片成图 增加历史流程
     *
     * @param resp 响应对象
     * @param processInstanceId 实例id
     * @throws IOException 可能发生文件IO异常
     */
    @GetMapping("getProcImage")
    public void getProcImage(HttpServletResponse resp, String processInstanceId) throws IOException {
        // 需要处于活动状态节点信息
        InputStream imageStream = generateStream(processInstanceId, true);
        // 生成失败
        if (imageStream == null) {
            return;
        }
        // 不带当前活动节点高亮出图
        InputStream imageNoCurrentStream = generateStream(processInstanceId, false);
        if (imageNoCurrentStream == null) {
            return;
        }

        // 生成gif
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // 设置背景颜色
        e.setTransparent(Color.BLACK);
        // 设置帧播放次数 0为无限循环播放
        e.setRepeat(0);
        // 设置颜色质量
        e.setQuality(19);
        // 设置输出流
        e.start(resp.getOutputStream());

        // 读入需要播放的jpg文件
        BufferedImage current = ImageIO.read(imageStream);
        // 添加到帧中
        e.addFrame(current);

        // 设置播放的延迟时间
        e.setDelay(200);
        // 读入需要播放的jpg文件
        BufferedImage nocurrent = ImageIO.read(imageNoCurrentStream);
        // 添加到帧中
        e.addFrame(nocurrent);

        // 输出图像
        e.finish();

        // byte[] b = new byte[1024];
        // int len;
        // while ((len = imageStream.read(b, 0, 1024)) != -1) {
        // resp.getOutputStream().write(b, 0, len);
        // }
    }

    /**
     * 只读图片页面
     * 
     * @param model 模型层
     * @param processInstanceId 流程实例id
     * @return 流程图页面
     */
    @GetMapping("shinePics/{processInstanceId}")
    public String shinePics(Model model, @PathVariable String processInstanceId) {
        model.addAttribute("processInstanceId", processInstanceId);
        return "/act/leave/shinePics";
    }

    /**
     * 根据流程实例id获取高亮处理后的图像
     * 
     * @param processInstanceId 流程实例id
     * @return 结果
     * @throws IOException 可能发生文件操作异常
     */
    @GetMapping("getShineProcImage")
    @ResponseBody
    public String getShineProcImage(String processInstanceId) throws IOException {
        // 定义返回结构
        JSONObject result = new JSONObject();
        // 图像数组，取得高亮当前的活动节点，高亮历史节点的图像
        JSONArray shineProImages = new JSONArray();
        // 高亮当前活动节点
        InputStream imageStream = generateStream(processInstanceId, true);
        if (imageStream != null) {
            // 将图像base64编码存储到json
            String imageCurrentNode = Base64Utils.ioToBase64(imageStream);
            if (StringUtils.isNotBlank(imageCurrentNode)) {
                shineProImages.add(imageCurrentNode);
            }
        }
        // 高亮历史节点
        InputStream imageNoCurrentStream = generateStream(processInstanceId, false);
        if (imageNoCurrentStream != null) {
            // 将图像的base64编码存储到json
            String imageNoCurrentNode = Base64Utils.ioToBase64(imageNoCurrentStream);
            if (StringUtils.isNotBlank(imageNoCurrentNode)) {
                shineProImages.add(imageNoCurrentNode);
            }
        }
        // 响应结果
        result.put("id", UUID.randomUUID().toString());
        result.put("errorNo", 0);
        result.put("images", shineProImages);
        return result.toJSONString();
    }

    /**
     * 根据流程id生成图片文件流对象
     *
     * @param processInstanceId 实例id
     * @param needCurrent 是否需要高亮当前活动节点
     * @return 流对象
     */
    public InputStream generateStream(String processInstanceId, boolean needCurrent) {
        ProcessEngineConfiguration processEngineConfiguration;
        // 查询实例数据
        ProcessInstance processInstance =
            runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance =
            historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        // 历史活动信息id集合
        List<String> executedActivityIdList = new ArrayList<>();
        // 当前处于活动状态的任务节点id集合
        List<String> currentActivityIdList = new ArrayList<>();
        // 历史活动任务信息实例集合
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            // 是否需要当前活动节点id数据
            if (needCurrent) {
                currentActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
            }
        }
        if (historicProcessInstance != null) {
            // 获取历史已完成流程定义id
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            // 获取流程实例的历史活动信息，按历史活动id升序
            historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                // 添加到历史信息id集合中
                executedActivityIdList.add(activityInstance.getActivityId());
            }
        }

        // 如果流程实例Definition id未获取到，或者没有已完成的历史流程任务
        if (StringUtils.isEmpty(processDefinitionId) || CollectionUtils.isEmpty(executedActivityIdList)) {
            return null;
        }

        // 高亮线路id集合
        ProcessDefinitionEntity definitionEntity =
            (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, historicActivityInstanceList);

        // 获取bpmn模型对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        // 获取流程引擎的配置
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        // 设置当前上下文的引擎相关配置
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl)processEngineConfiguration);
        // 获取流程图生成器
        HMProcessDiagramGenerator diagramGenerator =
            (HMProcessDiagramGenerator)processEngineConfiguration.getProcessDiagramGenerator();
        // List<String> activeIds = this.runtimeService.getActiveActivityIds(processInstance.getId());

        // 根据流程定义生成流程图像
        return diagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, highLightedFlows,
            processEngine.getProcessEngineConfiguration().getActivityFontName(),
            processEngine.getProcessEngineConfiguration().getLabelFontName(), "宋体", null, 1.0, currentActivityIdList);
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity 流程定义对象实体
     * @param historicActivityInstances 历史流程实例集合
     * @return 结果
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
        List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();

        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl =
                processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            ActivityImpl sameActivityImpl1 =
                processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 =
                        processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl)pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

}
