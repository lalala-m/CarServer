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
 * 品牌分类：(BrandClassNameification)表实体类
 *
 */
@TableName("`brand_class_nameification`")
@Data
@EqualsAndHashCode(callSuper = false)
public class BrandClassNameification implements Serializable {

    // BrandClassNameification编号
    @TableId(value = "brand_class_nameification_id", type = IdType.AUTO)
    private Integer brand_class_nameification_id;

    // 品牌名称
    @TableField(value = "`brand_name`")
    private String brand_name;




















							


                                                                                                                





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
