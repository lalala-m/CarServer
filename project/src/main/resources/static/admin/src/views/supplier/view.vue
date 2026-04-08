<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','name_of_manufacturer') || $check_field('add','name_of_manufacturer') || $check_field('set','name_of_manufacturer')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item prop="name_of_manufacturer">
					<div slot="label">
						<i style="color: red;">*</i>
						厂家名称
					</div>
															<el-input id="name_of_manufacturer" v-model="form['name_of_manufacturer']" placeholder="请输入厂家名称"
							  v-if="(form['supplier_id'] && $check_field('set','name_of_manufacturer')) || (!form['supplier_id'] && $check_field('add','name_of_manufacturer'))" :disabled="disabledObj['name_of_manufacturer_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','name_of_manufacturer')">
						{{form['name_of_manufacturer']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','contact_phone') || $check_field('add','contact_phone') || $check_field('set','contact_phone')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item prop="contact_phone">
					<div slot="label">
						<i style="color: red;">*</i>
						联系电话
					</div>
											<el-input id="contact_phone" v-model="form['contact_phone']" placeholder="请输入联系电话" type="tel"
						v-if="(form['supplier_id'] && $check_field('set','contact_phone')) || (!form['supplier_id'] && $check_field('add','contact_phone'))">
					</el-input>
					<div v-else-if="$check_field('get','contact_phone')">{{form['contact_phone']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','manufacturer_address') || $check_field('add','manufacturer_address') || $check_field('set','manufacturer_address')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="厂家地址" prop="manufacturer_address">
															<el-input id="manufacturer_address" v-model="form['manufacturer_address']" placeholder="请输入厂家地址"
							  v-if="(form['supplier_id'] && $check_field('set','manufacturer_address')) || (!form['supplier_id'] && $check_field('add','manufacturer_address'))" :disabled="disabledObj['manufacturer_address_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','manufacturer_address')">
						{{form['manufacturer_address']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','main_products') || $check_field('add','main_products') || $check_field('set','main_products')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="主营产品" prop="main_products">
											<el-input type="textarea" id="main_products" v-model="form['main_products']" placeholder="请输入主营产品"
						v-if="(form['supplier_id'] && $check_field('set','main_products')) || (!form['supplier_id'] && $check_field('add','main_products'))" :disabled="disabledObj['main_products_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','main_products')">{{form['main_products']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','manufacturer_profile') || $check_field('add','manufacturer_profile') || $check_field('set','manufacturer_profile')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="厂家简介" prop="manufacturer_profile">
											<el-input type="textarea" id="manufacturer_profile" v-model="form['manufacturer_profile']" placeholder="请输入厂家简介"
						v-if="(form['supplier_id'] && $check_field('set','manufacturer_profile')) || (!form['supplier_id'] && $check_field('add','manufacturer_profile'))" :disabled="disabledObj['manufacturer_profile_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','manufacturer_profile')">{{form['manufacturer_profile']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/supplier/view','set') || $check_action('/supplier/view','add')">
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
				field: "supplier_id",
				url_add: "~/api/supplier/add?",
				url_set: "~/api/supplier/set?",
				url_get_obj: "~/api/supplier/get_obj?",
				url_get_list: "~/api/supplier/get_list?",
				url_upload: "~/api/supplier/upload?",

				query: {
					"supplier_id": 0,
					like: 0,
				},

				form: {
								"name_of_manufacturer": null, // 厂家名称
										"contact_phone": null, // 联系电话
										"manufacturer_address":  '', // 厂家地址
										"main_products":  '', // 主营产品
										"manufacturer_profile":  '', // 厂家简介
												"supplier_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"name_of_manufacturer_isDisabled": false,
										"contact_phone_isDisabled": false,
										"manufacturer_address_isDisabled": false,
										"main_products_isDisabled": false,
										"manufacturer_profile_isDisabled": false,
										},

																																			

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
				if(!this.form.supplier_id){
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
								if(!this.form.supplier_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '供应厂商',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条供应厂商数据',
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
					
							if (!param.name_of_manufacturer){
					return "厂家名称不能为空";
				}
																			if (!param.contact_phone){
					return "联系电话不能为空";
				}
								let contact_phone_phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if(param.contact_phone && !contact_phone_phone_regular.test(param.contact_phone)){
					return "手机号格式错误"
				}
																																																																	return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/supplier/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier/view','get');
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
