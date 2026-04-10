-- 为 owner_user 表添加 examine_state 字段
ALTER TABLE `owner_user`
ADD COLUMN `examine_state` varchar(16) DEFAULT '待审核' COMMENT '审核状态' AFTER `accumulate_points`;

-- 为 financial_user 表添加 examine_state 字段
ALTER TABLE `financial_user`
ADD COLUMN `examine_state` varchar(16) DEFAULT '待审核' COMMENT '审核状态' AFTER `two_dimensional_code`;
