package com.project.demo.controller;

import com.project.demo.entity.TestDriveRecord;
import com.project.demo.service.TestDriveRecordService;
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
 * 试驾记录：(TestDriveRecord)表控制层
 *
 */
@RestController
@RequestMapping("/test_drive_record")
public class TestDriveRecordController extends BaseController<TestDriveRecord, TestDriveRecordService> {

    /**
     * 试驾记录对象
     */
    @Autowired
    public TestDriveRecordController(TestDriveRecordService service) {
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
        TestDriveRecord test_drive_record = new TestDriveRecord();
            test_drive_record.setTest_drive_code(paramMap.get("test_drive_code")==null?null:String.valueOf(paramMap.get("test_drive_code")));
                            test_drive_record.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                            test_drive_record.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                            test_drive_record.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                            test_drive_record.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            test_drive_record.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            if (paramMap.get("test_drive_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("test_drive_time" )))) {
            String timStr = String.valueOf(paramMap.get("test_drive_time" ));
            test_drive_record.setTest_drive_time(parseToTimestamp(timStr));
        } else {
            test_drive_record.setTest_drive_time(null);
        }
                            test_drive_record.setTest_drive_remarks(paramMap.get("test_drive_remarks")==null?null:String.valueOf(paramMap.get("test_drive_remarks")));
                                            test_drive_record.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        test_drive_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        test_drive_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        test_drive_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                    test_drive_record.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(test_drive_record);
        System.out.println("试驾记录新增成功");
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
        TestDriveRecord test_drive_record = new TestDriveRecord();
            test_drive_record.setTest_drive_code(paramMap.get("test_drive_code")==null?null:String.valueOf(paramMap.get("test_drive_code")));
                    test_drive_record.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                    test_drive_record.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                    test_drive_record.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                    test_drive_record.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    test_drive_record.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    if (paramMap.get("test_drive_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("test_drive_time" )))) {
            String timStr = String.valueOf(paramMap.get("test_drive_time" ));
            test_drive_record.setTest_drive_time(parseToTimestamp(timStr));
        } else {
            test_drive_record.setTest_drive_time(null);
        }
                    test_drive_record.setTest_drive_remarks(paramMap.get("test_drive_remarks")==null?null:String.valueOf(paramMap.get("test_drive_remarks")));
                                    test_drive_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        test_drive_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        test_drive_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                    this.setEntity(queryMap,configMap,test_drive_record);
        System.out.println("试驾记录修改成功");
        return success(1);
    }


}
