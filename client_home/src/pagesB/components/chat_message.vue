<template>
  <view class="chat_message" :class="{ 'own-message': isOwn }">
    <view v-if="!isOwn" class="avatar">
      <image class="avatar-img" :src="avatar" mode="aspectFill"></image>
    </view>
    <view class="content" :class="isOwn ? 'flex-end' : 'flex-start'">
      <text class="username">{{ username }}</text>
      <view v-if="type === 'text'" class="text">
        <rich-text :nodes="replaceEmoji(content)"></rich-text>
      </view>
      <image
        v-if="type === 'image'"
        class="image"
        :src="$fullImgUrl(content)"
        mode="aspectFill"
      ></image>
      <view v-if="type === 'file'" class="file">
        <text>{{ content.split('|')[1] }}</text>
        <button size="mini" type="primary" @click="downloadFile(content)">下载</button>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    isOwn: Boolean,
    username: String,
    type: String, // 'text', 'image', 'file'
    content: String, // 文本内容、图片URL或文件下载URL
    avatar: String,
    fileName: String, // 文件名，仅文件类型消息需要
  },
  data() {
    return {
      emojiList: [
        {
          url: 'Expression_0.png',
          alt: '[微笑]',
        },
        {
          url: 'Expression_1.png',
          alt: '[撇嘴]',
        },
        {
          url: 'Expression_2.png',
          alt: '[眨眼]',
        },
        {
          url: 'Expression_3.png',
          alt: '[乖巧]',
        },
        {
          url: 'Expression_4.png',
          alt: '[色]',
        },
        {
          url: 'Expression_5.png',
          alt: '[呲牙]',
        },
        {
          url: 'Expression_6.png',
          alt: '[流泪]',
        },
        {
          url: 'Expression_7.png',
          alt: '[可爱]',
        },
        {
          url: 'Expression_8.png',
          alt: '[难过]',
        },
        {
          url: 'Expression_9.png',
          alt: '[失望]',
        },
        {
          url: 'Expression_10.png',
          alt: '[鼻涕]',
        },
        {
          url: 'Expression_11.png',
          alt: '[冷汗]',
        },
        {
          url: 'Expression_12.png',
          alt: '[调皮]',
        },
        {
          url: 'Expression_13.png',
          alt: '[开心]',
        },
        {
          url: 'Expression_14.png',
          alt: '[悲伤]',
        },
        {
          url: 'Expression_15.png',
          alt: '[流汗]',
        },
        {
          url: 'Expression_16.png',
          alt: '[酷]',
        },
        {
          url: 'Expression_17.png',
          alt: '[微笑]',
        },
        {
          url: 'Expression_18.png',
          alt: '[抓狂]',
        },
        {
          url: 'Expression_19.png',
          alt: '[皱眉]',
        },
        {
          url: 'Expression_20.png',
          alt: '[大笑]',
        },
        {
          url: 'Expression_21.png',
          alt: '[快乐]',
        },
        {
          url: 'Expression_22.png',
          alt: '[眯眼]',
        },
        {
          url: 'Expression_23.png',
          alt: '[衰]',
        },
        {
          url: 'Expression_24.png',
          alt: '[高兴]',
        },
        {
          url: 'Expression_25.png',
          alt: '[笑哭]',
        },
        {
          url: 'Expression_26.png',
          alt: '[学习]',
        },
        {
          url: 'Expression_27.png',
          alt: '[萌]',
        },
        {
          url: 'Expression_28.png',
          alt: '[闭嘴]',
        },
        {
          url: 'Expression_29.png',
          alt: '[飞吻]',
        },
        {
          url: 'Expression_30.png',
          alt: '[撇嘴]',
        },
        {
          url: 'Expression_31.png',
          alt: '[色眯眯]',
        },
        {
          url: 'Expression_32.png',
          alt: '[傲娇]',
        },
        {
          url: 'Expression_33.png',
          alt: '[不屑]',
        },
        {
          url: 'Expression_34.png',
          alt: '[尴尬]',
        },
        {
          url: 'Expression_35.png',
          alt: '[吃饱了]',
        },
      ],
    };
  },
  methods: {
    replaceEmoji(str) {
      const emojiList = this.emojiList;
      return str.replace(/\[([^(\]|\[)]*)\]/g, (item, index) => {
        for (let i = 0; i < emojiList.length; i++) {
          if (emojiList[i].alt === item) {
            let imgstr =
              '<img height="24px" style="vertical-align:middle;width: 24px!important" src="' +
              this.getEmojiUrl(emojiList[i].url) +
              '">';
            return imgstr;
          }
        }
      });
    },
    getEmojiUrl(str) {
      return require('@/static/emoji/' + this.replaceEmoji(str));
    },
    downloadFile(content) {
      // 实现文件下载逻辑
      const url = content.split('|')[0];
      // #ifdef H5
      this.$download(this.$fullUrl(url), content.split('|')[1]);
      // #endif
      // #ifdef MP-WEIXIN
      uni.downloadFile({
        url: this.$fullUrl(url),
        success: (res) => {
          if (res.statusCode === 200) {
            uni.saveFile({
              tempFilePath: res.tempFilePath,
              success: function (saveRes) {
                uni.showToast({
                  icon: 'none',
                  title: `已保存到${saveRes.savedFilePath}`,
                });
                console.log('保存文件成功', saveRes.savedFilePath);
              },
            });
          }
        },
      });
      // #endif
    },
  },
};
</script>

<style lang="scss" scoped>
@import 'styles/components/index.scss';
</style>
