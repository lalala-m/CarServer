package com.project.demo.controller;

import com.project.demo.entity.OnlineMall;
import com.project.demo.service.OnlineMallService;
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
 * 网上商城：(OnlineMall)表控制层
 *
 */
@RestController
@RequestMapping("/online_mall")
public class OnlineMallController extends BaseController<OnlineMall, OnlineMallService> {

    /**
     * 网上商城对象
     */
    @Autowired
    public OnlineMallController(OnlineMallService service) {
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
        OnlineMall online_mall = new OnlineMall();
            online_mall.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            online_mall.setCommodity_specifications(paramMap.get("commodity_specifications")==null?null:String.valueOf(paramMap.get("commodity_specifications")));
                        online_mall.setCart_title(paramMap.get("cart_title")==null?null:String.valueOf(paramMap.get("cart_title")));
        online_mall.setCart_img(paramMap.get("cart_img")==null?null:String.valueOf(paramMap.get("cart_img")));
        online_mall.setCart_description(paramMap.get("cart_description")==null?null:String.valueOf(paramMap.get("cart_description")));
        online_mall.setCart_price_ago(paramMap.get("cart_price_ago")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price_ago"))));
        online_mall.setCart_price(paramMap.get("cart_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price"))));
        online_mall.setCart_inventory(paramMap.get("cart_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_inventory"))));
        online_mall.setCart_type(paramMap.get("cart_type")==null?null:String.valueOf(paramMap.get("cart_type")));
        online_mall.setCart_content(paramMap.get("cart_content")==null?null:String.valueOf(paramMap.get("cart_content")));
        online_mall.setCart_img_1(paramMap.get("cart_img_1")==null?null:String.valueOf(paramMap.get("cart_img_1")));
        online_mall.setCart_img_2(paramMap.get("cart_img_2")==null?null:String.valueOf(paramMap.get("cart_img_2")));
        online_mall.setCart_img_3(paramMap.get("cart_img_3")==null?null:String.valueOf(paramMap.get("cart_img_3")));
        online_mall.setCart_img_4(paramMap.get("cart_img_4")==null?null:String.valueOf(paramMap.get("cart_img_4")));
        online_mall.setCart_img_5(paramMap.get("cart_img_5")==null?null:String.valueOf(paramMap.get("cart_img_5")));
            online_mall.setCart_integral(paramMap.get("cart_integral")==null?null:Integer.valueOf(String.valueOf(paramMap.get("cart_integral"))));
                online_mall.setListStatus(paramMap.get("list_status")==null?null:Integer.valueOf(String.valueOf(paramMap.get("list_status"))));
                                                                                                                        online_mall.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(online_mall);
        System.out.println("网上商城新增成功");
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
        OnlineMall online_mall = new OnlineMall();
            online_mall.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    online_mall.setCommodity_specifications(paramMap.get("commodity_specifications")==null?null:String.valueOf(paramMap.get("commodity_specifications")));
                online_mall.setCart_title(paramMap.get("cart_title")==null?null:String.valueOf(paramMap.get("cart_title")));
        online_mall.setCart_img(paramMap.get("cart_img")==null?null:String.valueOf(paramMap.get("cart_img")));
        online_mall.setCart_description(paramMap.get("cart_description")==null?null:String.valueOf(paramMap.get("cart_description")));
        online_mall.setCart_price_ago(paramMap.get("cart_price_ago")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price_ago"))));
        online_mall.setCart_price(paramMap.get("cart_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price"))));
        online_mall.setCart_inventory(paramMap.get("cart_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_inventory"))));
        online_mall.setCart_type(paramMap.get("cart_type")==null?null:String.valueOf(paramMap.get("cart_type")));
        online_mall.setCart_content(paramMap.get("cart_content")==null?null:String.valueOf(paramMap.get("cart_content")));
        online_mall.setCart_img_1(paramMap.get("cart_img_1")==null?null:String.valueOf(paramMap.get("cart_img_1")));
        online_mall.setCart_img_2(paramMap.get("cart_img_2")==null?null:String.valueOf(paramMap.get("cart_img_2")));
        online_mall.setCart_img_3(paramMap.get("cart_img_3")==null?null:String.valueOf(paramMap.get("cart_img_3")));
        online_mall.setCart_img_4(paramMap.get("cart_img_4")==null?null:String.valueOf(paramMap.get("cart_img_4")));
    online_mall.setCart_img_5(paramMap.get("cart_img_5")==null?null:String.valueOf(paramMap.get("cart_img_5")));
            online_mall.setCart_integral(paramMap.get("cart_integral")==null?null:Integer.valueOf(String.valueOf(paramMap.get("cart_integral"))));
                online_mall.setListStatus(paramMap.get("list_status")==null?null:Integer.valueOf(String.valueOf(paramMap.get("list_status"))));
                            this.setEntity(queryMap,configMap,online_mall);
        System.out.println("网上商城修改成功");
        return success(1);
    }


}
