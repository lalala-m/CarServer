<template>
	<el-main class="bg edit_wrap comtable_e">
		<iframe :srcdoc="iframeContent" id='printIframe' width="600" height="400" style="display: none;"></iframe>
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','card_code') || $check_field('add','card_code') || $check_field('set','card_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="卡券编码" prop="card_code">
															<el-input id="card_code" v-model="form['card_code']" placeholder="请输入卡券编码"
							  v-if="(form['write_off_information_id'] && $check_field('set','card_code')) || (!form['write_off_information_id'] && $check_field('add','card_code'))" :disabled="disabledObj['card_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','card_code')">
						{{form['card_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','manager_user') || $check_field('add','manager_user') || $check_field('set','manager_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="经理用户" prop="manager_user">
																<el-select v-if="(form['write_off_information_id'] && $check_field('set','manager_user')) || (!form['write_off_information_id'] && $check_field('add','manager_user'))" id="manager_user" v-model="form['manager_user']" :disabled="disabledObj['manager_user_isDisabled']">
							<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','manager_user')" id="manager_user" v-model="form['manager_user']" :disabled="true">
							<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owner_user') || $check_field('add','owner_user') || $check_field('set','owner_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主用户" prop="owner_user">
																<el-select v-if="(form['write_off_information_id'] && $check_field('set','owner_user')) || (!form['write_off_information_id'] && $check_field('add','owner_user'))" id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
							<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','owner_user')" id="owner_user" v-model="form['owner_user']" :disabled="true">
							<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','financial_user') || $check_field('add','financial_user') || $check_field('set','financial_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="财务用户" prop="financial_user">
																<el-select v-if="(form['write_off_information_id'] && $check_field('set','financial_user')) || (!form['write_off_information_id'] && $check_field('add','financial_user'))" id="financial_user" v-model="form['financial_user']" :disabled="disabledObj['financial_user_isDisabled']">
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
																<el-col v-if="$check_field('get','card_name') || $check_field('add','card_name') || $check_field('set','card_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="卡券名称" prop="card_name">
															<el-input id="card_name" v-model="form['card_name']" placeholder="请输入卡券名称"
							  v-if="(form['write_off_information_id'] && $check_field('set','card_name')) || (!form['write_off_information_id'] && $check_field('add','card_name'))" :disabled="disabledObj['card_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','card_name')">
						{{form['card_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','effective_period') || $check_field('add','effective_period') || $check_field('set','effective_period')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="有效期限" prop="effective_period">
															<el-input id="effective_period" v-model="form['effective_period']" placeholder="请输入有效期限"
							  v-if="(form['write_off_information_id'] && $check_field('set','effective_period')) || (!form['write_off_information_id'] && $check_field('add','effective_period'))" :disabled="disabledObj['effective_period_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','effective_period')">
						{{form['effective_period']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','number_of_uses') || $check_field('add','number_of_uses') || $check_field('set','number_of_uses')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="使用次数" prop="number_of_uses">
															<el-input id="number_of_uses" v-model="form['number_of_uses']" placeholder="请输入使用次数"
							  v-if="(form['write_off_information_id'] && $check_field('set','number_of_uses')) || (!form['write_off_information_id'] && $check_field('add','number_of_uses'))" :disabled="disabledObj['number_of_uses_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','number_of_uses')">
						{{form['number_of_uses']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','issuing_time') || $check_field('add','issuing_time') || $check_field('set','issuing_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="发放时间" prop="issuing_time">
											<el-date-picker :disabled="disabledObj['issuing_time_isDisabled']" v-if="(form['write_off_information_id'] && $check_field('set','issuing_time')) || (!form['write_off_information_id'] && $check_field('add','issuing_time'))" id="issuing_time"
						v-model="form['issuing_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','issuing_time')">{{form['issuing_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','write_off_time') || $check_field('add','write_off_time') || $check_field('set','write_off_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="核销时间" prop="write_off_time">
											<el-date-picker :disabled="disabledObj['write_off_time_isDisabled']" v-if="(form['write_off_information_id'] && $check_field('set','write_off_time')) || (!form['write_off_information_id'] && $check_field('add','write_off_time'))" id="write_off_time"
						v-model="form['write_off_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','write_off_time')">{{form['write_off_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','write_off_doc') || $check_field('add','write_off_doc') || $check_field('set','write_off_doc')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="核销单" prop="write_off_doc">
											<el-upload :disabled="disabledObj['write_off_doc_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_write_off_doc"
						:show-file-list="false" v-if="(form['write_off_information_id'] && $check_field('set','write_off_doc')) || (!form['write_off_information_id'] && $check_field('add','write_off_doc'))">
						<img id="write_off_doc" v-if="form['write_off_doc']" :src="$fullUrl(form['write_off_doc'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','write_off_doc')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['write_off_doc'])" :preview-src-list="[$fullUrl(form['write_off_doc'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','write_off_remarks') || $check_field('add','write_off_remarks') || $check_field('set','write_off_remarks')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="核销备注" prop="write_off_remarks">
											<el-input type="textarea" id="write_off_remarks" v-model="form['write_off_remarks']" placeholder="请输入核销备注"
						v-if="(form['write_off_information_id'] && $check_field('set','write_off_remarks')) || (!form['write_off_information_id'] && $check_field('add','write_off_remarks'))" :disabled="disabledObj['write_off_remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','write_off_remarks')">{{form['write_off_remarks']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/write_off_information/view','set') || $check_action('/write_off_information/view','add')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
					<el-button v-if="!!query.write_off_information_id"  type="primary" @click="sendprint">打印</el-button>
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
				field: "write_off_information_id",
				url_add: "~/api/write_off_information/add?",
				url_set: "~/api/write_off_information/set?",
				url_get_obj: "~/api/write_off_information/get_obj?",
				url_get_list: "~/api/write_off_information/get_list?",
				url_upload: "~/api/write_off_information/upload?",

				query: {
					"write_off_information_id": 0,
					like: 0,
				},

				form: {
								"card_code":  '', // 卡券编码
										"manager_user": 0, // 经理用户
										"owner_user": 0, // 车主用户
										"financial_user": 0, // 财务用户
										"card_name":  '', // 卡券名称
										"effective_period":  '', // 有效期限
										"number_of_uses":  '', // 使用次数
										"issuing_time":  '', // 发放时间
										"write_off_time":  '', // 核销时间
										"write_off_doc":  '', // 核销单
										"write_off_remarks":  '', // 核销备注
												"write_off_information_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"card_code_isDisabled": false,
										"manager_user_isDisabled": false,
										"owner_user_isDisabled": false,
										"financial_user_isDisabled": false,
										"card_name_isDisabled": false,
										"effective_period_isDisabled": false,
										"number_of_uses_isDisabled": false,
										"issuing_time_isDisabled": false,
										"write_off_time_isDisabled": false,
										"write_off_doc_isDisabled": false,
										"write_off_remarks_isDisabled": false,
										},

														// 用户列表
				list_user_manager_user: [],
														// 用户列表
				list_user_owner_user: [],
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
														sendprint(){
				let imgArrs = [
																																				{ label: '核销单', key: 'write_off_doc', value: document.getElementById('write_off_doc') ? document.getElementById('write_off_doc').src : '' },
										];
				let formArrs = [
									{ label: '卡券编码', key: 'card_code', value: '' },
										{ label: '经理用户', key: 'manager_user', value: '' },
										{ label: '车主用户', key: 'owner_user', value: '' },
										{ label: '财务用户', key: 'financial_user', value: '' },
										{ label: '卡券名称', key: 'card_name', value: '' },
										{ label: '有效期限', key: 'effective_period', value: '' },
										{ label: '使用次数', key: 'number_of_uses', value: '' },
										{ label: '发放时间', key: 'issuing_time', value: '' },
										{ label: '核销时间', key: 'write_off_time', value: '' },
													{ label: '核销备注', key: 'write_off_remarks', value: '' },
										]
				this.printFrame(imgArrs,formArrs)
			},

														openPreview(file) {
			    window.open(file.url)
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
					
	
					
	
					
	
					
	
					
	
							/**
			 * 上传核销单
			 * @param {Object} param 图片参数
			 */
			upload_write_off_doc(param){
									this.uploadFile(param.file, "write_off_doc");
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
								        if (this.form["issuing_time"] && JSON.stringify(this.form["issuing_time"]).indexOf("-")===-1){
            this.form["issuing_time"] = this.$toTime(parseInt(this.form["issuing_time"]),"yyyy-MM-dd hh:mm:ss")
        }
		        if (this.form["write_off_time"] && JSON.stringify(this.form["write_off_time"]).indexOf("-")===-1){
            this.form["write_off_time"] = this.$toTime(parseInt(this.form["write_off_time"]),"yyyy-MM-dd hh:mm:ss")
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
																																																													if(this.form["issuing_time"]=="0000-00-00 00:00:00"){
				  this.form["issuing_time"] = null;
				}
				if(parseInt(this.form["issuing_time"]) > 9999){
					this.form["issuing_time"] = this.$toTime(parseInt(this.form["issuing_time"]),"yyyy-MM-dd hh:mm:ss")
				}
													if(this.form["write_off_time"]=="0000-00-00 00:00:00"){
				  this.form["write_off_time"] = null;
				}
				if(parseInt(this.form["write_off_time"]) > 9999){
					this.form["write_off_time"] = this.$toTime(parseInt(this.form["write_off_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																								
			},

																																																																																									async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.write_off_information_id){
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
								if(!this.form.write_off_information_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																															this.$post("~/api/issuing_card_voucher/set?card_code=" + this.form.card_code, { write_off_status: "已核销" }, (res) => {
								
							}).catch(() => {
								console.log("修改write_off_status出错")
							})
																																						  				  				  				  				  				  				  				  				  				  				  				  				  											let message_inform = {
								title: '核销信息',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条核销信息数据',
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
					
																																																																																																		if (!param.issuing_time){
					return "发放时间不能为空";
				}
																			if (!param.write_off_time){
					return "核销时间不能为空";
				}
																																																	return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/write_off_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/write_off_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/write_off_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/write_off_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/write_off_information/view','get');
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
										this.get_list_user_owner_user();
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
