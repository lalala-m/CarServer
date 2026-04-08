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
 * 服务分类：(ServiceClassNameification)表实体类
 *
 */
@TableName("`service_class_nameification`")
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceClassNameification implements Serializable {

    // ServiceClassNameification编号
    @TableId(value = "service_class_nameification_id", type = IdType.AUTO)
    private Integer service_class_nameification_id;

    // 服务类型
    @TableField(value = "`service_type`")
    private String service_type;




















							


                                                                                                                





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
