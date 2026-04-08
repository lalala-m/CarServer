<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



																			<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','quality_of_service')">
					<el-form-item label="服务质量">
									<el-select v-model="query.quality_of_service">
				                            <el-option v-for="o in list_quality_of_service" :key="o" :label="o"
                            	:value="o">
                            </el-option>
										</el-select>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','working_attitude')">
					<el-form-item label="工作态度">
									<el-select v-model="query.working_attitude">
				                            <el-option v-for="o in list_working_attitude" :key="o" :label="o"
                            	:value="o">
                            </el-option>
										</el-select>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','performance_level')">
					<el-form-item label="绩效等级">
									<el-select v-model="query.performance_level">
				                            <el-option v-for="o in list_performance_level" :key="o" :label="o"
                            	:value="o">
                            </el-option>
										</el-select>
								</el-form-item>
				</el-col>
										</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

														<el-button v-if="$check_action('/employee_performance/table','del') || $check_action('/employee_performance/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                                            </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="employee_performance_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="performance_title" @sort-change="$sortChange" label="绩效标题" 				v-if="$check_field('get','performance_title')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["performance_title"] }}
									</template>
					</el-table-column>
					<el-table-column prop="employee_name" @sort-change="$sortChange" label="员工姓名" 				v-if="$check_field('get','employee_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["employee_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="evaluation_time" @sort-change="$sortChange" label="评估时间" 				v-if="$check_field('get','evaluation_time')" min-width="300">
						<template slot-scope="scope">
									                	{{ $toTime(scope.row["evaluation_time"],"yyyy-MM-dd hh:mm:ss") }}
																		</template>
					</el-table-column>
					<el-table-column prop="service_singular" @sort-change="$sortChange" label="服务单数(单)" 				v-if="$check_field('get','service_singular')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["service_singular"] }}
									</template>
					</el-table-column>
					<el-table-column prop="quality_of_service" @sort-change="$sortChange" label="服务质量" 				v-if="$check_field('get','quality_of_service')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["quality_of_service"] }}
									</template>
					</el-table-column>
					<el-table-column prop="working_attitude" @sort-change="$sortChange" label="工作态度" 				v-if="$check_field('get','working_attitude')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["working_attitude"] }}
									</template>
					</el-table-column>
					<el-table-column prop="performance_level" @sort-change="$sortChange" label="绩效等级" 				v-if="$check_field('get','performance_level')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["performance_level"] }}
									</template>
					</el-table-column>
					<el-table-column prop="assessment_results" @sort-change="$sortChange" label="评估结果" 				v-if="$check_field('get','assessment_results')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["assessment_results"] }}
									</template>
					</el-table-column>
					<el-table-column prop="work_suggestion" @sort-change="$sortChange" label="工作建议" 				v-if="$check_field('get','work_suggestion')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["work_suggestion"] }}
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/employee_performance/table','set') || $check_action('/employee_performance/view','set') || $check_action('/employee_performance/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/employee_performance/table','set') || $check_action('/employee_performance/view','set') || $check_action('/employee_performance/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
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
				url_get_list: "~/api/employee_performance/get_list",
				url_del: "~/api/employee_performance/del?",

				// 字段ID
				field: "employee_performance_id",
																																// 查询
				query: {
					"size":  7,
					"page": 1,
												"quality_of_service": "",
											"working_attitude": "",
											"performance_level": "",
										"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 数据
				list: [],
																						// 服务质量列表
				list_quality_of_service: ['优','良','中','差'],
							// 工作态度列表
				list_working_attitude: ['优','良','中','差'],
							// 绩效等级列表
				list_performance_level: ['S','A','B','C','D'],
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
																																																						},

			get_list_before(param){
				var user_group = this.user.user_group;
				if(user_group != "管理员"){
						let sqlwhere = "(";
																																																				if (sqlwhere.length>1){
						sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
						sqlwhere += ")";
						param["sqlwhere"] = sqlwhere;
					}
					}
				return param;
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
