package com.project.demo.controller;

import com.project.demo.entity.BoutiqueCenter;
import com.project.demo.service.BoutiqueCenterService;
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
 * 精品中心：(BoutiqueCenter)表控制层
 *
 */
@RestController
@RequestMapping("/boutique_center")
public class BoutiqueCenterController extends BaseController<BoutiqueCenter, BoutiqueCenterService> {

    /**
     * 精品中心对象
     */
    @Autowired
    public BoutiqueCenterController(BoutiqueCenterService service) {
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
        BoutiqueCenter boutique_center = new BoutiqueCenter();
            boutique_center.setService_code(paramMap.get("service_code")==null?null:String.valueOf(paramMap.get("service_code")));
                            boutique_center.setService_name(paramMap.get("service_name")==null?null:String.valueOf(paramMap.get("service_name")));
                            boutique_center.setService_type(paramMap.get("service_type")==null?null:String.valueOf(paramMap.get("service_type")));
                            boutique_center.setService_price(paramMap.get("service_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_price"))));
                            boutique_center.setService_cover(paramMap.get("service_cover")==null?null:String.valueOf(paramMap.get("service_cover")));
                            boutique_center.setService_introduction(paramMap.get("service_introduction")==null?null:String.valueOf(paramMap.get("service_introduction")));
                            boutique_center.setService_specificss(paramMap.get("service_specificss")==null?null:String.valueOf(paramMap.get("service_specificss")));
                        boutique_center.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        boutique_center.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        boutique_center.setCollect_len(paramMap.get("collect_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("collect_len"))));
        boutique_center.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            boutique_center.setReservation_record_limit_times(paramMap.get("reservation_record_limit_times")==null?null:String.valueOf(paramMap.get("reservation_record_limit_times")));
                                                                                                                            boutique_center.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(boutique_center);
        System.out.println("精品中心新增成功");
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
        BoutiqueCenter boutique_center = new BoutiqueCenter();
            boutique_center.setService_code(paramMap.get("service_code")==null?null:String.valueOf(paramMap.get("service_code")));
                    boutique_center.setService_name(paramMap.get("service_name")==null?null:String.valueOf(paramMap.get("service_name")));
                    boutique_center.setService_type(paramMap.get("service_type")==null?null:String.valueOf(paramMap.get("service_type")));
                    boutique_center.setService_price(paramMap.get("service_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_price"))));
                    boutique_center.setService_cover(paramMap.get("service_cover")==null?null:String.valueOf(paramMap.get("service_cover")));
                    boutique_center.setService_introduction(paramMap.get("service_introduction")==null?null:String.valueOf(paramMap.get("service_introduction")));
                    boutique_center.setService_specificss(paramMap.get("service_specificss")==null?null:String.valueOf(paramMap.get("service_specificss")));
                boutique_center.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        boutique_center.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        boutique_center.setCollect_len(paramMap.get("collect_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("collect_len"))));
        boutique_center.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            boutique_center.setReservation_record_limit_times(paramMap.get("reservation_record_limit_times")==null?null:String.valueOf(paramMap.get("reservation_record_limit_times")));
                                this.setEntity(queryMap,configMap,boutique_center);
        System.out.println("精品中心修改成功");
        return success(1);
    }


}
