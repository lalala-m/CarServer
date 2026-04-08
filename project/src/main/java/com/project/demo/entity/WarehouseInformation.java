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
 * 仓库信息：(WarehouseInformation)表实体类
 *
 */
@TableName("`warehouse_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class WarehouseInformation implements Serializable {

    // WarehouseInformation编号
    @TableId(value = "warehouse_information_id", type = IdType.AUTO)
    private Integer warehouse_information_id;

    // 仓库名称
    @TableField(value = "`warehouse_name`")
    private String warehouse_name;
    // 仓库图片
    @TableField(value = "`warehouse_picture`")
    private String warehouse_picture;
    // 默认状态
    @TableField(value = "`default_state`")
    private String default_state;
    // 仓库位置
    @TableField(value = "`warehouse_location`")
    private String warehouse_location;
    // 仓库描述
    @TableField(value = "`warehouse_description`")
    private String warehouse_description;




















							


                                                                                                                





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
