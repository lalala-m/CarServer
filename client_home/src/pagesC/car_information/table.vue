<template>
  <view class="page_diy_table page_car_information_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">汽车信息列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
              <uni-forms-item label="汽车名称" name="car_name">
                <uni-easyinput type="text" v-model="query.car_name" placeholder="汽车名称" />
              </uni-forms-item>
                <uni-forms-item label="汽车车型" name="car_models">
                <view class="filter-item" @click="showFilter('car_models')">
              {{ filter_text.car_models || '请选择汽车车型' }}
            </view>
              </uni-forms-item>
                <uni-forms-item label="汽车新旧" name="new_and_old_car">
                <view class="filter-item" @click="showFilter('new_and_old_car')">
              {{ filter_text.new_and_old_car || '请选择汽车新旧' }}
            </view>
              </uni-forms-item>
                      </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/car_information/view?')" v-if="$check_action('/car_information/table', 'add') || $check_action('/car_information/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="0 && $check_field('get','car_coding')">
            <view class="label" v-if="true">
              <span>汽车编码</span>
            </view>
              <view class="value">
                              <span>{{ o['car_coding'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','car_name')">
            <view class="label" v-if="true">
              <span>汽车名称</span>
            </view>
              <view class="value">
                              <span>{{ o['car_name'] }}</span>
                          </view>
            </view>
          <view class="item-row diy-image" v-if="1 && $check_field('get','car_condition_picture')">
            <view class="label" v-if=" false">
              <span>车况图片</span>
            </view>
              <view class="value diy_img">
              <image :src="$fullImgUrl(o['car_condition_picture'])" width="100%" height="100" />
            </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','car_models')">
            <view class="label" v-if="true">
              <span>汽车车型</span>
            </view>
              <view class="value">
                              <span>{{ o['car_models'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','car_brand')">
            <view class="label" v-if="true">
              <span>汽车品牌</span>
            </view>
              <view class="value">
                              <span>{{ o['car_brand'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','new_and_old_car')">
            <view class="label" v-if="true">
              <span>汽车新旧</span>
            </view>
              <view class="value">
                              <span>{{ o['new_and_old_car'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','car_price')">
            <view class="label" v-if="true">
              <span>汽车价格</span>
            </view>
              <view class="value">
                              <span>{{ o['car_price'] }}元</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','car_status')">
            <view class="label" v-if="true">
              <span>汽车状态</span>
            </view>
              <view class="value">
                              <span>{{ o['car_status'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','business_user')">
            <view class="label" v-if="true">
              <span>商家用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_business_user(o['business_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','store_location')">
            <view class="label" v-if="true">
              <span>门店位置</span>
            </view>
              <view class="value">
                              <span>{{ o['store_location'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','car_configuration')">
            <view class="label" v-if="true">
              <span>汽车配置</span>
            </view>
              <view class="value">
                              <span>{{ o['car_configuration'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','car_specificss')">
            <view class="label" v-if="true">
              <span>汽车详情</span>
            </view>
              <view class="value">
                              <span>{{ o['car_specificss'] }}</span>
                          </view>
            </view>
          <view class="content">
            <view class="item-row">
              <view class="value">点赞数{{ o['praise_len'] }}</view>
            </view>
            <view class="item-row">
              <view class="value">点击数{{ o['hits'] }}</view>
            </view>
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/car_information/table', 'del') || $check_action('/car_information/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/car_information/view?' + field + '=' + o[field])"
                  v-if="$check_action('/car_information/table', 'set') || $check_action('/car_information/view', 'set') || $check_action('/car_information/view', 'get')">
              详情
            </view>
      <!--跨表按钮-->
			              <view width="100%" class="me-btn btn-pay"
                    v-if="$check_action('/test_drive_record/view', 'add') && !o.test_drive_record_limit && !o.test_drive_record_status_limit"
                    @click="to_form('/pagesC/test_drive_record/view', o)">
                <span>试驾</span>
              </view>
		              <view width="100%" class="me-btn btn-pay"
                    v-if="$check_action('/purchase_order/view', 'add') && !o.purchase_order_limit && !o.purchase_order_status_limit"
                    @click="to_form('/pagesC/purchase_order/view', o)">
                <span>购买</span>
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
      url_get_list: '~/api/car_information/get_list?like=1',
      url_del: '~/api/car_information/del',

      // 字段ID
      field: 'car_information_id',
      // 查询
      query: {
        size: 7,
        page: 1,
                car_name: "",
                      car_models: "",
                      new_and_old_car: "",
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
  	  	  	  			car_models: '',
	  	  			new_and_old_car: '',
	  	  	  	  	  	  	  			},
      // 数据
      list: [],
                                          // 汽车车型列表
      list_car_models: [{value:"",text:"全部"}],
                                // 汽车新旧列表
      list_new_and_old_car: [{value:"",text:"全部"}],
                  	  car_price_input_timer: null,
                                // 用户列表
      list_user_business_user: [],
                                          };
  },
  methods: {
                                                									car_price_input() {
			this.car_price_input_timer && clearTimeout(this.car_price_input_timer);
			this.car_price_input_timer = setTimeout(() => {
				if((this.query.car_price_min && this.query.car_price_max) || (!this.query.car_price_min && !this.query.car_price_max)) {
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
                                                    this.filter_text = {
                        		car_models: '',
	              		new_and_old_car: '',
	                                            };
      uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
                                                                                                  if(user_group=="商家用户"){
          sqlwhere+= "business_user = " + this.user.user_id + " or ";
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
		      			_this.list.map((item) => {
				_this.$set(item, 'test_drive_record_status_limit', false);
								_this.list.map((item) => {
					_this.$set(item, 'test_drive_record_status_limit', false);
										_this.$get("~/api/car_information/get_list?car_coding=" + item.car_coding + "&orderby=create_time desc", {}, (res1) => {
						if(res1.result && res1.result.list && res1.result.list.length > 0) {
							const latestRecord = res1.result.list[0];
							if(latestRecord.car_status != '空闲中') {
								_this.$set(item, 'test_drive_record_status_limit', true);
							}
						}
					})
									})
							})
								  _this.list.map((item) => {
			let param = {
			  source_table: "car_information",
			  source_id: item.car_information_id,
			  source_user_id: _this.user.user_id
			};
			if(item.test_drive_record_limit_times > 0){
			  _this.$get("~/api/test_drive_record/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.test_drive_record_limit_times){
			  		_this.$set(item,'test_drive_record_limit',true);
			  	  }else{
			  		_this.$set(item,'test_drive_record_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'test_drive_record_limit',false);
			}
			Object.assign(item, param)
		  })
				  _this.list.map((item) => {
			let param = {
			  source_table: "car_information",
			  source_id: item.car_information_id,
			  source_user_id: _this.user.user_id
			};
			if(item.purchase_order_limit_times > 0){
			  _this.$get("~/api/purchase_order/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.purchase_order_limit_times){
			  		_this.$set(item,'purchase_order_limit',true);
			  	  }else{
			  		_this.$set(item,'purchase_order_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'purchase_order_limit',false);
			}
			Object.assign(item, param)
		  })
			  		},
                              /**
     * 获取汽车车型列表
     */
    async get_list_car_models() {
                    let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                              var json = await this.$get("~/api/vehicle_class_nameification/get_list", param);
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
	},
	hideFilter() {
		this.$refs.filterPopup.close();
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
</style>