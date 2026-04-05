package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.UserChatFriend;
import com.project.demo.service.UserChatFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户聊天
 */
@Slf4j
@RestController
@RequestMapping("user_chat_friend")
public class UserChatFriendController extends BaseController<UserChatFriend, UserChatFriendService> {
    /**
     * 服务对象
     */
    @Autowired
    public UserChatFriendController(UserChatFriendService service) {
        setService(service);
    }

    /**
     * 查询用户好友
     */
    @GetMapping("get_friend_list")
    public Map<String, Object> getFriendList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            map.put("list",service.selectUserChatFriend(userId));
            return success(map);
        }
    }

    /**
     * 新增用户好友
     */
    @PostMapping("add_friend")
    public Map<String, Object> addFriend(@RequestBody UserChatFriend userChatFriend) {
        return success(service.insertUserChatFriend(userChatFriend));
    }

    /**
     * 删除用户好友
     */
    @GetMapping("del_friend")
    public Map<String, Object> delFriend(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userFriendIdStr = query.get("friend_user_id");
        String userIdStr = query.get("user_id");
        if (StringUtils.isEmpty(userFriendIdStr) || StringUtils.isEmpty(userIdStr)){
            return success(1);
        }else {
            Integer userFriendId = Integer.parseInt(userFriendIdStr);
            Integer userId = Integer.parseInt(userIdStr);
            return success(service.deleteUserChatFriend(userId,userFriendId));
        }
    }

    /**
     * 查询可添加的好友
     */
    @GetMapping("get_add_friend_list")
    public Map<String, Object> getAddFriendList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            map.put("list",service.selectAddFriendList(userId));
            return success(map);
        }
    }


    /**
     * 查询用户好友未读
     */
    @GetMapping("get_read_list")
    public Map<String, Object> getReadList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        String sendUserIdStr = query.get("send_user_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userIdStr) || StringUtils.isEmpty(sendUserIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            Integer sendUserId = Integer.parseInt(sendUserIdStr);
            map.put("list",service.selectReadList(userId,sendUserId));
            return success(map);
        }
    }



}
