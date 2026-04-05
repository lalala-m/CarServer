<template>
  <view>
    <tn-nav-bar>{{ currentView === 'list' ? '聊天列表' : receiver.nickname }}</tn-nav-bar>
    <view class="support_module">
      <!-- 聊天列表 -->
      <view class="list-content" v-if="currentView === 'list'">
        <view class="list-box">
          <view class="list-header">聊天列表</view>
          <view v-for="item in chatGroupList" :key="item.customer_chat_group_id"
            :class="currentChat === item.customer_chat_group_id ? 'current-chat' : ''" @click="enterChat(item)"
            class="chat-item">
            <view class="chat-avatar">
              <image :src="$fullImgUrl(getOtherAvatar(item.member))" />
            </view>
            <view class="chat-info">
              <view class="chat-top">
                <text class="chat-name">{{ getOtherNickname(item.member) }}</text>
                <text class="chat-time">{{ formatLastTime(item.last_time) }}</text>
              </view>
              <view class="chat-bottom">
                <text class="last-msg">{{ getLastMessage(item) }}</text>
                <text class="unread-count" v-if="item.unread_count > 0">{{ item.unread_count }}</text>
              </view>
            </view>
          </view>
        </view>
      </view>

      <!-- 聊天内容 -->
      <view class="chat-content" style="padding-top: 50px;" v-else>
        <view class="container">
          <scroll-view :scroll-top="scrollTop" scroll-y="true" class="content support_content" @scroll="scroll"
            v-if="chatList">
            <view v-for="(item, index) in chatList.content" :key="index">
              <view class="item item-center">
                <text>{{ $toTime(item.creat_time, 'yyyy-MM-dd hh:mm:ss') }}</text>
              </view>
              <view class="item" :class="{
                item_left: item.sender_id !== userInfo.user_id,
                item_right: item.sender_id == userInfo.user_id,
              }">
                <view class="avatar" v-if="item.sender_id !== userInfo.user_id">
                  <image :src="$fullImgUrl(receiver.avatar)" />
                </view>
                <view class="bubble" :class="{ 'bubble-right': item.sender_id === userInfo.user_id }">{{ item.msg }}
                </view>
                <view class="avatar" v-if="item.sender_id === userInfo.user_id">
                  <image :src="$fullImgUrl(userInfo.avatar)" />
                </view>
              </view>
            </view>
          </scroll-view>

          <view class="input-area">
            <textarea name="text" style="height: 50px" v-model="sendValue" id="textarea" placeholder="输入内容"></textarea>
          </view>
          <view class="button-area">
            <span class="send-btn" @tap.stop="sendChat">发 送</span>
          </view>
        </view>
      </view>
    </view>

    <!-- 返回按钮 -->
    <view class="back-btn" v-if="currentView === 'chat'" @click="backToList">
      <text class="back-icon">‹</text>
    </view>
  </view>
</template>

