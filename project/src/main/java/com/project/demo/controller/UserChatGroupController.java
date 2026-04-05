package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.UserChatGroup;
import com.project.demo.service.UserChatGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户群聊
 */
@Slf4j
@RestController
@RequestMapping("user_chat_group")
public class UserChatGroupController extends BaseController<UserChatGroup, UserChatGroupService> {
    /**
     * 服务对象
     */
    @Autowired
    public UserChatGroupController(UserChatGroupService service) {
        setService(service);
    }

    /**
     * 查询用户群聊
     */
    @GetMapping("get_group_list")
    public Map<String, Object> getGroupList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            map.put("list",service.selectUserChatGroup(userId));
            return success(map);
        }
    }

    /**
     * 查询群聊用户
     */
    @GetMapping("get_group_user_list")
    public Map<String, Object> getGroupUserList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String groupIdStr = query.get("group_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(groupIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer groupId = Integer.parseInt(groupIdStr);
            map.put("list",service.selectUserChatGroupUser(groupId));
            return success(map);
        }
    }

    /**
     * 新增用户群聊
     */
    @PostMapping("add_group")
    public Map<String, Object> addGroup(@RequestBody UserChatGroup userChatGroup) {
        return success(service.insertUserChatGroup(userChatGroup));
    }

    /**
     * 删除用户群聊
     */
    @GetMapping("del_group")
    public Map<String, Object> delGroup(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        String groupIdStr = query.get("group_id");
        if (StringUtils.isEmpty(userIdStr) || StringUtils.isEmpty(groupIdStr)){
            return success(1);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            Integer groupId = Integer.parseInt(groupIdStr);
            return success(service.deleteUserChatGroup(groupId,userId));
        }
    }

    /**
     * 查询用户群聊未读
     */
    @GetMapping("get_read_list")
    public Map<String, Object> getReadList(HttpServletRequest request) {
        Map<String,String> query =  service.readQuery(request);
        String userIdStr = query.get("user_id");
        String groupIdStr = query.get("group_id");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userIdStr) || StringUtils.isEmpty(groupIdStr)){
            map.put("list",new ArrayList<>());
            return success(map);
        }else {
            Integer userId = Integer.parseInt(userIdStr);
            Integer groupId = Integer.parseInt(groupIdStr);
            map.put("list",service.selectReadList(userId,groupId));
            return success(map);
        }
    }

}
