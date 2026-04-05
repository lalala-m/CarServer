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
 * 车型分类：(VehicleClassNameification)表实体类
 *
 */
@TableName("`vehicle_class_nameification`")
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleClassNameification implements Serializable {

    // VehicleClassNameification编号
    @TableId(value = "vehicle_class_nameification_id", type = IdType.AUTO)
    private Integer vehicle_class_nameification_id;

    // 车型名称
    @TableField(value = "`model_name`")
    private String model_name;




















					


                                                                                            


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
