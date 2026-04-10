package com.project.demo.controller;

import com.project.demo.entity.InfoUser;
import com.project.demo.service.InfoUserService;
import com.alibaba.fastjson.JSON;
import com.project.demo.entity.User;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


/**
 * 信息员：(InfoUser)表控制层
 *
 */
@RestController
@RequestMapping("/info_user")
public class InfoUserController extends BaseController<InfoUser, InfoUserService> {

    /**
     * 信息员对象
     */
    @Autowired
    public InfoUserController(InfoUserService service) {
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
                "SELECT u.*, iu.store_id FROM `user` u " +
                "LEFT JOIN info_user iu ON u.user_id = iu.user_id " +
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


    /**
     * 新增信息员
     */
    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        InfoUser info_user = new InfoUser();
            info_user.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
                    Map<String, String> mapemployee_work_number = new HashMap<>();
        mapemployee_work_number.put("employee_work_number",String.valueOf(paramMap.get("employee_work_number")));
        List listemployee_work_number = service.selectBaseList(service.select(mapemployee_work_number, new HashMap<>()));
        if (listemployee_work_number.size()>0){
            return error(30000, "字段员工工号内容不能重复");
        }
                    info_user.setEmployeeName(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                            info_user.setEmployeeGender(paramMap.get("employee_gender")==null?null:String.valueOf(paramMap.get("employee_gender")));
                            info_user.setContactNumber(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                            info_user.setTwoDimensionalCode(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
                            info_user.setExamineState(paramMap.get("examine_state")==null?"待审核":String.valueOf(paramMap.get("examine_state")));
                        info_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                                                                                                                        info_user.setCreateBy(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(info_user);
        System.out.println("信息员新增成功");
        return success(1);
    }

    /**
     * 修改信息员
     */
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
        InfoUser info_user = new InfoUser();
            info_user.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
                    info_user.setEmployeeName(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                    info_user.setEmployeeGender(paramMap.get("employee_gender")==null?null:String.valueOf(paramMap.get("employee_gender")));
                    info_user.setContactNumber(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    info_user.setTwoDimensionalCode(paramMap.get("two_dimensional_code")==null?null:String.valueOf(paramMap.get("two_dimensional_code")));
                    info_user.setExamineState(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
                info_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                            this.setEntity(queryMap,configMap,info_user);
        System.out.println("信息员修改成功");
        return success(1);
    }


    /**
     * 根据 user_id 查询信息员及其附加表信息
     */
    @GetMapping("/get_user_by_id")
    public Map<String,Object> getUserById(Long userId) {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("user_id",String.valueOf(userId));
        Map<String,Object> registeredMap = this.getObjByMap(queryMap);
        InfoUser info_user = JSON.parseObject(JSON.toJSONString(registeredMap),InfoUser.class);

        if (info_user != null) {
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("info_user",info_user);
            return result;
        } else {
            throw new RuntimeException("信息员不存在");
        }
    }
}
