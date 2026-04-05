package com.project.demo.service;

import com.alibaba.fastjson.JSON;
import com.project.demo.dao.UserChatFriendMapper;
import com.project.demo.entity.User;
import com.project.demo.entity.UserChatFriend;
import com.project.demo.entity.UserChatRead;
import com.project.demo.service.base.BaseService;
import com.project.demo.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户聊天
 *
 */
@Service
public class UserChatFriendService extends BaseService<UserChatFriend> {

    @Autowired
    UserChatFriendMapper userChatFriendMapper;

    public List<UserChatFriend> selectUserChatFriend(Integer userId) {
        return userChatFriendMapper.selectUserChatFriendByUserId(userId);
    }

    public int insertUserChatFriend(UserChatFriend userChatFriend) {
        int userChatFriendCount = userChatFriendMapper.selectUserChatFriend(userChatFriend);
        if (userChatFriendCount<=0){
            userChatFriendMapper.insertUserChatFriend(userChatFriend);
        }
        UserChatFriend reversFriend = new UserChatFriend();
        reversFriend.setUser_id(userChatFriend.getFriend_user_id());
        reversFriend.setFriend_user_id(userChatFriend.getUser_id());
        reversFriend.setFriend_user_name(userChatFriend.getUsername());
        int reversUserChatFriendCount = userChatFriendMapper.selectUserChatFriend(reversFriend);
        if (reversUserChatFriendCount<=0){
            userChatFriendMapper.insertUserChatFriend(reversFriend);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("message_type",5);
        map.put("user_id",userChatFriend.getUser_id());
        map.put("username",userChatFriend.getUsername());
        map.put("avatar",userChatFriend.getAvatar());
        try {
            new WebSocket().sendMessageTo(JSON.toJSONString(map),userChatFriend.getFriend_user_id());
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    public int deleteUserChatFriend(Integer userId,Integer userFriendId) {
        Map<String,Object> map = new HashMap<>();
        map.put("message_type",5);
        map.put("user_id",userId);
        try {
            new WebSocket().sendMessageTo(JSON.toJSONString(map),userFriendId);
        }catch (Exception e){
            e.printStackTrace();
        }
        userChatFriendMapper.deleteUserChatFriend(userId,userFriendId);
        return userChatFriendMapper.deleteUserChatFriend(userFriendId,userId);
    }

    public List<User> selectAddFriendList(Integer userId) {
        return userChatFriendMapper.selectAddFriendList(userId);
    }

    public List<UserChatRead> selectReadList(Integer userId, Integer sendUserId) {
        return userChatFriendMapper.selectReadList(userId,sendUserId);
    }
}


