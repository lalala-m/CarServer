<template>
  <view class="authorization_weixin" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>微信授权登录</tn-nav-bar>
    <view class="aut-box">
      <view class="aut-h">
        <view class="img-box">
          <view class="img will"> </view>
          <view class="img handoff"> </view>
          <view class="img arrive"> </view>
        </view>
        <view class="texts">
          <text>{{ accname }}:</text>
          <text class="text-name">{{ Account }}</text>
        </view>
      </view>
      <view class="conter-box">
        <view class="text-bl text-title">
          <text class="text-itme">{{ titem }}</text>
        </view>
        <view class="text-bl">
          <view class="text-l"><p>获取你的名字和登录名</p></view>
          <view class="text-l"><p>获取你的企业名称</p></view>
          <view class="text-l"><p>获取你的昵称、头像、性别、地区和用户状态</p></view>
        </view>
      </view>
      <view class="btn-n-box">
        <button class="btn de-author" @click="open">确认授权</button>
        <button class="btn temporarily-author" @click="returns">暂不授权</button>
      </view>

      <uni-popup class="popups" ref="popup" :mask-click="false">
        <view class="centor">
          <view class="centor-c">
            <h2>微信授权登录</h2>
          </view>

          <view class="centor-c">
            <h4>申请获得以下权限</h4>
          </view>

          <view class="centor-c">
            <text>获取你的公开信息 (昵称、投降、地区以及性别)</text>
          </view>

          <view class="btns">
            <button class="btn bt-de-author" @click="close">取消授权</button>
            <button class="btn Confirm-author" @click="aut" :disabled="authorization">
              确认授权
            </button>
          </view>
        </view>
      </uni-popup>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      titem: '',
      accname: '',
      Account: 0,
      authorization: false,
    };
  },
  onReady() {},
  methods: {
    open() {
      this.$refs.popup.open('center');
    },
    close() {
      this.$refs.popup.close();
    },
    aut() {
      this.authorization = true;
      uni.reLaunch({
        url: '../account/login',
      });
    },
    returns() {
      uni.navigateBack({});
    },
  },
  onLoad(option) {
    this.Account = option.id;
    if (this.Account == 2) {
      this.titem = '你同意将以下信息授权给微信';
      this.accname = '微信账号';
    } else {
      uni.navigateBack({});
    }
  },
};
</script>

<style lang="scss" scoped>
@import 'styles/pagesB/index.scss';
</style>
