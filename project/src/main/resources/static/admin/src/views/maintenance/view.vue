<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','project_bundle') || $check_field('add','project_bundle') || $check_field('set','project_bundle')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item prop="project_bundle">
					<div slot="label">
						<i style="color: red;">*</i>
						项目套餐
					</div>
															<el-input id="project_bundle" v-model="form['project_bundle']" placeholder="请输入项目套餐"
							  v-if="(form['maintenance_id'] && $check_field('set','project_bundle')) || (!form['maintenance_id'] && $check_field('add','project_bundle'))" :disabled="disabledObj['project_bundle_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','project_bundle')">
						{{form['project_bundle']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','project_picture') || $check_field('add','project_picture') || $check_field('set','project_picture')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="项目图片" prop="project_picture">
											<el-upload :disabled="disabledObj['project_picture_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_project_picture"
						:show-file-list="false" v-if="(form['maintenance_id'] && $check_field('set','project_picture')) || (!form['maintenance_id'] && $check_field('add','project_picture'))">
						<img id="project_picture" v-if="form['project_picture']" :src="$fullUrl(form['project_picture'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','project_picture')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['project_picture'])" :preview-src-list="[$fullUrl(form['project_picture'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','project_price') || $check_field('add','project_price') || $check_field('set','project_price')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="项目价格" prop="project_price">
											<el-input-number id="project_price" v-model.number="form['project_price']"
						v-if="(form['maintenance_id'] && $check_field('set','project_price')) || (!form['maintenance_id'] && $check_field('add','project_price'))" :disabled="disabledObj['project_price_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','project_price')">{{form['project_price']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">元</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','manager_user') || $check_field('add','manager_user') || $check_field('set','manager_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="经理用户" prop="manager_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_manager_user(form['manager_user']) }}
							<el-select v-if="(form['maintenance_id'] && $check_field('set','manager_user')) || (!form['maintenance_id'] && $check_field('add','manager_user'))" id="manager_user" v-model="form['manager_user']" :disabled="disabledObj['manager_user_isDisabled']">
								<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','manager_user')" id="manager_user" v-model="form['manager_user']" :disabled="true">
								<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="manager_user" v-model="form['manager_user']" :disabled="disabledObj['manager_user_isDisabled']">
							<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','financial_user') || $check_field('add','financial_user') || $check_field('set','financial_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="财务用户" prop="financial_user">
																<el-select v-if="(form['maintenance_id'] && $check_field('set','financial_user')) || (!form['maintenance_id'] && $check_field('add','financial_user'))" id="financial_user" v-model="form['financial_user']" :disabled="disabledObj['financial_user_isDisabled']">
							<el-option v-for="o in list_user_financial_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','financial_user')" id="financial_user" v-model="form['financial_user']" :disabled="true">
							<el-option v-for="o in list_user_financial_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','project_description') || $check_field('add','project_description') || $check_field('set','project_description')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="项目说明" prop="project_description">
											<el-input type="textarea" id="project_description" v-model="form['project_description']" placeholder="请输入项目说明"
						v-if="(form['maintenance_id'] && $check_field('set','project_description')) || (!form['maintenance_id'] && $check_field('add','project_description'))" :disabled="disabledObj['project_description_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','project_description')">{{form['project_description']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','project_content') || $check_field('add','project_content') || $check_field('set','project_content')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="项目内容" prop="project_content">
											<el-input type="textarea" id="project_content" v-model="form['project_content']" placeholder="请输入项目内容"
						v-if="(form['maintenance_id'] && $check_field('set','project_content')) || (!form['maintenance_id'] && $check_field('add','project_content'))" :disabled="disabledObj['project_content_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','project_content')">{{form['project_content']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="购买限次">
				<el-input id="limit_times" v-model="form['maintenance_order_limit_times']" placeholder="购买限制次数，0为不限"
						  v-if="$check_option('/maintenance/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/maintenance/view','get')" v-html="form['maintenance_order_limit_times']"></div>
			  </el-form-item>
			</el-col>
			
	
	
		
		
		
		</el-row>
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/maintenance/view','set') || $check_action('/maintenance/view','add')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
				<el-form-item v-else>
					<el-button @click="cancel()">返回</el-button>
				</el-form-item>
			</el-col>

		</el-form>
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
				field: "maintenance_id",
				url_add: "~/api/maintenance/add?",
				url_set: "~/api/maintenance/set?",
				url_get_obj: "~/api/maintenance/get_obj?",
				url_get_list: "~/api/maintenance/get_list?",
				url_upload: "~/api/maintenance/upload?",

				query: {
					"maintenance_id": 0,
				},

				form: {
								"project_bundle": null, // 项目套餐
										"project_picture":  '', // 项目图片
										"project_price":  0, // 项目价格
										"manager_user": 0, // 经理用户
										"financial_user": 0, // 财务用户
										"project_description":  '', // 项目说明
										"project_content":  '', // 项目内容
												"maintenance_id": 0, // ID
													"maintenance_order_limit_times": 0, // 购买限制次数
															"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"project_bundle_isDisabled": false,
										"project_picture_isDisabled": false,
					          			"project_price_isDisabled": false,
										"manager_user_isDisabled": false,
										"financial_user_isDisabled": false,
										"project_description_isDisabled": false,
										"project_content_isDisabled": false,
										},

																												// 用户列表
				list_user_manager_user: [],
						// 用户组
				group_user_manager_user: "",
														// 用户列表
				list_user_financial_user: [],
																					

										}
		},
		computed: {
			aiForm() {
				let form = {
																																																																							};
				return form;
			},
											},
		methods: {
							
										openPreview(file) {
			    window.open(file.url)
			},
		
	
							/**
			 * 上传项目图片
			 * @param {Object} param 图片参数
			 */
			upload_project_picture(param){
									this.uploadFile(param.file, "project_picture");
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
					/**
			 * 获取经理用户用户组
			 */
			async get_group_user_manager_user() {
							this.form["manager_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=经理用户");
				if(json.result && json.result.obj){
					this.group_user_manager_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_manager_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_manager_user.source_table+"/get_obj?"
				this.$get(url, user_id, function(res) {
					if (res.result && res.result.obj) {
						var arr = []
						for (let key in res.result.obj) {
							arr.push(key)
						}
						var arrForm = []
									for (let key in _this.form) {
							arrForm.push(key)
						}
												_this.form["manager_user"] = id
									_this.disabledObj['manager_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "manager_user" && arr[i] !== "create_by") {
			                      _this.form[arrForm[j]] = res.result.obj[arr[i]]
			                      _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								  break;
								} else {
								  _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								}
							  }
							}
						  }
						}
					}
				});
			},
					get_user_manager_user(id){
				var obj = this.list_user_manager_user.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
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
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},
					
	
					
	
					// 自动计算精度处理
		toFixed: function(num){
			if (!isNaN(num)) {
				return ((num + '').indexOf('.') == -1) ? num : num.toFixed(2);
			}
		},

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
																																																																																																																					
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
								this.form[key] = form[dbKey]
							}
							if(dbKey === "source_table"){
								this.form['source_table'] = form[dbKey];
							}
							if(dbKey === "source_id"){
								this.form['source_id'] = form[dbKey];
							}
							if(dbKey === "source_user_id"){
								this.form['source_user_id'] = form[dbKey];
							}
						})
					})
				}
											$.db.del("form");

				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			async get_obj_after(json, func){
				if (json.result && json.result.obj) {
					const obj = json.result.obj;
				}
																																																								
			},

																																																													async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.maintenance_id){
					param.create_by = this.$store.state.user.user_id;
				}
																				var pm = this.events("submit_before", Object.assign({}, param)) || param;
				var msg = await this.events("submit_check", pm);
				var ret;

			
				if (msg) {
					this.$toast(msg, 'danger');
				} else {
																																																																	let submitForm = JSON.parse(JSON.stringify(pm));
								ret = this.events("submit_main", submitForm, func);
				}
				return ret;
			},
					async submit_after(json, func) {
						let path = this.$route.fullPath
						let skip_Path  = path.replace('/view','/table')
								if(!this.form.maintenance_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  														let message_inform = {
								title: '维修养护',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条维修养护数据',
								state: 1,
								user_id:'9999'
							}
							this.$post("~/api/message_inform/add",message_inform)
									setTimeout(() => {
								this.$router.push(skip_Path);
							}, 1000);
						}else{
							this.$router.push(skip_Path);
						}
					},
			
			/**
			 * 提交前验证事件
			 * @param {Object} 请求参数
			 * @return {String} 验证成功返回null, 失败返回错误提示
			 */
						async submit_check(param) {
					
							if (!param.project_bundle){
					return "项目套餐不能为空";
				}
																																																																																																										return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/maintenance/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/maintenance/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/maintenance/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/maintenance/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/maintenance/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},
			handlePictureCardPreview(file) {
				this.previewImageUrl = file.url;
				this.previewVisible = true;
			},
			uploadExceed() {
				this.$message({
					message: "最多可设置5张主图",
					type: "warning",
				});
			},
																																						},
		created() {
																	this.get_list_user_manager_user();
					this.get_group_user_manager_user();
										this.get_list_user_financial_user();
															},
	}
