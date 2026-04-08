package com.project.demo.controller;

import com.project.demo.entity.CardType;
import com.project.demo.service.CardTypeService;
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
 * 卡券类型：(CardType)表控制层
 *
 */
@RestController
@RequestMapping("/card_type")
public class CardTypeController extends BaseController<CardType, CardTypeService> {

    /**
     * 卡券类型对象
     */
    @Autowired
    public CardTypeController(CardTypeService service) {
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
        CardType card_type = new CardType();
            card_type.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                if (card_type.getCard_name()== null || card_type.getCard_name().trim().isEmpty()){
            throw new IllegalArgumentException("卡券名称不能为空");
        }
                        if (paramMap.get("effective_period" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("effective_period" )))) {
            String timStr = String.valueOf(paramMap.get("effective_period" ));
            card_type.setEffective_period(parseToTimestamp(timStr));
        } else {
            card_type.setEffective_period(null);
        }
                            card_type.setCard_content(paramMap.get("card_content")==null?null:String.valueOf(paramMap.get("card_content")));
                            card_type.setApplicable_items(paramMap.get("applicable_items")==null?null:String.valueOf(paramMap.get("applicable_items")));
                                                                                                                                                                    card_type.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(card_type);
        System.out.println("卡券类型新增成功");
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
        CardType card_type = new CardType();
            card_type.setCard_name(paramMap.get("card_name")==null?null:String.valueOf(paramMap.get("card_name")));
                    if (paramMap.get("effective_period" ) != null && !StringUtils.isEmpty(String.valueOf(paramMap.get("effective_period" )))) {
            String timStr = String.valueOf(paramMap.get("effective_period" ));
            card_type.setEffective_period(parseToTimestamp(timStr));
        } else {
            card_type.setEffective_period(null);
        }
                    card_type.setCard_content(paramMap.get("card_content")==null?null:String.valueOf(paramMap.get("card_content")));
                    card_type.setApplicable_items(paramMap.get("applicable_items")==null?null:String.valueOf(paramMap.get("applicable_items")));
                                            this.setEntity(queryMap,configMap,card_type);
        System.out.println("卡券类型修改成功");
        return success(1);
    }


}
