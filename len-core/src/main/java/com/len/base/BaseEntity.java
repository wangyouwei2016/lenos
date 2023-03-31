package com.len.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;
}
