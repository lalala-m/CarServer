package com.project.demo.controller;

import com.project.demo.entity.ReservationRecord;
import com.project.demo.service.ReservationRecordService;
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
 * 预约记录：(ReservationRecord)表控制层
 *
 */
@RestController
@RequestMapping("/reservation_record")
public class ReservationRecordController extends BaseController<ReservationRecord, ReservationRecordService> {

    /**
     * 预约记录对象
     */
    @Autowired
    public ReservationRecordController(ReservationRecordService service) {
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
        ReservationRecord reservation_record = new ReservationRecord();
            reservation_record.setBooking_code(paramMap.get("booking_code")==null?null:String.valueOf(paramMap.get("booking_code")));
                            reservation_record.setService_code(paramMap.get("service_code")==null?null:String.valueOf(paramMap.get("service_code")));
                            reservation_record.setService_name(paramMap.get("service_name")==null?null:String.valueOf(paramMap.get("service_name")));
                            reservation_record.setService_price(paramMap.get("service_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_price"))));
                            reservation_record.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            reservation_record.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                            reservation_record.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                            if (paramMap.get("appointment_date" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("appointment_date" )))) {
            String timStr = String.valueOf(paramMap.get("appointment_date" ));
            reservation_record.setAppointment_date(parseToTimestamp(timStr));
        } else {
            reservation_record.setAppointment_date(null);
        }
                            reservation_record.setAppointment_period(paramMap.get("appointment_period")==null?null:String.valueOf(paramMap.get("appointment_period")));
                            reservation_record.setNumber_of_appointments(paramMap.get("number_of_appointments")==null?null:String.valueOf(paramMap.get("number_of_appointments")));
                            if (paramMap.get("delivery_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("delivery_time" )))) {
            String timStr = String.valueOf(paramMap.get("delivery_time" ));
            reservation_record.setDelivery_time(parseToTimestamp(timStr));
        } else {
            reservation_record.setDelivery_time(null);
        }
                            reservation_record.setSales_consultant(paramMap.get("sales_consultant")==null?null:String.valueOf(paramMap.get("sales_consultant")));
                            reservation_record.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                            reservation_record.setFrame_number(paramMap.get("frame_number")==null?null:String.valueOf(paramMap.get("frame_number")));
                            reservation_record.setCar_type(paramMap.get("car_type")==null?null:String.valueOf(paramMap.get("car_type")));
                            reservation_record.setAppointment_remarks(paramMap.get("appointment_remarks")==null?null:String.valueOf(paramMap.get("appointment_remarks")));
                            reservation_record.setSales_manager(paramMap.get("sales_manager")==null?null:Integer.valueOf(String.valueOf(paramMap.get("sales_manager"))));
                            reservation_record.setAccount_manager(paramMap.get("account_manager")==null?null:Integer.valueOf(String.valueOf(paramMap.get("account_manager"))));
                        reservation_record.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        reservation_record.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
                                reservation_record.setExtra(paramMap.get("extra")==null?null:String.valueOf(paramMap.get("extra")));
        reservation_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        reservation_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        reservation_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                                                                                                                                reservation_record.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(reservation_record);
        System.out.println("预约记录新增成功");
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
        ReservationRecord reservation_record = new ReservationRecord();
            reservation_record.setBooking_code(paramMap.get("booking_code")==null?null:String.valueOf(paramMap.get("booking_code")));
                    reservation_record.setService_code(paramMap.get("service_code")==null?null:String.valueOf(paramMap.get("service_code")));
                    reservation_record.setService_name(paramMap.get("service_name")==null?null:String.valueOf(paramMap.get("service_name")));
                    reservation_record.setService_price(paramMap.get("service_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("service_price"))));
                    reservation_record.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    reservation_record.setOwners_name(paramMap.get("owners_name")==null?null:String.valueOf(paramMap.get("owners_name")));
                    reservation_record.setContact_number(paramMap.get("contact_number")==null?null:String.valueOf(paramMap.get("contact_number")));
                    if (paramMap.get("appointment_date" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("appointment_date" )))) {
            String timStr = String.valueOf(paramMap.get("appointment_date" ));
            reservation_record.setAppointment_date(parseToTimestamp(timStr));
        } else {
            reservation_record.setAppointment_date(null);
        }
                    reservation_record.setAppointment_period(paramMap.get("appointment_period")==null?null:String.valueOf(paramMap.get("appointment_period")));
                    reservation_record.setNumber_of_appointments(paramMap.get("number_of_appointments")==null?null:String.valueOf(paramMap.get("number_of_appointments")));
                    if (paramMap.get("delivery_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("delivery_time" )))) {
            String timStr = String.valueOf(paramMap.get("delivery_time" ));
            reservation_record.setDelivery_time(parseToTimestamp(timStr));
        } else {
            reservation_record.setDelivery_time(null);
        }
                    reservation_record.setSales_consultant(paramMap.get("sales_consultant")==null?null:String.valueOf(paramMap.get("sales_consultant")));
                    reservation_record.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                    reservation_record.setFrame_number(paramMap.get("frame_number")==null?null:String.valueOf(paramMap.get("frame_number")));
                    reservation_record.setCar_type(paramMap.get("car_type")==null?null:String.valueOf(paramMap.get("car_type")));
                    reservation_record.setAppointment_remarks(paramMap.get("appointment_remarks")==null?null:String.valueOf(paramMap.get("appointment_remarks")));
                    reservation_record.setSales_manager(paramMap.get("sales_manager")==null?null:Integer.valueOf(String.valueOf(paramMap.get("sales_manager"))));
                    reservation_record.setAccount_manager(paramMap.get("account_manager")==null?null:Integer.valueOf(String.valueOf(paramMap.get("account_manager"))));
                reservation_record.setPay_state(paramMap.get("pay_state")==null?null:String.valueOf(paramMap.get("pay_state")));
        reservation_record.setPay_type(paramMap.get("pay_type")==null?null:String.valueOf(paramMap.get("pay_type")));
                                reservation_record.setSource_table(paramMap.get("source_table")==null?null:String.valueOf(paramMap.get("source_table")));
        reservation_record.setSource_id(paramMap.get("source_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_id"))));
        reservation_record.setSource_user_id(paramMap.get("source_user_id")==null?null:Integer.valueOf(String.valueOf(paramMap.get("source_user_id"))));
                this.setEntity(queryMap,configMap,reservation_record);
        System.out.println("预约记录修改成功");
        return success(1);
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
        ReservationRecord reservation_record = service.findOne(queryMap);

        if (reservation_record!=null) {
            // 更新支付状态
            reservation_record.setPay_state(newState);
            this.setEntity(queryMap,new HashMap<>(),reservation_record);

            return "支付成功";
        } else {
            return "支付成功：记录不存在";
        }
    }
}
