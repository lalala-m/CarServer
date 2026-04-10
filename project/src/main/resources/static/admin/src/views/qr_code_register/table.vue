<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="角色类型">
						<el-select v-model="query.role_type" placeholder="请选择">
							<el-option label="全部" value=""></el-option>
							<el-option label="会员" value="member"></el-option>
							<el-option label="信息员" value="info"></el-option>
							<el-option label="业务顾问" value="consultant"></el-option>
							<el-option label="财务审核" value="financial"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="状态">
						<el-select v-model="query.status" placeholder="请选择">
							<el-option label="全部" value=""></el-option>
							<el-option label="未使用" value="未使用"></el-option>
							<el-option label="已使用" value="已使用"></el-option>
							<el-option label="已失效" value="已失效"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="角色姓名">
						<el-input v-model="query.role_name"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">
					<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">
						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="qr_id" :default-expand-all="isExpandAll" ref="tableRef">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55"></el-table-column>
			<el-table-column prop="qr_id" label="ID" width="80"></el-table-column>
			<el-table-column prop="qr_token" @sort-change="$sortChange" label="二维码Token" min-width="200" show-overflow-tooltip>
				<template slot-scope="scope">{{ scope.row["qr_token"] }}</template>
			</el-table-column>
			<el-table-column prop="role_type" @sort-change="$sortChange" label="角色类型" min-width="120">
				<template slot-scope="scope">
					<el-tag :type="getRoleTypeTag(scope.row['role_type'])">{{ getRoleTypeName(scope.row["role_type"]) }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="role_name" @sort-change="$sortChange" label="角色姓名" min-width="120">
				<template slot-scope="scope">{{ scope.row["role_name"] }}</template>
			</el-table-column>
			<el-table-column prop="employee_work_number" @sort-change="$sortChange" label="员工工号" min-width="150">
				<template slot-scope="scope">{{ scope.row["employee_work_number"] || '-' }}</template>
			</el-table-column>
			<el-table-column prop="department" @sort-change="$sortChange" label="部门" min-width="120">
				<template slot-scope="scope">{{ scope.row["department"] || '-' }}</template>
			</el-table-column>
			<el-table-column prop="status" @sort-change="$sortChange" label="状态" min-width="100">
				<template slot-scope="scope">
					<el-tag :type="getStatusTag(scope.row['status'])">{{ scope.row["status"] }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="bind_user_id" @sort-change="$sortChange" label="绑定用户ID" min-width="100">
				<template slot-scope="scope">{{ scope.row["bind_user_id"] || '-' }}</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="180">
                <template slot-scope="scope">{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}</template>
            </el-table-column>
			<el-table-column sortable prop="expire_time" label="过期时间" min-width="180">
                <template slot-scope="scope">{{ scope.row["expire_time"] ? $toTime(scope.row["expire_time"],"yyyy-MM-dd hh:mm:ss") : '永不过期' }}</template>
            </el-table-column>
			<el-table-column fixed="right" label="操作" min-width="150">
				<template slot-scope="scope">
					<div class="view_a">
					<el-button class="el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="viewQrCode(scope.row)">
						<span>查看二维码</span>
					</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		
		<!-- 二维码弹窗 -->
		<el-dialog :title="'二维码 - ' + currentRow.role_name" :visible.sync="qrCodeDialogVisible" width="400px" center>
			<div style="text-align: center;">
				<canvas ref="qrCodeCanvas" style="margin: 0 auto;"></canvas>
				<p style="margin-top: 15px; color: #666;">扫描二维码进行注册</p>
				<p style="margin-top: 5px; color: #999; font-size: 12px;">Token: {{ currentRow.qr_token }}</p>
				<p style="margin-top: 5px;">
					<el-tag :type="getStatusTag(currentRow.status)">{{ currentRow.status }}</el-tag>
				</p>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="qrCodeDialogVisible = false">关闭</el-button>
			</div>
		</el-dialog>
	</el-main>
</template>
<script>
	import mixin from "@/mixins/page.js";
	        
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				url_get_list: "~/api/qr_code_register/get_list",
				url_del: "~/api/qr_code_register/del?",
				field: "qr_id",
				query: {
					"size": 10,
					"page": 1,
					"role_type": "",
					"status": "",
					"role_name": "",
					"orderby": `create_time desc`
				},
				list: [],
				isExpandAll: true,
				qrCodeDialogVisible: false,
				currentRow: {},
				qrCodeInstance: null,
			}
		},
		methods: {
			getRoleTypeName(type) {
				const map = {
					'member': '会员',
					'info': '信息员',
					'consultant': '业务顾问',
					'financial': '财务审核'
				};
				return map[type] || type;
			},
			getRoleTypeTag(type) {
				const map = {
					'member': 'success',
					'info': 'warning',
					'consultant': 'primary',
					'financial': 'info'
				};
				return map[type] || 'info';
			},
			getStatusTag(status) {
				const map = {
					'未使用': 'success',
					'已使用': 'info',
					'已失效': 'danger'
				};
				return map[status] || 'info';
			},
			viewQrCode(row) {
				this.currentRow = row;
				this.qrCodeDialogVisible = true;
				this.$nextTick(() => {
					this.renderQrCode();
				});
			},
			renderQrCode() {
				if (this.qrCodeInstance) {
					this.qrCodeInstance.clear();
					this.qrCodeInstance = null;
				}
				// 动态加载 qrcodejs2
				let script = document.createElement('script');
				script.src = 'https://cdn.bootcdn.net/ajax/libs/qrcodejs/1.0.0/qrcode.min.js';
				script.onload = () => {
					let qrUrl = window.location.origin + '/#/scan?token=' + this.currentRow.qr_token;
					this.qrCodeInstance = new QRCode(this.$refs.qrCodeCanvas, {
						text: qrUrl,
						width: 200,
						height: 200,
						colorDark: '#000000',
						colorLight: '#ffffff',
						correctLevel: QRCode.CorrectLevel.H
					});
				};
				document.head.appendChild(script);
			},
		},
		created() {}
	}
</script>
<style type="text/css">
	.bg { background: white; }
	.form.p_4 { padding: 1rem; }
	.form .el-input { width: initial; }
	.mt { margin-top: 1rem; }
	.text_center { text-align: center; }
</style>
