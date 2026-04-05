package com.project.demo.controller;

import com.project.demo.entity.ManagerUser;
import com.project.demo.service.ManagerUserService;
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
 * 经理用户：(ManagerUser)表控制层
 *
 */
@RestController
@RequestMapping("/manager_user")
public class ManagerUserController extends BaseController<ManagerUser, ManagerUserService> {

    /**
     * 经理用户对象
     */
    @Autowired
    public ManagerUserController(ManagerUserService service) {
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
        ManagerUser manager_user = new ManagerUser();
            manager_user.setEmployee_work_number(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
                    Map<String, String> mapemployee_work_number = new HashMap<>();
        mapemployee_work_number.put("employee_work_number",String.valueOf(paramMap.get("employee_work_number")));
        List listemployee_work_number = service.selectBaseList(service.select(mapemployee_work_number, new HashMap<>()));
        if (listemployee_work_number.size()>0){
            return error(30000, "字段员工工号内容不能重复");
        }
                    manager_user.setEmployee_name(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                            manager_user.setEmployee_gender(paramMap.get("employee_gender")==null?null:String.valueOf(paramMap.get("employee_gender")));
                            manager_user.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                            manager_user.setTwo_dimensional_code(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
                        manager_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                                                                                                                        manager_user.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(manager_user);
        System.out.println("经理用户新增成功");
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
        ManagerUser manager_user = new ManagerUser();
            manager_user.setEmployee_work_number(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
                    manager_user.setEmployee_name(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                    manager_user.setEmployee_gender(paramMap.get("employee_gender")==null?null:String.valueOf(paramMap.get("employee_gender")));
                    manager_user.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    manager_user.setTwo_dimensional_code(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
                manager_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                            this.setEntity(queryMap,configMap,manager_user);
        System.out.println("经理用户修改成功");
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
        ManagerUser manager_user = JSON.parseObject(JSON.toJSONString(registeredMap),ManagerUser.class);

        if (manager_user != null) {
            // 查询用户附加信息
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("manager_user",manager_user);
            return result;
        } else {
            throw new RuntimeException("经理用户不存在}");
        }
    }
}
