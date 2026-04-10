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
 * 4S店/店面：(Store)表实体类
 *
 */
@TableName("`store`")
@Data
@EqualsAndHashCode(callSuper = false)
public class Store implements Serializable {

    // 店面ID
    @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    // 店面名称
    @TableField(value = "`store_name`")
    private String storeName;

    // 店面地址
    @TableField(value = "`store_address`")
    private String storeAddress;

    // 联系电话
    @TableField(value = "`store_phone`")
    private String storePhone;

    // 负责人
    @TableField(value = "`store_manager`")
    private String storeManager;

    // 审核状态：待审核/已通过/已拒绝
    @TableField(value = "`examine_state`")
    private String examineState;

    // 关联 user 表的 user_id
    @TableField(value = "`user_id`")
    private Integer userId;

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
