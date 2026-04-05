package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购买订单：(PurchaseOrder)Mapper接口
 *
 */
@Mapper
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder>{

}
