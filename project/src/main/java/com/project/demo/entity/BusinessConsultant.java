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
 * 业务顾问：(BusinessConsultant)表实体类
 *
 */
@TableName("`business_consultant`")
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessConsultant implements Serializable {

    // 业务顾问ID
    @TableId(value = "consultant_id", type = IdType.AUTO)
    private Integer consultantId;

    // 员工工号（唯一）
    @TableField(value = "`employee_work_number`")
    private String employeeWorkNumber;

    // 员工姓名
    @TableField(value = "`employee_name`")
    private String employeeName;

    // 性别
    @TableField(value = "`consultant_gender`")
    private String consultantGender;

    // 联系号码
    @TableField(value = "`contact_number`")
    private String contactNumber;

    // 所属部门
    @TableField(value = "`department`")
    private String department;

    // 个人二维码图片路径
    @TableField(value = "`two_dimensional_code`")
    private String twoDimensionalCode;

    // 审核状态：待审核/已通过/已拒绝
    @TableField(value = "`examine_state`")
    private String examineState;

    // 关联 user 表的 user_id（注册后填充）
    @TableField(value = "`user_id`")
    private Integer userId;

    // 关联店面ID（0=总部，>0=具体4S店）
    @TableField(value = "`store_id`")
    private Integer storeId;

    // 创建用户ID
    @TableField(value = "`create_by`")
    private Integer createBy;

    // 创建时间
    @TableField(value = "`create_time`")
    private Timestamp createTime;

    // 更新时间
    @TableField(value = "`update_time`")
    private Timestamp updateTime;

}
