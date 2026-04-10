package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.QrCodeRegister;
import org.apache.ibatis.annotations.Mapper;

/**
 * 二维码注册标识表：(QrCodeRegister)Mapper接口
 *
 */
@Mapper
public interface QrCodeRegisterMapper extends BaseMapper<QrCodeRegister>{

}
