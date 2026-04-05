package com.project.demo.controller;

import com.project.demo.entity.SalesManager;
import com.project.demo.service.SalesManagerService;
import com.alibaba.fastjson.JSON;
import com.project.demo.entity.User;
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
 * 销售经理：(SalesManager)表控制层
 *
 */
@RestController
@RequestMapping("/sales_manager")
public class SalesManagerController extends BaseController<SalesManager, SalesManagerService> {

    /**
     * 销售经理对象
     */
    @Autowired
    public SalesManagerController(SalesManagerService service) {
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
        SalesManager sales_manager = new SalesManager();
            sales_manager.setManager_name(paramMap.get("manager_name")==null?null:String.valueOf(paramMap.get("manager_name")));
                            sales_manager.setGender_of_manager(paramMap.get("gender_of_manager")==null?null:String.valueOf(paramMap.get("gender_of_manager")));
                            sales_manager.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                        sales_manager.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                                                                                                                        sales_manager.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(sales_manager);
        System.out.println("销售经理新增成功");
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
        SalesManager sales_manager = new SalesManager();
            sales_manager.setManager_name(paramMap.get("manager_name")==null?null:String.valueOf(paramMap.get("manager_name")));
                    sales_manager.setGender_of_manager(paramMap.get("gender_of_manager")==null?null:String.valueOf(paramMap.get("gender_of_manager")));
                    sales_manager.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                sales_manager.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                            this.setEntity(queryMap,configMap,sales_manager);
        System.out.println("销售经理修改成功");
        return success(1);
    }


    /**
     * 根据 user_id 查询用户及其附加表信息
     *
     * @param userId 用户ID
     * @return 包含用户信息和附加信息的对象
     */
    @GetMapping("/get_user_by_id")
    public Map<String,Object> getUserById(Long userId) {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("user_id",String.valueOf(userId));
        // 查询用户基本信息
        Map<String,Object> registeredMap = this.getObjByMap(queryMap);
        SalesManager sales_manager = JSON.parseObject(JSON.toJSONString(registeredMap),SalesManager.class);

        if (sales_manager != null) {
            // 查询用户附加信息
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("sales_manager",sales_manager);
            return result;
        } else {
            throw new RuntimeException("销售经理不存在}");
        }
    }
}
