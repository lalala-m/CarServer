package com.project.demo.controller;

import com.project.demo.entity.CarInformation;
import com.project.demo.service.CarInformationService;
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
 * 汽车信息：(CarInformation)表控制层
 *
 */
@RestController
@RequestMapping("/car_information")
public class CarInformationController extends BaseController<CarInformation, CarInformationService> {

    /**
     * 汽车信息对象
     */
    @Autowired
    public CarInformationController(CarInformationService service) {
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
        CarInformation car_information = new CarInformation();
            car_information.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                    Map<String, String> mapcar_coding = new HashMap<>();
        mapcar_coding.put("car_coding",String.valueOf(paramMap.get("car_coding")));
        List listcar_coding = service.selectBaseList(service.select(mapcar_coding, new HashMap<>()));
        if (listcar_coding.size()>0){
            return error(30000, "字段汽车编码内容不能重复");
        }
                    car_information.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                            car_information.setCar_condition_picture(paramMap.get("car_condition_picture")==null?null:String.valueOf(paramMap.get("car_condition_picture")));
                            car_information.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                            car_information.setCar_brand(paramMap.get("car_brand")==null?null:String.valueOf(paramMap.get("car_brand")));
                            car_information.setNew_and_old_car(paramMap.get("new_and_old_car")==null?null:String.valueOf(paramMap.get("new_and_old_car")));
                            car_information.setCar_price(paramMap.get("car_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("car_price"))));
                            car_information.setCar_status(paramMap.get("car_status")==null?null:String.valueOf(paramMap.get("car_status")));
                            car_information.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            car_information.setStore_location(paramMap.get("store_location")==null?null:String.valueOf(paramMap.get("store_location")));
                            car_information.setCar_configuration(paramMap.get("car_configuration")==null?null:String.valueOf(paramMap.get("car_configuration")));
                            car_information.setCar_specificss(paramMap.get("car_specificss")==null?null:String.valueOf(paramMap.get("car_specificss")));
                        car_information.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        car_information.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        car_information.setCollect_len(paramMap.get("collect_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("collect_len"))));
        car_information.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            car_information.setTest_drive_record_limit_times(paramMap.get("test_drive_record_limit_times")==null?null:String.valueOf(paramMap.get("test_drive_record_limit_times")));
            car_information.setPurchase_order_limit_times(paramMap.get("purchase_order_limit_times")==null?null:String.valueOf(paramMap.get("purchase_order_limit_times")));
                                                                                                                            car_information.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(car_information);
        System.out.println("汽车信息新增成功");
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
        CarInformation car_information = new CarInformation();
            car_information.setCar_coding(paramMap.get("car_coding")==null?null:String.valueOf(paramMap.get("car_coding")));
                    car_information.setCar_name(paramMap.get("car_name")==null?null:String.valueOf(paramMap.get("car_name")));
                    car_information.setCar_condition_picture(paramMap.get("car_condition_picture")==null?null:String.valueOf(paramMap.get("car_condition_picture")));
                    car_information.setCar_models(paramMap.get("car_models")==null?null:String.valueOf(paramMap.get("car_models")));
                    car_information.setCar_brand(paramMap.get("car_brand")==null?null:String.valueOf(paramMap.get("car_brand")));
                    car_information.setNew_and_old_car(paramMap.get("new_and_old_car")==null?null:String.valueOf(paramMap.get("new_and_old_car")));
                    car_information.setCar_price(paramMap.get("car_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("car_price"))));
                    car_information.setCar_status(paramMap.get("car_status")==null?null:String.valueOf(paramMap.get("car_status")));
                    car_information.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    car_information.setStore_location(paramMap.get("store_location")==null?null:String.valueOf(paramMap.get("store_location")));
                    car_information.setCar_configuration(paramMap.get("car_configuration")==null?null:String.valueOf(paramMap.get("car_configuration")));
                    car_information.setCar_specificss(paramMap.get("car_specificss")==null?null:String.valueOf(paramMap.get("car_specificss")));
                car_information.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        car_information.setPraise_len(paramMap.get("praise_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("praise_len"))));
        car_information.setCollect_len(paramMap.get("collect_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("collect_len"))));
        car_information.setComment_len(paramMap.get("comment_len")==null?null:Integer.valueOf(String.valueOf(paramMap.get("comment_len"))));
            car_information.setTest_drive_record_limit_times(paramMap.get("test_drive_record_limit_times")==null?null:String.valueOf(paramMap.get("test_drive_record_limit_times")));
            car_information.setPurchase_order_limit_times(paramMap.get("purchase_order_limit_times")==null?null:String.valueOf(paramMap.get("purchase_order_limit_times")));
                                this.setEntity(queryMap,configMap,car_information);
        System.out.println("汽车信息修改成功");
        return success(1);
    }


}
