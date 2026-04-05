<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_card_type_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">卡券类型列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																								        <view class="search-wrap">
	  	    	      	          <Search v-model="query.card_name" placeholder="搜索卡券名称" @search="search_" @cancel="search_cancel('card_name')" @input="(val) => inputValue(val, 'card_name')" />
	      	    	  	    	      	    	  	    	  	    	  	  	    	      	    	  	    	      					<view class="date_select" @click="effective_period_date_range_picker_show = true">{{effective_period_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="effective_period_date_select_clear"/>

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
	        <view class="customized-list">
	  	          <view v-for="(o, i) in showList" :key="i" class="customized-item">
	  	  	            <view
	              class="customized-item-body"
	              @click="toDetails(o)">
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'card_name')">
	                <view class="label" v-if="true">
	                  <span>卡券名称</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['card_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'effective_period')">
	                <view class="label" v-if="true">
	                  <span>有效期限</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ $toTime(o['effective_period'], 'yyyy-MM-dd') }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'card_content')">
	                <view class="label" v-if="true">
	                  <span>卡券内容</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['card_content'] }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'applicable_items')">
	                <view class="label" v-if="true">
	                  <span>适用项目</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['applicable_items'] }}</span>
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
      :show="effective_period_date_range_picker_show"
      :minYear="2022"
      @close="effective_period_date_range_picker_show=false"
      @confirm="effective_period_date_range_picker_confirm"
    />
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
      url_get_list: '~/api/card_type/get_list?like=1',
                        				      query: {
                card_name: "", // 卡券名称
                				effective_period_min: '',
        effective_period_max: '',
                    card_type_id: 0, // ID
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
						 									 						effective_period_date_range_picker_show: false,
      effective_period_date_text: '请选择有效期限',
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
						this.$navTo('/pagesC/card_type/details?card_type_id=' + o['card_type_id'])
			},
											effective_period_date_range_picker_confirm(date) {
				this.query.effective_period_min = date[0];
				this.query.effective_period_max = date[1];
				this.effective_period_date_text = `${date[0]} - ${date[1]}`;
				this.search_();
			},
			effective_period_date_select_clear() {
				this.query.effective_period_min = '';
				this.query.effective_period_max = '';
				this.effective_period_date_text = '请选择有效期限';
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