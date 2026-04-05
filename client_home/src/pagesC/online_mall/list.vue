<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_online_mall_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">网上商城列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																								        <view class="search-wrap">
	  	    	  	    	      	          <Search v-model="query.commodity_specifications" placeholder="搜索商品规格" @search="search_" @cancel="search_cancel('commodity_specifications')" @input="(val) => inputValue(val, 'commodity_specifications')" />
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
	        <view class="customized-list">
	  	          <view v-for="(o, i) in showList" :key="i" class="customized-item">
	  	  	            <view
	              class="customized-item-body"
	              @click="toDetails(o)">
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'business_user')">
	                <view class="label" v-if="true">
	                  <span>商家用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_business_user(o['business_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_business_user(o['business_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'commodity_specifications')">
	                <view class="label" v-if="true">
	                  <span>商品规格</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['commodity_specifications'] }}</span>
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
							    		</view>
</template>

<script>
import Search from '@/components/businessCp/search.vue';
import mixin from '@/libs/mixins/page.js';
import MeDropdown from '@/components/me-dropdown/index.vue';
export default {
  mixins: [mixin],
  components: {
    Search,
    MeDropdown,
				  },
  data() {
    return {
	  isBack: true,
      url_get_list: '~/api/online_mall/get_list?like=1',
            // 用户列表
      list_user_business_user: [],
            		      query: {
                  commodity_specifications: "", // 商品规格
                online_mall_id: 0, // ID
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
						 									 						    };
  },

  watch: {
  	list: {
  		handler(val) {
								this.showList = val;
				  		},
  		deep: true
  	},
  },
  methods: {
	toDetails(o) {
						this.$navTo('/pagesC/online_mall/details?online_mall_id=' + o['online_mall_id'])
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
        this.get_list_user_business_user();
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