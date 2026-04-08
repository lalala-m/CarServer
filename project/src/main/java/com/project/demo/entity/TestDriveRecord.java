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
 * 试驾记录：(TestDriveRecord)表实体类
 *
 */
@TableName("`test_drive_record`")
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDriveRecord implements Serializable {

    // TestDriveRecord编号
    @TableId(value = "test_drive_record_id", type = IdType.AUTO)
    private Integer test_drive_record_id;

    // 试驾编码
    @TableField(value = "`test_drive_code`")
    private String test_drive_code;
    // 汽车编码
    @TableField(value = "`car_coding`")
    private String car_coding;
    // 汽车名称
    @TableField(value = "`car_name`")
    private String car_name;
    // 汽车车型
    @TableField(value = "`car_models`")
    private String car_models;
    // 商家用户
    @TableField(value = "`business_user`")
    private Integer business_user;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 试驾时间
    @TableField(value = "`test_drive_time`")
    private Timestamp test_drive_time;
    // 试驾备注
    @TableField(value = "`test_drive_remarks`")
    private String test_drive_remarks;




















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
