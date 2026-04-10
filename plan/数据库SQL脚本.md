# 组织管理模块 — 数据库 SQL 脚本

## 文档信息

| 项目 | 内容 |
|------|------|
| 版本 | V1.0 |
| 日期 | 2026-04-05 |
| 状态 | 设计中 |

---

## ⚠️ 执行前必读

1. **执行顺序**：请按下方序号顺序执行（1 → 2 → 3 → 4）
2. **备份数据**：执行前请备份现有数据库
3. **检查表是否存在**：如果表已存在，请先删除或修改表名
4. **编码格式**：确保执行时使用 UTF-8 编码

---

## 📋 SQL 脚本列表

| 序号 | 表名 | 操作类型 | 说明 |
|------|------|----------|------|
| 1 | `qr_code_register` | CREATE | 二维码追踪表 |
| 2 | `info_user` | CREATE | 信息员表 |
| 3 | `business_consultant` | CREATE | 业务顾问表 |
| 4 | `user_group` | INSERT | 添加用户组 |
| 5 | `owner_user` / `financial_user` | ALTER | 修改审核状态默认值（可选） |

---

## 1️⃣ 创建 qr_code_register 表（二维码追踪表）

```sql
-- ============================================
-- 脚本 1: 创建 qr_code_register 表
-- 用途：追踪二维码的 Token、状态、有效期
-- 关联：会员/信息员/业务顾问/财务审核的扫码注册
-- ============================================

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
```

**字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| `qr_id` | int | 主键，自增 |
| `qr_token` | varchar(64) | UUID Token，二维码的核心内容 |
| `role_type` | varchar(32) | 角色类型枚举 |
| `role_name` | varchar(64) | 扫码后展示的姓名 |
| `status` | varchar(16) | 确保二维码只能使用一次 |
| `expire_time` | datetime | 二维码过期时间 |

---

## 2️⃣ 创建 info_user 表（信息员表）

```sql
-- ============================================
-- 脚本 2: 创建 info_user 表
-- 用途：存储总部信息员的基本信息
-- 关联：user 表、qr_code_register 表
-- ============================================

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
```

**字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| `info_user_id` | int | 主键，自增 |
| `employee_work_number` | varchar(64) | 工号，唯一约束 |
| `employee_name` | varchar(64) | 姓名 |
| `examine_state` | varchar(16) | 新注册默认为"待审核" |
| `user_id` | int | 关联 user 表 |

---

## 3️⃣ 创建 business_consultant 表（业务顾问表）

```sql
-- ============================================
-- 脚本 3: 创建 business_consultant 表
-- 用途：存储4S店业务顾问的基本信息
-- 关联：user 表、qr_code_register 表
-- 注意：与 business_user（商家用户）是不同角色
-- ============================================

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
```

**字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| `consultant_id` | int | 主键，自增 |
| `employee_work_number` | varchar(64) | 工号，唯一约束 |
| `employee_name` | varchar(64) | 姓名 |
| `department` | varchar(64) | 部门（业务顾问特有） |
| `examine_state` | varchar(16) | 新注册默认为"待审核" |
| `user_id` | int | 关联 user 表 |

---

## 4️⃣ 添加用户组记录（user_group 表）

```sql
-- ============================================
-- 脚本 4: 更新 user_group 表
-- 用途：添加信息员、业务顾问的用户组记录
-- 关联：user 表的 user_group 字段
-- ============================================

-- 查看现有 user_group 表结构
-- SELECT * FROM user_group;

-- 添加信息员用户组
INSERT INTO `user_group` (`display`, `name`, `description`, `source_table`, `source_field`, `source_id`, `register`)
VALUES (110, '信息员', '信息员角色', 'info_user', 'info_user_id', 0, 0)
ON DUPLICATE KEY UPDATE `name` = '信息员';

-- 添加业务顾问用户组
INSERT INTO `user_group` (`display`, `name`, `description`, `source_table`, `source_field`, `source_id`, `register`)
VALUES (120, '业务顾问', '业务顾问角色', 'business_consultant', 'consultant_id', 0, 0)
ON DUPLICATE KEY UPDATE `name` = '业务顾问';

-- 验证插入结果
-- SELECT * FROM user_group WHERE `name` IN ('信息员', '业务顾问');
```

