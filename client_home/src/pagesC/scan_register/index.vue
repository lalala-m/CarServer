<template>
	<view class="container">
		<!-- 扫码成功，显示注册表单 -->
		<view v-if="step === 'form'" class="form-container">
			<view class="header">
				<image class="logo" src="/static/logo.png" mode="aspectFit"></image>
				<text class="title">扫码注册</text>
				<text class="subtitle">请填写您的注册信息</text>
			</view>
			
			<!-- 角色信息 -->
			<view class="role-info">
				<view class="role-badge" :class="'role-' + roleType">
					{{ getRoleTypeName(roleType) }}
				</view>
				<view class="role-name" v-if="roleName">注册为：{{ roleName }} 的 {{ getRoleTypeName(roleType) }}</view>
				<view class="role-dept" v-if="department">{{ department }}</view>
			</view>
			
			<!-- 会员表单 -->
			<view v-if="roleType === 'member'" class="form">
				<view class="form-item">
					<text class="label">车主姓名 <text class="required">*</text></text>
					<input class="input" v-model="form.owners_name" placeholder="请输入车主姓名" />
				</view>
				<view class="form-item">
					<text class="label">车主性别</text>
					<picker :value="genderIndex" :range="genderOptions" @change="onGenderChange">
						<view class="picker">
							{{ form.owners_gender || '请选择性别' }}
						</view>
					</picker>
				</view>
				<view class="form-item">
					<text class="label">联系号码 <text class="required">*</text></text>
					<input class="input" v-model="form.contact_number" type="number" placeholder="请输入手机号" maxlength="11" />
				</view>
				<view class="form-item">
					<text class="label">驾驶证件</text>
					<input class="input" v-model="form.driving_license" placeholder="请输入驾驶证件号" />
				</view>
			</view>
			
			<!-- 信息员表单 -->
			<view v-if="roleType === 'info'" class="form">
				<view class="form-item">
					<text class="label">员工姓名 <text class="required">*</text></text>
					<input class="input" v-model="form.employee_name" placeholder="请输入员工姓名" />
				</view>
				<view class="form-item">
					<text class="label">员工工号</text>
					<input class="input" v-model="form.employee_work_number" :placeholder="employeeWorkNumber || '请输入工号'" disabled />
				</view>
				<view class="form-item">
					<text class="label">员工性别</text>
					<picker :value="genderIndex" :range="genderOptions" @change="onGenderChange">
						<view class="picker">
							{{ form.employee_gender || '请选择性别' }}
						</view>
					</picker>
				</view>
				<view class="form-item">
					<text class="label">联系号码</text>
					<input class="input" v-model="form.contact_number" type="number" placeholder="请输入手机号" maxlength="11" />
				</view>
			</view>
			
			<!-- 业务顾问表单 -->
			<view v-if="roleType === 'consultant'" class="form">
				<view class="form-item">
					<text class="label">员工姓名 <text class="required">*</text></text>
					<input class="input" v-model="form.employee_name" placeholder="请输入员工姓名" />
				</view>
				<view class="form-item">
					<text class="label">员工工号</text>
					<input class="input" v-model="form.employee_work_number" :placeholder="employeeWorkNumber || '请输入工号'" disabled />
				</view>
				<view class="form-item">
					<text class="label">性别</text>
					<picker :value="genderIndex" :range="genderOptions" @change="onGenderChange">
						<view class="picker">
							{{ form.consultant_gender || '请选择性别' }}
						</view>
					</picker>
				</view>
				<view class="form-item">
					<text class="label">联系号码</text>
					<input class="input" v-model="form.contact_number" type="number" placeholder="请输入手机号" maxlength="11" />
				</view>
				<view class="form-item" v-if="department">
					<text class="label">部门</text>
					<input class="input" v-model="form.department" :value="department" disabled />
				</view>
			</view>
			
			<!-- 财务审核表单 -->
			<view v-if="roleType === 'financial'" class="form">
				<view class="form-item">
					<text class="label">员工姓名 <text class="required">*</text></text>
					<input class="input" v-model="form.employee_name" placeholder="请输入员工姓名" />
				</view>
				<view class="form-item">
					<text class="label">员工工号</text>
					<input class="input" v-model="form.employee_work_number" :placeholder="employeeWorkNumber || '请输入工号'" disabled />
				</view>
				<view class="form-item">
					<text class="label">性别</text>
					<picker :value="genderIndex" :range="genderOptions" @change="onGenderChange">
						<view class="picker">
							{{ form.financial_gender || '请选择性别' }}
						</view>
					</picker>
				</view>
				<view class="form-item">
					<text class="label">联系号码</text>
					<input class="input" v-model="form.contact_number" type="number" placeholder="请输入手机号" maxlength="11" />
				</view>
			</view>
			
			<!-- 提交按钮 -->
			<button class="submit-btn" type="primary" :loading="submitting" @click="submitForm">提交注册</button>
		</view>
		
		<!-- 注册成功 -->
		<view v-if="step === 'success'" class="success-container">
			<view class="success-icon">✓</view>
			<text class="success-title">注册成功！</text>
			<text class="success-subtitle">您的信息已提交，请等待审核</text>
			<button class="back-btn" type="primary" @click="goBack">返回首页</button>
		</view>
		
		<!-- 加载中 -->
		<view v-if="step === 'loading'" class="loading-container">
			<view class="loading-icon"></view>
			<text class="loading-text">正在验证二维码...</text>
		</view>
		
		<!-- 错误提示 -->
		<view v-if="step === 'error'" class="error-container">
			<view class="error-icon">✕</view>
			<text class="error-title">验证失败</text>
			<text class="error-subtitle">{{ errorMessage }}</text>
			<button class="back-btn" type="default" @click="goBack">返回</button>
		</view>
	</view>
