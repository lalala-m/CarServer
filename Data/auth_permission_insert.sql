-- =============================================
-- 权限配置SQL - 4S店管理员、信息员、业务顾问
-- 执行时间: 2026-04-10
-- =============================================

-- 0. 先删除可能存在的旧记录（确保干净）
DELETE FROM `auth` WHERE `table_name` IN ('store_admin', 'info_user', 'business_consultant');

-- 1. 插入4S店管理员权限（所有权限设置为1）
INSERT INTO `auth` (`user_group`, `mod_name`, `table_name`, `page_title`, `path`, `parent`, `parent_sort`, `position`, `mode`, `add`, `del`, `set`, `get`, `option`, `create_time`, `update_time`)
VALUES
('管理员', '系统用户', 'store_admin', '4S店管理员', '/store_admin/table', '系统用户', 8, 'left', 'iframe', 1, 1, 1, 1, '{"import_db":true,"examine":true}', NOW(), NOW());

-- 2. 插入信息员权限
INSERT INTO `auth` (`user_group`, `mod_name`, `table_name`, `page_title`, `path`, `parent`, `parent_sort`, `position`, `mode`, `add`, `del`, `set`, `get`, `option`, `create_time`, `update_time`)
VALUES
('管理员', '系统用户', 'info_user', '信息员', '/info_user/table', '系统用户', 8, 'left', 'iframe', 1, 1, 1, 1, '{"import_db":true}', NOW(), NOW());

-- 3. 插入业务顾问权限
INSERT INTO `auth` (`user_group`, `mod_name`, `table_name`, `page_title`, `path`, `parent`, `parent_sort`, `position`, `mode`, `add`, `del`, `set`, `get`, `option`, `create_time`, `update_time`)
VALUES
('管理员', '系统用户', 'business_consultant', '业务顾问', '/business_consultant/table', '系统用户', 8, 'left', 'iframe', 1, 1, 1, 1, '{"import_db":true}', NOW(), NOW());

-- 验证权限配置
SELECT '=== 权限配置完成 ===' AS message;
SELECT user_group, table_name, page_title, path, `add`, `del`, `set`, `get` FROM auth WHERE table_name IN ('store_admin', 'info_user', 'business_consultant');
