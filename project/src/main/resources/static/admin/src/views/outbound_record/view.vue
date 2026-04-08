<template>
	<el-main class="bg edit_wrap comtable_e">
		<iframe :srcdoc="iframeContent" id='printIframe' width="600" height="400" style="display: none;"></iframe>
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','consumables_code') || $check_field('add','consumables_code') || $check_field('set','consumables_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材编码" prop="consumables_code">
															<el-input id="consumables_code" v-model="form['consumables_code']" placeholder="请输入耗材编码"
							  v-if="(form['outbound_record_id'] && $check_field('set','consumables_code')) || (!form['outbound_record_id'] && $check_field('add','consumables_code'))" :disabled="disabledObj['consumables_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_code')">
						{{form['consumables_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','consumables_name') || $check_field('add','consumables_name') || $check_field('set','consumables_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材名称" prop="consumables_name">
															<el-input id="consumables_name" v-model="form['consumables_name']" placeholder="请输入耗材名称"
							  v-if="(form['outbound_record_id'] && $check_field('set','consumables_name')) || (!form['outbound_record_id'] && $check_field('add','consumables_name'))" :disabled="disabledObj['consumables_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_name')">
						{{form['consumables_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','consumables_specifications') || $check_field('add','consumables_specifications') || $check_field('set','consumables_specifications')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材规格" prop="consumables_specifications">
															<el-input id="consumables_specifications" v-model="form['consumables_specifications']" placeholder="请输入耗材规格"
							  v-if="(form['outbound_record_id'] && $check_field('set','consumables_specifications')) || (!form['outbound_record_id'] && $check_field('add','consumables_specifications'))" :disabled="disabledObj['consumables_specifications_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_specifications')">
						{{form['consumables_specifications']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','financial_user') || $check_field('add','financial_user') || $check_field('set','financial_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="财务用户" prop="financial_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_financial_user(form['financial_user']) }}
							<el-select v-if="(form['outbound_record_id'] && $check_field('set','financial_user')) || (!form['outbound_record_id'] && $check_field('add','financial_user'))" id="financial_user" v-model="form['financial_user']" :disabled="disabledObj['financial_user_isDisabled']">
								<el-option v-for="o in list_user_financial_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','financial_user')" id="financial_user" v-model="form['financial_user']" :disabled="true">
								<el-option v-for="o in list_user_financial_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="financial_user" v-model="form['financial_user']" :disabled="disabledObj['financial_user_isDisabled']">
							<el-option v-for="o in list_user_financial_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','outbound_time') || $check_field('add','outbound_time') || $check_field('set','outbound_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="出库时间" prop="outbound_time">
											<el-date-picker :disabled="disabledObj['outbound_time_isDisabled']" v-if="(form['outbound_record_id'] && $check_field('set','outbound_time')) || (!form['outbound_record_id'] && $check_field('add','outbound_time'))" id="outbound_time"
						v-model="form['outbound_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','outbound_time')">{{form['outbound_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','outbound_quantity') || $check_field('add','outbound_quantity') || $check_field('set','outbound_quantity')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="出库数量" prop="outbound_quantity">
											<el-input-number id="outbound_quantity" v-model.number="form['outbound_quantity']"
						v-if="(form['outbound_record_id'] && $check_field('set','outbound_quantity')) || (!form['outbound_record_id'] && $check_field('add','outbound_quantity'))" :disabled="disabledObj['outbound_quantity_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','outbound_quantity')">{{form['outbound_quantity']}}</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','outbound_note') || $check_field('add','outbound_note') || $check_field('set','outbound_note')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="出库备注" prop="outbound_note">
											<el-input type="textarea" id="outbound_note" v-model="form['outbound_note']" placeholder="请输入出库备注"
						v-if="(form['outbound_record_id'] && $check_field('set','outbound_note')) || (!form['outbound_record_id'] && $check_field('add','outbound_note'))" :disabled="disabledObj['outbound_note_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','outbound_note')">{{form['outbound_note']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/outbound_record/view','set') || $check_action('/outbound_record/view','add')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
					<el-button v-if="!!query.outbound_record_id"  type="primary" @click="sendprint">打印</el-button>
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
				iframeContent:'',
				field: "outbound_record_id",
				url_add: "~/api/outbound_record/add?",
				url_set: "~/api/outbound_record/set?",
				url_get_obj: "~/api/outbound_record/get_obj?",
				url_get_list: "~/api/outbound_record/get_list?",
				url_upload: "~/api/outbound_record/upload?",

				query: {
					"outbound_record_id": 0,
					like: 0,
				},

				form: {
								"consumables_code":  '', // 耗材编码
										"consumables_name":  '', // 耗材名称
										"consumables_specifications":  '', // 耗材规格
										"financial_user": 0, // 财务用户
										"outbound_time":  '', // 出库时间
										"outbound_quantity":  0, // 出库数量
										"outbound_note":  '', // 出库备注
												"outbound_record_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"consumables_code_isDisabled": false,
										"consumables_name_isDisabled": false,
										"consumables_specifications_isDisabled": false,
										"financial_user_isDisabled": false,
										"outbound_time_isDisabled": false,
					          			"outbound_quantity_isDisabled": false,
										"outbound_note_isDisabled": false,
										},

																												// 用户列表
				list_user_financial_user: [],
						// 用户组
				group_user_financial_user: "",
																												

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
										sendprint(){
				let imgArrs = [
																											];
				let formArrs = [
									{ label: '耗材编码', key: 'consumables_code', value: '' },
										{ label: '耗材名称', key: 'consumables_name', value: '' },
										{ label: '耗材规格', key: 'consumables_specifications', value: '' },
										{ label: '财务用户', key: 'financial_user', value: '' },
										{ label: '出库时间', key: 'outbound_time', value: '' },
										{ label: '出库数量', key: 'outbound_quantity', value: '' },
										{ label: '出库备注', key: 'outbound_note', value: '' },
										]
				this.printFrame(imgArrs,formArrs)
			},

										openPreview(file) {
			    window.open(file.url)
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
					/**
			 * 获取财务用户用户组
			 */
			async get_group_user_financial_user() {
							this.form["financial_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=财务用户");
				if(json.result && json.result.obj){
					this.group_user_financial_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_financial_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_financial_user.source_table+"/get_obj?"
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
												_this.form["financial_user"] = id
									_this.disabledObj['financial_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "financial_user" && arr[i] !== "create_by") {
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
					        if (this.form["outbound_time"] && JSON.stringify(this.form["outbound_time"]).indexOf("-")===-1){
            this.form["outbound_time"] = this.$toTime(parseInt(this.form["outbound_time"]),"yyyy-MM-dd hh:mm:ss")
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
																																					if(this.form["outbound_time"]=="0000-00-00 00:00:00"){
				  this.form["outbound_time"] = null;
				}
				if(parseInt(this.form["outbound_time"]) > 9999){
					this.form["outbound_time"] = this.$toTime(parseInt(this.form["outbound_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																								
			},

																																																													async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.outbound_record_id){
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
								if(!this.form.outbound_record_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																																														  		  							  		  							  		  							  		  														let message_inform = {
								title: '出库记录',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条出库记录数据',
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
					
																																																											if (!param.outbound_time){
					return "出库时间不能为空";
				}
																																																	return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/outbound_record/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/outbound_record/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/outbound_record/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/outbound_record/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/outbound_record/view','get');
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
																	this.get_list_user_financial_user();
					this.get_group_user_financial_user();
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
