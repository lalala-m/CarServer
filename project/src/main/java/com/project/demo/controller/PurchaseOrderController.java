package com.project.demo.controller;

import com.project.demo.entity.PurchaseOrder;
import com.project.demo.service.PurchaseOrderService;
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
 * 购买订单：(PurchaseOrder)表控制层
 *
 */
@RestController
@RequestMapping("/purchase_order")
public class PurchaseOrderController extends BaseController<PurchaseOrder, PurchaseOrderService> {

    /**
     * 购买订单对象
     */
    @Autowired
    public PurchaseOrderController(PurchaseOrderService service) {
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
        PurchaseOrder purchase_order = new PurchaseOrder();
            purchase_order.setPurchase_order_number(paramMap.get("purchase_order_number")==null?null:String.valueOf(paramMap.get("purchase_order_number")));
                            purchase_order.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                            purchase_order.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                            purchase_order.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                            purchase_order.setCar_brand(paramMap.get("car_brand")==null?null:String.valueOf(paramMap.get("car_brand")));
                            purchase_order.setNew_and_old_car(paramMap.get("new_and_old_car")==null?null:String.valueOf(paramMap.get("new_and_old_car")));
                            purchase_order.setCar_price(paramMap.get("car_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("car_price"))));
                            purchase_order.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            purchase_order.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            if (paramMap.get("purchase_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("purchase_time" )))) {
            String timStr = String.valueOf(paramMap.get("purchase_time" ));
            purchase_order.setPurchase_time(parseToTimestamp(timStr));
        } else {
            purchase_order.setPurchase_time(null);
        }
                            purchase_order.setNumber_of_purchases(paramMap.get("number_of_purchases")==null?null:String.valueOf(paramMap.get("number_of_purchases")));
                            purchase_order.setPurchase_quantity(paramMap.get("purchase_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("purchase_quantity"))));
                            purchase_order.setTotal_amount(paramMap.get("total_amount")==null?null:String.valueOf(paramMap.get("total_amount")));
                            purchase_order.setPurchase_notes(paramMap.get("purchase_notes")==null?null:String.valueOf(paramMap.get("purchase_notes")));
                        purchase_order.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        purchase_order.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
        purchase_order.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        purchase_order.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
                    purchase_order.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        purchase_order.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        purchase_order.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        purchase_order.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                            purchase_order.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(purchase_order);
        System.out.println("购买订单新增成功");
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
        PurchaseOrder purchase_order = new PurchaseOrder();
            purchase_order.setPurchase_order_number(paramMap.get("purchase_order_number")==null?null:String.valueOf(paramMap.get("purchase_order_number")));
                    purchase_order.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                    purchase_order.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                    purchase_order.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                    purchase_order.setCar_brand(paramMap.get("car_brand")==null?null:String.valueOf(paramMap.get("car_brand")));
                    purchase_order.setNew_and_old_car(paramMap.get("new_and_old_car")==null?null:String.valueOf(paramMap.get("new_and_old_car")));
                    purchase_order.setCar_price(paramMap.get("car_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("car_price"))));
                    purchase_order.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    purchase_order.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    if (paramMap.get("purchase_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("purchase_time" )))) {
            String timStr = String.valueOf(paramMap.get("purchase_time" ));
            purchase_order.setPurchase_time(parseToTimestamp(timStr));
        } else {
            purchase_order.setPurchase_time(null);
        }
                    purchase_order.setNumber_of_purchases(paramMap.get("number_of_purchases")==null?null:String.valueOf(paramMap.get("number_of_purchases")));
                    purchase_order.setPurchase_quantity(paramMap.get("purchase_quantity")==null?null:Double.valueOf(String.valueOf(paramMap.get("purchase_quantity"))));
                    purchase_order.setTotal_amount(paramMap.get("total_amount")==null?null:String.valueOf(paramMap.get("total_amount")));
                    purchase_order.setPurchase_notes(paramMap.get("purchase_notes")==null?null:String.valueOf(paramMap.get("purchase_notes")));
                purchase_order.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        purchase_order.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
        purchase_order.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        purchase_order.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
                    purchase_order.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        purchase_order.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        purchase_order.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                            this.setEntity(queryMap,configMap,purchase_order);
        System.out.println("购买订单修改成功");
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
        PurchaseOrder purchase_order = service.findOne(queryMap);

        if (purchase_order!=null) {
            // 更新审核状态
            purchase_order.setExamine_state(newState);
            this.setEntity(queryMap,new HashMap<>(),purchase_order);

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
        PurchaseOrder purchase_order = service.findOne(queryMap);

        if (purchase_order!=null) {
            // 更新支付状态
            purchase_order.setPay_state(newState);
            this.setEntity(queryMap,new HashMap<>(),purchase_order);

            return "支付成功";
        } else {
            return "支付成功：记录不存在";
        }
    }
}
