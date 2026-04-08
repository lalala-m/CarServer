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
 * 维修养护：(Maintenance)表实体类
 *
 */
@TableName("`maintenance`")
@Data
@EqualsAndHashCode(callSuper = false)
public class Maintenance implements Serializable {

    // Maintenance编号
    @TableId(value = "maintenance_id", type = IdType.AUTO)
    private Integer maintenance_id;

    // 项目套餐
    @TableField(value = "`project_bundle`")
    private String project_bundle;
    // 项目图片
    @TableField(value = "`project_picture`")
    private String project_picture;
    // 项目价格
    @TableField(value = "`project_price`")
    private Double project_price;
    // 经理用户
    @TableField(value = "`manager_user`")
    private Integer manager_user;
    // 财务用户
    @TableField(value = "`financial_user`")
    private Integer financial_user;
    // 项目说明
    @TableField(value = "`project_description`")
    private String project_description;
    // 项目内容
    @TableField(value = "`project_content`")
    private String project_content;

    // 点击数
    @TableField(value = "hits")
    private Integer hits;

    // 点赞数
    @TableField(value = "praise_len")
    private Integer praise_len;


    // 评论数
    @TableField(value = "comment_len")
    private Integer comment_len;















		// 购买限制次数
	@TableField(value = "maintenance_order_limit_times")
	private String maintenance_order_limit_times;
	
							


                                                                                                                





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
