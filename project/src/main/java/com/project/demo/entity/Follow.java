package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 用户关注：(Follow)表实体类
 *
 */
@TableName("`follow`")
@Data
@EqualsAndHashCode(callSuper = false)
public class Follow implements Serializable {

    /**
     * Follow编号
     */
    @TableId(value = "follow_id", type = IdType.AUTO)
    private Integer follow_id;
	
	/**
	 * 关注人ID
	 */
	@TableField(value = "follower_id")
	private Integer follower_id;

    /**
     * 关注人头像
     */
    @TableField(value = "`follower_avatar`")
    private String follower_avatar;
	
	/**
	 * 关注人昵称
	 */
	@TableField(value = "`follower_nickname`")
	private String follower_nickname;
	
	/**
	 * 被关注人ID
	 */
	@TableField(value = "followed_id")
	private Integer followed_id;
	
	/**
	 * 关被注人头像
	 */
	@TableField(value = "`followed_avatar`")
	private String followed_avatar;
	
	/**
	 * 被关注人昵称
	 */
	@TableField(value = "`followed_nickname`")
	private String followed_nickname;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Timestamp update_time;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Timestamp create_time;

}
