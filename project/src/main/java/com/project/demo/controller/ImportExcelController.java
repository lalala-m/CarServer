package com.project.demo.controller;

import com.project.demo.service.ImportExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量导入 Excel 控制器
 *
 */
@Controller
@RequestMapping("/import")
public class ImportExcelController {

    @Autowired
    private ImportExcelService importExcelService;

    /**
     * 下载导入模板
     *
     * GET /import/download_template?role_type=store_admin
     */
    @RequestMapping(value = "/download_template", method = RequestMethod.GET)
    public void downloadTemplate(
            @RequestParam(required = false, defaultValue = "") String role_type,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // 校验角色类型
            if (role_type == null || role_type.trim().isEmpty()) {
                role_type = "store_admin";
            }

            // 生成模板
            Workbook workbook = importExcelService.generateTemplate(role_type);

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("导入模板_" + role_type + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()), "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            // 输出文件
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量导入 Excel
     *
     * POST /import/excel_upload
     */
    @RequestMapping(value = "/excel_upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "role_type", required = false, defaultValue = "") String roleType,
            @RequestParam(value = "store_id", required = false, defaultValue = "0") Integer storeId,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        // 1. 校验文件是否为空
        if (file == null || file.isEmpty()) {
            result.put("error", "请上传文件");
            return result;
        }

        // 2. 校验文件名
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            result.put("error", "请上传 Excel 文件（.xlsx 或 .xls）");
            return result;
        }

        // 3. 校验角色类型
        if (roleType == null || roleType.trim().isEmpty()) {
            result.put("error", "请选择角色类型");
            return result;
        }

        // 4. 获取当前登录用户ID（从 session 或 token 中获取）
        Integer createBy = getCurrentUserId(request);

        try {
            // 5. 执行导入
            Map<String, Object> importResult = importExcelService.importExcel(file, roleType, storeId, createBy);

            if (importResult.containsKey("error")) {
                result.put("error", importResult.get("error"));
                return result;
            }

            result.put("result", importResult);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "导入失败: " + e.getMessage());
            return result;
        }
    }

    /**
     * 获取当前登录用户ID
     * 这里需要根据实际的认证机制来获取
     */
    private Integer getCurrentUserId(HttpServletRequest request) {
        // 从 session 中获取
        Object userId = request.getSession().getAttribute("user_id");
        if (userId != null) {
            return Integer.parseInt(userId.toString());
        }

        // 从 request 参数中获取（临时方案）
        String userIdStr = request.getParameter("user_id");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try {
                return Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                return 1; // 默认返回 1
            }
        }

        return 1; // 默认返回管理员 ID
    }
}
