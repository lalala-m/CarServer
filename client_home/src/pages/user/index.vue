<template>
  <view id="page_user" class="page_user" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar :isBack="false" :bottomShadow="false" :isScan="true">个人中心</tn-nav-bar>
    <!-- 用户栏模块(开始) -->
    <view class="user">
      <!-- 头像 -->
      <view class="left">
        <view class="avatar">
          <image :src="$fullImgUrl(userInfo.avatar) || '/static/img/default.png'"></image>
        </view>
        <view class="base-info" v-if="token">
          <view class="username">{{ userInfo.nickname }}</view>
          <view class="signature">{{ userInfo.user_group }}</view>
                </view>
        <view class="base-info" v-else @click="toLogin">
          <view class="username">游客</view>
        </view>
              <uni-badge size="small" :text="msgSum" absolute="rightTop" type="error" :offset="[4,4]" v-if="messageInformListAll.length > 0">
          <uni-icons @click="$navTo('/pagesA/user/releasing_notices?follower_id=' + userInfo.user_id)" type="notification" size="28" style="color: #333"></uni-icons>
        </uni-badge>
            </view>
      <!-- 右侧其它 -->
      <view class="other">
        <view class="user-info-btn" @click="$navTo('/pagesA/user/info')">
          <text class="iconfont icon-jinggao"></text>
          基本信息
        </view>
            </view>
    </view>
    <!-- 用户栏模块(结束) -->
    <view class="menu-group my-order" v-if="my_home_page_auth">
      <view class="menu-row user-statistics" @click="$navTo('/pagesA/user/my_home_page?follower_id=' + userInfo.user_id)">
        <view class="menu-item">
          <view class="user-sum">{{token ? dynamicList.length : '0'}}</view>
          <view class="user-sum-name">发布</view>
        </view>
        <view class="menu-item">
          <view class="user-sum">{{token ? myFollow.length : '0'}}</view>
          <view class="user-sum-name">关注</view>
        </view>
        <view class="menu-item">
          <view class="user-sum">{{token ? followSelf.length : '0'}}</view>
          <view class="user-sum-name">粉丝</view>
        </view>
      </view>
    </view>
    <view class="split"></view>
    <view class="menu-group my-money">
      <view class="title-wrap">
        <text class="title">我的资产</text>
      </view>
      <view class="menu-scroll">
        <scroll-view class="scroll-view" scroll-x="true">
          <!-- #ifdef MP-WEIXIN -->
          <view class="uni-scroll-view-content">
          <!-- #endif -->
								  <view class="menu-item coupon">
		    <view class="left">
		      <image class="menu-img" :src="'/static/img/default.png'"></image>
		    </view>
		    <view class="right">
		      <view class="name">积分</view>
		      <view class="info"><text>{{ userInfo.integral==undefined?0:userInfo.integral}}</text>积分可用</view>
		    </view>
		  </view>
		          <!-- #ifdef MP-WEIXIN -->
          </view>
          <!-- #endif -->
        </scroll-view>
      </view>
    </view>
    <view class="split"></view>
    <view class="menu-group my-order">
      <view class="title-wrap">
        <view class="title">我的订单</view>
        <view class="more" @click="$navTo('/pagesA/order/list')">
          查看全部
          <text class="iconfont icon-right"></text>
        </view>
      </view>

      <view class="menu-row">
        <view class="menu-item" @click="$navTo('/pagesA/order/list?type=1')">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">待付款</view>
        </view>
        <view class="menu-item" @click="$navTo('/pagesA/order/list?type=2')">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">待发货</view>
        </view>
        <view class="menu-item" @click="$navTo('/pagesA/order/list?type=3')">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">待签收</view>
        </view>

        </view>
    </view>
    <view class="split"></view>
    <view class="menu-group my-data">
      <view class="menu-row-sm">
          <view class="menu-item address" @click="$navTo('/pagesA/user/address')">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">收货地址</view>
        </view>
  		<view class="menu-item schedule" @click="$navTo('/pagesB/schedule/view')">
		  <image class="menu-img" :src="'/static/img/default.png'"></image>
		  <view class="name">日程管理</view>
		</view>
        <view v-if="$check_action('/collect/list', 'get')" class="menu-item collect" @click="$navTo('/pagesA/user/collect?title=' + $page_title('/collect/list'))">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">{{ $page_title("/collect/list") || '收藏记录' }}</view>
        </view>
        <view v-if="$check_action('/praise/list', 'get')" class="menu-item data" @click="$navTo('/pagesA/user/praise?title=' + $page_title('/praise/list'))">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">{{ $page_title("/praise/list") || '点赞记录' }}</view>
        </view>
        <view v-if="$check_action('/comment/table', 'get')" class="menu-item comment" @click="$navTo('/pagesA/user/comment?title=' + $page_title('/comment/table'))">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">{{ $page_title("/comment/table") || '评论管理' }}</view>
        </view>
        <view class="menu-item data" v-if="chartAble" @click="$navTo('/pagesA/user_center/index')">
          <image class="menu-img" :src="'/static/img/default.png'"></image>
          <view class="name">统计</view>
        </view>
      </view>
    </view>

    <view class="split"></view>
    <!-- 自定义栏模块(开始) -->
    <view class="nav-list">
      <view v-for="(item, index) in cellList" :key="index" v-if="$check_action(item.auth, 'get')">
        <tn-list-cell
            @click="$navTo(item.url)"
            :arrow="true"
        >
          <view class="nav-list-row">
            <image class="menu-img" :src="'/static/img/default.png'"></image>
            <text>{{ item.title }}</text>
          </view>
        </tn-list-cell>
      </view>
    </view>

    <!-- 自定义栏模块(结束) -->

    <!-- 按钮模块(开始) -->
    <view class="user-center-footer">
      <view class="me-btn logout-btn" v-if="token" @click="sign_out">退出登录</view>
      <view v-else class="me-btn login-btn" @click="toLogin">立即登录</view>
    </view>
    <!-- 按钮模块(结束) -->
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
  import tabbar from '@/libs/mixins/tabbar.js';

  export default {
    mixins: [tabbar],
    data() {
      return {
        tabbarIndex: 0,
        chartAble: false,
        cellList: [
                {
            title: this.$page_title("/owner_user/table") || '车主用户',
            auth: '/owner_user/table',
            url: '/pagesC/owner_user/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/business_user/table") || '商家用户',
            auth: '/business_user/table',
            url: '/pagesC/business_user/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/financial_user/table") || '财务用户',
            auth: '/financial_user/table',
            url: '/pagesC/financial_user/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/manager_user/table") || '经理用户',
            auth: '/manager_user/table',
            url: '/pagesC/manager_user/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/sales_manager/table") || '销售经理',
            auth: '/sales_manager/table',
            url: '/pagesC/sales_manager/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/vehicle_information/table") || '车辆信息',
            auth: '/vehicle_information/table',
            url: '/pagesC/vehicle_information/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/vehicle_class_nameification/table") || '车型分类',
            auth: '/vehicle_class_nameification/table',
            url: '/pagesC/vehicle_class_nameification/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/brand_class_nameification/table") || '品牌分类',
            auth: '/brand_class_nameification/table',
            url: '/pagesC/brand_class_nameification/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/car_information/table") || '汽车信息',
            auth: '/car_information/table',
            url: '/pagesC/car_information/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/test_drive_record/table") || '试驾记录',
            auth: '/test_drive_record/table',
            url: '/pagesC/test_drive_record/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/purchase_order/table") || '购买订单',
            auth: '/purchase_order/table',
            url: '/pagesC/purchase_order/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/maintenance/table") || '维修养护',
            auth: '/maintenance/table',
            url: '/pagesC/maintenance/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/maintenance_order/table") || '保养订单',
            auth: '/maintenance_order/table',
            url: '/pagesC/maintenance_order/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/card_type/table") || '卡券类型',
            auth: '/card_type/table',
            url: '/pagesC/card_type/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/issuing_card_voucher/table") || '发放卡券',
            auth: '/issuing_card_voucher/table',
            url: '/pagesC/issuing_card_voucher/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/write_off_information/table") || '核销信息',
            auth: '/write_off_information/table',
            url: '/pagesC/write_off_information/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/rescue_record/table") || '救援记录',
            auth: '/rescue_record/table',
            url: '/pagesC/rescue_record/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/service_class_nameification/table") || '服务分类',
            auth: '/service_class_nameification/table',
            url: '/pagesC/service_class_nameification/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/period_class_nameification/table") || '时段分类',
            auth: '/period_class_nameification/table',
            url: '/pagesC/period_class_nameification/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/boutique_center/table") || '精品中心',
            auth: '/boutique_center/table',
            url: '/pagesC/boutique_center/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/reservation_record/table") || '预约记录',
            auth: '/reservation_record/table',
            url: '/pagesC/reservation_record/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/supplier/table") || '供应厂商',
            auth: '/supplier/table',
            url: '/pagesC/supplier/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/warehouse_information/table") || '仓库信息',
            auth: '/warehouse_information/table',
            url: '/pagesC/warehouse_information/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/inventory_information/table") || '库存信息',
            auth: '/inventory_information/table',
            url: '/pagesC/inventory_information/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/inbound_record/table") || '入库记录',
            auth: '/inbound_record/table',
            url: '/pagesC/inbound_record/table',
            icon: 'icon-yonghu',
          },
                    {
            title: this.$page_title("/outbound_record/table") || '出库记录',
            auth: '/outbound_record/table',
            url: '/pagesC/outbound_record/table',
            icon: 'icon-yonghu',
          },
                          {
            title: this.$page_title("/employee_performance/table") || '员工绩效',
            auth: '/employee_performance/table',
            url: '/pagesC/employee_performance/table',
            icon: 'icon-yonghu',
          },
                      {
            title: this.$page_title("/forum/table") || '论坛管理',
            auth: '/forum/table',
            url: '/pagesA/forum/table',
            icon: 'icon-luntan',
          },
                  {
            title: '订单配送',
            auth: '/logistics_delivery/table',
            url: '/pagesA/logistics_delivery/table',
            icon: 'icon-dingdanguanli',
          },
              ],
        followList: [],
        myFollow: [],
        followSelf: [],
        dynamicList: [],
        msgSum: 0,
        msgList: [],
        messageInformListAll: [],
        my_home_page_auth_list: [
          "forum",
                                                                                                                                                                                                                                                                                                                                                                                                                      ],
      };
    },
    computed: {
      my_home_page_auth() {
        return this.my_home_page_auth_list.some(item =>
          this.$check_action('/' + item + '/list', 'get')
        );
      }
    },
    onLoad() {
          if (this.$check_option("/car_information/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/purchase_order/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/maintenance_order/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/car_information/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/employee_performance/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/purchase_order/table","figure")){
        this.chartAble = true
      }
            if (this.$check_option("/reservation_record/table","figure")){
        this.chartAble = true
      }
        if (this.$check_figure("/order/table")){
        this.chartAble = true
      }
    },
    methods: {
      async getMessageInformList() {
        if(!this.userInfo.user_id) {
          return
        }
        if(this.userInfo.user_group == '管理员') {
          let list = await this.$get("~/api/message_inform/get_list",{type: '消息'})
          this.msgList = list.result.list
        }
        let listAll = await this.$get("~/api/message_inform/get_list?user_id=" + this.userInfo.user_id + "&like=0")
        this.messageInformListAll = [...listAll.result.list,...this.msgList]
        this.msgSum = 0
        this.messageInformListAll.forEach(item => {
          if(item.state == 1) {
            this.msgSum++
          }
        })
      },
      async getFollowList() {
        let res = await this.$get('~/api/follow/get_list',{orderby: `create_time desc`})
        this.followList = res.result.list;
        this.myFollow = res.result.list.filter(item => item.follower_id == this.userInfo.user_id);
        this.followSelf = res.result.list.filter(item => item.followed_id == this.userInfo.user_id).map(item => {
          // 根据myFollow遍历是否互相关注
          let isFollow = this.myFollow.some(item2 => item2.follower_id === item.followed_id && item2.followed_id === item.follower_id);
          return {
          ...item,
            isFollow: isFollow
          }
        });
        console.log(this.myFollow,'我的关注');
        console.log(this.followSelf,'我的粉丝');
      },
      async getDynamicList(userId) {
        let params = Object.assign({}, {orderby: `create_time desc`}, {
          user_id: userId
        });
        let forum = await this.$get('~/api/forum/get_list', params);
        let forumList = forum.result.list.map(item => ({
          ...item,
          type: 'forum'
        }));
																												
        this.dynamicList = [
        ...forumList,
																												        ];
      },
      sign_out() {
        this.$store.dispatch('auth/logOut').then((res) => {
        this.$Router.replaceAll('/pagesB/account/login');
        });
      },
      toLogin() {
        this.$Router.replace('/pagesB/account/login');
      },
    },
    mounted() {
      for (let i = 0;i<this.tabbarList.length;i++){
        if (this.tabbarList[i].pagePath === "/pages/user/index") {
          this.tabbarIndex = i;
        }
      }
    },
    onShow() {
      this.getFollowList();
      this.getDynamicList(this.userInfo.user_id);
      this.getMessageInformList();
    },
  };
</script>

<style lang="scss" scoped>
.user-statistics {
  justify-content: space-around !important;
}
.user-sum {
  font-size: 24px;
  font-weight: 600;
}
</style>
