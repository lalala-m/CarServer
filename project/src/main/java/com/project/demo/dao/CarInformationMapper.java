package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.CarInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汽车信息：(CarInformation)Mapper接口
 *
 */
@Mapper
public interface CarInformationMapper extends BaseMapper<CarInformation>{

}
