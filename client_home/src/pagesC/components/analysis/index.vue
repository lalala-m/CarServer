<template>
  <view>
    <view class="me-btn btn-reset" @click="aiHandle" v-if="showBtn">{{ btnText }}</view>
    <!-- HTML展示模式 -->
    <view class="analysis-content" v-if="mode === 'anlHtml'">
      <view v-html="renderMD(answerMD)"></view>
    </view>

    <!-- 弹窗展示模式 -->

    <uni-popup v-if="mode === 'anlDialog'" ref="popup" maskClick is-mask-click>
      <view class="dialog-content">
        <view class="dialog-header">
          <text class="dialog-title">数据分析</text>
          <uni-icons type="close" size="24" @click="dialogClose" class="dialog-close"></uni-icons>
        </view>
        <view class="dialog-body">
          <view v-html="renderMD(answerMD)"></view>
        </view>
        <view class="dialog-footer">
          <button @click="dialogClose">确 定</button>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script>
import MarkdownIt from 'markdown-it';
export default {
  props: {
    content: {
      type: [Object, Array, String],
      default: null,
    },
    prompt: {
      type: String,
      default:
        '根据我提供的数据进行分析，请分析数据并总结关键信息，要求：只返回分析结果，不要解释分析过程，用简洁的语言描述。数据如下：',
    },
    mode: {
      type: String,
      default: 'anlHtml',
      validator: (value) => ['anlHtml', 'anlDialog','data'].includes(value),
    },
    btnText: {
      type: String,
      default: '数据分析',
    },
  },
  data() {
    return {
      answerMD: '',
      md: new MarkdownIt(),
      showBtn: true,
    };
  },
  methods: {
    async aiHandle() {
      if (this.mode === 'anlHtml') {
        this.showBtn = false;
      }
      uni.showLoading({
        title: '分析中',
      });
      try {
        let res = await this.$qwenPost({
          model: 'qwen-turbo',
          messages: [
            {
              role: 'user',
              content: this.prompt + (this.content ? JSON.stringify(this.content) : ''),
            },
          ],
        });
        this.answerMD = res?.choices?.[0]?.message?.content || '分析失败，请重试';

        if (this.mode === 'anlDialog') {
          this.$refs.popup.open('center');
        }

        this.$emit('analysis-event', this.answerMD);
      } catch (e) {
        this.answerMD = '请求失败：' + (e.message || '未知错误');
        console.error('AI分析错误:', e);
      } finally {
        uni.hideLoading();
      }
    },
    renderMD(data) {
      return this.md.render(data || '');
    },
    dialogClose() {
      this.$refs.popup.close();
    },
  },
};
</script>

<style scoped lang="scss">
.analysis-content {
  min-height: 200rpx;
  padding: 20rpx;
  margin: 10rpx;
  background-color: #fff;
  border-radius: 8rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.dialog-content {
  width: 100%;
  max-width: 600rpx;
  background-color: #fff;
  border-radius: 16rpx;
  overflow: hidden;
  
  .dialog-header {
    padding: 20rpx;
    border-bottom: 1rpx solid #eee;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .dialog-title {
      font-size: 32rpx;
      font-weight: bold;
    }

    .dialog-close {
      color: #999;
    }
  }

  .dialog-body {
    padding: 30rpx;
    min-height: 300rpx;
    max-height: 60vh;
    overflow-y: auto;
  }

  .dialog-footer {
    padding: 20rpx;
    border-top: 1rpx solid #eee;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
