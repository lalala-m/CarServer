package com.project.demo.service;

import com.project.demo.dao.BusinessConsultantMapper;
import com.project.demo.dao.FinancialUserMapper;
import com.project.demo.dao.InfoUserMapper;
import com.project.demo.dao.OwnerUserMapper;
import com.project.demo.entity.BusinessConsultant;
import com.project.demo.dao.FinancialUserMapper;
import com.project.demo.entity.FinancialUser;
import com.project.demo.entity.InfoUser;
import com.project.demo.entity.OwnerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 批量审核服务
 *
 */
@Service
public class ReviewService {

    @Autowired
    private InfoUserMapper infoUserMapper;

    @Autowired
    private BusinessConsultantMapper businessConsultantMapper;

    @Autowired
    private FinancialUserMapper financialUserMapper;

    @Autowired
    private OwnerUserMapper ownerUserMapper;

    // 角色类型常量
    public static final String ROLE_STORE_ADMIN = "store_admin";
    public static final String ROLE_HQ_INFO = "headquarter_info";
    public static final String ROLE_CONSULTANT = "consultant";
    public static final String ROLE_FINANCIAL = "financial";
    public static final String ROLE_MEMBER = "member";

    // 审核状态常量
    public static final String STATE_PENDING = "待审核";
    public static final String STATE_APPROVED = "已通过";
    public static final String STATE_REJECTED = "已拒绝";
    public static final String STATE_ENABLED = "已启用";

