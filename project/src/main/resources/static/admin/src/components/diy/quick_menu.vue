<template>
<el-col v-if="auth.length > 0" :span="24">
  <div class="card">
    <div class="card QuickMenu">
      <el-row>
        <el-col :span="2" v-for="i in auth" :key="i.auth_id" v-show="$check_action(i.path)">
          <div @click="moreBtn(i.path)">
            <el-button :type="i.Bgcolor" :icon="i.Icon" size="large" class='MenuIcon'/>
            <p class='Menutext'>{{ i.mod_name + "管理" }}</p>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</el-col>
</template>
<script>

  export default {
    computed: {
      auth() {
        // 保留原计算属性逻辑
        let tables = this.$store.state.web.auth;
        let lt = [];
        let site_list = [];
        let colorArr = ["success", "primary", "info", "warning", "danger"];
        let IconArr = ["el-icon-edit", "el-icon-document", "el-icon-menu", "el-icon-location", "el-icon-setting", "el-icon-search"];
                                                                                                            site_list.push("test_drive_record")
                        site_list.push("purchase_order")
                                  site_list.push("maintenance_order")
                                            site_list.push("write_off_information")
                                                                site_list.push("reservation_record")
                                                      site_list.push("inbound_record")
                        site_list.push("outbound_record")
                                          let list = [
          "exam",
          "article",
          "article_type",
          "comment",
                "goods",  // 移除空元素修复语法错误
          "order",
                  "logistics_delivery",
                              "forum",
          "forum_type",
                      "notice",
                                ];
                                                                                                                                                                                                                                                                                                                                                                                                                                                            list.push("online_mall");
                                                      list.push("owner_user");
                        list.push("business_user");
                        list.push("financial_user");
                        list.push("manager_user");
                        list.push("sales_manager");
                                                                                                                                                                                                                                                        
        for (let i = 0; i < tables.length; i++) {
          let o = tables[i];
          if (o.path && o.path.indexOf("/table") !== -1) {  // 添加path存在性检查
            if (list.indexOf(o.table_name) === -1) {
              o.has_add = site_list.indexOf(o.table_name) === -1;
              o.Bgcolor = colorArr[parseInt(Math.random() * colorArr.length)];
              o.Icon = IconArr[parseInt(Math.random() * IconArr.length)];
              lt.push(o);
            }
          }
        }
        return lt;
      }
    },
    methods: {
      moreBtn(path) {
        this.$router.push(path);  
      }
    },
    mounted() {

    }
  };
</script>

<style scoped>
  .QuickMenu {
    text-align: center;
    display: block;
    width: 100%;
    position: relative;
    padding: 10px 0;
  }

  .QuickMenu .MenuIcon {
    width: 80px;
    height: 80px;
    font-size: 30px;
    padding: 1.5rem;
  }

  .QuickMenu .Menutext {
    font-size: 18px;
    padding-top: 8px;
  }
</style>