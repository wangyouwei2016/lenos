package com.len.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础任务类 （不可直接实例化）
 *
 * @author kingdee-001
 * @date 2018/6/30
 */
public abstract class BaseTask implements Serializable {

    /**
     * 任务id
     */
    protected String id;

    /**
     * 用户id
     */
    protected String userId;

    /**
     * 用户名称
     */
    protected String userName;

    /**
     * act流程实例id
     */
    protected String processInstanceId;

    /**
     * 状态说明
     */
    protected String status;

    /**
     * 创建时间
     */
    protected Date createDate;

    /**
     * 创建者
     */
    protected String createBy;

    /**
     * 更新日期
     */
    protected Date updateDate;

    /**
     * 更新者
     */
    protected String updateBy;

    /**
     * 原因
     */
    protected String reason;

    /**
     * act任务名称
     */
    protected String taskName;

    /**
     * 流程查看地址
     */
    private String urlpath;

    /**
     * 提交时间
     */
    private Integer submittimes;

    /**
     * 获取id
     * @return 任务id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取用户名称
     * @return 用户名称
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取原因
     * @return 原因
     */
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 获取流程实例id
     * @return 流程实例id
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    /**
     * 获取状态信息
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取创建时间
     * @return 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取创建者
     * @return 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * 获取更新时间
     * @return 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取更新者
     * @return 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * 获取act任务名城
     * @return 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务查看地址
     * @return 任务查看地址
     */
    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }

    /**
     * 获取提交时间
     * @return 提交时间
     */
    public Integer getSubmittimes() {
        return submittimes;
    }

    public void setSubmittimes(Integer submittimes) {
        this.submittimes = submittimes;
    }
}
