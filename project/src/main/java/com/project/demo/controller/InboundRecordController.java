package com.project.demo.controller;

import com.project.demo.entity.InboundRecord;
import com.project.demo.service.InboundRecordService;
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
 * 入库记录：(InboundRecord)表控制层
 *
 */
@RestController
@RequestMapping("/inbound_record")
public class InboundRecordController extends BaseController<InboundRecord, InboundRecordService> {

    /**
     * 入库记录对象
     */
    @Autowired
    public InboundRecordController(InboundRecordService service) {
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
        InboundRecord inbound_record = new InboundRecord();
            inbound_record.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                            inbound_record.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                            inbound_record.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                            inbound_record.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            if (paramMap.get("inbound_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("inbound_time" )))) {
            String timStr = String.valueOf(paramMap.get("inbound_time" ));
            inbound_record.setInbound_time(parseToTimestamp(timStr));
        } else {
            inbound_record.setInbound_time(null);
        }
                            inbound_record.setInbound_quantity(paramMap.get("inbound_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("inbound_quantity"))));
                            inbound_record.setInbound_notes(paramMap.get("inbound_notes")==null?null:String.valueOf(paramMap.get("inbound_notes")));
                                inbound_record.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        inbound_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        inbound_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        inbound_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                                inbound_record.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(inbound_record);
        String sql = "SELECT MAX(inbound_record_id) AS max FROM "+"`inbound_record`";
        Integer max = service.selectBaseCount(sql);
        sql = "UPDATE `inventory_information` INNER JOIN `inbound_record` ON inventory_information.consumables_code=inbound_record.consumables_code SET inventory_information.consumables_inventory= inventory_information.consumables_inventory + inbound_record.inbound_quantity WHERE inbound_record.inbound_record_id="+max;
        service.updateBaseSql(sql);
        System.out.println("入库记录新增成功");
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
        InboundRecord inbound_record = new InboundRecord();
            inbound_record.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                    inbound_record.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                    inbound_record.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                    inbound_record.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    if (paramMap.get("inbound_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("inbound_time" )))) {
            String timStr = String.valueOf(paramMap.get("inbound_time" ));
            inbound_record.setInbound_time(parseToTimestamp(timStr));
        } else {
            inbound_record.setInbound_time(null);
        }
                    inbound_record.setInbound_quantity(paramMap.get("inbound_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("inbound_quantity"))));
                    inbound_record.setInbound_notes(paramMap.get("inbound_notes")==null?null:String.valueOf(paramMap.get("inbound_notes")));
                        inbound_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        inbound_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        inbound_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                this.setEntity(queryMap,configMap,inbound_record);
        System.out.println("入库记录修改成功");
        return success(1);
    }


}
