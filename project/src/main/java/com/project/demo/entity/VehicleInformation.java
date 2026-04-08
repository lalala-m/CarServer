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
 * 车辆信息：(VehicleInformation)表实体类
 *
 */
@TableName("`vehicle_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleInformation implements Serializable {

    // VehicleInformation编号
    @TableId(value = "vehicle_information_id", type = IdType.AUTO)
    private Integer vehicle_information_id;

    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 车牌号码
    @TableField(value = "`license_plate_number`")
    private String license_plate_number;
    // 汽车类型
    @TableField(value = "`car_type`")
    private String car_type;
    // 车架号
    @TableField(value = "`frame_number`")
    private String frame_number;
    // 车辆备注
    @TableField(value = "`vehicle_remarks`")
    private String vehicle_remarks;





    // 审核状态
    @TableField(value = "examine_state")
    private String examine_state;



    // 审核回复
    @TableField(value = "examine_reply")
    private String examine_reply;












							


                                                                                                                





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
