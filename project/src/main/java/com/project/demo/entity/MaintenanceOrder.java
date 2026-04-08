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
 * 保养订单：(MaintenanceOrder)表实体类
 *
 */
@TableName("`maintenance_order`")
@Data
@EqualsAndHashCode(callSuper = false)
public class MaintenanceOrder implements Serializable {

    // MaintenanceOrder编号
    @TableId(value = "maintenance_order_id", type = IdType.AUTO)
    private Integer maintenance_order_id;

    // 项目套餐
    @TableField(value = "`project_bundle`")
    private String project_bundle;
    // 项目价格
    @TableField(value = "`project_price`")
    private String project_price;
    // 经理用户
    @TableField(value = "`manager_user`")
    private Integer manager_user;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 财务用户
    @TableField(value = "`financial_user`")
    private Integer financial_user;
    // 购买时间
    @TableField(value = "`purchase_time`")
    private Timestamp purchase_time;
    // 购买次数
    @TableField(value = "`number_of_purchases`")
    private String number_of_purchases;
    // 购买备注
    @TableField(value = "`purchase_notes`")
    private String purchase_notes;
    // 订单状态
    @TableField(value = "`order_status`")
    private String order_status;





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
