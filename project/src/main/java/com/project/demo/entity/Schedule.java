package com.project.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 日程：(Schedule)表实体类
 *
 * @author xxx
 *@since 202X-XX-XX
 */
@TableName("schedule")
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule implements Serializable {

    private static final long serialVersionUID = -49443879021884350L;

    /**
     * 日程ID：
     */
    @TableId(value = "schedule_id", type = IdType.AUTO)
    private Integer scheduleId;

    /**
     * 日程内容：
     */
    @TableField(value = "content")
    private String content;

    /**
     * 计划时间：
     */
    @TableField(value = "scheduled_time")
    private Timestamp scheduled_time;

    /**
     * 用户id：
     */
    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 创建时间：
     */
    @TableField(value = "create_time")
    private Timestamp createTime;

    /**
     * 更新时间：
     */
    @TableField(value = "update_time")
    private Timestamp updateTime;
}

