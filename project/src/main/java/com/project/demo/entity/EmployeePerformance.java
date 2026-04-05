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
 * 员工绩效：(EmployeePerformance)表实体类
 *
 */
@TableName("`employee_performance`")
@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeePerformance implements Serializable {

    // EmployeePerformance编号
    @TableId(value = "employee_performance_id", type = IdType.AUTO)
    private Integer employee_performance_id;

    // 绩效标题
    @TableField(value = "`performance_title`")
    private String performance_title;
    // 员工姓名
    @TableField(value = "`employee_name`")
    private String employee_name;
    // 评估时间
    @TableField(value = "`evaluation_time`")
    private Timestamp evaluation_time;
    // 服务单数
    @TableField(value = "`service_singular`")
    private Double service_singular;
    // 服务质量
    @TableField(value = "`quality_of_service`")
    private String quality_of_service;
    // 工作态度
    @TableField(value = "`working_attitude`")
    private String working_attitude;
    // 绩效等级
    @TableField(value = "`performance_level`")
    private String performance_level;
    // 评估结果
    @TableField(value = "`assessment_results`")
    private String assessment_results;
    // 工作建议
    @TableField(value = "`work_suggestion`")
    private String work_suggestion;




















					


                                                                                            


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
