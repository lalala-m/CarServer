<template>
  <view class="page_diy_table page_rescue_record_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">救援记录列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="救援编码" name="rescue_code">
                <uni-easyinput type="text" v-model="query.rescue_code" placeholder="救援编码" />
              </uni-forms-item>
                      <uni-forms-item label="审核状态" name="audit_status">
                <view class="filter-item" @click="showFilter('audit_status')">
              {{ filter_text.audit_status || '请选择审核状态' }}
            </view>
              </uni-forms-item>
                <uni-forms-item label="维修时间" name="maintenance_time">
    					<view class="date_select" @click="maintenance_time_date_range_picker_show = true">{{maintenance_time_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="maintenance_time_date_select_clear"/>
          </view>
              </uni-forms-item>
              </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/rescue_record/view?')" v-if="$check_action('/rescue_record/table', 'add') || $check_action('/rescue_record/view', 'add')">添加</view>
		          <Analysis mode="anlDialog" :content="list" />
        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','rescue_code')">
            <view class="label" v-if="true">
              <span>救援编码</span>
            </view>
              <view class="value">
                              <span>{{ o['rescue_code'] }}</span>
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
          <view class="item-row " v-if="0 && $check_field('get','rescue_user')">
            <view class="label" v-if="true">
              <span>救援用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_rescue_user(o['rescue_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','owners_name')">
            <view class="label" v-if="true">
              <span>车主姓名</span>
            </view>
              <view class="value">
                              <span>{{ o['owners_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','contact_number')">
            <view class="label" v-if="true">
              <span>联系号码</span>
            </view>
              <view class="value">
                              <span>{{ o['contact_number'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','audit_status')">
            <view class="label" v-if="true">
              <span>审核状态</span>
            </view>
              <view class="value">
                              <span>{{ o['audit_status'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','maintenance_personnel')">
            <view class="label" v-if="true">
              <span>维修人员</span>
            </view>
              <view class="value">
                              <span>{{ o['maintenance_personnel'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','maintenance_time')">
            <view class="label" v-if="true">
              <span>维修时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['maintenance_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
                          </view>
            </view>
          <view class="item-row diy-image" v-if="0 && $check_field('get','scene_picture')">
            <view class="label" v-if=" false">
              <span>现场图片</span>
            </view>
              <view class="value diy_img">
              <image :src="$fullImgUrl(o['scene_picture'])" width="100%" height="100" />
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','maintenance')">
            <view class="label" v-if="true">
              <span>维修情况</span>
            </view>
              <view class="value">
                              <span>{{ o['maintenance'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/rescue_record/table', 'del') || $check_action('/rescue_record/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/rescue_record/view?' + field + '=' + o[field])"
                  v-if="$check_action('/rescue_record/table', 'set') || $check_action('/rescue_record/view', 'set') || $check_action('/rescue_record/view', 'get')">
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
import mixin from '@/libs/mixins/page.js';
import dateRangePicker from '@/components/date-range-picker/date-range-picker.vue';
import DateSelector from '@/components/dengrq-datetime-picker/dateSelector/index.vue';
import Analysis from '@/pagesC/components/analysis/index.vue';

																												
export default {
  mixins: [mixin],
  components: {
    dateRangePicker,
    DateSelector,
    Analysis,
  },
  data() {
    return {
      // 获取数据地址
      url_get_list: '~/api/rescue_record/get_list?like=1',
      url_del: '~/api/rescue_record/del',

      // 字段ID
      field: 'rescue_record_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              rescue_code: "",
                            audit_status: "",
              				maintenance_time_min: '',
        maintenance_time_max: '',
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
  	  	  	  	  	  			audit_status: '',
	  	  	  	  	  			},
      // 数据
      list: [],
                            // 用户列表
      list_user_business_user: [],
                          // 用户列表
      list_user_rescue_user: [],
                                        // 审核状态列表
      list_audit_status: [{value:"",text:"全部"}],
                                  maintenance_time_date_range_picker_show: false,
    maintenance_time_date_text: '请选择维修时间',
                          };
  },
  methods: {
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
          										    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
                                    this.maintenance_time_date_range_picker_show = false;
    this.maintenance_time_date_text = '请选择维修时间';
              this.filter_text = {
                                    		audit_status: '',
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
                        if(user_group=="车主用户"){
          sqlwhere+= "rescue_user = " + this.user.user_id + " or ";
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
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
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
                                                // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	                case 'audit_status':
          this.popupTitle = '选择审核状态';
          this.initCascader(this.list_audit_status);
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
																																																																																			queryValue = lastItem.audit_status;
						selectedText = selected.map(item => item.audit_status).join(' / ');
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
</style>