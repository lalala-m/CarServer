package com.project.demo.controller;

import com.project.demo.entity.QrCodeRegister;
import com.project.demo.service.QrCodeRegisterService;
import com.alibaba.fastjson.JSON;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 二维码注册标识表：(QrCodeRegister)表控制层
 *
 */
@RestController
@RequestMapping("/qr_code_register")
public class QrCodeRegisterController extends BaseController<QrCodeRegister, QrCodeRegisterService> {

    /**
     * 二维码注册标识表对象
     */
    @Autowired
    public QrCodeRegisterController(QrCodeRegisterService service) {
        setService(service);
    }


    /**
     * 新增二维码
     */
    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        QrCodeRegister qr_code_register = new QrCodeRegister();
            qr_code_register.setQrToken(paramMap.get("qr_token")==null?null:String.valueOf(paramMap.get("qr_token")));
            qr_code_register.setRoleType(paramMap.get("role_type")==null?null:String.valueOf(paramMap.get("role_type")));
            qr_code_register.setRoleId(paramMap.get("role_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("role_id"))));
            qr_code_register.setRoleName(paramMap.get("role_name")==null?null:String.valueOf(paramMap.get("role_name")));
            qr_code_register.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
            qr_code_register.setDepartment(paramMap.get("department")==null?null:String.valueOf(paramMap.get("department")));
            qr_code_register.setStatus(paramMap.get("status")==null?null:String.valueOf(paramMap.get("status")));
            qr_code_register.setBindUserId(paramMap.get("bind_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("bind_user_id"))));
            qr_code_register.setCreateBy(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(qr_code_register);
        System.out.println("二维码新增成功");
        return success(1);
    }

    /**
     * 修改二维码
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
        QrCodeRegister qr_code_register = new QrCodeRegister();
            qr_code_register.setQrToken(paramMap.get("qr_token")==null?null:String.valueOf(paramMap.get("qr_token")));
            qr_code_register.setRoleType(paramMap.get("role_type")==null?null:String.valueOf(paramMap.get("role_type")));
            qr_code_register.setRoleId(paramMap.get("role_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("role_id"))));
            qr_code_register.setRoleName(paramMap.get("role_name")==null?null:String.valueOf(paramMap.get("role_name")));
            qr_code_register.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
            qr_code_register.setDepartment(paramMap.get("department")==null?null:String.valueOf(paramMap.get("department")));
            qr_code_register.setStatus(paramMap.get("status")==null?null:String.valueOf(paramMap.get("status")));
            qr_code_register.setBindUserId(paramMap.get("bind_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("bind_user_id"))));
        this.setEntity(queryMap,configMap,qr_code_register);
        System.out.println("二维码修改成功");
        return success(1);
    }

    /**
     * 根据 qr_token 查询二维码信息
     */
    @GetMapping("/get_by_token")
    public Map<String, Object> getByToken(String qr_token) {
        if (qr_token == null || qr_token.trim().isEmpty()) {
            return error(30000, "qr_token不能为空");
        }
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("qr_token", qr_token);
        return this.getObjByMap(queryMap);
    }

    /**
     * 验证二维码是否有效（未使用且未过期）
     */
    @GetMapping("/validate")
    public Map<String, Object> validate(String qr_token) {
        if (qr_token == null || qr_token.trim().isEmpty()) {
            return error(30000, "qr_token不能为空");
        }
        
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("qr_token", qr_token);
        List resultList = service.selectBaseList(service.select(queryMap, new HashMap<>()));
        
        if (resultList.isEmpty()) {
            return error(30001, "二维码不存在");
        }
        
        QrCodeRegister qrCode = (QrCodeRegister) resultList.get(0);
        
        // 检查状态
        if (!"未使用".equals(qrCode.getStatus())) {
            return error(30002, "二维码已被使用或已失效");
        }
        
        // 检查过期时间
        if (qrCode.getExpireTime() != null) {
            if (qrCode.getExpireTime().before(new java.util.Date())) {
                return error(30003, "二维码已过期");
            }
        }
        
        return success(qrCode);
    }

    /**
     * 标记二维码为已使用
     */
    @PostMapping("/mark_used")
    @Transactional
    public Map<String, Object> markUsed(String qr_token, Integer userId, Integer roleId) {
        if (qr_token == null || qr_token.trim().isEmpty()) {
            return error(30000, "qr_token不能为空");
        }
        
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("qr_token", qr_token);
        
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("status", "已使用");
        if (userId != null) {
            paramMap.put("bind_user_id", userId);
        }
        if (roleId != null) {
            paramMap.put("role_id", roleId);
        }
        
        service.update(queryMap, new HashMap<>(), paramMap);
        return success(1);
    }
}
