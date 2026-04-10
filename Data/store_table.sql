-- 4S店店面表建表脚本
-- 如果表已存在请先删除

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店面ID',
  `store_name` varchar(100) NOT NULL COMMENT '店面名称',
  `store_address` varchar(255) DEFAULT '' COMMENT '店面地址',
  `store_phone` varchar(20) DEFAULT '' COMMENT '联系电话',
  `store_manager` varchar(50) DEFAULT '' COMMENT '负责人',
  `examine_state` varchar(20) DEFAULT '已通过' COMMENT '审核状态：待审核/已通过/已拒绝',
  `user_id` int(11) DEFAULT NULL COMMENT '关联user表ID',
  `create_by` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`store_id`),
  UNIQUE KEY `uk_store_name` (`store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='4S店/店面表';

-- 插入初始数据
INSERT INTO `store` (`store_name`, `store_address`, `store_phone`, `store_manager`, `examine_state`) VALUES
('总部', '总公司地址', '400-800-1000', '管理员', '已通过');
