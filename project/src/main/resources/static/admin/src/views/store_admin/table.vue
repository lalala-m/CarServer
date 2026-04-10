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
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="审核状态">
						<el-select v-model="query.examine_state" placeholder="请选择状态" clearable>
							<el-option label="待审核" value="待审核"></el-option>
							<el-option label="已通过" value="已通过"></el-option>
							<el-option label="已拒绝" value="已拒绝"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">
					<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">
						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
						<el-button v-if="$check_action('/store_admin/table','add') || $check_action('/store_admin/view','add')" type="primary" @click="$router.push('./view?')">添加</el-button>
						<el-button v-if="$check_action('/store_admin/table','del') || $check_action('/store_admin/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
						<el-button type="warning" @click="batchExamine()" :disabled="selection.length === 0">批量审核</el-button>
						<el-button type="success" @click="batchEnable()" :disabled="selection.length === 0">批量启用</el-button>
						<download-excel v-if="$check_option('/store_admin/table','import_db')" class="export-excel-wrapper" :data="DownloadForm" :fields="json_fields" name="4S店管理员数据导入表格.xls">
							<el-button type="success">下载导入文档</el-button>
						</download-excel>
						<el-upload v-if="$check_option('/store_admin/table','import_db')" action accept=".xlsx, .xls" :auto-upload="false" :show-file-list="false" :on-change="handle_import">
							<el-button type="primary">导入</el-button>
						</el-upload>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="store_admin_id" :default-expand-all="isExpandAll" ref="tableRef">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55"></el-table-column>
			<el-table-column prop="user_id" label="用户ID" width="80"></el-table-column>
			<el-table-column prop="username" label="用户名" width="120"></el-table-column>
			<el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
			<el-table-column prop="employee_work_number" @sort-change="$sortChange" label="员工工号" min-width="150">
				<template slot-scope="scope">{{ scope.row["employee_work_number"] }}</template>
			</el-table-column>
			<el-table-column prop="employee_name" @sort-change="$sortChange" label="员工姓名" min-width="150">
				<template slot-scope="scope">{{ scope.row["employee_name"] }}</template>
			</el-table-column>
			<el-table-column prop="employee_gender" @sort-change="$sortChange" label="性别" min-width="120">
				<template slot-scope="scope">{{ scope.row["employee_gender"] }}</template>
			</el-table-column>
			<el-table-column prop="contact_number" @sort-change="$sortChange" label="联系号码" min-width="150">
				<template slot-scope="scope">{{ scope.row["contact_number"] }}</template>
			</el-table-column>
			<el-table-column prop="store_name" @sort-change="$sortChange" label="所属门店" min-width="150">
				<template slot-scope="scope">{{ scope.row["store_id"] === 0 ? '总部' : (scope.row["store_name"] || '门店' + scope.row["store_id"]) }}</template>
			</el-table-column>
			<el-table-column prop="examine_state" @sort-change="$sortChange" label="审核状态" min-width="120">
				<template slot-scope="scope">
					<el-tag :type="getExamineStateType(scope.row['examine_state'])">{{ scope.row["examine_state"] }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="state" @sort-change="$sortChange" label="账号状态" min-width="120">
				<template slot-scope="scope">
					<el-tag :type="scope.row['state'] === 1 ? 'success' : 'info'">{{ scope.row['state'] === 1 ? '启用' : '禁用' }}</el-tag>
				</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}</template>
            </el-table-column>
			<el-table-column fixed="right" label="操作" min-width="300" v-if="$check_action('/store_admin/table','set') || $check_action('/store_admin/view','set') || $check_action('/store_admin/view','get')">
				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/store_admin/table','set') || $check_action('/store_admin/view','set') || $check_action('/store_admin/view','get')"
						:to="'./view?store_admin_id=' + scope.row['store_admin_id']" size="small">
						<span>详情</span>
					</router-link>
					<el-button v-if="$check_action('/store_admin/table','set')" class="el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="examineRow(scope.row)">
						<span>审核</span>
					</el-button>
					<el-button v-if="$check_action('/store_admin/table','set')" class="el-button--small is-plain" style="margin: 5px !important;" size="small" :type="scope.row['state'] === 1 ? 'warning' : 'success'" @click="toggleState(scope.row)">
						<span>{{ scope.row['state'] === 1 ? '禁用' : '启用' }}</span>
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
		
		<!-- 批量审核弹窗 -->
		<el-dialog title="批量审核" :visible.sync="batchExamineDialogVisible" width="500px">
			<el-form :model="batchExamineForm" label-width="100px">
				<el-form-item label="审核操作">
					<el-radio-group v-model="batchExamineForm.action">
						<el-radio label="pass">全部通过</el-radio>
						<el-radio label="reject">全部拒绝</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="审核备注">
					<el-input type="textarea" v-model="batchExamineForm.remark" placeholder="请输入审核备注（可选）"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="batchExamineDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="submitBatchExamine">确定</el-button>
			</div>
		</el-dialog>
		
		<!-- 单条审核弹窗 -->
		<el-dialog title="审核信息管理员" :visible.sync="examineDialogVisible" width="500px">
			<el-form :model="examineForm" label-width="100px">
				<el-form-item label="审核操作">
					<el-radio-group v-model="examineForm.action">
						<el-radio label="pass">通过</el-radio>
						<el-radio label="reject">拒绝</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="审核备注">
					<el-input type="textarea" v-model="examineForm.remark" placeholder="请输入审核备注（可选）"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="examineDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="submitExamine">确定</el-button>
			</div>
		</el-dialog>
	</el-main>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import * as XLSX from 'xlsx/xlsx.mjs'
	        
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				url_get_list: "~/api/store_admin/get_list",
				url_del: "~/api/store_admin/del?",
				field: "store_admin_id",
				query: {
					"size": 7,
					"page": 1,
					"employee_name": "",
					"employee_work_number": "",
					"store_id": "",
					"examine_state": "",
					"orderby": `create_time desc`
				},
				list: [],
				storeList: [],
				selection: [],
				json_fields: {
					"员工工号": "employee_work_number",
					"员工姓名": "employee_name",
					"性别": "employee_gender",
					"联系号码": "contact_number",
				},
				DownloadForm: [
					{
						"employee_work_number": "",
						"employee_name": "",
						"employee_gender": "",
						"contact_number": "",
					},
				],
				isExpandAll: true,
				expandKeys: [],
				prevSelection: [],
				// 批量审核
				batchExamineDialogVisible: false,
				batchExamineForm: {
					action: 'pass',
					remark: ''
				},
				// 单条审核
				examineDialogVisible: false,
				examineRowData: null,
				examineForm: {
					action: 'pass',
					remark: ''
				},
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
			// 加载门店列表
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
			// 批量审核
			batchExamine() {
				if (this.selection.length === 0) {
					this.$message({ type: 'info', message: '请先选择要审核的记录' });
					return;
				}
				this.batchExamineDialogVisible = true;
			},
			async submitBatchExamine() {
				const ids = this.selection.map(item => item.store_admin_id);
				const state = this.batchExamineForm.action === 'pass' ? '已通过' : '已拒绝';
				
				for (let id of ids) {
					await this.$post("~/api/store_admin/set?", {
						store_admin_id: id,
						examine_state: state
					}, (json) => {
						if (!json.result) {
							console.log("审核失败: " + id);
						}
					});
				}
				
				this.$message({ type: 'success', message: '批量审核完成' });
				this.batchExamineDialogVisible = false;
				this.get_list();
			},
			// 单条审核
			examineRow(row) {
				this.examineRowData = row;
				this.examineForm = { action: 'pass', remark: '' };
				this.examineDialogVisible = true;
			},
			async submitExamine() {
				if (!this.examineRowData) return;
				
				const state = this.examineForm.action === 'pass' ? '已通过' : '已拒绝';
				
				await this.$post("~/api/store_admin/set?", {
					store_admin_id: this.examineRowData.store_admin_id,
					examine_state: state
				}, (json) => {
					if (json.result) {
						this.$message({ type: 'success', message: '审核操作成功' });
						this.examineDialogVisible = false;
						this.get_list();
					} else {
						this.$message({ type: 'error', message: json.error?.message || '操作失败' });
					}
				});
			},
			// 切换启用/禁用状态
			async toggleState(row) {
				const newState = row.state === 1 ? 0 : 1;
				
				await this.$post("~/api/user/set?", {
					user_id: row.user_id,
					state: newState
				}, (json) => {
					if (json.result) {
						this.$message({ type: 'success', message: newState === 1 ? '已启用' : '已禁用' });
						this.get_list();
					} else {
						this.$message({ type: 'error', message: json.error?.message || '操作失败' });
					}
				});
			},
			// 批量启用
			async batchEnable() {
				if (this.selection.length === 0) {
					this.$message({ type: 'info', message: '请先选择要启用的记录' });
					return;
				}
				
				for (let row of this.selection) {
					await this.$post("~/api/store_admin/set?", {
						store_admin_id: row.store_admin_id,
						examine_state: '已通过'
					}, (json) => {
						if (json.result) {
							this.$post("~/api/user/set?", {
								user_id: row.user_id,
								state: 1
							}, (userJson) => {
								if (!userJson.result) {
									console.log("启用用户失败: " + row.user_id);
								}
							});
						}
					});
				}
				
				this.$message({ type: 'success', message: '批量启用完成' });
				this.get_list();
			},
			closeModal() {
				this.showModal = false;
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
						if (json.result && json.result.obj) {
							this.$set(this.list[i], 'username', json.result.obj.username);
							this.$set(this.list[i], 'nickname', json.result.obj.nickname);
							this.$set(this.list[i], 'state', json.result.obj.state);
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
			// 导入功能
			readFile(file) {
				return new Promise(resolve => {
					let reader = new FileReader();
					reader.readAsBinaryString(file);
					reader.onload = ev => {
						resolve(ev.target.result);
					}
				})
			},
			async handle_import(ev) {
				let file = ev.raw;
				if (!file) {
					console.log("文件打开失败");
					return;
				}
				
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
					if (result[i].username) form_user.username = result[i].username;
					delete result[i]["username"];
					if (result[i].password) form_user.password = result[i].password;
					delete result[i]["password"];
					if (result[i].nickname) form_user.nickname = result[i].nickname;
					delete result[i]["nickname"];
					if (result[i].email) form_user.email = result[i].email;
					delete result[i]["email"];
					
					form_user.user_group = "4S店管理员";
					
					await this.$post("~/api/user/add?", form_user, function (json, status) {
						if (json.result) {
							delete form_user["password"];
							_this.$get("~/api/user/get_obj?", form_user, (res) => {
								if (res.result && res.result.obj) {
									result[i].user_id = res.result.obj.user_id;
									result[i].examine_state = "待审核";
									_this.$post("~/api/store_admin/add?", result[i], function (resJson, status) {
										if (resJson.result) {
											_this.$toast("导入成功", 'success');
										} else if (resJson.error) {
											_this.$toast(resJson.error.message, 'danger');
										}
									});
								}
							});
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
	}
</script>

<style type="text/css">
	.bg {
		background: white;
	}
	.form.p_4 {
		padding: 1rem;
	}
	.form .el-input {
		width: initial;
	}
	.mt {
		margin-top: 1rem;
	}
	.text_center {
		text-align: center;
	}
	.view_a {
		display: flex;
		flex-wrap: wrap;
	}
</style>
