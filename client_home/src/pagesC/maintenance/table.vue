<template>
  <view class="page_diy_table page_maintenance_table" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar backUrl="/pages/user/index">维修养护列表</tn-nav-bar>
    <view class="page-list" id="page_diy_table">
      <!-- 筛选模块(开始) -->
      <view class="search-wrap">
        <!-- 搜索栏 -->
        <uni-forms :modelValue="query" labelWidth="70px">
            <uni-forms-item label="项目套餐" name="project_bundle">
                <uni-easyinput type="text" v-model="query.project_bundle" placeholder="项目套餐" />
              </uni-forms-item>
                      </uni-forms>
        <!-- /搜索栏 -->
        <view class="search-btn-wrap">
          <view class="me-btn btn-reset" @click="reset()"> 重置 </view>
          <view class="me-btn btn-search" @click="search_()"> 查询 </view>
        </view>
		<view class="toolbar">
				  <view width="100%" class="me-btn btn-add" @click="$navTo('/pagesC/maintenance/view?')" v-if="$check_action('/maintenance/table', 'add') || $check_action('/maintenance/view', 'add')">添加</view>
		        		</view>
      </view>
      <!-- 列表 -->
      <view class="list-wrap-row">
        <view v-for="(o, i) in list" :key="i" class="list-item">
          <view class="item-row " v-if="1 && $check_field('get','project_bundle')">
            <view class="label" v-if="true">
              <span>项目套餐</span>
            </view>
              <view class="value">
                              <span>{{ o['project_bundle'] }}</span>
                          </view>
            </view>
          <view class="item-row diy-image" v-if="1 && $check_field('get','project_picture')">
            <view class="label" v-if=" false">
              <span>项目图片</span>
            </view>
              <view class="value diy_img">
              <image :src="$fullImgUrl(o['project_picture'])" width="100%" height="100" />
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','project_price')">
            <view class="label" v-if="true">
              <span>项目价格</span>
            </view>
              <view class="value">
                              <span>{{ o['project_price'] }}元</span>
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
          <view class="item-row " v-if="0 && $check_field('get','financial_user')">
            <view class="label" v-if="true">
              <span>财务用户</span>
            </view>
              <view class="value">
              <span>{{ get_user_financial_user(o['financial_user']) }}</span>
            </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','project_description')">
            <view class="label" v-if="true">
              <span>项目说明</span>
            </view>
              <view class="value">
                              <span>{{ o['project_description'] }}</span>
                          </view>
            </view>
          <view class="item-row " v-if="0 && $check_field('get','project_content')">
            <view class="label" v-if="true">
              <span>项目内容</span>
            </view>
              <view class="value">
                              <span>{{ o['project_content'] }}</span>
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
                  v-if="$check_action('/maintenance/table', 'del') || $check_action('/maintenance/view', 'del')">
              删除
            </view>
            <view width="100%" class="me-btn btn-info" @click="$navTo('/pagesC/maintenance/view?' + field + '=' + o[field])"
                  v-if="$check_action('/maintenance/table', 'set') || $check_action('/maintenance/view', 'set') || $check_action('/maintenance/view', 'get')">
              详情
            </view>
      <!--跨表按钮-->
			              <view width="100%" class="me-btn btn-pay"
                    v-if="$check_action('/maintenance_order/view', 'add') && !o.maintenance_order_limit && !o.maintenance_order_status_limit"
                    @click="to_form('/pagesC/maintenance_order/view', o)">
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
      url_get_list: '~/api/maintenance/get_list?like=1',
      url_del: '~/api/maintenance/del',

      // 字段ID
      field: 'maintenance_id',
      // 查询
      query: {
        size: 7,
        page: 1,
              project_bundle: "",
                          login_time: '',
        create_time: '',
      },
							      // 数据
      list: [],
                            	  project_price_input_timer: null,
                      // 用户列表
      list_user_manager_user: [],
                          // 用户列表
      list_user_financial_user: [],
                                };
  },
  methods: {
                            					project_price_input() {
			this.project_price_input_timer && clearTimeout(this.project_price_input_timer);
			this.project_price_input_timer = setTimeout(() => {
				if((this.query.project_price_min && this.query.project_price_max) || (!this.query.project_price_min && !this.query.project_price_max)) {
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
    get_list_before(param){
      var user_group = this.user.user_group;
      if(user_group != "管理员"){
          let sqlwhere = "(";
                                                if(user_group=="经理用户"){
          sqlwhere+= "manager_user = " + this.user.user_id + " or ";
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
				_this.$set(item, '_status_limit', false);
							})
							_this.list.map((item) => {
				_this.$set(item, '_status_limit', false);
							})
		    				  _this.list.map((item) => {
			let param = {
			  source_table: "maintenance",
			  source_id: item.maintenance_id,
			  source_user_id: _this.user.user_id
			};
			if(item.maintenance_order_limit_times > 0){
			  _this.$get("~/api/maintenance_order/count",param,(result)=>{
			  	if(result){
			  	  if(result.result >= item.maintenance_order_limit_times){
			  		_this.$set(item,'maintenance_order_limit',true);
			  	  }else{
			  		_this.$set(item,'maintenance_order_limit',false);
			  	  }
				}
			  })
			}else{
			  _this.$set(item,'maintenance_order_limit',false);
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
                        this.get_list_user_manager_user();
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
</style>