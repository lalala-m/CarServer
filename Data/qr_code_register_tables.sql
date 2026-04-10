-- ============================================
-- 组织管理模块 - 数据库建表脚本
-- 执行时间：2026-04-05
-- 说明：创建扫码注册功能所需的3张新表
-- ============================================

-- 1. 创建 qr_code_register 表（二维码追踪表）
DROP TABLE IF EXISTS `qr_code_register`;

CREATE TABLE `qr_code_register`(
  `qr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '二维码ID',
  `qr_token` varchar(64) NOT NULL UNIQUE COMMENT '二维码Token（唯一标识，用于扫码查询）',
  `role_type` varchar(32) NOT NULL COMMENT '角色类型：member-会员/info-信息员/consultant-业务顾问/financial-财务审核',
  `role_id` int(11) DEFAULT '0' COMMENT '关联角色记录ID（注册后填充）',
  `role_name` varchar(64) COMMENT '角色姓名（扫码后展示给用户）',
  `employee_work_number` varchar(64) COMMENT '员工工号（信息员/业务顾问/财务审核专用）',
  `department` varchar(64) COMMENT '所属部门（业务顾问专用）',
  `status` varchar(16) DEFAULT '未使用' COMMENT '状态：未使用/已使用/已失效',
  `bind_user_id` int(11) DEFAULT '0' COMMENT '绑定用户ID（注册成功后填充）',
  `expire_time` datetime COMMENT '过期时间（为空表示永不过期）',
  `create_by` int(11) DEFAULT '1' COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`qr_id`),
  INDEX `idx_qr_token` (`qr_token`),
  INDEX `idx_role_type` (`role_type`),
  INDEX `idx_status` (`status`),
  INDEX `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='二维码注册标识表';

-- 2. 创建 info_user 表（信息员表）
DROP TABLE IF EXISTS `info_user`;

CREATE TABLE `info_user`(
  `info_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '信息员ID',
  `employee_work_number` varchar(64) NOT NULL UNIQUE COMMENT '员工工号（唯一）',
  `employee_name` varchar(64) COMMENT '员工姓名',
  `employee_gender` varchar(16) COMMENT '员工性别',
  `contact_number` varchar(16) COMMENT '联系号码',
  `two_dimensional_code` varchar(255) COMMENT '个人二维码图片路径',
  `examine_state` varchar(16) DEFAULT '待审核' COMMENT '审核状态：待审核/已通过/已拒绝',
  `user_id` int(11) DEFAULT '0' COMMENT '关联 user 表的 user_id（注册后填充）',
  `create_by` int(11) DEFAULT '1' COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`info_user_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_employee_work_number` (`employee_work_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='信息员';

-- 3. 创建 business_consultant 表（业务顾问表）
DROP TABLE IF EXISTS `business_consultant`;

CREATE TABLE `business_consultant`(
  `consultant_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业务顾问ID',
  `employee_work_number` varchar(64) NOT NULL UNIQUE COMMENT '员工工号（唯一）',
  `employee_name` varchar(64) COMMENT '员工姓名',
  `consultant_gender` varchar(16) COMMENT '性别',
  `contact_number` varchar(16) COMMENT '联系号码',
  `department` varchar(64) COMMENT '所属部门',
  `two_dimensional_code` varchar(255) COMMENT '个人二维码图片路径',
  `examine_state` varchar(16) DEFAULT '待审核' COMMENT '审核状态：待审核/已通过/已拒绝',
  `user_id` int(11) DEFAULT '0' COMMENT '关联 user 表的 user_id（注册后填充）',
  `create_by` int(11) DEFAULT '1' COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`consultant_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_employee_work_number` (`employee_work_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='业务顾问';

-- 4. 添加用户组记录（user_group 表）
INSERT INTO `user_group` (`display`, `name`, `description`, `source_table`, `source_field`, `source_id`, `register`)
VALUES (110, '信息员', '信息员角色', 'info_user', 'info_user_id', 0, 0)
ON DUPLICATE KEY UPDATE `name` = '信息员';

INSERT INTO `user_group` (`display`, `name`, `description`, `source_table`, `source_field`, `source_id`, `register`)
VALUES (120, '业务顾问', '业务顾问角色', 'business_consultant', 'consultant_id', 0, 0)
ON DUPLICATE KEY UPDATE `name` = '业务顾问';

-- 5. 可选：修改现有表的审核状态默认值
-- 如果需要新注册用户默认"待审核"，执行以下语句：
-- ALTER TABLE `owner_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '待审核' NOT NULL COMMENT '审核状态';
-- ALTER TABLE `financial_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '待审核' NOT NULL COMMENT '审核状态';

-- ========== 验证语句 ==========
-- 执行以下语句验证表是否创建成功：
-- SHOW TABLES LIKE '%user%';
-- SHOW TABLES LIKE '%qr_code%';
-- SELECT * FROM user_group WHERE `name` IN ('信息员', '业务顾问');
