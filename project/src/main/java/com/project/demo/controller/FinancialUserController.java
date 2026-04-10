package com.project.demo.controller;

import com.project.demo.entity.FinancialUser;
import com.project.demo.service.FinancialUserService;
import com.alibaba.fastjson.JSON;
import com.project.demo.entity.User;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
 * 财务用户：(FinancialUser)表控制层
 *
 */
@RestController
@RequestMapping("/financial_user")
public class FinancialUserController extends BaseController<FinancialUser, FinancialUserService> {

    /**
     * 财务用户对象
     */
    @Autowired
    public FinancialUserController(FinancialUserService service) {
        setService(service);
    }
    
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取当前登录用户ID
     */
    public Integer getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if (token == null || "".equals(token)) {
            return null;
        }
        try {
            Object tokenObj = redisTemplate.opsForValue().get(token);
            if (tokenObj != null) {
                com.project.demo.entity.AccessToken accessToken = 
                    JSON.parseObject(JSON.toJSONString(tokenObj), com.project.demo.entity.AccessToken.class);
                return accessToken.getUser_id();
            }
        } catch (Exception e) {
            System.err.println("获取用户ID失败: " + e.getMessage());
        }
        return null;
    }

    /**
     * 重写获取列表方法 - 添加权限控制
     */
    @Override
    @RequestMapping("/get_list")
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, String> queryMap = service.readQuery(request);
        Map<String, String> configMap = service.readConfig(request);
        
        // 获取当前用户ID
        Integer userId = getCurrentUserId(request);
        if (userId != null) {
            // 查询用户信息
            List userList = service.selectBaseList(
                "SELECT u.*, fu.store_id FROM `user` u " +
                "LEFT JOIN financial_user fu ON u.user_id = fu.user_id " +
                "WHERE u.user_id = " + userId
            );
            
            if (userList.size() > 0) {
                Map<String, Object> userMap = (Map<String, Object>) userList.get(0);
                String userGroup = (String) userMap.get("user_group");
                Object storeIdObj = userMap.get("store_id");
                
                // 如果是4S店管理员，自动添加store_id过滤
                if ("4S店管理员".equals(userGroup) && storeIdObj != null) {
                    Integer storeId = Integer.valueOf(String.valueOf(storeIdObj));
                    if (storeId != null && storeId > 0) {
                        // 添加store_id条件到查询
                        queryMap.put("store_id", String.valueOf(storeId));
                    }
                }
            }
        }
        
        Map<String, Object> map = service.selectToPage(queryMap, configMap);
        return success(map);
    }


    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        FinancialUser financialUser = new FinancialUser();
        financialUser.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
        Map<String, String> mapemployeeWorkNumber = new HashMap<>();
        mapemployeeWorkNumber.put("employee_work_number",String.valueOf(paramMap.get("employee_work_number")));
        List listemployeeWorkNumber = service.selectBaseList(service.select(mapemployeeWorkNumber, new HashMap<>()));
        if (listemployeeWorkNumber.size()>0){
            return error(30000, "字段员工工号内容不能重复");
        }
        financialUser.setEmployeeName(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
        financialUser.setFinancialGender(paramMap.get("financial_gender")==null?null:String.valueOf(paramMap.get("financial_gender")));
        financialUser.setContactNumber(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
        financialUser.setTwoDimensionalCode(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
        financialUser.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
        financialUser.setStoreId(paramMap.get("store_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("store_id"))));
        financialUser.setExamineState(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        financialUser.setCreateBy(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(financialUser);
        System.out.println("财务用户新增成功");
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
        FinancialUser financialUser = new FinancialUser();
        financialUser.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
        financialUser.setEmployeeName(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
        financialUser.setFinancialGender(paramMap.get("financial_gender")==null?null:String.valueOf(paramMap.get("financial_gender")));
        financialUser.setContactNumber(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
        financialUser.setTwoDimensionalCode(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
        financialUser.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
        financialUser.setStoreId(paramMap.get("store_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("store_id"))));
        financialUser.setExamineState(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        financialUser.setCreateBy(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.setEntity(queryMap,configMap,financialUser);
        System.out.println("财务用户修改成功");
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
        FinancialUser financialUser = JSON.parseObject(JSON.toJSONString(registeredMap),FinancialUser.class);

        if (financialUser != null) {
            // 查询用户附加信息
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("financial_user",financialUser);
            return result;
        } else {
            throw new RuntimeException("财务用户不存在}");
        }
    }
}
