<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_maintenance_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">维修养护列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																								        <view class="search-wrap">
	  	    	      	          <Search v-model="query.project_bundle" placeholder="搜索项目套餐" @search="search_" @cancel="search_cancel('project_bundle')" @input="(val) => inputValue(val, 'project_bundle')" />
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
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'project_bundle')">
	                <view class="label" v-if="true">
	                  <span>项目套餐</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['project_bundle'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row diy-image " v-if="1 && $check_field('get', 'project_picture')">
	                <view class="label" v-if=" false">
	                  <span>项目图片</span>
	                </view>
	    	                <view class="value diy_img">
	                  <image :src="$fullImgUrl(o['project_picture'])" width="100%" height="100" />
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'project_price')">
	                <view class="label" v-if="true">
	                  <span>项目价格</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['project_price'] }}元</span>
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
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'financial_user')">
	                <view class="label" v-if="true">
	                  <span>财务用户</span>
	                </view>
	    	                <view class="value">
	                  <image class="diy-create-avatar" style="width: 20px; height: 20px;" :src="$fullImgUrl(get_user_financial_user(o['financial_user']).avatar) || '/static/img/default.png'" mode="scaleToFill" />
	                  <span>{{ get_user_financial_user(o['financial_user']).nickname }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'project_description')">
	                <view class="label" v-if="true">
	                  <span>项目说明</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['project_description'] }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'project_content')">
	                <view class="label" v-if="true">
	                  <span>项目内容</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['project_content'] }}</span>
	                </view>
	    	              </view>
	  	  			            </view>
	  
	  	            <view class="customized-item-footer">
	    	              <view class="praise">
	                <text class="icon iconfont icon-dianzan"></text>
	                {{ o['praise_len'] || 0 }}
	              </view>
	    	    	    	              <view class="comment">
	                <text class="icon iconfont icon-pinglun"></text>
	                {{ o['comment_len'] || 0 }}
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
      url_get_list: '~/api/maintenance/get_list?like=1',
                              // 用户列表
      list_user_manager_user: [],
                  // 用户列表
      list_user_financial_user: [],
                  							      query: {
                project_bundle: "", // 项目套餐
                            maintenance_id: 0, // ID
        page: 1,
        size: 10,
      },
      list: [],
			showList: [],
      count: 50,
      menuList: [
                      {
            title: '点赞数',
            type: 'sort',
            command: '`praise_len`',
            value: 0,
        },
        {
            title: '点击数',
            type: 'sort',
            command: '`hits`',
            value: 0,
        },
        {
            title: '发布时间',
            type: 'sort',
            command: '`create_time`',
            value: 0,
        }
      ],
						 									 									 			project_price_input_timer: null,
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
						this.$navTo('/pagesC/maintenance/details?maintenance_id=' + o['maintenance_id'])
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
      this.get_praise(list);
      if (func) {
        func(json);
      }
    },
    /**
     * 获取点赞数
     * @param {Object} list
     */
    get_praise(list) {
		for (let i = 0; i < list.length; i++) {
			list[i].praise_len = 0;
		}
		this.$get('~/api/praise/list_group?groupby=source_id&source_table=maintenance',{},(res) => {
		    if (res.result && res.result.list) {
		      res.result.list.map((o) => {
		        for (var i = 0; i < list.length; i++) {
		          var oj = list[i];
		          if (oj['maintenance_id'] === o['source_id']) {
		            oj['praise_len'] = o['count'];
		            // break;
		          }
		        }
		      });
		    } else if (res.error) {
		      console.error(res.error);
		    }
		  }
		);
    },
					project_price_input() {
			this.project_price_input_timer && clearTimeout(this.project_price_input_timer);
			this.project_price_input_timer = setTimeout(() => {
				if((this.query.project_price_min && this.query.project_price_max) || (!this.query.project_price_min && !this.query.project_price_max)) {
					this.search_();
				}
			}, 300);
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