<script>
import mixin from '@/libs/mixins/page.js';
export default {
  mixins: [mixin],
  data() {
    return {
      currentView: 'list', // 'list' 或 'chat'
      isSmall: false,
      sendValue: '',
      chatList: null,
      scrollTop: 0,

      chatGroupList: null,
      currentChat: null,
      receiver: {
        user_id: 1,
        avatar: '/upload/admin_avatar.jpg',
        nickname: '管理员',
      },
    };
  },
  onLoad(args) {
    // 根据参数决定默认视图
    if (args?.viewType === 'chat') {
      this.currentView = 'chat';
      if (args?.receiverID) {
        this.initReceiver(args.receiverID);
        this.$nextTick(() => {
          this.checkAndCreateGroup(args.receiverID);
        });
      }
    } else {
      this.currentView = 'list';
    }

    this.getChatGroup();
  },
  onBackPress() {
    if (this.currentView === 'chat') {
      this.backToList();
      return true; // 阻止默认返回行为
    }
    return false;
  },
  methods: {
    async checkAndCreateGroup(receiverID) {
      try {
        // 先获取当前用户的聊天群组
        await this.getChatGroup();

        // 等待一下确保 chatGroupList 已更新
        await this.$nextTick();

        // 检查是否已存在与接收者的聊天群组
        const existingGroup = this.chatGroupList?.find(group =>
          group.member?.some(m => parseInt(m.user_id) === parseInt(receiverID))
        );

        if (existingGroup) {
          // 如果已存在群组，直接进入聊天
          console.log('找到已存在的群组:', existingGroup);
          this.enterChat(existingGroup);
        } else {
          // 如果不存在，创建新群组
          console.log('未找到群组，创建新群组');
          this.addChatGroup();
        }
      } catch (error) {
        console.error('检查创建群组失败:', error);
      }
    },
    initReceiver(id) {
      return this.$get('~/api/user/get_obj?user_id=' + id).then((res) => {
        let receiver = {
          user_id: res.result.obj.user_id,
          avatar: res.result.obj.avatar,
          nickname: res.result.obj.nickname,
        };
        this.receiver = receiver;
        return receiver; // 返回 promise 以便链式调用
      });
    },
    // 进入聊天
    async enterChat(data) {
      this.currentChat = data.customer_chat_group_id;
      this.setReceiver(data);

      // 立即更新本地未读状态
      if (this.chatGroupList && Array.isArray(this.chatGroupList)) {
        const chatGroup = this.chatGroupList.find(item =>
          parseInt(item.customer_chat_group_id) === parseInt(data.customer_chat_group_id)
        );
        if (chatGroup) {
          chatGroup.unread_count = 0;  // 这里要确保字段名正确
        }
      }

      await Promise.all([
        this.markAsRead(data.customer_chat_group_id),
        this.getChat()
      ]);

      this.currentView = 'chat';
    },

    // 返回列表
    backToList() {
      this.currentView = 'list';
      this.getChatGroup(); // 刷新列表更新未读状态
    },

    async getChatGroup() {
      try {
        const res = await this.$get('~/api/customer_chat_group/get_list');
        if (!res.result) return;

        const userChatGroups = await Promise.all(
          res.result.list.map(async (item) => {
            const member = JSON.parse(item.member);
            return await this.processChatGroup(item, member);
          })
        );

        const filteredGroups = userChatGroups.filter((item) =>
          item.member && Array.isArray(item.member) &&
          item.member.some((m) => m && parseInt(m.user_id) === parseInt(this.userInfo.user_id))
        );

        this.chatGroupList = filteredGroups.sort((a, b) => new Date(b.last_time) - new Date(a.last_time));

        // 如果当前是聊天视图且有接收者，自动进入对应聊天
        if (this.currentView === 'chat' && this.receiver.user_id && this.receiver.user_id !== 1) {
          const targetChat = filteredGroups.find(group =>
            group.member.some(m => parseInt(m.user_id) === parseInt(this.receiver.user_id))
          );

          if (targetChat) {
            await this.enterChat(targetChat);
          } else {
            // await this.addChatGroup();
          }
        }
      } catch (error) {
        console.error("获取聊天组失败:", error);
      }
    },

    async processChatGroup(item, member) {
      try {
        const chatRes = await this.$get(`~/api/customer_chat_log/get_obj?customer_chat_id=${item.customer_chat_group_id}`);
        let last_time = item.update_time;
        let last_message = '';

        if (chatRes.result && chatRes.result.obj) {
          const content = JSON.parse(chatRes.result.obj.content);
          if (content && content.length > 0) {
            const lastMsg = content[content.length - 1];
            last_time = lastMsg.creat_time || item.update_time;
            last_message = lastMsg.msg || '';
          }
        }

        // 计算当前用户的未读消息数（Vue2版本的正负数逻辑）
        let displayUnreadCount = 0;
        const currentUserIndex = member.findIndex(m =>
          parseInt(m.user_id) === parseInt(this.userInfo.user_id)
        );

        if (currentUserIndex === 0 && item.unread_count > 0) {
          // 当前用户是第一位，显示正数未读
          displayUnreadCount = item.unread_count;
        } else if (currentUserIndex === 1 && item.unread_count < 0) {
          // 当前用户是第二位，显示负数未读的绝对值
          displayUnreadCount = Math.abs(item.unread_count);
        }

        return {
          ...item,
          member: member || [],
          last_time,
          last_message,
          unread_count: displayUnreadCount,  // 显示用的未读数
          original_unread_count: item.unread_count  // 保留数据库原始值
        };
      } catch (error) {
        console.error('处理聊天组失败:', error);
        return {
          ...item,
          member: member || [],
          last_time: item.update_time,
          last_message: '',
          unread_count: 0,
          original_unread_count: item.unread_count
        };
      }
    },

    getOtherAvatar(members) {
      const other = members.find((m) => m.user_id !== this.userInfo.user_id);
      return other ? other.avatar : "/upload/default_avatar.jpg";
    },

    formatLastTime(time) {
      if (!time) return '';
      const now = new Date();
      const msgTime = new Date(time);
      const isToday = now.toDateString() === msgTime.toDateString();

      if (isToday) {
        return this.$toTime(time, 'hh:mm');
      } else {
        return this.$toTime(time, 'MM-dd');
      }
    },

    getLastMessage(item) {
      return item.last_message || '[暂无消息]';
    },

    addChatGroup() {
      if (parseInt(this.receiver.user_id) === parseInt(this.userInfo.user_id)) {
        return;
      }
      // if (this.userInfo.user_id === 1) return;

      let member = JSON.stringify([
        this.receiver,
        {
          avatar: this.userInfo.avatar,
          user_id: this.userInfo.user_id,
          nickname: this.userInfo.nickname,
        },
      ]);

      this.$post("~/api/customer_chat_group/add", { member }).then((res) => {
        if (res.result) {
          this.getChatGroup().then(() => {
            // 找到包含当前用户和接收者的聊天组
            const newChat = this.chatGroupList.find(item => {
              if (!item.member || !Array.isArray(item.member)) return false;

              const hasCurrentUser = item.member.some(m =>
                m && parseInt(m.user_id) === parseInt(this.userInfo.user_id)
              );
              const hasReceiver = item.member.some(m =>
                m && parseInt(m.user_id) === parseInt(this.receiver.user_id)
              );

              return hasCurrentUser && hasReceiver;
            });

            if (newChat) {
              console.log('找到新聊天组，ID:', newChat.customer_chat_group_id);
              this.currentChat = newChat.customer_chat_group_id;
              this.enterChat(newChat);
            }
          });
        }
      }).catch(error => {
        console.error("创建聊天组失败:", error);
      });
    },

    getChat() {
      this.$get(
        `~/api/customer_chat_log/get_obj?customer_chat_id=${this.currentChat}`
      ).then((res) => {
        if (res.result) {
          if (res.result.obj) {
            res.result.obj.content = JSON.parse(res.result.obj.content);
          }
          this.chatList = res.result.obj;

          this.$nextTick(() => {
            setTimeout(() => {
              this.scrollTop = 99999;
            }, 100);
          });
        }
      });
    },

    async markAsRead(chatGroupId) {
      try {
        const currentGroup = this.chatGroupList.find(item =>
          parseInt(item.customer_chat_group_id) === parseInt(chatGroupId)
        );

        if (currentGroup) {
          const currentUserIndex = currentGroup.member.findIndex(m =>
            parseInt(m.user_id) === parseInt(this.userInfo.user_id)
          );
          let newUnreadCount = currentGroup.original_unread_count || currentGroup.unread_count;
          if (currentUserIndex === 0 && newUnreadCount > 0) {
            // 当前用户是第一位，有正数未读，清零
            newUnreadCount = 0;
          } else if (currentUserIndex === 1 && newUnreadCount < 0) {
            // 当前用户是第二位，有负数未读，清零
            newUnreadCount = 0;
          } else {
            return;
          }

          // 更新服务器状态
          await this.$post(`~/api/customer_chat_group/set?customer_chat_group_id=${chatGroupId}`, {
            last_read_time: new Date().toISOString().slice(0, 19).replace('T', ' '),
            unread_count: newUnreadCount
          });

          // 更新本地状态
          if (this.chatGroupList && Array.isArray(this.chatGroupList)) {
            const chatGroup = this.chatGroupList.find(item =>
              parseInt(item.customer_chat_group_id) === parseInt(chatGroupId)
            );
            if (chatGroup) {
              chatGroup.unread_count = 0; // 显示用的未读数清零
              chatGroup.original_unread_count = newUnreadCount; // 原始值也更新
            }
          }
        }
      } catch (error) {
        console.error('标记已读失败:', error);
      }
    },

    async sendChat() {
      if (!this.sendValue.trim()) return;

      let content = [];
      if (this.chatList && this.chatList.content) {
        content = this.chatList.content;
      }

      const newMsg = {
        sender_id: this.userInfo.user_id,
        msg: this.sendValue,
        creat_time: new Date().getTime(),
      };

      content.push(newMsg);

      let obj = {
        content: JSON.stringify(content),
      };

      try {
        if (this.chatList) {
          await this.$post(`~/api/customer_chat_log/set?customer_chat_id=${this.currentChat}`, obj);
        } else {
          obj.customer_chat_id = this.currentChat;
          await this.$post(`~/api/customer_chat_log/add`, obj);
        }

        // 先重新获取最新的聊天组数据
        await this.getChatGroup();

        // 再从更新后的 chatGroupList 中获取 currentGroup
        const currentGroup = this.chatGroupList.find(item =>
          parseInt(item.customer_chat_group_id) === parseInt(this.currentChat)
        );

        if (currentGroup) {
          const currentUserIndex = currentGroup.member.findIndex(m =>
            parseInt(m.user_id) === parseInt(this.userInfo.user_id)
          );

          let newUnreadCount;
          // 使用 original_unread_count 而不是 unread_count
          const currentUnread = currentGroup.original_unread_count || currentGroup.unread_count;

          if (currentUserIndex === 0) {
            newUnreadCount = parseInt(currentUnread || 0) - 1;
          } else {
            newUnreadCount = parseInt(currentUnread || 0) + 1;
          }

          await this.$post(`~/api/customer_chat_group/set?customer_chat_group_id=${this.currentChat}`, {
            unread_count: newUnreadCount
          });
        }

        // 最后重新获取数据
        await Promise.all([
          this.getChat(),
          this.getChatGroup()
        ]);

        this.sendValue = "";
      } catch (error) {
        console.error('发送消息失败:', error);
      }
    },

    setReceiver(data) {
      const otherUser = data.member.find((item) => item.user_id !== this.userInfo.user_id);
      if (otherUser) {
        this.$get("~/api/user/get_obj?user_id=" + otherUser.user_id).then((res) => {
          if (res.result && res.result.obj) {
            this.receiver = res.result.obj;
          }
        });
      }
    },

    getOtherNickname(members) {
      const other = members.find((m) => m.user_id !== this.userInfo.user_id);
      return other ? other.nickname : "未知用户";
    },
  },
};
</script>

