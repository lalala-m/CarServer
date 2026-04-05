package com.project.demo.controller;

import com.project.demo.entity.PeriodClassNameification;
import com.project.demo.service.PeriodClassNameificationService;
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
 * 时段分类：(PeriodClassNameification)表控制层
 *
 */
@RestController
@RequestMapping("/period_class_nameification")
public class PeriodClassNameificationController extends BaseController<PeriodClassNameification, PeriodClassNameificationService> {

    /**
     * 时段分类对象
     */
    @Autowired
    public PeriodClassNameificationController(PeriodClassNameificationService service) {
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
        PeriodClassNameification period_class_nameification = new PeriodClassNameification();
            period_class_nameification.setTime_period(paramMap.get("time_period")==null?null:String.valueOf(paramMap.get("time_period")));
                if (period_class_nameification.getTime_period()== null || period_class_nameification.getTime_period().trim().isEmpty()){
            throw new IllegalArgumentException("时间时段不能为空");
        }
                                                                                                                                    period_class_nameification.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(period_class_nameification);
        System.out.println("时段分类新增成功");
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
        PeriodClassNameification period_class_nameification = new PeriodClassNameification();
            period_class_nameification.setTime_period(paramMap.get("time_period")==null?null:String.valueOf(paramMap.get("time_period")));
                                    this.setEntity(queryMap,configMap,period_class_nameification);
        System.out.println("时段分类修改成功");
        return success(1);
    }


}
