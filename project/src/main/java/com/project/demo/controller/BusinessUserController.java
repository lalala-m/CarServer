package com.project.demo.controller;

import com.project.demo.entity.BusinessUser;
import com.project.demo.service.BusinessUserService;
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
 * 商家用户：(BusinessUser)表控制层
 *
 */
@RestController
@RequestMapping("/business_user")
public class BusinessUserController extends BaseController<BusinessUser, BusinessUserService> {

    /**
     * 商家用户对象
     */
    @Autowired
    public BusinessUserController(BusinessUserService service) {
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
        BusinessUser business_user = new BusinessUser();
            business_user.setBusiness_name(paramMap.get("business_name")==null?null:String.valueOf(paramMap.get("business_name")));
                if (business_user.getBusiness_name()== null || business_user.getBusiness_name().trim().isEmpty()){
            throw new IllegalArgumentException("商家姓名不能为空");
        }
                        business_user.setBusiness_gender(paramMap.get("business_gender")==null?null:String.valueOf(paramMap.get("business_gender")));
                            business_user.setMobile_phone_number(paramMap.get("mobile_phone_number")==null?null:String.valueOf(paramMap.get("mobile_phone_number")));
                    Map<String, String> mapmobile_phone_number = new HashMap<>();
        mapmobile_phone_number.put("mobile_phone_number",String.valueOf(paramMap.get("mobile_phone_number")));
        List listmobile_phone_number = service.selectBaseList(service.select(mapmobile_phone_number, new HashMap<>()));
        if (listmobile_phone_number.size()>0){
            return error(30000, "字段手机号码内容不能重复");
        }
                    business_user.setStore_location(paramMap.get("store_location")==null?null:String.valueOf(paramMap.get("store_location")));
                        business_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                                                                                                                        business_user.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(business_user);
        System.out.println("商家用户新增成功");
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
        BusinessUser business_user = new BusinessUser();
            business_user.setBusiness_name(paramMap.get("business_name")==null?null:String.valueOf(paramMap.get("business_name")));
                    business_user.setBusiness_gender(paramMap.get("business_gender")==null?null:String.valueOf(paramMap.get("business_gender")));
                    business_user.setMobile_phone_number(paramMap.get("mobile_phone_number")==null?null:String.valueOf(paramMap.get("mobile_phone_number")));
                    business_user.setStore_location(paramMap.get("store_location")==null?null:String.valueOf(paramMap.get("store_location")));
                business_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                            this.setEntity(queryMap,configMap,business_user);
        System.out.println("商家用户修改成功");
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
        BusinessUser business_user = JSON.parseObject(JSON.toJSONString(registeredMap),BusinessUser.class);

        if (business_user != null) {
            // 查询用户附加信息
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("business_user",business_user);
            return result;
        } else {
            throw new RuntimeException("商家用户不存在}");
        }
    }
}
