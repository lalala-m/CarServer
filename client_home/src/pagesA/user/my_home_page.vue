<template>
  <view class="page_user_home" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>用户主页</tn-nav-bar>
    <view class="my_home_page" id="my_home_page">
      <view class="list_wrapper">
        <view class="user-info">
          <image
            class="avatar"
            :src="
              userInfos.avatar
                ? $fullImgUrl(userInfos.avatar)
                : 'http://192.168.1.4:5000/upload/default_avatar.jpg'
            "
          ></image>
          <view class="user-statistics">
            <view class="statistics-item">
              <view class="sum">{{ myFollow.length }}</view>
              <view class="name">关注</view>
            </view>
            <view class="statistics-item">
              <view class="sum">{{ followSelf.length }}</view>
              <view class="name">粉丝</view>
            </view>
          </view>
        </view>
        <view class="user-btn">
          <view class="name">{{ userInfos.nickname }}</view>
          <view v-if="now_follower_id != userInfo.user_id" class="btn">
            <button class="me-btn item-btn" type="primary" @click="myAttention(userInfos)">
              {{ followState ? '已关注' : '+ 关注' }}
            </button>
                      <button class="me-btn item-btn plain" type="primary" @click="sendMessage(userInfo)">
              私信
            </button>
                    </view>
        </view>
        <view class="user-account">
          <view class="account">账户: {{ userInfos.username }}</view>
          <view class="account">邮箱: {{ userInfos.email }}</view>
        </view>
        <view class="user-content">
          <v-tabs v-model="current" :tabs="tabs" @change="changeTab" :z-index="9"></v-tabs>
          <view v-if="current == 0" class="tab-content">
            <template v-for="item in dynamicList">
              <view
                v-if="item.type === 'forum'"
                class="dynamic"
                @click="$navTo('/pagesA/forum/details?forum_id=' + item.forum_id)"
              >
                <view class="dynamic-title">{{ item.title }}</view>
                <!-- <view class="dynamic-bz" v-html="item.content"></view> -->
                <image v-if="item.img" class="dynamic-img" :src="$fullImgUrl(item.img)"></image>
                <view class="dynamic-btn">
                  <view class="dynamic-btn-box">
                    <button class="me-btn item-btn plain praise-no" type="primary"  @click.stop="add_praise(item)" v-if="!item.praised">
                      点赞
                    </button>
                    <button class="me-btn item-btn plain praise-yes" type="primary"  @click.stop="add_praise(item)" v-else>
                      已点赞
                    </button>
                    <button class="me-btn item-btn collect-no" type="primary" @click.stop="add_collect(item)" v-if="!item.collected">
                      收藏
                    </button>
                    <button class="me-btn item-btn collect-yes" type="primary" @click.stop="add_collect(item)" v-else>
                      已收藏
                    </button>
                  </view>
                  <view class="dynamic-time">{{ $toTime(item.create_time, 'yyyy-MM-dd hh:mm:ss') }}</view>
                </view>
                <view class="dynamic-statistics">
                  <view class="dynamic-statistics-item">点击数: {{ item.hits }}</view>
                  <view class="dynamic-statistics-item">点赞数: {{ item.praise_len }}</view>
                  <!-- <view class="dynamic-statistics-item">收藏数: 345</view> -->
                </view>
              </view>
																							            </template>
          </view>
          <view v-if="current == 1" class="tab-content">
            <view v-for="item in myFollow" :key="item.follow_id" class="attention">
              <view class="attention-user">
                <image
                  class="attention-img"
                  :src="
                    item.followed_avatar
                      ? $fullImgUrl(item.followed_avatar)
                      : 'http://192.168.1.4:5000/upload/default_avatar.jpg'
                  "
                ></image>
                <view class="attention-name">{{ item.followed_nickname }}</view>
              </view>
              <view class="attention-btn">
                <button
                  v-if="now_follower_id == userInfo.user_id"
                  class="me-btn item-btn-radius plain"
                  type="primary"
                  @click="cancelAttention(item)"
                >
                  已关注
                </button>
                <button
                  class="me-btn item-btn-radius plain"
                  type="primary"
                  @click="$navTo('/pagesA/user/my_home_page?follower_id=' + item.followed_id)"
                >
                  查看主页
                </button>
              </view>
            </view>
          </view>
          <view v-if="current == 2" class="tab-content">
            <view v-for="item in followSelf" :key="item.follow_id" class="attention">
              <view class="attention-user">
                <image
                  class="attention-img"
                  :src="
                    item.follower_avatar
                      ? $fullImgUrl(item.follower_avatar)
                      : 'http://192.168.1.4:5000/upload/default_avatar.jpg'
                  "
                ></image>
                <view class="attention-name">{{ item.follower_nickname }}</view>
              </view>
              <view class="attention-btn">
                <button
                  v-if="now_follower_id == userInfo.user_id"
                  class="me-btn item-btn-radius plain"
                  type="primary"
                  @click="attention(item)"
                >
                  {{ item.isFollow ? '互相关注' : '+ 关注' }}
                </button>
                <button
                  class="me-btn item-btn-radius plain"
                  type="primary"
                  @click="$navTo('/pagesA/user/my_home_page?follower_id=' + item.follower_id)"
                >
                  查看主页
                </button>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
    <!-- // 通知功能开始 -->
    <uni-popup ref="inputDialog" type="dialog">
      <uni-popup-dialog
        ref="inputClose"
        mode="input"
        title="发私信"
        :value="privateValue"
        placeholder="请输入内容"
        @confirm="dialogInputConfirm"
      ></uni-popup-dialog>
    </uni-popup>
    <!-- // 通知功能结束 -->
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
      tabs: ['动态', '关注', '粉丝'],
      current: 0,
      userInfos: {},
      dynamicList: [],
      followList: [],
      followSelf: [],
      myFollow: [],
      oneSelf: null,
      followState: false,
      now_follower_id: 0,
      query: {
        orderby: `create_time desc`,
      },
      // 通知功能开始
      privateValue: '',
      // 通知功能结束
    };
  },
  methods: {
      sendMessage() {
      this.$refs.inputDialog.open();
    },
    dialogInputConfirm(value) {
      let message_inform = {
        title: '你收到一条来自' + this.userInfo.nickname + '的私信',
        type: '私信',
        content: value,
        state: 1,
        user_id: this.now_follower_id,
      };
      this.$post('~/api/message_inform/add', message_inform);
      this.$toast('发送成功');
    },
      changeTab(index) {
      console.log('当前选中的项：' + index);
    },
    async getUserInfo(userId) {
      let res = await this.$get('~/api/user/get_obj', { user_id: userId });
      this.userInfos = res.result.obj;
      this.getFollowList();
    },
    async getFollowList() {
      let res = await this.$get('~/api/follow/get_list', this.query);
      this.followList = res.result.list;
      this.myFollow = res.result.list.filter((item) => item.follower_id == this.now_follower_id);
      this.oneSelf = res.result.list.filter(
        (item) =>
          item.follower_id == this.userInfo.user_id && item.followed_id == this.now_follower_id
      )[0];
      this.followSelf = res.result.list
        .filter((item) => item.followed_id == this.now_follower_id)
        .map((item) => {
          // 根据myFollow遍历是否互相关注
          let isFollow = this.myFollow.some(
            (item2) =>
              item2.follower_id === item.followed_id && item2.followed_id === item.follower_id
          );
          return {
            ...item,
            isFollow: isFollow,
          };
        });

      if (this.oneSelf) {
        this.followState = true;
      } else {
        this.followState = false;
      }
      console.log(this.myFollow, '我的关注');
      console.log(this.followSelf, '我的粉丝');
    },
    async getDynamicList(userId) {
      let params = Object.assign({}, this.query, {
        user_id: userId,
      });
      let forum = await this.$get('~/api/forum/get_list', params);
      let forumList = await Promise.all(forum.result.list.map(async (item) => {
        const new_forumItem = {
          ...item,
          type: 'forum',
        };
        await this.checkCollect(new_forumItem);
        await this.checkPraise(new_forumItem);
        return new_forumItem;
      }));
																							      

      this.dynamicList = [
        ...forumList,
																							      ];
    },
    async attention(obj) {
      let param = {
        follower_id: this.userInfo.user_id,
        follower_avatar: this.userInfo.avatar,
        follower_nickname: this.userInfo.nickname,
        followed_id: obj.follower_id || obj.user_id,
        followed_avatar: obj.follower_avatar || obj.avatar,
        followed_nickname: obj.follower_nickname || obj.nickname,
      };
      if (obj.isFollow || this.followState) {
        uni.showModal({
          title: '提示',
          content: '不再关注该用户？',
          success: async (res) => {
            if (res.confirm) {
              let oneself = this.myFollow.find(
                (item) =>
                  item.followed_id === obj.follower_id && item.follower_id === obj.followed_id
              );
              let res = await this.$get(`~/api/follow/del?follow_id=${oneself.follow_id}`);
              if (res.result) {
                this.getUserInfo(this.now_follower_id);
                this.getDynamicList(this.now_follower_id);
                this.$toast('取消关注');
              }
            } else if (res.cancel) {
              console.log('用户点击取消');
            }
          },
        });
      } else {
        let res = await this.$post('~/api/follow/add', param);
        if (res.result) {
          this.getUserInfo(this.now_follower_id);
          this.getDynamicList(this.now_follower_id);
          this.$toast('关注成功');
        }
      }
    },
    cancelAttention(obj) {
      uni.showModal({
        title: '提示',
        content: '不再关注该用户？',
        success: async (res) => {
          if (res.confirm) {
            let res = await this.$get(`~/api/follow/del?follow_id=${obj.follow_id}`);
            if (res.result) {
              this.getUserInfo(this.now_follower_id);
              this.getDynamicList(this.now_follower_id);
              this.$toast('取消关注');
            }
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        },
      });
    },
    async myAttention(obj) {
      let param = {
        follower_id: this.userInfo.user_id,
        follower_avatar: this.userInfo.avatar,
        follower_nickname: this.userInfo.nickname,
        followed_id: obj.follower_id || obj.user_id,
        followed_avatar: obj.follower_avatar || obj.avatar,
        followed_nickname: obj.follower_nickname || obj.nickname,
      };
      if (this.followState) {
        uni.showModal({
          title: '提示',
          content: '不再关注该用户？',
          success: async (res) => {
            if (res.confirm) {
              let res = await this.$get(`~/api/follow/del?follow_id=${this.oneSelf.follow_id}`);
              if (res.result) {
                this.getUserInfo(this.now_follower_id);
                this.getDynamicList(this.now_follower_id);
                this.$toast('取消关注');
              }
            } else if (res.cancel) {
              console.log('用户点击取消');
            }
          },
        });
      } else {
        let res = await this.$post('~/api/follow/add', param);
        if (res.result) {
          this.getUserInfo(this.now_follower_id);
          this.getDynamicList(this.now_follower_id);
          this.$toast('关注成功');
        }
      }
    },
    async add_praise(obj) {
      if (obj.type === 'forum') {
        let res = await this.$get('~/api/praise/count', {
          source_table: 'forum',
          source_field: 'forum_id',
          source_id: obj.forum_id,
          user_id: this.userInfo.user_id,
        });
        if (res.result || res.result === 0) {
          console.log('sadsfasf', res.result);
          let check_praised = res.result ? true : false;
          let param = {
            source_table: 'forum',
            source_field: 'forum_id',
            source_id: obj.forum_id,
            user_id: this.userInfo.user_id,
          };
          if (!check_praised) {
            let praiseRes = await this.$post('~/api/praise/add', param);
            if (praiseRes.result) {
              let praise_len = (obj.praise_len += 1);
              let forumRes = await this.$post('~/api/forum/set?forum_id=' + obj.forum_id, {
                praise_len,
              });
              if (forumRes.result) {
                console.log('添加点赞数状态：', forumRes.result);
              } else if (forumRes.error) {
                console.error(forumRes.error);
              }
              this.getDynamicList(this.now_follower_id);
              this.$toast('点赞成功');
            } else if (praiseRes.error) {
              this.$message.error(praiseRes.error.message);
              console.error(praiseRes.error);
            }
          } else {
            let res = await this.$get('~/api/praise/del', param);
            if (res.result) {
              if (obj.praise_len > 0) {
                obj.praise_len -= 1;
              } else {
                obj.praise_len = 0;
              }
              let praise_len = obj.praise_len;
              let forumRes = await this.$post('~/api/forum/set?forum_id=' + obj.forum_id, {
                praise_len,
              });
              if (forumRes.result) {
                console.log('取消点赞数状态：', forumRes.result);
              } else if (forumRes.error) {
                console.error(forumRes.error);
              }
              this.getDynamicList(this.now_follower_id);
              this.$toast('取消点赞');
            } else if (res.error) {
              this.$message.error(res.error.message);
              console.error(res.error);
            }
          }
        } else if (res.error) {
          this.$message.error(res.error.message);
          console.error(res.error);
        }
      }
																							    },
    async checkCollect(obj) {
      if (obj.type === 'forum') {
         let res = await this.$get('~/api/collect/count', {
              source_table: 'forum',
              source_field: 'forum_id',
              source_id: obj.forum_id,
              user_id: this.userInfo.user_id,
          });
          this.$set(obj, 'collected', res.result !== 0);
      }
																							    },
    async add_collect(obj) {
      if (obj.type === 'forum') {
        let res = await this.$get('~/api/collect/count', {
          source_table: 'forum',
          source_field: 'forum_id',
          source_id: obj.forum_id,
          user_id: this.userInfo.user_id,
        });
        let check_collected = res.result;
        let { title, img, forum_id } = obj;
        let body = {
          title,
          img,
          source_table: 'forum',
          source_field: 'forum_id',
          source_id: forum_id,
          user_id: this.userInfo.user_id,
        };
        if (!check_collected) {
          await this.$post('~/api/collect/add', body);
          this.$set(obj, 'collected', true);
          this.$toast('收藏成功');
        } else {
          await this.$get('~/api/collect/del', {
            user_id: this.userInfo.user_id,
            source_id: forum_id,
          });
          this.$set(obj, 'collected', false);
          this.$toast('取消收藏');
        }
      }
																							    },
    async checkPraise(obj) {
      if (obj.type === 'forum') {
         let res = await this.$get('~/api/praise/count', {
              source_table: 'forum',
              source_field: 'forum_id',
              source_id: obj.forum_id,
              user_id: this.userInfo.user_id,
          });
          this.$set(obj, 'praised', res.result !== 0);
      }
																							    },
  },
  onLoad(options) {
    if (options.follower_id) {
      this.now_follower_id = options.follower_id;
      this.getUserInfo(this.now_follower_id);
      this.getDynamicList(this.now_follower_id);
    }
  },
  mounted() {
    // if(this.$route.query.follower_id) {
    //   this.now_follower_id = this.$route.query.follower_id;
    //   this.getUserInfo(this.now_follower_id);
    //   this.getDynamicList(this.now_follower_id);
    // }
  },
};
</script>

