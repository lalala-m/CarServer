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
 * 发放卡券：(IssuingCardVoucher)表实体类
 *
 */
@TableName("`issuing_card_voucher`")
@Data
@EqualsAndHashCode(callSuper = false)
public class IssuingCardVoucher implements Serializable {

    // IssuingCardVoucher编号
    @TableId(value = "issuing_card_voucher_id", type = IdType.AUTO)
    private Integer issuing_card_voucher_id;

    // 卡券编码
    @TableField(value = "`card_code`")
    private String card_code;
    // 经理用户
    @TableField(value = "`manager_user`")
    private Integer manager_user;
    // 车主用户
    @TableField(value = "`owner_user`")
    private Integer owner_user;
    // 车牌号码
    @TableField(value = "`license_plate_number`")
    private String license_plate_number;
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
    // 核销状态
    @TableField(value = "`write_off_status`")
    private String write_off_status;





    // 审核状态
    @TableField(value = "examine_state")
    private String examine_state;



    // 审核回复
    @TableField(value = "examine_reply")
    private String examine_reply;











		// 卡券核销限制次数
	@TableField(value = "write_off_information_limit_times")
	private String write_off_information_limit_times;
	
					


                                                                                            


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
