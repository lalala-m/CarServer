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
 * 预约记录：(ReservationRecord)表实体类
 *
 */
@TableName("`reservation_record`")
@Data
@EqualsAndHashCode(callSuper = false)
public class ReservationRecord implements Serializable {

    // ReservationRecord编号
    @TableId(value = "reservation_record_id", type = IdType.AUTO)
    private Integer reservation_record_id;

    // 预约编码
    @TableField(value = "`booking_code`")
    private String booking_code;
    // 服务编码
    @TableField(value = "`service_code`")
    private String service_code;
    // 服务名称
    @TableField(value = "`service_name`")
    private String service_name;
    // 服务价格
    @TableField(value = "`service_price`")
    private Double service_price;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 车主姓名
    @TableField(value = "`owners_name`")
    private String owners_name;
    // 联系号码
    @TableField(value = "`contact_number`")
    private String contact_number;
    // 预约日期
    @TableField(value = "`appointment_date`")
    private Timestamp appointment_date;
    // 预约时段
    @TableField(value = "`appointment_period`")
    private String appointment_period;
    // 预约次数
    @TableField(value = "`number_of_appointments`")
    private String number_of_appointments;
    // 交车时间
    @TableField(value = "`delivery_time`")
    private Timestamp delivery_time;
    // 销售顾问
    @TableField(value = "`sales_consultant`")
    private String sales_consultant;
    // 车牌号码
    @TableField(value = "`license_plate_number`")
    private String license_plate_number;
    // 车架号
    @TableField(value = "`frame_number`")
    private String frame_number;
    // 汽车类型
    @TableField(value = "`car_type`")
    private String car_type;
    // 预约备注
    @TableField(value = "`appointment_remarks`")
    private String appointment_remarks;
    // 销售经理
    @TableField(value = "`sales_manager`")
    private Integer sales_manager;
    // 客户经理
    @TableField(value = "`account_manager`")
    private Integer account_manager;






    // 支付状态
    @TableField(value = "pay_state")
    private String pay_state;

    // 支付类型: 微信、支付宝、网银
    @TableField(value = "pay_type")
    private String pay_type;














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
