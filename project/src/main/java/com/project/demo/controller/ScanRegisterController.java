package com.project.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.demo.entity.*;
import com.project.demo.service.*;
import com.project.demo.dao.UserMapper;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 扫码注册控制层
 * 支持会员，信息员、业务顾问、财务审核四种角色的扫码注册
 */
@RestController
@RequestMapping("/scan_register")
public class ScanRegisterController {

    public static final String ROLE_MEMBER = "member";
    public static final String ROLE_INFO = "info";
    public static final String ROLE_CONSULTANT = "consultant";
    public static final String ROLE_FINANCIAL = "financial";

    @Autowired
    private QrCodeRegisterService qrCodeRegisterService;

    @Autowired
    private OwnerUserService ownerUserService;

    @Autowired
    private InfoUserService infoUserService;

    @Autowired
    private BusinessConsultantService businessConsultantService;

    @Autowired
    private FinancialUserService financialUserService;

    @Autowired
    private UserMapper userMapper;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @GetMapping("/verify")
    public Map<String, Object> verify(String qr_token) {
        if (qr_token == null || qr_token.trim().isEmpty()) {
            return error(30000, "qr_token不能为空");
        }

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("qr_token", qr_token);
        List resultList = qrCodeRegisterService.selectBaseList(
            qrCodeRegisterService.select(queryMap, new HashMap<>())
        );

        if (resultList.isEmpty()) {
            return error(30001, "二维码不存在");
        }

        QrCodeRegister qrCode = (QrCodeRegister) resultList.get(0);

        if (!"未使用".equals(qrCode.getStatus())) {
            return error(30002, "二维码已被使用或已失效");
        }

        if (qrCode.getExpireTime() != null) {
            if (qrCode.getExpireTime().before(new Date())) {
                return error(30003, "二维码已过期");
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("role_type", qrCode.getRoleType());
        result.put("role_name", qrCode.getRoleName());
        result.put("employee_work_number", qrCode.getEmployeeWorkNumber());
        result.put("department", qrCode.getDepartment());

        Map<String, Object> formFields = getFormFields(qrCode.getRoleType());
        result.put("form_fields", formFields);

        return success(result);
    }

    private Map<String, Object> getFormFields(String roleType) {
        Map<String, Object> fields = new HashMap<>();

        switch (roleType) {
            case ROLE_MEMBER:
                fields.put("required", new String[]{"owners_name", "contact_number"});
                fields.put("optional", new String[]{"owners_gender", "driving_license"});
                break;
            case ROLE_INFO:
                fields.put("required", new String[]{"employee_name", "employee_work_number"});
                fields.put("optional", new String[]{"employee_gender", "contact_number"});
                break;
            case ROLE_CONSULTANT:
                fields.put("required", new String[]{"employee_name", "employee_work_number"});
                fields.put("optional", new String[]{"consultant_gender", "contact_number", "department"});
                break;
            case ROLE_FINANCIAL:
                fields.put("required", new String[]{"employee_name", "employee_work_number"});
                fields.put("optional", new String[]{"financial_gender", "contact_number"});
                break;
        }

        return fields;
    }

    @PostMapping("/register")
    @Transactional
    public Map<String, Object> register(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonBody = JSON.parseObject(sb.toString());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(jsonBody);

        String qr_token = paramMap.get("qr_token") != null ? paramMap.get("qr_token").toString() : null;
        String role_type = paramMap.get("role_type") != null ? paramMap.get("role_type").toString() : null;

        if (qr_token == null || qr_token.trim().isEmpty()) {
            return error(30000, "qr_token不能为空");
        }
        if (role_type == null || role_type.trim().isEmpty()) {
            return error(30001, "role_type不能为空");
        }

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("qr_token", qr_token);
        List resultList = qrCodeRegisterService.selectBaseList(
            qrCodeRegisterService.select(queryMap, new HashMap<>())
        );

        if (resultList.isEmpty()) {
            return error(30002, "二维码不存在");
        }

        QrCodeRegister qrCode = (QrCodeRegister) resultList.get(0);

        if (!"未使用".equals(qrCode.getStatus())) {
            return error(30003, "二维码已被使用或已失效");
        }

        if (qrCode.getExpireTime() != null && qrCode.getExpireTime().before(new Date())) {
            return error(30004, "二维码已过期");
        }

        Integer userId = null;
        Integer roleId = null;

        switch (role_type) {
            case ROLE_MEMBER:
                Map<String, Object> memberResult = registerMember(paramMap);
                if ((int)memberResult.get("code") != 200) {
                    throw new RuntimeException((String)memberResult.get("message"));
                }
                userId = (Integer) memberResult.get("user_id");
                roleId = (Integer) memberResult.get("role_id");
                break;

            case ROLE_INFO:
                Map<String, Object> infoResult = registerInfoUser(paramMap);
                if ((int)infoResult.get("code") != 200) {
                    throw new RuntimeException((String)infoResult.get("message"));
                }
                userId = (Integer) infoResult.get("user_id");
                roleId = (Integer) infoResult.get("role_id");
                break;

            case ROLE_CONSULTANT:
                Map<String, Object> consultantResult = registerConsultant(paramMap);
                if ((int)consultantResult.get("code") != 200) {
                    throw new RuntimeException((String)consultantResult.get("message"));
                }
                userId = (Integer) consultantResult.get("user_id");
                roleId = (Integer) consultantResult.get("role_id");
                break;

            case ROLE_FINANCIAL:
                Map<String, Object> financialResult = registerFinancial(paramMap);
                if ((int)financialResult.get("code") != 200) {
                    throw new RuntimeException((String)financialResult.get("message"));
                }
                userId = (Integer) financialResult.get("user_id");
                roleId = (Integer) financialResult.get("role_id");
                break;

            default:
                return error(30005, "不支持的角色类型");
        }

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("status", "已使用");
        updateMap.put("bind_user_id", userId);
        updateMap.put("role_id", roleId);
        qrCodeRegisterService.update(queryMap, new HashMap<>(), updateMap);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("user_id", userId);
        result.put("role_id", roleId);
        result.put("role_type", role_type);

        return result;
    }

    private Map<String, Object> registerMember(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        String owners_name = paramMap.get("owners_name") != null ? paramMap.get("owners_name").toString() : null;
        String contact_number = paramMap.get("contact_number") != null ? paramMap.get("contact_number").toString() : null;

        if (owners_name == null || owners_name.trim().isEmpty()) {
            result.put("code", 30010);
            result.put("message", "车主姓名不能为空");
            return result;
        }
        if (contact_number == null || contact_number.trim().isEmpty()) {
            result.put("code", 30011);
            result.put("message", "联系号码不能为空");
            return result;
        }
        if (!PHONE_PATTERN.matcher(contact_number).matches()) {
            result.put("code", 30012);
            result.put("message", "手机号格式不正确");
            return result;
        }

        User user = new User();
        user.setUserGroup("车主用户");
        user.setUsername(contact_number);
        user.setNickname(owners_name);
        user.setPhone(contact_number);
        user.setState(1);
        user.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userMapper.insertUser(user);
        Integer userId = user.getUserId();

        OwnerUser ownerUser = new OwnerUser();
        ownerUser.setOwnersName(owners_name);
        ownerUser.setOwnersGender(paramMap.get("owners_gender") != null ? paramMap.get("owners_gender").toString() : null);
        ownerUser.setContactNumber(contact_number);
        ownerUser.setDrivingLicense(paramMap.get("driving_license") != null ? paramMap.get("driving_license").toString() : null);
        ownerUser.setMemberLevel("普通会员");
        ownerUser.setExamineState("待审核");
        ownerUser.setUserId(userId);
        ownerUser.setCreateBy(1);
        ownerUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        ownerUserService.insert(ownerUser);

        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("user_id", userId);
        result.put("role_id", ownerUser.getOwnerUserId());
        return result;
    }

    private Map<String, Object> registerInfoUser(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        String employee_name = paramMap.get("employee_name") != null ? paramMap.get("employee_name").toString() : null;
        String employee_work_number = paramMap.get("employee_work_number") != null ? paramMap.get("employee_work_number").toString() : null;

        if (employee_name == null || employee_name.trim().isEmpty()) {
            result.put("code", 30020);
            result.put("message", "员工姓名不能为空");
            return result;
        }
        if (employee_work_number == null || employee_work_number.trim().isEmpty()) {
            result.put("code", 30021);
            result.put("message", "员工工号不能为空");
            return result;
        }

        Map<String, String> workNumberQuery = new HashMap<>();
        workNumberQuery.put("employee_work_number", employee_work_number);
        List existList = infoUserService.selectBaseList(
            infoUserService.select(workNumberQuery, new HashMap<>())
        );
        if (!existList.isEmpty()) {
            result.put("code", 30022);
            result.put("message", "员工工号已存在");
            return result;
        }

        String contact_number = paramMap.get("contact_number") != null ? paramMap.get("contact_number").toString() : null;
        if (contact_number != null && !contact_number.trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(contact_number).matches()) {
                result.put("code", 30023);
                result.put("message", "手机号格式不正确");
                return result;
            }
        }

        User user = new User();
        user.setUserGroup("信息员");
        user.setUsername(employee_work_number);
        user.setNickname(employee_name);
        user.setPhone(contact_number);
        user.setState(1);
        user.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userMapper.insertUser(user);
        Integer userId = user.getUserId();

        InfoUser infoUser = new InfoUser();
        infoUser.setEmployeeWorkNumber(employee_work_number);
        infoUser.setEmployeeName(employee_name);
        infoUser.setEmployeeGender(paramMap.get("employee_gender") != null ? paramMap.get("employee_gender").toString() : null);
        infoUser.setContactNumber(contact_number);
        infoUser.setExamineState("待审核");
        infoUser.setUserId(userId);
        infoUser.setCreateBy(1);
        infoUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserService.insert(infoUser);

        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("user_id", userId);
        result.put("role_id", infoUser.getInfoUserId());
        return result;
    }

    private Map<String, Object> registerConsultant(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        String employee_name = paramMap.get("employee_name") != null ? paramMap.get("employee_name").toString() : null;
        String employee_work_number = paramMap.get("employee_work_number") != null ? paramMap.get("employee_work_number").toString() : null;

        if (employee_name == null || employee_name.trim().isEmpty()) {
            result.put("code", 30030);
            result.put("message", "员工姓名不能为空");
            return result;
        }
        if (employee_work_number == null || employee_work_number.trim().isEmpty()) {
            result.put("code", 30031);
            result.put("message", "员工工号不能为空");
            return result;
        }

        Map<String, String> workNumberQuery = new HashMap<>();
        workNumberQuery.put("employee_work_number", employee_work_number);
        List existList = businessConsultantService.selectBaseList(
            businessConsultantService.select(workNumberQuery, new HashMap<>())
        );
        if (!existList.isEmpty()) {
            result.put("code", 30032);
            result.put("message", "员工工号已存在");
            return result;
        }

        String contact_number = paramMap.get("contact_number") != null ? paramMap.get("contact_number").toString() : null;
        if (contact_number != null && !contact_number.trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(contact_number).matches()) {
                result.put("code", 30033);
                result.put("message", "手机号格式不正确");
                return result;
            }
        }

        User user = new User();
        user.setUserGroup("业务顾问");
        user.setUsername(employee_work_number);
        user.setNickname(employee_name);
        user.setPhone(contact_number);
        user.setState(1);
        user.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userMapper.insertUser(user);
        Integer userId = user.getUserId();

        BusinessConsultant consultant = new BusinessConsultant();
        consultant.setEmployeeWorkNumber(employee_work_number);
        consultant.setEmployeeName(employee_name);
        consultant.setConsultantGender(paramMap.get("consultant_gender") != null ? paramMap.get("consultant_gender").toString() : null);
        consultant.setContactNumber(contact_number);
        consultant.setDepartment(paramMap.get("department") != null ? paramMap.get("department").toString() : null);
        consultant.setExamineState("待审核");
        consultant.setUserId(userId);
        consultant.setCreateBy(1);
        consultant.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        businessConsultantService.insert(consultant);

        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("user_id", userId);
        result.put("role_id", consultant.getConsultantId());
        return result;
    }

