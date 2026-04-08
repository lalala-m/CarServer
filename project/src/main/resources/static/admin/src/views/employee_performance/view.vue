<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','performance_title') || $check_field('add','performance_title') || $check_field('set','performance_title')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="绩效标题" prop="performance_title">
															<el-input id="performance_title" v-model="form['performance_title']" placeholder="请输入绩效标题"
							  v-if="(form['employee_performance_id'] && $check_field('set','performance_title')) || (!form['employee_performance_id'] && $check_field('add','performance_title'))" :disabled="disabledObj['performance_title_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','performance_title')">
						{{form['performance_title']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','employee_name') || $check_field('add','employee_name') || $check_field('set','employee_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="员工姓名" prop="employee_name">
															<el-input id="employee_name" v-model="form['employee_name']" placeholder="请输入员工姓名"
							  v-if="(form['employee_performance_id'] && $check_field('set','employee_name')) || (!form['employee_performance_id'] && $check_field('add','employee_name'))" :disabled="disabledObj['employee_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','employee_name')">
						{{form['employee_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','evaluation_time') || $check_field('add','evaluation_time') || $check_field('set','evaluation_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="评估时间" prop="evaluation_time">
											<el-date-picker :disabled="disabledObj['evaluation_time_isDisabled']" v-if="(form['employee_performance_id'] && $check_field('set','evaluation_time')) || (!form['employee_performance_id'] && $check_field('add','evaluation_time'))" id="evaluation_time"
						v-model="form['evaluation_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','evaluation_time')">{{form['evaluation_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_singular') || $check_field('add','service_singular') || $check_field('set','service_singular')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务单数" prop="service_singular">
											<el-input-number id="service_singular" v-model.number="form['service_singular']"
						v-if="(form['employee_performance_id'] && $check_field('set','service_singular')) || (!form['employee_performance_id'] && $check_field('add','service_singular'))" :disabled="disabledObj['service_singular_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','service_singular')">{{form['service_singular']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">单</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','quality_of_service') || $check_field('add','quality_of_service') || $check_field('set','quality_of_service')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务质量" prop="quality_of_service">
											<el-select id="quality_of_service" v-model="form['quality_of_service']"
						v-if="(form['employee_performance_id'] && $check_field('set','quality_of_service')) || (!form['employee_performance_id'] && $check_field('add','quality_of_service'))">
						<el-option v-for="o in list_quality_of_service" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','quality_of_service')">{{form['quality_of_service']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','working_attitude') || $check_field('add','working_attitude') || $check_field('set','working_attitude')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="工作态度" prop="working_attitude">
											<el-select id="working_attitude" v-model="form['working_attitude']"
						v-if="(form['employee_performance_id'] && $check_field('set','working_attitude')) || (!form['employee_performance_id'] && $check_field('add','working_attitude'))">
						<el-option v-for="o in list_working_attitude" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','working_attitude')">{{form['working_attitude']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','performance_level') || $check_field('add','performance_level') || $check_field('set','performance_level')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="绩效等级" prop="performance_level">
											<el-select id="performance_level" v-model="form['performance_level']"
						v-if="(form['employee_performance_id'] && $check_field('set','performance_level')) || (!form['employee_performance_id'] && $check_field('add','performance_level'))">
						<el-option v-for="o in list_performance_level" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','performance_level')">{{form['performance_level']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','assessment_results') || $check_field('add','assessment_results') || $check_field('set','assessment_results')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="评估结果" prop="assessment_results">
															<el-input id="assessment_results" v-model="form['assessment_results']" placeholder="请输入评估结果"
							  v-if="(form['employee_performance_id'] && $check_field('set','assessment_results')) || (!form['employee_performance_id'] && $check_field('add','assessment_results'))" :disabled="disabledObj['assessment_results_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','assessment_results')">
						{{form['assessment_results']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','work_suggestion') || $check_field('add','work_suggestion') || $check_field('set','work_suggestion')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="工作建议" prop="work_suggestion">
											<el-input type="textarea" id="work_suggestion" v-model="form['work_suggestion']" placeholder="请输入工作建议"
						v-if="(form['employee_performance_id'] && $check_field('set','work_suggestion')) || (!form['employee_performance_id'] && $check_field('add','work_suggestion'))" :disabled="disabledObj['work_suggestion_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','work_suggestion')">{{form['work_suggestion']}}</div>
										</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/employee_performance/view','set') || $check_action('/employee_performance/view','add')">
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
				field: "employee_performance_id",
				url_add: "~/api/employee_performance/add?",
				url_set: "~/api/employee_performance/set?",
				url_get_obj: "~/api/employee_performance/get_obj?",
				url_get_list: "~/api/employee_performance/get_list?",
				url_upload: "~/api/employee_performance/upload?",

				query: {
					"employee_performance_id": 0,
					like: 0,
				},

				form: {
								"performance_title":  '', // 绩效标题
										"employee_name":  '', // 员工姓名
										"evaluation_time":  '', // 评估时间
										"service_singular":  0, // 服务单数
										"quality_of_service":  '', // 服务质量
										"working_attitude":  '', // 工作态度
										"performance_level":  '', // 绩效等级
										"assessment_results":  '', // 评估结果
										"work_suggestion":  '', // 工作建议
												"employee_performance_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"performance_title_isDisabled": false,
										"employee_name_isDisabled": false,
										"evaluation_time_isDisabled": false,
					          			"service_singular_isDisabled": false,
										"quality_of_service_isDisabled": false,
										"working_attitude_isDisabled": false,
										"performance_level_isDisabled": false,
										"assessment_results_isDisabled": false,
										"work_suggestion_isDisabled": false,
										},

																																					// 服务质量选项列表
				list_quality_of_service: ['优','良','中','差'],
																// 工作态度选项列表
				list_working_attitude: ['优','良','中','差'],
																// 绩效等级选项列表
				list_performance_level: ['S','A','B','C','D'],
																					

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
			        if (this.form["evaluation_time"] && JSON.stringify(this.form["evaluation_time"]).indexOf("-")===-1){
            this.form["evaluation_time"] = this.$toTime(parseInt(this.form["evaluation_time"]),"yyyy-MM-dd hh:mm:ss")
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
																					if(this.form["evaluation_time"]=="0000-00-00 00:00:00"){
				  this.form["evaluation_time"] = null;
				}
				if(parseInt(this.form["evaluation_time"]) > 9999){
					this.form["evaluation_time"] = this.$toTime(parseInt(this.form["evaluation_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																																																								
			},

																																																																											async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.employee_performance_id){
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
								if(!this.form.employee_performance_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '员工绩效',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条员工绩效数据',
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
					
																																	if (!param.evaluation_time){
					return "评估时间不能为空";
				}
																																																																																																					return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/employee_performance/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/employee_performance/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/employee_performance/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/employee_performance/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/employee_performance/view','get');
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
																				if(!this.query.employee_performance_id){
			this.form["quality_of_service"] = this.list_quality_of_service[0];
		}
						if(!this.query.employee_performance_id){
			this.form["working_attitude"] = this.list_working_attitude[0];
		}
						if(!this.query.employee_performance_id){
			this.form["performance_level"] = this.list_performance_level[0];
		}
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
