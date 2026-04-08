<template>
  <view id="home" class="page_home" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
	<view class="header_bg"></view>
    <tn-nav-bar fixed :isBack="false">
      <view class="nav-wrapper">
        <view class="nav-user" @click="$navTo('/pagesA/user/info')">
          <image :src="$fullImgUrl(userInfo.avatar) || '/static/img/default.png'"></image>
        </view>
        <view class="tabs">
          <view :class="['tab-item', tabIndex === 0 ? 'active' : '']" @click="onClickTab(0)">
            <view class="name">推荐</view>
            <view class="active-flag" v-if="tabIndex === 0"> </view>
          </view>
                                                                </view>
      </view>
    </tn-nav-bar>

    <view class="tab-pane" v-show="tabIndex === 0">
      <view class="search-wrapper" @click.stop="$navTo('/pages/search/index')">
        <Search disabled placeholder="搜索内容" @cancel="cancel" />
      </view>

      <view class="scroll-x">
        <view class="left">
          <scroll-view class="scroll-view" :style="[!isExpand ? expandStyle : '']" scroll-x="true">
		    <view class="item">
		      推荐
		    </view>
            <view
              class="item"
              @click="clickMore(item.path)"
              v-for="(item, index) in list_menu"
              :key="index"
			   v-if="$check_action(item.path,'get') || item.path == '/chat/index'"
            >
              {{ item.mod_name }}
            </view>
          </scroll-view>
        </view>
        <view class="right">
          <view class="iconfont icon-liebiao" @click="isExpand = !isExpand"></view
        ></view>
      </view>

      <!-- 轮播图模块(开始) -->
      <view class="swiper">
        <Slide :list="list_slide" :show_title="true" />
      </view>
      <!-- 轮播图模块(结束) -->

      <!-- 菜单模块(开始) -->
      <view class="menu" v-if="list_menu.length > 0">
        <list_menu :list="list_menu"></list_menu>
      </view>
      <!-- 菜单模块(结束) -->
      <!-- 汽车资讯模块(开始) -->
      <Card
        v-if="$check_action('/article/list', 'get')"
        title="汽车资讯"
        url="/pages/article/index" 
        :list="list_article"
        class="article_list"
      >
        <view class="scroll-category">
          <scroll-view class="scroll-view" scroll-x="true">
            <view
              :class="['category-item', activeArticle == item.value ? 'active' : '']"
              @click="onClickCategory(item.value)"
              v-for="(item, index) in types_article"
              :key="index"
            >
              {{ item.value }}</view
            >
          </scroll-view>
        </view>
        <list_article :list="list_article"></list_article>
      </Card>
      <view class="split-line"></view>
      <!-- 汽车资讯模块(结束) -->
      <!-- 商品模块(开始) -->
      <Card
        v-if="$check_action('/goods/list', 'get')"
        class="goods_list"
        title=" 网上商城 "
        url="/pagesA/goods/list"
        :list="list_goods"
      >
        <view class="scroll-category">
          <scroll-view class="scroll-view" scroll-x="true">
            <view
              :class="['category-item', activeGoods == item.value ? 'active' : '']"
              @click="onClickGoods(item.value)"
              v-for="(item, index) in types_goods"
              :key="index"
            >
              {{ item.value }}</view
            >
          </scroll-view>
        </view>

        <list_goods :list="list_goods" :activeGoods="activeGoods"></list_goods>
      </Card>
      <view class="split-line"></view>
      <!-- 商品模块(结束) -->
	  <view class="home_recommend">
                                                        	  </view>
      <Card
        v-if="$check_action('/notice/list', 'get')"
        class="notice_list list_diy"
        title="通知公告"
        url="/pages/notice/list"
        :list="list_notice"
      >
        <view class="gg-list-wrap">
          <view
            class="gg-list-item"
            v-for="(o, i) in list_notice"
            :key="i"
            @click="$navTo('/pages/notice/details?notice_id=' + o['notice_id'])"
          >
            <view class="content">
              <text class="num">{{ i + 1 }}.</text>
              <text class="title">{{ o.title }}</text>
            </view>
			<view class="time"> {{ $toTime(o.create_time, "yyyy-MM-dd hh:mm:ss") }}</view>
			<rich-text class="desc" :nodes="$setRichTextImage(o.content)"></rich-text>
          </view>
        </view>
      </Card>

      <view class="split-line"></view>
      <!-- 推荐通知公告模块(结束) -->

      <!-- 版权模块(开始) -->
      <view class="copyright">
        <text>@版权归属 XX 所有</text>
      </view>
      <!-- 版权模块(结束) -->
    </view>

                                                        

    <image v-if="emergencyUser.includes(userInfo.user_group)" src="/static/emergency.svg" class="emergency_icon" @tap="openEmergencyModal" />
    <!-- 紧急联系弹窗 -->
    <uni-popup ref="emergencyPopup" type="center" :mask-click="false">
      <view class="emergency-card">
        <view class="emergency-title">紧急求助</view>
        <view class="form-row">
          <text class="label">求助用户</text>
          <input class="input" v-model="emergencyForm.user" disabled />
        </view>
        <view class="form-row">
          <text class="label">求助时间</text>
          <input class="input" v-model="emergencyForm.time" disabled />
        </view>
        <view class="form-row">
          <text class="label">紧急联系人</text>
          <input class="input" v-model="emergencyForm.contact" placeholder="请输入联系人姓名" />
        </view>
        <view class="form-row">
          <text class="label">紧急联系电话</text>
          <input class="input" v-model="emergencyForm.phone" placeholder="请输入联系电话" />
        </view>
        <view class="form-row">
          <text class="label">当前位置</text>
          <view class="location-row" style="width: calc(100% - 98px);">
            <input class="input" style="flex: 1;" v-model="emergencyForm.location" placeholder="当前位置" />
            <button class="me-btn btn-locate" type="primary" size="mini" @click="locate">定位</button>
          </view>
        </view>
        <view class="form-row">
          <text class="label">求助内容</text>
          <textarea class="textarea" style="width: calc(100% - 98px);" v-model="emergencyForm.content" placeholder="请描述您的情况"></textarea>
        </view>
        <view class="btn-row">
          <button class="me-btn" type="primary" @click="submitEmergency">求助</button>
          <button class="me-btn plain" @click="closeEmergencyModal">取消</button>
        </view>
      </view>
    </uni-popup>


    <tn-tabbar
      :value="tabbarIndex"
      :list="tabbarList"
      @change="switchTabbar"
      :animation="true"
      :safeAreaInsetBottom="true"
    ></tn-tabbar>
    <view class="communicate_index" v-if="user.user_id">
      <text @click="openOnlineCommunication">✉</text>
    </view>
	  <view class="back_top" v-if="show_back_top" @click="scroll_top">
      <text>↑</text>
    </view>
  </view>
