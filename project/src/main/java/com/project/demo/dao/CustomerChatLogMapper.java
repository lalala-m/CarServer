package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.CustomerChatLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 在线沟通记录：(CustomerChatLog)Mapper接口
 *
 */
@Mapper
public interface CustomerChatLogMapper extends BaseMapper<CustomerChatLog>{

}
