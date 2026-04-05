package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户未读消息
 */
@TableName("user_chat_read")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserChatRead implements Serializable {

    private static final long serialVersionUID = -89845695959527178L;
    /**
     * id
     */
    @TableField(value = "user_chat_read_id")
    private String user_chat_read_id;

    /**
     * 接收人id
     */
    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 发送人id
     */
    @TableField(value = "send_user_id")
    private Integer send_user_id;

    /**
     * 群聊id
     */
    @TableField(value = "group_id")
    private Integer group_id;

    /**
     * 类型1-点对点消息，2-群聊消息
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 消息
     */
    @TableField(value = "message")
    private String message;

}
