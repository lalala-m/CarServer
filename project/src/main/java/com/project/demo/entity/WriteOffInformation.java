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
 * 核销信息：(WriteOffInformation)表实体类
 *
 */
@TableName("`write_off_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class WriteOffInformation implements Serializable {

    // WriteOffInformation编号
    @TableId(value = "write_off_information_id", type = IdType.AUTO)
    private Integer write_off_information_id;

    // 卡券编码
    @TableField(value = "`card_code`")
    private String card_code;
    // 经理用户
    @TableField(value = "`manager_user`")
    private Integer manager_user;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 财务用户
    @TableField(value = "`financial_user`")
    private Integer financial_user;
    // 卡券名称
    @TableField(value = "`card_name`")
    private String card_name;
    // 有效期限
    @TableField(value = "`effective_period`")
    private String effective_period;
    // 使用次数
    @TableField(value = "`number_of_uses`")
    private String number_of_uses;
    // 发放时间
    @TableField(value = "`issuing_time`")
    private Timestamp issuing_time;
    // 核销时间
    @TableField(value = "`write_off_time`")
    private Timestamp write_off_time;
    // 核销单
    @TableField(value = "`write_off_doc`")
    private String write_off_doc;
    // 核销备注
    @TableField(value = "`write_off_remarks`")
    private String write_off_remarks;




















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
