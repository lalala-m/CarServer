<template>
  <view id="account_login" class="user_account account_login">
    <view class="container">
      <view class="custom-1"></view>
      <view class="custom-2"></view>
      <view class="custom-3"></view>
      <view class="custom-4"><text>e行家车友会服务系统</text></view>
      <view class="custom-5"></view>

      <view class="login-wrapper">
        <view class="input-content">
          <view class="input-item">
            <view class="input-item-icon">
              <view class="tn-icon-my-lack"></view>
            </view>
            <input
                type="text"
                v-model="form.username"
                placeholder="请输入用户名"
                maxlength="16"
                data-key="username"
            />
          </view>
          <view class="input-item">
            <view class="input-item-icon">
              <view class="tn-icon-lock"></view>
            </view>
            <input
                v-model="form.password"
                placeholder="请输入密码"
                placeholder-class="input-empty"
                maxlength="20"
                :password="!showPassword"
                data-key="password"
                @confirm="login"
            />
            <view class="input-item-icon">
              <view
                  @click="showPassword = !showPassword"
                  :class="[showPassword ? 'tn-icon-eye' : 'tn-icon-eye-hide']"
              >
              </view>
            </view>
          </view>
        </view>
        <button class="confirm-btn" @click="login" :disabled="logining">登录</button>
        <view class="forget-section">
          <view @click="$navTo('/pagesB/account/forgot')">忘记密码?</view>
        </view>
                                                                <view class="author-wrap" style="width: 100%; display: flex; justify-content: center">
          <!-- #ifdef MP-WEIXIN -->
          <view class="author">
            <button class="author-weixin" @click="getUserInfo"></button>
          </view>
          <!-- #endif -->
          <!-- #ifdef APP-VUE || H5 -->
          <view class="author">
            <button class="author-weixin" @click="weixin"></button>
          </view>
          <!-- #endif -->
        </view>
      </view>
    </view>

  </view>
</template>

