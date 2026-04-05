package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.FinancialUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 财务用户：(FinancialUser)Mapper接口
 *
 */
@Mapper
public interface FinancialUserMapper extends BaseMapper<FinancialUser>{

}
