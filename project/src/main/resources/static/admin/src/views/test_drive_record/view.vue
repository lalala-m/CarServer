<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','test_drive_code') || $check_field('add','test_drive_code') || $check_field('set','test_drive_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="试驾编码" prop="test_drive_code">
															<el-input id="test_drive_code" v-model="form['test_drive_code']" placeholder="请输入试驾编码"
							  v-if="(form['test_drive_record_id'] && $check_field('set','test_drive_code')) || (!form['test_drive_record_id'] && $check_field('add','test_drive_code'))" :disabled="true">
				  					</el-input>
					<div v-else-if="$check_field('get','test_drive_code')">
						{{form['test_drive_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_coding') || $check_field('add','car_coding') || $check_field('set','car_coding')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车编码" prop="car_coding">
															<el-input id="car_coding" v-model="form['car_coding']" placeholder="请输入汽车编码"
							  v-if="(form['test_drive_record_id'] && $check_field('set','car_coding')) || (!form['test_drive_record_id'] && $check_field('add','car_coding'))" :disabled="disabledObj['car_coding_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_coding')">
						{{form['car_coding']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_name') || $check_field('add','car_name') || $check_field('set','car_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车名称" prop="car_name">
															<el-input id="car_name" v-model="form['car_name']" placeholder="请输入汽车名称"
							  v-if="(form['test_drive_record_id'] && $check_field('set','car_name')) || (!form['test_drive_record_id'] && $check_field('add','car_name'))" :disabled="disabledObj['car_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_name')">
						{{form['car_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_models') || $check_field('add','car_models') || $check_field('set','car_models')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车车型" prop="car_models">
															<el-input id="car_models" v-model="form['car_models']" placeholder="请输入汽车车型"
							  v-if="(form['test_drive_record_id'] && $check_field('set','car_models')) || (!form['test_drive_record_id'] && $check_field('add','car_models'))" :disabled="disabledObj['car_models_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_models')">
						{{form['car_models']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','business_user') || $check_field('add','business_user') || $check_field('set','business_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商家用户" prop="business_user">
																<el-select v-if="(form['test_drive_record_id'] && $check_field('set','business_user')) || (!form['test_drive_record_id'] && $check_field('add','business_user'))" id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
							<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','business_user')" id="business_user" v-model="form['business_user']" :disabled="true">
							<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owner_user') || $check_field('add','owner_user') || $check_field('set','owner_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主用户" prop="owner_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_owner_user(form['owner_user']) }}
							<el-select v-if="(form['test_drive_record_id'] && $check_field('set','owner_user')) || (!form['test_drive_record_id'] && $check_field('add','owner_user'))" id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
								<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','owner_user')" id="owner_user" v-model="form['owner_user']" :disabled="true">
								<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
							<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','test_drive_time') || $check_field('add','test_drive_time') || $check_field('set','test_drive_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="试驾时间" prop="test_drive_time">
											<el-date-picker :disabled="disabledObj['test_drive_time_isDisabled']" v-if="(form['test_drive_record_id'] && $check_field('set','test_drive_time')) || (!form['test_drive_record_id'] && $check_field('add','test_drive_time'))" id="test_drive_time"
						v-model="form['test_drive_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','test_drive_time')">{{form['test_drive_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','test_drive_remarks') || $check_field('add','test_drive_remarks') || $check_field('set','test_drive_remarks')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="试驾备注" prop="test_drive_remarks">
											<el-input type="textarea" id="test_drive_remarks" v-model="form['test_drive_remarks']" placeholder="请输入试驾备注"
						v-if="(form['test_drive_record_id'] && $check_field('set','test_drive_remarks')) || (!form['test_drive_record_id'] && $check_field('add','test_drive_remarks'))" :disabled="disabledObj['test_drive_remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','test_drive_remarks')">{{form['test_drive_remarks']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/test_drive_record/view','set') || $check_action('/test_drive_record/view','add')">
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
				field: "test_drive_record_id",
				url_add: "~/api/test_drive_record/add?",
				url_set: "~/api/test_drive_record/set?",
				url_get_obj: "~/api/test_drive_record/get_obj?",
				url_get_list: "~/api/test_drive_record/get_list?",
				url_upload: "~/api/test_drive_record/upload?",

				query: {
					"test_drive_record_id": 0,
					like: 0,
				},

				form: {
								"test_drive_code": this.$get_stamp(), // 试驾编码
										"car_coding":  '', // 汽车编码
										"car_name":  '', // 汽车名称
										"car_models":  '', // 汽车车型
										"business_user": 0, // 商家用户
										"owner_user": 0, // 车主用户
										"test_drive_time":  '', // 试驾时间
										"test_drive_remarks":  '', // 试驾备注
												"test_drive_record_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"test_drive_code_isDisabled": false,
										"car_coding_isDisabled": false,
										"car_name_isDisabled": false,
										"car_models_isDisabled": false,
										"business_user_isDisabled": false,
										"owner_user_isDisabled": false,
										"test_drive_time_isDisabled": false,
										"test_drive_remarks_isDisabled": false,
										},

																																			// 用户列表
				list_user_business_user: [],
														// 用户列表
				list_user_owner_user: [],
						// 用户组
				group_user_owner_user: "",
																					

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
			 * 获取商家用户用户列表
			 */
			async get_list_user_business_user() {
                var json = await this.$get("~/api/user/get_list?user_group=商家用户");
                if(json.result && json.result.list){
                    this.list_user_business_user = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_business_user(id){
				var obj = this.list_user_business_user.getObj({"user_id":id});
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
					/**
			 * 获取车主用户用户组
			 */
			async get_group_user_owner_user() {
							this.form["owner_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=车主用户");
				if(json.result && json.result.obj){
					this.group_user_owner_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_owner_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_owner_user.source_table+"/get_obj?"
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
												_this.form["owner_user"] = id
									_this.disabledObj['owner_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "owner_user" && arr[i] !== "create_by") {
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
					get_user_owner_user(id){
				var obj = this.list_user_owner_user.getObj({"user_id":id});
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
																																											// 获取缓存数据附加
					form = $.db.get("form");
													$.push(this.form ,form);
																												
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
							        if (this.form["test_drive_time"] && JSON.stringify(this.form["test_drive_time"]).indexOf("-")===-1){
            this.form["test_drive_time"] = this.$toTime(parseInt(this.form["test_drive_time"]),"yyyy-MM-dd hh:mm:ss")
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
																																																					if(this.form["test_drive_time"]=="0000-00-00 00:00:00"){
				  this.form["test_drive_time"] = null;
				}
				if(parseInt(this.form["test_drive_time"]) > 9999){
					this.form["test_drive_time"] = this.$toTime(parseInt(this.form["test_drive_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																
			},

																																																																				async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.test_drive_record_id){
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
								if(!this.form.test_drive_record_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																															this.$post("~/api/car_information/set?car_coding=" + this.form.car_coding, { car_status: "试驾中" }, (res) => {
								
							}).catch(() => {
								console.log("修改car_status出错")
							})
																																													  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  											let message_inform = {
								title: '试驾记录',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条试驾记录数据',
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
					
																																																																																					if (!param.test_drive_time){
					return "试驾时间不能为空";
				}
																																				return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/test_drive_record/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/test_drive_record/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/test_drive_record/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/test_drive_record/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/test_drive_record/view','get');
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
																					this.get_list_user_business_user();
										this.get_list_user_owner_user();
					this.get_group_user_owner_user();
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
