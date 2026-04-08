<template>
  <view
    class="page_cart"
    id="page_cart"
    :style="{ paddingTop: vuex_custom_bar_height + 'px' }"
  >
    <tn-nav-bar :isBack="false">购物车</tn-nav-bar>
    <!-- 购物车模块(开始) -->
    <list_cart :list="list"></list_cart>
    <!-- 购物车模块(结束) -->
	<!-- 商品推荐 -->
	<view class="goods_hot">
		<view class="hot_title">商品推荐</view>
		<list_goods :list="list_goods" @refresh="get_list"></list_goods>
	</view>
    <tn-tabbar
      :value="tabbarIndex"
      :list="tabbarList"
      @change="switchTabbar"
      :animation="true"
      :safeAreaInsetBottom="true"
    ></tn-tabbar>
  </view>
</template>

<script>
import list_cart from '@/components/diy/list_cart.vue';
import mixin from '@/libs/mixins/page.js';
import tabbar from '@/libs/mixins/tabbar.js';
import list_goods from '@/components/diy/list_goods.vue';
import * as HomeApi from '@/api/home';
export default {
  mixins: [mixin, tabbar],
  components: {
    list_cart,
	list_goods,
  },
  data() {
    return {
      tabbarIndex: 0,
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      url_get_list: '~/api/cart/get_list?',
      query: {
        user_id: 0,
      },
	  list_goods: [],
    };
  },
  methods: {
    get_list_before(param) {
      param.user_id = this.userInfo.user_id;
      return param;
    },
    get_list_after(json) {
      return json.result.list.map((o) => {
        o.selected = false;
      });
    },
	/**
     *  获取商品
     */
    get_goods() {
      let params = {
        page: 1,
        size: 4,
      };
                                                                                                                  if (this.userInfo.user_group != '管理员' && this.activeGoods == '全部') {
        params.user_id = this.userInfo.user_id;
      }
              HomeApi.getHomeGoodsListApi(params).then((res) => {
        if (res.result && res.result.list) {
          this.list_goods = res.result.list;
        }
      });
    },
  },
  onShow() {
	this.get_goods();
  },
  mounted() {
    for (let i = 0;i<this.tabbarList.length;i++){
      if (this.tabbarList[i].pagePath === "/pages/cart/index") {
        this.tabbarIndex = i;
      }
    }
  },
};
</script>

<style lang="scss" scoped>
.goods_hot{
	width: 100%;
	padding: 10px 4%;
	.hot_title{
		font-weight: bold;
		margin-bottom: 10px;
	}
}
</style>