</template>

<script>
	import { scanRegister } from '@/api/scan_register.js';
	
	export default {
		data() {
			return {
				step: 'loading', // loading, form, success, error
				qrToken: '',
				roleType: '',
				roleName: '',
				employeeWorkNumber: '',
				department: '',
				submitting: false,
				errorMessage: '',
				genderOptions: ['男', '女', '未知'],
				genderIndex: 2,
				form: {
					// 会员
					owners_name: '',
					owners_gender: '',
					contact_number: '',
					driving_license: '',
					// 信息员/业务顾问/财务审核
					employee_name: '',
					employee_work_number: '',
					employee_gender: '',
					consultant_gender: '',
					financial_gender: '',
					// 业务顾问专用
					department: ''
				}
			}
		},
		onLoad(options) {
			if (options.token) {
				this.qrToken = options.token;
				this.verifyQrCode();
			} else {
				this.step = 'error';
				this.errorMessage = '无效的二维码';
			}
		},
		methods: {
			// 验证二维码
			async verifyQrCode() {
				this.step = 'loading';
				try {
					const res = await scanRegister.verify(this.qrToken);
					if (res.data.code === 200) {
						const data = res.data.data;
						this.roleType = data.role_type;
						this.roleName = data.role_name;
						this.employeeWorkNumber = data.employee_work_number;
						this.department = data.department;
						
						// 预填充工号
						if (this.employeeWorkNumber) {
							this.form.employee_work_number = this.employeeWorkNumber;
						}
						if (this.department) {
							this.form.department = this.department;
						}
						
						this.step = 'form';
					} else {
						this.step = 'error';
						this.errorMessage = res.data.message || '二维码验证失败';
					}
				} catch (e) {
					this.step = 'error';
					this.errorMessage = '网络错误，请重试';
				}
			},
			
			// 性别选择
			onGenderChange(e) {
				const index = e.detail.value;
				this.genderIndex = index;
				const gender = this.genderOptions[index];
				if (this.roleType === 'member') {
					this.form.owners_gender = gender;
				} else if (this.roleType === 'info') {
					this.form.employee_gender = gender;
				} else if (this.roleType === 'consultant') {
					this.form.consultant_gender = gender;
				} else if (this.roleType === 'financial') {
					this.form.financial_gender = gender;
				}
			},
			
			// 获取角色名称
			getRoleTypeName(type) {
				const map = {
					'member': '会员',
					'info': '信息员',
					'consultant': '业务顾问',
					'financial': '财务审核'
				};
				return map[type] || type;
			},
			
			// 提交表单
			async submitForm() {
				// 验证必填字段
				if (this.roleType === 'member') {
					if (!this.form.owners_name) {
						uni.showToast({ title: '请输入车主姓名', icon: 'none' });
						return;
					}
					if (!this.form.contact_number) {
						uni.showToast({ title: '请输入联系号码', icon: 'none' });
						return;
					}
					if (!/^1[3-9]\d{9}$/.test(this.form.contact_number)) {
						uni.showToast({ title: '手机号格式不正确', icon: 'none' });
						return;
					}
				} else {
					if (!this.form.employee_name) {
						uni.showToast({ title: '请输入员工姓名', icon: 'none' });
						return;
					}
				}
				
				this.submitting = true;
				
				try {
					const res = await scanRegister.register({
						qr_token: this.qrToken,
						role_type: this.roleType,
						...this.form
					});
					
					if (res.data.code === 200) {
						this.step = 'success';
					} else {
						uni.showToast({ title: res.data.message || '注册失败', icon: 'none' });
					}
				} catch (e) {
					uni.showToast({ title: '网络错误，请重试', icon: 'none' });
				} finally {
					this.submitting = false;
				}
			},
			
			// 返回首页
			goBack() {
				uni.switchTab({
					url: '/pages/index/index'
				});
			}
		}
	}
