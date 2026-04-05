<template>
  <view class="page_user_password" id="page_user_password" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>修改密码</tn-nav-bar>
    <view class="user-password-list">
      <tn-form
        :model="form"
        ref="formRef"
        :errorType="errorType"
        :labelPosition="labelPosition"
        :labelAlign="labelAlign"
      >
        <tn-form-item
          label="原密码"
          prop="o_password"
          :required="true"
          :labelPosition="labelPosition"
          :labelAlign="labelAlign"
          :labelWidth="200"
        >
          <tn-input v-model="form.o_password" placeholder="请输入密码" :border="border"></tn-input>
        </tn-form-item>
        <tn-form-item
          label="密码"
          prop="password"
          :required="true"
          :labelPosition="labelPosition"
          :labelAlign="labelAlign"
          :labelWidth="200"
        >
          <tn-input v-model="form.password" placeholder="请输入密码" type="password" :border="border"></tn-input>
        </tn-form-item>
                <tn-form-item
          label="确认密码"
          prop="confirm_password"
          :required="true"
          :labelPosition="labelPosition"
          :labelAlign="labelAlign"
          :labelWidth="200"
        >
          <tn-input
            v-model="form.confirm_password"
            placeholder="请再次输入密码"
            :border="border"
            type="password"
          ></tn-input>
        </tn-form-item>
      </tn-form>
    </view>
    <view class="user-password-footer">
      <view class="me-btn btn-save" size="lg" @click="changePassword">保存</view>
    </view>
  </view>
</template>

<script>
import mixin from '@/libs/mixins/page.js';
import { changePasswordApi } from '@/api/user';
export default {
  mixins: [mixin],
  data() {
    return {
      loading: false,
      border: false,
      labelPosition: 'left',
      labelAlign: 'left',
      errorType: ['message', 'border-bottom', 'toast'],
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      form: {
        o_password: '',
        password: '',
        confirm_password: '',
      },
      rules: {
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
                    {
            min: 6,
            max: 16,
            message: '密码长度在6到16个字符',
            trigger: ['change', 'blur'],
          },
                  ],
        o_password: [
          {
            required: true,
            message: '请输入原密码',
            trigger: 'blur',
          },
          {
            min: 6,
            max: 16,
            message: '原密码长度在6到16个字符',
            trigger: ['change', 'blur'],
          },
        ],
        confirm_password: [
          {
            required: true,
            message: '请输入确认密码',
            trigger: 'blur',
          },
          {
            min: 6,
            max: 16,
            message: '确认密码长度在6到16个字符',
            trigger: ['change', 'blur'],
          },
        ],
      },
    };
  },
  computed: {
      },
  onReady() {
    this.$refs.formRef.setRules(this.rules);
  },
  methods: {
    changePassword() {
      if (this.loading) return;
      if (this.form.password !== this.form.confirm_password) {
        this.$toast('密码不相同', 'error');
        return;
      }
      if (!this.form.o_password || !this.form.password || !this.form.confirm_password){
          this.$toast("密码不能为空", "error");
          return;
      }
            this.loading = true;
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const data = { ...this.form };
          delete data.confirm_password;
          changePasswordApi(data)
            .then((res) => {
              if (res.result) {
                this.$toast('修改成功');
                setTimeout(() => {
                  // this.$nav('/pages/index/index');
                  this.$store.dispatch('auth/logOut').then((res) => {
                    this.$Router.replaceAll('/pagesB/account/login');
                  });
                }, 2000);
              } else if (res.error) {
                this.$toast(res.error.message, 'error');
              }
            })
            .finally(() => {
              this.loading = false;
            });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.password-strength-container {
  margin-top: 10px;
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 4px;
  font-size: 12px;
}

.strength-indicator {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.strength-label {
  color: #666;
  margin-right: 8px;
}

.strength-text {
  font-weight: bold;
  
  &.weak {
    color: #dc3545;
  }
  
  &.medium {
    color: #fd7e14;
  }
  
  &.strong {
    color: #28a745;
  }
}

.strength-bar {
  width: 100%;
  height: 6px;
  background-color: #e9ecef;
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 10px;
}

.strength-fill {
  height: 100%;
  transition: width 0.3s ease, background-color 0.3s ease;
  border-radius: 3px;
}

.password-requirements {
  margin-bottom: 8px;
}

.requirement {
  display: flex;
  align-items: center;
  margin-bottom: 4px;
  
  &.valid .requirement-icon {
    color: #28a745;
  }
  
  &:not(.valid) .requirement-icon {
    color: #dc3545;
  }
}

.requirement-icon {
  width: 16px;
  margin-right: 6px;
  font-weight: bold;
}

.requirement-text {
  color: #666;
  font-size: 11px;
}

.character-types {
  border-top: 1px solid #dee2e6;
  padding-top: 8px;
}

.types-hint {
  color: #999;
  font-size: 10px;
  line-height: 1.4;
}
</style>
