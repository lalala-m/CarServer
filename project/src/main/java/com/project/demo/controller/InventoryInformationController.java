package com.project.demo.controller;

import com.project.demo.entity.InventoryInformation;
import com.project.demo.service.InventoryInformationService;
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
 * 库存信息：(InventoryInformation)表控制层
 *
 */
@RestController
@RequestMapping("/inventory_information")
public class InventoryInformationController extends BaseController<InventoryInformation, InventoryInformationService> {

    /**
     * 库存信息对象
     */
    @Autowired
    public InventoryInformationController(InventoryInformationService service) {
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
        InventoryInformation inventory_information = new InventoryInformation();
            inventory_information.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                    Map<String, String> mapconsumables_code = new HashMap<>();
        mapconsumables_code.put("consumables_code",String.valueOf(paramMap.get("consumables_code")));
        List listconsumables_code = service.selectBaseList(service.select(mapconsumables_code, new HashMap<>()));
        if (listconsumables_code.size()>0){
            return error(30000, "字段耗材编码内容不能重复");
        }
                    inventory_information.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                            inventory_information.setConsumables_inventory(paramMap.get("consumables_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("consumables_inventory"))));
                            inventory_information.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                            inventory_information.setIntroduction_of_consumables(paramMap.get("introduction_of_consumables")==null?null:String.valueOf(paramMap.get("introduction_of_consumables")));
                            inventory_information.setInbound_record_limit_times(paramMap.get("inbound_record_limit_times")==null?null:String.valueOf(paramMap.get("inbound_record_limit_times")));
            inventory_information.setOutbound_record_limit_times(paramMap.get("outbound_record_limit_times")==null?null:String.valueOf(paramMap.get("outbound_record_limit_times")));
                                                                                                                                                        inventory_information.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(inventory_information);
        System.out.println("库存信息新增成功");
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
        InventoryInformation inventory_information = new InventoryInformation();
            inventory_information.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                    inventory_information.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                    inventory_information.setConsumables_inventory(paramMap.get("consumables_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("consumables_inventory"))));
                    inventory_information.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                    inventory_information.setIntroduction_of_consumables(paramMap.get("introduction_of_consumables")==null?null:String.valueOf(paramMap.get("introduction_of_consumables")));
                    inventory_information.setInbound_record_limit_times(paramMap.get("inbound_record_limit_times")==null?null:String.valueOf(paramMap.get("inbound_record_limit_times")));
            inventory_information.setOutbound_record_limit_times(paramMap.get("outbound_record_limit_times")==null?null:String.valueOf(paramMap.get("outbound_record_limit_times")));
                                        this.setEntity(queryMap,configMap,inventory_information);
        System.out.println("库存信息修改成功");
        return success(1);
    }


}
