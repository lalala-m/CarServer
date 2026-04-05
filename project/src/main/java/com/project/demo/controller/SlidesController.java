package com.project.demo.controller;

import com.alibaba.fastjson.JSON;
import com.project.demo.entity.Slides;
import com.project.demo.service.SlidesService;

import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import com.alibaba.fastjson.JSONObject;

/**
 * 轮播图：(Slides)表控制层
 */
@RestController
@RequestMapping("slides")
public class SlidesController extends BaseController<Slides, SlidesService> {
    /**
     * 服务对象
     */
    @Autowired
    public SlidesController(SlidesService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        // 将请求体转换为对象
        Slides slides = convertRequestBodyToObject(request);
        // 验证参数是否合法
        validateObject(slides);
        this.addEntity(slides);
        System.out.println("轮播图新增成功");
        return success(1);
    }

    @PostMapping("/set")
    @Transactional
    public Map<String, Object> set(HttpServletRequest request) throws IOException {
        Slides slides = convertRequestBodyToObject(request);
        this.service.updateEntity(service.readQuery(request), service.readConfig(request), slides);
        return success(1);
    }

    @RequestMapping(value = "/del")
    @Transactional
    public Map<String, Object> del(HttpServletRequest request) {
        Map<String, String> query = validateQuery(request);

        // 读取配置信息（不校验）
        Map<String, String> config = service.readConfig(request);

        // 执行删除操作并返回结果
        executeDeleteAndReturnResult(query, config);
        return success(1);
    }

    @RequestMapping("/get_obj")
    public Map<String, Object> obj(HttpServletRequest request) {
        Map<String, String> query = validateQuery(request);
        // 读取配置信息
        Map<String, String> config = service.readConfig(request);
        List resultList = service.selectBaseList(service.select(query, config));
        if (resultList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("obj",resultList.get(0));
            return success(jsonObject);
        } else {
            return success(null);
        }
    }

    /**
     * 将请求体转换为对象
     */
    private Slides convertRequestBodyToObject(HttpServletRequest request) throws IOException {
        Map<String, Object> bodyMap = service.readBody(request.getReader());
        return JSON.parseObject(JSON.toJSONString(bodyMap), Slides.class);
    }

    /**
     * 验证对象的参数是否合法
     */
    private void validateObject(Slides slides) {
            if (isNullOrBlank(slides.getTitle())) {
            throw new IllegalArgumentException("标题不能为空");
        }
        }

    /**
     * 判断字符串是否为空或空白
     */
    private boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 校验并返回查询条件
     */
    private Map<String, String> validateQuery(HttpServletRequest request) {
        Map<String, String> query = service.readQuery(request);
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("查询条件不能为空");
        }
        return query;
    }

    /**
     * 执行删除操作
     */
    private void executeDeleteAndReturnResult(Map<String, String> query, Map<String, String> config) {
        this.service.delete(query, config);
    }
}