<script>
  import mixin from '@/libs/mixins/page.js';
  import {loginApi} from '@/api/login.js';
  import wx_open from "@/libs/wx_open.json";
  import jsencrypt from '@/components/jsencrypt/jsencrypt.vue';

  export default {
    mixins: [mixin],
    data() {
      return {
        logining: false,
		allow_user: [
			'管理员'
				,"车主用户"
					,"商家用户"
																													],
        form: {
          username: '',
          password: '',
        },
        rules: {
          username: {
            rules: [
              {
                required: true,
                errorMessage: '请输入用户名',
              },
              {
                minLength: 5,
                maxLength: 16,
                errorMessage: '用户名长度在 {minLength} 到 {maxLength} 个字符',
              },
            ],
          },
          password: {
            rules: [
              {
                required: true,
                errorMessage: '请输入密码',
              },
              {
                minLength: 6,
                maxLength: 16,
                errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
              },
            ],
          },
        },
        showPassword: false,
      };
    },
    onLoad() {
      console.log(this.$u.route);
    },
    methods: {
      closePopup() {
        this.logining = false;
      },
      login() {
          this.logining = true;
        let form = Object.assign({}, this.form);
          const publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+mEDzYLbKNB9rbOuvGgwdBUpPaHryRGarxBQppkOzlj+ouep8MMq1Xg7NBkjLOV2vnn4E5AVvX0XVOmBg8W5eNQ1uS1HCG2fie8BpXGgl1pWj/HYIrA2d/U7xxvMO8UMhAGfMdaGrPrGdZTr95pzL/q+VJZOcqSAgux/YEdu11wIDAQAB";
        form.password = jsencrypt.setEncrypt(publicKey,form.password);
          loginApi(form)
          .then((res) => {
            if (res.result && res.result.obj) {
              let user = res.result.obj;
              const login_time = new Date().toLocaleString('zh-CN').replace(/\//g, '-').replace(/\s/, ' ').split('.')[0]
              this.$post("~/api/user/set?user_id=" + user.user_id, {
                login_time: login_time,
              });
              user.login_time = login_time;
			  if(this.allow_user.includes(user.user_group)){
          				  // 缓存token
				  this.$u.vuex('token', user.token);
				  // 存储用户信息
				  this.$u.vuex('userInfo', user);
				  // 设置权限集
				  this.$u.vuex('userGroup', user.user_group);
  				  // 前往首页
				  uni.switchTab({
				    url: '/pages/index/index',
				  });
				  console.log('---登录成功---');
			  }else{
				  this.$toast("您的账号不支持在用户端登录！", 'error');
			  }
              
            } else if (res.error) {
                            this.$toast(res.error.message, 'error');
            }
          })
          .finally(() => {
            this.logining = false;
          });
      },
      /**
       * 手动重置表单
       */
      resetForm() {
        this.$refs.form.resetFields();
      },
      // #ifdef APP-VUE || H5
      // 三个授权组件地址
      // Tencent(){
      // 	uni.navigateTo({
      // 		url:'../authorization/authorization?id=1'
      // 	})
      // },
      weixin() {
            this.$nav('/pagesB/authorization/authorization-weixin?id=2');
          },
      // weibo(){
      // 	uni.navigateTo({
      // 		url:'../authorization/authorization-weibo?id=3'
      // 	})
      // }
      // #endif
      // #ifdef MP-WEIXIN
      getUserInfo() {
            uni.showLoading({
          // 展示加载框
          title: '加载中',
        });
        uni.getUserProfile({
          //获取到用户信息
          desc: '登录后可同步数据',
          success: async (obj) => {
            this.nickName = obj.userInfo.nickName; //用户名
            this.avatarUrl = obj.userInfo.avatarUrl; //用户头像
            // 调用 action ，请求登录接口
            uni.login({
              provider: 'weixin',
              success: (res) => {
                console.log('res-login', res); //获取到code
                this.code = res.code;
                //请求登录接口
                if (res.errMsg == 'login:ok') {
                  let params = {
                    code: this.code,
                    nickname: this.nickName,
                    avatar: this.avatarUrl,
                  };
                  const ps = {
                    appid: wx_open.appid,
                    secret: wx_open.secret,
                    js_code: params.code,
                    grant_type: 'authorization_code',
                  };
                  this.$get('https://api.weixin.qq.com/sns/jscode2session',ps,(res) =>{
					console.log(res);
					if (res.openid) {
					  //登录
					  this.$post(
					      '~/api/user/open_id_login?',
					      {
					        open_id: res.openid,
					        username: this.form.username,
					        password: this.form.password,
					      },
					      (res) => {
					        if (res.result && res.result.obj) {
							  let user = res.result.obj;
							  if(this.allow_user.includes(user.user_group)){
							  								  	// 缓存token
							  	this.$u.vuex('token', user.token);
							  	// 存储用户信息
							  	this.$u.vuex('userInfo', user);
							  	// 设置权限集
							  	this.$u.vuex('userGroup', user.user_group);
							  	// 前往首页
							  	uni.switchTab({
							  		url: '/pages/index/index',
							  	});
							  	console.log('---登录成功---');
							  }else{
							  	this.$toast("您的账号不支持在用户端登录！", 'error');
							  }
					        } else if (res.error) {
					          this.$toast(res.error.message, 'error');
					        }
					      }
					  );
					} else {
					  uni.showToast({
					    icon: 'none',
					    title: '获取用户信息失败！',
					  });
					}
				  })
                }
              },
            });
          },
          fail: () => {
            uni.showToast({
              title: '授权已取消',
              icon: 'error',
              mask: true,
            });
          },
          complete: () => {
            // 隐藏loading
            uni.hideLoading();
          },
        });
            },
      // #endif
    },
    onBackPress() {
      var bl = false;
      var user_id = this.user.user_id;
      if (user_id == null || user_id < 1) {
        // this.$nav('/pages/index/index');
        bl = true;
      }
      return false;
    },
  };
</script>

<style lang="scss" scoped>
  @import 'styles/pagesB/index.scss';
/* 记住密码样式 */
.remember {
  margin-top: 15px;
  display: flex;
  align-items: center;
  
  label {
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #666;
    
    checkbox {
      margin-right: 8px;
    }
  }
}

/* 协议样式 */
.agree {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #666;
  
  checkbox {
    margin-right: 8px;
  }
  
  .protocol {
    color: #409EFF;
    text-decoration: underline;
  }
}

/* 协议弹窗样式 */
.agreement-content {
  width: 300px;
  background: white;
  border-radius: 10px;
  padding: 20px;
  
  .agreement-text {
    height: 200px;
    border: 1px solid #eee;
    padding: 10px;
    margin-bottom: 15px;
    font-size: 14px;
    line-height: 1.5;
  }
  
  .confirm-btn {
    width: 100%;
    height: 40px;
    background: #409EFF;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    
    &:disabled {
      background: #ccc;
      color: #999;
    }
  }
}
</style>