**user_group 字段说明：**

| 字段 | 说明 |
|------|------|
| `display` | 显示顺序 |
| `name` | 用户组名称 |
| `source_table` | 关联的扩展表 |
| `source_field` | 关联的扩展表主键字段 |
| `register` | 注册位置（0=关闭注册） |

---

## 5️⃣ 修改审核状态默认值（可选）

```sql
-- ============================================
-- 脚本 5: 修改审核状态默认值（可选执行）
-- 用途：统一新注册用户的审核状态为"待审核"
-- 注意：如有需要再执行，否则保持原有配置
-- ============================================

-- 修改 owner_user 表的 examine_state 默认值
ALTER TABLE `owner_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '待审核' NOT NULL COMMENT '审核状态';

-- 修改 financial_user 表的 examine_state 默认值
ALTER TABLE `financial_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '待审核' NOT NULL COMMENT '审核状态';

-- 验证修改结果
-- DESCRIBE owner_user;
-- DESCRIBE financial_user;
```

---

## 📊 表关系图

```
┌─────────────────────────────────────────────────────────────────┐
│                        数据库表关系                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│    ┌──────────────┐                                              │
│    │    user      │  ← 用户主表（已有）                           │
│    │──────────────│                                              │
│    │ user_id (PK) │                                              │
│    │ user_group   │ ──┐                                          │
│    │ username     │   │ 关联                                      │
│    └──────────────┘   │                                          │
│              ▲         │                                          │
│              │         ▼                                          │
│    ┌─────────┴─────────────┐                                    │
│    │                         │                                    │
│ ┌──┴────────┐  ┌───────────┴───────┐                            │
│ │owner_user │  │financial_user     │                            │
│ │───────────│  │───────────────────│                            │
│ │会员 ✅已有│  │财务审核 ✅已有    │                            │
│ └───────────┘  └───────────────────┘                            │
│                                                                 │
│ ┌───────────┐  ┌────────────────────┐  ┌───────────────────┐   │
│ │info_user  │  │business_consultant│  │qr_code_register   │   │
│ │───────────│  │───────────────────│  │───────────────────│   │
│ │信息员     │  │业务顾问            │  │二维码追踪表       │   │
│ │❌新建     │  │❌新建              │  │❌新建             │   │
│ └───────────┘  └────────────────────┘  └───────────────────┘   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔍 验证 SQL

执行完所有脚本后，运行以下 SQL 验证：

```sql
-- 验证表是否创建成功
SHOW TABLES LIKE '%user%';
SHOW TABLES LIKE '%qr_code%';

-- 验证表结构
DESCRIBE qr_code_register;
DESCRIBE info_user;
DESCRIBE business_consultant;

-- 验证 user_group 记录
SELECT * FROM user_group WHERE `name` IN ('信息员', '业务顾问');
```

---

## 📝 回滚脚本

如需回滚，执行以下 SQL：

```sql
-- 删除新建的表（注意：会丢失数据）
DROP TABLE IF EXISTS `qr_code_register`;
DROP TABLE IF EXISTS `info_user`;
DROP TABLE IF EXISTS `business_consultant`;

-- 删除 user_group 记录
DELETE FROM `user_group` WHERE `name` IN ('信息员', '业务顾问');

-- 恢复审核状态默认值（如果执行过脚本5）
ALTER TABLE `owner_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '已通过' NOT NULL COMMENT '审核状态';
ALTER TABLE `financial_user` MODIFY COLUMN `examine_state` varchar(16) DEFAULT '已通过' NOT NULL COMMENT '审核状态';
```
