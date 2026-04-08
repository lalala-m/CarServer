package com.project.demo.controller;

import com.project.demo.entity.VehicleInformation;
import com.project.demo.service.VehicleInformationService;
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
 * 车辆信息：(VehicleInformation)表控制层
 *
 */
@RestController
@RequestMapping("/vehicle_information")
public class VehicleInformationController extends BaseController<VehicleInformation, VehicleInformationService> {

    /**
     * 车辆信息对象
     */
    @Autowired
    public VehicleInformationController(VehicleInformationService service) {
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
        VehicleInformation vehicle_information = new VehicleInformation();
            vehicle_information.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            vehicle_information.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                            vehicle_information.setCar_type(paramMap.get("car_type")==null?null:String.valueOf(paramMap.get("car_type")));
                            vehicle_information.setFrame_number(paramMap.get("frame_number")==null?null:String.valueOf(paramMap.get("frame_number")));
                            vehicle_information.setVehicle_remarks(paramMap.get("vehicle_remarks")==null?null:String.valueOf(paramMap.get("vehicle_remarks")));
                        vehicle_information.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        vehicle_information.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
                                                                                                                                                    vehicle_information.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(vehicle_information);
        System.out.println("车辆信息新增成功");
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
        VehicleInformation vehicle_information = new VehicleInformation();
            vehicle_information.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    vehicle_information.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                    vehicle_information.setCar_type(paramMap.get("car_type")==null?null:String.valueOf(paramMap.get("car_type")));
                    vehicle_information.setFrame_number(paramMap.get("frame_number")==null?null:String.valueOf(paramMap.get("frame_number")));
                    vehicle_information.setVehicle_remarks(paramMap.get("vehicle_remarks")==null?null:String.valueOf(paramMap.get("vehicle_remarks")));
                vehicle_information.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        vehicle_information.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
                                    this.setEntity(queryMap,configMap,vehicle_information);
        System.out.println("车辆信息修改成功");
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
        VehicleInformation vehicle_information = service.findOne(queryMap);

        if (vehicle_information!=null) {
            // 更新审核状态
            vehicle_information.setExamine_state(newState);
            this.setEntity(queryMap,new HashMap<>(),vehicle_information);

            return "审核成功";
        } else {
            return "审核失败：记录不存在";
        }
    }
}
