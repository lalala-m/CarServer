package com.project.demo.controller;

import com.project.demo.entity.CustomerChatLog;
import com.project.demo.service.CustomerChatLogService;
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
 * 在线沟通记录：(CustomerChatLog)表控制层
 *
 */
@RestController
@RequestMapping("/customer_chat_log")
public class CustomerChatLogController extends BaseController<CustomerChatLog, CustomerChatLogService> {

    /**
     * 在线沟通记录对象
     */
    @Autowired
    public CustomerChatLogController(CustomerChatLogService service) {
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
        CustomerChatLog customer_chat_log = new CustomerChatLog();
            customer_chat_log.setCustomer_chat_id(paramMap.get("customer_chat_id")==null?null:String.valueOf(paramMap.get("customer_chat_id")));
                customer_chat_log.setContent(paramMap.get("content")==null?null:String.valueOf(paramMap.get("content")));
            this.addEntity(customer_chat_log);
        System.out.println("在线沟通记录新增成功");
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
        CustomerChatLog customer_chat_log = new CustomerChatLog();
            customer_chat_log.setCustomer_chat_id(paramMap.get("customer_chat_id")==null?null:String.valueOf(paramMap.get("customer_chat_id")));
                customer_chat_log.setContent(paramMap.get("content")==null?null:String.valueOf(paramMap.get("content")));
            this.setEntity(queryMap,configMap,customer_chat_log);
        System.out.println("在线沟通记录修改成功");
        return success(1);
    }




}
