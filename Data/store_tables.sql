-- ============================================
-- 组织管理模块 - 店面与员工关联扩展
-- 执行时间：2026-04-05
-- 说明：新增 store 表，并为各角色表添加 store_id 关联字段
-- ============================================

-- 1. 创建 store 表（4S店/店面表）
DROP TABLE IF EXISTS `store`;

CREATE TABLE `store`(
  `store_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店面ID',
  `store_name` varchar(128) NOT NULL COMMENT '店面名称',
  `store_address` varchar(255) COMMENT '店面地址',
  `store_phone` varchar(16) COMMENT '联系电话',
  `store_manager` varchar(64) COMMENT '负责人',
  `examine_state` varchar(16) DEFAULT '已通过' COMMENT '审核状态：待审核/已通过/已拒绝',
  `user_id` int(11) DEFAULT '0' COMMENT '关联 user 表的 user_id',
  `create_by` int(11) DEFAULT '1' COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`store_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='4S店/店面';

-- 2. 为 info_user 表添加 store_id 字段
ALTER TABLE `info_user` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `contact_number`;

-- 3. 为 business_consultant 表添加 store_id 字段
ALTER TABLE `business_consultant` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `contact_number`;

-- 4. 为 owner_user 表添加 store_id 字段（会员关联店面）
ALTER TABLE `owner_user` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `member_level`;

-- 5. 为 financial_user 表添加 store_id 字段（财务审核关联店面）
ALTER TABLE `financial_user` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `contact_number`;

-- 6. 为 manager_user 表添加 store_id 字段（经理关联店面）
ALTER TABLE `manager_user` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `contact_number`;

-- 7. 为 sales_manager 表添加 store_id 字段（销售经理关联店面）
ALTER TABLE `sales_manager` 
ADD COLUMN `store_id` int(11) DEFAULT 0 COMMENT '关联店面ID' AFTER `contact_number`;

-- 8. 添加 store 表的用户组记录
INSERT INTO `user_group` (`display`, `name`, `description`, `source_table`, `source_field`, `source_id`, `register`)
VALUES (130, '4S店', '4S店/店面管理', 'store', 'store_id', 0, 0)
ON DUPLICATE KEY UPDATE `name` = '4S店';

-- ========== 验证语句 ==========
-- 执行以下语句验证表和字段是否创建成功：
-- SHOW TABLES LIKE '%store%';
-- DESCRIBE `store`;
-- SELECT * FROM `store`;
-- SHOW COLUMNS FROM `info_user` LIKE '%store_id%';
-- SHOW COLUMNS FROM `business_consultant` LIKE '%store_id%';
-- SHOW COLUMNS FROM `owner_user` LIKE '%store_id%';
-- SHOW COLUMNS FROM `financial_user` LIKE '%store_id%';
-- SELECT * FROM user_group WHERE `name` = '4S店';
