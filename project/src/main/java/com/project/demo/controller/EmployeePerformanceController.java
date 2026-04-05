package com.project.demo.controller;

import com.project.demo.entity.EmployeePerformance;
import com.project.demo.service.EmployeePerformanceService;
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
 * 员工绩效：(EmployeePerformance)表控制层
 *
 */
@RestController
@RequestMapping("/employee_performance")
public class EmployeePerformanceController extends BaseController<EmployeePerformance, EmployeePerformanceService> {

    /**
     * 员工绩效对象
     */
    @Autowired
    public EmployeePerformanceController(EmployeePerformanceService service) {
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
        EmployeePerformance employee_performance = new EmployeePerformance();
            employee_performance.setPerformance_title(paramMap.get("performance_title")==null?null:String.valueOf(paramMap.get("performance_title")));
                            employee_performance.setEmployee_name(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                            if (paramMap.get("evaluation_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("evaluation_time" )))) {
            String timStr = String.valueOf(paramMap.get("evaluation_time" ));
            employee_performance.setEvaluation_time(parseToTimestamp(timStr));
        } else {
            employee_performance.setEvaluation_time(null);
        }
                            employee_performance.setService_singular(paramMap.get("service_singular")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_singular"))));
                            employee_performance.setQuality_of_service(paramMap.get("quality_of_service")==null?null:String.valueOf(paramMap.get("quality_of_service")));
                            employee_performance.setWorking_attitude(paramMap.get("working_attitude")==null?null:String.valueOf(paramMap.get("working_attitude")));
                            employee_performance.setPerformance_level(paramMap.get("performance_level")==null?null:String.valueOf(paramMap.get("performance_level")));
                            employee_performance.setAssessment_results(paramMap.get("assessment_results")==null?null:String.valueOf(paramMap.get("assessment_results")));
                            employee_performance.setWork_suggestion(paramMap.get("work_suggestion")==null?null:String.valueOf(paramMap.get("work_suggestion")));
                                                                                                                                        employee_performance.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(employee_performance);
        System.out.println("员工绩效新增成功");
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
        EmployeePerformance employee_performance = new EmployeePerformance();
            employee_performance.setPerformance_title(paramMap.get("performance_title")==null?null:String.valueOf(paramMap.get("performance_title")));
                    employee_performance.setEmployee_name(paramMap.get("employee_name")==null?null:String.valueOf(paramMap.get("employee_name")));
                    if (paramMap.get("evaluation_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("evaluation_time" )))) {
            String timStr = String.valueOf(paramMap.get("evaluation_time" ));
            employee_performance.setEvaluation_time(parseToTimestamp(timStr));
        } else {
            employee_performance.setEvaluation_time(null);
        }
                    employee_performance.setService_singular(paramMap.get("service_singular")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_singular"))));
                    employee_performance.setQuality_of_service(paramMap.get("quality_of_service")==null?null:String.valueOf(paramMap.get("quality_of_service")));
                    employee_performance.setWorking_attitude(paramMap.get("working_attitude")==null?null:String.valueOf(paramMap.get("working_attitude")));
                    employee_performance.setPerformance_level(paramMap.get("performance_level")==null?null:String.valueOf(paramMap.get("performance_level")));
                    employee_performance.setAssessment_results(paramMap.get("assessment_results")==null?null:String.valueOf(paramMap.get("assessment_results")));
                    employee_performance.setWork_suggestion(paramMap.get("work_suggestion")==null?null:String.valueOf(paramMap.get("work_suggestion")));
                                    this.setEntity(queryMap,configMap,employee_performance);
        System.out.println("员工绩效修改成功");
        return success(1);
    }


}
