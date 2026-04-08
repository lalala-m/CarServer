<template>
  <view class="page_diy_table page_test_drive_record_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">试驾记录列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="试驾编码" name="test_drive_code">
                <uni-easyinput type="text" v-model="query.test_drive_code" placeholder="试驾编码" />
              </uni-forms-item>
                <uni-forms-item label="汽车名称" name="car_name">
                <uni-easyinput type="text" v-model="query.car_name" placeholder="汽车名称" />
              </uni-forms-item>
              <uni-forms-item label="汽车车型" name="car_models">
                <uni-easyinput type="text" v-model="query.car_models" placeholder="汽车车型" />
              </uni-forms-item>
                  <uni-forms-item label="试驾时间" name="test_drive_time">
    					<view class="date_select" @click="test_drive_time_date_range_picker_show = true">{{test_drive_time_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="test_drive_time_date_select_clear"/>
          </view>
              </uni-forms-item>
            </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				          <Analysis mode="anlDialog" :content="list" />
        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','test_drive_code')">
            <view class="label" v-if="true">
              <span>试驾编码</span>
            </view>
              <view class="value">
                              <span>{{ o['test_drive_code'] }}</span>
                          </view>
            </view>
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
          <view class="item-row " v-if="0 && $check_field('get','car_models')">
            <view class="label" v-if="true">
              <span>汽车车型</span>
            </view>
              <view class="value">
                              <span>{{ o['car_models'] }}</span>
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
          <view class="item-row " v-if="0 && $check_field('get','owner_user')">
            <view class="label" v-if="true">
              <span>车主用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_owner_user(o['owner_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','test_drive_time')">
            <view class="label" v-if="true">
              <span>试驾时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['test_drive_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','test_drive_remarks')">
            <view class="label" v-if="true">
              <span>试驾备注</span>
            </view>
              <view class="value">
                              <span>{{ o['test_drive_remarks'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/test_drive_record/table', 'del') || $check_action('/test_drive_record/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/test_drive_record/view?' + field + '=' + o[field])"
                  v-if="$check_action('/test_drive_record/table', 'set') || $check_action('/test_drive_record/view', 'set') || $check_action('/test_drive_record/view', 'get')">
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
                                                                                                                                                                                                          <view class="popup-mask" v-if="test_drive_time_date_range_picker_show"></view>
        <view class="date-selector-popup" :class="{ 'popup-show': test_drive_time_date_range_picker_show }">
          <view class="popup-header">
            <view class="popup-title">选择试驾时间</view>
          </view>
          <view class="popup-content">
            <DateSelector
              :mode="4"
              :defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
              @onSubmit="test_drive_time_date_range_picker_confirm"
              @onCancel="test_drive_time_date_range_picker_show = false"
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
      url_get_list: '~/api/test_drive_record/get_list?like=1',
      url_del: '~/api/test_drive_record/del',

      // 字段ID
      field: 'test_drive_record_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              test_drive_code: "",
                      car_name: "",
                    car_models: "",
                				test_drive_time_min: '',
        test_drive_time_max: '',
                login_time: '',
        create_time: '',
      },
								      // 数据
      list: [],
                                                          // 用户列表
      list_user_business_user: [],
                          // 用户列表
      list_user_owner_user: [],
                      test_drive_time_date_range_picker_show: false,
    test_drive_time_date_text: '请选择试驾时间',
                };
  },
  methods: {
                                test_drive_time_date_range_picker_confirm({ startDate, endDate }) {
      this.query.test_drive_time_min = startDate;
      this.query.test_drive_time_max = endDate;
      this.test_drive_time_date_text = `${startDate} - ${endDate}`;
      this.search_();
      this.test_drive_time_date_range_picker_show = false;
    },
    test_drive_time_date_select_clear() {
      this.query.test_drive_time_min = '';
      this.query.test_drive_time_max = '';
      this.test_drive_time_date_text = '请选择试驾时间';
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
                                this.test_drive_time_date_range_picker_show = false;
    this.test_drive_time_date_text = '请选择试驾时间';
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
                      },
  created() {
                            this.get_list_user_business_user();
              this.get_list_user_owner_user();
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
</style>