package com.project.demo.controller;

import com.project.demo.entity.StoreAdmin;
import com.project.demo.service.StoreAdminService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 4S店管理员：(StoreAdmin)表控制层
 */
@RestController
@RequestMapping("/store_admin")
public class StoreAdminController extends BaseController<StoreAdmin, StoreAdminService> {

    @Autowired
    public StoreAdminController(StoreAdminService service) {
        setService(service);
    }

    /**
     * 获取列表（带分页）
     */
    @PostMapping("/get_list")
    public Map<String, Object> get_list(HttpServletRequest request) throws IOException {
        Map<String, String> queryMap = service.readQuery(request);
        Map<String, String> configMap = service.readConfig(request);
        
        Map<String, Object> map = service.selectToPage(queryMap, configMap);
        return success(map);
    }

    /**
     * 获取单个对象
     */
    @PostMapping("/get_obj")
    public Map<String, Object> get_obj(HttpServletRequest request) throws IOException {
        Map<String, String> queryMap = service.readQuery(request);
        Map<String, String> configMap = service.readConfig(request);
        
        List list = service.selectBaseList(service.select(queryMap, configMap));
        if (list.size() > 0) {
            return success(list.get(0));
        } else {
            return success(null);
        }
    }

    /**
     * 添加4S店管理员
     */
    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        
        StoreAdmin store_admin = new StoreAdmin();
        store_admin.setEmployeeWorkNumber(paramMap.get("employee_work_number")==null?null:String.valueOf(paramMap.get("employee_work_number")));
        
        // 检查员工工号是否重复
        Map<String, String> mapemployee_work_number = new HashMap<>();
        mapemployee_work_number.put("employee_work_number", String.valueOf(paramMap.get("employee_work_number")));
        List listemployee_work_number = service.selectBaseList(service.select(mapemployee_work_number, new HashMap<>()));
        if (listemployee_work_number.size()>0){
            return error(30000, "字段员工工号内容不能重复");
        }
        
        store_admin.setEmployeeName(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
        store_admin.setEmployeeGender(paramMap.get("employee_gender")==null?null:String.valueOf(paramMap.get("employee_gender")));
        store_admin.setContactNumber(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
        store_admin.setStoreId(paramMap.get("store_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("store_id"))));
        store_admin.setExamineState(paramMap.get("examine_state")==null?"待审核":String.valueOf(paramMap.get("examine_state")));
        store_admin.setUserState(paramMap.get("user_state")==null?"未启用":String.valueOf(paramMap.get("user_state")));
        store_admin.setUserId(paramMap.get("user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("user_id"))));
        
        this.addEntity(store_admin);
        return success(1);
    }

    /**
     * 删除4S店管理员
     */
    @PostMapping("/del")
    @Transactional
    public Map<String, Object> del(HttpServletRequest request) {
        Map<String,String> queryMap = service.readQuery(request);
        Map<String,String> configMap = service.readConfig(request);
        
        service.delete(queryMap, configMap);
        return success(1);
    }

}
