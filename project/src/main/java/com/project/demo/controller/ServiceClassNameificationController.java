package com.project.demo.controller;

import com.project.demo.entity.ServiceClassNameification;
import com.project.demo.service.ServiceClassNameificationService;
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
 * 服务分类：(ServiceClassNameification)表控制层
 *
 */
@RestController
@RequestMapping("/service_class_nameification")
public class ServiceClassNameificationController extends BaseController<ServiceClassNameification, ServiceClassNameificationService> {

    /**
     * 服务分类对象
     */
    @Autowired
    public ServiceClassNameificationController(ServiceClassNameificationService service) {
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
        ServiceClassNameification service_class_nameification = new ServiceClassNameification();
            service_class_nameification.setService_type(paramMap.get("service_type")==null?null:String.valueOf(paramMap.get("service_type")));
                if (service_class_nameification.getService_type()== null || service_class_nameification.getService_type().trim().isEmpty()){
            throw new IllegalArgumentException("服务类型不能为空");
        }
                                                                                                                                    service_class_nameification.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(service_class_nameification);
        System.out.println("服务分类新增成功");
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
        ServiceClassNameification service_class_nameification = new ServiceClassNameification();
            service_class_nameification.setService_type(paramMap.get("service_type")==null?null:String.valueOf(paramMap.get("service_type")));
                                    this.setEntity(queryMap,configMap,service_class_nameification);
        System.out.println("服务分类修改成功");
        return success(1);
    }


}
