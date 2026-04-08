package com.project.demo.controller;

import com.project.demo.entity.WarehouseInformation;
import com.project.demo.service.WarehouseInformationService;
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
 * 仓库信息：(WarehouseInformation)表控制层
 *
 */
@RestController
@RequestMapping("/warehouse_information")
public class WarehouseInformationController extends BaseController<WarehouseInformation, WarehouseInformationService> {

    /**
     * 仓库信息对象
     */
    @Autowired
    public WarehouseInformationController(WarehouseInformationService service) {
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
        WarehouseInformation warehouse_information = new WarehouseInformation();
            warehouse_information.setWarehouse_name(paramMap.get("warehouse_name")==null?null:String.valueOf(paramMap.get("warehouse_name")));
                if (warehouse_information.getWarehouse_name()== null || warehouse_information.getWarehouse_name().trim().isEmpty()){
            throw new IllegalArgumentException("仓库名称不能为空");
        }
                        warehouse_information.setWarehouse_picture(paramMap.get("warehouse_picture")==null?null:String.valueOf(paramMap.get("warehouse_picture")));
                            warehouse_information.setDefault_state(paramMap.get("default_state")==null?null:String.valueOf(paramMap.get("default_state")));
                            warehouse_information.setWarehouse_location(paramMap.get("warehouse_location")==null?null:String.valueOf(paramMap.get("warehouse_location")));
                            warehouse_information.setWarehouse_description(paramMap.get("warehouse_description")==null?null:String.valueOf(paramMap.get("warehouse_description")));
                                                                                                                                                                    warehouse_information.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(warehouse_information);
        System.out.println("仓库信息新增成功");
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
        WarehouseInformation warehouse_information = new WarehouseInformation();
            warehouse_information.setWarehouse_name(paramMap.get("warehouse_name")==null?null:String.valueOf(paramMap.get("warehouse_name")));
                    warehouse_information.setWarehouse_picture(paramMap.get("warehouse_picture")==null?null:String.valueOf(paramMap.get("warehouse_picture")));
                    warehouse_information.setDefault_state(paramMap.get("default_state")==null?null:String.valueOf(paramMap.get("default_state")));
                    warehouse_information.setWarehouse_location(paramMap.get("warehouse_location")==null?null:String.valueOf(paramMap.get("warehouse_location")));
                    warehouse_information.setWarehouse_description(paramMap.get("warehouse_description")==null?null:String.valueOf(paramMap.get("warehouse_description")));
                                            this.setEntity(queryMap,configMap,warehouse_information);
        System.out.println("仓库信息修改成功");
        return success(1);
    }


}
