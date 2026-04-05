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
 * 在线沟通群组：(CustomerChatGroup)表实体类
 *
 */
@TableName("`customer_chat_group`")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerChatGroup implements Serializable {

    // CustomerChatGroup编号
    @TableId(value = "customer_chat_group_id", type = IdType.AUTO)
    private Integer customer_chat_group_id;

    // 成员信息
    @TableField(value = "`member`")
    private String member;
    // 已读状态
    @TableField(value = "`read_state`")
    private String read_state;
    // 最后阅读时间
    @TableField(value = "`last_read_time`")
    private String last_read_time;
    // 未读消息数
    @TableField(value = "`unread_count`")
    private String unread_count;
    
	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
