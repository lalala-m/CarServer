<template>
  <view class="page_diy_table page_issuing_card_voucher_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">发放卡券列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="卡券编码" name="card_code">
                <uni-easyinput type="text" v-model="query.card_code" placeholder="卡券编码" />
              </uni-forms-item>
                  <uni-forms-item label="车牌号码" name="license_plate_number">
                <view class="filter-item" @click="showFilter('license_plate_number')">
              {{ filter_text.license_plate_number || '请选择车牌号码' }}
            </view>
              </uni-forms-item>
                <uni-forms-item label="卡券名称" name="card_name">
                <view class="filter-item" @click="showFilter('card_name')">
              {{ filter_text.card_name || '请选择卡券名称' }}
            </view>
              </uni-forms-item>
                    <uni-forms-item label="核销状态" name="write_off_status">
                <view class="filter-item" @click="showFilter('write_off_status')">
              {{ filter_text.write_off_status || '请选择核销状态' }}
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
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/issuing_card_voucher/view?')" v-if="$check_action('/issuing_card_voucher/table', 'add') || $check_action('/issuing_card_voucher/view', 'add')">添加</view>
		          <Analysis mode="anlDialog" :content="list" />
        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','card_code')">
            <view class="label" v-if="true">
              <span>卡券编码</span>
            </view>
              <view class="value">
                              <span>{{ o['card_code'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','manager_user')">
            <view class="label" v-if="true">
              <span>经理用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_manager_user(o['manager_user']) }}</span>
            </view>
            </view>
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
          <view class="item-row " v-if="0 && $check_field('get','financial_user')">
            <view class="label" v-if="true">
              <span>财务用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_financial_user(o['financial_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','card_name')">
            <view class="label" v-if="true">
              <span>卡券名称</span>
            </view>
              <view class="value">
                              <span>{{ o['card_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','effective_period')">
            <view class="label" v-if="true">
              <span>有效期限</span>
            </view>
              <view class="value">
                              <span>{{ o['effective_period'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','number_of_uses')">
            <view class="label" v-if="true">
              <span>使用次数</span>
            </view>
              <view class="value">
                              <span>{{ o['number_of_uses'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','issuing_time')">
            <view class="label" v-if="true">
              <span>发放时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['issuing_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','write_off_status')">
            <view class="label" v-if="true">
              <span>核销状态</span>
            </view>
              <view class="value">
                              <span>{{ o['write_off_status'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/issuing_card_voucher/table', 'del') || $check_action('/issuing_card_voucher/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/issuing_card_voucher/view?' + field + '=' + o[field])"
                  v-if="$check_action('/issuing_card_voucher/table', 'set') || $check_action('/issuing_card_voucher/view', 'set') || $check_action('/issuing_card_voucher/view', 'get')">
              详情
            </view>
      <!--跨表按钮-->
					      <view width="100%" class="me-btn btn-pay"
		            v-if="$check_action('/write_off_information/view', 'add') && o.examine_state == '已通过' && !o.write_off_information_limit && !o.write_off_information_status_limit"
		            @click="to_form('/pagesC/write_off_information/view', o)">
		        <span>卡券核销</span>
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              {{ item.license_plate_number || item.card_name || item.write_off_status }}
                </view>
              </picker-view-column>
            </picker-view>

            <view class="popup-footer">
              <button class="confirm-btn" @click="confirmFilter">确定</button>
            </view>
          </view>
        </uni-popup>
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
      url_get_list: '~/api/issuing_card_voucher/get_list?like=1',
      url_del: '~/api/issuing_card_voucher/del',

      // 字段ID
      field: 'issuing_card_voucher_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              card_code: "",
                        license_plate_number: "",
                      card_name: "",
                          write_off_status: "",
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
  	  	  	  			license_plate_number: '',
	  	  			card_name: '',
	  	  	  	  			write_off_status: '',
	  			},
      // 数据
      list: [],
                            // 用户列表
      list_user_manager_user: [],
                          // 用户列表
      list_user_owner_user: [],
                    // 车牌号码列表
      list_license_plate_number: [{value:"",text:"全部"}],
                            // 用户列表
      list_user_financial_user: [],
                    // 卡券名称列表
      list_card_name: [{value:"",text:"全部"}],
                                            issuing_time_date_range_picker_show: false,
    issuing_time_date_text: '请选择发放时间',
              // 核销状态列表
      list_write_off_status: [{value:"",text:"全部"}],
                list_examine_state: [
        { value: '', text: '全部' },
        { value: '未审核', text: '未审核' },
        { value: '已通过', text: '已通过' },
        { value: '未通过', text: '未通过' },
      ],
    };
  },
  methods: {
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
      										    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
                                        this.issuing_time_date_range_picker_show = false;
    this.issuing_time_date_text = '请选择发放时间';
          this.filter_text = {
                        		license_plate_number: '',
	              		card_name: '',
	                          		write_off_status: '',
	        };
      uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
                            if(user_group=="经理用户"){
          sqlwhere+= "manager_user = " + this.user.user_id + " or ";
        }
                        if(user_group=="车主用户"){
          sqlwhere+= "owner_user = " + this.user.user_id + " or ";
        }
                                  if(user_group=="财务用户"){
          sqlwhere+= "financial_user = " + this.user.user_id + " or ";
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
				_this.$set(item, 'write_off_information_status_limit', false);
				                              _this.$get("~/api/issuing_card_voucher/get_list?card_code=" + item.card_code + "&orderby=create_time desc", {}, (res1) => {
            if(res1.result && res1.result.list && res1.result.list.length > 0) {
              const latestRecord = res1.result.list[0];
              if(latestRecord.write_off_status != '待核销') {
                _this.$set(item, 'write_off_information_status_limit', true);
              }
            }
          })
							})
							_this.list.map((item) => {
				_this.$set(item, '_status_limit', false);
							})
		    				  _this.list.map((item) => {
			let param = {
			  source_table: "issuing_card_voucher",
			  source_id: item.issuing_card_voucher_id,
			  source_user_id: _this.user.user_id
			};
			if(item.write_off_information_limit_times > 0){
			  _this.$get("~/api/write_off_information/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.write_off_information_limit_times){
			  		_this.$set(item,'write_off_information_limit',true);
			  	  }else{
			  		_this.$set(item,'write_off_information_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'write_off_information_limit',false);
			}
			Object.assign(item, param)
		  })
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
     * 获取车牌号码列表
     */
    async get_list_license_plate_number() {
                    let param = {}
                                                                                                                      var user_group = this.user.user_group;
                          let sqlwhere = "(";
                                                if(user_group=="车主用户"){
                    sqlwhere+= "owner_user = " + this.user.user_id + " or ";
                  }
                                                                                                                                                                    if (sqlwhere.length>1){
                sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
                sqlwhere += ")";
                param["sqlwhere"] = sqlwhere;
              }
                                                                                                                                                                                                                                                                                                                                                                                                                                                  var json = await this.$get("~/api/vehicle_information/get_list", param);
        if(json.result && json.result.list){
          if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
            json.result.list = json.result.list.filter(item => item.type == 1);
          }
          const hasFatherId = json.result.list.some(item => 'father_id' in item);
          if (hasFatherId) {
            this.list_license_plate_number = this.buildTree(json.result.list, 0, 'vehicle_information_id');
          }else{
            this.list_license_plate_number = json.result.list.map(item => ({
              license_plate_number: item.license_plate_number,
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
      let ret = "";
      for(let i=0;i<obj.length;i++){
        if(obj[i].user_id==id){
          ret = obj[i].nickname+"-"+obj[i].username;
        }
      }
      return ret;
    },
          /**
     * 获取卡券名称列表
     */
    async get_list_card_name() {
                    let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        var json = await this.$get("~/api/card_type/get_list", param);
        if(json.result && json.result.list){
          if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
            json.result.list = json.result.list.filter(item => item.type == 1);
          }
          const hasFatherId = json.result.list.some(item => 'father_id' in item);
          if (hasFatherId) {
            this.list_card_name = this.buildTree(json.result.list, 0, 'card_type_id');
          }else{
            this.list_card_name = json.result.list.map(item => ({
              card_name: item.card_name,
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
     * 获取核销状态列表
     */
    async get_list_write_off_status() {
              this.list_write_off_status = ['待核销'].map((o) => ({ 
          write_off_status: o, 
          children: [] 
        }));
                },
                // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	                case 'license_plate_number':
          this.popupTitle = '选择车牌号码';
          this.initCascader(this.list_license_plate_number);
          break;
        	        	                case 'card_name':
          this.popupTitle = '选择卡券名称';
          this.initCascader(this.list_card_name);
          break;
        	        	        	        	                case 'write_off_status':
          this.popupTitle = '选择核销状态';
          this.initCascader(this.list_write_off_status);
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
																																																																																											queryValue = lastItem.license_plate_number || lastItem.card_name || lastItem.write_off_status;
						selectedText = selected.map(item => item.license_plate_number || item.card_name || item.write_off_status).join(' / ');
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
                this.get_list_user_manager_user();
              this.get_list_user_owner_user();
        // 初始化车牌号码列表
    this.get_list_license_plate_number();
                this.get_list_user_financial_user();
        // 初始化卡券名称列表
    this.get_list_card_name();
                      // 初始化核销状态列表
    this.get_list_write_off_status();
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