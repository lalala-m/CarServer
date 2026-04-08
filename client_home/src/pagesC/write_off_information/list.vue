<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_write_off_information_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">核销信息列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																													        <view class="search-wrap">
	  	    	      	          <Search v-model="query.card_code" placeholder="搜索卡券编码" @search="search_" @cancel="search_cancel('card_code')" @input="(val) => inputValue(val, 'card_code')" />
	      	    	  	    	  	    	  	    	  	    	      	          <Search v-model="query.card_name" placeholder="搜索卡券名称" @search="search_" @cancel="search_cancel('card_name')" @input="(val) => inputValue(val, 'card_name')" />
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
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'card_code')">
	                <view class="label" v-if="true">
	                  <span>卡券编码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['card_code'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'manager_user')">
	                <view class="label" v-if="true">
	                  <span>经理用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_manager_user(o['manager_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_manager_user(o['manager_user']).nickname }}</span>
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
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'financial_user')">
	                <view class="label" v-if="true">
	                  <span>财务用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_financial_user(o['financial_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_financial_user(o['financial_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'card_name')">
	                <view class="label" v-if="true">
	                  <span>卡券名称</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['card_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'effective_period')">
	                <view class="label" v-if="true">
	                  <span>有效期限</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['effective_period'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'number_of_uses')">
	                <view class="label" v-if="true">
	                  <span>使用次数</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['number_of_uses'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'issuing_time')">
	                <view class="label" v-if="true">
	                  <span>发放时间</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['issuing_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'write_off_time')">
	                <view class="label" v-if="true">
	                  <span>核销时间</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['write_off_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row diy-image " v-if="0 && $check_field('get', 'write_off_doc')">
	                <view class="label" v-if=" false">
	                  <span>核销单</span>
	                </view>
	    	                <view class="value diy_img">
	                  <image :src="$fullImgUrl(o['write_off_doc'])" width="100%" height="100" />
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'write_off_remarks')">
	                <view class="label" v-if="true">
	                  <span>核销备注</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['write_off_remarks'] }}</span>
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
				    														    											<view class="popup-mask" v-if="issuing_time_date_range_picker_show"></view>
    <view class="date-selector-popup" :class="{ 'popup-show': issuing_time_date_range_picker_show }">
      <view class="popup-header">
        <view class="popup-title">选择发放时间</view>
      </view>
      <view class="popup-content">
        <DateSelector
          :mode="4"
					:defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
					@onSubmit="issuing_time_date_range_picker_confirm"
					@onCancel="issuing_time_date_range_picker_show = false"
        />
      </view>
    </view>
						<view class="popup-mask" v-if="write_off_time_date_range_picker_show"></view>
    <view class="date-selector-popup" :class="{ 'popup-show': write_off_time_date_range_picker_show }">
      <view class="popup-header">
        <view class="popup-title">选择核销时间</view>
      </view>
      <view class="popup-content">
        <DateSelector
          :mode="4"
					:defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
					@onSubmit="write_off_time_date_range_picker_confirm"
					@onCancel="write_off_time_date_range_picker_show = false"
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
      url_get_list: '~/api/write_off_information/get_list?like=1',
                  // 用户列表
      list_user_manager_user: [],
                  // 用户列表
      list_user_owner_user: [],
                  // 用户列表
      list_user_financial_user: [],
                                                											      query: {
                card_code: "", // 卡券编码
                              card_name: "", // 卡券名称
                            write_off_information_id: 0, // ID
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
						 									 									 									 									 									 									 									 						issuing_time_date_range_picker_show: false,
      issuing_time_date_text: '请选择发放时间',
										 						write_off_time_date_range_picker_show: false,
      write_off_time_date_text: '请选择核销时间',
										 									 						    };
  },
	computed: {
		// 判断列表中是否有图片项
		hasImageItems() {
			return this.showList.some(item => {
																																																																																																if (item['write_off_doc'] && item['write_off_doc'] !== '') {
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
						this.$navTo('/pagesC/write_off_information/details?write_off_information_id=' + o['write_off_information_id'])
			},
																																											issuing_time_date_range_picker_confirm({ startDate, endDate }) {
				this.query.issuing_time_min = startDate;
				this.query.issuing_time_max = endDate;
				this.issuing_time_date_text = `${startDate} - ${endDate}`;
				this.search_();
				this.issuing_time_date_range_picker_show = false;
			},
			issuing_time_date_select_clear() {
				this.query.issuing_time_min = '';
				this.query.issuing_time_max = '';
				this.issuing_time_date_text = '请选择发放时间';
				this.search_();
			},
										write_off_time_date_range_picker_confirm({ startDate, endDate }) {
				this.query.write_off_time_min = startDate;
				this.query.write_off_time_max = endDate;
				this.write_off_time_date_text = `${startDate} - ${endDate}`;
				this.search_();
				this.write_off_time_date_range_picker_show = false;
			},
			write_off_time_date_select_clear() {
				this.query.write_off_time_min = '';
				this.query.write_off_time_max = '';
				this.write_off_time_date_text = '请选择核销时间';
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
	inputValue(val, key) {
	  this.query[key] = val;
	  this.search_();
	},
		  },
  created() {
            this.get_list_user_manager_user();
                this.get_list_user_owner_user();
                this.get_list_user_financial_user();
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
.uni-popup.bottom{
  z-index: 9998;
}
</style>