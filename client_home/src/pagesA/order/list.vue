<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar back-url="/pages/user/index">我的订单</tn-nav-bar>

    <view class="page_order_list" id="order_list">
      <view class="top-warp">
		<Search v-model="query.title" placeholder="查询订单" @search="search_" @cancel="cancel" />
        <!-- <me-search @search="searchData"></me-search> -->
        <me-tabs v-model="tabIndex" :tabs="tabs" @change="tabChange" :height="80"></me-tabs>
      </view>
      <view class="order-list">
        <view class="order-item" v-for="(o, i) in list" :key="i">
          <view class="header" style="">
            <view class="state">{{ getDisplayState(o) }}</view>
          </view>
          <view class="goods-list" v-for="(obj, idx) in o.list" :key="idx">
			    <view @click="go_order_detail(obj)" class="goods-item">
	              <view class="goods-img">
                <image
                  :src="$fullImgUrl(obj[vm.img]) || '../../static/img/default.png'"
                  mode="scaleToFill"
                >
                </image>
              </view>
              <view class="goods-info">
                <view class="title text-ellipsis-2">
                  {{ obj[vm.title] }}
                </view>
                <view class="time">
                  <text class="norms" v-if="obj[vm.norms]">规格：{{ obj[vm.norms] }}</text>
                  <text>{{ $toTime(o.create_time, 'yyyy-MM-dd hh:mm:ss') }}</text>
                </view>
              </view>

              <view class="right-info">
					<view class="price" v-if="!!o.integral"> {{ o.integral }}积分</view>
				<view class="price" v-else> ￥{{ obj[vm.price] }} </view>
	                <view class="num"> ×{{ obj[vm.num] }} </view>
              </view>
            </view>
          </view>

          <view class="footer">
            <view class="sum-price">
              <text>{{ o.state == '待付款' || o.state == '已取消' ? '需付' : '实付' }}：</text>
				  <text v-if="!!o.integral" class="price">{{ o.integral_total }}积分</text>
			  <text v-else class="price">￥{{ o.sum_price.toFixed(2) }}</text>
	            </view>

            <view class="btn-wrap">
			  <view
			    width="100%"
			    @click="cancel_order(o.order_number, i)"
			    v-if="o.state == '待付款'" 
			    class="me-btn btn-cancel"
			    >取消订单</view
			  >
        <view
          width="100%"
          @click="sign_order(o.order_number, i)"
          v-if="o.state == '待签收' "
          class="me-btn btn-cancel"
          >签收</view
        >
	              <view
                width="100%"
                @click="del_order(o.order_number, i)"
                v-if="$check_action('/order/list', 'del') && (o.state == '售后/退款' || o.state == '已取消')"
                class="me-btn btn-del"
                >删除订单</view
              >
            </view>
          </view>
        </view>
      </view>
    </view>
  <tn-modal v-model="cancelDialogVisible" :custom="true" showCloseBtn>
      <view class="cancel-container">
        <view class="cancel-container-title">取消订单</view>
        <radio-group @change="radioChange">
          <view class="cancel-container-item" v-for="item in cancelList" :key="item.id">
            <radio :value="item.id" :checked="item.id === cancelListID">
              <view>{{ item.desc }}</view>
            </radio>
          </view>
        </radio-group>
        <button type="primary" @click="cancelHandle" class="handin-after-sale-btn">提交</button>
      </view>
  </tn-modal>
  <tn-modal v-model="delDialogVisible" title="提示" content="是否确认删除订单？" :button="delButton" @click="delHandle"></tn-modal>
  </view>
</template>

