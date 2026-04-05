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
 * 用户群天
 */
@TableName("user_chat_friend")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserChatFriend implements Serializable {

    private static final long serialVersionUID = -82540585424852965L;

    /**
     * 群聊id
     */
    @TableId(value = "user_chat_friend_id", type = IdType.AUTO)
    private Integer user_chat_friend_id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 用户好友id
     */
    @TableField(value = "friend_user_id")
    private Integer friend_user_id;

    /**
     * 用户好友名称
     */
    @TableField(value = "friend_user_name")
    private String friend_user_name;

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

    /**
     * 好友用户名
     */
    @TableField(exist = false)
    private String username;

    /**
     * 好友昵称
     */
    @TableField(exist = false)
    private String nickname;

    /**
     * 好友头像
     */
    @TableField(exist = false)
    private String avatar;

}
