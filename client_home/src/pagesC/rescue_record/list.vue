<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_rescue_record_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">救援记录列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																													        <view class="search-wrap">
	  	    	      	          <Search v-model="query.rescue_code" placeholder="搜索救援编码" @search="search_" @cancel="search_cancel('rescue_code')" @input="(val) => inputValue(val, 'rescue_code')" />
	      	    	  	    	  	    	  	    	  	    	  	    	      	    	  	    	  	    	      	    	  	    	  	    	  	  	    	      	    	  	    	  	    	  	    	  	    	  	    	      		  	  <view class="drop_down">
	        	<view class="filter-item" @click="showFilter('audit_status')">
					{{ filter_text.audit_status || '请选择审核状态' }}
				</view>
	          </view>
	      	    	  	    	  	    	      					<view class="date_select" @click="maintenance_time_date_range_picker_show = true">{{maintenance_time_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="maintenance_time_date_select_clear"/>

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
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'rescue_code')">
	                <view class="label" v-if="true">
	                  <span>救援编码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['rescue_code'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'business_user')">
	                <view class="label" v-if="true">
	                  <span>商家用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_business_user(o['business_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_business_user(o['business_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'rescue_user')">
	                <view class="label" v-if="true">
	                  <span>救援用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_rescue_user(o['rescue_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_rescue_user(o['rescue_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'owners_name')">
	                <view class="label" v-if="true">
	                  <span>车主姓名</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['owners_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'contact_number')">
	                <view class="label" v-if="true">
	                  <span>联系号码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['contact_number'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'audit_status')">
	                <view class="label" v-if="true">
	                  <span>审核状态</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['audit_status'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'maintenance_personnel')">
	                <view class="label" v-if="true">
	                  <span>维修人员</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['maintenance_personnel'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'maintenance_time')">
	                <view class="label" v-if="true">
	                  <span>维修时间</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['maintenance_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row diy-image " v-if="0 && $check_field('get', 'scene_picture')">
	                <view class="label" v-if=" false">
	                  <span>现场图片</span>
	                </view>
	    	                <view class="value diy_img">
	                  <image :src="$fullImgUrl(o['scene_picture'])" width="100%" height="100" />
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'maintenance')">
	                <view class="label" v-if="true">
	                  <span>维修情况</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['maintenance'] }}</span>
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
																																																																																																																																																																																			{{ item.audit_status }}
						</view>
					</picker-view-column>
				</picker-view>

				<view class="popup-footer">
					<button class="confirm-btn" @click="confirmFilter">确定</button>
				</view>
			</view>
		</uni-popup>
												<view class="popup-mask" v-if="maintenance_time_date_range_picker_show"></view>
    <view class="date-selector-popup" :class="{ 'popup-show': maintenance_time_date_range_picker_show }">
      <view class="popup-header">
        <view class="popup-title">选择维修时间</view>
      </view>
      <view class="popup-content">
        <DateSelector
          :mode="4"
					:defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
					@onSubmit="maintenance_time_date_range_picker_confirm"
					@onCancel="maintenance_time_date_range_picker_show = false"
        />
      </view>
    </view>
			    								</view>
</template>

<script>
import Search from '@/components/businessCp/search.vue';
import mixin from '@/libs/mixins/page.js';
import MeDropdown from '@/components/me-dropdown/index.vue';
import DateSelector from '@/components/dengrq-datetime-picker/dateSelector/index.vue';
export default {
  mixins: [mixin],
  components: {
    Search,
    MeDropdown,
																			DateSelector,
	  },
  data() {
    return {
	  isBack: true,
      url_get_list: '~/api/rescue_record/get_list?like=1',
                  // 用户列表
      list_user_business_user: [],
                  // 用户列表
      list_user_rescue_user: [],
                          // 审核状态选项列表
      list_audit_status: [{value:"全部",text:"全部"}],
	  placeholder_audit_status: "请选择审核状态",
                              											  currentFilterType: '', // 当前筛选类型
	  popupTitle: '',
	  // 选择器数据
	  cascaderList: [],
	  cascaderColumns: [[], [], []],
	  cascaderIndex: [0, 0, 0],
	  // 显示文本
	  filter_text: {
  	  	  	  	  	  			audit_status: '',
	  	  	  	  	  			},
      query: {
                rescue_code: "", // 救援编码
                                audit_status: "", // 审核状态
                  				maintenance_time_min: '',
        maintenance_time_max: '',
                    rescue_record_id: 0, // ID
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
						 									 									 									 									 									 									 									 						maintenance_time_date_range_picker_show: false,
      maintenance_time_date_text: '请选择维修时间',
										 									 						    };
  },
	computed: {
		// 判断列表中是否有图片项
		hasImageItems() {
			return this.showList.some(item => {
																																																																																							if (item['scene_picture'] && item['scene_picture'] !== '') {
							return true;
						}
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
						this.$navTo('/pagesC/rescue_record/details?rescue_record_id=' + o['rescue_record_id'])
			},
																																											maintenance_time_date_range_picker_confirm({ startDate, endDate }) {
				this.query.maintenance_time_min = startDate;
				this.query.maintenance_time_max = endDate;
				this.maintenance_time_date_text = `${startDate} - ${endDate}`;
				this.search_();
				this.maintenance_time_date_range_picker_show = false;
			},
			maintenance_time_date_select_clear() {
				this.query.maintenance_time_min = '';
				this.query.maintenance_time_max = '';
				this.maintenance_time_date_text = '请选择维修时间';
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
										                            /**
     * 获取商家用户用户列表
     */
    async get_list_user_business_user() {
      var json = await this.$get("~/api/user/get_list?user_group=商家用户");
      if(json.result && json.result.list){
        this.list_user_business_user = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },
    get_user_business_user(id){
      let obj = this.list_user_business_user;
      let ret = {nickname: "未知用户", avatar: ""};
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i];
        }
      }
      return ret;
    },
                    /**
     * 获取车主用户用户列表
     */
    async get_list_user_rescue_user() {
      var json = await this.$get("~/api/user/get_list?user_group=车主用户");
      if(json.result && json.result.list){
        this.list_user_rescue_user = json.result.list;
      }
      else if(json.error){
        console.error(json.error);
      }
    },
    get_user_rescue_user(id){
      let obj = this.list_user_rescue_user;
      let ret = {nickname: "未知用户", avatar: ""};
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i];
        }
      }
      return ret;
    },
                                        // 改变分类标签
        change_audit_status(val) {
          if (val === "全部" || val === "") {
            this.query.audit_status = "";
          }else{
            this.query.audit_status = val
          }
          this.search_();
        },
                /**
     * 获取审核状态列表
     */
    async get_list_audit_status() {
        		  this.list_audit_status = ['待审核','已审核'].map((o) => ({ 
			audit_status: o, 
			children: [] 
		  }));
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
	        	        	        	        	        	                case 'audit_status':
          this.popupTitle = '选择审核状态';
          this.initCascader(this.list_audit_status);
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
																																																																																			queryValue = lastItem.audit_status;
						selectedText = selected.map(item => item.audit_status).join(' / ');
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
            this.get_list_user_business_user();
                this.get_list_user_rescue_user();
                      // 初始化审核状态列表
    this.get_list_audit_status();
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