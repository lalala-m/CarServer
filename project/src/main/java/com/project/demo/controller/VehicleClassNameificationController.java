package com.project.demo.controller;

import com.project.demo.entity.VehicleClassNameification;
import com.project.demo.service.VehicleClassNameificationService;
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
 * 车型分类：(VehicleClassNameification)表控制层
 *
 */
@RestController
@RequestMapping("/vehicle_class_nameification")
public class VehicleClassNameificationController extends BaseController<VehicleClassNameification, VehicleClassNameificationService> {

    /**
     * 车型分类对象
     */
    @Autowired
    public VehicleClassNameificationController(VehicleClassNameificationService service) {
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
        VehicleClassNameification vehicle_class_nameification = new VehicleClassNameification();
            vehicle_class_nameification.setModel_name(paramMap.get("model_name")==null?null:String.valueOf(paramMap.get("model_name")));
                if (vehicle_class_nameification.getModel_name()== null || vehicle_class_nameification.getModel_name().trim().isEmpty()){
            throw new IllegalArgumentException("车型名称不能为空");
        }
                                                                                                                                    vehicle_class_nameification.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(vehicle_class_nameification);
        System.out.println("车型分类新增成功");
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
        VehicleClassNameification vehicle_class_nameification = new VehicleClassNameification();
            vehicle_class_nameification.setModel_name(paramMap.get("model_name")==null?null:String.valueOf(paramMap.get("model_name")));
                                    this.setEntity(queryMap,configMap,vehicle_class_nameification);
        System.out.println("车型分类修改成功");
        return success(1);
    }


}
