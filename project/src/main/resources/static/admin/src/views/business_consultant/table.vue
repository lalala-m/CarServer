<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="员工姓名">
						<el-input v-model="query.employee_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="员工工号">
						<el-input v-model="query.employee_work_number"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="所属门店">
						<el-select v-model="query.store_id" placeholder="请选择门店" clearable>
							<el-option label="总部" :value="0"></el-option>
							<el-option v-for="store in storeList" :key="store.store_id" :label="store.store_name" :value="store.store_id"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">
					<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">
						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
						<el-button v-if="$check_action('/business_consultant/table','add') || $check_action('/business_consultant/view','add')" @click="$router.push('./view?')">添加</el-button>
						<el-button v-if="$check_action('/business_consultant/table','del') || $check_action('/business_consultant/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
						<download-excel v-if="$check_option('/business_consultant/table','import_db')" class="export-excel-wrapper" :data="DetailsForm" :fields="json_fields" name="业务顾问数据导入表格.xls">
							<el-button type="success">下载导入文档</el-button>
						</download-excel>
						<el-upload v-if="$check_option('/business_consultant/table','import_db')" action accept=".xlsx, .xls" :auto-upload="false" :show-file-list="false" :on-change="handle_import">
							<el-button type="primary">导入</el-button>
						</el-upload>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="consultant_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :default-expand-all="isExpandAll" ref="tableRef">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55"></el-table-column>
			<el-table-column prop="user_id" label="用户ID" width="80"></el-table-column>
			<el-table-column prop="username" label="用户名" width="120"></el-table-column>
			<el-table-column fixed prop="nickname" label="昵称"></el-table-column>
			<el-table-column prop="employee_work_number" @sort-change="$sortChange" label="员工工号" min-width="150">
				<template slot-scope="scope">{{ scope.row["employee_work_number"] }}</template>
			</el-table-column>
			<el-table-column prop="employee_name" @sort-change="$sortChange" label="员工姓名" min-width="150">
				<template slot-scope="scope">{{ scope.row["employee_name"] }}</template>
			</el-table-column>
			<el-table-column prop="consultant_gender" @sort-change="$sortChange" label="性别" min-width="120">
				<template slot-scope="scope">{{ scope.row["consultant_gender"] }}</template>
			</el-table-column>
			<el-table-column prop="contact_number" @sort-change="$sortChange" label="联系号码" min-width="150">
				<template slot-scope="scope">{{ scope.row["contact_number"] }}</template>
			</el-table-column>
			<el-table-column prop="department" @sort-change="$sortChange" label="部门" min-width="150">
				<template slot-scope="scope">{{ scope.row["department"] }}</template>
			</el-table-column>
			<el-table-column prop="store_name" @sort-change="$sortChange" label="所属门店" min-width="150">
				<template slot-scope="scope">{{ scope.row["store_id"] === 0 ? '总部' : (scope.row["store_name"] || '门店' + scope.row["store_id"]) }}</template>
			</el-table-column>
			<el-table-column prop="examine_state" @sort-change="$sortChange" label="审核状态" min-width="120">
				<template slot-scope="scope">
					<el-tag :type="getExamineStateType(scope.row['examine_state'])">{{ scope.row["examine_state"] }}</el-tag>
				</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}</template>
            </el-table-column>
			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/business_consultant/table','set') || $check_action('/business_consultant/view','set') || $check_action('/business_consultant/view','get')">
				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/business_consultant/table','set') || $check_action('/business_consultant/view','set') || $check_action('/business_consultant/view','get')"
						:to="'./view?consultant_id=' + scope.row['consultant_id']" size="small">
						<span>详情</span>
					</router-link>
					<el-button v-if="$check_action('/business_consultant/table','set')" class="el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="generateQrCode(scope.row)">
						<span>生成二维码</span>
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
		<el-dialog title="二维码" :visible.sync="qrCodeDialogVisible" width="400px" center>
			<div style="text-align: center;">
				<canvas ref="qrCodeCanvas" style="margin: 0 auto;"></canvas>
				<p style="margin-top: 15px; color: #666;">扫描二维码进行注册</p>
				<p style="margin-top: 5px; color: #999; font-size: 12px;">Token: {{ currentQrToken }}</p>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="qrCodeDialogVisible = false">关闭</el-button>
			</div>
		</el-dialog>
	</el-main>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import * as XLSX from 'xlsx/xlsx.mjs'
	import QRCode from 'qrcodejs2'
	        
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				showModal: false,
				url_get_list: "~/api/business_consultant/get_list",
				url_del: "~/api/business_consultant/del?",
				field: "consultant_id",
				query: {
					"size": 7,
					"page": 1,
					"employee_name": "",
					"employee_work_number": "",
					"store_id": "",
					"orderby": `create_time desc`
				},
				list: [],
				storeList: [],
				json_fields: {
					"员工工号": "employee_work_number",
					"员工姓名": "employee_name",
					"性别": "consultant_gender",
					"联系号码": "contact_number",
					"部门": "department",
				},
				DetailsForm: [
					{
						"employee_work_number": "填写工号",
						"employee_name": "填写姓名",
						"consultant_gender": "下拉类型",
						"contact_number": "手机类型",
						"department": "文本类型",
					},
				],
				isExpandAll: true,
				expandKeys: [],
				prevSelection: [],
				qrCodeDialogVisible: false,
				currentQrToken: '',
				qrCodeInstance: null,
			}
		},
		methods: {
			getExamineStateType(state) {
				switch(state) {
					case '待审核': return 'warning';
					case '已通过': return 'success';
					case '已拒绝': return 'danger';
					default: return 'info';
				}
			},
			loadStoreList() {
				this.$get("~/api/store/get_list", {
					size: 1000,
					page: 1
				}, (json) => {
					if (json.result) {
						this.storeList = json.result.list || [];
					}
				});
			},
			closeModal() {
				this.showModal = false;
			},
			generateQrCode(row) {
				this.currentQrToken = row.employee_work_number + '_' + Date.now();
				
				// 创建二维码记录
				this.$post("~/api/qr_code_register/add?", {
					qr_token: this.currentQrToken,
					role_type: 'consultant',
					role_id: row.consultant_id,
					role_name: row.employee_name,
					employee_work_number: row.employee_work_number,
					department: row.department,
					status: '未使用'
				}, (json) => {
					if (json.result) {
						this.qrCodeDialogVisible = true;
						this.$nextTick(() => {
							this.renderQrCode();
						});
					} else if (json.error) {
						this.$toast(json.error.message, 'danger');
					}
				});
			},
			renderQrCode() {
				if (this.qrCodeInstance) {
					this.qrCodeInstance.clear();
				}
				let qrUrl = window.location.origin + '/#/scan?token=' + this.currentQrToken;
				this.qrCodeInstance = new QRCode(this.$refs.qrCodeCanvas, {
					text: qrUrl,
					width: 200,
					height: 200,
					colorDark: '#000000',
					colorLight: '#ffffff',
					correctLevel: QRCode.CorrectLevel.H
				});
			},
			delInfo() {
			    var list = this.selection;
			    if (list.length === 0) {
			        this.$message({ type: 'info', message: '选择对象不能为空!' });
			        return;
			    }
			    this.$confirm('删除后数据将无法恢复，请确认是否删除？', '提示', {
			        confirmButtonText: '确定',
			        cancelButtonText: '取消',
			        type: 'warning'
			    }).then(async () => {
			        await this.delAll(list, async (list) => {
						var bl = true;
						for(var i = 0; i < list.length; i++) {
							var user_id = list[i].user_id;
							var res = await this.$get("~/api/user/del?", {user_id});
							if(res.result) {
								console.log("删除成功" + i);
							} else {
								console.log("删除失败" + i);
								bl = false;
								break;
							}
						}
						if(bl) {
							this.$message({ type: 'success', message: '删除成功!' });
							this.get_list();
						}
					});
			    }).catch(() => {
			        this.$message({ type: 'info', message: '已取消删除' });
			    });
			},
			get_list_after(param) {
				let _this = this;
				for (let i = 0; i < this.list.length; i++) {
					let user_id = _this.list[i].user_id;
					_this.$get("~/api/user/get_obj?", { user_id }, (json) => {
						if (json.result.obj) {
							_this.$delete(_this.list[i], 'username');
							_this.$set(_this.list[i], 'username', json.result.obj.username);
							_this.$delete(_this.list[i], 'nickname');
							_this.$set(_this.list[i], 'nickname', json.result.obj.nickname);
						}
					});
					// 获取门店名称
					if (_this.list[i].store_id && _this.list[i].store_id !== 0) {
						_this.$get("~/api/store/get_obj?", { store_id: _this.list[i].store_id }, (storeJson) => {
							if (storeJson.result && storeJson.result.obj) {
								this.$set(this.list[i], 'store_name', storeJson.result.obj.store_name);
							}
						});
					}
				}
			},
			readFile(file) {
				return new Promise(resolve => {
					let reader = new FileReader();
					reader.readAsBinaryString(file);
					reader.onload = ev => { resolve(ev.target.result); }
				})
			},
			async handle_import(ev) {
				let file = ev.raw;
				if (!file) { return; }
				let data = await this.readFile(file);
				let workbook = XLSX.read(data, { type: "binary" });
				let worksheet = workbook.Sheets[workbook.SheetNames[0]];
				let result = XLSX.utils.sheet_to_json(worksheet);
				result.forEach((item) => {
					for (let k in this.json_fields) {
						let newKey = this.json_fields[k];
						if (newKey) {
							item[newKey] = item[k];
							delete item[k];
						}
					}
				});
				let _this = this;
				for (let i = 0; i < result.length; i++) {
					let form_user = {};
					if (result[i].employee_work_number) { form_user.username = result[i].employee_work_number; }
					delete result[i]["username"]
					if (result[i].employee_name) { form_user.nickname = result[i].employee_name; }
					delete result[i]["nickname"]
					form_user.user_group = "业务顾问";
					await this.$post("~/api/user/add?", form_user, function (json, status) {
						if (json.result) {
							_this.$get("~/api/user/get_obj?", form_user, (res) => {
								if (res.result && res.result.obj) {
									result[i].user_id = res.result.obj.user_id;
									result[i].examine_state = "待审核";
									_this.$post("~/api/business_consultant/add?", result[i], function (json, status) {
										if (json.result) {
											_this.$toast("导入成功", 'success');
										} else if (json.error) {
											_this.$toast(json.error.message, 'danger');
										}
									});
								}
							})
						} else if (json.error) {
							_this.$toast(json.error.message, 'danger');
						}
					});
				}
			},
		},
		created() {
			this.loadStoreList();
		},
		computed: {
			hasExtraData() {
				return this.list.some(item => item.extra && item.extra.trim() !== '');
			}
		}
	}
</script>
<style type="text/css">
	.bg { background: white; }
	.form.p_4 { padding: 1rem; }
	.form .el-input { width: initial; }
	.mt { margin-top: 1rem; }
	.text_center { text-align: center; }
	.float-right { float: right; }
</style>
