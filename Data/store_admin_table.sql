-- =============================================
-- 4S店管理员表结构创建
-- 执行时间: 2026-04-10
-- =============================================

-- 创建 store_admin 表（如果不存在）
CREATE TABLE IF NOT EXISTS `store_admin` (
  `store_admin_id` int NOT NULL AUTO_INCREMENT COMMENT '4S店管理员ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `store_id` int DEFAULT 0 COMMENT '所属4S店ID（0表示总部）',
  `employee_work_number` varchar(50) DEFAULT NULL COMMENT '员工工号',
  `employee_name` varchar(50) DEFAULT NULL COMMENT '员工姓名',
  `employee_gender` varchar(10) DEFAULT NULL COMMENT '员工性别',
  `contact_number` varchar(20) DEFAULT NULL COMMENT '联系号码',
  `examine_state` varchar(20) DEFAULT '待审核' COMMENT '审核状态（待审核/已通过/已拒绝）',
  `user_state` varchar(20) DEFAULT '未启用' COMMENT '用户状态（未启用/已启用）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`store_admin_id`),
  UNIQUE KEY `uk_employee_work_number` (`employee_work_number`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='4S店管理员表';

SELECT 'store_admin表创建完成！' AS message;
