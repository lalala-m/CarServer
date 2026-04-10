<template>
	<el-main class="bg batch_review_page">
		<el-card shadow="hover">
			<div slot="header">
				<span>批量审核管理</span>
			</div>

			<!-- 筛选条件 -->
			<el-form :inline="true" :model="query" class="form_inline">
				<el-form-item label="角色类型">
					<el-select v-model="query.role_type" @change="onRoleTypeChange" placeholder="请选择">
						<el-option label="4S店信息管理员" value="store_admin"></el-option>
						<el-option label="总部信息员" value="headquarter_info"></el-option>
						<el-option label="业务顾问" value="consultant"></el-option>
						<el-option label="财务审核" value="financial"></el-option>
						<el-option label="会员" value="member"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="审核状态">
					<el-select v-model="query.examine_state" @change="getList" placeholder="请选择">
						<el-option label="待审核" value="待审核"></el-option>
						<el-option label="已通过" value="已通过"></el-option>
						<el-option label="已拒绝" value="已拒绝"></el-option>
						<el-option label="已启用" value="已启用"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" icon="el-icon-search" @click="getList">查询</el-button>
					<el-button @click="resetQuery">重置</el-button>
				</el-form-item>
			</el-form>

			<!-- 操作按钮 -->
			<div class="action_bar">
				<el-button type="success" icon="el-icon-check" @click="batchApproveAndEnable" :disabled="selection.length === 0">
					批量通过并启用
				</el-button>
				<el-button type="primary" icon="el-icon-circle-check" @click="batchApprove" :disabled="selection.length === 0">
					批量通过
				</el-button>
				<el-button type="warning" icon="el-icon-video-play" @click="batchEnable" :disabled="selection.length === 0">
					批量启用
				</el-button>
				<el-button type="danger" icon="el-icon-close" @click="batchReject" :disabled="selection.length === 0">
					批量拒绝
				</el-button>
			</div>

			<!-- 数据表格 -->
			<el-table
				:data="list"
				@selection-change="handleSelectionChange"
				@sort-change="$sortChange"
				style="width: 100%"
				v-loading="loading"
				row-key="id"
				ref="tableRef">
				<el-table-column type="selection" width="55">
				</el-table-column>

				<!-- 动态列 -->
				<el-table-column prop="employee_work_number" label="工号" v-if="showColumn('work_number')">
				</el-table-column>
				<el-table-column prop="employee_name" label="姓名" v-if="showColumn('name')">
				</el-table-column>
				<el-table-column prop="owners_name" label="姓名" v-if="showColumn('owners_name')">
				</el-table-column>
				<el-table-column prop="gender" label="性别" v-if="showColumn('gender')">
				</el-table-column>
				<el-table-column prop="contact_number" label="联系电话" v-if="showColumn('contact')">
				</el-table-column>
				<el-table-column prop="store_name" label="所属店面" v-if="showColumn('store')">
				</el-table-column>
				<el-table-column prop="department" label="部门" v-if="showColumn('department')">
				</el-table-column>
				<el-table-column prop="member_level" label="会员等级" v-if="showColumn('member_level')">
				</el-table-column>
				<el-table-column prop="examine_state" label="状态" width="100">
					<template slot-scope="scope">
						<el-tag :type="getStateType(scope.row.examine_state)">
							{{ scope.row.examine_state }}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column sortable prop="create_time" label="创建时间" width="180">
					<template slot-scope="scope">
						{{ $toTime(scope.row.create_time, 'yyyy-MM-dd hh:mm:ss') }}
					</template>
				</el-table-column>

				<!-- 操作列 -->
				<el-table-column label="操作" width="200" fixed="right">
					<template slot-scope="scope">
						<el-button size="mini" type="success" @click="singleApproveAndEnable(scope.row)" v-if="scope.row.examine_state === '待审核'">
							通过并启用
						</el-button>
						<el-button size="mini" type="primary" @click="singleApprove(scope.row)" v-if="scope.row.examine_state === '待审核'">
							通过
						</el-button>
						<el-button size="mini" type="warning" @click="singleEnable(scope.row)" v-if="scope.row.examine_state === '已通过'">
							启用
						</el-button>
						<el-button size="mini" type="danger" @click="singleReject(scope.row)" v-if="scope.row.examine_state === '待审核'">
							拒绝
						</el-button>
					</template>
				</el-table-column>
			</el-table>

			<!-- 分页 -->
			<div class="mt text_center">
				<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="query.page"
					:page-sizes="[10, 20, 50, 100]"
					:page-size="query.size"
					layout="total, sizes, prev, pager, next, jumper"
					:total="count">
				</el-pagination>
			</div>
		</el-card>
	</el-main>
</template>

