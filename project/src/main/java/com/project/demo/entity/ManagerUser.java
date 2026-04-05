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
 * 经理用户：(ManagerUser)表实体类
 *
 */
@TableName("`manager_user`")
@Data
@EqualsAndHashCode(callSuper = false)
public class ManagerUser implements Serializable {

    // ManagerUser编号
    @TableId(value = "manager_user_id", type = IdType.AUTO)
    private Integer manager_user_id;

    // 员工工号
    @TableField(value = "`employee_work_number`")
    private String employee_work_number;
    // 员工姓名
    @TableField(value = "`employee_name`")
    private String employee_name;
    // 员工性别
    @TableField(value = "`employee_gender`")
    private String employee_gender;
    // 联系号码
    @TableField(value = "`contact_number`")
    private String contact_number;
    // 二维码
    @TableField(value = "`two_dimensional_code`")
    private String two_dimensional_code;









    // 用户编号
    @TableField(value = "user_id")
    private Integer userId;











					


                                                                                            


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