<script>
import MescrollMixin from '@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js';
import Search from '@/components/businessCp/search.vue';
import { getOrderListApi } from '@/api/order';
export default {
  mixins: [MescrollMixin],
  components: {
	Search
  },
  data() {
    return {
      query: {
        user_id: '',
        state: '',
        like: 1,
      },
      vm: {
        order_number: 'order_number',
        title: 'title',
        img: 'img',
        price: 'price',
        price_ago: 'price_ago',
        price_count: 'price_count',
        num: 'num',
        norms: 'norms',
        goods_id: 'goods_id',
        description: 'description',
        contact_name: 'contact_name',
        contact_phone: 'contact_phone',
        contact_address: 'contact_address',
        postal_code: 'postal_code',
        user_id: 'user_id',
        state: 'state',
      },
      list: [],
      tabs: [
        {
          name: '全部',
          type: '1',
        },
        {
          name: '待付款',
          type: '2',
          state: '待付款',
        },
        {
          name: '待发货',
          type: '3',
          state: '待发货',
        },
        {
          name: '待签收',
          type: '4',
          state: '待签收',
        },
      ],
      tabIndex: 0,
    cancelDialogVisible: false,
    cancelListID: null,
    cancelList: [
        {
          id: 1,
          desc: '暂时不需要了',
        },
        {
          id: 2,
          desc: '误操作/重复下单',
        },
        {
          id: 3,
          desc: '订单信息填写错误',
        },
        {
          id: 4,
          desc: '产品不符合预期',
        },
        {
          id: 5,
          desc: '其他原因',
        },
      ],
    currentCancelData: null,
    delDialogVisible: false,
    delButton: [
        {
          text: '取消',
          backgroundColor: '#e83a30',
          fontColor: '#e83a30',
          plain: true,
        },
        {
          text: '确定',
          backgroundColor: '#e83a30',
          fontColor: '#FFFFFF',
        },
    ],
    delOrderData: null,
    };
  },
  onLoad(e) {
    this.query.user_id = this.userInfo.user_id;
    /*#ifdef H5 || APP-PLUS*/
    let type = this.$route.query.type;
    /*#endif*/
    /*#ifdef  MP-WEIXIN*/
    let type = e.type;
    /*#endif*/
    if (type){
      this.tabIndex = parseInt(type)
    }
    this.getOrderList();
  },
  methods: {
    getDisplayState(item) {
      return item.state;
  },
	go_order_detail(obj) {
	  if (!!obj.integral) {
	    this.$Router.push(
	      `/pagesA/order/details?order_number=${obj.order_number}&integral=${obj.integral}`
	    );
	  } else {
	    this.$Router.push(`/pagesA/order/details?order_number=${obj.order_number}`);
	  }
	},
    /**
     * 获取订单列表数据
     */
    getOrderList() {
      let curTab = this.tabs[this.tabIndex];
      let state = curTab.state;

      if (state) {
        this.query.state = state;
      }
      getOrderListApi(this.query)
        .then((res) => {
          this.list = this.get_list_after(res.result.list);
        })
        .catch(() => {});
    },
    // 切换菜单
    tabChange() {
      if (this.tabIndex === 1) {
        this.query.state = '待付款';
      } else if (this.tabIndex === 2) {
        this.query.state = '已付款';
      } else {
        delete this.query.state;
      }
      this.list = []; // 先置空列表,显示加载进度
      this.getOrderList();
    },
    // 删除订单
    del_order(order_number, i) {
      this.delDialogVisible = true;
      this.delOrderData = {
        order_number,
        i,
      };
    },
    delHandle(evt) {
      this.delDialogVisible = false;
      const { order_number, i } = this.delOrderData;
      if (evt.index === 1) {
        this.$get(
          '~/api/order/del',
          {
            order_number,
          },
          (res) => {
            this.list.splice(i, 1);
          }
        );
      }
    },
	  // 取消订单
	  cancel_order(order_number, i) {
      this.currentCancelData = {
        order_number,
        i,
      };
      this.cancelDialogVisible = true;
    },
    radioChange(event) {
      this.cancelListID = Number(event.detail.value);
    },
    cancelHandle() {
      var _this = this;
      let currDesc = this.cancelList.filter((item) => {
        return item.id === this.cancelListID;
      })[0];

      let desc = currDesc?.desc || '其他原因';
      _this.$post(
        '~/api/order/set?order_number=' + this.currentCancelData.order_number,
        {
          state: '已取消',
          desc,
        },
        (res) => {
          this.cancelDialogVisible = false;
          _this.$toast('取消订单成功！', 'success');
          _this.list[this.currentCancelData.i].state = '已取消';
        }
      );
    },
    /**
     * 搜索
     */
	search_() {
	  this.query.page = 1;
	  this.get_list();
	},
	cancel() {
	  console.log('eer');
	  this.query.title = '';
	  this.search_();
	},
    searchData(v) {
      this.query.page = 1;
      this.query.title = v;
      this.getOrderList();
    },
    input(e, key) {
      this.query[key] = e;
    },
    // 改变分类标签
    changeTab(val) {
      if (val === '全部') {
        this.query.state = '';
      } else {
        this.query.state = val;
      }
      this.search_();
    },
    get_list_before(param) {
      param.user_id = this.userInfo.user_id;
      return param;
    },
    get_list_after(list) {
      // 根据订单号区分列表
      let list_diff = [];
      let order_num = '';
      let index = -1;

      list.map((o) => {
        if (o.order_number === order_num) {
          list_diff[index]['list'].push(o);
          list_diff[index]['sum_price'] =
            parseFloat(o.price_count) + parseFloat(list_diff[index]['sum_price']);
        } else {
          index += 1;
          order_num = o.order_number;
          list_diff[index] = {};
          var obj_diff = list_diff[index];
          obj_diff['create_time'] = o.create_time;
          obj_diff['order_number'] = order_num;
          obj_diff['list'] = [];
          obj_diff['list'].push(o);
          obj_diff['sum_price'] = o.price_count;
          obj_diff['state'] = o.state;
		  obj_diff['integral'] = o.integral;
		  obj_diff['buy_type'] = o.buy_type;
		  obj_diff['integral_total'] = Number(o.integral) * Number(o.num);
        }
      });
      return list_diff;
    },
    sign_order(order_number, i) {
      const that = this;
      uni.showModal({
        title: '提示',
        content: '是否确认签收此订单, 是否继续?',
        success: function (res) {
          if (res.confirm) {
            that.$post('~/api/logistics_delivery/set?order_number=' + order_number, {
              signing_status: '已签收',
              delivery_status: '已完成',
            }).then((json, status) => {
              if (json.result) {
                that.$toast('签收成功!');
                that.$post('~/api/order/set?order_number=' + order_number, {
                  state: '已完成',
                });
                uni.redirectTo({
                  url: '/pagesA/order/list',
                });
              } else if (json.error) {
                that.$toast(json.error.message);
              } else {
                that.$toast('服务器连接失败！');
              }
            });
          }
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@import 'styles/pagesA/index.scss';
.cancel-container-title{
  font-weight: bold;
  margin-bottom: 20px;
}
.cancel-container-item{
  margin-bottom: 15px;
}
.handin-after-sale-btn{
  // margin-top: 20px;
}
.norms{
  font-size: 12px;
  color: #999;
}
</style>