    private Map<String, Object> registerFinancial(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        String employee_name = paramMap.get("employee_name") != null ? paramMap.get("employee_name").toString() : null;
        String employee_work_number = paramMap.get("employee_work_number") != null ? paramMap.get("employee_work_number").toString() : null;

        if (employee_name == null || employee_name.trim().isEmpty()) {
            result.put("code", 30040);
            result.put("message", "员工姓名不能为空");
            return result;
        }
        if (employee_work_number == null || employee_work_number.trim().isEmpty()) {
            result.put("code", 30041);
            result.put("message", "员工工号不能为空");
            return result;
        }

        Map<String, String> workNumberQuery = new HashMap<>();
        workNumberQuery.put("employee_work_number", employee_work_number);
        List existList = financialUserService.selectBaseList(
            financialUserService.select(workNumberQuery, new HashMap<>())
        );
        if (!existList.isEmpty()) {
            result.put("code", 30042);
            result.put("message", "员工工号已存在");
            return result;
        }

        String contact_number = paramMap.get("contact_number") != null ? paramMap.get("contact_number").toString() : null;
        if (contact_number != null && !contact_number.trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(contact_number).matches()) {
                result.put("code", 30043);
                result.put("message", "手机号格式不正确");
                return result;
            }
        }

        User user = new User();
        user.setUserGroup("财务用户");
        user.setUsername(employee_work_number);
        user.setNickname(employee_name);
        user.setPhone(contact_number);
        user.setState(1);
        user.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userMapper.insertUser(user);
        Integer userId = user.getUserId();

        FinancialUser financialUser = new FinancialUser();
        financialUser.setEmployeeWorkNumber(employee_work_number);
        financialUser.setEmployeeName(employee_name);
        financialUser.setFinancialGender(paramMap.get("financial_gender") != null ? paramMap.get("financial_gender").toString() : null);
        financialUser.setContactNumber(contact_number);
        financialUser.setExamineState("待审核");
        financialUser.setUserId(userId);
        financialUser.setCreateBy(1);
        financialUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        financialUserService.insert(financialUser);

        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("user_id", userId);
        result.put("role_id", financialUser.getFinancialUserId());
        return result;
    }

    private Map<String, Object> success(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", data);
        return map;
    }

    private Map<String, Object> error(Integer code, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        return map;
    }
}
