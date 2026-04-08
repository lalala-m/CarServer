package com.project.demo.controller;

import com.project.demo.entity.MaintenanceOrder;
import com.project.demo.service.MaintenanceOrderService;
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
 * 保养订单：(MaintenanceOrder)表控制层
 *
 */
@RestController
@RequestMapping("/maintenance_order")
public class MaintenanceOrderController extends BaseController<MaintenanceOrder, MaintenanceOrderService> {

    /**
     * 保养订单对象
     */
    @Autowired
    public MaintenanceOrderController(MaintenanceOrderService service) {
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
        MaintenanceOrder maintenance_order = new MaintenanceOrder();
            maintenance_order.setProject_bundle(paramMap.get("project_bundle")==null?null:String.valueOf(paramMap.get("project_bundle")));
                            maintenance_order.setProject_price(paramMap.get("project_price")==null?null:String.valueOf(paramMap.get("project_price")));
                            maintenance_order.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                            maintenance_order.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            maintenance_order.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            if (paramMap.get("purchase_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("purchase_time" )))) {
            String timStr = String.valueOf(paramMap.get("purchase_time" ));
            maintenance_order.setPurchase_time(parseToTimestamp(timStr));
        } else {
            maintenance_order.setPurchase_time(null);
        }
                            maintenance_order.setNumber_of_purchases(paramMap.get("number_of_purchases")==null?null:String.valueOf(paramMap.get("number_of_purchases")));
                            maintenance_order.setPurchase_notes(paramMap.get("purchase_notes")==null?null:String.valueOf(paramMap.get("purchase_notes")));
                            maintenance_order.setOrder_status(paramMap.get("order_status")==null?null:String.valueOf(paramMap.get("order_status")));
                        maintenance_order.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        maintenance_order.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
        maintenance_order.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        maintenance_order.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
            maintenance_order.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        maintenance_order.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        maintenance_order.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        maintenance_order.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                                    maintenance_order.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(maintenance_order);
        System.out.println("保养订单新增成功");
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
        MaintenanceOrder maintenance_order = new MaintenanceOrder();
            maintenance_order.setProject_bundle(paramMap.get("project_bundle")==null?null:String.valueOf(paramMap.get("project_bundle")));
                    maintenance_order.setProject_price(paramMap.get("project_price")==null?null:String.valueOf(paramMap.get("project_price")));
                    maintenance_order.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                    maintenance_order.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    maintenance_order.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    if (paramMap.get("purchase_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("purchase_time" )))) {
            String timStr = String.valueOf(paramMap.get("purchase_time" ));
            maintenance_order.setPurchase_time(parseToTimestamp(timStr));
        } else {
            maintenance_order.setPurchase_time(null);
        }
                    maintenance_order.setNumber_of_purchases(paramMap.get("number_of_purchases")==null?null:String.valueOf(paramMap.get("number_of_purchases")));
                    maintenance_order.setPurchase_notes(paramMap.get("purchase_notes")==null?null:String.valueOf(paramMap.get("purchase_notes")));
                    maintenance_order.setOrder_status(paramMap.get("order_status")==null?null:String.valueOf(paramMap.get("order_status")));
                maintenance_order.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        maintenance_order.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
        maintenance_order.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        maintenance_order.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
            maintenance_order.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        maintenance_order.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        maintenance_order.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                    this.setEntity(queryMap,configMap,maintenance_order);
        System.out.println("保养订单修改成功");
        return success(1);
    }


    /**
     * 修改审核状态
     *
     * @param id          要修改的记录ID
     * @param newState    新的审核状态
     * @return 提示信息：审核成功/失败
     */
    @Transactional
    @GetMapping("/update_examine_state")
    public String updateExamineState(Long id, String newState) throws IOException {
        // 检查传入的状态是否合法
        if (!newState.equals("未审核") && !newState.equals("已通过") && !newState.equals("未通过")) {
            return "非法的审核状态";
        }

        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("id",String.valueOf(id));
        // 根据ID查找记录
        MaintenanceOrder maintenance_order = service.findOne(queryMap);

        if (maintenance_order!=null) {
            // 更新审核状态
            maintenance_order.setExamine_state(newState);
            this.setEntity(queryMap,new HashMap<>(),maintenance_order);

            return "审核成功";
        } else {
            return "审核失败：记录不存在";
        }
    }
    /**
     * 修改支付状态
     *
     * @param id          要修改的记录ID
     * @param newState    新的支付状态
     * @return 提示信息：审核成功/失败
     */
    @Transactional
    @GetMapping("/update_pay_state")
    public String updatePayState(Long id, String newState) throws IOException {
        // 检查传入的状态是否合法
        if (!newState.equals("未支付") && !newState.equals("已支付")) {
            return "非法的支付状态";
        }

        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("id",String.valueOf(id));
        // 根据ID查找记录
        MaintenanceOrder maintenance_order = service.findOne(queryMap);

        if (maintenance_order!=null) {
            // 更新支付状态
            maintenance_order.setPay_state(newState);
            this.setEntity(queryMap,new HashMap<>(),maintenance_order);

            return "支付成功";
        } else {
            return "支付成功：记录不存在";
        }
    }
}
