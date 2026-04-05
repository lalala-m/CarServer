package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户群聊
 */
@TableName("user_chat_group")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserChatGroup implements Serializable {

    private static final long serialVersionUID = -82540585424852965L;

    /**
     * 群聊id
     */
    @TableId(value = "user_chat_group_id", type = IdType.AUTO)
    private Integer user_chat_group_id;

    /**
     * 群聊id
     */
    @TableField(value = "group_id")
    private String group_id;

    /**
     * 群聊名称
     */
    @TableField(value = "group_name")
    private String group_name;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 创建时间：
     */
    @TableField(value = "create_time")
    private Timestamp create_time;

    /**
     * 更新时间：
     */
    @TableField(value = "update_time")
    private Timestamp update_time;

}
