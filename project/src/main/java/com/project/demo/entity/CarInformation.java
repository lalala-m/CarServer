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
 * 汽车信息：(CarInformation)表实体类
 *
 */
@TableName("`car_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class CarInformation implements Serializable {

    // CarInformation编号
    @TableId(value = "car_information_id", type = IdType.AUTO)
    private Integer car_information_id;

    // 汽车编码
    @TableField(value = "`car_coding`")
    private String car_coding;
    // 汽车名称
    @TableField(value = "`car_name`")
    private String car_name;
    // 车况图片
    @TableField(value = "`car_condition_picture`")
    private String car_condition_picture;
    // 汽车车型
    @TableField(value = "`car_models`")
    private String car_models;
    // 汽车品牌
    @TableField(value = "`car_brand`")
    private String car_brand;
    // 汽车新旧
    @TableField(value = "`new_and_old_car`")
    private String new_and_old_car;
    // 汽车价格
    @TableField(value = "`car_price`")
    private Double car_price;
    // 汽车状态
    @TableField(value = "`car_status`")
    private String car_status;
    // 商家用户
    @TableField(value = "`business_user`")
    private Integer business_user;
    // 门店位置
    @TableField(value = "`store_location`")
    private String store_location;
    // 汽车配置
    @TableField(value = "`car_configuration`")
    private String car_configuration;
    // 汽车详情
    @TableField(value = "`car_specificss`")
    private String car_specificss;

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















		// 试驾限制次数
	@TableField(value = "test_drive_record_limit_times")
	private String test_drive_record_limit_times;
		// 购买限制次数
	@TableField(value = "purchase_order_limit_times")
	private String purchase_order_limit_times;
	
					


                                                                                            


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
