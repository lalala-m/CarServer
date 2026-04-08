<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



							<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','project_bundle')">
					<el-form-item label="项目套餐">
									<el-input v-model="query.project_bundle"></el-input>
								</el-form-item>
				</el-col>
																						</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

														<el-button v-if="$check_action('/maintenance/table','del') || $check_action('/maintenance/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                                </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="maintenance_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="project_bundle" @sort-change="$sortChange" label="项目套餐" 				v-if="$check_field('get','project_bundle')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["project_bundle"] }}
									</template>
					</el-table-column>
					<el-table-column prop="project_picture" @sort-change="$sortChange" label="项目图片" 				v-if="$check_field('get','project_picture')" min-width="300">
						<template slot-scope="scope">
					<el-image style="width: 100px; height: 100px" :src="$fullUrl(scope.row['project_picture'])"
						:preview-src-list="[$fullUrl(scope.row['project_picture'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</template>
					</el-table-column>
					<el-table-column prop="project_price" @sort-change="$sortChange" label="项目价格(元)" 				v-if="$check_field('get','project_price')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["project_price"] }}
									</template>
					</el-table-column>
					<el-table-column prop="manager_user" @sort-change="$sortChange" label="经理用户" 				v-if="$check_field('get','manager_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_manager_user(scope.row['manager_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="financial_user" @sort-change="$sortChange" label="财务用户" 				v-if="$check_field('get','financial_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_financial_user(scope.row['financial_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="project_description" @sort-change="$sortChange" label="项目说明" 				v-if="$check_field('get','project_description')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["project_description"] }}
									</template>
					</el-table-column>
					<el-table-column prop="project_content" @sort-change="$sortChange" label="项目内容" 				v-if="$check_field('get','project_content')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["project_content"] }}
									</template>
					</el-table-column>
	
			<el-table-column prop="extra" @sort-change="$sortChange" label="信息" min-width="300" v-if="hasExtraData" >
				<template slot-scope="scope">
					<div v-for="(value, key) in JSON.parse(scope.row.extra || '{}')" :key="key">{{ key }}：{{ value }}</div>
				</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
            </el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
			</el-table-column>







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/maintenance/table','set') || $check_action('/maintenance/view','set') || $check_action('/maintenance/view','get')
						|| $check_action('/maintenance_order/table','add') || $check_action('/maintenance_order/view','add')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/maintenance/table','set') || $check_action('/maintenance/view','set') || $check_action('/maintenance/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
						<!--跨表按钮-->
										<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="to_table(scope.row,'/maintenance_order/view')" v-if="($check_action('/maintenance_order/table','add') || $check_action('/maintenance_order/view','add')) && !scope.row['maintenance_order_limit'] && !scope.row['maintenance_order_status_limit']">
						<span>购买</span>
					</el-button>
												<router-link v-if="$check_comment('/maintenance/table')" class="e-button el-button--small is-plain el-button--primary"
								  :to="'../comment/table?size=10&page=1&source_table=maintenance&source_field=' + field + '&source_id=' + scope.row[field]" size="small">
						<span>查看评论</span>
					</router-link>
					</div>
				</template>
			</el-table-column>

		</el-table>
									<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->
									<el-dialog title="重要提醒" :visible.sync="showModal" width="400px" :before-close="closeModal">
			<div style="text-align: center">
				<p style="margin-bottom: 16px; font-size: 16px; color: #333;">
				当前有数据达到预警值！
				</p>
				<p style="color: #666;">{{ message }}</p>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="closeModal">取消</el-button>
				<el-button type="primary" @click="closeModal">确定</el-button>
			</div>
		</el-dialog>


	</el-main>
</template>
              <script>
	import mixin from "@/mixins/page.js";
  	              
	export default {
		mixins: [mixin],
		components: {
        },
		data() {
			return {
				statisticsData: {
      				total: 0,
              				},
																// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/maintenance/get_list",
				url_del: "~/api/maintenance/del?",

				// 字段ID
				field: "maintenance_id",
																																// 查询
				query: {
					"size":  7,
					"page": 1,
								"project_bundle": "",
														"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 数据
				list: [],
																					// 用户列表
				list_user_manager_user: [],
									// 用户列表
				list_user_financial_user: [],
									message: '',
				// 控制展开状态
				isExpandAll: true,
				// 存储展开的行
				expandKeys: [],
				prevSelection: [],
			}
		},
		methods: {
				previewFile(fileName) {
					if(!fileName){
						return;
					}
					const fileExtension = fileName.split('.').pop().toLowerCase();
					if(fileExtension == 'docx' || fileExtension == 'xlsx' || fileExtension == 'pptx') {
						this.$router.push('/filePreview?url=' + fileName + '&type=' + fileExtension);
						return;
					}
					window.open(this.$fullUrl(fileName), '_blank');
				},
				showPreview(file) {
					if(!file){
						return false;
					}
					let fileType = file.split('.').pop().toLowerCase();
					if(fileType == 'docx' || fileType == 'xlsx' || fileType == 'pptx') {
						return true;
					}else {
						return false;
					}
				},
													// 关闭弹框
			closeModal(){
				this.showModal = false;
				},
			/**
			 * @description 获取到列表事件
			 * @param {Object} res 响应结果
			 */
			get_list_after: function get_list_after(res, func, url) {
				let _this = this
									
				
												_this.list.map((item) => {
					let param = {
						source_table: "maintenance",
						source_id: item.maintenance_id,
						source_user_id: _this.user.user_id
					};
					if(item.maintenance_order_limit_times > 0){
						_this.$get("~/api/maintenance_order/count?",param,(result)=>{
							if(result){
								if(result.result >= item.maintenance_order_limit_times){
									_this.$set(item,'maintenance_order_limit',true);
								}else{
									_this.$set(item,'maintenance_order_limit',false);
								}
							}
						})
					}else{
						_this.$set(item,'maintenance_order_limit',false);
					}
					Object.assign(item, param)
				})
																																																			},

			get_list_before(param){
				var user_group = this.user.user_group;
				if(user_group != "管理员"){
						let sqlwhere = "(";
																									if(user_group=="经理用户"){
						sqlwhere+= "manager_user = " + this.user.user_id + " or ";
					}
													if(user_group=="财务用户"){
						sqlwhere+= "financial_user = " + this.user.user_id + " or ";
					}
																				if (sqlwhere.length>1){
						sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
						sqlwhere += ")";
						param["sqlwhere"] = sqlwhere;
					}
					}
				return param;
			},

														



										/**
			 * 获取经理用户用户列表
			 */
			async get_list_user_manager_user() {
				var json = await this.$get("~/api/user/get_list?user_group=经理用户");
				if(json.result && json.result.list){
					this.list_user_manager_user = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			get_user_manager_user(id){
				var obj = this.list_user_manager_user.getObj({"user_id":id});
				var ret = "";
				if(obj){
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
						/**
			 * 获取财务用户用户列表
			 */
			async get_list_user_financial_user() {
				var json = await this.$get("~/api/user/get_list?user_group=财务用户");
				if(json.result && json.result.list){
					this.list_user_financial_user = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			get_user_financial_user(id){
				var obj = this.list_user_financial_user.getObj({"user_id":id});
				var ret = "";
				if(obj){
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
									async delInfo() {
				var list = this.selection;
				if (list.length === 0) {
					this.$message({
						type: 'info',
						message: '选择对象不能为空!'
					});
					return;
				}
				for (let i = 0; i < list.length; i++) {
					let type = list[i];
					let res
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		}
				this.$confirm('删除后数据将无法恢复，请确认是否删除？', '提示', {
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
	},
		created() {
													this.get_list_user_manager_user();
								this.get_list_user_financial_user();
							},
		computed: {
			hasExtraData() {
				return this.list.some(item => item.extra && item.extra.trim() !== '');
			}
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

	.text_center {
		text-align: center;
	}

	.float-right {
		float: right;
	}


	.modal_wrap{
		width: 100vw;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
		background: rgba(0,0,0,0.5);
		z-index: 9999999999;
	}
	.el-date-editor .el-range-separator{
		width: 10% !important;
	}
	.num_range {
	  display: flex;
	  align-items: center;
	}

	.num_range_span {
	  font-size: 28px;
	}
	td > .cell > a, td > .cell > span, td > .cell {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 3;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.expand-all {
		margin-top: 10px;
	}
	.el-table__indent, .el-table__placeholder {
		display: inline-block !important;
	}
.route-map-dialog {
  display: flex;
  height: 500px;
}

.route-map-left {
  flex: 1;
  position: relative;
  height: 100%;
  background: #f5f5f5;
}

.route-map-container {
  width: 100%;
  height: 100%;
}

.route-map-loading {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
  color: #409eff;
  font-size: 14px;
}

.route-map-right {
  width: 320px;
  padding-left: 20px;
  overflow-y: auto;
  border-left: 1px solid #eee;
}

.route-map-info-row {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.route-map-info-label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.route-map-info-value {
  color: #666;
  line-height: 1.5;
  font-size: 14px;
}
</style>