</script>

<style scoped>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
	
	.img_multiple{
		overflow: hidden;
	}
	.img_multiple .img_block{
		float: left;
		margin-right: 5px;
		margin-bottom: 5px;
		position: relative;
	}
	.img_multiple .img_block img{
		height: 100px;
		width: auto;
	}
	.img_multiple .img_del{
		position: absolute;
		top: 5px;
		right: 5px;
		width: 20px;
		height: 20px;
		background: #0000008a;
		color: #fff;
		line-height: 20px;
		text-align: center;
		border-radius: 100%;
		cursor: pointer;
	}
	.mul-upload-container{
		display: flex;
	}
	.mul-upload-container .el-form-item__content{
		margin-left: 0 !important;
	}
	.upload-container.hide-upload .el-upload--picture-card {
		display: none;
	}
	.el-upload--picture-card{
		width: 180px;
    	height: 180px;
		line-height: 180px;
	}





	.check-item-list {
		width: 100%;
	}
	.check-item-list .check-item-li .check-item {
		width: 100%;
		height: 70px;
		display: flex;
		flex-direction: row;
		align-items: center;
		padding: 0 10px;
		box-sizing: border-box;
		background-color: #fff;
		line-height: 100%;
		position: relative;
		margin: 10px 0;
		border: 1px solid #000;
	}
	.check-item-list .check-item-li .check-item .image-container {
		display: block;
		width: 50px;
		height: 50px;
	}
	.check-item-list .check-item-li .check-item .check-item-title {
		width: 60%;
		margin-left: 10px;
	}
	.check-item-list .check-item-li .check-item .check-item-title > p:nth-child(1) {
		font-weight: bold;
		margin-bottom: 10px;
		text-align: left;
		font-size: 16px;
	}
	.check-item-list .check-item-li .check-item .check-item-title > p:nth-child(2) {
		width: 100%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.check-item-list .check-item-li .check-item .check-item-btn {
		margin-left: auto;
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.check-item-list .check-item-li .check-item-form {
		width: 100%;
		background-color: #fff;
		margin-top: 10px;
		padding: 10px 0;
		box-sizing: border-box;
	}
	.check-item-list .check-item-li .check-item-form form {
		margin: 0 auto !important;
	}
	.check-item-list .check-item-li .check-item-form form .el-form-item {
		margin-bottom: 15px;
	}
	.check-item-list .check-item-li .check-item-complete {
		border: 1px solid #409eff;
		overflow: hidden;
	}
	.check-item-list .check-item-li .check-item-complete:before {
		content: "";
		position: absolute;
		right: 0;
		top: 0;
		border: 15px solid #409eff;
		border-bottom-color: transparent;
		border-left-color: transparent;
	}
	.check-item-list .check-item-li .check-item-complete:after {
		content: "";
		width: 8px;
		height: 4px;
		position: absolute;
		right: 3px;
		top: 3px;
		border: 2px solid #fff;
		border-bottom-color: transparent;
		border-left-color: transparent;
		transform: rotate(135deg);
	}
	.ccc {
		color: #ccc;
	}
	.image-slot {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #ccc;
		font-size: 24px;
	}
</style>