</script>

<style scoped>
.container {
	min-height: 100vh;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	padding: 40rpx;
}

.form-container {
	background: #fff;
	border-radius: 20rpx;
	padding: 40rpx;
	box-shadow: 0 10rpx 30rpx rgba(0,0,0,0.1);
}

.header {
	text-align: center;
	margin-bottom: 40rpx;
}

.logo {
	width: 120rpx;
	height: 120rpx;
	border-radius: 60rpx;
	margin-bottom: 20rpx;
}

.title {
	display: block;
	font-size: 40rpx;
	font-weight: bold;
	color: #333;
}

.subtitle {
	display: block;
	font-size: 26rpx;
	color: #999;
	margin-top: 10rpx;
}

.role-info {
	background: #f5f7fa;
	border-radius: 10rpx;
	padding: 30rpx;
	margin-bottom: 30rpx;
	text-align: center;
}

.role-badge {
	display: inline-block;
	padding: 10rpx 30rpx;
	border-radius: 30rpx;
	font-size: 28rpx;
	color: #fff;
	margin-bottom: 10rpx;
}

.role-badge.role-member { background: #67c23a; }
.role-badge.role-info { background: #e6a23c; }
.role-badge.role-consultant { background: #409eff; }
.role-badge.role-financial { background: #909399; }

.role-name {
	font-size: 28rpx;
	color: #333;
	margin-top: 10rpx;
}

.role-dept {
	font-size: 24rpx;
	color: #666;
	margin-top: 5rpx;
}

.form {
	margin-bottom: 40rpx;
}

.form-item {
	margin-bottom: 30rpx;
}

.label {
	display: block;
	font-size: 28rpx;
	color: #333;
	margin-bottom: 10rpx;
}

.required {
	color: #f56c6c;
}

.input {
	width: 100%;
	height: 80rpx;
	border: 1rpx solid #dcdfe6;
	border-radius: 10rpx;
	padding: 0 20rpx;
	font-size: 28rpx;
	box-sizing: border-box;
}

.picker {
	width: 100%;
	height: 80rpx;
	border: 1rpx solid #dcdfe6;
	border-radius: 10rpx;
	padding: 0 20rpx;
	line-height: 80rpx;
	font-size: 28rpx;
	color: #606266;
	box-sizing: border-box;
}

.submit-btn {
	width: 100%;
	height: 90rpx;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	border-radius: 45rpx;
	font-size: 32rpx;
	line-height: 90rpx;
	border: none;
}

.loading-container,
.success-container,
.error-container {
	background: #fff;
	border-radius: 20rpx;
	padding: 80rpx 40rpx;
	text-align: center;
	box-shadow: 0 10rpx 30rpx rgba(0,0,0,0.1);
}

.loading-icon {
	width: 80rpx;
	height: 80rpx;
	border: 4rpx solid #f3f3f3;
	border-top: 4rpx solid #667eea;
	border-radius: 50%;
	margin: 0 auto 30rpx;
	animation: spin 1s linear infinite;
}

@keyframes spin {
	from { transform: rotate(0deg); }
	to { transform: rotate(360deg); }
}

.loading-text {
	font-size: 28rpx;
	color: #666;
}

.success-icon {
	width: 120rpx;
	height: 120rpx;
	background: #67c23a;
	color: #fff;
	border-radius: 50%;
	font-size: 60rpx;
	line-height: 120rpx;
	margin: 0 auto 30rpx;
}

.success-title {
	display: block;
	font-size: 36rpx;
	font-weight: bold;
	color: #333;
	margin-bottom: 15rpx;
}

.success-subtitle {
	display: block;
	font-size: 28rpx;
	color: #666;
	margin-bottom: 50rpx;
}

.error-icon {
	width: 120rpx;
	height: 120rpx;
	background: #f56c6c;
	color: #fff;
	border-radius: 50%;
	font-size: 60rpx;
	line-height: 120rpx;
	margin: 0 auto 30rpx;
}

.error-title {
	display: block;
	font-size: 36rpx;
	font-weight: bold;
	color: #333;
	margin-bottom: 15rpx;
}

.error-subtitle {
	display: block;
	font-size: 28rpx;
	color: #666;
	margin-bottom: 50rpx;
}

.back-btn {
	width: 80%;
	height: 90rpx;
	background: #fff;
	color: #667eea;
	border: 2rpx solid #667eea;
	border-radius: 45rpx;
	font-size: 32rpx;
	line-height: 90rpx;
	margin-top: 30rpx;
}
</style>
