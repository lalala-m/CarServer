package com.project.demo.controller;

import com.project.demo.entity.BrandClassNameification;
import com.project.demo.service.BrandClassNameificationService;
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
 * 品牌分类：(BrandClassNameification)表控制层
 *
 */
@RestController
@RequestMapping("/brand_class_nameification")
public class BrandClassNameificationController extends BaseController<BrandClassNameification, BrandClassNameificationService> {

    /**
     * 品牌分类对象
     */
    @Autowired
    public BrandClassNameificationController(BrandClassNameificationService service) {
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
        BrandClassNameification brand_class_nameification = new BrandClassNameification();
            brand_class_nameification.setBrand_name(paramMap.get("brand_name")==null?null:String.valueOf(paramMap.get("brand_name")));
                if (brand_class_nameification.getBrand_name()== null || brand_class_nameification.getBrand_name().trim().isEmpty()){
            throw new IllegalArgumentException("品牌名称不能为空");
        }
                                                                                                                                                                brand_class_nameification.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(brand_class_nameification);
        System.out.println("品牌分类新增成功");
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
        BrandClassNameification brand_class_nameification = new BrandClassNameification();
            brand_class_nameification.setBrand_name(paramMap.get("brand_name")==null?null:String.valueOf(paramMap.get("brand_name")));
                                            this.setEntity(queryMap,configMap,brand_class_nameification);
        System.out.println("品牌分类修改成功");
        return success(1);
    }


}
