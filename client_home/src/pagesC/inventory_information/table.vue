<template>
  <view class="page_diy_table page_inventory_information_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">库存信息列表</tn-nav-bar>
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
                </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/inventory_information/view?')" v-if="$check_action('/inventory_information/table', 'add') || $check_action('/inventory_information/view', 'add')">添加</view>
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
          <view class="item-row " v-if="0 && $check_field('get','consumables_inventory')">
            <view class="label" v-if="true">
              <span>耗材库存</span>
            </view>
              <view class="value">
                              <span>{{ o['consumables_inventory'] }}</span>
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
          <view class="item-row " v-if="0 && $check_field('get','introduction_of_consumables')">
            <view class="label" v-if="true">
              <span>耗材简介</span>
            </view>
              <view class="value">
                              <span>{{ o['introduction_of_consumables'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/inventory_information/table', 'del') || $check_action('/inventory_information/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/inventory_information/view?' + field + '=' + o[field])"
                  v-if="$check_action('/inventory_information/table', 'set') || $check_action('/inventory_information/view', 'set') || $check_action('/inventory_information/view', 'get')">
              详情
            </view>
      <!--跨表按钮-->
			              <view width="100%" class="me-btn btn-pay"
                    v-if="$check_action('/inbound_record/view', 'add') && !o.inbound_record_limit && !o.inbound_record_status_limit"
                    @click="to_form('/pagesC/inbound_record/view', o)">
                <span>入库</span>
              </view>
		              <view width="100%" class="me-btn btn-pay"
                    v-if="$check_action('/outbound_record/view', 'add') && !o.outbound_record_limit && !o.outbound_record_status_limit"
                    @click="to_form('/pagesC/outbound_record/view', o)">
                <span>出库</span>
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
                                                                                                                                                              <!-- 重要提醒弹窗 -->
    <view class="popup-mask" v-if="showModal" @click="closeModal"></view>
    <view class="reminder-modal" v-if="showModal">
      <view class="reminder-title">重要提醒</view>
      <view class="reminder-content">
        <view class="reminder-text-bold">当前有数据达到预警值！</view>
        <view class="reminder-text">{{ message }}</view>
      </view>
      <view class="reminder-footer">
        <button class="reminder-btn cancel-btn" @click="closeModal">取消</button>
        <button class="reminder-btn confirm-btn" @click="closeModal">确定</button>
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
      url_get_list: '~/api/inventory_information/get_list?like=1',
      url_del: '~/api/inventory_information/del',

      // 字段ID
      field: 'inventory_information_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              consumables_code: "",
                    consumables_name: "",
                    login_time: '',
        create_time: '',
      },
					      // 数据
      list: [],
      showModal: false,
      message: '',
                            	  consumables_inventory_input_timer: null,
                            };
  },
  methods: {
                    					consumables_inventory_input() {
			this.consumables_inventory_input_timer && clearTimeout(this.consumables_inventory_input_timer);
			this.consumables_inventory_input_timer = setTimeout(() => {
				if((this.query.consumables_inventory_min && this.query.consumables_inventory_max) || (!this.query.consumables_inventory_min && !this.query.consumables_inventory_max)) {
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
		        		    				  _this.list.map((item) => {
			let param = {
			  source_table: "inventory_information",
			  source_id: item.inventory_information_id,
			  source_user_id: _this.user.user_id
			};
			if(item.inbound_record_limit_times > 0){
			  _this.$get("~/api/inbound_record/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.inbound_record_limit_times){
			  		_this.$set(item,'inbound_record_limit',true);
			  	  }else{
			  		_this.$set(item,'inbound_record_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'inbound_record_limit',false);
			}
			Object.assign(item, param)
		  })
				  _this.list.map((item) => {
			let param = {
			  source_table: "inventory_information",
			  source_id: item.inventory_information_id,
			  source_user_id: _this.user.user_id
			};
			if(item.outbound_record_limit_times > 0){
			  _this.$get("~/api/outbound_record/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.outbound_record_limit_times){
			  		_this.$set(item,'outbound_record_limit',true);
			  	  }else{
			  		_this.$set(item,'outbound_record_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'outbound_record_limit',false);
			}
			Object.assign(item, param)
		  })
			  	        setTimeout(() => {
        _this.open_tip();
      }, 1000);
  	},
                                            open_tip() {
      var message = "";
      var list = this.list;
      // 清空预警标记
      list.forEach(item => {
        this.$set(item, '_hasWarning', false);
  								        this.$set(item, '_hasWarning_consumables_inventory', false);
    							      });

      var ifs = [
			        {
          title: "耗材库存",
          factor: "consumables_inventory",
              start: -1,
          end: 5,
              type: "数内",
          idx: 0
        },
  		      ];
      for (var n = 0; n < ifs.length; n++) {
        var o = ifs[n];
        o.idx = 0;
        for (var i = 0; i < list.length; i++) {
          var lt = list[i];
          var hasWarning = false;
          if (o.type == "数内") {
            if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
              if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
                hasWarning = true;
              }
            } else if (o.start || o.start === 0) {
              if (lt[o.factor] > o.start) {
                hasWarning = true;
              }
            } else if (o.end || o.end === 0) {
              if (lt[o.factor] < o.end) {
                hasWarning = true;
              }
            }
          } else if (o.type == "数外") {
            if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
              if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
                hasWarning = true;
              }
            } else if (o.start || o.start === 0) {
              if (lt[o.factor] < o.start) {
                hasWarning = true;
              }
            } else if (o.end || o.end === 0) {
              if (lt[o.factor] > o.end) {
                hasWarning = true;
              }
            }
          } else if (o.type == "日内") {
            let currentDate = new Date(lt[o.factor]).getTime();
            let startDate = new Date(o.start).getTime();
            let endDate = new Date(o.end).getTime();
            if (o.start && o.end) {
              if (currentDate > startDate && currentDate < endDate) {
                hasWarning = true;
              }
            } else if (o.start) {
              if (currentDate < startDate) {
                hasWarning = true;
              }
            } else if (o.end) {
              if (currentDate > endDate) {
                hasWarning = true;
              }
            }
          } else if (o.type == "日外") {
            let currentDate = new Date(lt[o.factor]).getTime();
            let startDate = new Date(o.start).getTime();
            let endDate = new Date(o.end).getTime();
            if (o.start && o.end) {
              if (currentDate < startDate || currentDate > endDate) {
                hasWarning = true;
              }
            } else if (o.start) {
              if (currentDate < startDate) {
                hasWarning = true;
              }
            } else if (o.end) {
              if (currentDate > endDate) {
                hasWarning = true;
              }
            }
          }
          if (hasWarning) {
            o.idx = o.idx + 1;
            this.$set(lt, '_hasWarning', true);
            this.$set(lt, '_hasWarning_' + o.factor, true);
          }
        }

        if (o["idx"]) {
          message += o.title;
          if (o["type"] == "数内") {
            if (o.start || o.start === 0) {
              message += "大于" + o.start;
            }
            if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
              message += "并且";
            }
            if (o.end || o.end === 0) {
              message += "小于" + o.end;
            }
          } else if (o["type"] == "数外") {
            if (o.start || o.start === 0) {
              message += "小于" + o.start;
            }
            if ((o.start || o.start === 0) || (o.end || o.end === 0)) {
              message += "或者";
            }
            if (o.end || o.end === 0) {
              message += "大于" + o.end;
            }
          } else if (o["type"] == "日内") {
            if (o.start) {
              message += "在" + o.start + "之后";
            }
            if (o.start && o.end) {
              message += "并且";
            }
            if (o.end) {
              message += "在" + o.end + "之前";
            }
          } else if (o["type"] == "日外") {
            if (o.start) {
              message += "在" + o.start + "之前";
            }
            if (o.start || o.end) {
              message += "或者";
            }
            if (o.end) {
              message += "在" + o.end + "之后";
            }
          }
          message += "的有" + o["idx"] + "条，";
        }
      }

      if (message) {
        message += "请尽快处理。";
        this.showModal = true;
        this.message = message
      }
    },

    closeModal() {
      this.showModal = false;
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
.reminder-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600rpx;
  background-color: #fff;
  border-radius: 8rpx;
  z-index: 1000;
  overflow: hidden;

  .reminder-title {
    padding: 30rpx;
    font-size: 32rpx;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #eee;
  }

  .reminder-content {
    padding: 40rpx;
    text-align: center;

    .reminder-text-bold {
      font-size: 30rpx;
      color: #333;
      margin-bottom: 20rpx;
      font-weight: bold;
    }

    .reminder-text {
      font-size: 28rpx;
      color: #666;
    }
  }

  .reminder-footer {
    display: flex;
    border-top: 1px solid #eee;

    .reminder-btn {
      flex: 1;
      height: 88rpx;
      line-height: 88rpx;
      text-align: center;
      font-size: 30rpx;
      background: none;
      border: none;
      border-radius: 0;
      margin: 0;

      &::after {
        border: none;
      }

      &.cancel-btn {
        color: #666;
        border-right: 1px solid #eee;
      }

      &.confirm-btn {
        color: #409EFF;
      }
    }
  }
}
</style>