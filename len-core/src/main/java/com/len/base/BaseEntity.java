package com.len.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    public void create(String createdBy) {
        this.createBy = createdBy;
        this.createDate = new Date();
        this.updateBy = createdBy;
        this.updateDate = new Date();
    }

    public void update(String updatedBy) {
        this.updateBy = updatedBy;
        this.updateDate = new Date();
    }
}
