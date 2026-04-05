<template>
  <view class="page_diy_table page_reservation_record_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">预约记录列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
                <uni-forms-item label="服务名称" name="service_name">
                <uni-easyinput type="text" v-model="query.service_name" placeholder="服务名称" />
              </uni-forms-item>
                  <uni-forms-item label="车主姓名" name="owners_name">
                <uni-easyinput type="text" v-model="query.owners_name" placeholder="车主姓名" />
              </uni-forms-item>
              <uni-forms-item label="联系号码" name="contact_number">
                <uni-easyinput type="text" v-model="query.contact_number" placeholder="联系号码" />
              </uni-forms-item>
              <uni-forms-item label="预约日期" name="appointment_date">
    					<view class="date_select" @click="appointment_date_date_range_picker_show = true">{{appointment_date_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="appointment_date_date_select_clear"/>
          </view>
              </uni-forms-item>
                      <uni-forms-item label="车牌号码" name="license_plate_number">
                <view class="filter-item" @click="showFilter('license_plate_number')">
              {{ filter_text.license_plate_number || '请选择车牌号码' }}
            </view>
              </uni-forms-item>
                <uni-forms-item label="汽车类型" name="car_type">
                <uni-easyinput type="text" v-model="query.car_type" placeholder="汽车类型" />
              </uni-forms-item>
                    <uni-forms-item label="支付状态" name="pay_state">
              <uni-data-select placeholder="请选择支付状态"
                               v-model="query['pay_state']"
                               :localdata="list_pay_state"
              ></uni-data-select>
            </uni-forms-item>
        </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="0 && $check_field('get','booking_code')">
            <view class="label" v-if="true">
              <span>预约编码</span>
            </view>
              <view class="value">
                              <span>{{ o['booking_code'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','service_code')">
            <view class="label" v-if="true">
              <span>服务编码</span>
            </view>
              <view class="value">
                              <span>{{ o['service_code'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','service_name')">
            <view class="label" v-if="true">
              <span>服务名称</span>
            </view>
              <view class="value">
                              <span>{{ o['service_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','service_price')">
            <view class="label" v-if="true">
              <span>服务价格</span>
            </view>
              <view class="value">
                              <span>{{ o['service_price'] }}元</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','owner_user')">
            <view class="label" v-if="true">
              <span>车主用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_owner_user(o['owner_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','owners_name')">
            <view class="label" v-if="true">
              <span>车主姓名</span>
            </view>
              <view class="value">
                              <span>{{ o['owners_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','contact_number')">
            <view class="label" v-if="true">
              <span>联系号码</span>
            </view>
              <view class="value">
                              <span>{{ o['contact_number'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','appointment_date')">
            <view class="label" v-if="true">
              <span>预约日期</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['appointment_date'], 'yyyy-MM-dd') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','appointment_period')">
            <view class="label" v-if="true">
              <span>预约时段</span>
            </view>
              <view class="value">
                              <span>{{ o['appointment_period'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','number_of_appointments')">
            <view class="label" v-if="true">
              <span>预约次数</span>
            </view>
              <view class="value">
                              <span>{{ o['number_of_appointments'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','delivery_time')">
            <view class="label" v-if="true">
              <span>交车时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['delivery_time'], 'yyyy-MM-dd') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','sales_consultant')">
            <view class="label" v-if="true">
              <span>销售顾问</span>
            </view>
              <view class="value">
                              <span>{{ o['sales_consultant'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','license_plate_number')">
            <view class="label" v-if="true">
              <span>车牌号码</span>
            </view>
              <view class="value">
                              <span>{{ o['license_plate_number'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','frame_number')">
            <view class="label" v-if="true">
              <span>车架号</span>
            </view>
              <view class="value">
                              <span>{{ o['frame_number'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','car_type')">
            <view class="label" v-if="true">
              <span>汽车类型</span>
            </view>
              <view class="value">
                              <span>{{ o['car_type'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','appointment_remarks')">
            <view class="label" v-if="true">
              <span>预约备注</span>
            </view>
              <view class="value">
                              <span>{{ o['appointment_remarks'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','sales_manager')">
            <view class="label" v-if="true">
              <span>销售经理</span>
            </view>
              <view class="value">
              <span>{{ get_user_sales_manager(o['sales_manager']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','account_manager')">
            <view class="label" v-if="true">
              <span>客户经理</span>
            </view>
              <view class="value">
              <span>{{ get_user_account_manager(o['account_manager']) }}</span>
            </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/reservation_record/table', 'del') || $check_action('/reservation_record/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/reservation_record/view?' + field + '=' + o[field])"
                  v-if="$check_action('/reservation_record/table', 'set') || $check_action('/reservation_record/view', 'set') || $check_action('/reservation_record/view', 'get')">
              详情
            </view>
              <view width="100%"
                    @click="goto_pay(o)"
                    v-if="o.pay_state === '未支付' && $check_pay('/reservation_record/table') "
                    class="me-btn btn-pay">支付</view>
          </view>
        </view>
      </view>
      <!-- /列表 -->
      <!-- 分页器 -->
      <uni-pagination
        class="pager"
        show-icon="true"
        :total="count"
        :pageSize="query.size"
        :current="query.page"
        @change="page_change"
      ></uni-pagination>
      <!-- /分页器 -->
																																																												      <view class="pay-model" v-if="payModalVisible">
        <view class="mask" @click="payModalVisible = false"></view>
        <view class="pay-model-inn">
          <view v-if="pay_step === 1">
            <view class="pay-info">
    		            		            		                <view class="pay-info-item">
                <view class="pay-info-title">支付内容</view>
                <view class="pay-info-content">{{ pay_data.service_name }}</view>
              </view>
                		                      <view class="pay-info-item">
                <view class="pay-info-title">订单金额</view>
                <view class="pay-info-content">¥{{ pay_data.service_price }}</view>
              </view>
          		            		            		            		            		            		            		            		            		            		            		            		            		            		                              <view class="pay-info-item pay-amount">
                <view class="pay-info-title">实付金额</view>
                <view class="pay-info-content pay-info-amount">¥{{ pay_data.amount_paid }}</view>
              </view>
            </view>
            <view class="pay-type">
              <view class="pay-type-title">支付方式</view>
              <view class="pay-type-list">
                <view v-for="(item, index) in pay_source" :key="index"
                  :class="['pay-type-item', pay_obj.payActiveName === item.name ? 'pay-type-item-active' : '']"
                  @click="pay_obj.payActiveName = item.name">
                  <view class="pay-type-content">
                    <image class="pay-type-img" :src="item.img" mode="aspectFit"></image>
                    <view class="pay-type-name">{{ item.name }}</view>
                  </view>
                    </view>
              </view>
            </view>
            <view class="pay-btn-wrap">
              <button class="me-btn" type="primary" @click="confirmPayStep">确认</button>
            </view>
          </view>
          <view v-if="pay_step === 2">
            <view class="pay-op">
              <image v-if="pay_obj.payActiveName === '微信支付'" :src="pay_source[0].pay_img" mode="widthFix" class="pay-image"></image>
              <image v-if="pay_obj.payActiveName === '支付宝支付'" :src="pay_source[1].pay_img" mode="widthFix" class="pay-image"></image>
              <view v-if="pay_obj.payActiveName === '网银支付'" class="pay-ebank">
                <view class="pay-ebank-item">
                  <view class="pay-ebank-title">请输入网银账号：</view>
                  <input class="pay-ebank-input" v-model="pay_obj.account" placeholder="请输入网银账号" />
                </view>
                <view class="pay-ebank-item">
                  <view class="pay-ebank-title">请输入支付密码，6位数字：</view>
                  <input class="pay-ebank-input" placeholder="请输入密码" v-model="pay_obj.password" type="password"
                    maxlength="6" />
                </view>
              </view>
            </view>
            <view class="pay-btn-wrap">
              <button class="me-btn me-btn-plain" @click="pay_step = 1">其他支付方式</button>
              <button class="me-btn" type="primary" @click="submit_pay">确认支付</button>
            </view>
          </view>
        </view>
      </view>
      </view>
                                                                                                                                                                                                                          <dateRangePicker
          style="z-index: 9999;"
          :show="appointment_date_date_range_picker_show"
          :minYear="2022"
          @close="appointment_date_date_range_picker_show=false"
          @confirm="appointment_date_date_range_picker_confirm"
        />
                                                                                              <dateRangePicker
          style="z-index: 9999;"
          :show="delivery_time_date_range_picker_show"
          :minYear="2022"
          @close="delivery_time_date_range_picker_show=false"
          @confirm="delivery_time_date_range_picker_confirm"
        />
                                                                              <!--分类选择器-->
        <uni-popup ref="filterPopup" type="bottom" background-color="#fff">
          <view class="filter-popup">
            <view class="popup-header">
              <text class="popup-title">{{ popupTitle }}</text>
              <text class="popup-close" @click="hideFilter">取消</text>
            </view>
            <picker-view class="cascader-picker" :value="cascaderIndex" @change="cascaderPickerChange" :key="cascaderColumns.length" 
              indicator-style="height: 50px;">
              <picker-view-column v-for="(column, colIndex) in cascaderColumns" :key="colIndex">
                <view class="picker-item" :class="{ active: cascaderIndex[colIndex] === 0 }">全部</view>
                <view v-for="(item, index) in column" :key="index" class="picker-item"
                  :class="{ active: cascaderIndex[colIndex] === index + 1 }">
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {{ item.license_plate_number }}
                </view>
              </picker-view-column>
            </picker-view>

            <view class="popup-footer">
              <button class="confirm-btn" @click="confirmFilter">确定</button>
            </view>
          </view>
        </uni-popup>
                                                                                                                                                            </view>
</template>
<script>
import mixin from '@/libs/mixins/page.js';
import dateRangePicker from '@/components/date-range-picker/date-range-picker.vue';
import DateSelector from '@/components/dengrq-datetime-picker/dateSelector/index.vue';

																							
export default {
  mixins: [mixin],
  components: {
    dateRangePicker,
    DateSelector,
  },
  data() {
    return {
      // 获取数据地址
      url_get_list: '~/api/reservation_record/get_list?like=1',
      url_del: '~/api/reservation_record/del',

      // 字段ID
      field: 'reservation_record_id',
      // 查询
      query: {
        size: 7,
        page: 1,
                  service_name: "",
                        owners_name: "",
                    contact_number: "",
            				appointment_date_min: '',
        appointment_date_max: '',
                            license_plate_number: "",
                      car_type: "",
                    "pay_state":"",
        login_time: '',
        create_time: '',
      },
																			  currentFilterType: '', // 当前筛选类型
	  popupTitle: '',
	  // 选择器数据
	  cascaderList: [],
	  cascaderColumns: [[], [], []],
	  cascaderIndex: [0, 0, 0],
	  // 显示文本
	  filter_text: {
  	  	  	  	  	  	  	  	  	  	  	  	  			license_plate_number: '',
	  	  	  	  	  	  			},
      // 数据
      list: [],
                                      	  service_price_input_timer: null,
                      // 用户列表
      list_user_owner_user: [],
                                          appointment_date_date_range_picker_show: false,
    appointment_date_date_text: '请选择预约日期',
                                    delivery_time_date_range_picker_show: false,
    delivery_time_date_text: '请选择交车时间',
                        // 车牌号码列表
      list_license_plate_number: [{value:"",text:"全部"}],
                                                          // 用户列表
      list_user_sales_manager: [],
                          // 用户列表
      list_user_account_manager: [],
              list_pay_state: [
        { value: '', text: '全部' },
        { value: '未支付', text: '未支付' },
        { value: '已支付', text: '已支付' },
        ],
      model:false,
      paywayact:0,
      // 支付方式
      payway:[
        { name: '微信', url: '/static/img/wx.png', id: 1 },
        { name: '支付宝', url: '/static/img/aplipay.png', id: 2 },
        {
          name: '其他',
          url: 'https://dzgj.yuyuebus.com/static/front/dist/static/img/icon-question.png',
          id: 3,
        }
      ],
      pay_obj:{
        payActiveName:"微信支付",
        account:"",
        password:"",
        id:""
      },
        payModalVisible: false,
      pay_step: 1,
      pay_data: {},
      pay_source: [
        {
          name: "微信支付",
          img: "/static/icon/wechat.svg",
          pay_img: "/static/img/wx.png"
        },
        {
          name: "支付宝支付",
          img: "/static/icon/alipay.svg",
          pay_img: "/static/img/aplipay.png"
        },
        {
          name: "网银支付",
          img: "/static/icon/ebank.svg"
        },
          ],
      };
  },
  methods: {
                                  appointment_date_date_range_picker_confirm(date) {
      this.query.appointment_date_min = date[0];
      this.query.appointment_date_max = date[1];
      this.appointment_date_date_text = `${date[0]} - ${date[1]}`;
      this.search_();
    },
    appointment_date_date_select_clear() {
      this.query.appointment_date_min = '';
      this.query.appointment_date_max = '';
      this.appointment_date_date_text = '请选择预约日期';
      this.search_();
    },
                  delivery_time_date_range_picker_confirm(date) {
      this.query.delivery_time_min = date[0];
      this.query.delivery_time_max = date[1];
      this.delivery_time_date_text = `${date[0]} - ${date[1]}`;
      this.search_();
    },
    delivery_time_date_select_clear() {
      this.query.delivery_time_min = '';
      this.query.delivery_time_max = '';
      this.delivery_time_date_text = '请选择交车时间';
      this.search_();
    },
                                						service_price_input() {
			this.service_price_input_timer && clearTimeout(this.service_price_input_timer);
			this.service_price_input_timer = setTimeout(() => {
				if((this.query.service_price_min && this.query.service_price_max) || (!this.query.service_price_min && !this.query.service_price_max)) {
					this.search_();
				}
			}, 300);
		},
															    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
                                    this.appointment_date_date_range_picker_show = false;
    this.appointment_date_date_text = '请选择预约日期';
                  this.delivery_time_date_range_picker_show = false;
    this.delivery_time_date_text = '请选择交车时间';
                                  this.filter_text = {
                                                                              		license_plate_number: '',
	                                      };
      uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
                                                          if(user_group=="车主用户"){
          sqlwhere+= "owner_user = " + this.user.user_id + " or ";
        }
                                                                                                                                      if(user_group=="销售经理"){
          sqlwhere+= "sales_manager = " + this.user.user_id + " or ";
        }
                        if(user_group=="经理用户"){
          sqlwhere+= "account_manager = " + this.user.user_id + " or ";
        }
                  if (sqlwhere.length>1){
          sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
          sqlwhere += ")";
          param["sqlwhere"] = sqlwhere;
        }
        }
      return param;
    },
    async delInfo(v) {
      let _this = this;
      let type = this.list[v];
			let res
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              uni.showModal({
        title: '删除',
        content: '此操作将永久删除该文件, 是否继续?',
        success: function (res) {
          if (res.confirm) {
            let list = [v];
            _this.delAll(list);
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        },
      });
    },
	get_list_after(param){
	      let _this = this;
		},
                                          /**
     * 获取车主用户用户列表
     */
    async get_list_user_owner_user() {
      var json = await this.$get("~/api/user/get_list?user_group=车主用户");
      if(json.result && json.result.list){
        this.list_user_owner_user = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },

    get_user_owner_user(id){
      let obj = this.list_user_owner_user;
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
    },
                                                                  /**
     * 获取车牌号码列表
     */
    async get_list_license_plate_number() {
                    let param = {}
                                                                                                                      var user_group = this.user.user_group;
                          let sqlwhere = "(";
                                                if(user_group=="车主用户"){
                    sqlwhere+= "owner_user = " + this.user.user_id + " or ";
                  }
                                                                                                                                                                    if (sqlwhere.length>1){
                sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
                sqlwhere += ")";
                param["sqlwhere"] = sqlwhere;
              }
                                                                                                                                                                                                                                                                                                                                                        var json = await this.$get("~/api/vehicle_information/get_list", param);
        if(json.result && json.result.list){
          if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
            json.result.list = json.result.list.filter(item => item.type == 1);
          }
          const hasFatherId = json.result.list.some(item => 'father_id' in item);
          if (hasFatherId) {
            this.list_license_plate_number = this.buildTree(json.result.list, 0, 'vehicle_information_id');
          }else{
            this.list_license_plate_number = json.result.list.map(item => ({
              license_plate_number: item.license_plate_number,
              children: []
            }));
          }
        }else if (json.error){
          console.log(json.error);
        }
          },
          buildTree(list, fatherId, type) {
        const tree = [];
        for (let item of list) {
          if (item.father_id === fatherId) {
            const children = this.buildTree(list, item[type], type);
            item.children = children.length > 0 ? children : [];
            tree.push(item);
          }
        }
        return tree;
      },
                                              /**
     * 获取销售经理用户列表
     */
    async get_list_user_sales_manager() {
      var json = await this.$get("~/api/user/get_list?user_group=销售经理");
      if(json.result && json.result.list){
        this.list_user_sales_manager = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },

    get_user_sales_manager(id){
      let obj = this.list_user_sales_manager;
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
    },
              /**
     * 获取经理用户用户列表
     */
    async get_list_user_account_manager() {
      var json = await this.$get("~/api/user/get_list?user_group=经理用户");
      if(json.result && json.result.list){
        this.list_user_account_manager = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },

    get_user_account_manager(id){
      let obj = this.list_user_account_manager;
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
    },
        // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	        	        	        	        	        	        	        	                case 'license_plate_number':
          this.popupTitle = '选择车牌号码';
          this.initCascader(this.list_license_plate_number);
          break;
        	        	        	        	        	        	      }
      this.$refs.filterPopup.open();
    },
	initCascader(list) {
		this.cascaderList = list;
		const maxDepth = Math.max(1, this.getMaxDepth(list));
		this.cascaderColumns = Array(maxDepth).fill().map(() => []);
		this.cascaderIndex = Array(maxDepth).fill(0);
		this.cascaderColumns[0] = list;
		// 递归设置后续列数据
		this.updateCascaderColumns(0);
	},
	// 计算最大深度
	getMaxDepth(list, depth = 1) {
		if (!list || list.length === 0) return depth;
		let max = depth;
		for (let item of list) {
			if (item.children && item.children.length > 0) {
				const childDepth = this.getMaxDepth(item.children, depth + 1);
				max = Math.max(max, childDepth);
			}
		}
		return max;
	},
	// 更新级联列数据
	updateCascaderColumns(level) {
		// 更新当前层级的下一个层级
		const nextLevel = level + 1;
		if (nextLevel >= this.cascaderColumns.length) return;
		
		const currentList = this.cascaderColumns[level];
		const currentIndex = this.cascaderIndex[level];

		if (currentIndex > 0 && currentList[currentIndex - 1]?.children) {
			this.cascaderColumns[nextLevel] = currentList[currentIndex - 1].children;
		} else {
			this.cascaderColumns[nextLevel] = [];
		}
		this.$forceUpdate();
			// 递归更新后续层级
		this.updateCascaderColumns(nextLevel);
	},
	// 选择器变化
	cascaderPickerChange(e) {
		const value = e.detail.value;
		// 找出变化的层级
		let changedLevel = 0;
		for (let i = 0; i < value.length; i++) {
			if (value[i] !== this.cascaderIndex[i]) {
				changedLevel = i;
				break;
			}
		}
		// 更新索引
		this.cascaderIndex = value;
		this.updateCascaderColumns(changedLevel);

		// 重置后续级别的索引为0
		for (let i = changedLevel + 1; i < this.cascaderIndex.length; i++) {
			this.cascaderIndex[i] = 0;
		}
	},
	// 确认选择
	confirmFilter() {
		let selectedText = '';
		let queryValue = '';

		const selected = [];
		for (let i = 0; i < this.cascaderColumns.length; i++) {
			if (this.cascaderIndex[i] > 0) {
				const item = this.cascaderColumns[i][this.cascaderIndex[i] - 1];
				selected.push(item);
			}
		}

		if (selected.length > 0) {
			const lastItem = selected[selected.length - 1];
																																																																																																																																											queryValue = lastItem.license_plate_number;
						selectedText = selected.map(item => item.license_plate_number).join(' / ');
		}else{
			selectedText = '';
		}


		// 更新显示文本和查询条件
		this.filter_text[this.currentFilterType] = selectedText;
		this.query[this.currentFilterType] = queryValue;

		this.hideFilter();
	},
	hideFilter() {
		this.$refs.filterPopup.close();
	},
    /**
     * 打开支付
     */
    goto_pay(v) {
      //打开选择支付方式
      this.pay_obj.id = v.reservation_record_id;
        this.pay_data = v;
                                              this.pay_data.amount_paid = v.service_price;
                                                                                                                                                                
      this.payModalVisible = true;
      this.pay_step = 1;
        
    },
      confirmPayStep() {
      if (['微信支付', '支付宝支付', '网银支付'].includes(this.pay_obj.payActiveName)) {
        this.pay_step = 2;
      }
        },
      choosepayway(idx) {
      switch (idx) {
        case 0:
          this.pay_obj.payActiveName = '微信';
          break;
        case 1:
          this.pay_obj.payActiveName = '支付宝';
          break;
        case 2:
          this.pay_obj.payActiveName = '网银';
          break;
      }
      this.paywayact = idx;
    },
    closemodel() {
      //关闭选择支付方式
        this.payModalVisible = false;
      this.pay_step = 1;
      this.pay_obj.account = "";
      this.pay_obj.password = "";
      },
    /**
     * 支付
     */
    submit_pay() {
            let _this = this;
      let url = this.$toUrl(
              this.query,
              '~/api/reservation_record/set?reservation_record_id=' + this.pay_obj.id
      );
      let name = this.pay_obj.payActiveName;
      let payType = name.endsWith("支付") ? name.slice(0, -2) : name;
      let param = { pay_state: '已支付', pay_type: payType };
                                                                                                                                                                                              this.$post(url, param, (res) => {
        if (res.result) {
          _this.$toast('支付成功');
                              setTimeout(() => {
            _this.$nav('/pagesC/reservation_record/table');
            _this.pay_obj.payActiveName = "微信支付";
            _this.pay_step = 1;
            _this.pay_obj.account = "";
            _this.pay_obj.password = "";
          }, 700);
        } else if (res.error) {
          _this.$toast(res.error.message);
          console.log(res.error);
        }
      });
    },
  },
  created() {
                            this.get_list_user_owner_user();
                                    // 初始化车牌号码列表
    this.get_list_license_plate_number();
                            this.get_list_user_sales_manager();
              this.get_list_user_account_manager();
    },
};
</script>

<style lang="scss" scoped>
  @import 'styles/pagesC/index.scss';
  .num_range {
    display: flex;
    align-items: center;
    font-size: 28px;
  }
  .date_select {
    position: relative;
    text-align: center;
    padding: 8px 0;
    border: 1px solid #e5e5e5;
    border-radius: 5px;
    color: #6a6a6a;
  }

  .date_select_clear {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
  }

  /* 弹窗样式 */
  .popup-mask {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 999;
  }

  .date-selector-popup {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #fff;
    border-radius: 16px 16px 0 0;
    z-index: 1000;
    transform: translateY(100%);
    transition: transform 0.3s ease;
    max-height: 80vh;
    display: flex;
    flex-direction: column;
  }

  .date-selector-popup.popup-show {
    transform: translateY(0);
  }

  .popup-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 16px 20px;
    border-bottom: 1px solid #f0f0f0;
    flex-shrink: 0;
  }

  .popup-title {
    font-size: 18px;
    font-weight: 600;
    color: #333;
  }

  .popup-close {
    width: 32px;
    height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 24px;
    color: #666;
    cursor: pointer;
  }

  .popup-content {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
  }
.filter-item {
	padding: 8px 12px;
	border: 1px solid #e5e5e5;
	border-radius: 4px;
	color: #6a6a6a;
	text-align: center;
	margin-bottom: 10px;
}

.filter-popup {
	background: #fff;
	border-radius: 16px 16px 0 0;
	height: 60vh;
	display: flex;
	flex-direction: column;
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 16px 20px;
	border-bottom: 1px solid #f0f0f0;
}

.popup-title {
	font-size: 18px;
	font-weight: 600;
	color: #333;
}

.popup-close {
	font-size: 16px;
	color: #666;
}

.single-picker,
.cascader-picker {
	flex: 1;
	height: 300px;
}

.picker-item {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 50px;
	font-size: 16px;
	color: #666;
}

.picker-item.active {
	color: #0079fe;
	font-weight: 600;
}

.popup-footer {
	padding: 16px 20px;
	border-top: 1px solid #f0f0f0;
}

.confirm-btn {
	width: 100%;
	background: #0079fe;
	color: #fff;
	border: none;
	border-radius: 8px;
	padding: 12px;
	font-size: 16px;
}
.pay-model {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1000;
  
  .mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }
  
  .pay-model-inn {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background: #fff;
    border-radius: 20rpx 20rpx 0 0;
    padding: 40rpx 30rpx;
    box-sizing: border-box;
    max-height: 80vh;
    overflow-y: auto;
  }
}
.pay-info {
  .pay-info-item {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 30rpx;
    font-size: 28rpx;
    
    .pay-info-title {
      color: #666;
      width: 80px;
    }
    
    .pay-info-content {
      color: #999;
      text-align: right;
    }
  }
  
  .pay-amount {
    margin-top: 40rpx;
    padding-top: 30rpx;
    border-top: 1rpx solid #eee;
    border-bottom: 1rpx solid #eee;
    padding-bottom: 30rpx;
    
    .pay-info-amount {
      font-weight: bold;
      font-size: 36rpx;
      color: #f5222d;
    }
  }
}
.pay-type {
  margin-top: 40rpx;
  
  .pay-type-title {
    color: #999;
    margin-bottom: 20rpx;
    font-size: 28rpx;
  }
  
  .pay-type-list {
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    gap: 20rpx;
  }
  
  .pay-type-item {
    background: #f8f8f8;
    border: 2rpx solid #f8f8f8;
    border-radius: 10rpx;
    padding: 30rpx 20rpx;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    .pay-type-content{
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .pay-type-img {
      width: 40rpx;
      height: 40rpx;
      margin-right: 20px;
    }
    
    .pay-type-name {
      font-size: 28rpx;
    }
    
    .pay-type-remark {
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .pay-type-item-active {
    border-color: #2c70f6;
    background: #e8f0ff;
  }
}
.pay-btn-wrap {
  margin-top: 40rpx;
  display: flex;
  gap: 20rpx;
  
  .me-btn {
    flex: 1;
    height: 80rpx;
    line-height: 80rpx;
    border-radius: 10rpx;
    
    &.me-btn-plain {
      background: #fff;
      border: 1rpx solid #ddd;
      color: #333;
    }
  }
}
.pay-ebank {
  .pay-ebank-item {
    margin-top: 40rpx;
    
    .pay-ebank-title {
      margin-bottom: 20rpx;
      color: #666;
    }
    
    .pay-ebank-input {
      border: 1rpx solid #ddd;
      border-radius: 10rpx;
      padding: 20rpx;
      font-size: 28rpx;
      height: 50px;
    }
  }
}

.pay-image {
  width: 96%;
  margin: 0 auto;
  display: block;
}
</style>