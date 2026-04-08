<template>
  <view class="page_diy_table page_maintenance_order_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">保养订单列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="项目套餐" name="project_bundle">
                <uni-easyinput type="text" v-model="query.project_bundle" placeholder="项目套餐" />
              </uni-forms-item>
                            <uni-forms-item label="订单状态" name="order_status">
                <view class="filter-item" @click="showFilter('order_status')">
              {{ filter_text.order_status || '请选择订单状态' }}
            </view>
              </uni-forms-item>
              <uni-forms-item label="审核状态" name="examine_state">
              <uni-data-select placeholder="请选择审核状态"
                               v-model="query['examine_state']"
                               :localdata="list_examine_state"
              ></uni-data-select>
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
				          <Analysis mode="anlDialog" :content="list" />
        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','project_bundle')">
            <view class="label" v-if="true">
              <span>项目套餐</span>
            </view>
              <view class="value">
                              <span>{{ o['project_bundle'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','project_price')">
            <view class="label" v-if="true">
              <span>项目价格</span>
            </view>
              <view class="value">
                              <span>{{ o['project_price'] }}元</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','manager_user')">
            <view class="label" v-if="true">
              <span>经理用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_manager_user(o['manager_user']) }}</span>
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
          <view class="item-row " v-if="0 && $check_field('get','financial_user')">
            <view class="label" v-if="true">
              <span>财务用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_financial_user(o['financial_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','purchase_time')">
            <view class="label" v-if="true">
              <span>购买时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['purchase_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','number_of_purchases')">
            <view class="label" v-if="true">
              <span>购买次数</span>
            </view>
              <view class="value">
                              <span>{{ o['number_of_purchases'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','purchase_notes')">
            <view class="label" v-if="true">
              <span>购买备注</span>
            </view>
              <view class="value">
                              <span>{{ o['purchase_notes'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','order_status')">
            <view class="label" v-if="true">
              <span>订单状态</span>
            </view>
              <view class="value">
                              <span>{{ o['order_status'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/maintenance_order/table', 'del') || $check_action('/maintenance_order/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/maintenance_order/view?' + field + '=' + o[field])"
                  v-if="$check_action('/maintenance_order/table', 'set') || $check_action('/maintenance_order/view', 'set') || $check_action('/maintenance_order/view', 'get')">
              详情
            </view>
              <view width="100%"
                    @click="goto_pay(o)"
                    v-if="o.pay_state === '未支付' && $check_pay('/maintenance_order/table')  && o.examine_state != '未通过'"
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
																													      <view class="pay-model" v-if="!!model">
        <view class="mask" @click="closemodel"></view>
        <view class="pay-model-inn">
          <view class="pay_tab">
            <view @click="choosepayway(idx)" v-for="(obj, idx) in payway" :key="idx" :class="['pay_tab_one', paywayact == idx ? 'pay_tab_one_act' : '']">{{ obj.name }}</view>
          </view>
          <view v-if="payway[paywayact].id !== 3" class="inclued_pay_image">
            <image mode="widthFix" class="pay_image" :src="payway[paywayact].url"></image>
          </view>
          <view v-else class="inclued_pay_image">
            <view>
              <view style="width: 80%; margin: 0 auto; margin-top: 30px" class="account">
                <view style="width: 80%; margin: 0 auto">
                  <view style="line-height: 40px">请输入网银账号：</view>
                </view>
                <view style="width: 80%; margin: 0 auto">
                  <input v-model="pay_obj.account" placeholder="请输入网银账号" />
                </view>
              </view>
              <view style="width: 80%; margin: 0 auto; margin-top: 20px" class="password">
                <view style="width: 80%; margin: 0 auto">
                  <view style="line-height: 40px">请输入支付密码，6位数字：</view>
                </view>
                <view style="width: 80%; margin: 0 auto">
                  <input placeholder="请输入密码" type="password" v-model="pay_obj.password" maxlength="6" />
                </view>
              </view>
            </view>
          </view>
          <view> <button class="me-btn" type="primary" @click="submit_pay()">确认支付</button></view>
        </view>
      </view>
      </view>
                                                                                                                                                        <view class="popup-mask" v-if="purchase_time_date_range_picker_show"></view>
        <view class="date-selector-popup" :class="{ 'popup-show': purchase_time_date_range_picker_show }">
          <view class="popup-header">
            <view class="popup-title">选择购买时间</view>
          </view>
          <view class="popup-content">
            <DateSelector
              :mode="4"
              :defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
              @onSubmit="purchase_time_date_range_picker_confirm"
              @onCancel="purchase_time_date_range_picker_show = false"
            />
          </view>
        </view>
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
                                                                                                                                                                                                                                                                                                                                                                                                                                {{ item.order_status }}
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
import Analysis from '@/pagesC/components/analysis/index.vue';

																												
export default {
  mixins: [mixin],
  components: {
    dateRangePicker,
    DateSelector,
    Analysis,
  },
  data() {
    return {
      // 获取数据地址
      url_get_list: '~/api/maintenance_order/get_list?like=1',
      url_del: '~/api/maintenance_order/del',

      // 字段ID
      field: 'maintenance_order_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              project_bundle: "",
                                  order_status: "",
              "examine_state":"",
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
  	  	  	  	  	  	  	  	  			order_status: '',
	  			},
      // 数据
      list: [],
                                      // 用户列表
      list_user_manager_user: [],
                          // 用户列表
      list_user_owner_user: [],
                          // 用户列表
      list_user_financial_user: [],
                      purchase_time_date_range_picker_show: false,
    purchase_time_date_text: '请选择购买时间',
                                  // 订单状态列表
      list_order_status: [{value:"",text:"全部"}],
                list_examine_state: [
        { value: '', text: '全部' },
        { value: '未审核', text: '未审核' },
        { value: '已通过', text: '已通过' },
        { value: '未通过', text: '未通过' },
      ],
      list_pay_state: [
        { value: '', text: '全部' },
        { value: '未支付', text: '未支付' },
        { value: '已支付', text: '已支付' },
          { value: '已退回', text: '已退回' },
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
        payActiveName:"微信",
        account:"",
        password:"",
        id:""
      },
      };
  },
  methods: {
                            purchase_time_date_range_picker_confirm({ startDate, endDate }) {
      this.query.purchase_time_min = startDate;
      this.query.purchase_time_max = endDate;
      this.purchase_time_date_text = `${startDate} - ${endDate}`;
      this.search_();
      this.purchase_time_date_range_picker_show = false;
    },
    purchase_time_date_select_clear() {
      this.query.purchase_time_min = '';
      this.query.purchase_time_max = '';
      this.purchase_time_date_text = '请选择购买时间';
      this.search_();
    },
              									    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
                            this.purchase_time_date_range_picker_show = false;
    this.purchase_time_date_text = '请选择购买时间';
                  this.filter_text = {
                                                      		order_status: '',
	        };
      uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
                                      if(user_group=="经理用户"){
          sqlwhere+= "manager_user = " + this.user.user_id + " or ";
        }
                        if(user_group=="车主用户"){
          sqlwhere+= "owner_user = " + this.user.user_id + " or ";
        }
                        if(user_group=="财务用户"){
          sqlwhere+= "financial_user = " + this.user.user_id + " or ";
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
     * 获取经理用户用户列表
     */
    async get_list_user_manager_user() {
      var json = await this.$get("~/api/user/get_list?user_group=经理用户");
      if(json.result && json.result.list){
        this.list_user_manager_user = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },

    get_user_manager_user(id){
      let obj = this.list_user_manager_user;
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
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
     * 获取财务用户用户列表
     */
    async get_list_user_financial_user() {
      var json = await this.$get("~/api/user/get_list?user_group=财务用户");
      if(json.result && json.result.list){
        this.list_user_financial_user = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },

    get_user_financial_user(id){
      let obj = this.list_user_financial_user;
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
    },
                                  /**
     * 获取订单状态列表
     */
    async get_list_order_status() {
              this.list_order_status = ['待完成','进行中','已完成'].map((o) => ({ 
          order_status: o, 
          children: [] 
        }));
                },
                // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	        	        	        	                case 'order_status':
          this.popupTitle = '选择订单状态';
          this.initCascader(this.list_order_status);
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
																																																																												queryValue = lastItem.order_status;
						selectedText = selected.map(item => item.order_status).join(' / ');
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
      this.pay_obj.id = v.maintenance_order_id;
        this.model = true;
        
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
        this.model = false;
      },
    /**
     * 支付
     */
    submit_pay() {
            let _this = this;
      let url = this.$toUrl(
              this.query,
              '~/api/maintenance_order/set?maintenance_order_id=' + this.pay_obj.id
      );
      let name = this.pay_obj.payActiveName;
      let payType = name.endsWith("支付") ? name.slice(0, -2) : name;
      let param = { pay_state: '已支付', pay_type: payType };
                                                                                                    this.$post(url, param, (res) => {
        if (res.result) {
          _this.$toast('支付成功');
                              setTimeout(() => {
            _this.$nav('/pagesC/maintenance_order/table');
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
                    this.get_list_user_manager_user();
              this.get_list_user_owner_user();
              this.get_list_user_financial_user();
                    // 初始化订单状态列表
    this.get_list_order_status();
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
	font-size: 16px;
}
</style>