</template>

<script>
import Search from '@/components/businessCp/search.vue';
                                                        import Card from '@/components/common/card.vue';
import list_menu from '@/components/diy/list_menu.vue';
import list_goods from '@/components/diy/list_goods.vue';
import list_article from '@/components/diy/list_article.vue';
import Slide from '@/components/common/slide.vue';
import Notice from '@/components/common/notice.vue';

import mixin from '@/libs/mixins/page.js';
import tabbar from '@/libs/mixins/tabbar.js';
import * as HomeApi from '@/api/home';

export default {
  mixins: [mixin, tabbar],
  components: {
    Search,
    Card,
                                                            list_menu,
    list_goods,
    list_article,
    Slide,
    Notice,
  },
  data() {
    return {
      tabIndex: 0,
      isExpand: false,
      expandStyle: {
        whiteSpace: 'nowrap',
      },
      tabbarIndex: 0,
                                                              list_goods: [],
      goodsTypes: [],
      list_slide: [],
      list_article: [],
      list_menu: [],
      list_notice: [],
      activeGoods: '全部',
      types_goods: [],
      activeArticle: '全部',
      types_article: [],
      emergencyForm: {
        user: '',
        time: '',
        contact: '',
        phone: '',
        location: '',
        content: ''
      },
      emergencyUser: [
        "管理员"
                          ,"车主用户"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ],
      emergencyUserTable: [
                                                                                                              {table: 'owner_user', field: 'contact_number' },
                                                                                                                                      {table: 'business_user', field: 'mobile_phone_number' },
                                                                                                                                                                {table: 'financial_user', field: 'contact_number' },
                                                                                                                                                                {table: 'manager_user', field: 'contact_number' },
                                                                                                                                      {table: 'sales_manager', field: 'contact_number' },
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ],
    };
  },
  created() {
  },
  methods: {
    openEmergencyModal() {
      if (!this.token) {
        return uni.showToast({ title: '请先进行登录', icon: 'none' });
      }
      const now = new Date();
      this.emergencyForm.user = `${this.userInfo.username}-${this.userInfo.nickname}`;
      this.emergencyForm.time = this.formatDate(now);
      this.emergencyForm.contact = this.userInfo.emergency_name;
      this.emergencyForm.phone = this.userInfo.emergency_mobile;
      this.emergencyForm.location = '';
      this.emergencyForm.content = '';
      this.$refs.emergencyPopup.open();
    },
    // 关闭弹窗
    closeEmergencyModal() {
      this.$refs.emergencyPopup.close();
    },
    // 一键定位
    async locate() {
      try {
        const res = await uni.chooseLocation();
        const addr = res.filter(Boolean);
        this.emergencyForm.location = addr[0].address;
      } catch (e) {
        try {
          const geo = await uni.getLocation({ type: 'wgs84' });
          this.emergencyForm.location = `${geo.latitude},${geo.longitude}`;
        } catch (err) {
          uni.showToast({ title: '定位失败，请稍后重试', icon: 'none' });
        }
      }
    },
    // 提交求助
    async submitEmergency() {
      if (!this.emergencyForm.contact) {
        return uni.showToast({ title: '请填写紧急联系人', icon: 'none' });
      }
      if (!this.emergencyForm.phone) {
        return uni.showToast({ title: '请填写联系人电话', icon: 'none' });
      }
      if (!this.emergencyForm.content) {
        return uni.showToast({ title: '请填写求助内容', icon: 'none' });
      }
      try {
        const url = '~/api/message_inform/add';
        let targetUserId = '9999';
        let targetType = '消息';

        const tables = Array.isArray(this.emergencyUserTable) && this.emergencyUserTable.length ? this.emergencyUserTable : [];
        for (const table of tables) {
          let params = {};
          params[table.field] = this.emergencyForm.phone;
          try {
            const res = await this.$get('~/api/' + table.table + '/get_obj', params);
            if (res && res.result && res.result.obj && res.result.obj.user_id) {
              targetUserId = res.result.obj.user_id;
              targetType = '通知';
              break;
            }
          } catch (err) {
            // 某张表查询失败不影响继续遍历
          }
        }
        const message_inform_admin = {
          title: this.emergencyForm.user + '提交紧急求助',
          type: '消息',
          content: `用户：[${this.emergencyForm.user}]\n时间：[${this.emergencyForm.time}]\n位置：[${this.emergencyForm.location}]\n求助内容：[${this.emergencyForm.content}]\n请尽快处理！`,
          state: 1,
          user_id: '9999',
        };
        await this.$post(url, message_inform_admin);
        if(targetUserId != '9999'){
          const message_inform = {
            title: this.emergencyForm.user + '提交紧急求助',
            type: targetType,
            content: `用户：[${this.emergencyForm.user}]\n时间：[${this.emergencyForm.time}]\n位置：[${this.emergencyForm.location}]\n求助内容：[${this.emergencyForm.content}]\n请尽快处理！`,
            state: 1,
            user_id: targetUserId,
          };
          await this.$post(url, message_inform);
        }
        uni.showToast({ title: '已发送求助', icon: 'success' });
        this.closeEmergencyModal();
      } catch (e) {
        uni.showToast({ title: '发送失败，请稍后重试', icon: 'none' });
      }
    },
    // 时间格式化
    formatDate(d) {
      const pad = (n) => (n < 10 ? '0' + n : '' + n);
      return (
        d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) +
        ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
      );
    },
    onClickGoods(val) {
      this.activeGoods = val;
      this.get_goods();
    },
    /**
     *  获取商品
     */
    get_goods() {
      let params = {
        page: 1,
        size: 100,
        list_status: 1,
      };
                                                                                                                                                                        if (this.userInfo.user_group != '管理员' && this.activeGoods == '全部') {
        params.user_id = this.userInfo.user_id;
      }
                  HomeApi.getHomeGoodsListApi(params).then((res) => {
        if (res.result && res.result.list) {
          let goodsList = res.result.list.filter((item) => { return item.list_status === 1; });
          if (this.activeGoods && this.activeGoods != '全部') {
            // 获取当前分类及其所有子分类的名称列表
            const categoryNames = this.getCategoryAndChildrenNames(this.activeGoods);
            goodsList = goodsList.filter((item) => categoryNames.includes(item.type));
          }
          this.list_goods = goodsList.slice(0, 12);
        }
      });
    },
    /**
     * 获取分类及其所有子分类的名称
     */
    getCategoryAndChildrenNames(categoryName) {
      const allCategories = this.goodsTypes;
      // 找到当前分类
      const currentCategory = allCategories.find(cat => cat.name === categoryName);
      if (!currentCategory) return [categoryName];

      // 递归获取所有子分类
      const getAllChildren = (fatherId) => {
        const children = allCategories.filter(cat => cat.father_id === fatherId);
        let result = [];
        children.forEach(child => {
          result.push(child.name);
          result = result.concat(getAllChildren(child.type_id));
        });
        return result;
      };

      // 返回当前分类及其所有子分类的名称
      return [currentCategory.name].concat(getAllChildren(currentCategory.type_id));
    },
    /**
     * 添加商品过滤
     */
    get_goods_type() {
      this.$get('/goods_type/get_list', { }, (res) => {
        if (res.result && res.result.list) {
          this.goodsTypes = res.result.list;
          let list = res.result.list.filter(obj => obj.father_id === 0).map((obj) => {
            return { value: obj.name, text: obj.name };
          });
          let arr = [
            {
              value: '全部',
              text: '全部',
            },
          ];
          this.types_goods = arr.concat(list);
        }
      });
    },
    onClickCategory(val) {
      this.activeArticle = val;
      this.get_article();
    },
    /**
     *  获取文章
     */
    get_article() {
      let params = {
        page: 1,
        size: 6,
      };
      if (this.activeArticle && this.activeArticle != '全部') {
        params['type'] = this.activeArticle;
      }
      HomeApi.getHomeArticleListApi(params).then((res) => {
        if (res.result && res.result.list) {
          this.list_article = res.result.list;
        }
      });
    },
    /**
     * 添加文章过滤
     */
    get_article_type() {
      this.$get(
        '~/api/article_type/get_list',
        {
          page: 1,
        },
        (res) => {
          if (res.result && res.result.list) {
            let list = res.result.list.map((obj) => {
              return { value: obj.name, text: obj.name };
            });
            let arr = [
              {
                value: '全部',
                text: '全部',
              },
            ];
            this.types_article = arr.concat(list);
          }
        }
      );
    },
    getFullPath(path, route=null) {
      if (path) {
        const fullPath = this.pathList[path];
        console.log('fullPath', fullPath)
        if (fullPath) {
          return fullPath + path;
        }else if (path.indexOf("/webview/urlview") !== -1) {
          return '/pages' + path;
        } else if(route) {
          return route + path;
        } else {
          return '/pagesC' + path;
        }
      }
      return '';
    },
    onClickTab(val) {
      this.tabIndex = val;
    },
                                                                                                                
    /**
     *  获取轮播图
     */
    get_slides() {
      HomeApi.gethomeSlidesListApi().then((res) => {
        if (res.result && res.result.list) {
          this.list_slide = res.result.list;
        }
      });
    },

    /**
     *  获取导航栏
     */
    get_menu() {
      const user_group = this.userGroup;
      HomeApi.getMenuListApi({
        size: '0',
        get: 1,
        user_group,
        position: 'top',
      }).then((res) => {
        if (res.result && res.result.list) {
          this.list_menu = res.result.list;
        }
                      this.list_menu.push({mod_name:"网上商城",path:"/goods/list"})
                                      if(this.userInfo.user_group!="游客") {
          this.list_menu.unshift({mod_name: "在线聊天", path: "/chat/index"});
        }
        		this.list_menu.push({mod_name:"汽车资讯",path:"/article/list"})
        		this.list_menu.push({mod_name:"车友社群",path:"/forum/list"})
                  });
    },

    /**
     *  获取公告列表
     */
    get_notice() {
      HomeApi.getHomeNoticeListApi({ page: 1, size: 3 }).then((res) => {
        if (res.result && res.result.list) {
          // console.log('公告', res.result.list);
          this.list_notice = res.result.list;
          this.list_notice.map((o) => {
            o['praise_len'];
          });
          // this.get_praise(this.list_notice, 'notice', 'notice_id');
        }
      });
    },
    /**
     *  获取点赞数
     *  @param {Object} list
     */
    get_praise(list, table, idName) {
      if (list) {
        for (let i = 0; i < list.length; i++) {
          list[i].praise_len = 0;
        }

        HomeApi.getPraiseListApi({
          source_table: table,
          groupby: 'source_id',
        }).then((res) => {
          if (res.result && res.result.list) {
            res.result.list.map((o) => {
              for (let i = 0; i < list.length; i++) {
                let oj = list[i];
                if (oj[idName] === o['source_id']) {
                  oj['praise_len'] = o['count'];
                  break;
                }
              }
            });
          } else if (res.error) {
            // console.error(res.error);
          }
        });
      }
    },
	clickMore(url){
		if(url == "/forum/list"){
      url = "/forum/index"
    }
    if(url == "/article/list"){
      url = "/article/index"
    }
    if(url == "/subject_exam/list"){
      url = "/exam_subject/list"
    }
    const isTabbarPage = this.tabbarList.some(item => item.pagePath.endsWith(url));
    if (isTabbarPage) {
      this.$navToTab(this.getFullPath(url,'/pages'))
    }else{
      this.$navTo(this.getFullPath(url))
    }
	},
  openOnlineCommunication() {
    uni.navigateTo({
      url: `/pagesA/support/index?viewType=list`,
    });
  },
  },
  onShow() {
    this.get_slides();
    this.get_menu();
    this.get_notice();
                                                            this.get_article_type();
    this.get_article();
    this.get_goods_type();
    this.get_goods();
  },
};
</script>
<style lang="scss" scoped>
.nav-wrapper {
  pointer-events: auto;
}
.back_top {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  font-size: 24px;
  font-weight: 700;
  position: fixed;
  bottom: 100px;
  right: 10px;
  border-radius: 30px;
  background-color: #fff;
  cursor: pointer;
  z-index: 1000;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.communicate_index{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  font-size: 24px;
  font-weight: normal;
  position: fixed;
  bottom: 150px;
  right: 10px;
  border-radius: 30px;
  background-color: #fff;
  cursor: pointer;
  z-index: 1000;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.emergency_icon {
  width: 41px;
  height: 41px;
  position: fixed;
  right: 0%;
  bottom: 190px;
  z-index: 9999;
}
.emergency-card {
  width: 600rpx;
  max-width: 90vw;
  background: #fff;
  border-radius: 12rpx;
  padding: 24rpx;
}
.emergency-title {
  font-size: 32rpx;
  font-weight: 600;
  margin-bottom: 20rpx;
}
.form-row {
  margin-bottom: 16rpx;
  display: flex;
  align-items: center;
}
.label {
  display: inline-block;
  width: 180rpx;
  color: #333;
  font-size: 28rpx;
}
.input {
  display: inline-block;
  width: calc(100% - 190rpx);
  height: 70rpx;
  line-height: 70rpx;
  border: 1px solid #dcdcdc;
  border-radius: 8rpx;
  padding: 0 16rpx;
  box-sizing: border-box;
}
.location-row {
  display: flex;
  align-items: center;
}
.btn-locate {
  margin-left: 12rpx;
}
.textarea {
  width: calc(100% - 0rpx);
  min-height: 160rpx;
  border: 1px solid #dcdcdc;
  border-radius: 8rpx;
  padding: 10rpx 16rpx;
  box-sizing: border-box;
}
.btn-row {
  display: flex;
  justify-content: center;
  margin-top: 20rpx;
  gap: 10px;
}
</style>
