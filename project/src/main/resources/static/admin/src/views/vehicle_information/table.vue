<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



										<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="车牌号码">
									<el-input v-model="query.license_plate_number"></el-input>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="汽车类型">
									<el-cascader v-model="query.car_type" :options="list_car_type" :props="car_type_cascader"
							:show-all-levels="false" clearable collapse-tags placeholder="请选择汽车类型"
							@change="handle_car_type_change"></el-cascader>
								</el-form-item>
				</el-col>
													<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="审核状态">
						<el-select v-model="query.examine_state">
							<el-option value="">全部</el-option>
							<el-option value="未审核">未审核</el-option>
								<el-option value="已通过">已通过</el-option>
							<el-option value="未通过">未通过</el-option>
						</el-select>
					</el-form-item>
				</el-col>
	</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

										<el-button v-if="$check_option('/vehicle_information/table', 'examine')" @click="batchInfo()" class="examine_btn_state">批量审核</el-button>
														<el-button v-if="$check_action('/vehicle_information/table','del') || $check_action('/vehicle_information/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                    </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="vehicle_information_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="owner_user" @sort-change="$sortChange" label="车主用户" 				v-if="$check_field('get','owner_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_owner_user(scope.row['owner_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="license_plate_number" @sort-change="$sortChange" label="车牌号码" 				v-if="$check_field('get','license_plate_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["license_plate_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_type" @sort-change="$sortChange" label="汽车类型" 				v-if="$check_field('get','car_type')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_type"] }}
									</template>
					</el-table-column>
					<el-table-column prop="frame_number" @sort-change="$sortChange" label="车架号" 				v-if="$check_field('get','frame_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["frame_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="vehicle_remarks" @sort-change="$sortChange" label="车辆备注" 				v-if="$check_field('get','vehicle_remarks')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["vehicle_remarks"] }}
									</template>
					</el-table-column>
				<el-table-column label="审核状态" prop="examine_state" min-width="100">
				<template slot-scope="scope">
					<span v-if="scope.row['examine_state'] == '未审核'" style="color: red;">未审核</span>
					<span v-else-if="scope.row['examine_state'] == '审核中'" style="color: orange;">审核中</span>
					<span v-else-if="scope.row['examine_state'] == '已通过'" style="color: green;">已通过</span>
					<span v-else-if="scope.row['examine_state'] == '未通过'" style="color: gray;">未通过</span>
				</template>
			</el-table-column>
			<el-table-column label="审核回复" prop="examine_reply" min-width="200">
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/vehicle_information/table','set') || $check_action('/vehicle_information/view','set') || $check_action('/vehicle_information/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/vehicle_information/table','set') || $check_action('/vehicle_information/view','set') || $check_action('/vehicle_information/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
					<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="changeSigning(scope.row, scope.$index)" v-if="$check_option('/vehicle_information/table', 'examine') && (scope.row['examine_state'] == '未审核' || scope.row['examine_state'] == '审核中') ">
						<span>审核</span>
					</el-button>
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
		<el-dialog title="审核" :visible.sync="dialogVisible" width="30%" :show-close="true">
			<el-form ref="verifyForm" :rules="rules" :model="verifyItem">
				<el-form-item label="审核状态" prop="radio">
					<el-radio-group v-model="verifyItem.examine_state">
						<el-radio label="已通过" value="已通过"></el-radio>
						<el-radio label="未通过" value="未通过"></el-radio>
					</el-radio-group>
				</el-form-item>
								<el-form-item label="审核回复" prop="reply">
					<el-input type="textarea" placeholder="请填写审核回复" v-model="verifyItem.examine_reply"></el-input>
				</el-form-item>
							</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="assureVerify">确 定</el-button>
			</span>
		</el-dialog>
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
																car_type_cascader: {
					value: 'model_name',
					label: 'model_name',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
									// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/vehicle_information/get_list",
				url_del: "~/api/vehicle_information/del?",

				// 字段ID
				field: "vehicle_information_id",
				dialogVisible: false,
				verifyIdx: 0,
				verifyItem: {},
				rules: {
				  "examine_state": [
				    { required: true, message: '请选择审核状态', trigger: 'change' },
				  ],
				},
																											// 查询
				query: {
					"size":  7,
					"page": 1,
									"license_plate_number": "",
											"car_type": "",
										"examine_state":"",
					"login_time": "",
					"create_time": "",
					"orderby": `CASE WHEN examine_state = "未审核" THEN 1 ELSE 2 END,create_time desc`
				},

				// 数据
				list: [],
				batchAllState: false,
				batchAllList: [],
													// 用户列表
				list_user_owner_user: [],
								// 汽车类型列表
				list_car_type: [""],
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
			// 审核弹窗
			changeSigning(query, index) {
			  let beforeQuery=JSON.parse(JSON.stringify(query));
			  this.verifyIdx = index;
			  this.verifyItem = beforeQuery;
			  this.batchAllState = false;
			  this.batchAllList = [];
			  this.dialogVisible = true;
			},
			batchAll(list) {
				this.batchAllState = true;
				this.batchAllList = list.filter(item => item.examine_state === "未审核");
				this.dialogVisible = true;
			},
			assureVerify() {
				//审核
				let _this = this;
				let examineForm = this.$refs["verifyForm"];
				examineForm.validate(async (valid) => {
					if (valid) {
						let apiState = true;
						if (this.batchAllState) {
							for (var i = 0; i < this.batchAllList.length; i++) {
								let url = "~/api/vehicle_information/set?vehicle_information_id=" +  this.batchAllList[i]["vehicle_information_id"];
									let json = await _this.$post(url,{
									'examine_state': _this.verifyItem.examine_state,
										'examine_reply': _this.verifyItem.examine_reply,
										  }
								)
									if (json.error) {
									apiState = false;
									_this.$toast(json.error.message, "danger");
									break;
								}
							}
							if (apiState) {
							  _this.$toast("审核成功！", "success");
							  _this.dialogVisible = false;
							  _this.get_list();
							}
						}else{
							var url = "~/api/vehicle_information/set?vehicle_information_id=" + this.verifyItem['vehicle_information_id']
								_this.$post(url, {
								'examine_state': _this.verifyItem.examine_state,
									'examine_reply': _this.verifyItem.examine_reply,
									}, (json, status) => {
								if (json.result) {
										_this.$toast('审核成功！', 'success');
									_this.dialogVisible = false;
																	let message_inform = {
										title: '审核结果',
										type: '通知',
										content: '你在车辆信息下提交的内容，审核结果为：' + _this.verifyItem.examine_state,
										state: 1,
										user_id: _this.verifyItem.create_by
									}
									this.$post("~/api/message_inform/add",message_inform)
																	_this.get_list();
								} else if (json.error) {
									_this.$toast(json.error.message, 'danger');
								}
							})
						}

					}
				})
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
										if(user_group=="车主用户"){
						sqlwhere+= "owner_user = " + this.user.user_id + " or ";
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
			 * 获取汽车类型列表
			 */
			async get_list_car_type() {
				let param = {}
			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/vehicle_class_nameification/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  			  		const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.list_car_type = this.build_car_type_tree(json.result.list, 0);
					} else {
						this.list_car_type = json.result.list;
					}
			  				}else if (json.error){
					console.log(json.error);
				}
			},
					build_car_type_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_car_type_tree(list, item.vehicle_class_nameification_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			handle_car_type_change(value) {
				if (value) {
					this.query.car_type = value;
				} else {
					this.query.car_type = '';
				}
			},
								



				/**
			 * 获取车主用户用户列表
			 */
			async get_list_user_owner_user() {
				var json = await this.$get("~/api/user/get_list?user_group=车主用户");
				if(json.result && json.result.list){
					this.list_user_owner_user = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			get_user_owner_user(id){
				var obj = this.list_user_owner_user.getObj({"user_id":id});
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
																																																																																																																																																																																																																																																																																										res = await this.$get("~/api/issuing_card_voucher/get_list?", {	
					license_plate_number: type.license_plate_number
				});
				if (res.result && res.result.list && res.result.list.length > 0) {
					this.$message({
						type: 'error',
						message: '"' + type.license_plate_number + '"下有关联的内容，无法删除！'
					});
					return;
				}
																																																																																																																																																															res = await this.$get("~/api/reservation_record/get_list?", {	
					license_plate_number: type.license_plate_number
				});
				if (res.result && res.result.list && res.result.list.length > 0) {
					this.$message({
						type: 'error',
						message: '"' + type.license_plate_number + '"下有关联的内容，无法删除！'
					});
					return;
				}
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
							this.get_list_user_owner_user();
							// 初始化汽车类型列表
			this.get_list_car_type();
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
