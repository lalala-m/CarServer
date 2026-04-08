package com.project.demo.controller;

import com.project.demo.entity.Supplier;
import com.project.demo.service.SupplierService;
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
 * 供应厂商：(Supplier)表控制层
 *
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController<Supplier, SupplierService> {

    /**
     * 供应厂商对象
     */
    @Autowired
    public SupplierController(SupplierService service) {
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
        Supplier supplier = new Supplier();
            supplier.setName_of_manufacturer(paramMap.get("name_of_manufacturer")==null?null:String.valueOf(paramMap.get("name_of_manufacturer")));
                if (supplier.getName_of_manufacturer()== null || supplier.getName_of_manufacturer().trim().isEmpty()){
            throw new IllegalArgumentException("厂家名称不能为空");
        }
                        supplier.setContact_phone(paramMap.get("contact_phone")==null?null:String.valueOf(paramMap.get("contact_phone")));
                if (supplier.getContact_phone()== null || supplier.getContact_phone().trim().isEmpty()){
            throw new IllegalArgumentException("联系电话不能为空");
        }
                        supplier.setManufacturer_address(paramMap.get("manufacturer_address")==null?null:String.valueOf(paramMap.get("manufacturer_address")));
                            supplier.setMain_products(paramMap.get("main_products")==null?null:String.valueOf(paramMap.get("main_products")));
                            supplier.setManufacturer_profile(paramMap.get("manufacturer_profile")==null?null:String.valueOf(paramMap.get("manufacturer_profile")));
                                                                                                                                                                    supplier.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(supplier);
        System.out.println("供应厂商新增成功");
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
        Supplier supplier = new Supplier();
            supplier.setName_of_manufacturer(paramMap.get("name_of_manufacturer")==null?null:String.valueOf(paramMap.get("name_of_manufacturer")));
                    supplier.setContact_phone(paramMap.get("contact_phone")==null?null:String.valueOf(paramMap.get("contact_phone")));
                    supplier.setManufacturer_address(paramMap.get("manufacturer_address")==null?null:String.valueOf(paramMap.get("manufacturer_address")));
                    supplier.setMain_products(paramMap.get("main_products")==null?null:String.valueOf(paramMap.get("main_products")));
                    supplier.setManufacturer_profile(paramMap.get("manufacturer_profile")==null?null:String.valueOf(paramMap.get("manufacturer_profile")));
                                            this.setEntity(queryMap,configMap,supplier);
        System.out.println("供应厂商修改成功");
        return success(1);
    }


}
