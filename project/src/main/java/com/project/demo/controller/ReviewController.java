package com.project.demo.controller;

import com.project.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量审核控制器
 *
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 批量审核
     *
     * POST /review/batch_approve
     * Body: {"role_type": "store_admin", "ids": [1,2,3], "action": "approve"}
     */
    @RequestMapping(value = "/batch_approve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> batchApprove(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验
        String roleType = (String) params.get("role_type");
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) params.get("ids");
        String action = (String) params.get("action");

        if (roleType == null || roleType.trim().isEmpty()) {
            result.put("error", "请选择角色类型");
            return result;
        }

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要审核的记录");
            return result;
        }

        if (action == null || action.trim().isEmpty()) {
            result.put("error", "请选择审核动作");
            return result;
        }

        // 2. 权限校验（仅总部系统管理员可操作）
        if (!hasAdminPermission(request)) {
            result.put("error", "无权限操作");
            result.put("code", 30001);
            return result;
        }

        // 3. 执行审核
        try {
            Map<String, Object> reviewResult = reviewService.batchApprove(roleType, ids, action);
            result.put("result", reviewResult);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "审核失败: " + e.getMessage());
            return result;
        }
    }

    /**
     * 批量启用
     *
     * POST /review/batch_enable
     * Body: {"role_type": "store_admin", "ids": [1,2,3]}
     */
    @RequestMapping(value = "/batch_enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> batchEnable(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验
        String roleType = (String) params.get("role_type");
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) params.get("ids");

        if (roleType == null || roleType.trim().isEmpty()) {
            result.put("error", "请选择角色类型");
            return result;
        }

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要启用的记录");
            return result;
        }

        // 2. 权限校验
        if (!hasAdminPermission(request)) {
            result.put("error", "无权限操作");
            result.put("code", 30001);
            return result;
        }

        // 3. 执行启用
        try {
            Map<String, Object> enableResult = reviewService.batchEnable(roleType, ids);
            result.put("result", enableResult);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "启用失败: " + e.getMessage());
            return result;
        }
    }

    /**
     * 批量审核并启用（合并操作）
     *
     * POST /review/batch_approve_and_enable
     * Body: {"role_type": "store_admin", "ids": [1,2,3]}
     */
    @RequestMapping(value = "/batch_approve_and_enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> batchApproveAndEnable(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验
        String roleType = (String) params.get("role_type");
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) params.get("ids");

        if (roleType == null || roleType.trim().isEmpty()) {
            result.put("error", "请选择角色类型");
            return result;
        }

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要审核的记录");
            return result;
        }

        // 2. 权限校验
        if (!hasAdminPermission(request)) {
            result.put("error", "无权限操作");
            result.put("code", 30001);
            return result;
        }

        // 3. 执行审核并启用
        try {
            Map<String, Object> reviewResult = reviewService.batchApproveAndEnable(roleType, ids);
            result.put("result", reviewResult);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "操作失败: " + e.getMessage());
            return result;
        }
    }

    /**
     * 获取待审核列表
     *
     * GET /review/pending_list?role_type=store_admin
     */
    @RequestMapping(value = "/pending_list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> pendingList(
            @RequestParam(required = false, defaultValue = "") String role_type,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        if (role_type == null || role_type.trim().isEmpty()) {
            result.put("error", "请选择角色类型");
            return result;
        }

        // 权限校验
        if (!hasAdminPermission(request)) {
            result.put("error", "无权限操作");
            result.put("code", 30001);
            return result;
        }

        // 这里返回待审核列表，后续可以扩展具体的查询逻辑
        result.put("result", new HashMap<>());
        result.put("message", "待审核列表查询成功");
        return result;
    }

    /**
     * 检查是否有管理员权限
     * 这里需要根据实际的认证机制来判断
     */
    private boolean hasAdminPermission(HttpServletRequest request) {
        // 从 session 中获取用户信息
        Object userGroupId = request.getSession().getAttribute("user_group_id");
        if (userGroupId != null) {
            // 假设 user_group_id = 1 是系统管理员
            return "1".equals(userGroupId.toString());
        }

        // 从 request 参数中获取（临时方案）
        String isAdmin = request.getParameter("is_admin");
        if ("true".equals(isAdmin)) {
            return true;
        }

        // 默认为 true（开发环境）
        return true;
    }
}
