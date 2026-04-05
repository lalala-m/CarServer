package com.project.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 根路径说明：context-path 为 /api，业务接口在 /api/xxx 下，直接访问 /api/ 原先无映射会 404。
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", "ok");
        body.put("message", "车友服务系统后端已运行");
        body.put("apiPrefix", "/api");
        body.put("examples", new String[]{
                "POST http://localhost:5000/api/user/login",
                "GET  http://localhost:5000/api/user/state"
        });
        body.put("adminTip", "管理端前端一般在 project/src/main/resources/static/admin 下用 npm run serve 单独启动，或按项目文档访问打包后的页面");
        return body;
    }
}
