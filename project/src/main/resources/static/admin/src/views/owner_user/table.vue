<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



							<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','owners_name')">
					<el-form-item label="车主姓名">
									<el-input v-model="query.owners_name"></el-input>
								</el-form-item>
				</el-col>
												<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','contact_number')">
					<el-form-item label="联系号码">
									<el-input v-model="query.contact_number"></el-input>
								</el-form-item>
				</el-col>
													</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						
						<el-button v-if="$check_action('/owner_user/table','add') || $check_action('/owner_user/view','add')" @click="$router.push('./view?')">添加</el-button>

						

														<el-button v-if="$check_action('/owner_user/table','del') || $check_action('/owner_user/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
										<download-excel v-if="$check_option('/owner_user/table','import_db')" class="export-excel-wrapper" :data="DetailsForm" :fields="json_fields" name="数据导入表格.xls" >
							<el-button type="success">下载导入文档</el-button>
						</download-excel>
						<el-upload v-if="$check_option('/owner_user/table','import_db')" action accept = ".xlsx, .xls" :auto-upload="false" :show-file-list="false" :on-change="handle_import">
							<el-button type="primary">导入</el-button>
						</el-upload>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                          </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="owner_user_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
			<el-table-column prop="user_id" label="用户ID" width="80">
			</el-table-column>
			<el-table-column prop="username" label="用户名" width="120">
			</el-table-column>
				<el-table-column fixed prop="nickname" label="昵称">
				</el-table-column>
					<el-table-column  prop="integral" label="积分"></el-table-column>
	
					<el-table-column prop="owners_name" @sort-change="$sortChange" label="车主姓名" 				v-if="$check_field('get','owners_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["owners_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="owners_gender" @sort-change="$sortChange" label="车主性别" 				v-if="$check_field('get','owners_gender')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["owners_gender"] }}
									</template>
					</el-table-column>
					<el-table-column prop="contact_number" @sort-change="$sortChange" label="联系号码" 				v-if="$check_field('get','contact_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["contact_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="driving_license" @sort-change="$sortChange" label="驾驶证件" 				v-if="$check_field('get','driving_license')" min-width="300">
						<template slot-scope="scope">
					<el-image style="width: 100px; height: 100px" :src="$fullUrl(scope.row['driving_license'])"
						:preview-src-list="[$fullUrl(scope.row['driving_license'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</template>
					</el-table-column>
					<el-table-column prop="member_level" @sort-change="$sortChange" label="会员等级" 				v-if="$check_field('get','member_level')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["member_level"] }}
									</template>
					</el-table-column>
					<el-table-column prop="accumulate_points" @sort-change="$sortChange" label="积攒积分(分)" 				v-if="$check_field('get','accumulate_points')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["accumulate_points"] }}
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/owner_user/table','set') || $check_action('/owner_user/view','set') || $check_action('/owner_user/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/owner_user/table','set') || $check_action('/owner_user/view','set') || $check_action('/owner_user/view','get')"
						:to="'./view?user_id=' + scope.row['user_id']"

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
	import * as XLSX from 'xlsx/xlsx.mjs'
  	      	        
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
				url_get_list: "~/api/owner_user/get_list",
				url_del: "~/api/owner_user/del?",

				// 字段ID
				field: "owner_user_id",
																																// 查询
				query: {
					"size":  7,
					"page": 1,
								"owners_name": "",
												"contact_number": "",
											"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 数据
				list: [],
				json_fields: {
					"账号":"username",
				"密码":"password",
				"昵称":"nickname",
				// "手机号码":"phone",
				"邮箱":"email",
							"车主姓名":'owners_name',
						"车主性别":'owners_gender',
						"联系号码":'contact_number',
						"驾驶证件":'driving_license',
						"会员等级":'member_level',
						"积攒积分":'accumulate_points',
					},
				DetailsForm: [
					{
							"username":"填写账号",
						"password":"填写密码",
						"nickname":"填写昵称",
						// "phone":"填写手机号码",
						"email":"填写邮箱",
								owners_name:"文本类型",
							owners_gender:"下拉类型",
							contact_number:"手机类型",
							driving_license:"图片类型",
							member_level:"下拉类型",
							accumulate_points:"数字类型",
						},
				],
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
			delInfo() {
			    var list = this.selection;
			    if (list.length === 0) {
			        this.$message({
			            type: 'info',
			            message: '选择对象不能为空!'
			        });
			        return;
			    }
			    this.$confirm('删除后数据将无法恢复，请确认是否删除？', '提示', {
			        confirmButtonText: '确定',
			        cancelButtonText: '取消',
			        type: 'warning'
			    }).then(async () => {
			        await this.delAll(list ,async (list)=>{
						var bl = true;
						for(var i=0; i < list.length; i++){
							var user_id = list[i].user_id;
							var res = await this.$get("~/api/user/del?",{user_id});
							if(res.result){
								console.log("删除成功" + i);
							}
							else{
								console.log("删除失败" + i);
								bl = false;
								break;
							}
						}
						if(bl){
							this.$message({
							    type: 'success',
							    message: '删除成功!'
							});
							this.get_list();
						}

					});
			    }).catch(() => {
			        this.$message({
			            type: 'info',
			            message: '已取消删除'
			        });
			    });
			},
			get_list_after(param){
				let _this = this;
				for (let i = 0;i<this.list.length;i++){
					let user_id = _this.list[i].user_id
					_this.$get("~/api/user/get_obj?", {
						user_id
					}, (json) => {
						if (json.result.obj){
							_this.$delete(_this.list[i],'username');
							_this.$set(_this.list[i],'username',json.result.obj.username);
							_this.$delete(_this.list[i],'nickname');
							_this.$set(_this.list[i],'nickname',json.result.obj.nickname);
								_this.$delete(_this.list[i],'integral');
							_this.$set(_this.list[i],'integral',json.result.obj.integral);
																												}
					});
				}
							},


												


	/**
	 * 导入
	 */
	readFile(file){//文件读取
		return new Promise(resolve => {
			let reader = new FileReader();
			reader.readAsBinaryString(file);//以二进制的方式读取
			reader.onload = ev =>{
				resolve(ev.target.result);
			}
		})
	},
	async handle_import(ev){
		let file = ev.raw;
		console.log(file)
		if(!file){
			console.log("文件打开失败")
			return ;
		}else{
			let data = await this.readFile(file);
			let workbook = XLSX.read(data,{ type: "binary"});//解析二进制格式数据
			let worksheet = workbook.Sheets[workbook.SheetNames[0]];//获取第一个Sheet
			let result = XLSX.utils.sheet_to_json(worksheet);//json数据格式
			result.forEach((item) => {
				// 将中文的键名替换成英文的
				for (let k in this.json_fields) {
					let newKey = this.json_fields[k];
					if (newKey) {
						item[newKey] = item[k];
						delete item[k];
					}
				}
			});
			let _this = this;
				for (let i=0;i<result.length;i++){
				let form_user = {};
				if (result[i].username){
					form_user.username = result[i].username;
				}
				delete result[i]["username"]
				if (result[i].password){
					form_user.password = result[i].password;
				}
				delete result[i]["password"]
				if (result[i].nickname){
					form_user.nickname = result[i].nickname;
				}
				delete result[i]["nickname"]
				// if (result[i].phone){
				// 	form_user.phone = result[i].phone;
				// }
				// delete result[i]["phone"]
				if (result[i].email){
					form_user.email = result[i].email;
				}
				delete result[i]["email"]
				form_user.user_group = "车主用户";
				await this.$post("~/api/user/add?", form_user, function (json, status) {
					if (json.result) {
						delete form_user["password"]
						_this.$get("~/api/user/get_obj?",form_user,(res)=>{
							if(res.result && res.result.obj){
								result[i].user_id = res.result.obj.user_id;
								_this.$post("~/api/owner_user/add?", result[i], function (json, status) {
									if (json.result) {
										_this.$toast("操作成功", 'success');
									} else if (json.error) {
										_this.$toast(json.error.message, 'danger');
									}
								});
							}else if(res.error){
								this.$toast(res.error.message,"error");
							}
						})
					} else if (json.error) {
						_this.$toast(json.error.message, 'danger');
					}
				});
			}
			}
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
