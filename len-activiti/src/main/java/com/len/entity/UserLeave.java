package com.len.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 请假流程 表实体
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 */
@TableName(value = "user_leave")
public class UserLeave extends BaseTask {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 请假天数
     */
    private Integer days;

    /**
     * 请假开始时间
     */
    @TableField(value = "begin_time")
    private Date beginTime;

    /**
     * 请假介绍时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * act流程实例id
     */
    @TableField(value = "process_instance_Id")
    private String processInstanceId;

    /**
     * 状态信息
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    private Date updateDate;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * act实时节点信息
     */
    @TableField(exist = false)
    private String taskName;

    /**
     * 请假单审核信息
     */
    @TableField(exist = false)
    private List<LeaveOpinion> opinionList = new ArrayList<>();

    /**
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    @Override
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public void leaveOpAdd(LeaveOpinion leaveOpinion) {
        this.opinionList.add(leaveOpinion);
    }

    public void leaveOpAddAll(List<LeaveOpinion> leaveOpinionList) {
        this.opinionList.addAll(leaveOpinionList);
    }

    public List<LeaveOpinion> getOpinionList() {
        return opinionList;
    }

    public void setOpinionList(List<LeaveOpinion> opinionList) {
        this.opinionList = opinionList;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}