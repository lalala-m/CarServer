-- =============================================
-- 4S店管理权限配置SQL
-- 执行时间: 2026-04-10
-- =============================================

-- 0. 先删除可能存在的旧记录
DELETE FROM `auth` WHERE `table_name` = 'store';

-- 1. 插入4S店管理权限
INSERT INTO `auth` (`user_group`, `mod_name`, `table_name`, `page_title`, `path`, `parent`, `parent_sort`, `position`, `mode`, `add`, `del`, `set`, `get`, `option`, `create_time`, `update_time`)
VALUES
('管理员', '系统用户', 'store', '4S店管理', '/store/table', '系统用户', 8, 'left', 'iframe', 1, 1, 1, 1, NULL, NOW(), NOW());

-- 验证权限配置
SELECT '=== 4S店管理权限配置完成 ===' AS message;
SELECT user_group, table_name, page_title, path, `add`, `del`, `set`, `get` FROM auth WHERE table_name = 'store';
