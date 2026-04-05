package com.project.demo.controller;

import com.project.demo.entity.RescueRecord;
import com.project.demo.service.RescueRecordService;
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
 * 救援记录：(RescueRecord)表控制层
 *
 */
@RestController
@RequestMapping("/rescue_record")
public class RescueRecordController extends BaseController<RescueRecord, RescueRecordService> {

    /**
     * 救援记录对象
     */
    @Autowired
    public RescueRecordController(RescueRecordService service) {
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
        RescueRecord rescue_record = new RescueRecord();
            rescue_record.setRescue_code(paramMap.get("rescue_code")==null?null:String.valueOf(paramMap.get("rescue_code")));
                            rescue_record.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            rescue_record.setRescue_user(paramMap.get("rescue_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("rescue_user"))));
                            rescue_record.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                            rescue_record.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                            rescue_record.setAudit_status(paramMap.get("audit_status")==null?null:String.valueOf(paramMap.get("audit_status")));
                            rescue_record.setMaintenance_personnel(paramMap.get("maintenance_personnel")==null?null:String.valueOf(paramMap.get("maintenance_personnel")));
                            if (paramMap.get("maintenance_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("maintenance_time" )))) {
            String timStr = String.valueOf(paramMap.get("maintenance_time" ));
            rescue_record.setMaintenance_time(parseToTimestamp(timStr));
        } else {
            rescue_record.setMaintenance_time(null);
        }
                            rescue_record.setScene_picture(paramMap.get("scene_picture")==null?null:String.valueOf(paramMap.get("scene_picture")));
                            rescue_record.setMaintenance(paramMap.get("maintenance")==null?null:String.valueOf(paramMap.get("maintenance")));
                        rescue_record.setLocation_address(paramMap.get("location_address")==null?null:String.valueOf(paramMap.get("location_address")));
        rescue_record.setLocation_lng(paramMap.get("location_lng")==null?null:String.valueOf(paramMap.get("location_lng")));
        rescue_record.setLocation_lat(paramMap.get("location_lat")==null?null:String.valueOf(paramMap.get("location_lat")));
                                                                                                                        rescue_record.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(rescue_record);
        System.out.println("救援记录新增成功");
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
        RescueRecord rescue_record = new RescueRecord();
            rescue_record.setRescue_code(paramMap.get("rescue_code")==null?null:String.valueOf(paramMap.get("rescue_code")));
                    rescue_record.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    rescue_record.setRescue_user(paramMap.get("rescue_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("rescue_user"))));
                    rescue_record.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                    rescue_record.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    rescue_record.setAudit_status(paramMap.get("audit_status")==null?null:String.valueOf(paramMap.get("audit_status")));
                    rescue_record.setMaintenance_personnel(paramMap.get("maintenance_personnel")==null?null:String.valueOf(paramMap.get("maintenance_personnel")));
                    if (paramMap.get("maintenance_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("maintenance_time" )))) {
            String timStr = String.valueOf(paramMap.get("maintenance_time" ));
            rescue_record.setMaintenance_time(parseToTimestamp(timStr));
        } else {
            rescue_record.setMaintenance_time(null);
        }
                    rescue_record.setScene_picture(paramMap.get("scene_picture")==null?null:String.valueOf(paramMap.get("scene_picture")));
                    rescue_record.setMaintenance(paramMap.get("maintenance")==null?null:String.valueOf(paramMap.get("maintenance")));
                rescue_record.setLocation_address(paramMap.get("location_address")==null?null:String.valueOf(paramMap.get("location_address")));
        rescue_record.setLocation_lng(paramMap.get("location_lng")==null?null:String.valueOf(paramMap.get("location_lng")));
        rescue_record.setLocation_lat(paramMap.get("location_lat")==null?null:String.valueOf(paramMap.get("location_lat")));
                            this.setEntity(queryMap,configMap,rescue_record);
        System.out.println("救援记录修改成功");
        return success(1);
    }


}
