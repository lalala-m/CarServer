<template>
  <view class="page_diy_table page_outbound_record_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">出库记录列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="耗材编码" name="consumables_code">
                <uni-easyinput type="text" v-model="query.consumables_code" placeholder="耗材编码" />
              </uni-forms-item>
              <uni-forms-item label="耗材名称" name="consumables_name">
                <uni-easyinput type="text" v-model="query.consumables_name" placeholder="耗材名称" />
              </uni-forms-item>
                  <uni-forms-item label="出库时间" name="outbound_time">
    					<view class="date_select" @click="outbound_time_date_range_picker_show = true">{{outbound_time_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="outbound_time_date_select_clear"/>
          </view>
              </uni-forms-item>
              </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','consumables_code')">
            <view class="label" v-if="true">
              <span>耗材编码</span>
            </view>
              <view class="value">
                              <span>{{ o['consumables_code'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','consumables_name')">
            <view class="label" v-if="true">
              <span>耗材名称</span>
            </view>
              <view class="value">
                              <span>{{ o['consumables_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','consumables_specifications')">
            <view class="label" v-if="true">
              <span>耗材规格</span>
            </view>
              <view class="value">
                              <span>{{ o['consumables_specifications'] }}</span>
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
          <view class="item-row " v-if="1 && $check_field('get','outbound_time')">
            <view class="label" v-if="true">
              <span>出库时间</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['outbound_time'], 'yyyy-MM-dd hh:mm:ss') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','outbound_quantity')">
            <view class="label" v-if="true">
              <span>出库数量</span>
            </view>
              <view class="value">
                              <span>{{ o['outbound_quantity'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','outbound_note')">
            <view class="label" v-if="true">
              <span>出库备注</span>
            </view>
              <view class="value">
                              <span>{{ o['outbound_note'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/outbound_record/table', 'del') || $check_action('/outbound_record/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/outbound_record/view?' + field + '=' + o[field])"
                  v-if="$check_action('/outbound_record/table', 'set') || $check_action('/outbound_record/view', 'set') || $check_action('/outbound_record/view', 'get')">
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
                                                                                                                                                <view class="popup-mask" v-if="outbound_time_date_range_picker_show"></view>
        <view class="date-selector-popup" :class="{ 'popup-show': outbound_time_date_range_picker_show }">
          <view class="popup-header">
            <view class="popup-title">选择出库时间</view>
          </view>
          <view class="popup-content">
            <DateSelector
              :mode="4"
              :defaultStartDate="$toTime(new Date(), 'yyyy-MM-dd hh:mm:ss')"
              @onSubmit="outbound_time_date_range_picker_confirm"
              @onCancel="outbound_time_date_range_picker_show = false"
            />
          </view>
        </view>
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
      url_get_list: '~/api/outbound_record/get_list?like=1',
      url_del: '~/api/outbound_record/del',

      // 字段ID
      field: 'outbound_record_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              consumables_code: "",
                    consumables_name: "",
                				outbound_time_min: '',
        outbound_time_max: '',
                  login_time: '',
        create_time: '',
      },
							      // 数据
      list: [],
                                                // 用户列表
      list_user_financial_user: [],
                      outbound_time_date_range_picker_show: false,
    outbound_time_date_text: '请选择出库时间',
          	  outbound_quantity_input_timer: null,
                  };
  },
  methods: {
                        outbound_time_date_range_picker_confirm({ startDate, endDate }) {
      this.query.outbound_time_min = startDate;
      this.query.outbound_time_max = endDate;
      this.outbound_time_date_text = `${startDate} - ${endDate}`;
      this.search_();
      this.outbound_time_date_range_picker_show = false;
    },
    outbound_time_date_select_clear() {
      this.query.outbound_time_min = '';
      this.query.outbound_time_max = '';
      this.outbound_time_date_text = '请选择出库时间';
      this.search_();
    },
          								outbound_quantity_input() {
			this.outbound_quantity_input_timer && clearTimeout(this.outbound_quantity_input_timer);
			this.outbound_quantity_input_timer = setTimeout(() => {
				if((this.query.outbound_quantity_min && this.query.outbound_quantity_max) || (!this.query.outbound_quantity_min && !this.query.outbound_quantity_max)) {
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
                        this.outbound_time_date_range_picker_show = false;
    this.outbound_time_date_text = '请选择出库时间';
                uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
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
                              },
  created() {
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