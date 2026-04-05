package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.User;
import com.project.demo.entity.UserChatGroup;
import com.project.demo.entity.UserChatRead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户群聊：用于保存用户群聊信息Mapper接口
 *
 */
@Mapper
public interface UserChatGroupMapper extends BaseMapper<UserChatGroup>
{
    /**
     * 查询用户群聊
     */
    List<UserChatGroup> selectUserChatGroupByUserId(Integer userId);

    /**
     * 新增用户群聊
     */
    int insertUserChatGroup(UserChatGroup userChatGroup);

    /**
     * 删除用户群聊
     */
    int deleteUserChatGroup(@Param("groupId") Integer groupId,@Param("userId") Integer userId);

    /**
     * 查询用户群聊
     */
    List<Integer> selectUserByGroupId(@Param("groupId") Integer groupId,@Param("userId") Integer userId);

    int selectUserGroupCount(UserChatGroup userChatGroup);

    List<UserChatRead> selectReadList(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    List<User> selectUserChatGroupUser(@Param("groupId") Integer groupId);
}
