<template>
  <view class="page_diy_table page_vehicle_information_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">车辆信息列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
              <uni-forms-item label="车牌号码" name="license_plate_number">
                <uni-easyinput type="text" v-model="query.license_plate_number" placeholder="车牌号码" />
              </uni-forms-item>
              <uni-forms-item label="汽车类型" name="car_type">
                <view class="filter-item" @click="showFilter('car_type')">
              {{ filter_text.car_type || '请选择汽车类型' }}
            </view>
              </uni-forms-item>
                  <uni-forms-item label="审核状态" name="examine_state">
              <uni-data-select placeholder="请选择审核状态"
                               v-model="query['examine_state']"
                               :localdata="list_examine_state"
              ></uni-data-select>
            </uni-forms-item>
        </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/vehicle_information/view?')" v-if="$check_action('/vehicle_information/table', 'add') || $check_action('/vehicle_information/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="0 && $check_field('get','owner_user')">
            <view class="label" v-if="true">
              <span>车主用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_owner_user(o['owner_user']) }}</span>
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
          <view class="item-row " v-if="1 && $check_field('get','car_type')">
            <view class="label" v-if="true">
              <span>汽车类型</span>
            </view>
              <view class="value">
                              <span>{{ o['car_type'] }}</span>
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
          <view class="item-row " v-if="0 && $check_field('get','vehicle_remarks')">
            <view class="label" v-if="true">
              <span>车辆备注</span>
            </view>
              <view class="value">
                              <span>{{ o['vehicle_remarks'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/vehicle_information/table', 'del') || $check_action('/vehicle_information/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/vehicle_information/view?' + field + '=' + o[field])"
                  v-if="$check_action('/vehicle_information/table', 'set') || $check_action('/vehicle_information/view', 'set') || $check_action('/vehicle_information/view', 'get')">
              详情
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
                                                                                                                                                                                                                                                                        {{ item.model_name }}
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
      url_get_list: '~/api/vehicle_information/get_list?like=1',
      url_del: '~/api/vehicle_information/del',

      // 字段ID
      field: 'vehicle_information_id',
      // 查询
      query: {
        size: 7,
        page: 1,
                license_plate_number: "",
                    car_type: "",
                  "examine_state":"",
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
  	  	  			car_type: '',
	  	  	  			},
      // 数据
      list: [],
                  // 用户列表
      list_user_owner_user: [],
                              // 汽车类型列表
      list_car_type: [{value:"",text:"全部"}],
                                    list_examine_state: [
        { value: '', text: '全部' },
        { value: '未审核', text: '未审核' },
        { value: '已通过', text: '已通过' },
        { value: '未通过', text: '未通过' },
      ],
    };
  },
  methods: {
                    					    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
                        this.filter_text = {
                  		car_type: '',
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            res = await _this.$get("~/api/issuing_card_voucher/get_list?", {	
              license_plate_number: type.license_plate_number
            });
            if (res.result && res.result.list && res.result.list.length > 0) {
              _this.$toast('"' + type.license_plate_number + '"下有关联的内容，无法删除！');
              return;
            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              res = await _this.$get("~/api/reservation_record/get_list?", {	
              license_plate_number: type.license_plate_number
            });
            if (res.result && res.result.list && res.result.list.length > 0) {
              _this.$toast('"' + type.license_plate_number + '"下有关联的内容，无法删除！');
              return;
            }
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
     * 获取汽车类型列表
     */
    async get_list_car_type() {
                    let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                              var json = await this.$get("~/api/vehicle_class_nameification/get_list", param);
        if(json.result && json.result.list){
          if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
            json.result.list = json.result.list.filter(item => item.type == 1);
          }
          const hasFatherId = json.result.list.some(item => 'father_id' in item);
          if (hasFatherId) {
            this.list_car_type = this.buildTree(json.result.list, 0, 'vehicle_class_nameification_id');
          }else{
            this.list_car_type = json.result.list.map(item => ({
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
                                // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	                case 'car_type':
          this.popupTitle = '选择汽车类型';
          this.initCascader(this.list_car_type);
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
																																																queryValue = lastItem.model_name;
						selectedText = selected.map(item => item.model_name).join(' / ');
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
            this.get_list_user_owner_user();
            // 初始化汽车类型列表
    this.get_list_car_type();
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