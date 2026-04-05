<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>创建群聊</tn-nav-bar>
    <view class="create_group">
      <uni-list :border="true">
        <!-- 显示圆形头像 -->
        <checkbox-group v-model="checkList" @change="onChange">
          <uni-list-chat
            v-for="(item, index) in friendList"
            :key="index"
            :avatar-circle="true"
            :title="item.nickname"
            :avatar="$fullImgUrl(item.avatar)"
          >
            <checkbox :value="item.friend_user_id"></checkbox>
          </uni-list-chat>
        </checkbox-group>
        <view
          style="display: flex; margin-top: 20px; justify-content: center"
          v-if="friendList.length <= 0 && type === 'add'"
          >您还没有好友，先去添加几个好友吧！</view
        >
        <view
          style="display: flex; margin-top: 20px; justify-content: center"
          v-if="friendList.length <= 0 && type === 'yaoqing'"
          >您还没有好友或者对方已经在群聊中</view
        >
      </uni-list>
      <uni-popup ref="popup">
        <chat-dialog mode="input" @confirm="createGroup"></chat-dialog>
      </uni-popup>
      <button @tap="startCreateGroup" type="primary" v-if="friendList.length > 0">
        {{ type === 'add' ? '创建群聊' : '添加成员' }}
      </button>
    </view>
  </view>
</template>

<script>
import chatDialog from './chat-dialog.vue';

export default {
  components: {
    chatDialog,
  },
  data() {
    return {
      type: '',
      friendList: [],
      group_name: '',
      checkList: [],
    };
  },
  onShow() {
    this.getFriendList();
  },
  onLoad(e) {
    this.type = e.type;
    this.friendList = [];
    const isAdd = this.type === 'add';
    if (this.type === 'add') {
      this.friendList = this.$store.state.chat.friendList;
    } else {
      const list = this.$store.state.chat.friendList;
      const groupChatUser = this.$store.state.chat.groupChatUser;

      for (let i = 0; i < list.length; i++) {
        let isAdd = false;
        for (let j = 0; j < groupChatUser.length; j++) {
          if (list[i].friend_user_id == groupChatUser[j].user_id) {
            isAdd = true;
          }
        }

        if (!isAdd) {
          this.friendList.push(list[i]);
        }
      }
    }
    this.friendList.forEach((item) => {
      item.friend_user_id = item.friend_user_id + '';
    });
  },
  methods: {
    startCreateGroup() {
      const isAdd = this.type === 'add';
      if (this.checkList.length <= 1 && isAdd) {
        uni.showToast({
          icon: 'none',
          title: '至少选择两个成员加入群聊',
        });
        return false;
      }
      console.log(this.type);
      if (this.type === 'add') {
        this.$refs.popup.open();
      } else {
        this.createGroup();
      }
    },
    getFriendList() {
      this.$get('~/api/user_chat_friend/get_friend_list?user_id=' + this.userInfo.user_id).then(
        (res) => {
          this.$store.commit('chat/set_friendList', res.result.list || []);
        }
      );
    },
    async createGroup(e) {
      const isAdd = this.type === 'add';
      this.group_name = e;
      if (!this.group_name && isAdd) {
        uni.showToast({
          icon: 'none',
          title: '请输入群聊名称',
        });
        return false;
      }

      const group_id = isAdd
        ? Math.floor(new Date().getTime() / 1000)
        : this.$store.state.chat.currentChatObj.group_id;

      this.checkList.push(this.userInfo.user_id);

      for (let i = 0; i < this.checkList.length; i++) {
        await this.$post('~/api/user_chat_group/add_group', {
          group_name: isAdd ? this.group_name : this.$store.state.chat.currentChatObj.group_name,
          user_id: this.checkList[i],
          group_id: group_id,
        });
      }
      const arr = this.$store.state.chat.groupList;
      const recordList = this.$store.state.chat.recordList;
      console.log(1);
      for (let i = 0; i < arr.length; i++) {
        const id = arr[i].friend_user_id || arr[i].group_id;
        let isExist = false;
        for (let j = 0; j < recordList.length; j++) {
          if (id === recordList[j].id) {
            isExist = true;
          }
        }
        if (!isExist) {
          recordList.push({
            id: id,
            list: [],
          });
        }
      }
      this.$store.commit('chat/set_recordList', recordList);
      if (this.type === 'yaoqing') {
        this.$get('~/api/user_chat_group/get_group_user_list', {
          group_id: this.$store.state.chat.currentChatObj.group_id,
        }).then((res) => {
          this.$store.commit('chat/set_groupChatUser', res.result.list);
        });
      }

      uni.showToast({
        title: isAdd ? '创建成功' : '添加成功',
      });
      uni.navigateBack();
    },
    onChange(e) {
      this.checkList = e.target.value;
    },
  },
};
</script>

<style lang="scss" scoped>
@import 'styles/pagesB/index.scss';
</style>