<style lang="scss" scoped>
.support_module {
  height: calc(100vh - 100rpx);
}

.list-content {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  background: #fff;

  .list-box {
    width: 100%;
    height: 100%;
    overflow-y: auto;

    .list-header {
      width: 100%;
      height: 80rpx;
      border-bottom: 1rpx solid #f0f0f0;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 32rpx;
      font-weight: bold;
      position: sticky;
      top: 0;
      background: #fff;
      z-index: 1;
    }
  }
}

.chat-content {
  width: 100%;
  height: calc(100vh - 50px);
}

.chat-item {
  display: flex;
  padding: 20rpx;
  cursor: pointer;
  border-bottom: 1rpx solid #f5f5f5;
  align-items: center;

  &:hover {
    background-color: #f9f9f9;
  }

  &.current-chat {
    background-color: #f0f8ff;
  }
}

.chat-avatar {
  width: 80rpx;
  height: 80rpx;
  margin-right: 20rpx;

  image {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
}

.chat-info {
  flex: 1;
  min-width: 0;
}

.chat-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8rpx;
}

.chat-name {
  font-weight: bold;
  font-size: 28rpx;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
}

.chat-time {
  font-size: 22rpx;
  color: #999;
  flex-shrink: 0;
  margin-left: 20rpx;
}

