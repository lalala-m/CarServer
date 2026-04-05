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
 * 在线沟通记录：(CustomerChatLog)表实体类
 *
 */
@TableName("`customer_chat_log`")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerChatLog implements Serializable {

    // CustomerChatLog编号
    @TableId(value = "customer_chat_log_id", type = IdType.AUTO)
    private Integer customer_chat_log_id;

    // 沟通群组id
    @TableField(value = "`customer_chat_id`")
    private String customer_chat_id;
    // 沟通内容
    @TableField(value = "`content`")
    private String content;

	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
