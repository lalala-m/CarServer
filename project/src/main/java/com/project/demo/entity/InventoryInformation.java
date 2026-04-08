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
 * 库存信息：(InventoryInformation)表实体类
 *
 */
@TableName("`inventory_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class InventoryInformation implements Serializable {

    // InventoryInformation编号
    @TableId(value = "inventory_information_id", type = IdType.AUTO)
    private Integer inventory_information_id;

    // 耗材编码
    @TableField(value = "`consumables_code`")
    private String consumables_code;
    // 耗材名称
    @TableField(value = "`consumables_name`")
    private String consumables_name;
    // 耗材库存
    @TableField(value = "`consumables_inventory`")
    private Double consumables_inventory;
    // 耗材规格
    @TableField(value = "`consumables_specifications`")
    private String consumables_specifications;
    // 耗材简介
    @TableField(value = "`introduction_of_consumables`")
    private String introduction_of_consumables;



















		// 入库限制次数
	@TableField(value = "inbound_record_limit_times")
	private String inbound_record_limit_times;
		// 出库限制次数
	@TableField(value = "outbound_record_limit_times")
	private String outbound_record_limit_times;
	
							


                                                                                                                





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
