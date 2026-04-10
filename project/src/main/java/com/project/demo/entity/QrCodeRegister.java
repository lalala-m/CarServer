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
 * 二维码注册标识表：(QrCodeRegister)表实体类
 *
 */
@TableName("`qr_code_register`")
@Data
@EqualsAndHashCode(callSuper = false)
public class QrCodeRegister implements Serializable {

    // 二维码ID
    @TableId(value = "qr_id", type = IdType.AUTO)
    private Integer qrId;

    // 二维码Token（唯一标识，用于扫码查询）
    @TableField(value = "`qr_token`")
    private String qrToken;

    // 角色类型：member-会员/info-信息员/consultant-业务顾问/financial-财务审核
    @TableField(value = "`role_type`")
    private String roleType;

    // 关联角色记录ID（注册后填充）
    @TableField(value = "`role_id`")
    private Integer roleId;

    // 角色姓名（扫码后展示给用户）
    @TableField(value = "`role_name`")
    private String roleName;

    // 员工工号（信息员/业务顾问/财务审核专用）
    @TableField(value = "`employee_work_number`")
    private String employeeWorkNumber;

    // 所属部门（业务顾问专用）
    @TableField(value = "`department`")
    private String department;

    // 状态：未使用/已使用/已失效
    @TableField(value = "`status`")
    private String status;

    // 绑定用户ID（注册成功后填充）
    @TableField(value = "`bind_user_id`")
    private Integer bindUserId;

    // 过期时间（为空表示永不过期）
    @TableField(value = "`expire_time`")
    private Timestamp expireTime;

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
