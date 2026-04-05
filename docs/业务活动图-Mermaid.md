# 车友服务系统 — 业务活动图（Mermaid）

在 Cursor / VS Code 中打开本文件，使用 **Markdown 预览**（建议安装下方插件）即可渲染图中的流程。

---

## 1. 4S店基本信息管理

```mermaid
flowchart TB
  subgraph HQ["总部信息员"]
    A1["审核4S车型信息（更新车型库）"]
    A2["审核4S服务类别（更新服务类别库）"]
    A3["审核4S服务时段信息"]
  end

  subgraph S4["4S信息管理员"]
    B1["车型库中选择4S车型信息"]
    B2["增加4S车型信息（excel表）"]
    B3["服务类别库中选择"]
    B4["增加服务类别（excel表）"]
    B5["增修服务时段信息（含服务类别及工位数）"]
  end

  B1 -->|在库| A1
  B1 -->|库中无| B2
  B2 --> A1
  A1 --> B3
  B3 -->|在库| A2
  B3 -->|库中无| B4
  B4 --> A2
  A2 --> B5
  B5 --> A3
```

---

## 2. 会员及车辆管理

```mermaid
flowchart TB
  subgraph M["会员"]
    M1["扫描业务顾问二维码注册启用会员（短信验证码自行审核）"]
    M2["修改个人信息（短信验证码自行审核）"]
    M3["修改车牌信息（短信验证）"]
  end

  subgraph C["4S业务顾问"]
    C0["启用个人组织管理二维码"]
    C1["增加会员车辆信息（扫描识别业务明细单+手工输入）"]
  end

  subgraph F["4S财务审核"]
    F1["车辆信息审核（销售单据或照片）"]
  end

  C0 --> M1
  M1 --> M2
  M2 --> M3
  M3 --> F1
  C1 --> F1
```

**注：** 修改个人信息步骤旁的业务规则为——手机更换须新旧手机号分别验证。

Mermaid 对「侧边注释」支持有限，上式用虚线示意；正式文档可在图旁保留文字说明。

---

## 3. 组织结构设计思路（信息审核 / 业务审核）

```mermaid
flowchart TB
  subgraph T1["集团总部"]
    ADM["总部 系统管理员"]
    INF1["总部 信息员"]
    INFDOT["..."]
    INF3["总部 信息员"]
    ADM --> INF1
    ADM --> INFDOT
    ADM --> INF3
  end

  subgraph T2["4S店 1"]
    IA1["4S店 信息管理员"]
    subgraph INNER1["店内流程"]
      FA1["4S店 财务审核员"]
      FA1b["..."]
      BC1["4S店 业务顾问"]
      BC1b["..."]
      FA1 --> BC1
      FA1b --> BC1b
    end
    MEM1[会员]
    VEH1[车辆]
    BC1 --> MEM1
    MEM1 --> VEH1
  end

  subgraph T3["4S店 N"]
    IA2["4S店 信息管理员"]
    subgraph INNER2["店内流程（同店1）"]
      FA2["4S店 财务审核员"]
      BC2["4S店 业务顾问"]
      FA2 --> BC2
    end
    MEM2[会员]
    VEH2[车辆]
    BC2 --> MEM2
    MEM2 --> VEH2
  end

  ADM -.->|纵向管控各店信息管理员| IA1
  ADM -.->|纵向管控各店信息管理员| IA2
  INF1 -.->|共享信息审核| INNER1
  INF3 -.->|共享信息审核| INNER2
```

说明：**信息审核** 为 4S—总部共享审核；**业务审核** 在 4S 店内（财务审核员 → 业务顾问）。虚线框表示会员、车辆为外部实体或数据对象。

---

## 4. 卡券管理

```mermaid
flowchart LR
  subgraph L1["总部信息员"]
    N1["增修卡券类型：服务类别+次卡/月/年卡；期限：开始、结束、有效时长"]
  end
  subgraph L2["总部系统管理员"]
    N2["审核卡券类型"]
  end
  subgraph L3["4S信息管理员"]
    N3["卡券发放（车辆）支持excel批量"]
    N4["卡券发放（客户）支持excel批量"]
  end
  subgraph L4["4S财务审核"]
    N5["卡券发放审核"]
    N6["卡券核销审核"]
  end
  subgraph L5["4S业务顾问"]
    N7["发起卡券核销申请（服务清单），自动建立业务顾问与客户关联"]
    NOTE["支持按车辆查找客户"]
  end

  N1 --> N2
  N3 --> N5
  N4 --> N5
  N4 -.-> NOTE
  N7 --> N6
```

---

## 5. 组织管理（多路径汇总）

```mermaid
flowchart TB
  subgraph HQIO["总部信息员"]
    H1["扫描系统管理员二维码注册信息员"]
    H2["修改个人信息"]
    H3["修改4S店信息"]
  end

  subgraph HQSYS["总部系统管理员"]
    S1["增加4S店信息管理员（excel批量）"]
    S2["个人信息审核，启用4S信息管理员"]
    S3["启用个人组织管理二维码"]
    S4["增加总部信息员（excel批量）"]
    S5["个人信息审核，启用信息员"]
    S6["4S店信息审核"]
  end

  subgraph S4IA["4S信息管理员"]
    A1["修改个人信息"]
    A2["启用个人组织管理二维码"]
    A3["增加4S财务审核（excel批量）"]
    A4["增加4S业务顾问（excel批量）"]
    A5["个人信息审核，启用4S财务审核"]
    A6["个人信息审核，启用4S业务顾问"]
  end

  subgraph S4FIN["4S财务审核"]
    F1["扫描4S信息管理员二维码注册4S财务审核"]
    F2["修改个人信息"]
  end

  subgraph S4BC["4S业务顾问"]
    B1["扫描4S信息管理员二维码注册4S业务顾问"]
    B2["修改个人信息"]
  end

  S3 --> H1
  S4 --> H2
  H1 --> H2
  H2 --> S5
  S5 --> H3
  H3 --> S6

  S1 --> A1
  A1 --> S2
  S2 --> A2
  S2 --> A3
  S2 --> A4
  A2 --> F1
  A2 --> B1
  F1 --> F2
  B1 --> B2
  F2 --> A5
  B2 --> A6
```

---

## 推荐插件（Cursor / VS Code）

| 用途 | 扩展名称（在扩展市场搜索） | 说明 |
|------|---------------------------|------|
| 预览本文件中的 Mermaid | **Markdown Preview Mermaid Support** | 在 Markdown 预览里直接渲染 `mermaid` 代码块 |
| 仅预览 Mermaid | **Mermaid Chart** 或 **Mermaid Preview** | 可单独打开 `.mmd` 文件预览 |
| 可选：PlantUML | **PlantUML** | 需本机 Java；复杂泳道图用 `.puml` 更贴近 UML（见同目录 `diagrams/*.puml`） |

预览快捷键：`Ctrl+Shift+V`（打开预览）；分栏预览：`Ctrl+K` 再按 `V`。
