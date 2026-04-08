package com.project.demo.controller;

import com.project.demo.entity.OutboundRecord;
import com.project.demo.service.OutboundRecordService;
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
 * 出库记录：(OutboundRecord)表控制层
 *
 */
@RestController
@RequestMapping("/outbound_record")
public class OutboundRecordController extends BaseController<OutboundRecord, OutboundRecordService> {

    /**
     * 出库记录对象
     */
    @Autowired
    public OutboundRecordController(OutboundRecordService service) {
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
        OutboundRecord outbound_record = new OutboundRecord();
            outbound_record.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                            outbound_record.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                            outbound_record.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                            outbound_record.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            if (paramMap.get("outbound_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("outbound_time" )))) {
            String timStr = String.valueOf(paramMap.get("outbound_time" ));
            outbound_record.setOutbound_time(parseToTimestamp(timStr));
        } else {
            outbound_record.setOutbound_time(null);
        }
                            outbound_record.setOutbound_quantity(paramMap.get("outbound_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("outbound_quantity"))));
                            outbound_record.setOutbound_note(paramMap.get("outbound_note")==null?null:String.valueOf(paramMap.get("outbound_note")));
                                                    outbound_record.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        outbound_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        outbound_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        outbound_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                            outbound_record.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(outbound_record);
        String sql = "SELECT MAX(outbound_record_id) AS max FROM "+"`outbound_record`";
        Integer max = service.selectBaseCount(sql);
        sql = ("SELECT count(*) as count FROM `inventory_information` INNER JOIN `outbound_record` ON inventory_information.consumables_code=outbound_record.consumables_code WHERE inventory_information.consumables_inventory < outbound_record.outbound_quantity AND outbound_record.outbound_record_id="+max).replaceAll("&#60;","<");
        Integer count = service.selectBaseCount(sql);
        if(count>0){
            sql = "delete from "+"outbound_record"+" WHERE "+"outbound_record_id"+" ="+max;
            service.deleteBaseSql(sql);
            return error(30000,"库存数量不足！");
        }
        sql = "UPDATE `inventory_information` INNER JOIN `outbound_record` ON inventory_information.consumables_code=outbound_record.consumables_code SET inventory_information.consumables_inventory= inventory_information.consumables_inventory - outbound_record.outbound_quantity WHERE outbound_record.outbound_record_id="+max;
        service.updateBaseSql(sql);
        System.out.println("出库记录新增成功");
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
        OutboundRecord outbound_record = new OutboundRecord();
            outbound_record.setConsumables_code(paramMap.get("consumables_code")==null?null:String.valueOf(paramMap.get("consumables_code")));
                    outbound_record.setConsumables_name(paramMap.get("consumables_name")==null?null:String.valueOf(paramMap.get("consumables_name")));
                    outbound_record.setConsumables_specifications(paramMap.get("consumables_specifications")==null?null:String.valueOf(paramMap.get("consumables_specifications")));
                    outbound_record.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    if (paramMap.get("outbound_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("outbound_time" )))) {
            String timStr = String.valueOf(paramMap.get("outbound_time" ));
            outbound_record.setOutbound_time(parseToTimestamp(timStr));
        } else {
            outbound_record.setOutbound_time(null);
        }
                    outbound_record.setOutbound_quantity(paramMap.get("outbound_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("outbound_quantity"))));
                    outbound_record.setOutbound_note(paramMap.get("outbound_note")==null?null:String.valueOf(paramMap.get("outbound_note")));
                                            outbound_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        outbound_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        outbound_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
            this.setEntity(queryMap,configMap,outbound_record);
        System.out.println("出库记录修改成功");
        return success(1);
    }


}
