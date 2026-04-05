package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.Follow;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户关注：(Follow)Mapper接口
 *
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow>{

}
