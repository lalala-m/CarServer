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
 * 车主用户：(OwnerUser)表实体类
 *
 */
@TableName("`owner_user`")
@Data
@EqualsAndHashCode(callSuper = false)
public class OwnerUser implements Serializable {

    // OwnerUser编号
    @TableId(value = "owner_user_id", type = IdType.AUTO)
    private Integer owner_user_id;

    // 车主姓名
    @TableField(value = "`owners_name`")
    private String owners_name;
    // 车主性别
    @TableField(value = "`owners_gender`")
    private String owners_gender;
    // 联系号码
    @TableField(value = "`contact_number`")
    private String contact_number;
    // 驾驶证件
    @TableField(value = "`driving_license`")
    private String driving_license;
    // 会员等级
    @TableField(value = "`member_level`")
    private String member_level;
    // 积攒积分
    @TableField(value = "`accumulate_points`")
    private Double accumulate_points;









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
