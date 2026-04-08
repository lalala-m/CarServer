package com.project.demo.controller;

import com.project.demo.entity.WriteOffInformation;
import com.project.demo.service.WriteOffInformationService;
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
 * 核销信息：(WriteOffInformation)表控制层
 *
 */
@RestController
@RequestMapping("/write_off_information")
public class WriteOffInformationController extends BaseController<WriteOffInformation, WriteOffInformationService> {

    /**
     * 核销信息对象
     */
    @Autowired
    public WriteOffInformationController(WriteOffInformationService service) {
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
        WriteOffInformation write_off_information = new WriteOffInformation();
            write_off_information.setCard_code(paramMap.get("card_code")==null?null:String.valueOf(paramMap.get("card_code")));
                            write_off_information.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                            write_off_information.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            write_off_information.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            write_off_information.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                            write_off_information.setEffective_period(paramMap.get("effective_period")==null?null:String.valueOf(paramMap.get("effective_period")));
                            write_off_information.setNumber_of_uses(paramMap.get("number_of_uses")==null?null:String.valueOf(paramMap.get("number_of_uses")));
                            if (paramMap.get("issuing_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("issuing_time" )))) {
            String timStr = String.valueOf(paramMap.get("issuing_time" ));
            write_off_information.setIssuing_time(parseToTimestamp(timStr));
        } else {
            write_off_information.setIssuing_time(null);
        }
                            if (paramMap.get("write_off_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("write_off_time" )))) {
            String timStr = String.valueOf(paramMap.get("write_off_time" ));
            write_off_information.setWrite_off_time(parseToTimestamp(timStr));
        } else {
            write_off_information.setWrite_off_time(null);
        }
                            write_off_information.setWrite_off_doc(paramMap.get("write_off_doc")==null?null:String.valueOf(paramMap.get("write_off_doc")));
                            write_off_information.setWrite_off_remarks(paramMap.get("write_off_remarks")==null?null:String.valueOf(paramMap.get("write_off_remarks")));
                                        write_off_information.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        write_off_information.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        write_off_information.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        write_off_information.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                        write_off_information.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(write_off_information);
        System.out.println("核销信息新增成功");
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
        WriteOffInformation write_off_information = new WriteOffInformation();
            write_off_information.setCard_code(paramMap.get("card_code")==null?null:String.valueOf(paramMap.get("card_code")));
                    write_off_information.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                    write_off_information.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    write_off_information.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    write_off_information.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                    write_off_information.setEffective_period(paramMap.get("effective_period")==null?null:String.valueOf(paramMap.get("effective_period")));
                    write_off_information.setNumber_of_uses(paramMap.get("number_of_uses")==null?null:String.valueOf(paramMap.get("number_of_uses")));
                    if (paramMap.get("issuing_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("issuing_time" )))) {
            String timStr = String.valueOf(paramMap.get("issuing_time" ));
            write_off_information.setIssuing_time(parseToTimestamp(timStr));
        } else {
            write_off_information.setIssuing_time(null);
        }
                    if (paramMap.get("write_off_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("write_off_time" )))) {
            String timStr = String.valueOf(paramMap.get("write_off_time" ));
            write_off_information.setWrite_off_time(parseToTimestamp(timStr));
        } else {
            write_off_information.setWrite_off_time(null);
        }
                    write_off_information.setWrite_off_doc(paramMap.get("write_off_doc")==null?null:String.valueOf(paramMap.get("write_off_doc")));
                    write_off_information.setWrite_off_remarks(paramMap.get("write_off_remarks")==null?null:String.valueOf(paramMap.get("write_off_remarks")));
                                write_off_information.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        write_off_information.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        write_off_information.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                        this.setEntity(queryMap,configMap,write_off_information);
        System.out.println("核销信息修改成功");
        return success(1);
    }


}
