import { mapState } from 'vuex';
export default {
  data() {
    return {
      // 底部tabbar菜单数据
      tabbarList: [
        {
          title: '首页',
          activeIcon: 'home',
          inactiveIcon: 'home',
          pagePath: '/pages/index/index',
        },
        {
          title: '购物车',
          activeIcon: 'basket',
          inactiveIcon: 'basket',
          pagePath: '/pages/cart/index',
        },
                                                                  {
          title: '车友社群',
          activeIcon: 'moments',
          inactiveIcon: 'moments',
          pagePath: '/pages/forum/index',
        },
        {
          title: '汽车资讯',
          activeIcon: 'level',
          inactiveIcon: 'level',
          pagePath: '/pages/article/index',
        },
        {
          title: '我的',
          activeIcon: 'my',
          inactiveIcon: 'my',
          pagePath: '/pages/user/index',
        },
      ],
	  // 显示返回顶部
      show_back_top: false,
    };
  },
  methods: {
	scroll_top() {
      uni.pageScrollTo({
        scrollTop: 0,
        duration: 300,
      });
    },
    // 切换导航
    switchTabbar(index) {
      const menu = this.tabbarList[index];
      if (menu.pagePath !== this.$Router.currentRoute.path) {
        this.$Router.pushTab(menu.pagePath);
      }
      setTimeout(() => {
        this._switchTabbarPage(index);
      }, 0);
    },

    // 导航页面滚动到底部
    tabbarPageScrollLower(e) {},

    // 切换导航页面
    _switchTabbarPage(index) {},
  },
  
  onPageScroll(e) {
    // 监听页面滚动事件
    if (e.scrollTop > 200) {
      this.show_back_top = true; // 显示按钮
    } else {
      this.show_back_top = false; // 隐藏按钮
    }
  },

  onLoad(options) {
    uni.hideTabBar();
  },

  onShow() {},

  onHide() {},
};
