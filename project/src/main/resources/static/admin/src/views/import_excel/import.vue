<template>
	<el-main class="bg import_excel_page">
		<!-- 步骤指示器 -->
		<el-steps :active="currentStep" align-center class="step-indicator">
			<el-step title="选择角色类型" description=""></el-step>
			<el-step title="下载模板" description=""></el-step>
			<el-step title="上传文件" description=""></el-step>
			<el-step title="导入结果" description=""></el-step>
		</el-steps>

		<!-- 步骤1: 选择角色类型 -->
		<div v-show="currentStep === 0" class="step-content">
			<el-card shadow="hover">
				<div slot="header">
					<span>选择要导入的角色类型</span>
				</div>
				<el-form label-width="120px">
					<el-form-item label="角色类型">
						<el-radio-group v-model="form.role_type" @change="onRoleTypeChange">
							<el-radio label="store_admin">
								<span class="role-label">4S店信息管理员</span>
								<span class="role-desc">由总部管理员添加，需审核后启用</span>
							</el-radio>
							<el-radio label="headquarter_info">
								<span class="role-label">总部信息员</span>
								<span class="role-desc">由总部管理员添加，直接启用</span>
							</el-radio>
							<el-radio label="consultant">
								<span class="role-label">业务顾问</span>
								<span class="role-desc">由4S店管理员添加，需审核后启用</span>
							</el-radio>
							<el-radio label="financial">
								<span class="role-label">财务审核</span>
								<span class="role-desc">由4S店管理员添加，需审核后启用</span>
							</el-radio>
							<el-radio label="member">
								<span class="role-label">会员</span>
								<span class="role-desc">车主用户，可指定所属店面</span>
							</el-radio>
						</el-radio-group>
					</el-form-item>
				</el-form>
			</el-card>
		</div>

		<!-- 步骤2: 下载模板 -->
		<div v-show="currentStep === 1" class="step-content">
			<el-card shadow="hover">
				<div slot="header">
					<span>下载导入模板</span>
				</div>
				<el-alert
					type="info"
					:closable="false"
					show-icon
					class="mb-20">
					<div slot="title">
						<p><strong>当前选择：{{ getRoleLabel(form.role_type) }}</strong></p>
						<p class="mt-10">请下载模板文件，填写数据后上传</p>
					</div>
				</el-alert>

				<div class="template-info">
					<h4>模板字段说明：</h4>
					<ul>
						<li v-for="(field, index) in currentTemplateFields" :key="index">
							{{ field }}
						</li>
					</ul>
				</div>

				<el-button type="primary" icon="el-icon-download" @click="downloadTemplate" size="medium">
					下载模板
				</el-button>
			</el-card>
		</div>

		<!-- 步骤3: 上传文件 -->
		<div v-show="currentStep === 2" class="step-content">
			<el-card shadow="hover">
				<div slot="header">
					<span>上传 Excel 文件</span>
				</div>

				<el-upload
					class="upload-demo"
					drag
					action=""
					:http-request="handleUpload"
					:on-change="handleFileChange"
					:on-remove="handleFileRemove"
					accept=".xlsx,.xls"
					:auto-upload="false"
					limit="1">
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将 Excel 文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip">只能上传 xlsx/xls 文件，且不超过 5MB</div>
				</el-upload>

				<div v-if="previewData.length > 0" class="preview-section">
					<h4>数据预览：</h4>
					<el-table :data="previewData" max-height="300" border size="small">
						<el-table-column v-for="(header, index) in previewHeaders" :key="index" :prop="header" :label="header">
						</el-table-column>
					</el-table>
				</div>

				<div class="upload-tips">
					<el-alert type="warning" :closable="false" show-icon>
						<p>导入说明：</p>
						<ul>
							<li v-if="form.role_type === 'consultant' || form.role_type === 'financial'">
								数据将关联到您所在的4S店
							</li>
							<li v-if="form.role_type !== 'headquarter_info'">
								导入后需提交审核才能启用
							</li>
							<li>请确保数据格式与模板一致</li>
						</ul>
					</el-alert>
				</div>

				<el-button type="primary" @click="startImport" :loading="importing" :disabled="!file" class="mt-20">
					开始导入
				</el-button>
			</el-card>
		</div>

		<!-- 步骤4: 导入结果 -->
		<div v-show="currentStep === 3" class="step-content">
			<el-card shadow="hover">
				<div slot="header">
					<span>导入结果</span>
				</div>

				<div class="result-summary">
					<el-row :gutter="20">
						<el-col :span="6">
							<div class="result-card total">
								<div class="result-num">{{ importResult.total }}</div>
								<div class="result-label">总记录</div>
							</div>
						</el-col>
						<el-col :span="6">
							<div class="result-card success">
								<div class="result-num">{{ importResult.success }}</div>
								<div class="result-label">成功</div>
							</div>
						</el-col>
						<el-col :span="6">
							<div class="result-card pending">
								<div class="result-num">{{ importResult.pending_review }}</div>
								<div class="result-label">待审核</div>
							</div>
						</el-col>
						<el-col :span="6">
							<div class="result-card failed">
								<div class="result-num">{{ importResult.failed }}</div>
								<div class="result-label">失败</div>
							</div>
						</el-col>
					</el-row>
				</div>

				<div v-if="importResult.failed_list && importResult.failed_list.length > 0" class="failed-list mt-20">
					<h4>失败详情：</h4>
					<el-table :data="importResult.failed_list" border size="small" max-height="300">
						<el-table-column prop="row" label="行号" width="80"></el-table-column>
						<el-table-column prop="reason" label="失败原因"></el-table-column>
					</el-table>
				</div>

				<div class="result-actions mt-20">
					<el-button @click="resetStep">继续导入</el-button>
					<el-button type="primary" @click="$router.go(-1)">返回列表</el-button>
				</div>
			</el-card>
		</div>

		<!-- 步骤按钮 -->
		<div class="step-buttons">
			<el-button v-if="currentStep > 0" @click="prevStep" :disabled="importing">上一步</el-button>
			<el-button v-if="currentStep < 3" type="primary" @click="nextStep" :disabled="!canNext">下一步</el-button>
		</div>
	</el-main>
