package com.project.demo.controller;

import com.project.demo.entity.CustomerChatGroup;
import com.project.demo.service.CustomerChatGroupService;
import com.alibaba.fastjson.JSON;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


/**
 * 在线沟通群组：(CustomerChatGroup)表控制层
 *
 */
@RestController
@RequestMapping("/customer_chat_group")
public class CustomerChatGroupController extends BaseController<CustomerChatGroup, CustomerChatGroupService> {

    /**
     * 在线沟通群组对象
     */
    @Autowired
    public CustomerChatGroupController(CustomerChatGroupService service) {
        setService(service);
    }



    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        CustomerChatGroup customer_chat_group = new CustomerChatGroup();
            customer_chat_group.setMember(paramMap.get("member")==null?null:String.valueOf(paramMap.get("member")));
                customer_chat_group.setRead_state(paramMap.get("read_state")==null?null:String.valueOf(paramMap.get("read_state")));
                customer_chat_group.setLast_read_time(paramMap.get("last_read_time")==null?null:String.valueOf(paramMap.get("last_read_time")));
                customer_chat_group.setUnread_count(paramMap.get("unread_count")==null?null:String.valueOf(paramMap.get("unread_count")));
            this.addEntity(customer_chat_group);
        System.out.println("在线沟通群组新增成功");
        return success(1);
    }

    @PostMapping("/set")
    @Transactional
    public Map<String, Object> set(HttpServletRequest request) throws IOException {
        Map<String,String> queryMap = service.readQuery(request);
        Map<String,String> configMap = service.readConfig(request);
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        CustomerChatGroup customer_chat_group = new CustomerChatGroup();
            customer_chat_group.setMember(paramMap.get("member")==null?null:String.valueOf(paramMap.get("member")));
                customer_chat_group.setRead_state(paramMap.get("read_state")==null?null:String.valueOf(paramMap.get("read_state")));
                customer_chat_group.setLast_read_time(paramMap.get("last_read_time")==null?null:String.valueOf(paramMap.get("last_read_time")));
                customer_chat_group.setUnread_count(paramMap.get("unread_count")==null?null:String.valueOf(paramMap.get("unread_count")));
            this.setEntity(queryMap,configMap,customer_chat_group);
        System.out.println("在线沟通群组修改成功");
        return success(1);
    }




}
