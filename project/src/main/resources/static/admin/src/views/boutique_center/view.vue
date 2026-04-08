<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','service_code') || $check_field('add','service_code') || $check_field('set','service_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务编码" prop="service_code">
															<el-input id="service_code" v-model="form['service_code']" placeholder="请输入服务编码"
							  v-if="(form['boutique_center_id'] && $check_field('set','service_code')) || (!form['boutique_center_id'] && $check_field('add','service_code'))" :disabled="disabledObj['service_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','service_code')">
						{{form['service_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_name') || $check_field('add','service_name') || $check_field('set','service_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务名称" prop="service_name">
															<el-input id="service_name" v-model="form['service_name']" placeholder="请输入服务名称"
							  v-if="(form['boutique_center_id'] && $check_field('set','service_name')) || (!form['boutique_center_id'] && $check_field('add','service_name'))" :disabled="disabledObj['service_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','service_name')">
						{{form['service_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_type') || $check_field('add','service_type') || $check_field('set','service_type')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务类型" prop="service_type">
											<el-cascader id="service_type" v-model="form['service_type']" :options="service_type_tree" :props="service_type_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择服务类型" 
      					v-if="(form['boutique_center_id'] && $check_field('set', 'service_type')) || (!form['boutique_center_id'] && $check_field('add', 'service_type'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','service_type')">{{form['service_type']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_price') || $check_field('add','service_price') || $check_field('set','service_price')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务价格" prop="service_price">
											<el-input-number id="service_price" v-model.number="form['service_price']"
						v-if="(form['boutique_center_id'] && $check_field('set','service_price')) || (!form['boutique_center_id'] && $check_field('add','service_price'))" :disabled="disabledObj['service_price_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','service_price')">{{form['service_price']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">元</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_cover') || $check_field('add','service_cover') || $check_field('set','service_cover')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务封面" prop="service_cover">
											<el-upload :disabled="disabledObj['service_cover_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_service_cover"
						:show-file-list="false" v-if="(form['boutique_center_id'] && $check_field('set','service_cover')) || (!form['boutique_center_id'] && $check_field('add','service_cover'))">
						<img id="service_cover" v-if="form['service_cover']" :src="$fullUrl(form['service_cover'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','service_cover')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['service_cover'])" :preview-src-list="[$fullUrl(form['service_cover'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_introduction') || $check_field('add','service_introduction') || $check_field('set','service_introduction')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务简介" prop="service_introduction">
											<el-input type="textarea" id="service_introduction" v-model="form['service_introduction']" placeholder="请输入服务简介"
						v-if="(form['boutique_center_id'] && $check_field('set','service_introduction')) || (!form['boutique_center_id'] && $check_field('add','service_introduction'))" :disabled="disabledObj['service_introduction_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','service_introduction')">{{form['service_introduction']}}</div>
										</el-form-item>
			</el-col>
													<el-col v-if="$check_field('get','service_specificss') || $check_field('add','service_specificss') || $check_field('set','service_specificss')" :xs="24" :sm="24" :lg="24" class="el_form_editor_warp">
							<el-form-item label="服务详情" prop="service_specificss">
								<!-- <quill-editor v-model.number="form['service_specificss']"
						v-if="(form['boutique_center_id'] && $check_field('set','service_specificss')) || (!form['boutique_center_id'] && $check_field('add','service_specificss')) ">
					</quill-editor> -->
					<ai_editor
						v-if="(form['boutique_center_id'] && $check_field('set','service_specificss')) || (!form['boutique_center_id'] && $check_field('add','service_specificss'))"
						class="editor el_form_editor_warp"
						:details="form.service_specificss"
						:form="form"
						@blur_comment="blur_comment_service_specificss"
						@update_comment="update_comment_service_specificss"
						:publish_flag="false" />
					<div v-else-if="$check_field('get','service_specificss')" v-html="form['service_specificss']"></div>
				</el-form-item>
			</el-col>
						
	
	
	
		
		
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="在线预约限次">
				<el-input id="limit_times" v-model="form['reservation_record_limit_times']" placeholder="在线预约限制次数，0为不限"
						  v-if="$check_option('/boutique_center/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/boutique_center/view','get')" v-html="form['reservation_record_limit_times']"></div>
			  </el-form-item>
			</el-col>
			
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/boutique_center/view','set') || $check_action('/boutique_center/view','add')">
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
				field: "boutique_center_id",
				url_add: "~/api/boutique_center/add?",
				url_set: "~/api/boutique_center/set?",
				url_get_obj: "~/api/boutique_center/get_obj?",
				url_get_list: "~/api/boutique_center/get_list?",
				url_upload: "~/api/boutique_center/upload?",

				query: {
					"boutique_center_id": 0,
					like: 0,
				},

				form: {
								"service_code":  '', // 服务编码
										"service_name":  '', // 服务名称
										"service_type":  '', // 服务类型
										"service_price":  0, // 服务价格
										"service_cover":  '', // 服务封面
										"service_introduction":  '', // 服务简介
										"service_specificss":  '', // 服务详情
												"boutique_center_id": 0, // ID
													"reservation_record_limit_times": 0, // 在线预约限制次数
															"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"service_code_isDisabled": false,
										"service_name_isDisabled": false,
										"service_type_isDisabled": false,
					          			"service_price_isDisabled": false,
										"service_cover_isDisabled": false,
										"service_introduction_isDisabled": false,
										"service_specificss_isDisabled": false,
										},

																							service_type_cascader: {
					value: 'service_type',
					label: 'service_type',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				service_type_tree: [],
						// 服务类型选项列表
				list_service_type: [""],
																																			

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
			 * 获取服务类型列表
			 */
						async get_list_service_type() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/service_class_nameification/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.service_type_tree = this.build_service_type_tree(json.result.list, 0);
					} else {
						this.service_type_tree = json.result.list;
					}
					if (!this.query.boutique_center_id && this.service_type_tree.length > 0) {
										this.form["service_type"] = this.service_type_tree[0]["service_type"];
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
												build_service_type_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_service_type_tree(list, item.service_class_nameification_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			
					
	
							/**
			 * 上传服务封面
			 * @param {Object} param 图片参数
			 */
			upload_service_cover(param){
									this.uploadFile(param.file, "service_cover");
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
				if(!this.form.boutique_center_id){
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
								if(!this.form.boutique_center_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '精品中心',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条精品中心数据',
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
					
																																																																																																											return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/boutique_center/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/boutique_center/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/boutique_center/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/boutique_center/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/boutique_center/view','get');
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
																																				blur_comment_service_specificss(data) {
				this.form['service_specificss'] = data;
			},
			update_comment_service_specificss(data) {
				this.form['service_specificss'] = data;
			},
							},
		created() {
												this.get_list_service_type();
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