</template>

<script>
import * as XLSX from 'xlsx/xlsx.mjs'

export default {
	data() {
		return {
			currentStep: 0,
			form: {
				role_type: 'store_admin',
				store_id: 0
			},
			file: null,
			previewData: [],
			previewHeaders: [],
			importing: false,
			importResult: {
				total: 0,
				success: 0,
				failed: 0,
				pending_review: 0,
				failed_list: []
			}
		}
	},
	computed: {
		canNext() {
			if (this.currentStep === 0) {
				return !!this.form.role_type;
			}
			return true;
		},
		currentTemplateFields() {
			const fields = {
				'store_admin': ['员工工号（必填，唯一）', '员工姓名（必填）', '员工性别', '联系号码', '所属店面（必填）'],
				'headquarter_info': ['员工工号（必填，唯一）', '员工姓名（必填）', '员工性别', '联系号码'],
				'consultant': ['员工工号（必填，唯一）', '员工姓名（必填）', '性别', '联系号码', '所属部门'],
				'financial': ['员工工号（必填，唯一）', '员工姓名（必填）', '性别', '联系号码'],
				'member': ['车主姓名（必填）', '车主性别', '联系号码（必填，唯一）', '驾驶证件', '会员等级', '所属店面']
			};
			return fields[this.form.role_type] || [];
		}
	},
	methods: {
		getRoleLabel(roleType) {
			const labels = {
				'store_admin': '4S店信息管理员',
				'headquarter_info': '总部信息员',
				'consultant': '业务顾问',
				'financial': '财务审核',
				'member': '会员'
			};
			return labels[roleType] || roleType;
		},
		onRoleTypeChange() {
			// 重置预览数据
			this.file = null;
			this.previewData = [];
			this.previewHeaders = [];
		},
		prevStep() {
			if (this.currentStep > 0) {
				this.currentStep--;
			}
		},
		nextStep() {
			if (this.currentStep < 3) {
				this.currentStep++;
			}
		},
		downloadTemplate() {
			// 直接打开下载链接
			window.open('/import/download_template?role_type=' + this.form.role_type, '_blank');
		},
		handleFileChange(file, fileList) {
			this.file = file.raw;
			this.parsePreview(file.raw);
		},
		handleFileRemove(file, fileList) {
			this.file = null;
			this.previewData = [];
			this.previewHeaders = [];
		},
		handleUpload(param) {
			// 自定义上传，这里不使用
		},
		async parsePreview(file) {
			try {
				const data = await this.readFile(file);
				const workbook = XLSX.read(data, { type: 'binary' });
				const worksheet = workbook.Sheets[workbook.SheetNames[0]];
				const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

				if (jsonData.length > 0) {
					this.previewHeaders = jsonData[0];
					this.previewData = jsonData.slice(1, 11).map((row, index) => {
						let obj = { row: index + 2 };
						this.previewHeaders.forEach((header, i) => {
							obj[header] = row[i] || '';
						});
						return obj;
					});
				}
			} catch (e) {
				console.error('预览文件失败', e);
				this.$message.error('预览文件失败');
			}
		},
		readFile(file) {
			return new Promise((resolve) => {
				let reader = new FileReader();
				reader.readAsBinaryString(file);
				reader.onload = ev => {
					resolve(ev.target.result);
				}
			})
		},
		async startImport() {
			if (!this.file) {
				this.$message.warning('请先选择文件');
				return;
			}

			this.importing = true;

			try {
				let formData = new FormData();
				formData.append('file', this.file);
				formData.append('role_type', this.form.role_type);
				formData.append('store_id', this.form.store_id);

				this.$post('~/api/import/excel_upload', formData, (json) => {
					this.importing = false;
					if (json.result && json.result.result) {
						this.importResult = json.result.result;
						this.currentStep = 3;
						this.$message.success('导入完成');
					} else if (json.error) {
						this.$message.error(json.error.message || json.error);
					} else {
						this.$message.error('导入失败');
					}
				}, {
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				});
			} catch (e) {
				this.importing = false;
				this.$message.error('导入失败: ' + e.message);
			}
		},
		resetStep() {
			this.currentStep = 0;
			this.file = null;
			this.previewData = [];
			this.previewHeaders = [];
			this.importResult = {
				total: 0,
				success: 0,
				failed: 0,
				pending_review: 0,
				failed_list: []
			};
		}
	}
}
</script>

