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
 * 供应厂商：(Supplier)表实体类
 *
 */
@TableName("`supplier`")
@Data
@EqualsAndHashCode(callSuper = false)
public class Supplier implements Serializable {

    // Supplier编号
    @TableId(value = "supplier_id", type = IdType.AUTO)
    private Integer supplier_id;

    // 厂家名称
    @TableField(value = "`name_of_manufacturer`")
    private String name_of_manufacturer;
    // 联系电话
    @TableField(value = "`contact_phone`")
    private String contact_phone;
    // 厂家地址
    @TableField(value = "`manufacturer_address`")
    private String manufacturer_address;
    // 主营产品
    @TableField(value = "`main_products`")
    private String main_products;
    // 厂家简介
    @TableField(value = "`manufacturer_profile`")
    private String manufacturer_profile;




















							


                                                                                                                





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
