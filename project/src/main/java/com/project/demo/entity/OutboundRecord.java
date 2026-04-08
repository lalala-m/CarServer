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
 * 出库记录：(OutboundRecord)表实体类
 *
 */
@TableName("`outbound_record`")
@Data
@EqualsAndHashCode(callSuper = false)
public class OutboundRecord implements Serializable {

    // OutboundRecord编号
    @TableId(value = "outbound_record_id", type = IdType.AUTO)
    private Integer outbound_record_id;

    // 耗材编码
    @TableField(value = "`consumables_code`")
    private String consumables_code;
    // 耗材名称
    @TableField(value = "`consumables_name`")
    private String consumables_name;
    // 耗材规格
    @TableField(value = "`consumables_specifications`")
    private String consumables_specifications;
    // 财务用户
    @TableField(value = "`financial_user`")
    private Integer financial_user;
    // 出库时间
    @TableField(value = "`outbound_time`")
    private Timestamp outbound_time;
    // 出库数量
    @TableField(value = "`outbound_quantity`")
    private Double outbound_quantity;
    // 出库备注
    @TableField(value = "`outbound_note`")
    private String outbound_note;




















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
