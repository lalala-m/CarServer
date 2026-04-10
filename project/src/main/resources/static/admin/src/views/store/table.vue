<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="店面名称">
						<el-input v-model="query.store_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="负责人">
						<el-input v-model="query.store_manager"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">
					<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">
						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
						<el-button v-if="$check_action('/store/table','add') || $check_action('/store/view','add')" @click="$router.push('./view?')">添加</el-button>
						<el-button v-if="$check_action('/store/table','del') || $check_action('/store/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="store_id" :default-expand-all="isExpandAll" ref="tableRef">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55"></el-table-column>
			<el-table-column prop="store_id" label="店面ID" width="80"></el-table-column>
			<el-table-column prop="store_name" @sort-change="$sortChange" label="店面名称" min-width="200">
				<template slot-scope="scope">{{ scope.row["store_name"] }}</template>
			</el-table-column>
			<el-table-column prop="store_address" @sort-change="$sortChange" label="店面地址" min-width="300">
				<template slot-scope="scope">{{ scope.row["store_address"] }}</template>
			</el-table-column>
			<el-table-column prop="store_phone" @sort-change="$sortChange" label="联系电话" min-width="150">
				<template slot-scope="scope">{{ scope.row["store_phone"] }}</template>
			</el-table-column>
			<el-table-column prop="store_manager" @sort-change="$sortChange" label="负责人" min-width="150">
				<template slot-scope="scope">{{ scope.row["store_manager"] }}</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}</template>
            </el-table-column>
			<el-table-column fixed="right" label="操作" min-width="150" v-if="$check_action('/store/table','set') || $check_action('/store/view','set') || $check_action('/store/view','get')">
				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/store/table','set') || $check_action('/store/view','set') || $check_action('/store/view','get')"
						:to="'./view?store_id=' + scope.row['store_id']" size="small">
						<span>编辑</span>
					</router-link>
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
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";
	        
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				url_get_list: "~/api/store/get_list",
				url_del: "~/api/store/del?",
				field: "store_id",
				query: {
					"size": 10,
					"page": 1,
					"store_name": "",
					"store_manager": "",
					"orderby": `store_id desc`
				},
				list: [],
				isExpandAll: true,
				prevSelection: [],
			}
		},
		methods: {
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
			        for(var i = 0; i < list.length; i++) {
			            var store_id = list[i].store_id;
			            var res = await this.$get("~/api/store/del?", {store_id});
			            if(res.result) {
			                console.log("删除成功" + i);
			            } else {
			                this.$message({ type: 'error', message: '删除失败!' });
			                return;
			            }
			        }
			        this.$message({ type: 'success', message: '删除成功!' });
			        this.get_list();
			    }).catch(() => {
			        this.$message({ type: 'info', message: '已取消删除' });
			    });
			},
		},
		created() {},
	}
</script>

<style type="text/css">
	.bg { background: white; }
	.form.p_4 { padding: 1rem; }
	.form .el-input { width: initial; }
	.mt { margin-top: 1rem; }
	.text_center { text-align: center; }
	.view_a {
		display: flex;
		flex-wrap: wrap;
	}
</style>
