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
 * 卡券类型：(CardType)表实体类
 *
 */
@TableName("`card_type`")
@Data
@EqualsAndHashCode(callSuper = false)
public class CardType implements Serializable {

    // CardType编号
    @TableId(value = "card_type_id", type = IdType.AUTO)
    private Integer card_type_id;

    // 卡券名称
    @TableField(value = "`card_name`")
    private String card_name;
    // 有效期限
    @TableField(value = "`effective_period`")
    private Timestamp effective_period;
    // 卡券内容
    @TableField(value = "`card_content`")
    private String card_content;
    // 适用项目
    @TableField(value = "`applicable_items`")
    private String applicable_items;




















							


                                                                                                                





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
