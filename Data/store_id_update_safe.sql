-- =============================================
-- 4S店信息管理员相关表结构更新（安全版）
-- 使用 IF NOT EXISTS 避免重复添加错误
-- 执行时间: 2026-04-09
-- =============================================

-- 1. 为 business_consultant 表添加 store_id 字段（如果不存在）
SET @column_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'business_consultant' 
    AND COLUMN_NAME = 'store_id'
);
SET @sql = IF(@column_exists = 0, 
    'ALTER TABLE business_consultant ADD COLUMN store_id INT DEFAULT 0 COMMENT ''所属4S店ID（0表示总部）'' AFTER consultant_id',
    'SELECT ''store_id字段已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. 为 info_user 表添加 store_id 字段（如果不存在）
SET @column_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'info_user' 
    AND COLUMN_NAME = 'store_id'
);
SET @sql = IF(@column_exists = 0, 
    'ALTER TABLE info_user ADD COLUMN store_id INT DEFAULT 0 COMMENT ''所属4S店ID（0表示总部）'' AFTER info_user_id',
    'SELECT ''store_id字段已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3. 为 financial_user 表添加 store_id 字段（如果不存在）
SET @column_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'financial_user' 
    AND COLUMN_NAME = 'store_id'
);
SET @sql = IF(@column_exists = 0, 
    'ALTER TABLE financial_user ADD COLUMN store_id INT DEFAULT 0 COMMENT ''所属4S店ID（0表示总部）'' AFTER financial_user_id',
    'SELECT ''store_id字段已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 4. 为 owner_user 表添加 store_id 字段（如果不存在）
SET @column_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'owner_user' 
    AND COLUMN_NAME = 'store_id'
);
SET @sql = IF(@column_exists = 0, 
    'ALTER TABLE owner_user ADD COLUMN store_id INT DEFAULT 0 COMMENT ''所属4S店ID（0表示总部）'' AFTER owner_user_id',
    'SELECT ''store_id字段已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 5. 创建4S店管理员用户组（如果不存在）
INSERT IGNORE INTO user_group (display, name, description, source_table, source_field, register, create_time, update_time)
VALUES (110, '4S店管理员', '4S店信息管理员', 'business_consultant', 'consultant_id', 0, NOW(), NOW());

-- 6. 为 business_consultant 表添加索引（如果不存在）
SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'business_consultant' 
    AND INDEX_NAME = 'idx_store_id'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE business_consultant ADD INDEX idx_store_id (store_id)',
    'SELECT ''idx_store_id索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 7. 为 info_user 表添加索引（如果不存在）
SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'info_user' 
    AND INDEX_NAME = 'idx_store_id'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE info_user ADD INDEX idx_store_id (store_id)',
    'SELECT ''idx_store_id索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 8. 为 financial_user 表添加索引（如果不存在）
SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'financial_user' 
    AND INDEX_NAME = 'idx_store_id'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE financial_user ADD INDEX idx_store_id (store_id)',
    'SELECT ''idx_store_id索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 9. 为 owner_user 表添加索引（如果不存在）
SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'owner_user' 
    AND INDEX_NAME = 'idx_store_id'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE owner_user ADD INDEX idx_store_id (store_id)',
    'SELECT ''idx_store_id索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 10. 为各表添加审核状态索引（如果不存在）
SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'business_consultant' 
    AND INDEX_NAME = 'idx_examine_state'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE business_consultant ADD INDEX idx_examine_state (examine_state)',
    'SELECT ''idx_examine_state索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'info_user' 
    AND INDEX_NAME = 'idx_examine_state'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE info_user ADD INDEX idx_examine_state (examine_state)',
    'SELECT ''idx_examine_state索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'financial_user' 
    AND INDEX_NAME = 'idx_examine_state'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE financial_user ADD INDEX idx_examine_state (examine_state)',
    'SELECT ''idx_examine_state索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @index_exists = (
    SELECT COUNT(*) 
    FROM INFORMATION_SCHEMA.STATISTICS 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'owner_user' 
    AND INDEX_NAME = 'idx_examine_state'
);
SET @sql = IF(@index_exists = 0, 
    'ALTER TABLE owner_user ADD INDEX idx_examine_state (examine_state)',
    'SELECT ''idx_examine_state索引已存在'' AS message');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT '数据库更新完成！' AS message;
