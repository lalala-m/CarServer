<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_reservation_record_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">预约记录列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																													        <view class="search-wrap">
	  	    	  	    	  	    	      	          <Search v-model="query.service_name" placeholder="搜索服务名称" @search="search_" @cancel="search_cancel('service_name')" @input="(val) => inputValue(val, 'service_name')" />
	      	    	  	    	  	    	  	    	      	          <Search v-model="query.owners_name" placeholder="搜索车主姓名" @search="search_" @cancel="search_cancel('owners_name')" @input="(val) => inputValue(val, 'owners_name')" />
	      	    	  	    	      	          <Search v-model="query.contact_number" placeholder="搜索联系号码" @search="search_" @cancel="search_cancel('contact_number')" @input="(val) => inputValue(val, 'contact_number')" />
	      	    	  	    	      	    	  	    	  	    	  	    	  	    	  	    	      	    	  	    	  	    	      	          <Search v-model="query.car_type" placeholder="搜索汽车类型" @search="search_" @cancel="search_cancel('car_type')" @input="(val) => inputValue(val, 'car_type')" />
	      	    	  	    	  	    	  	    	  	  	    	  	    	  	    	      	    	  	    	  	    	  	    	      	    	  	    	      	    	  	    	      					<view class="date_select" @click="appointment_date_date_range_picker_show = true">{{appointment_date_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="appointment_date_date_select_clear"/>

          </view>
					    	  	    	  	    	  	    	  	    	  	    	      		  	  <view class="drop_down">
	        	<view class="filter-item" @click="showFilter('license_plate_number')">
					{{ filter_text.license_plate_number || '请选择车牌号码' }}
				</view>
	          </view>
	      	    	  	    	  	    	      	    	  	    	  	    	  	    	  	  			          <MeDropdown
	            :menuList.sync="menuList"
	            themeColor="#0079fe"
	            :duration="300"
	            :isCeiling="true"
	            @onConfirm="onConfirmDropdown"
	            @onChange="onChangeDropdown"
	          ></MeDropdown>
	        </view>
	  
	        <!-- 筛选模块(结束) -->
	  <!-- 列表 -->
	        <view class="customized-list" :class="{'has-image': hasImageItems}">
	  	          <view v-for="(o, i) in showList" :key="i" class="customized-item">
	  	  	            <view
	              class="customized-item-body"
	              @click="toDetails(o)">
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'booking_code')">
	                <view class="label" v-if="true">
	                  <span>预约编码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['booking_code'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'service_code')">
	                <view class="label" v-if="true">
	                  <span>服务编码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['service_code'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'service_name')">
	                <view class="label" v-if="true">
	                  <span>服务名称</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['service_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'service_price')">
	                <view class="label" v-if="true">
	                  <span>服务价格</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['service_price'] }}元</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'owner_user')">
	                <view class="label" v-if="true">
	                  <span>车主用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_owner_user(o['owner_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_owner_user(o['owner_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'owners_name')">
	                <view class="label" v-if="true">
	                  <span>车主姓名</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['owners_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'contact_number')">
	                <view class="label" v-if="true">
	                  <span>联系号码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['contact_number'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'appointment_date')">
	                <view class="label" v-if="true">
	                  <span>预约日期</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['appointment_date'], 'yyyy-MM-dd') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'appointment_period')">
	                <view class="label" v-if="true">
	                  <span>预约时段</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['appointment_period'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'number_of_appointments')">
	                <view class="label" v-if="true">
	                  <span>预约次数</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['number_of_appointments'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'delivery_time')">
	                <view class="label" v-if="true">
	                  <span>交车时间</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['delivery_time'], 'yyyy-MM-dd') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'sales_consultant')">
	                <view class="label" v-if="true">
	                  <span>销售顾问</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['sales_consultant'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'license_plate_number')">
	                <view class="label" v-if="true">
	                  <span>车牌号码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['license_plate_number'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'frame_number')">
	                <view class="label" v-if="true">
	                  <span>车架号</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['frame_number'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'car_type')">
	                <view class="label" v-if="true">
	                  <span>汽车类型</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_type'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'appointment_remarks')">
	                <view class="label" v-if="true">
	                  <span>预约备注</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['appointment_remarks'] }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'sales_manager')">
	                <view class="label" v-if="true">
	                  <span>销售经理</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_sales_manager(o['sales_manager']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_sales_manager(o['sales_manager']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'account_manager')">
	                <view class="label" v-if="true">
	                  <span>客户经理</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_account_manager(o['account_manager']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_account_manager(o['account_manager']).nickname }}</span>
	                </view>
	    	              </view>
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
		<uni-popup ref="filterPopup" type="bottom" background-color="#fff" @change="onPopupChange">
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
import Search from '@/components/businessCp/search.vue';
import mixin from '@/libs/mixins/page.js';
import MeDropdown from '@/components/me-dropdown/index.vue';
import dateRangePicker from '@/components/date-range-picker/date-range-picker.vue';
export default {
  mixins: [mixin],
  components: {
    Search,
    MeDropdown,
								    dateRangePicker,
																			  },
  data() {
    return {
	  isBack: true,
      url_get_list: '~/api/reservation_record/get_list?like=1',
                                    // 用户列表
      list_user_owner_user: [],
                                                        // 车牌号码选项列表
      list_license_plate_number: [{value:"全部",text:"全部"}],
	  placeholder_license_plate_number: "请选择车牌号码",
                                    // 用户列表
      list_user_sales_manager: [],
                  // 用户列表
      list_user_account_manager: [],
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
      query: {
                    service_name: "", // 服务名称
                            owners_name: "", // 车主姓名
                        contact_number: "", // 联系号码
                				appointment_date_min: '',
        appointment_date_max: '',
                                license_plate_number: "", // 车牌号码
                          car_type: "", // 汽车类型
                      reservation_record_id: 0, // ID
        page: 1,
        size: 10,
      },
      list: [],
			showList: [],
      count: 50,
      menuList: [
                                            {
            title: '发布时间',
            type: 'sort',
            command: '`create_time`',
            value: 0,
        }
      ],
						 									 									 									 			service_price_input_timer: null,
													 									 									 									 						appointment_date_date_range_picker_show: false,
      appointment_date_date_text: '请选择预约日期',
										 									 									 						delivery_time_date_range_picker_show: false,
      delivery_time_date_text: '请选择交车时间',
										 									 									 									 									 									 									 						    };
  },
	computed: {
		// 判断列表中是否有图片项
		hasImageItems() {
			return this.showList.some(item => {
																																																																																																																																																																										return false;
			});
		},
	},
  watch: {
  	list: {
  		handler(val) {
								this.showList = val;
				  		},
  		deep: true
  	},
  },
	onLoad() {
	},
  methods: {
	toDetails(o) {
						this.$navTo('/pagesC/reservation_record/details?reservation_record_id=' + o['reservation_record_id'])
			},
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
																																								    onConfirmDropdown(val) {
      if (val.value) {
        this.query.orderby = val.command + ' ' + val.value;
      } else {
        this.query.orderby = '';
      }
      this.search_();
    },
    onChangeDropdown(v) {},
    /**
     * 获取列表后
     * @param {Object} json
     * @param {Object} func
     */
    get_list_after(json, func) {
      let list = json.result.list;
      if (func) {
        func(json);
      }
    },
						service_price_input() {
			this.service_price_input_timer && clearTimeout(this.service_price_input_timer);
			this.service_price_input_timer = setTimeout(() => {
				if((this.query.service_price_min && this.query.service_price_max) || (!this.query.service_price_min && !this.query.service_price_max)) {
					this.search_();
				}
			}, 300);
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
      let ret = {nickname: "未知用户", avatar: ""};
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i];
        }
      }
      return ret;
    },
                                                                                                        // 改变分类标签
        change_license_plate_number(val) {
          if (val === "全部" || val === "") {
            this.query.license_plate_number = "";
          }else{
            this.query.license_plate_number = val
          }
          this.search_();
        },
                /**
     * 获取车牌号码列表
     */
    async get_list_license_plate_number() {
                          var json = await this.$get("~/api/vehicle_information/get_list?");
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
          }
          else if(json.error){
            $.toast(json.error.message);
            console.error(json.error);
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
      let ret = {nickname: "未知用户", avatar: ""};
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i];
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
      let ret = {nickname: "未知用户", avatar: ""};
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i];
        }
      }
      return ret;
    },
            search_() {
      this.query.page = 1;
      this.get_list();
    },
    search_cancel(key) {
      this.query[key] = '';
      this.search_();
    },
    // 显示筛选弹窗
    showFilter(type) {
	  this.showTabbar = false;
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	        	        	        	        	        	        	        	                case 'license_plate_number':
          this.popupTitle = '选择车牌号码';
          this.initCascader(this.list_license_plate_number);
          break;
        	        	        	        	        	        	      }
      this.$refs.filterPopup.open();
    },
	onPopupChange(e) {
		if (!e.show) {
			this.showTabbar = true;
		}
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
		this.search_();
	},
	hideFilter() {
		this.$refs.filterPopup.close();
	},
	inputValue(val, key) {
	  this.query[key] = val;
	  this.search_();
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
		margin-bottom: 10px;
	}
	.date_select {
		position: relative;
		text-align: center;
		padding: 8px;
		border: 1px solid #e5e5e5;
		border-radius: 50px;
		color: #6a6a6a;
		margin-bottom: 10px;
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
	border-radius: 50px;
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
.uni-popup.bottom{
  z-index: 9998;
}
</style>