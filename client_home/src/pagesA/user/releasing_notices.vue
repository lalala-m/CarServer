<template>
  <view class="page_user_home" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>通知中心</tn-nav-bar>
    <view class="releasing_notices" id="releasing_notices">
      <view class="list_wrapper">
        <view class="user-content">
          <v-tabs v-model="current" :tabs="tabs" @change="changeTab" :z-index="9"></v-tabs>
          <view style="display: flex;justify-content: end;color: #0079fe;">
            <text style="margin-right: 10px;" @click="readAll">全部已读</text>
            <text @click="clearAll">删除</text>
          </view>
          <view class="tab-content">
            <view v-for="item in messageInformList" :key="item.message_inform_id" @click="setState(item)" style="margin-bottom: 10px;">
              <uni-badge style="width: 100%;" size="small" :is-dot="true" :text="Number(item.state)" absolute="rightTop" type="error">
                <view style="width: 100%;font-size: 14px;font-weight: 700;color:#333">{{item.title}}</view>
              </uni-badge>
              <view style="font-size: 14px;color: #999;white-space: pre-line;">{{item.content}}</view>
              <view style="font-size: 14px;color: #999;">{{ $toTime(item.create_time, "yyyy-MM-dd hh:mm:ss") }}</view>
            </view>
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
  components: {},
  data() {
    return {
      // 登录权限
      tabs: ['通知', '系统', '互动', '私信', '消息'],
      current: 0,
      messageType: "通知",
      messageInformList: [],
      messageInformListAll: [],
      msgList: [],
      msgSum: 0,
      getMessageInformListTimer: null,
    };
  },
  methods: {
    async changeTab(index) {
      console.log('当前选中的项：' + index);
      this.messageType = this.tabs[index]
      if(this.tabs[index] == '消息') {
        this.messageInformList = this.msgList
        return
      }
      let res = await this.$get("~/api/message_inform/get_list?user_id=" + this.user.user_id, {
        type: this.tabs[index],
      })
      this.messageInformList = res.result.list
    },
    async getMessageInformList() {
      if(this.userInfo.user_group == '管理员') {
        let list = await this.$get("~/api/message_inform/get_list",{type: '消息'})
        this.msgList = list.result.list
      }
      let listAll = await this.$get("~/api/message_inform/get_list?user_id=" + this.userInfo.user_id)
      this.messageInformListAll = [...listAll.result.list,...this.msgList]
      this.msgSum = 0
      this.messageInformListAll.forEach(item => {
        if(item.state == 1) {
          this.msgSum++
        }
      })
      let arr = this.messageInformListAll.map(item => {
        return item.type
      })
      // 去重
      this.tabs = [...new Set(arr)]
      
      let list = await this.$get("~/api/message_inform/get_list?user_id=" + this.userInfo.user_id,{type: this.messageType})
      if(this.messageType == '消息') {
        this.messageInformList = this.msgList
      }else {
        this.messageInformList = list.result.list
      }
      this.changeTab(0)
    },
    async setState(item) {
      await this.$post('~/api/message_inform/set?message_inform_id=' + item.message_inform_id,{state: 0})
      this.getMessageInformList()
    },
    readAll() {
      this.messageInformList.forEach(async item => {
        await this.$post('~/api/message_inform/set?message_inform_id=' + item.message_inform_id,{state: 0})
      })
      this.getMessageInformList()
    },
    clearAll() {
      this.messageInformList.forEach(async item => {
        await this.$get('~/api/message_inform/del?message_inform_id=' + item.message_inform_id)
      })
      this.getMessageInformList()
    },
  },
  onLoad(options) {
    
  },
  mounted() {
    // 通知功能开始
    this.getMessageInformListTimer && clearTimeout(this.getMessageInformListTimer)
    this.getMessageInformListTimer = setTimeout(() => {
      this.getMessageInformList()
    },500)
    // 通知功能结束
  },
};
</script>

<style lang="scss" scoped>
.list_wrapper {
  padding: 20px 16px;
  .user-content {
    .tab-content {
      margin-top: 20px;
      padding: 0 11px;
    }
  }
}
</style>
