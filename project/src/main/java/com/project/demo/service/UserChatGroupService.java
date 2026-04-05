package com.project.demo.service;

import com.project.demo.dao.UserChatGroupMapper;
import com.project.demo.entity.User;
import com.project.demo.entity.UserChatGroup;
import com.project.demo.entity.UserChatRead;
import com.project.demo.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户群聊
 *
 */
@Service
public class UserChatGroupService extends BaseService<UserChatGroup> {

    @Autowired
    UserChatGroupMapper userChatGroupMapper;

    public List<UserChatGroup> selectUserChatGroup(Integer userId) {
        return userChatGroupMapper.selectUserChatGroupByUserId(userId);
    }

    public int insertUserChatGroup(UserChatGroup userChatGroup) {
        int groupCount = userChatGroupMapper.selectUserGroupCount(userChatGroup);
        if (groupCount<=0){
            userChatGroupMapper.insertUserChatGroup(userChatGroup);
        }
        return 1;
    }

    public int deleteUserChatGroup(Integer groupId,Integer userId) {
        return userChatGroupMapper.deleteUserChatGroup(groupId,userId);
    }

    public List<UserChatRead> selectReadList(Integer userId, Integer groupId) {
        return userChatGroupMapper.selectReadList(userId,groupId);
    }

    public List<User> selectUserChatGroupUser(Integer groupId) {
        return userChatGroupMapper.selectUserChatGroupUser(groupId);
    }
}


