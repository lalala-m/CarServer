package com.project.demo.controller;

import com.project.demo.entity.OwnerUser;
import com.project.demo.service.OwnerUserService;
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
 * 车主用户：(OwnerUser)表控制层
 *
 */
@RestController
@RequestMapping("/owner_user")
public class OwnerUserController extends BaseController<OwnerUser, OwnerUserService> {

    /**
     * 车主用户对象
     */
    @Autowired
    public OwnerUserController(OwnerUserService service) {
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
        OwnerUser owner_user = new OwnerUser();
            owner_user.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                            owner_user.setOwners_gender(paramMap.get("owners_gender")==null?null:String.valueOf(paramMap.get("owners_gender")));
                            owner_user.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    Map<String, String> mapcontact_number = new HashMap<>();
        mapcontact_number.put("contact_number",String.valueOf(paramMap.get("contact_number")));
        List listcontact_number = service.selectBaseList(service.select(mapcontact_number, new HashMap<>()));
        if (listcontact_number.size()>0){
            return error(30000, "字段联系号码内容不能重复");
        }
                    owner_user.setDriving_license(paramMap.get("driving_license")==null?null:String.valueOf(paramMap.get("driving_license")));
                            owner_user.setMember_level(paramMap.get("member_level")==null?null:String.valueOf(paramMap.get("member_level")));
                            owner_user.setAccumulate_points(paramMap.get("accumulate_points")==null?null:Double.valueOf(String.valueOf(paramMap.get("accumulate_points"))));
                        owner_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                                                                                                                        owner_user.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(owner_user);
        System.out.println("车主用户新增成功");
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
        OwnerUser owner_user = new OwnerUser();
            owner_user.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                    owner_user.setOwners_gender(paramMap.get("owners_gender")==null?null:String.valueOf(paramMap.get("owners_gender")));
                    owner_user.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    owner_user.setDriving_license(paramMap.get("driving_license")==null?null:String.valueOf(paramMap.get("driving_license")));
                    owner_user.setMember_level(paramMap.get("member_level")==null?null:String.valueOf(paramMap.get("member_level")));
                    owner_user.setAccumulate_points(paramMap.get("accumulate_points")==null?null:Double.valueOf(String.valueOf(paramMap.get("accumulate_points"))));
                owner_user.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
                            this.setEntity(queryMap,configMap,owner_user);
        System.out.println("车主用户修改成功");
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
        OwnerUser owner_user = JSON.parseObject(JSON.toJSONString(registeredMap),OwnerUser.class);

        if (owner_user != null) {
            // 查询用户附加信息
            Map<String,Object> userMap = this.getObjByMap(queryMap);
            User user = JSON.parseObject(JSON.toJSONString(userMap),User.class);

            Map<String,Object> result = new HashMap<>();
            result.put("user",user);
            result.put("owner_user",owner_user);
            return result;
        } else {
            throw new RuntimeException("车主用户不存在}");
        }
    }
}
