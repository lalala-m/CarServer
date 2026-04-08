<template>
  <view class="page_diy_table page_warehouse_information_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">仓库信息列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="仓库名称" name="warehouse_name">
                <uni-easyinput type="text" v-model="query.warehouse_name" placeholder="仓库名称" />
              </uni-forms-item>
                <uni-forms-item label="默认状态" name="default_state">
                <view class="filter-item" @click="showFilter('default_state')">
              {{ filter_text.default_state || '请选择默认状态' }}
            </view>
              </uni-forms-item>
              </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/warehouse_information/view?')" v-if="$check_action('/warehouse_information/table', 'add') || $check_action('/warehouse_information/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','warehouse_name')">
            <view class="label" v-if="true">
              <span>仓库名称</span>
            </view>
              <view class="value">
                              <span>{{ o['warehouse_name'] }}</span>
                          </view>
            </view>
          <view class="item-row diy-image" v-if="0 && $check_field('get','warehouse_picture')">
            <view class="label" v-if=" false">
              <span>仓库图片</span>
            </view>
              <view class="value diy_img">
              <image :src="$fullImgUrl(o['warehouse_picture'])" width="100%" height="100" />
            </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','default_state')">
            <view class="label" v-if="true">
              <span>默认状态</span>
            </view>
              <view class="value">
                              <span>{{ o['default_state'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','warehouse_location')">
            <view class="label" v-if="true">
              <span>仓库位置</span>
            </view>
              <view class="value">
                              <span>{{ o['warehouse_location'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','warehouse_description')">
            <view class="label" v-if="true">
              <span>仓库描述</span>
            </view>
              <view class="value">
                              <span>{{ o['warehouse_description'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/warehouse_information/table', 'del') || $check_action('/warehouse_information/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/warehouse_information/view?' + field + '=' + o[field])"
                  v-if="$check_action('/warehouse_information/table', 'set') || $check_action('/warehouse_information/view', 'set') || $check_action('/warehouse_information/view', 'get')">
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
                                                                                                                                                                                                                                                                        {{ item.default_state }}
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
      url_get_list: '~/api/warehouse_information/get_list?like=1',
      url_del: '~/api/warehouse_information/del',

      // 字段ID
      field: 'warehouse_information_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              warehouse_name: "",
                      default_state: "",
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
  	  	  			default_state: '',
	  	  	  			},
      // 数据
      list: [],
                                // 默认状态列表
      list_default_state: [{value:"",text:"全部"}],
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
                  		default_state: '',
	                    };
      uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
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
     * 获取默认状态列表
     */
    async get_list_default_state() {
              this.list_default_state = ['否','是'].map((o) => ({ 
          default_state: o, 
          children: [] 
        }));
                },
                                // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	                case 'default_state':
          this.popupTitle = '选择默认状态';
          this.initCascader(this.list_default_state);
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
																																																queryValue = lastItem.default_state;
						selectedText = selected.map(item => item.default_state).join(' / ');
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
              // 初始化默认状态列表
    this.get_list_default_state();
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
.pay-model {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1000;
  
  .mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }
  
  .pay-model-inn {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background: #fff;
    border-radius: 20rpx 20rpx 0 0;
    padding: 40rpx 30rpx;
    box-sizing: border-box;
    max-height: 80vh;
    overflow-y: auto;
  }
}
.pay-info {
  .pay-info-item {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 30rpx;
    font-size: 28rpx;
    
    .pay-info-title {
      color: #666;
      width: 80px;
    }
    
    .pay-info-content {
      color: #999;
      text-align: right;
    }
  }
  
  .pay-amount {
    margin-top: 40rpx;
    padding-top: 30rpx;
    border-top: 1rpx solid #eee;
    border-bottom: 1rpx solid #eee;
    padding-bottom: 30rpx;
    
    .pay-info-amount {
      font-weight: bold;
      font-size: 36rpx;
      color: #f5222d;
    }
  }
}
.pay-type {
  margin-top: 40rpx;
  
  .pay-type-title {
    color: #999;
    margin-bottom: 20rpx;
    font-size: 28rpx;
  }
  
  .pay-type-list {
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    gap: 20rpx;
  }
  
  .pay-type-item {
    background: #f8f8f8;
    border: 2rpx solid #f8f8f8;
    border-radius: 10rpx;
    padding: 30rpx 20rpx;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    .pay-type-content{
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .pay-type-img {
      width: 40rpx;
      height: 40rpx;
      margin-right: 20px;
    }
    
    .pay-type-name {
      font-size: 28rpx;
    }
    
    .pay-type-remark {
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .pay-type-item-active {
    border-color: #2c70f6;
    background: #e8f0ff;
  }
}
.pay-btn-wrap {
  margin-top: 40rpx;
  display: flex;
  gap: 20rpx;
  
  .me-btn {
    flex: 1;
    height: 80rpx;
    line-height: 80rpx;
    border-radius: 10rpx;
    
    &.me-btn-plain {
      background: #fff;
      border: 1rpx solid #ddd;
      color: #333;
    }
  }
}
.pay-ebank {
  .pay-ebank-item {
    margin-top: 40rpx;
    
    .pay-ebank-title {
      margin-bottom: 20rpx;
      color: #666;
    }
    
    .pay-ebank-input {
      border: 1rpx solid #ddd;
      border-radius: 10rpx;
      padding: 20rpx;
      font-size: 28rpx;
      height: 50px;
    }
  }
}

.pay-image {
  width: 96%;
  margin: 0 auto;
  display: block;
}
</style>