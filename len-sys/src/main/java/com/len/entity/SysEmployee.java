package com.len.entity;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_employee")
@Data
public class SysEmployee extends BaseEntity {

    /**
     * 编码
     */
    private String employeeCode;

    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 员工性别
     */
    private String employeeGender;
    /**
     * 员工生日
     */
    private String employeeBirthday;
    /**
     * 员工部门
     */
    private String employeeDeptcodes;
    /**
     * 员工工作电话
     */
    private String employeeMobile1;
    /**
     * 员工私人电话
     */
    private String employeeMobile2;
    /**
     * 员工工作邮箱
     */
    private String employeeMail1;
    /**
     * 员工私人邮箱
     */
    private String employeeMail2;
    /**
     * 员工微信
     */
    private String employeeWechat;
    /**
     * 员工QQ
     */
    private String employeeQq;
    /**
     * 员工头像
     */
    private String employeePhoto;
    /**
     * 员工社保
     */
    private String employeeSsn;
    /**
     * 员工医保
     */
    private String employeeMin;

    /**
     * 部门编码
     */
    @TableField(exist = false)
    private List<Integer> deptCodeList;

    /**
     * 添加部门编码
     * 
     * @param deptCode 部门编码
     */
    public void addDeptCode(int deptCode) {
        if (deptCodeList == null) {
            deptCodeList = new ArrayList<>();
        }
        deptCodeList.add(deptCode);
    }
}