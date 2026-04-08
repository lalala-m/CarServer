<template>
  <view class="page_diy_table page_card_type_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">卡券类型列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="卡券名称" name="card_name">
                <uni-easyinput type="text" v-model="query.card_name" placeholder="卡券名称" />
              </uni-forms-item>
              <uni-forms-item label="有效期限" name="effective_period">
    					<view class="date_select" @click="effective_period_date_range_picker_show = true">{{effective_period_date_text}}
            <icon class="date_select_clear" type="clear" size="14" @click.stop="effective_period_date_select_clear"/>
          </view>
              </uni-forms-item>
              </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/card_type/view?')" v-if="$check_action('/card_type/table', 'add') || $check_action('/card_type/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','card_name')">
            <view class="label" v-if="true">
              <span>卡券名称</span>
            </view>
              <view class="value">
                              <span>{{ o['card_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','effective_period')">
            <view class="label" v-if="true">
              <span>有效期限</span>
            </view>
              <view class="value">
                              <span>{{ $toTime(o['effective_period'], 'yyyy-MM-dd') }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','card_content')">
            <view class="label" v-if="true">
              <span>卡券内容</span>
            </view>
              <view class="value">
                              <span>{{ o['card_content'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','applicable_items')">
            <view class="label" v-if="true">
              <span>适用项目</span>
            </view>
              <view class="value">
                              <span>{{ o['applicable_items'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/card_type/table', 'del') || $check_action('/card_type/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/card_type/view?' + field + '=' + o[field])"
                  v-if="$check_action('/card_type/table', 'set') || $check_action('/card_type/view', 'set') || $check_action('/card_type/view', 'get')">
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
      url_get_list: '~/api/card_type/get_list?like=1',
      url_del: '~/api/card_type/del',

      // 字段ID
      field: 'card_type_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              card_name: "",
            				effective_period_min: '',
        effective_period_max: '',
                  login_time: '',
        create_time: '',
      },
				      // 数据
      list: [],
                        effective_period_date_range_picker_show: false,
    effective_period_date_text: '请选择有效期限',
                          };
  },
  methods: {
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
            				    search_() {
      this.query.page = 1;
      this.get_list();
    },
    /**
     * 重置
     */
    reset: function reset() {
            this.effective_period_date_range_picker_show = false;
    this.effective_period_date_text = '请选择有效期限';
                uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    async delInfo(v) {
      let _this = this;
      let type = this.list[v];
			let res
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        res = await _this.$get("~/api/issuing_card_voucher/get_list?", {	
              card_name: type.card_name
            });
            if (res.result && res.result.list && res.result.list.length > 0) {
              _this.$toast('"' + type.card_name + '"下有关联的内容，无法删除！');
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
</style>