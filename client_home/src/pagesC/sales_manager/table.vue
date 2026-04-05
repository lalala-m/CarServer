<template>
  <view class="page_diy_table page_sales_manager_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">销售经理列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="经理姓名" name="manager_name">
                <uni-easyinput type="text" v-model="query.manager_name" placeholder="经理姓名" />
              </uni-forms-item>
                <uni-forms-item label="联系号码" name="contact_number">
                <uni-easyinput type="text" v-model="query.contact_number" placeholder="联系号码" />
              </uni-forms-item>
          </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/sales_manager/view?')" v-if="$check_action('/sales_manager/table', 'add') || $check_action('/sales_manager/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','manager_name')">
            <view class="label" v-if="true">
              <span>经理姓名</span>
            </view>
              <view class="value">
                              <span>{{ o['manager_name'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','gender_of_manager')">
            <view class="label" v-if="true">
              <span>经理性别</span>
            </view>
              <view class="value">
                              <span>{{ o['gender_of_manager'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="1 && $check_field('get','contact_number')">
            <view class="label" v-if="true">
              <span>联系号码</span>
            </view>
              <view class="value">
                              <span>{{ o['contact_number'] }}</span>
                          </view>
            </view>
          <view class="content">
          </view>
          <view class="operate-bar">
            <view width="100%" class="me-btn btn-delete" @click="delInfo(i)"
                  v-if="$check_action('/sales_manager/table', 'del') || $check_action('/sales_manager/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/sales_manager/view?' + field + '=' + o[field])"
                  v-if="$check_action('/sales_manager/table', 'set') || $check_action('/sales_manager/view', 'set') || $check_action('/sales_manager/view', 'get')">
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
      url_get_list: '~/api/sales_manager/get_list?like=1',
      url_del: '~/api/sales_manager/del',

      // 字段ID
      field: 'sales_manager_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              manager_name: "",
                      contact_number: "",
              login_time: '',
        create_time: '',
      },
			      // 数据
      list: [],
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
                  uni.clear(this.query);
      uni.push(this.query, this.config);
      this.get_list();
    },
    delInfo(v) {
      let _this = this;
      uni.showModal({
        title: '删除',
        content: '此操作将永久删除该文件, 是否继续?',
        success: function (res) {
          if (res.confirm) {
            let list = [v];
            _this.delInfoSub(list);
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        },
      });
    },
    async delInfoSub(list) {
      let _this = this;
      await this.delAll(list, async (list) => {
        var bl = true;
        for (var i = 0; i < list.length; i++) {
          var user_id = _this.list[list[i]].user_id;
          var res = await this.$get('~/api/user/del', { user_id });
          if (res.result) {
            console.log('删除成功' + i);
          } else {
            console.log('删除失败' + i);
            bl = false;
            break;
          }
        }
        if (bl) {
          _this.$toast('删除成功!', 'success');
          this.get_list();
        }
      });
    },
    get_list_after(param){
      let _this = this;
      for (let i = 0;i<this.list.length;i++){
        let user_id = _this.list[i].user_id
        _this.$get("~/api/user/get_obj", {
          user_id
        }, (json) => {
          if (json.result.obj){
            _this.$delete(_this.list[i],'username');
            _this.$set(_this.list[i],'username',json.result.obj.username);
            _this.$delete(_this.list[i],'nickname');
            _this.$set(_this.list[i],'nickname',json.result.obj.nickname);
          }
        });
      }
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