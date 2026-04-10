package com.project.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.demo.dao.BusinessConsultantMapper;
import com.project.demo.dao.FinancialUserMapper;
import com.project.demo.dao.InfoUserMapper;
import com.project.demo.dao.OwnerUserMapper;
import com.project.demo.entity.BusinessConsultant;
import com.project.demo.entity.FinancialUser;
import com.project.demo.entity.InfoUser;
import com.project.demo.entity.OwnerUser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 批量导入 Excel 服务
 */
@Service
public class ImportExcelService {

    @Autowired
    private InfoUserMapper infoUserMapper;

    @Autowired
    private BusinessConsultantMapper businessConsultantMapper;

    @Autowired
    private FinancialUserMapper financialUserMapper;

    @Autowired
    private OwnerUserMapper ownerUserMapper;

    @Autowired
    private StoreService storeService;

    public static final String ROLE_STORE_ADMIN = "store_admin";
    public static final String ROLE_HQ_INFO = "headquarter_info";
    public static final String ROLE_CONSULTANT = "consultant";
    public static final String ROLE_FINANCIAL = "financial";
    public static final String ROLE_MEMBER = "member";

    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, String roleType, Integer storeId, Integer createBy) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> failedList = new ArrayList<>();
        int successCount = 0;
        int pendingReviewCount = 0;

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int totalRows = sheet.getLastRowNum();
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    Map<String, Object> data = parseRow(row, roleType);
                    String error = validateData(data, roleType);
                    if (error != null) {
                        failedList.add(createFailedItem(i + 1, error));
                        continue;
                    }

                    int roleId = 0;
                    switch (roleType) {
                        case ROLE_STORE_ADMIN:
                            String storeName = (String) data.get("store_name");
                            Integer targetStoreId = storeService.getIdByName(storeName);
                            if (targetStoreId == null || targetStoreId == 0) {
                                failedList.add(createFailedItem(i + 1, "所属店面不存在: " + storeName));
                                continue;
                            }
                            roleId = insertStoreAdmin(data, targetStoreId, createBy);
                            pendingReviewCount++;
                            break;

                        case ROLE_HQ_INFO:
                            roleId = insertHqInfo(data, createBy);
                            break;

                        case ROLE_CONSULTANT:
                            if (storeId == null || storeId <= 0) {
                                failedList.add(createFailedItem(i + 1, "店面ID无效"));
                                continue;
                            }
                            roleId = insertConsultant(data, storeId, createBy);
                            pendingReviewCount++;
                            break;

                        case ROLE_FINANCIAL:
                            if (storeId == null || storeId <= 0) {
                                failedList.add(createFailedItem(i + 1, "店面ID无效"));
                                continue;
                            }
                            roleId = insertFinancial(data, storeId, createBy);
                            pendingReviewCount++;
                            break;

                        case ROLE_MEMBER:
                            Integer memberStoreId = determineMemberStoreId(data, storeId);
                            roleId = insertMember(data, memberStoreId, createBy);
                            break;

                        default:
                            failedList.add(createFailedItem(i + 1, "不支持的角色类型: " + roleType));
                            continue;
                    }

                    if (roleId > 0) {
                        successCount++;
                    }

                } catch (Exception e) {
                    failedList.add(createFailedItem(i + 1, e.getMessage()));
                }
            }

            workbook.close();

        } catch (Exception e) {
            result.put("error", "Excel解析失败: " + e.getMessage());
            return result;
        }

        result.put("total", successCount + failedList.size());
        result.put("success", successCount);
        result.put("failed", failedList.size());
        result.put("pending_review", pendingReviewCount);
        result.put("failed_list", failedList);
        return result;
    }

    private Map<String, Object> parseRow(Row row, String roleType) {
        Map<String, Object> data = new HashMap<>();

        switch (roleType) {
            case ROLE_STORE_ADMIN:
                data.put("employee_work_number", getCellValue(row.getCell(0)));
                data.put("employee_name", getCellValue(row.getCell(1)));
                data.put("employee_gender", getCellValue(row.getCell(2)));
                data.put("contact_number", getCellValue(row.getCell(3)));
                data.put("store_name", getCellValue(row.getCell(4)));
                break;

            case ROLE_HQ_INFO:
                data.put("employee_work_number", getCellValue(row.getCell(0)));
                data.put("employee_name", getCellValue(row.getCell(1)));
                data.put("employee_gender", getCellValue(row.getCell(2)));
                data.put("contact_number", getCellValue(row.getCell(3)));
                break;

            case ROLE_CONSULTANT:
                data.put("employee_work_number", getCellValue(row.getCell(0)));
                data.put("employee_name", getCellValue(row.getCell(1)));
                data.put("consultant_gender", getCellValue(row.getCell(2)));
                data.put("contact_number", getCellValue(row.getCell(3)));
                data.put("department", getCellValue(row.getCell(4)));
                break;

            case ROLE_FINANCIAL:
                data.put("employee_work_number", getCellValue(row.getCell(0)));
                data.put("employee_name", getCellValue(row.getCell(1)));
                data.put("financial_gender", getCellValue(row.getCell(2)));
                data.put("contact_number", getCellValue(row.getCell(3)));
                break;

            case ROLE_MEMBER:
                data.put("owners_name", getCellValue(row.getCell(0)));
                data.put("owners_gender", getCellValue(row.getCell(1)));
                data.put("contact_number", getCellValue(row.getCell(2)));
                data.put("driving_license", getCellValue(row.getCell(3)));
                data.put("member_level", getCellValue(row.getCell(4)));
                data.put("store_name", getCellValue(row.getCell(5)));
                break;
        }

        return data;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(cell.getDateCellValue());
                } else {
                    double value = cell.getNumericCellValue();
                    if (value == Math.floor(value)) {
                        return String.valueOf((long) value);
                    }
                    return String.valueOf(value);
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                try {
                    return String.valueOf(cell.getStringCellValue());
                } catch (Exception e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }

    private String validateData(Map<String, Object> data, String roleType) {
        switch (roleType) {
            case ROLE_STORE_ADMIN:
            case ROLE_HQ_INFO:
                String workNumber = (String) data.get("employee_work_number");
                if (workNumber == null || workNumber.isEmpty()) {
                    return "员工工号不能为空";
                }
                QueryWrapper<InfoUser> infoWrapper = new QueryWrapper<>();
                infoWrapper.eq("employee_work_number", workNumber);
                if (infoUserMapper.selectCount(infoWrapper) > 0) {
                    return "员工工号已存在: " + workNumber;
                }
                String name = (String) data.get("employee_name");
                if (name == null || name.isEmpty()) {
                    return "员工姓名不能为空";
                }
                break;

            case ROLE_CONSULTANT:
                String consultantWorkNumber = (String) data.get("employee_work_number");
                if (consultantWorkNumber == null || consultantWorkNumber.isEmpty()) {
                    return "员工工号不能为空";
                }
                QueryWrapper<BusinessConsultant> bcWrapper = new QueryWrapper<>();
                bcWrapper.eq("employee_work_number", consultantWorkNumber);
                if (businessConsultantMapper.selectCount(bcWrapper) > 0) {
                    return "员工工号已存在: " + consultantWorkNumber;
                }
                break;

            case ROLE_FINANCIAL:
                String financialWorkNumber = (String) data.get("employee_work_number");
                if (financialWorkNumber == null || financialWorkNumber.isEmpty()) {
                    return "员工工号不能为空";
                }
                QueryWrapper<FinancialUser> fuWrapper = new QueryWrapper<>();
                fuWrapper.eq("employee_work_number", financialWorkNumber);
                if (financialUserMapper.selectCount(fuWrapper) > 0) {
                    return "员工工号已存在: " + financialWorkNumber;
                }
                break;

            case ROLE_MEMBER:
                String ownersName = (String) data.get("owners_name");
                if (ownersName == null || ownersName.isEmpty()) {
                    return "车主姓名不能为空";
                }
                String contact = (String) data.get("contact_number");
                if (contact == null || contact.isEmpty()) {
                    return "联系号码不能为空";
                }
                QueryWrapper<OwnerUser> ouWrapper = new QueryWrapper<>();
                ouWrapper.eq("contact_number", contact);
                if (ownerUserMapper.selectCount(ouWrapper) > 0) {
                    return "联系号码已存在: " + contact;
                }
                break;
        }

        return null;
    }

    private int insertStoreAdmin(Map<String, Object> data, Integer storeId, Integer createBy) {
        InfoUser infoUser = new InfoUser();
        infoUser.setEmployeeWorkNumber((String) data.get("employee_work_number"));
        infoUser.setEmployeeName((String) data.get("employee_name"));
        infoUser.setEmployeeGender((String) data.get("employee_gender"));
        infoUser.setContactNumber((String) data.get("contact_number"));
        infoUser.setStoreId(storeId);
        infoUser.setExamineState("待审核");
        infoUser.setCreateBy(createBy);
        infoUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserMapper.insert(infoUser);
        return infoUser.getInfoUserId();
    }

    private int insertHqInfo(Map<String, Object> data, Integer createBy) {
        InfoUser infoUser = new InfoUser();
        infoUser.setEmployeeWorkNumber((String) data.get("employee_work_number"));
        infoUser.setEmployeeName((String) data.get("employee_name"));
        infoUser.setEmployeeGender((String) data.get("employee_gender"));
        infoUser.setContactNumber((String) data.get("contact_number"));
        infoUser.setStoreId(0);
        infoUser.setExamineState("已启用");
        infoUser.setCreateBy(createBy);
        infoUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserMapper.insert(infoUser);
        return infoUser.getInfoUserId();
    }

    private int insertConsultant(Map<String, Object> data, Integer storeId, Integer createBy) {
        BusinessConsultant consultant = new BusinessConsultant();
        consultant.setEmployeeWorkNumber((String) data.get("employee_work_number"));
        consultant.setEmployeeName((String) data.get("employee_name"));
        consultant.setConsultantGender((String) data.get("consultant_gender"));
        consultant.setContactNumber((String) data.get("contact_number"));
        consultant.setDepartment((String) data.get("department"));
        consultant.setStoreId(storeId);
        consultant.setExamineState("待审核");
        consultant.setCreateBy(createBy);
        consultant.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        businessConsultantMapper.insert(consultant);
        return consultant.getConsultantId();
    }

    private int insertFinancial(Map<String, Object> data, Integer storeId, Integer createBy) {
        FinancialUser financialUser = new FinancialUser();
        financialUser.setEmployeeWorkNumber((String) data.get("employee_work_number"));
        financialUser.setEmployeeName((String) data.get("employee_name"));
        financialUser.setFinancialGender((String) data.get("financial_gender"));
        financialUser.setContactNumber((String) data.get("contact_number"));
        financialUser.setStoreId(storeId);
        financialUser.setExamineState("待审核");
        financialUser.setCreateBy(createBy);
        financialUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        financialUserMapper.insert(financialUser);
        return financialUser.getFinancialUserId();
    }

    private int insertMember(Map<String, Object> data, Integer storeId, Integer createBy) {
        OwnerUser ownerUser = new OwnerUser();
        ownerUser.setOwnersName((String) data.get("owners_name"));
        ownerUser.setOwnersGender((String) data.get("owners_gender"));
        ownerUser.setContactNumber((String) data.get("contact_number"));
        ownerUser.setDrivingLicense((String) data.get("driving_license"));
        ownerUser.setMemberLevel((String) data.get("member_level"));
        ownerUser.setStoreId(storeId);
        ownerUser.setCreateBy(createBy);
        ownerUser.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        ownerUserMapper.insert(ownerUser);
        return ownerUser.getOwnerUserId();
    }

    private Integer determineMemberStoreId(Map<String, Object> data, Integer currentStoreId) {
        String storeName = (String) data.get("store_name");
        if (storeName == null || storeName.trim().isEmpty()) {
            return currentStoreId != null ? currentStoreId : 0;
        }
        return storeService.getIdByName(storeName);
    }

    private Map<String, Object> createFailedItem(int rowNum, String reason) {
        Map<String, Object> item = new HashMap<>();
        item.put("row", rowNum);
        item.put("reason", reason);
        return item;
    }

    public Workbook generateTemplate(String roleType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("导入模板");

        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        List<String> headers = getHeaders(roleType);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        Row dataRow = sheet.createRow(1);
        List<String> examples = getExamples(roleType);
        for (int i = 0; i < examples.size(); i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(examples.get(i));
        }

        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }

    private List<String> getHeaders(String roleType) {
        switch (roleType) {
            case ROLE_STORE_ADMIN:
                return Arrays.asList("员工工号", "员工姓名", "员工性别", "联系号码", "所属店面");
            case ROLE_HQ_INFO:
                return Arrays.asList("员工工号", "员工姓名", "员工性别", "联系号码");
            case ROLE_CONSULTANT:
                return Arrays.asList("员工工号", "员工姓名", "性别", "联系号码", "所属部门");
            case ROLE_FINANCIAL:
                return Arrays.asList("员工工号", "员工姓名", "性别", "联系号码");
            case ROLE_MEMBER:
                return Arrays.asList("车主姓名", "车主性别", "联系号码", "驾驶证件", "会员等级", "所属店面");
            default:
                return new ArrayList<>();
        }
    }

    private List<String> getExamples(String roleType) {
        switch (roleType) {
            case ROLE_STORE_ADMIN:
                return Arrays.asList("S001", "张三", "男", "13900139001", "北京旗舰店");
            case ROLE_HQ_INFO:
                return Arrays.asList("H001", "李四", "女", "13800138001");
            case ROLE_CONSULTANT:
                return Arrays.asList("C001", "王五", "男", "13700137001", "销售部");
            case ROLE_FINANCIAL:
                return Arrays.asList("F001", "赵六", "女", "13600136001");
            case ROLE_MEMBER:
                return Arrays.asList("钱七", "男", "13500135001", "3100000001", "普通会员", "北京旗舰店");
            default:
                return new ArrayList<>();
        }
    }
}
