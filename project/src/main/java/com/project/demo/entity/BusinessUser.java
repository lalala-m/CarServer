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
 * 商家用户：(BusinessUser)表实体类
 *
 */
@TableName("`business_user`")
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessUser implements Serializable {

    // BusinessUser编号
    @TableId(value = "business_user_id", type = IdType.AUTO)
    private Integer business_user_id;

    // 商家姓名
    @TableField(value = "`business_name`")
    private String business_name;
    // 商家性别
    @TableField(value = "`business_gender`")
    private String business_gender;
    // 手机号码
    @TableField(value = "`mobile_phone_number`")
    private String mobile_phone_number;
    // 门店位置
    @TableField(value = "`store_location`")
    private String store_location;









    // 用户编号
    @TableField(value = "user_id")
    private Integer userId;











							


                                                                                                                





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
