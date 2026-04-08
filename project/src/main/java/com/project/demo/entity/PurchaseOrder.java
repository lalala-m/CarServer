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
 * 购买订单：(PurchaseOrder)表实体类
 *
 */
@TableName("`purchase_order`")
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseOrder implements Serializable {

    // PurchaseOrder编号
    @TableId(value = "purchase_order_id", type = IdType.AUTO)
    private Integer purchase_order_id;

    // 购买单号
    @TableField(value = "`purchase_order_number`")
    private String purchase_order_number;
    // 汽车编码
    @TableField(value = "`car_coding`")
    private String car_coding;
    // 汽车名称
    @TableField(value = "`car_name`")
    private String car_name;
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
    // 商家用户
    @TableField(value = "`business_user`")
    private Integer business_user;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 购买时间
    @TableField(value = "`purchase_time`")
    private Timestamp purchase_time;
    // 购买次数
    @TableField(value = "`number_of_purchases`")
    private String number_of_purchases;
    // 购买数量
    @TableField(value = "`purchase_quantity`")
    private Double purchase_quantity;
    // 合计金额
    @TableField(value = "`total_amount`")
    private String total_amount;
    // 购买备注
    @TableField(value = "`purchase_notes`")
    private String purchase_notes;





    // 审核状态
    @TableField(value = "examine_state")
    private String examine_state;

    // 支付状态
    @TableField(value = "pay_state")
    private String pay_state;

    // 支付类型: 微信、支付宝、网银
    @TableField(value = "pay_type")
    private String pay_type;


    // 审核回复
    @TableField(value = "examine_reply")
    private String examine_reply;












			    // 额外信息
    @TableField(value = "extra")
    private String extra;
	// 来源表
	@TableField(value = "source_table")
	private String source_table;
	
	// 来源ID
	@TableField(value = "source_id")
	private Integer source_id;
	
	// 来源用户ID
	@TableField(value = "source_user_id")
	private Integer source_user_id;
					


                                                                                                                





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
