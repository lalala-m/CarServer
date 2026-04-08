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
 * 救援记录：(RescueRecord)表实体类
 *
 */
@TableName("`rescue_record`")
@Data
@EqualsAndHashCode(callSuper = false)
public class RescueRecord implements Serializable {

    // RescueRecord编号
    @TableId(value = "rescue_record_id", type = IdType.AUTO)
    private Integer rescue_record_id;

    // 救援编码
    @TableField(value = "`rescue_code`")
    private String rescue_code;
    // 商家用户
    @TableField(value = "`business_user`")
    private Integer business_user;
    // 救援用户
    @TableField(value = "`rescue_user`")
    private Integer rescue_user;
    // 车主姓名
    @TableField(value = "`owners_name`")
    private String owners_name;
    // 联系号码
    @TableField(value = "`contact_number`")
    private String contact_number;
    // 审核状态
    @TableField(value = "`audit_status`")
    private String audit_status;
    // 维修人员
    @TableField(value = "`maintenance_personnel`")
    private String maintenance_personnel;
    // 维修时间
    @TableField(value = "`maintenance_time`")
    private Timestamp maintenance_time;
    // 现场图片
    @TableField(value = "`scene_picture`")
    private String scene_picture;
    // 维修情况
    @TableField(value = "`maintenance`")
    private String maintenance;














    // 当前位置
    @TableField(value = "location_address")
    private String location_address;
    // 当前位置经度
    @TableField(value = "location_lng")
    private String location_lng;
    // 当前位置纬度
    @TableField(value = "location_lat")
    private String location_lat;






							


                                                                                                                





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
