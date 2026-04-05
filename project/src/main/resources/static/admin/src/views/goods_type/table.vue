<template>
	<el-main class="bg table_wrap goods_type">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="分类名称">
						<el-input v-model="query.name"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1 btns">

						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" class="search_btn_reset">重置</el-button>
						<el-button v-if="user_group == '管理员'" class="float-right search_btn_del" type="danger"
							@click="delInfo()">删除</el-button>
						<el-button v-if="user_group == '管理员'" class="float-right  search_btn_add"
							@click="$router.push('./view?')">添加</el-button>


					</el-col>

				</el-col>
			</el-row>
		</el-form>

		<el-table ref="tableRef" border :data="list" @selection-change="selectionChange" style="width: 100%"
			row-key="type_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>

			<el-table-column fixed label="分类名称" prop="name">
				<template slot-scope="scope">
					<span>{{ scope.row.name }}</span>
				</template>
			</el-table-column>

			<el-table-column label="图标" prop="icon">
				<template slot-scope="scope">
					<el-image style="width: 60px; height: 60px" :src="$fullUrl(scope.row.icon)"
						:preview-src-list="[$fullUrl(scope.row.icon)]">
						<div slot="error" class="image-slot">
							<img src="/img/error.png" style="width: 60px; height: 60px" />
						</div>
					</el-image>
				</template>
			</el-table-column>

			<!-- <el-table-column label="描述" prop="desc" min-width="80">
			</el-table-column> -->

			<el-table-column sortable prop="create_time" label="创建时间">
				<template slot-scope="scope">
					{{ $toTime(scope.row["create_time"], "yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间">
				<template slot-scope="scope">
					{{ $toTime(scope.row["update_time"], "yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>

			<!-- 操作 -->
			<el-table-column fixed="right" label="操作" width="120">
				<template slot-scope="scope">
					<div class="view_a">
						<router-link class="e-button el-button--small is-plain el-button--primary"
							:to="'./view?' + field + '=' + scope.row[field]" size="small"><span>详情</span></router-link>
					</div>
				</template>
			</el-table-column>

		</el-table>
		<el-button class="expand-all" @click="toggleExpand" size="small">
			{{ isExpandAll ? '收起全部' : '展开全部' }}
		</el-button>
		<!-- 分页器 -->
		<!-- <div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div> -->

	</el-main>
</template>

<script>
import mixin from "@/mixins/page.js";

export default {
	mixins: [mixin],
	data() {
		return {

			// 表
			table: "goods_type",

			// 获取数据地址
			url_get_list: "~/api/goods_type/get_list",
			url_del: "~/api/goods_type/del?",

			// 字段ID
			field: "type_id",

			// 查询
			query: {
				size: 100,
				page: 1,
				name: ""
			},

			// 数据
			list: [],

			// 状态
			list_state: [
				{ "name": "使用中", "value": 1 },
				{ "name": "已失效", "value": 0 }
			],
			// 控制展开状态
			isExpandAll: true,
			// 存储展开的行
			expandKeys: []
		}
	},
	methods: {
		async delInfo() {
			var list = this.selection;
			if (list.length === 0) {
				this.$message({
					type: 'info',
					message: '选择对象不能为空!'
				});
				return;
			}
			// 检查是否有内容关联
			for (let i = 0; i < list.length; i++) {
				let type = list[i];
				let res = await this.$get("~/api/goods/get_list?", {
					type: type.name
				});

				if (res.result && res.result.list && res.result.list.length > 0) {
					this.$message({
						type: 'error',
						message: `类型"${type.name}"下有关联的内容，无法删除！`
					});
					return;
				}
			}

			this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				await this.delAll(list);
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		async get_list_after(json) {
			if (json && json.result && json.result.list) {
				if (this.query.name) {
					this.list = json.result.list;
				} else {
					// 没有查询条件，显示树形结构
					this.list = this.buildTreeTable(json.result.list, 0);
				}
			}
		},
		/**
		 * 构建表格树形数据
		 * @param {Array} list 分类列表
		 * @param {Number} fatherId 父级ID
		 * @returns {Array} 树形结构
		 */
		buildTreeTable(list, fatherId) {
			const tree = [];
			for (let item of list) {
				if (item.father_id === fatherId) {
					const children = this.buildTreeTable(list, item.type_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
				}
			}
			return tree;
		},
		/**
		 * 切换展开收起状态
		 */
		toggleExpand() {
			this.isExpandAll = !this.isExpandAll;
			this.$nextTick(() => {
				this.toggleTableExpand(this.list, this.isExpandAll);
			});
		},

		/**
		 * 递归展开/收起所有行
		 */
		toggleTableExpand(data, expand) {
			if (data && data.length > 0 && this.$refs.tableRef) {
				data.forEach(item => {
					this.$refs.tableRef.toggleRowExpansion(item, expand);
					if (item.children && item.children.length > 0) {
						this.toggleTableExpand(item.children, expand);
					}
				});
			}
		},
	},
	created() {
	}
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

.float-right {
	float: right;
}

.mr-1 {
	margin-right: 1rem;
}

.el-table .table_class {
	background: rgba(150, 150, 150, 0.1);
	text-align: center;
}

.text_center {
	text-align: center;
}

.float-right {
	float: right;
}

.expand-all {
	margin-top: 10px;
}
</style>
