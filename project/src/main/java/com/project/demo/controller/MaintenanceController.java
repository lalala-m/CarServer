package com.project.demo.controller;

import com.project.demo.entity.Maintenance;
import com.project.demo.service.MaintenanceService;
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
 * 维修养护：(Maintenance)表控制层
 *
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceController extends BaseController<Maintenance, MaintenanceService> {

    /**
     * 维修养护对象
     */
    @Autowired
    public MaintenanceController(MaintenanceService service) {
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
        Maintenance maintenance = new Maintenance();
            maintenance.setProject_bundle(paramMap.get("project_bundle")==null?null:String.valueOf(paramMap.get("project_bundle")));
                if (maintenance.getProject_bundle()== null || maintenance.getProject_bundle().trim().isEmpty()){
            throw new IllegalArgumentException("项目套餐不能为空");
        }
                        maintenance.setProject_picture(paramMap.get("project_picture")==null?null:String.valueOf(paramMap.get("project_picture")));
                            maintenance.setProject_price(paramMap.get("project_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("project_price"))));
                            maintenance.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                            maintenance.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            maintenance.setProject_description(paramMap.get("project_description")==null?null:String.valueOf(paramMap.get("project_description")));
                            maintenance.setProject_content(paramMap.get("project_content")==null?null:String.valueOf(paramMap.get("project_content")));
                        maintenance.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        maintenance.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        maintenance.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            maintenance.setMaintenance_order_limit_times(paramMap.get("maintenance_order_limit_times")==null?null:String.valueOf(paramMap.get("maintenance_order_limit_times")));
                                                                                                                            maintenance.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(maintenance);
        System.out.println("维修养护新增成功");
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
        Maintenance maintenance = new Maintenance();
            maintenance.setProject_bundle(paramMap.get("project_bundle")==null?null:String.valueOf(paramMap.get("project_bundle")));
                    maintenance.setProject_picture(paramMap.get("project_picture")==null?null:String.valueOf(paramMap.get("project_picture")));
                    maintenance.setProject_price(paramMap.get("project_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("project_price"))));
                    maintenance.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                    maintenance.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    maintenance.setProject_description(paramMap.get("project_description")==null?null:String.valueOf(paramMap.get("project_description")));
                    maintenance.setProject_content(paramMap.get("project_content")==null?null:String.valueOf(paramMap.get("project_content")));
                maintenance.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        maintenance.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        maintenance.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            maintenance.setMaintenance_order_limit_times(paramMap.get("maintenance_order_limit_times")==null?null:String.valueOf(paramMap.get("maintenance_order_limit_times")));
                                this.setEntity(queryMap,configMap,maintenance);
        System.out.println("维修养护修改成功");
        return success(1);
    }


}
