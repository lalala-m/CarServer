package com.project.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 4S店管理员：(StoreAdmin)表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("store_admin")
public class StoreAdmin implements Serializable {

    private static final long serialVersionUID = 952713957612341200L;

    /**
     * 4S店管理员ID
     */
    @TableId(value = "store_admin_id", type = IdType.AUTO)
    private Integer storeAdminId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 所属4S店
     */
    @TableField(value = "store_id")
    private Integer storeId;

    /**
     * 员工工号
     */
    @TableField(value = "employee_work_number")
    private String employeeWorkNumber;

    /**
     * 员工姓名
     */
    @TableField(value = "employee_name")
    private String employeeName;

    /**
     * 员工性别
     */
    @TableField(value = "employee_gender")
    private String employeeGender;

    /**
     * 联系号码
     */
    @TableField(value = "contact_number")
    private String contactNumber;

    /**
     * 审核状态
     */
    @TableField(value = "examine_state")
    private String examineState;

    /**
     * 用户状态
     */
    @TableField(value = "user_state")
    private String userState;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private java.sql.Timestamp createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private java.sql.Timestamp updateTime;

}
