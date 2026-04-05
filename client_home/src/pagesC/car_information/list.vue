<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }" class="page_diy_list page_car_information_list container">
	<view class="content">
	  <tn-nav-bar :isBack="isBack">汽车信息列表</tn-nav-bar>
	  <view class="page-list" id="page_diy_list">
	        <!-- 筛选模块(开始) -->
																								        <view class="search-wrap">
	  	    	  	    	      	          <Search v-model="query.car_name" placeholder="搜索汽车名称" @search="search_" @cancel="search_cancel('car_name')" @input="(val) => inputValue(val, 'car_name')" />
	      	    	  	    	  	    	      	    	  	    	  	    	      	    	  	    	  	    	  	    	  	    	  	    	  	    	  	  	    	  	    	      	    	  	    	  	    	      		  	  <view class="drop_down">
	        	<view class="filter-item" @click="showFilter('car_models')">
					{{ filter_text.car_models || '请选择汽车车型' }}
				</view>
	          </view>
	      	    	  	    	  	    	      		  	  <view class="drop_down">
	        	<view class="filter-item" @click="showFilter('new_and_old_car')">
					{{ filter_text.new_and_old_car || '请选择汽车新旧' }}
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
	        <view class="customized-list">
	  	          <view v-for="(o, i) in showList" :key="i" class="customized-item">
	  	  	            <view
	              class="customized-item-body"
	              @click="toDetails(o)">
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'car_coding')">
	                <view class="label" v-if="true">
	                  <span>汽车编码</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_coding'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  diy-main-title " v-if="1 && $check_field('get', 'car_name')">
	                <view class="label" v-if="true">
	                  <span>汽车名称</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_name'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row diy-image " v-if="1 && $check_field('get', 'car_condition_picture')">
	                <view class="label" v-if=" false">
	                  <span>车况图片</span>
	                </view>
	    	                <view class="value diy_img">
	                  <image :src="$fullImgUrl(o['car_condition_picture'])" width="100%" height="100" />
	                </view>
	    	              </view>
	  	              <view class="item-row  diy-tag-type " v-if="1 && $check_field('get', 'car_models')">
	                <view class="label" v-if="true">
	                  <span>汽车车型</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_models'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'car_brand')">
	                <view class="label" v-if="true">
	                  <span>汽车品牌</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_brand'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'new_and_old_car')">
	                <view class="label" v-if="true">
	                  <span>汽车新旧</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['new_and_old_car'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="1 && $check_field('get', 'car_price')">
	                <view class="label" v-if="true">
	                  <span>汽车价格</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_price'] }}元</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'car_status')">
	                <view class="label" v-if="true">
	                  <span>汽车状态</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['car_status'] }}</span>
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
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'store_location')">
	                <view class="label" v-if="true">
	                  <span>门店位置</span>
	                </view>
	    	                <view class="value">
											                  <span>{{ o['store_location'] }}</span>
											                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'car_configuration')">
	                <view class="label" v-if="true">
	                  <span>汽车配置</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['car_configuration'] }}</span>
	                </view>
	    	              </view>
	  	              <view class="item-row  " v-if="0 && $check_field('get', 'car_specificss')">
	                <view class="label" v-if="true">
	                  <span>汽车详情</span>
	                </view>
	    	                <view class="value">
	                  <span>{{ o['car_specificss'] }}</span>
	                </view>
	    	              </view>
	  	  			            </view>
	  
	  	            <view class="customized-item-footer">
	    	              <view class="praise">
	                <text class="icon iconfont icon-dianzan"></text>
	                {{ o['praise_len'] || 0 }}
	              </view>
	    	    	              <view class="collect">
	                <text class="icon iconfont icon-shoucang"></text>
	                {{ o['collect_len'] || 0 }}
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
																																																																																																																																																																																																																									{{ item.model_name || item.new_and_old_car }}
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
export default {
  mixins: [mixin],
  components: {
    Search,
    MeDropdown,
																								  },
  data() {
    return {
	  isBack: true,
      url_get_list: '~/api/car_information/get_list?like=1',
                          // 汽车车型选项列表
      list_car_models: [{value:"全部",text:"全部"}],
	  placeholder_car_models: "请选择汽车车型",
                    // 汽车新旧选项列表
      list_new_and_old_car: [{value:"全部",text:"全部"}],
	  placeholder_new_and_old_car: "请选择汽车新旧",
                              // 用户列表
      list_user_business_user: [],
                        													  currentFilterType: '', // 当前筛选类型
	  popupTitle: '',
	  // 选择器数据
	  cascaderList: [],
	  cascaderColumns: [[], [], []],
	  cascaderIndex: [0, 0, 0],
	  // 显示文本
	  filter_text: {
  	  	  	  			car_models: '',
	  	  			new_and_old_car: '',
	  	  	  	  	  	  	  			},
      query: {
                  car_name: "", // 汽车名称
                          car_models: "", // 汽车车型
                          new_and_old_car: "", // 汽车新旧
                            car_information_id: 0, // ID
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
            title: '收藏数',
            type: 'sort',
            command: '`collect_len`',
            value: 0,
        },
        {
            title: '发布时间',
            type: 'sort',
            command: '`create_time`',
            value: 0,
        }
      ],
						 									 									 									 									 									 									 			car_price_input_timer: null,
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
						this.$navTo('/pagesC/car_information/details?car_information_id=' + o['car_information_id'])
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
		this.$get('~/api/praise/list_group?groupby=source_id&source_table=car_information',{},(res) => {
		    if (res.result && res.result.list) {
		      res.result.list.map((o) => {
		        for (var i = 0; i < list.length; i++) {
		          var oj = list[i];
		          if (oj['car_information_id'] === o['source_id']) {
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
									car_price_input() {
			this.car_price_input_timer && clearTimeout(this.car_price_input_timer);
			this.car_price_input_timer = setTimeout(() => {
				if((this.query.car_price_min && this.query.car_price_max) || (!this.query.car_price_min && !this.query.car_price_max)) {
					this.search_();
				}
			}, 300);
		},
						                                                // 改变分类标签
        change_car_models(val) {
          if (val === "全部" || val === "") {
            this.query.car_models = "";
          }else{
            this.query.car_models = val
          }
          this.search_();
        },
                /**
     * 获取汽车车型列表
     */
    async get_list_car_models() {
                          var json = await this.$get("~/api/vehicle_class_nameification/get_list?");
          if(json.result && json.result.list){
            if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
              json.result.list = json.result.list.filter(item => item.type == 1);
            }
		  	const hasFatherId = json.result.list.some(item => 'father_id' in item);
			if (hasFatherId) {
				this.list_car_models = this.buildTree(json.result.list, 0, 'vehicle_class_nameification_id');
			}else{
            	this.list_car_models = json.result.list.map(item => ({
					model_name: item.model_name,
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
	                            // 改变分类标签
        change_new_and_old_car(val) {
          if (val === "全部" || val === "") {
            this.query.new_and_old_car = "";
          }else{
            this.query.new_and_old_car = val
          }
          this.search_();
        },
                /**
     * 获取汽车新旧列表
     */
    async get_list_new_and_old_car() {
        		  this.list_new_and_old_car = ['新车','二手车'].map((o) => ({ 
			new_and_old_car: o, 
			children: [] 
		  }));
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
    // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	                case 'car_models':
          this.popupTitle = '选择汽车车型';
          this.initCascader(this.list_car_models);
          break;
        	        	                case 'new_and_old_car':
          this.popupTitle = '选择汽车新旧';
          this.initCascader(this.list_new_and_old_car);
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
																																																																																																					queryValue = lastItem.model_name || lastItem.new_and_old_car;
						selectedText = selected.map(item => item.model_name || item.new_and_old_car).join(' / ');
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
                  // 初始化汽车车型列表
    this.get_list_car_models();
              // 初始化汽车新旧列表
    this.get_list_new_and_old_car();
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