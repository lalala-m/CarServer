<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar></tn-nav-bar>
    <view class="chat_session">
      <view class="scroll_view">
        <scroll-view scroll-y class="scroll" ref="right_message" :scroll-top="scrollTop">
          <template v-for="item in messageList">
            <chatMessage
              :username="item.from_user_nickname"
              :content="item.content"
              :avatar="$fullImgUrl(item.avatar)"
              :type="item.type"
              :isOwn="item.me"
              :text="'你好'"
            ></chatMessage>
          </template>
        </scroll-view>
      </view>
      <view class="input_message_area">
        <view class="message_tool">
          <view style="display: flex">
            <image
              src="../../static/img/emoji.png"
              class="button-image"
              @click="$refs.emojiRef.open()"
            >
            </image>
            <!-- #ifdef MP-WEIXIN || H5 -->
            <uni-file-picker
              :auto-upload="false"
              :disable-preview="true"
              :limit="1"
              file-mediatype="all"
              @select="beforeUploadFile"
              ref="uploadFile"
            >
              <image src="../../static/img/file.png" class="button-image"></image>
            </uni-file-picker>
            <!-- #endif -->
            <!-- #ifdef APP-VUE -->
            <image
              src="../../static/img/file.png"
              class="button-image"
              @click="beforeUploadFile"
            ></image>
            <!-- #endif -->
            <image
              v-if="currentChatObj.group_id"
              @click="$refs.showRight.open()"
              src="../../static/img/group_persion_list.png"
              class="button-image"
            ></image>
          </view>
          <button
            type="primary"
            size="mini"
            style="margin: 0; margin-right: 10px"
            @click="sendMessage('text', textarea)"
          >
            发送
          </button>
        </view>
        <view class="text_input">
          <uni-easyinput type="textarea" v-model="textarea"></uni-easyinput>
        </view>
      </view>
      <uni-popup type="bottom" ref="emojiRef">
        <view class="emoji_box">
          <view
            style="padding: 2px; width: 34px; height: 34px"
            v-for="(item, idnex) in emojiList"
            :key="idnex"
            @click="joinEmoji(item.alt)"
          >
            <image class="emoji_image" :src="'/static/emoji/' + item.url" :alt="item.alt" />
          </view>
        </view>
      </uni-popup>
      <uni-drawer ref="showRight" mode="right">
        <view style="padding: 5px; display: flex; flex-direction: row; align-items: center">
          群成员<button size="mini" type="primary" @click="addUser">添加成员</button>
        </view>
        <scroll-view style="height: 100%" scroll-y="true">
          <uni-list :border="true">
            <!-- 显示圆形头像 -->
            <uni-list-chat
              clickable
              v-for="(item, index) in $store.state.chat.groupChatUser"
              :key="index"
              :avatar-circle="true"
              :title="item.nickname"
              :avatar="$fullImgUrl(item.avatar) || '/static/img/avatar.jpg'"
              @click="toChat(item)"
            >
            </uni-list-chat>
          </uni-list>
        </scroll-view>
      </uni-drawer>
    </view>
  </view>
</template>

<script>
import chatMessage from '@/pagesB/components/chat_message.vue';

