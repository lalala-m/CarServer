<template>
  <view class="list_comment" v-if="list.length > 0">
    <view
      class="item_comment"
      v-for="(o, i) in sortList"
      :key="i"
      :to="'/pages/article/details?' + vm.comment_id + '=' + o[vm.comment_id]"
    >
      <view class="comment_box">
        <view class="left_block">
          <image
            class="image"
            :src="$fullImgUrl(o[vm.avatar]) || '/static/img/default.png'"
          ></image>
        </view>
        <view class="right_block">
          <view class="top_comment">
            <view class="nickname">
              {{ o[vm.nickname] }}
              <view class="nickname-sticky" v-if="o.sticky">置顶</view>
            </view>
            <view class="right">
              <view class="del" @click="delete_comment(o)" v-if="userInfo.user_id === o[vm.user_id] && $check_action('/comment/list', 'del')"> 删除</view>
              <view class="link" @click="$navTo(goto_edit(o))"> 回复</view>
              <text
                class="tn-icon-more-vertical right-options"
                @tap="showOptions(o)"
                v-if="self"
              ></text>
            </view>
          </view>
          <rich-text class="content" :nodes="$setRichTextImage(o[vm.content])"></rich-text>
          <view class="time">
            {{ $toTime(o[vm.create_time], 'yyyy-MM-dd hh:mm:ss') }}
          </view>
        </view>
      </view>

      <!-- 回复内容 -->
      <view class="list_reply_block" v-if="o.list_reply">
        <view class="list_reply_box" v-for="(obj, idx) in o.list_reply" :key="idx">
          <view v-if="!obj.hidden">
            <view class="list_reply">
              <view class="reply_left">
                <image
                  class="hf-image"
                  :src="$fullImgUrl(obj[vm.avatar]) || '@/static/img/avatar.jpg'"
                ></image>
                <text class="nickname">{{ obj[vm.nickname] }}</text>
              </view>
              <view class="reply_right" v-if="userInfo.user_id === o[vm.user_id] && $check_action('/comment/list', 'del')">
                <view class="del" @click="delete_comment(obj)"> 删除</view>
                <text
                  class="tn-icon-more-vertical right-options"
                  @tap="showOptionsSub(obj)"
                  v-if="self"
                ></text>
              </view>
            </view>
            <rich-text class="content" :nodes="$setRichTextImage(obj[vm.content])"></rich-text>

            <text class="reply_time">{{ $toTime(obj[vm.create_time], 'yyyy-MM-dd hh:mm:ss') }}</text>
          </view>
        </view>
      </view>
      <!-- /回复内容 -->
      <view class="line"></view>
    </view>
  </view>
  <view class="list_comment empty" v-else>还没有评论哦</view>
</template>