.chat-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.last-msg {
  font-size: 24rpx;
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
}

.unread-count {
  background-color: #ff3b30;
  color: white;
  border-radius: 20rpx;
  padding: 4rpx 12rpx;
  font-size: 20rpx;
  min-width: 36rpx;
  text-align: center;
  margin-left: 16rpx;
}

.support_module .container {
  width: 100%;
  height: 100%;
  background-color: #f5f5f5;
  position: relative;
  display: flex;
  flex-direction: column;
}

.support_module .content {
  flex: 1;
  width: 100%;
  overflow-y: auto;
  padding: 20rpx;
  box-sizing: border-box;
}

.support_module .bubble {
  max-width: 400rpx;
  padding: 20rpx;
  border-radius: 10rpx;
  position: relative;
  color: #000;
  word-wrap: break-word;
  word-break: normal;
  font-size: 28rpx;
}

.support_module .item_left .bubble {
  margin-left: 20rpx;
  background-color: #fff;
}

.support_module .item_left .bubble:before {
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  border-left: 10rpx solid transparent;
  border-top: 10rpx solid transparent;
  border-right: 10rpx solid #fff;
  border-bottom: 10rpx solid transparent;
  left: -20rpx;
}

.support_module .item_right .bubble {
  margin-right: 20rpx;
  background-color: #9eea6a;
}

