-- 插入管理员用户
-- 注意：密码是 asd123，但实际需要根据前端加密方式确定

-- 首先检查管理员用户组是否存在
SELECT * FROM user_group WHERE name = '管理员';

-- 插入管理员用户（密码是 asd123）
INSERT INTO `user` (`state`, `user_group`, `username`, `nickname`, `password`, `avatar`) 
VALUES (1, '管理员', 'admin', '管理员', 'asd123', '/api/upload/admin_avatar.jpg');

-- 如果需要查看所有用户
SELECT user_id, username, user_group, state FROM user;