<script>
export default {
	data() {
		return {
			loading: false,
			list: [],
			count: 0,
			selection: [],
			query: {
				page: 1,
				size: 10,
				role_type: 'store_admin',
				examine_state: '待审核'
			},
			// 列配置
			columnConfig: {
				'store_admin': {
					id: 'info_user_id',
					url: '~/api/info_user/get_list',
					columns: ['work_number', 'name', 'gender', 'contact', 'store', 'state'],
					fields: {
						idField: 'info_user_id',
						workNumberField: 'employee_work_number',
						nameField: 'employee_name',
						genderField: 'employee_gender',
						contactField: 'contact_number'
					}
				},
				'headquarter_info': {
					id: 'info_user_id',
					url: '~/api/info_user/get_list',
					columns: ['work_number', 'name', 'gender', 'contact', 'state'],
					fields: {
						idField: 'info_user_id',
						workNumberField: 'employee_work_number',
						nameField: 'employee_name',
						genderField: 'employee_gender',
						contactField: 'contact_number'
					}
				},
				'consultant': {
					id: 'consultant_id',
					url: '~/api/business_consultant/get_list',
					columns: ['work_number', 'name', 'gender', 'contact', 'department', 'store', 'state'],
					fields: {
						idField: 'consultant_id',
						workNumberField: 'employee_work_number',
						nameField: 'employee_name',
						genderField: 'consultant_gender',
						contactField: 'contact_number'
					}
				},
				'financial': {
					id: 'financial_user_id',
					url: '~/api/financial_user/get_list',
					columns: ['work_number', 'name', 'gender', 'contact', 'store', 'state'],
					fields: {
						idField: 'financial_user_id',
						workNumberField: 'employee_work_number',
						nameField: 'employee_name',
						genderField: 'financial_gender',
						contactField: 'contact_number'
					}
				},
				'member': {
					id: 'owner_user_id',
					url: '~/api/owner_user/get_list',
					columns: ['owners_name', 'contact', 'member_level', 'store', 'state'],
					fields: {
						idField: 'owner_user_id'
					}
				}
			}
		}
	},
	created() {
		this.getList();
	},
	methods: {
		getList() {
			this.loading = true;
			const config = this.columnConfig[this.query.role_type];
			if (!config) {
				this.loading = false;
				return;
			}

			let params = {
				page: this.query.page,
				size: this.query.size,
				examine_state: this.query.examine_state
			};

			this.$get(config.url, params, (json) => {
				this.loading = false;
				if (json.result) {
					this.list = json.result.list || [];
					this.count = json.result.count || 0;
				} else if (json.error) {
					this.$message.error(json.error.message || '获取数据失败');
				}
			});
		},
		showColumn(type) {
			const config = this.columnConfig[this.query.role_type];
			if (!config) return false;
			return config.columns.includes(type);
		},
		getStateType(state) {
			const typeMap = {
				'待审核': 'warning',
				'已通过': 'primary',
				'已拒绝': 'danger',
				'已启用': 'success'
			};
			return typeMap[state] || 'info';
		},
		handleSelectionChange(selection) {
			this.selection = selection;
		},
		handleSizeChange(size) {
			this.query.size = size;
			this.getList();
		},
		handleCurrentChange(page) {
			this.query.page = page;
			this.getList();
		},
		onRoleTypeChange() {
			this.query.page = 1;
			this.getList();
		},
		resetQuery() {
			this.query = {
				page: 1,
				size: 10,
				role_type: this.query.role_type,
				examine_state: '待审核'
			};
			this.getList();
		},
		getSelectedIds() {
			const config = this.columnConfig[this.query.role_type];
			if (!config) return [];
			return this.selection.map(item => item[config.id]);
		},
		async batchApproveAndEnable() {
			const ids = this.getSelectedIds();
			if (ids.length === 0) {
				this.$message.warning('请选择要操作的记录');
				return;
			}

			try {
				await this.$post('~/api/review/batch_approve_and_enable', {
					role_type: this.query.role_type,
					ids: ids
				}, (json) => {
					if (json.result) {
						this.$message.success('操作成功');
						this.getList();
					} else if (json.error) {
						this.$message.error(json.error.message || '操作失败');
					}
				});
			} catch (e) {
				this.$message.error('操作失败');
			}
		},
		async batchApprove() {
			const ids = this.getSelectedIds();
			if (ids.length === 0) {
				this.$message.warning('请选择要操作的记录');
				return;
			}

			try {
				await this.$post('~/api/review/batch_approve', {
					role_type: this.query.role_type,
					ids: ids,
					action: 'approve'
				}, (json) => {
					if (json.result) {
						this.$message.success('操作成功');
						this.getList();
					} else if (json.error) {
						this.$message.error(json.error.message || '操作失败');
					}
				});
			} catch (e) {
				this.$message.error('操作失败');
			}
		},
		async batchEnable() {
			const ids = this.getSelectedIds();
			if (ids.length === 0) {
				this.$message.warning('请选择要操作的记录');
				return;
			}

			try {
				await this.$post('~/api/review/batch_enable', {
					role_type: this.query.role_type,
					ids: ids
				}, (json) => {
					if (json.result) {
						this.$message.success('操作成功');
						this.getList();
					} else if (json.error) {
						this.$message.error(json.error.message || '操作失败');
					}
				});
			} catch (e) {
				this.$message.error('操作失败');
			}
		},
		async batchReject() {
			const ids = this.getSelectedIds();
			if (ids.length === 0) {
				this.$message.warning('请选择要操作的记录');
				return;
			}

			try {
				await this.$post('~/api/review/batch_approve', {
					role_type: this.query.role_type,
					ids: ids,
					action: 'reject'
				}, (json) => {
					if (json.result) {
						this.$message.success('操作成功');
						this.getList();
					} else if (json.error) {
						this.$message.error(json.error.message || '操作失败');
					}
				});
			} catch (e) {
				this.$message.error('操作失败');
			}
		},
		singleApproveAndEnable(row) {
			this.selection = [row];
			this.batchApproveAndEnable();
		},
		singleApprove(row) {
			this.selection = [row];
			this.batchApprove();
		},
		singleEnable(row) {
			this.selection = [row];
			this.batchEnable();
		},
		singleReject(row) {
			this.selection = [row];
			this.batchReject();
		}
	}
}
</script>

<style scoped>
.batch_review_page {
	padding: 20px;
}

.form_inline {
	margin-bottom: 20px;
}

.action_bar {
	margin-bottom: 20px;
}

.action_bar .el-button {
	margin-right: 10px;
}

.mt {
	margin-top: 20px;
}

.text_center {
	text-align: center;
}
</style>