export default {
  data() {
    return {
      visible: false,
      textarea: '',
      dialogVisible: false,
      isImage: false,
      filename: '',
      messageList: [],
      groupPersionList: [],
      scrollTop: 9999,
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
  components: {
    chatMessage,
  },
  onShow() {
    uni.setNavigationBarTitle({
      title: this.currentChatObj.nickname || this.currentChatObj.group_name,
    });
  },
  methods: {
    addUser() {
      uni.navigateTo({
        url: '/pagesB/chat/createGroup?type=yaoqing',
      });
    },
    getGroupPersionList() {
      if (!this.currentChatObj.group_id) return;
      this.$get('~/api/user_chat_group/get_group_user_list', {
        group_id: this.currentChatObj.group_id,
      }).then((res) => {
        this.$store.commit('chat/set_groupChatUser', res.result.list);
      });
    },
    selectFile(url) {
      uni.showLoading({
        title: '上传中',
        mask: true,
      });

      console.log(url);
      uni.uploadFile({
        url: this.$fullUrl('~/api/user_chat_friend/upload'),
        filePath: url,
        name: 'file',
        success: (uploadFileRes) => {
          const res = JSON.parse(uploadFileRes.data);
          this.uploadFileSuccess(res);
        },
        complete() {
          uni.hideLoading();
        },
      });
    },
    getList1() {
      if (!this.currentChatObj) {
        return false;
      }
      const currentChatObjId = this.currentChatObj.group_id || this.currentChatObj.friend_user_id;
      this.messageList = [];
      for (let i = 0; i < this.recordList.length; i++) {
        if (this.recordList[i].id === currentChatObjId) {
          this.messageList = this.recordList[i].list;
        }
      }
      console.log(this.messageList);

      this.$nextTick(() => {
        this.scrollTop += 1;
        this.scrollTop = this.scrollTop;
      });
    },
    beforeUploadFile(obj) {
      // #ifdef MP-WEIXIN || H5
      const file = obj.tempFiles[0].file;
      if (file.type.indexOf('image') > -1) {
        this.isImage = true;
      } else {
        this.isImage = false;
      }
      this.filename = file.name;
      this.selectFile(file.path);
      // #endif
      // #ifdef APP-VUE

      uni.chooseImage({
        count: 1,
        success: (res) => {
          (this.isImage = true), this.selectFile(res.tempFilePaths[0]);
        },
      });
      // #endif
    },
    uploadFileSuccess(res) {
      if (this.isImage) {
        this.sendMessage('image', res.result.url);
      } else {
        this.sendMessage('file', res.result.url + '|' + this.filename);
      }
      this.$refs.uploadFile.clearFiles();
    },
    getEmojiUrl(url) {
      return require('@/static/emoji/' + url);
    },
    joinEmoji(alt) {
      this.textarea += alt;
      console.log(this.$refs.emojiRef);
      this.$refs.emojiRef.close();
    },
    sendMessage(type, content) {
      if (this.textarea.trim() || type !== 'text') {
        const to = {};
        const message = {
          type: type,
          content: content,
          avatar: this.userInfo.avatar,
        };

        const currentChatObj = this.$store.state.chat.currentChatObj;

        if (currentChatObj.group_id) {
          to.type = 'group';
          to.group_id = currentChatObj.group_id;
          message.group_id = currentChatObj.group_id;
        } else {
          to.type = 'single';
          to.user_id = currentChatObj.friend_user_id;
        }

        message.from_user_id = this.userInfo.user_id;
        message.from_user_nickname = this.userInfo.nickname;

        this.send(message, to);

        //将自己发送的消息加入到聊天记录
        const recordList = this.$store.state.chat.recordList;
        const objId = currentChatObj.friend_user_id || currentChatObj.group_id;
        for (let i = 0; i < recordList.length; i++) {
          if (recordList[i].id === objId) {
            message.me = true;
            recordList[i].list.push(message);
          }
        }
        //保存到sessionStorage

        this.$store.commit('chat/set_recordList', recordList);
        uni.setStorageSync('recordList' + this.userInfo.user_id, JSON.stringify(recordList));
        console.log(this.$store.state.chat.recordList);
        this.textarea = '';
      }
    },
    send(message, to) {
      const obj = {
        message: message,
        user_id: this.userInfo.user_id,
        username: this.userInfo.nickname,
        to: to,
      };
      console.log(this.$store.state.chat.websocket);
      this.$store.state.chat.websocket.send({
        data: JSON.stringify(obj),
      });
    },
  },
  watch: {
    recordList: {
      handler() {
        this.getList1();
      },
      deep: true,
      immediate: true,
    },
  },
  computed: {
    recordList() {
      return this.$store.state.chat.recordList;
    },
    currentChatObj() {
      return this.$store.state.chat.currentChatObj;
    },
  },
};
</script>

<style lang="scss" scoped>
@import 'styles/pagesB/index.scss';
</style>
