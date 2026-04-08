<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','consumables_code') || $check_field('add','consumables_code') || $check_field('set','consumables_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材编码" prop="consumables_code">
															<el-input id="consumables_code" v-model="form['consumables_code']" placeholder="请输入耗材编码"
							  v-if="(form['inventory_information_id'] && $check_field('set','consumables_code')) || (!form['inventory_information_id'] && $check_field('add','consumables_code'))" :disabled="disabledObj['consumables_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_code')">
						{{form['consumables_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','consumables_name') || $check_field('add','consumables_name') || $check_field('set','consumables_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材名称" prop="consumables_name">
															<el-input id="consumables_name" v-model="form['consumables_name']" placeholder="请输入耗材名称"
							  v-if="(form['inventory_information_id'] && $check_field('set','consumables_name')) || (!form['inventory_information_id'] && $check_field('add','consumables_name'))" :disabled="disabledObj['consumables_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_name')">
						{{form['consumables_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','consumables_inventory') || $check_field('add','consumables_inventory') || $check_field('set','consumables_inventory')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材库存" prop="consumables_inventory">
											<el-input-number id="consumables_inventory" v-model.number="form['consumables_inventory']"
						v-if="(form['inventory_information_id'] && $check_field('set','consumables_inventory')) || (!form['inventory_information_id'] && $check_field('add','consumables_inventory'))" :disabled="disabledObj['consumables_inventory_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','consumables_inventory')">{{form['consumables_inventory']}}</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','consumables_specifications') || $check_field('add','consumables_specifications') || $check_field('set','consumables_specifications')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材规格" prop="consumables_specifications">
															<el-input id="consumables_specifications" v-model="form['consumables_specifications']" placeholder="请输入耗材规格"
							  v-if="(form['inventory_information_id'] && $check_field('set','consumables_specifications')) || (!form['inventory_information_id'] && $check_field('add','consumables_specifications'))" :disabled="disabledObj['consumables_specifications_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','consumables_specifications')">
						{{form['consumables_specifications']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','introduction_of_consumables') || $check_field('add','introduction_of_consumables') || $check_field('set','introduction_of_consumables')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="耗材简介" prop="introduction_of_consumables">
											<el-input type="textarea" id="introduction_of_consumables" v-model="form['introduction_of_consumables']" placeholder="请输入耗材简介"
						v-if="(form['inventory_information_id'] && $check_field('set','introduction_of_consumables')) || (!form['inventory_information_id'] && $check_field('add','introduction_of_consumables'))" :disabled="disabledObj['introduction_of_consumables_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','introduction_of_consumables')">{{form['introduction_of_consumables']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="入库限次">
				<el-input id="limit_times" v-model="form['inbound_record_limit_times']" placeholder="入库限制次数，0为不限"
						  v-if="$check_option('/inventory_information/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/inventory_information/view','get')" v-html="form['inbound_record_limit_times']"></div>
			  </el-form-item>
			</el-col>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="出库限次">
				<el-input id="limit_times" v-model="form['outbound_record_limit_times']" placeholder="出库限制次数，0为不限"
						  v-if="$check_option('/inventory_information/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/inventory_information/view','get')" v-html="form['outbound_record_limit_times']"></div>
			  </el-form-item>
			</el-col>
			
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/inventory_information/view','set') || $check_action('/inventory_information/view','add')">
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
				field: "inventory_information_id",
				url_add: "~/api/inventory_information/add?",
				url_set: "~/api/inventory_information/set?",
				url_get_obj: "~/api/inventory_information/get_obj?",
				url_get_list: "~/api/inventory_information/get_list?",
				url_upload: "~/api/inventory_information/upload?",

				query: {
					"inventory_information_id": 0,
					like: 0,
				},

				form: {
								"consumables_code":  '', // 耗材编码
										"consumables_name":  '', // 耗材名称
										"consumables_inventory":  0, // 耗材库存
										"consumables_specifications":  '', // 耗材规格
										"introduction_of_consumables":  '', // 耗材简介
												"inventory_information_id": 0, // ID
													"inbound_record_limit_times": 0, // 入库限制次数
							"outbound_record_limit_times": 0, // 出库限制次数
															"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"consumables_code_isDisabled": false,
										"consumables_name_isDisabled": false,
					          			"consumables_inventory_isDisabled": false,
										"consumables_specifications_isDisabled": false,
										"introduction_of_consumables_isDisabled": false,
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
				if(!this.form.inventory_information_id){
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
								if(!this.form.inventory_information_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '库存信息',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条库存信息数据',
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
					bl = this.$check_action('/inventory_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/inventory_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/inventory_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/inventory_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/inventory_information/view','get');
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