    /**
     * 批量审核
     *
     * @param roleType 角色类型
     * @param ids      待审核的记录ID数组
     * @param action   审核动作：approve（通过）/ reject（拒绝）
     * @return 审核结果
     */
    @Transactional
    public Map<String, Object> batchApprove(String roleType, List<Integer> ids, String action) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> failedList = new ArrayList<>();
        int successCount = 0;

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要审核的记录");
            return result;
        }

        for (Integer id : ids) {
            try {
                boolean success = false;
                switch (roleType) {
                    case ROLE_STORE_ADMIN:
                    case ROLE_HQ_INFO:
                        success = approveOrRejectInfoUser(id, action);
                        break;
                    case ROLE_CONSULTANT:
                        success = approveOrRejectConsultant(id, action);
                        break;
                    case ROLE_FINANCIAL:
                        success = approveOrRejectFinancial(id, action);
                        break;
                    case ROLE_MEMBER:
                        success = approveOrRejectMember(id, action);
                        break;
                    default:
                        failedList.add(createFailedItem(id, "不支持的角色类型: " + roleType));
                        continue;
                }

                if (success) {
                    successCount++;
                } else {
                    failedList.add(createFailedItem(id, "记录不存在或状态不允许该操作"));
                }
            } catch (Exception e) {
                failedList.add(createFailedItem(id, e.getMessage()));
            }
        }

        result.put("total", ids.size());
        result.put("success", successCount);
        result.put("failed", failedList.size());
        result.put("failed_list", failedList);
        return result;
    }

    /**
     * 批量启用
     *
     * @param roleType 角色类型
     * @param ids      待启用的记录ID数组
     * @return 启用结果
     */
    @Transactional
    public Map<String, Object> batchEnable(String roleType, List<Integer> ids) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> failedList = new ArrayList<>();
        int successCount = 0;

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要启用的记录");
            return result;
        }

        for (Integer id : ids) {
            try {
                boolean success = false;
                switch (roleType) {
                    case ROLE_STORE_ADMIN:
                    case ROLE_HQ_INFO:
                        success = enableInfoUser(id);
                        break;
                    case ROLE_CONSULTANT:
                        success = enableConsultant(id);
                        break;
                    case ROLE_FINANCIAL:
                        success = enableFinancial(id);
                        break;
                    case ROLE_MEMBER:
                        success = enableMember(id);
                        break;
                    default:
                        failedList.add(createFailedItem(id, "不支持的角色类型: " + roleType));
                        continue;
                }

                if (success) {
                    successCount++;
                } else {
                    failedList.add(createFailedItem(id, "记录不存在或状态不允许启用"));
                }
            } catch (Exception e) {
                failedList.add(createFailedItem(id, e.getMessage()));
            }
        }

        result.put("total", ids.size());
        result.put("success", successCount);
        result.put("failed", failedList.size());
        result.put("failed_list", failedList);
        return result;
    }

    /**
     * 批量审核并启用（合并操作）
     *
     * @param roleType 角色类型
     * @param ids      待审核并启用的记录ID数组
     * @return 结果
     */
    @Transactional
    public Map<String, Object> batchApproveAndEnable(String roleType, List<Integer> ids) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> failedList = new ArrayList<>();
        int successCount = 0;

        if (ids == null || ids.isEmpty()) {
            result.put("error", "请选择要审核的记录");
            return result;
        }

        for (Integer id : ids) {
            try {
                boolean success = false;
                switch (roleType) {
                    case ROLE_STORE_ADMIN:
                    case ROLE_HQ_INFO:
                        success = approveAndEnableInfoUser(id);
                        break;
                    case ROLE_CONSULTANT:
                        success = approveAndEnableConsultant(id);
                        break;
                    case ROLE_FINANCIAL:
                        success = approveAndEnableFinancial(id);
                        break;
                    case ROLE_MEMBER:
                        success = approveAndEnableMember(id);
                        break;
                    default:
                        failedList.add(createFailedItem(id, "不支持的角色类型: " + roleType));
                        continue;
                }

                if (success) {
                    successCount++;
                } else {
                    failedList.add(createFailedItem(id, "记录不存在或状态不允许该操作"));
                }
            } catch (Exception e) {
                failedList.add(createFailedItem(id, e.getMessage()));
            }
        }

        result.put("total", ids.size());
        result.put("success", successCount);
        result.put("failed", failedList.size());
        result.put("failed_list", failedList);
        return result;
    }

    /**
     * 审核或拒绝信息员
     */
    private boolean approveOrRejectInfoUser(Integer id, String action) {
        InfoUser infoUser = infoUserMapper.selectById(id);
        if (infoUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(infoUser.getExamineState())) {
            return false;
        }

        if ("approve".equals(action)) {
            infoUser.setExamineState(STATE_APPROVED);
        } else if ("reject".equals(action)) {
            infoUser.setExamineState(STATE_REJECTED);
        } else {
            return false;
        }

        infoUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserMapper.updateById(infoUser);
        return true;
    }

    /**
     * 审核或拒绝业务顾问
     */
    private boolean approveOrRejectConsultant(Integer id, String action) {
        BusinessConsultant consultant = businessConsultantMapper.selectById(id);
        if (consultant == null) {
            return false;
        }
        if (!STATE_PENDING.equals(consultant.getExamineState())) {
            return false;
        }

        if ("approve".equals(action)) {
            consultant.setExamineState(STATE_APPROVED);
        } else if ("reject".equals(action)) {
            consultant.setExamineState(STATE_REJECTED);
        } else {
            return false;
        }

        consultant.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        businessConsultantMapper.updateById(consultant);
        return true;
    }

    /**
     * 审核或拒绝财务审核
     */
    private boolean approveOrRejectFinancial(Integer id, String action) {
        FinancialUser financialUser = financialUserMapper.selectById(id);
        if (financialUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(financialUser.getExamineState())) {
            return false;
        }

        if ("approve".equals(action)) {
            financialUser.setExamineState(STATE_APPROVED);
        } else if ("reject".equals(action)) {
            financialUser.setExamineState(STATE_REJECTED);
        } else {
            return false;
        }

        financialUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        financialUserMapper.updateById(financialUser);
        return true;
    }

    /**
     * 审核或拒绝会员
     */
    private boolean approveOrRejectMember(Integer id, String action) {
        OwnerUser ownerUser = ownerUserMapper.selectById(id);
        if (ownerUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(ownerUser.getExamineState())) {
            return false;
        }

        if ("approve".equals(action)) {
            ownerUser.setExamineState(STATE_APPROVED);
        } else if ("reject".equals(action)) {
            ownerUser.setExamineState(STATE_REJECTED);
        } else {
            return false;
        }

        ownerUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        ownerUserMapper.updateById(ownerUser);
        return true;
    }

    /**
     * 启用信息员
     */
    private boolean enableInfoUser(Integer id) {
        InfoUser infoUser = infoUserMapper.selectById(id);
        if (infoUser == null) {
            return false;
        }
        if (!STATE_APPROVED.equals(infoUser.getExamineState())) {
            return false;
        }

        infoUser.setExamineState(STATE_ENABLED);
        infoUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserMapper.updateById(infoUser);
        return true;
    }

    /**
     * 启用业务顾问
     */
    private boolean enableConsultant(Integer id) {
        BusinessConsultant consultant = businessConsultantMapper.selectById(id);
        if (consultant == null) {
            return false;
        }
        if (!STATE_APPROVED.equals(consultant.getExamineState())) {
            return false;
        }

        consultant.setExamineState(STATE_ENABLED);
        consultant.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        businessConsultantMapper.updateById(consultant);
        return true;
    }

    /**
     * 启用财务审核
     */
    private boolean enableFinancial(Integer id) {
        FinancialUser financialUser = financialUserMapper.selectById(id);
        if (financialUser == null) {
            return false;
        }
        if (!STATE_APPROVED.equals(financialUser.getExamineState())) {
            return false;
        }

        financialUser.setExamineState(STATE_ENABLED);
        financialUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        financialUserMapper.updateById(financialUser);
        return true;
    }

    /**
     * 启用会员
     */
    private boolean enableMember(Integer id) {
        OwnerUser ownerUser = ownerUserMapper.selectById(id);
        if (ownerUser == null) {
            return false;
        }
        if (!STATE_APPROVED.equals(ownerUser.getExamineState())) {
            return false;
        }

        ownerUser.setExamineState(STATE_ENABLED);
        ownerUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        ownerUserMapper.updateById(ownerUser);
        return true;
    }

    /**
     * 审核并启用信息员
     */
    private boolean approveAndEnableInfoUser(Integer id) {
        InfoUser infoUser = infoUserMapper.selectById(id);
        if (infoUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(infoUser.getExamineState())) {
            return false;
        }

        infoUser.setExamineState(STATE_ENABLED);
        infoUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        infoUserMapper.updateById(infoUser);
        return true;
    }

    /**
     * 审核并启用业务顾问
     */
    private boolean approveAndEnableConsultant(Integer id) {
        BusinessConsultant consultant = businessConsultantMapper.selectById(id);
        if (consultant == null) {
            return false;
        }
        if (!STATE_PENDING.equals(consultant.getExamineState())) {
            return false;
        }

        consultant.setExamineState(STATE_ENABLED);
        consultant.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        businessConsultantMapper.updateById(consultant);
        return true;
    }

    /**
     * 审核并启用财务审核
     */
    private boolean approveAndEnableFinancial(Integer id) {
        FinancialUser financialUser = financialUserMapper.selectById(id);
        if (financialUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(financialUser.getExamineState())) {
            return false;
        }

        financialUser.setExamineState(STATE_ENABLED);
        financialUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        financialUserMapper.updateById(financialUser);
        return true;
    }

    /**
     * 审核并启用会员
     */
    private boolean approveAndEnableMember(Integer id) {
        OwnerUser ownerUser = ownerUserMapper.selectById(id);
        if (ownerUser == null) {
            return false;
        }
        if (!STATE_PENDING.equals(ownerUser.getExamineState())) {
            return false;
        }

        ownerUser.setExamineState(STATE_ENABLED);
        ownerUser.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        ownerUserMapper.updateById(ownerUser);
        return true;
    }

    /**
     * 创建失败项
     */
    private Map<String, Object> createFailedItem(Object id, String reason) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", id);
        item.put("reason", reason);
        return item;
    }
}
