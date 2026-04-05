<template>
  <uni-card margin="0" :isShadow="false">
    <view class="card-title-box">
      <text class="card-title">{{ title }}</text>
      <view @click="clickMore" v-if="url" class="more"> 更多 </view>
    </view>
    <slot></slot>
  </uni-card>
</template>
<script>
import tabbar from '@/libs/mixins/tabbar.js';
export default {
  mixins: [tabbar],
  name: 'CardList',
  props: {
    title: {
      type: String,
      default: '',
    },
    url: {
      type: String,
      default: '',
    },
	toTab: {
	  type: Boolean,
	  default: false,
	},
  },
  methods: {
    clickMore() {
      let url_to_tab = false
      for (let i = 0;i<this.tabbarList.length;i++){
        if (this.tabbarList[i].pagePath === this.url) {
          url_to_tab = true
        }
      }
      if (this.toTab || url_to_tab) {
        this.$navToTab(this.url);
      } else {
        // if(this.url == '/pages/article/index'){
        //   this.url = '/pages/article/list'
        // }
        this.$navTo(this.url);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@import 'styles/components/common.scss';
</style>
