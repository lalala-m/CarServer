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
 * 销售经理：(SalesManager)表实体类
 *
 */
@TableName("`sales_manager`")
@Data
@EqualsAndHashCode(callSuper = false)
public class SalesManager implements Serializable {

    // SalesManager编号
    @TableId(value = "sales_manager_id", type = IdType.AUTO)
    private Integer sales_manager_id;

    // 经理姓名
    @TableField(value = "`manager_name`")
    private String manager_name;
    // 经理性别
    @TableField(value = "`gender_of_manager`")
    private String gender_of_manager;
    // 联系号码
    @TableField(value = "`contact_number`")
    private String contact_number;









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