.support_module .item_right .bubble:before {
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  border-left: 10rpx solid #9eea6a;
  border-top: 10rpx solid transparent;
  border-right: 10rpx solid transparent;
  border-bottom: 10rpx solid transparent;
  right: -20rpx;
}

.support_module .item {
  margin-top: 30rpx;
  display: flex;
  width: 100%;
}

.support_module .item.item_right {
  justify-content: flex-end;
}

.support_module .item.item-center {
  justify-content: center;
}

.support_module .item.item-center text {
  font-size: 24rpx;
  padding: 8rpx 16rpx;
  color: #fff;
  background-color: #dadada;
  border-radius: 6rpx;
}

.support_module .avatar image {
  width: 84rpx;
  height: 84rpx;
  border-radius: 50%;
}

.support_module .input-area {
  border-top: 0.5px solid #e0e0e0;
  width: 100%;
  height: 200rpx;
  background-color: #fff;
  position: absolute;
  bottom: 0;
}

.support_module textarea {
  flex: 1;
  padding: 20rpx;
  font-size: 28rpx;
  border: none;
  overflow-y: auto;
  overflow-x: hidden;
  outline: none;
  resize: none;
  width: 100%;
  height: 100%;
  min-height: 100%;
  max-height: 100%;
  box-sizing: border-box;
}

.support_module .button-area {
  display: flex;
  height: 100rpx;
  line-height: 100rpx;
  padding: 5px;
  justify-content: flex-end;
  text-align: right;
  width: 100%;
  background: #fff;
  position: absolute;
  bottom: -30px;
}

.support_module .button-area .send-btn {
  width: 160rpx;
  height: 60rpx;
  line-height: 60rpx;
  border: none;
  outline: none;
  border-radius: 8rpx;
  float: right;
  cursor: pointer;
  background: #9eea6a;
  font-size: 28rpx;
  color: #333;
  text-align: center;
  display: block;
}

.back-btn {
  position: fixed;
  top: 100rpx;
  left: 20rpx;
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.back-icon {
  color: white;
  font-size: 48rpx;
  font-weight: bold;
}

.support_content {
  padding-bottom: 130px !important;
}
</style>