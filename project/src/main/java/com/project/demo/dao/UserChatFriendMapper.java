package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.User;
import com.project.demo.entity.UserChatFriend;
import com.project.demo.entity.UserChatRead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户好友：用于保存用户好友信息Mapper接口
 *
 */
@Repository
@Mapper
public interface UserChatFriendMapper extends BaseMapper<UserChatFriend>
{
    /**
     * 查询用户好友
     */
    List<UserChatFriend> selectUserChatFriendByUserId(Integer userId);

    /**
     * 新增用户好友
     */
    int insertUserChatFriend(UserChatFriend userChatFriend);

    /**
     * 删除用户好友
     */
    int deleteUserChatFriend(@Param("userId") Integer userId, @Param("userFriendId") Integer userFriendId);

    /**
     * 查询可添加的用户好友
     */
    List<User> selectAddFriendList(Integer userId);

    int selectUserChatFriend(UserChatFriend userChatFriend);

    void insertUserChatRead(List<UserChatRead> list);

    void deleteUserRead(List<String> list);

    List<UserChatRead> selectReadList(@Param("userId") Integer userId, @Param("sendUserId") Integer sendUserId);
}
