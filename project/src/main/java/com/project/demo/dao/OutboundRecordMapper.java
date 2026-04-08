package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.OutboundRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出库记录：(OutboundRecord)Mapper接口
 *
 */
@Mapper
public interface OutboundRecordMapper extends BaseMapper<OutboundRecord>{

}