<style scoped>
.import_excel_page {
	padding: 20px;
}

.step-indicator {
	margin-bottom: 30px;
}

.step-content {
	max-width: 800px;
	margin: 0 auto;
}

.mb-20 {
	margin-bottom: 20px;
}

.mt-10 {
	margin-top: 10px;
}

.mt-20 {
	margin-top: 20px;
}

.role-label {
	font-weight: bold;
	margin-left: 5px;
}

.role-desc {
	color: #999;
	font-size: 12px;
	margin-left: 10px;
}

.template-info {
	background: #f5f7fa;
	padding: 15px;
	border-radius: 4px;
	margin-bottom: 20px;
}

.template-info h4 {
	margin: 0 0 10px 0;
}

.template-info ul {
	margin: 0;
	padding-left: 20px;
}

.template-info li {
	line-height: 1.8;
}

.preview-section {
	margin-top: 20px;
}

.upload-tips {
	margin-top: 20px;
}

.upload-tips ul {
	margin: 10px 0 0 0;
	padding-left: 20px;
}

.upload-tips li {
	line-height: 1.8;
}

.result-summary {
	margin-bottom: 20px;
}

.result-card {
	text-align: center;
	padding: 20px;
	border-radius: 8px;
	color: white;
}

.result-card.total {
	background: #409EFF;
}

.result-card.success {
	background: #67C23A;
}

.result-card.pending {
	background: #E6A23C;
}

.result-card.failed {
	background: #F56C6C;
}

.result-num {
	font-size: 36px;
	font-weight: bold;
}

.result-label {
	font-size: 14px;
	margin-top: 5px;
}

.failed-list {
	background: #fef0f0;
	padding: 15px;
	border-radius: 4px;
}

.step-buttons {
	text-align: center;
	margin-top: 30px;
}

.step-buttons .el-button {
	min-width: 100px;
}
</style>
