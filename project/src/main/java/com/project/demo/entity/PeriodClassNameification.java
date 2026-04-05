package com.project.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 时段分类：(PeriodClassNameification)表实体类
 *
 */
@TableName("`period_class_nameification`")
@Data
@EqualsAndHashCode(callSuper = false)
public class PeriodClassNameification implements Serializable {

    // PeriodClassNameification编号
    @TableId(value = "period_class_nameification_id", type = IdType.AUTO)
    private Integer period_class_nameification_id;

    // 时间时段
    @TableField(value = "`time_period`")
    private String time_period;




















					


                                                                                            


    // 创建用户
    @TableField(value = "create_by")
    private Integer create_by;
    
	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
