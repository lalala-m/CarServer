<template>
  <view id="page_diy_details" class="page_diy_details page_online_mall_details" :style="{ paddingTop: vuex_custom_bar_height + 'px', paddingBottom: vuex_safe_area_bottom + 'px',}">
    <tn-nav-bar>网上商城</tn-nav-bar>
    <view class="page_diy page_online_mall" id="online_mall_details">
    
																							      <view class="warp info-wrapper">
        <view class="container-fluid">
          <view class="row">
              <view v-if="$check_field('get', 'business_user')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>商家用户</span>
                </view>
                    <view class="diy_field diy_uid">
                  {{ get_user_business_user(obj['business_user']) }}
                </view>
                </view>
                <view v-if="$check_field('get', 'commodity_specifications')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>商品规格</span>
                </view>
                    <view class="diy_field diy_text">
                  <span>
                                          {{ obj["commodity_specifications"] }}                                      </span>
                </view>
                </view>
            </view>
        </view>
      </view>

	<view class="kb-row">


      <view class="kb-wrap" v-if="user.user_id">
        <view class="me-btn" @click="openSupportModal">
          在线沟通
        </view>
      </view>
	</view>





    </view>
  </view>
</template>

<script>
  import mixin from '@/libs/mixins/page.js';

  export default {
    mixins: [mixin],
    components: {
    },
    data() {
      return {
        url_get_obj: '~/api/online_mall/get_obj?',
        field: 'online_mall_id',
        query: {
                online_mall_id: 0,
        },
        // 商品详情初始化
        obj: {
          online_mall_id: 0,
              business_user: 0,
                  commodity_specifications: "",
          create_by: 0, // 创建用户ID
        },
          // 用户列表
        list_user_business_user: [],
          };
    },
    methods: {
    // #ifdef MP-WEIXIN
    preview_file(flie) {
      uni.downloadFile({
        url: flie,
        success: function (res) {
          var filePath = res.tempFilePath;
          uni.openDocument({
            filePath: filePath,
            showMenu: true,
            success: function (res) {
              console.log('打开文档成功');
            }
          });
        }
      });
    },
    // #endif
      openUrl(url) {
        uni.navigateTo({
          url: `/pages/webview/webview?url=${url}`,
        });
      },
      /**
       * 获取对象之后
       * @param {Object} json 结果对象
       */
      get_obj_after(json) {
        // 判断是否获取到数据
        if (this.obj) {
          var obj = this.obj;
                }
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
                      openSupportModal() {
        uni.navigateTo({
          url: `/pagesA/support/index?viewType=chat&receiverID=${this.obj.create_by}`,
        });
      },
    },
    created() {
          this.get_list_user_business_user();
            },

    mounted() {
    },
    onShow() {
    },
  };
</script>

<style lang="scss" scoped>
  @import 'styles/pagesC/index.scss';
  .score_statistics_chart {
    display: flex;
    padding: 0 10px;
  }
  .score_num_text {
    font-size: 24px;
    margin-right: 5px;
  }
  .score_person_num {
    font-size: 12px;
    color: #6195bd;
    text-align: left;
  }
  .user_box {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .user_avatar {
    width: 40px !important;
    height: 40px !important;
    border-radius: 100%;
    margin-right: 10px
  }

  .user_info {
    display: flex;
    flex-direction: column;
    margin-right: 20px;
  }

  .user_nickname {
    font-size: 16px;
    color: #333;
    margin-bottom: 5px;
  }
  .user_fans {
    font-size: 12px;
    color: #999;
  }

  .item-btn {
    height: 26px;
    padding: 0 20px;
    border-radius: 4px;
    margin-right: 6px;
  }
  .item-btn-radius {
    height: 26px;
    padding: 0 10px;
    border-radius: 26px;
    margin-right: 6px;
    font-size: 12px;
  }
  .plain {
    color: #0079fe;
    background-color: transparent;
    border: 1px solid #0079fe;
  }

  .chek-box {
    width: 100%;
    margin-top: 20rpx;
    padding: 1.5rpx 1rpx;
    box-sizing: border-box;
  }

  .check-item-list .check-item-li .check-item {
    width: 100%;
    height: 80rpx;
    padding: 0 10rpx;
    margin: 10rpx 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    border: 1rpx solid #ccc;
  }

  .check-item-list .check-item-li .check-item .image-container {
    width: 70rpx;
    height: 70rpx;
    margin-right: 20rpx;
  }

  .check-item-list .check-item-li .check-item .check-item-title > p:nth-child(1) {
    font-size: 20rpx;
  }

  .check-item-list .check-item-li .check-item .check-item-title > div {
    margin-top: 10rpx;
  }

  .check-item-list .check-item-li .check-item-form {
    padding: 10rpx 0;
  }

  .check-item-list .check-item-li .check-item-form form .el-form-item {
    margin-bottom: 15rpx;
  }

  .check-item-complete {
    border: 1px solid #007bff !important;
    overflow: hidden;
    position: relative;

    &::before {
      content: '';
      position: absolute;
      right: 0;
      top: 0;
      border: 15rpx solid #007bff;
      border-bottom-color: transparent;
      border-left-color: transparent;
    }

    &::after {
      content: '';
      width: 8rpx;
      height: 4rpx;
      position: absolute;
      right: 3rpx;
      top: 3rpx;
      border: 2rpx solid #fff;
      border-bottom-color: transparent;
      border-left-color: transparent;
      transform: rotate(135deg);
    }
  }

  .check-box .check-box-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 15rpx;
  }
  .check-box-desc img{
    width: 100%;
  }
  .check-progress {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: start;
  }
</style>