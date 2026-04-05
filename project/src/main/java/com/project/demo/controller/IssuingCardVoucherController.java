package com.project.demo.controller;

import com.project.demo.entity.IssuingCardVoucher;
import com.project.demo.service.IssuingCardVoucherService;
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
 * 发放卡券：(IssuingCardVoucher)表控制层
 *
 */
@RestController
@RequestMapping("/issuing_card_voucher")
public class IssuingCardVoucherController extends BaseController<IssuingCardVoucher, IssuingCardVoucherService> {

    /**
     * 发放卡券对象
     */
    @Autowired
    public IssuingCardVoucherController(IssuingCardVoucherService service) {
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
        IssuingCardVoucher issuing_card_voucher = new IssuingCardVoucher();
            issuing_card_voucher.setCard_code(paramMap.get("card_code")==null?null:String.valueOf(paramMap.get("card_code")));
                            issuing_card_voucher.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                            issuing_card_voucher.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                            issuing_card_voucher.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                            issuing_card_voucher.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                            issuing_card_voucher.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                            issuing_card_voucher.setEffective_period(paramMap.get("effective_period")==null?null:String.valueOf(paramMap.get("effective_period")));
                            issuing_card_voucher.setNumber_of_uses(paramMap.get("number_of_uses")==null?null:String.valueOf(paramMap.get("number_of_uses")));
                            if (paramMap.get("issuing_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("issuing_time" )))) {
            String timStr = String.valueOf(paramMap.get("issuing_time" ));
            issuing_card_voucher.setIssuing_time(parseToTimestamp(timStr));
        } else {
            issuing_card_voucher.setIssuing_time(null);
        }
                            issuing_card_voucher.setWrite_off_status(paramMap.get("write_off_status")==null?null:String.valueOf(paramMap.get("write_off_status")));
                        issuing_card_voucher.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        issuing_card_voucher.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
            issuing_card_voucher.setWrite_off_information_limit_times(paramMap.get("write_off_information_limit_times")==null?null:String.valueOf(paramMap.get("write_off_information_limit_times")));
                                                                                                                            issuing_card_voucher.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(issuing_card_voucher);
        System.out.println("发放卡券新增成功");
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
        IssuingCardVoucher issuing_card_voucher = new IssuingCardVoucher();
            issuing_card_voucher.setCard_code(paramMap.get("card_code")==null?null:String.valueOf(paramMap.get("card_code")));
                    issuing_card_voucher.setManager_user(paramMap.get("manager_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("manager_user"))));
                    issuing_card_voucher.setOwner_user(paramMap.get("owner_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("owner_user"))));
                    issuing_card_voucher.setLicense_plate_number(paramMap.get("license_plate_number")==null?null:String.valueOf(paramMap.get("license_plate_number")));
                    issuing_card_voucher.setFinancial_user(paramMap.get("financial_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("financial_user"))));
                    issuing_card_voucher.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                    issuing_card_voucher.setEffective_period(paramMap.get("effective_period")==null?null:String.valueOf(paramMap.get("effective_period")));
                    issuing_card_voucher.setNumber_of_uses(paramMap.get("number_of_uses")==null?null:String.valueOf(paramMap.get("number_of_uses")));
                    if (paramMap.get("issuing_time" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("issuing_time" )))) {
            String timStr = String.valueOf(paramMap.get("issuing_time" ));
            issuing_card_voucher.setIssuing_time(parseToTimestamp(timStr));
        } else {
            issuing_card_voucher.setIssuing_time(null);
        }
                    issuing_card_voucher.setWrite_off_status(paramMap.get("write_off_status")==null?null:String.valueOf(paramMap.get("write_off_status")));
                issuing_card_voucher.setExamine_state(paramMap.get("examine_state")==null?null:String.valueOf(paramMap.get("examine_state")));
        issuing_card_voucher.setExamine_reply(paramMap.get("examine_reply")==null?null:String.valueOf(paramMap.get("examine_reply")));
            issuing_card_voucher.setWrite_off_information_limit_times(paramMap.get("write_off_information_limit_times")==null?null:String.valueOf(paramMap.get("write_off_information_limit_times")));
                                this.setEntity(queryMap,configMap,issuing_card_voucher);
        System.out.println("发放卡券修改成功");
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
        IssuingCardVoucher issuing_card_voucher = service.findOne(queryMap);

        if (issuing_card_voucher!=null) {
            // 更新审核状态
            issuing_card_voucher.setExamine_state(newState);
            this.setEntity(queryMap,new HashMap<>(),issuing_card_voucher);

            return "审核成功";
        } else {
            return "审核失败：记录不存在";
        }
    }
}
