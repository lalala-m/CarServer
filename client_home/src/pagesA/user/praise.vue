<template>
  <view class="page_user_collect" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>{{title}}</tn-nav-bar>
    <tn-tabs :list="tagList" name="name" :current="current" @change="tagChange"></tn-tabs>
    <view class="user_collect_wrapper" id="user_collect_wrapper">
      <view class="list_wrapper">
        <list_praise :list="list"></list_praise>
      </view>
    </view>
  </view>
</template>

<script>
import list_praise from '@/components/diy/list_praise.vue';
import mixin from '@/libs/mixins/page.js';
import { type_names } from '@/utils/typeManage.js'

export default {
  mixins: [mixin],
  components: {
    list_praise,
  },
  data() {
    return {
      title: '我的点赞',
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      url_get_list: '~/api/praise/get_list?like=0',
      query: {
        source_table: null,
      },
      list: [],
      tagList: type_names,

      current: 0,
    };
  },
  onLoad(options) {
    if (options && options.title) {
      this.title = decodeURIComponent(options.title);
    }
    this.query.user_id = this.userInfo.user_id;
  },
  methods: {
    tagChange(index) {
      this.current = index;
      this.query.source_table = this.tagList[index].value;
      this.get_list();
    },
  },
};
</script>

<style lang="scss" scoped>
.page_user_collect {
  padding-top: 90rpx;
  box-sizing: border-box;
}
</style>