<style lang="scss" scoped>
.list_wrapper {
  padding: 20px 16px;
  .user-info {
    display: flex;
    flex-direction: row;
    align-items: end;
    margin-bottom: 20px;
    padding: 0 11px;
    .avatar {
      width: 80px;
      height: 80px;
      border-radius: 4px;
    }
    .user-statistics {
      flex: 1;
      display: flex;
      flex-direction: row;
      justify-content: space-evenly;
      .statistics-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        .sum {
          font-size: 20px;
          font-weight: 600;
        }
      }
    }
  }
  .user-btn {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding: 0 11px;
    .name {
      font-size: 18px;
      font-weight: 600;
    }
  }
  .user-account {
    margin-bottom: 10px;
    padding: 0 11px;
    .account {
      font-size: 14px;
      color: #999;
      margin-bottom: 6px;
    }
  }
  .user-content {
    .tab-content {
      margin-top: 20px;
      padding: 0 11px;
      .dynamic {
        margin-bottom: 20px;
        .dynamic-title {
          font-size: 14px;
          font-weight: 600;
          margin-bottom: 10px;
        }
        .dynamic-bz {
          font-size: 14px;
          color: #999;
          margin-bottom: 10px;
        }
        .dynamic-img {
          width: 200px;
          height: 140px;
          border-radius: 4px;
        }
        .dynamic-btn {
          display: flex;
          flex-direction: row;
          align-items: center;
          justify-content: space-between;
          margin-top: 10px;
          margin-bottom: 10px;
          .dynamic-time {
            font-size: 14px;
            color: #999;
            margin-left: 10px;
          }
        }
        .dynamic-statistics {
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          .dynamic-statistics-item {
            font-size: 14px;
            color: #999;
          }
        }
      }
      .attention {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 20px;
        .attention-user {
          display: flex;
          flex-direction: row;
          align-items: center;
          margin-right: 10px;
          .attention-img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
          }
          .attention-name {
            font-size: 14px;
          }
        }
      }
    }
  }
  .item-btn {
    height: 26px;
    padding: 0 20px;
    border-radius: 4px;
    margin-right: 6px;
  }
  .item-btn-radius {
    height: 26px;
    padding: 0 10px;
    border-radius: 26px;
    margin-right: 6px;
  }
  .plain {
    color: #0079fe;
    background-color: transparent;
    border: 1px solid #0079fe;
  }
}
</style>
