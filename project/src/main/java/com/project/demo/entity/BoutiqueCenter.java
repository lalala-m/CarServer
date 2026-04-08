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
 * 精品中心：(BoutiqueCenter)表实体类
 *
 */
@TableName("`boutique_center`")
@Data
@EqualsAndHashCode(callSuper = false)
public class BoutiqueCenter implements Serializable {

    // BoutiqueCenter编号
    @TableId(value = "boutique_center_id", type = IdType.AUTO)
    private Integer boutique_center_id;

    // 服务编码
    @TableField(value = "`service_code`")
    private String service_code;
    // 服务名称
    @TableField(value = "`service_name`")
    private String service_name;
    // 服务类型
    @TableField(value = "`service_type`")
    private String service_type;
    // 服务价格
    @TableField(value = "`service_price`")
    private Double service_price;
    // 服务封面
    @TableField(value = "`service_cover`")
    private String service_cover;
    // 服务简介
    @TableField(value = "`service_introduction`")
    private String service_introduction;
    // 服务详情
    @TableField(value = "`service_specificss`")
    private String service_specificss;

    // 点击数
    @TableField(value = "hits")
    private Integer hits;

    // 点赞数
    @TableField(value = "praise_len")
    private Integer praise_len;

    // 收藏数
    @TableField(value = "collect_len")
    private Integer collect_len;

    // 评论数
    @TableField(value = "comment_len")
    private Integer comment_len;















		// 在线预约限制次数
	@TableField(value = "reservation_record_limit_times")
	private String reservation_record_limit_times;
	
							


                                                                                                                





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