<script scoped>
export default {
  props: {
    list: {
      type: Array,
      default: function () {
        return [];
      },
    },
    obj: {
      type: Object,
      default: function () {
        return {};
      },
    },
    vm: {
      type: Object,
      default: function () {
        return {
          avatar: 'avatar',
          nickname: 'nickname',
          comment_id: 'comment_id',
          create_time: 'create_time',
          content: 'content',
		      user_id: 'user_id',
        };
      },
    },
    self: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      active_index: -1,
      reply_comment: '',
    };
  },
  methods: {
    goto_edit(o) {
      var vm = this.vm;
      return (
        '/pagesB/comment/edit?source_table=' +
        o.source_table +
        '&source_field=' +
        o.source_field +
        '&source_id=' +
        o.source_id +
        '&reply_to_id=' +
        o[vm.comment_id] +
        '&nickname=' +
        o[vm.nickname]
      );
    },
    async delete_comment(o) {
      let del_list = [o];
	  let del_num = 1;
      if (o.reply_to_id == 0) {
        const query = {
          page: 1,
          size: 9999,
          reply_to_id: o.comment_id,
        };
        // 查询是否有回复
        let res = await this.$get('~/api/comment/get_list', query);
        if (res.result.list.length > 0) {
		  del_num += res.result.list.length;
          del_list.push(...res.result.list);
        }
      }

      uni.showModal({
        title: '删除',
        content: '是否要删除评论?',
        success: async (res) => {
          if (res.confirm) {
            let flag = true;
            for (var i = 0; i < del_list.length; i++) {
              let res = await this.$get('~/api/comment/del', {
                comment_id: del_list[i].comment_id,
              });
              if (res.error) {
                flag = false;
                this.$toast('删除失败' + o.error.message, 'danger');
                break;
              }
            }
            if (flag) {
              this.$toast('删除成功', 'success');
			  // 更新模块评论数
			  let source_table = del_list[0].source_table
			  let source_field = del_list[0].source_field
			  let source_id = del_list[0].source_id
			  if (del_list[0].source_table != 'article' && del_list[0].source_table !=
			  	'forum' && del_list[0].source_table != 'goods') {
			  	this.$get("~/api" + source_table + "/get_obj", {
			  		[source_field]: source_id
			  	}, (json) => {
			  		if(json.result && json.result.obj){
			  			let comment_len = json.result.obj.comment_len - del_num
			  			this.$post('~/api/' + source_table + '/set?' + source_field + '=' + source_id,
			  			    {comment_len},(res) => {
			  			      if (res.result) {
			  			        console.log('添加评论数状态：', res.result);
			  			      } else if (res.error) {
			  			        console.error(res.error);
			  			      }
			  			    }
			  			);
			  		}
			  		console.log(json)
			  	});
			  }
              this.$emit('refresh');
            }
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        },
      });
    },
    showOptions(o) {
      let itemList = [];
      o.sticky == 1 ? itemList.push('取消置顶') : itemList.push('置顶');
      itemList.push('屏蔽');
      uni.showActionSheet({
        itemList,
        success: (res) => {
          if (res.tapIndex == 0) {
            this.$post(
              '~/api/comment/set?comment_id=' + o.comment_id,
              {
                sticky: o.sticky == 1 ? 0 : 1,
              },
              (res) => {
                if (res.result) {
                  this.$toast('操作成功', 'success');
                  this.$emit('refresh');
                }
              }
            );
          } else if (res.tapIndex == 1) {
            this.hideComment(o);
          }
        },
      });
    },
    showOptionsSub(o) {
      let itemList = ['屏蔽'];
      uni.showActionSheet({
        itemList,
        success: (res) => {
          if (res.tapIndex == 0) {
            this.hideComment(o);
          }
        },
      });
    },
    hideComment(o) {
      uni.showModal({
        title: '提示',
        content: '确定要屏蔽这条评论吗？',
        confirmText: '确定',
        cancelText: '取消',
        success: (res) => {
          if (res.confirm) {
            this.$post(
              '~/api/comment/set?comment_id=' + o.comment_id,
              {
                hidden: 1,
              },
              (res) => {
                if (res.result) {
                  this.$toast('操作成功', 'success');
                  this.$emit('refresh');
                }
              }
            );
          } else if (res.cancel) {
          }
        }
      });
    },
  },
  computed: {
    sortList() {
      return this.list
        .filter((item) => item.hidden !== 1)
        .sort((a, b) => {
          if (a.sticky === 1 && b.sticky !== 1) return -1;
          if (a.sticky !== 1 && b.sticky === 1) return 1;
          return 0;
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.nickname {
  display: flex;
  flex-direction: row;
  align-items: center;
  .nickname-sticky {
    width: 55rpx;
    height: 35rpx;
    font-size: 16rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1rpx solid #0079fe;
    color: #0079fe;
    margin-left: 10rpx;
  }
}
.right {
  display: flex;
  flex-direction: row;
}
.list_reply {
  justify-content: space-between;
  .reply_right {
    display: flex;
    flex-direction: row !important;
  }
}
.right-options {
  margin-left: 15rpx;
}
</style>
