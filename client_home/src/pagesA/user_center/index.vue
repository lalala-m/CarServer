<template>
  <view id="page_statistics" class="page_user_center" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>统计图</tn-nav-bar>
    <view class="warp">
      <view class="container">
        <view class="row justify-content-between">
          <view class="col-12 col-md-3">
            <view class="card_menu">
              <!-- 左侧边栏 -->
            </view>
          </view>
          <view class="col-12 col-md-9">
            <view class="card_addres">
              <view class="warp">
                <view class="container-fluid">
                  <view class="d-row-line">
                    <view class="title">数据统计中心</view>
                  </view>
                  <view class="d-row" style="height: 300px">
                    <view class="d-col" v-if="user_group == '管理员' || $check_figure('/order/table')" :span="8">
                      <view class="card chart">
                        <h5 class="title">销售金额统计图</h5>
                        <uni-datetime-picker
                            v-model="line_obj_shop_money.month"
                            type="daterange"
                            @change="changeMonth($event, line_obj_shop_money)"
                            :disabled="line_obj_shop_money.loading"
                        />
                        <view style="margin-top: 10px">
                          销售金额总数：{{ line_obj_shop_money.total }}
                        </view>
                        <newLineChart
                            v-if="line_obj_shop_money.values.length > 0"
                            id="line_shop_money_chart"
                            :vm="line_obj_shop_money"
                            :title="'销售金额统计'"
                        >
                        </newLineChart>
                        <view v-else style="text-align: center; margin-top: 20px">
                          正在加载数据...请稍后
                        </view>
                      </view>
                    </view>
                    <view class="d-col" v-if="user_group == '管理员' || $check_figure('/order/table')" :span="8">
                      <view class="card chart">
                        <h5 class="title">销售数量统计图</h5>
                        <uni-datetime-picker
                            v-model="bar_obj_shop_num.month"
                            type="daterange"
                            @change="changeMonth($event, bar_obj_shop_num)"
                            :disabled="bar_obj_shop_num.loading"
                        />
                        <view style="margin-top: 10px">
                          销售数量总数：{{ bar_obj_shop_num.total }}
                        </view>
                        <newBarChart
                            v-if="bar_obj_shop_num.values.length > 0"
                            id="bar_obj_bar_chart"
                            :vm="bar_obj_shop_num"
                            :title="'销售数量统计'"
                        >
                        </newBarChart>
                        <view v-else style="text-align: center; margin-top: 20px">
                          正在加载数据...请稍后
                        </view>
                      </view>
                    </view>
                    <view class="d-col" v-if="user_group == '管理员' || $check_figure('/order/table')" :span="8">
                      <view class="card chart">
                        <h5 class="title">销售分类统计图</h5>
                        <uni-datetime-picker
                            v-model="circle_obj_shop_type.month"
                            type="daterange"
                            @change="changeMonthForType($event, circle_obj_shop_type)"
                            :disabled="circle_obj_shop_type.loading"
                        />
                        <pieChart
                            v-if="circle_obj_shop_type.values.length > 0"
                            id="circle_obj_shop_type"
                            :title="'商品销售分类统计'"
                            :list="circle_obj_shop_type.values"
                            :type="'商品类型'"
                            style="margin-top: 20px;height: 300px"
                        >
                        </pieChart>
                        <view v-else style="text-align: center; margin-top: 20px">
                          正在加载数据...请稍后
                        </view>
                      </view>
                    </view>
                      <view class="d-col" v-if="$check_figure('/car_information/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">汽车车型统计</h5>
                        <pieChart v-if="list_car_information_1.length" id="list_car_information_1" :list="list_car_information_1" :title="'汽车车型统计'"></pieChart>
                        <view v-if="!list_car_information_1.length">汽车车型统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/purchase_order/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">购买订单统计</h5>
                        <barChat v-if="bar_obj_purchase_order_2.values.length > 0" id="bar_obj_purchase_order_2" :vm="bar_obj_purchase_order_2" :title="'购买订单统计'">
                        </barChat>
                        <view v-if="!bar_obj_purchase_order_2.values.length">购买订单统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/maintenance_order/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">保养订单统计</h5>
                        <barChat v-if="bar_obj_maintenance_order_3.values.length > 0" id="bar_obj_maintenance_order_3" :vm="bar_obj_maintenance_order_3" :title="'保养订单统计'">
                        </barChat>
                        <view v-if="!bar_obj_maintenance_order_3.values.length">保养订单统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/car_information/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">汽车新旧统计</h5>
                        <pieChart v-if="list_car_information_4.length" id="list_car_information_4" :list="list_car_information_4" :title="'汽车新旧统计'"></pieChart>
                        <view v-if="!list_car_information_4.length">汽车新旧统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/employee_performance/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">员工绩效统计</h5>
                        <barChat v-if="bar_obj_employee_performance_5.values.length > 0" id="bar_obj_employee_performance_5" :vm="bar_obj_employee_performance_5" :title="'员工绩效统计'">
                        </barChat>
                        <view v-if="!bar_obj_employee_performance_5.values.length">员工绩效统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/purchase_order/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">汽车品牌销售统计</h5>
                        <barChat v-if="bar_obj_purchase_order_6.values.length > 0" id="bar_obj_purchase_order_6" :vm="bar_obj_purchase_order_6" :title="'汽车品牌销售统计'">
                        </barChat>
                        <view v-if="!bar_obj_purchase_order_6.values.length">汽车品牌销售统计没有符合条件的数据</view>
                          </view>
                    </view>
                        <view class="d-col" v-if="$check_figure('/reservation_record/table')" :span="8">
                      <view class="card chart">
                            <h5 class="title">预约记录统计</h5>
                        <barChat v-if="bar_obj_reservation_record_7.values.length > 0" id="bar_obj_reservation_record_7" :vm="bar_obj_reservation_record_7" :title="'预约记录统计'">
                        </barChat>
                        <view v-if="!bar_obj_reservation_record_7.values.length">预约记录统计没有符合条件的数据</view>
                          </view>
                    </view>
                    </view>
                </view>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  import mixin from '@/libs/mixins/page.js';
  import newBarChart from "./charts/new_bar_chart";
  import newLineChart from "./charts/new_line_chart";
    import barChat from "./charts/barChat";
    import pieChart from "./charts/pieChat";



  export default {
    mixins: [mixin],
    data() {
      return {
                list_car_information_1: [],
                      bar_obj_purchase_order_2: {
            names:[],
            xAxis: [],
            values:[]
          },
                      bar_obj_maintenance_order_3: {
            names:[],
            xAxis: [],
            values:[]
          },
                      list_car_information_4: [],
                      bar_obj_employee_performance_5: {
            names:[],
            xAxis: [],
            values:[]
          },
                      bar_obj_purchase_order_6: {
            names:[],
            xAxis: [],
            values:[]
          },
                      bar_obj_reservation_record_7: {
            names:[],
            xAxis: [],
            values:[]
          },
              line_obj_shop_money: {
          type: 'money',
          names: ['销售金额'],
          xAxis: [],
          values: [],
          month: [],
          loading: false,
          total: 0,
        },
        bar_obj_shop_num: {
          type: 'num',
          names: ['销售数量'],
          xAxis: [],
          values: [],
          month: [],
          loading: false,
          total: 0,
        },
        circle_obj_shop_type: {
          type: "category",
          names: [],
          values: [],
          month: [],
          loading: false,
          total: 0,
        },
      };
    },
    mounted() {
    },
    methods: {

      async get_nickname(list, flag) {
        if (flag) {
          for (let i = 0; i < list.length; i++) {
            await this.$get('~/api/user/get_obj?user_id=' + list[i], null, (json) => {
              if (json.result) {
                list[i] = json.result.obj.nickname;
              }
            });
          }
        } else {
          for (let i = 0; i < list.length; i++) {
            await this.$get('~/api/user/get_obj?user_id=' + list[i].name, null, (json) => {
              if (json.result) {
                list[i].name = json.result.obj.nickname;
              }
            });
          }
        }
      },
        // 获取汽车车型统计统计图数据
      get_list_car_information_1() {
        let data = {};
                let flag = false;
                        let user_group = this.userGroup;
          let user_id = this.userInfo.user_id;
          if (user_group!='管理员'){
                      let sqlwhere = "(";
                                                                                                                                                                                                                                                                                        if (user_group=="商家用户"){
            sqlwhere+= "business_user = " + user_id + " or ";
          }
                                                                                                                                  if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
                          }
                    this.$get("~/api/car_information/list_group?groupby=car_models", data, (json) => {
            if (json.result) {
              var list = json.result.list;
              this.list_car_information_1 = list.map((o) => {
                return {
                            name: o[1],
                            value: o[0]
                };
              });
              if (flag){
                this.get_nickname(this.list_car_information_1,false);
              }
            }
          });
        },
        // 获取购买订单统计统计柱状图
      async get_list_purchase_order_2() {
        let name_list = [];
        let query_str = "";

                let flag = false;
                        let date_flag = "其他"
        
            name_list.push("购买次数");
        query_str = query_str+"number_of_purchases"+","
            name_list.push("购买数量");
        query_str = query_str+"purchase_quantity"+","
            name_list.push("合计金额");
        query_str = query_str+"total_amount"+","
            this.bar_obj_purchase_order_2.names = name_list
        query_str = query_str.substr(0,query_str.length-1);
        let data = {};
            let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        if (user_group!='管理员'){
                let sqlwhere = "(";
                                                                                                                                                          if (user_group=="商家用户"){
            sqlwhere+= "business_user = " + user_id + " or ";
          }
                                      if (user_group=="车主用户"){
            sqlwhere+= "owner_user = " + user_id + " or ";
          }
                                                                                                                      if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
              }
            await this.$get( "~/api/purchase_order/bar_group?field="+query_str+"&groupby="+"car_name",data,(json) => {
          if (json.result) {
            let xAxis = [];
            let values = [];
            json.result.list.map((o) => {
              if (date_flag === "日期") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
              }else if (date_flag === "时间") {
                xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
              }else if (date_flag === "日长") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
              }else {
                xAxis.push(o[0]);
              }
              values.push(o.splice(1))
            });
            this.bar_obj_purchase_order_2.xAxis = xAxis;
            this.bar_obj_purchase_order_2.values = values;
          }
          if (flag){
            this.get_nickname(this.bar_obj_purchase_order_2.xAxis,true);
          }
        });
      },
        // 获取保养订单统计统计柱状图
      async get_list_maintenance_order_3() {
        let name_list = [];
        let query_str = "";

                let flag = false;
                        let date_flag = "其他"
        
            name_list.push("购买次数");
        query_str = query_str+"number_of_purchases"+","
            name_list.push("项目价格");
        query_str = query_str+"project_price"+","
            this.bar_obj_maintenance_order_3.names = name_list
        query_str = query_str.substr(0,query_str.length-1);
        let data = {};
            let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        if (user_group!='管理员'){
                let sqlwhere = "(";
                                                                if (user_group=="经理用户"){
            sqlwhere+= "manager_user = " + user_id + " or ";
          }
                                      if (user_group=="车主用户"){
            sqlwhere+= "owner_user = " + user_id + " or ";
          }
                                      if (user_group=="财务用户"){
            sqlwhere+= "financial_user = " + user_id + " or ";
          }
                                                                                                    if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
              }
            await this.$get( "~/api/maintenance_order/bar_group?field="+query_str+"&groupby="+"project_bundle",data,(json) => {
          if (json.result) {
            let xAxis = [];
            let values = [];
            json.result.list.map((o) => {
              if (date_flag === "日期") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
              }else if (date_flag === "时间") {
                xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
              }else if (date_flag === "日长") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
              }else {
                xAxis.push(o[0]);
              }
              values.push(o.splice(1))
            });
            this.bar_obj_maintenance_order_3.xAxis = xAxis;
            this.bar_obj_maintenance_order_3.values = values;
          }
          if (flag){
            this.get_nickname(this.bar_obj_maintenance_order_3.xAxis,true);
          }
        });
      },
          // 获取汽车新旧统计统计图数据
      get_list_car_information_4() {
        let data = {};
                let flag = false;
                        let user_group = this.userGroup;
          let user_id = this.userInfo.user_id;
          if (user_group!='管理员'){
                      let sqlwhere = "(";
                                                                                                                                                                                                                                                                                        if (user_group=="商家用户"){
            sqlwhere+= "business_user = " + user_id + " or ";
          }
                                                                                                                                  if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
                          }
                    this.$get("~/api/car_information/list_group?groupby=new_and_old_car", data, (json) => {
            if (json.result) {
              var list = json.result.list;
              this.list_car_information_4 = list.map((o) => {
                return {
                            name: o[1],
                            value: o[0]
                };
              });
              if (flag){
                this.get_nickname(this.list_car_information_4,false);
              }
            }
          });
        },
        // 获取员工绩效统计统计柱状图
      async get_list_employee_performance_5() {
        let name_list = [];
        let query_str = "";

                let flag = false;
                        let date_flag = "其他"
        
            name_list.push("服务单数");
        query_str = query_str+"service_singular"+","
            this.bar_obj_employee_performance_5.names = name_list
        query_str = query_str.substr(0,query_str.length-1);
        let data = {};
            let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        if (user_group!='管理员'){
                let sqlwhere = "(";
                                                                                                                                                                                    if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
              }
            await this.$get( "~/api/employee_performance/bar_group?field="+query_str+"&groupby="+"employee_name",data,(json) => {
          if (json.result) {
            let xAxis = [];
            let values = [];
            json.result.list.map((o) => {
              if (date_flag === "日期") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
              }else if (date_flag === "时间") {
                xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
              }else if (date_flag === "日长") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
              }else {
                xAxis.push(o[0]);
              }
              values.push(o.splice(1))
            });
            this.bar_obj_employee_performance_5.xAxis = xAxis;
            this.bar_obj_employee_performance_5.values = values;
          }
          if (flag){
            this.get_nickname(this.bar_obj_employee_performance_5.xAxis,true);
          }
        });
      },
        // 获取汽车品牌销售统计统计柱状图
      async get_list_purchase_order_6() {
        let name_list = [];
        let query_str = "";

                let flag = false;
                        let date_flag = "其他"
        
            name_list.push("购买次数");
        query_str = query_str+"number_of_purchases"+","
            this.bar_obj_purchase_order_6.names = name_list
        query_str = query_str.substr(0,query_str.length-1);
        let data = {};
            let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        if (user_group!='管理员'){
                let sqlwhere = "(";
                                                                                                                                                          if (user_group=="商家用户"){
            sqlwhere+= "business_user = " + user_id + " or ";
          }
                                      if (user_group=="车主用户"){
            sqlwhere+= "owner_user = " + user_id + " or ";
          }
                                                                                                                      if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
              }
            await this.$get( "~/api/purchase_order/bar_group?field="+query_str+"&groupby="+"car_brand",data,(json) => {
          if (json.result) {
            let xAxis = [];
            let values = [];
            json.result.list.map((o) => {
              if (date_flag === "日期") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
              }else if (date_flag === "时间") {
                xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
              }else if (date_flag === "日长") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
              }else {
                xAxis.push(o[0]);
              }
              values.push(o.splice(1))
            });
            this.bar_obj_purchase_order_6.xAxis = xAxis;
            this.bar_obj_purchase_order_6.values = values;
          }
          if (flag){
            this.get_nickname(this.bar_obj_purchase_order_6.xAxis,true);
          }
        });
      },
        // 获取预约记录统计统计柱状图
      async get_list_reservation_record_7() {
        let name_list = [];
        let query_str = "";

                let flag = false;
                        let date_flag = "其他"
        
            name_list.push("预约次数");
        query_str = query_str+"number_of_appointments"+","
            this.bar_obj_reservation_record_7.names = name_list
        query_str = query_str.substr(0,query_str.length-1);
        let data = {};
            let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        if (user_group!='管理员'){
                let sqlwhere = "(";
                                                                                                    if (user_group=="车主用户"){
            sqlwhere+= "owner_user = " + user_id + " or ";
          }
                                                                                                                                                                                                                                            if (user_group=="销售经理"){
            sqlwhere+= "sales_manager = " + user_id + " or ";
          }
                                      if (user_group=="经理用户"){
            sqlwhere+= "account_manager = " + user_id + " or ";
          }
                            if (sqlwhere.length>1){
            sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
            sqlwhere += ")";
            data.sqlwhere = sqlwhere;
          }
              }
            await this.$get( "~/api/reservation_record/bar_group?field="+query_str+"&groupby="+"service_name",data,(json) => {
          if (json.result) {
            let xAxis = [];
            let values = [];
            json.result.list.map((o) => {
              if (date_flag === "日期") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
              }else if (date_flag === "时间") {
                xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
              }else if (date_flag === "日长") {
                xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
              }else {
                xAxis.push(o[0]);
              }
              values.push(o.splice(1))
            });
            this.bar_obj_reservation_record_7.xAxis = xAxis;
            this.bar_obj_reservation_record_7.values = values;
          }
          if (flag){
            this.get_nickname(this.bar_obj_reservation_record_7.xAxis,true);
          }
        });
      },
        // 获取指定日期的数据
      async get_order_month_some_price(date, o) {
        function getDateBetween(start, end) {
          var result = [];
          //使用传入参数的时间
          var startTime = new Date(start);
          var endTime = new Date(end);
          while (endTime - startTime >= 0) {
            let year = startTime.getFullYear();
            let month = startTime.getMonth();
            month = month < 9 ? '0' + (month + 1) : month + 1;
            let day =
                startTime.getDate().toString().length === 1
                    ? '0' + startTime.getDate()
                    : startTime.getDate();
            //加入数组
            result.push(year + '-' + month + '-' + day);
            //更新日期
            startTime.setDate(startTime.getDate() + 1);
          }
          return result;
        }

        const now = new Date();
        const nowYear = now.getFullYear();
        const nowMonth = now.getMonth() + 1;
        const nowDate = now.getDate();
        let days = [];
        if (date) {
          days = getDateBetween(date[0], date[1]);
        } else {
          o.month[0] = new Date(nowYear, nowMonth - 1, 1, 0, 0, 0).toStr('yyyy-MM-dd');
          o.month[1] = new Date(nowYear, nowMonth - 1, nowDate, 23, 59, 59).toStr('yyyy-MM-dd');
          days = getDateBetween(o.month[0], o.month[1]);
        }

        o.xAxis = [];
        o.values = [];
        o.loading = true;
        let list = {
          result: [],
          total: 0,
        };
        let field = o.type === 'money' ? 'price_count' : 'num';
        for (let i = 0; i < days.length; i++) {
          const t = new Date(days[i]);
          const time = t.getFullYear() + '-' + (t.getMonth() + 1) + '-' + t.getDate();
          o.xAxis.push(t.getMonth() + 1 + '-' + t.getDate());
          await this.get_order_sum_price_sub(list, time, i, field);
        }
        o.loading = false;
        o.values = list.result;
        o.total = list.total;
      },

      // 获取某一天的销售数量
      async get_order_sum_price_sub(list, date, index, field) {
        let user_group = this.userGroup;
        let user_id = this.userInfo.user_id;
        let time = new Date(date).toStr('yyyy-MM-dd');
        var $where = '';
        if (user_group != '管理员') $where = '&merchant_id=' + user_id;
        const json = await this.$get(
            `~/api/order/sum?field=${field}&create_time_min=${time} 00:00:00&create_time_max=${time} 23:59:59&state=已完成` +
                    $where,
            null
        );
        list.result[index] = [];
        if (json.result) {
          list.total += json.result;
          list.result[index].push(json.result);
        } else {
          list.result[index].push(0);
        }
      },

      // 选择日期
      changeMonth(e, o) {
        this.get_order_month_some_price(e, o);
      },
      async get_shop_type_statistics(date, o) {
        const now = new Date();
        const nowYear = now.getFullYear();
        const nowMonth = now.getMonth() + 1;
        const nowDate = now.getDate();

        let create_time_min, create_time_max;

        if (date && date.length === 2) {
          create_time_min = new Date(date[0]).toStr("yyyy-MM-dd hh:mm:ss");
          create_time_max = new Date(date[1]).toStr("yyyy-MM-dd hh:mm:ss");
        } else {
          create_time_min = nowYear + '-' + nowMonth.toString().padStart(2, "0") + '-01 00:00:00';
          create_time_max = nowYear + '-' + nowMonth.toString().padStart(2, "0") + '-' + nowDate + ' 23:59:59';
          o.month = [new Date(create_time_min), new Date(create_time_max)];
        }

        o.loading = true;
        try {
          let user_group = this.userGroup;
          let user_id = this.userInfo.user_id;
          var $where = "";
          if (user_group != "管理员") {
            $where = '&merchant_id=' + user_id;
          }

          const json = await this.$get(
            `~/api/order/list_group?groupby=type&create_time_min=${create_time_min}&create_time_max=${create_time_max}&state=已完成` + $where,
            null
          );

          if (json.result && json.result.list) {
            o.values = json.result.list.map(function (item) {
              return {
                value: item.count,
                name: item.type || '未知类型'
              };
            });
            o.total = o.values.reduce(function (sum, item) {
              return sum + item.value;
            }, 0);
          } else {
            o.values = [];
            o.total = 0;
          }
        } catch (error) {
          console.error("获取商品类型统计失败:", error);
          o.values = [];
          o.total = 0;
        }
        o.loading = false;
      },

      // 销售分类的日期选择事件
      changeMonthForType(e, o) {
        this.get_shop_type_statistics(e, o);
      },
    },
    created() {
        // 执行汽车车型统计数据获取
      this.get_list_car_information_1();
          // 执行购买订单统计数据获取
      this.get_list_purchase_order_2();
          // 执行保养订单统计数据获取
      this.get_list_maintenance_order_3();
          // 执行汽车新旧统计数据获取
      this.get_list_car_information_4();
          // 执行员工绩效统计数据获取
      this.get_list_employee_performance_5();
          // 执行汽车品牌销售统计数据获取
      this.get_list_purchase_order_6();
          // 执行预约记录统计数据获取
      this.get_list_reservation_record_7();
        this.get_order_month_some_price(null, this.line_obj_shop_money)
      this.get_order_month_some_price(null, this.bar_obj_shop_num)
      this.get_shop_type_statistics(null, this.circle_obj_shop_type)
    },
    components: {
        newBarChart,
        newLineChart,
        barChat,
        pieChart,
    },
  };
</script>
<style lang="scss" scoped>
  @import 'styles/pagesA/index.scss';
</style>
