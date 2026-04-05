<template>
  <scroll-view class="robotic-container" scroll-y>
    <tn-nav-bar>{{ title }}</tn-nav-bar>
    <view class="robotic-content">
      <view
        v-for="(item, index) in msgList"
        :key="index"
        :class="item.role === 'user' ? 'robotic-item-right' : 'robotic-item-left'"
        v-html="renderMD(item.content)"
      ></view>
      <view class="robotic-item-left" v-if="showLoading">
        <text class="tn-icon-loading"></text>
      </view>
    </view>
    <view class="robotic-input">
      <tn-input v-model="roboticVal" style="width: 85%" border type="textarea" :height="70" />
      <tn-button shape="round" backgroundColor="#0079fe" fontColor="#fff" @click="roboticSend">
        <text class="tn-icon-send"></text>
      </tn-button>
    </view>
  </scroll-view>
</template>
<script>
import MarkdownIt from 'markdown-it';
import mixin from '@/libs/mixins/page.js';
export default {
  mixins: [mixin],
  data() {
    return {
      roboticVal: '',
      msgList: [],
      showLoading: false,
      md: new MarkdownIt(),
    };
  },
  methods: {
    roboticSend() {
      let content = this.roboticVal;
      this.msgList.push({
        content: content,
        role: 'user',
      });
      this.roboticVal = '';
      this.showLoading = true;
      this.$qwenPost({
        model: 'qwen-turbo',
        messages: [
          {
            role: 'user',
            content,
          },
        ],
      }).then((res) => {
        this.showLoading = false;
        let message = res.choices[0].message;
        this.msgList.push(message);
      });
    },
    renderMD(data) {
      return this.md.render(data);
    },
    onLoad(e) {
      this.title = decodeURIComponent(e.title) || '智能客服聊天';
    }
  },
};
</script>
<style lang="scss" scoped>
.robotic-container {
  width: 100vw;
  height: 100vh;
  position: relative;
  .robotic-content {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 100rpx;
    padding: 0 10rpx;
    padding-bottom: 200rpx;
    box-sizing: border-box;
    > view {
      min-width: 65rpx;
      display: inline-block;
      padding: 15rpx;
      box-sizing: border-box;
      border-radius: 25rpx;
      margin: 10rpx 0;
      font-size: 24rpx;
    }
    .robotic-item-left {
      margin-right: auto;
      background-color: #f0f0f0;
    }
    .robotic-item-right {
      background-color: #0079fe;
      color: #fff;
      margin-left: auto;
    }
  }
  .robotic-input {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    position: fixed;
    bottom: 0;
    left: 0;
    padding: 15rpx;
    padding-bottom: 50rpx;
    box-sizing: border-box;
    background-color: #fff;
  }
}
/* #ifndef H5 */
.robotic-container .robotic-content{
	padding-top: 100rpx;
}
/* #endif */
</style>
