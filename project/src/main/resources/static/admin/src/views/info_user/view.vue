<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="头像" prop="avatar">
					<el-upload class="avatar-uploader" drag accept="image/gif, image/jpeg, image/png, image/jpg"
						action="" :http-request="uploadimg" :show-file-list="false">
						<img v-if="form.avatar" :src="$fullUrl(form.avatar)" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
			</el-col>

			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="账号" prop="username">
			        <span v-if="obj.username">{{obj.username}}</span>
					<el-input v-else v-model="form.username" placeholder="请输入账号"></el-input>
				</el-form-item>
			</el-col>

			<el-col v-if="!obj.password" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="密码" prop="password">
					<el-input type="password" v-model="form.password" placeholder="请输入密码" show-password></el-input>
				</el-form-item>
			</el-col>

			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="昵称" prop="nickname">
					<el-input v-model="form.nickname" placeholder="请输入昵称"></el-input>
				</el-form-item>
			</el-col>

			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="邮箱" prop="email">
					<el-input type="email" v-model="form.email" placeholder="请输入邮箱"></el-input>
				</el-form-item>
			</el-col>

			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="状态" prop="state">
					<el-select :disabled="user_group!=='管理员'" v-model="form.state" placeholder="请选择">
						<el-option v-for="group in list_user_state" :key="group.value" :label="group.name"
							:value="group.value">
						</el-option>
					</el-select>
				</el-form-item>
			</el-col>

				<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="积分" prop="integral">
					<el-input :disabled='true' type="integral" v-model="form.integral" placeholder="请输入积分"></el-input>
				</el-form-item>
			</el-col>
	
			<el-col v-if="$check_field('get','employee_work_number') || $check_field('add','employee_work_number') || $check_field('set','employee_work_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="员工工号" prop="employee_work_number">
					<el-input id="employee_work_number" v-model="form_sub['employee_work_number']" placeholder="请输入员工工号"
					  v-if="(form_sub['info_user_id'] && $check_field('set','employee_work_number')) || (!form_sub['info_user_id'] && $check_field('add','employee_work_number'))">
					</el-input>
					<div v-else-if="$check_field('get','employee_work_number')">
						{{form_sub['employee_work_number']}}
				  	</div>
				</el-form-item>
			</el-col>
			<el-col v-if="$check_field('get','employee_name') || $check_field('add','employee_name') || $check_field('set','employee_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="员工姓名" prop="employee_name">
					<el-input id="employee_name" v-model="form_sub['employee_name']" placeholder="请输入员工姓名"
					  v-if="(form_sub['info_user_id'] && $check_field('set','employee_name')) || (!form_sub['info_user_id'] && $check_field('add','employee_name'))">
				  	</el-input>
					<div v-else-if="$check_field('get','employee_name')">
						{{form_sub['employee_name']}}
				  	</div>
				</el-form-item>
			</el-col>
			<el-col v-if="$check_field('get','employee_gender') || $check_field('add','employee_gender') || $check_field('set','employee_gender')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="员工性别" prop="employee_gender">
					<el-select id="employee_gender" v-model="form_sub['employee_gender']"
						v-if="(form_sub['info_user_id'] && $check_field('set','employee_gender')) || (!form_sub['info_user_id'] && $check_field('add','employee_gender'))">
						<el-option v-for="o in list_employee_gender" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','employee_gender')">{{form_sub['employee_gender']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="$check_field('get','contact_number') || $check_field('add','contact_number') || $check_field('set','contact_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="联系号码" prop="contact_number">
					<el-input id="contact_number" v-model="form_sub['contact_number']" placeholder="请输入联系号码" type="tel"
						v-if="(form_sub['info_user_id'] && $check_field('set','contact_number')) || (!form_sub['info_user_id'] && $check_field('add','contact_number'))">
					</el-input>
					<div v-else-if="$check_field('get','contact_number')">{{form_sub['contact_number']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="$check_field('get','examine_state') || $check_field('add','examine_state') || $check_field('set','examine_state')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核状态" prop="examine_state">
					<el-select id="examine_state" v-model="form_sub['examine_state']"
						v-if="(form_sub['info_user_id'] && $check_field('set','examine_state')) || (!form_sub['info_user_id'] && $check_field('add','examine_state'))">
						<el-option v-for="o in list_examine_state" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','examine_state')">{{form_sub['examine_state']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="$check_field('get','two_dimensional_code') || $check_field('add','two_dimensional_code') || $check_field('set','two_dimensional_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="二维码" prop="two_dimensional_code">
					<el-upload :disabled="disabledObj['two_dimensional_code_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_two_dimensional_code"
						:show-file-list="false" v-if="(form_sub['info_user_id'] && $check_field('set','two_dimensional_code')) || (!form_sub['info_user_id'] && $check_field('add','two_dimensional_code'))">
						<img id="two_dimensional_code" v-if="form_sub['two_dimensional_code']" :src="$fullUrl(form_sub['two_dimensional_code'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','two_dimensional_code')" style="width: 100px; height: 100px"
						:src="$fullUrl(form_sub['two_dimensional_code'])" :preview-src-list="[$fullUrl(form_sub['two_dimensional_code'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</el-form-item>
			</el-col>
			
		</el-row>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/info_user/view','set') || $check_action('/info_user/view','add')">
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
		components: {},
		data() {
			return {
				field: "user_id",
				url_add: "~/api/user/register?",
				url_set: "~/api/user/set?",
				url_get_obj: "~/api/user/get_obj?",
				url_upload: "~/api/user/upload?",

				group_table: "info_user",
				is_password: true,

				query: {
					"user_id": 0,
				},

				obj: {
					user_id: 0,
					username: '',
					nickname: '',
					password: '',
					avatar: '',
					email: '',
					integral: 0,
					user_group: "信息员",
					state: 1,
				},

				form: {
					user_id: 0,
				    username: '',
					nickname: '',
				    password: '',
					avatar: '',
					email: '',
					integral: 0,
					user_group: "信息员",
					state: 1,
				},

				disabledObj: {
					"employee_work_number_isDisabled": false,
					"employee_name_isDisabled": false,
					"employee_gender_isDisabled": false,
					"contact_number_isDisabled": false,
					"two_dimensional_code_isDisabled": false,
				},

				form_sub: {
					"employee_work_number": '',
					"employee_name": '',
					"employee_gender": '',
					"contact_number": '',
					"examine_state": '',
					"two_dimensional_code": '',
				    "user_id": 0,
					"info_user_id": 0
				},

				list_state: [{
					value: 0,
					name: "未认证"
				}, {
					value: 1,
					name: "审核中"
				}, {
					value: 2,
					name: "已认证"
				}],

				list_user_state: [{
					value: 1,
					name: "可用"
				}, {
					value: 2,
					name: "禁用"
				}],

				list_employee_gender: ['男', '女'],
				list_examine_state: ['待审核', '已通过', '已拒绝'],
			}
		},
		computed: {
			aiForm() {
				let form = {};
				return form;
			},
		},
		methods: {
			openPreview(file) {
			    window.open(file.url)
			},
		
			upload_two_dimensional_code(param){
				let _this = this;
				var form = new FormData();
				form.append('file', param.file);
				this.$upload(this.url_upload, form, function(json) {
					if (json.result) {
						_this.form_sub['two_dimensional_code'] = json.result.url;
					} else {
						_this.$toast('上传失败！');
					}
				});
			},

			toFixed: function(num){
				if (!isNaN(num)) {
					return ((num + '').indexOf('.') == -1) ? num : num.toFixed(2);
				}
			},

			get_obj_before(param) {
				var form = "";
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
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

			async get_obj_after(json, func){
				if (json.result && json.result.obj) {
					const obj = json.result.obj;
				}
				var user = json.result.obj;
				this.is_password = user.password ? false : true;
				var user_id = user.user_id;
				this.$get("~/api/" + this.group_table + "/get_obj?" ,{user_id} ,(res)=>{
					if(res.result && res.result.obj){
						var o = res.result.obj;
						delete o["create_time"];
						delete o["update_time"];
						this.form_sub = res.result.obj;
					}else if(res.error){
						console.log(res.error);
						console.log("获取不到相关信息");
					}
				})
			},

			delImg(img, key = "img"){
				var _this = this;
				_this.form_sub[key].splice(img, 1);
			},

			async submit(param, func){
				if (!param) {
					param = this.form;
				}
				param.avatar = param.avatar == "" ? "/api/upload/default_avatar.jpg" : param.avatar;
				var pm = this.events("submit_before", Object.assign({}, param)) || param;
				var msg = await this.events("submit_check", pm);
				var ret;
				if (msg) {
					this.$toast(msg, 'danger');
				} else {
					ret = this.events("submit_main", pm, func);
				}
				return ret;
			},

			async submit_check(param){
				var ret = null;
				var email_regular = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				var {username ,password ,nickname ,user_group ,email} = param;
				var confirm_password = this.confirm_password;

				console.log("表单校验username ,password ,email ,nickname ,user_group" ,username ,password ,email ,nickname ,user_group);

				if(!username){
					ret = "账号不能为空";
				}
				else if(username.length > 16 || username.length < 5){
					ret = "账号长度应为5到16个字符之间！";
				}
				else if(!password){
					ret = "密码不能为空!";
				}
				else if(this.is_password && (password.length > 16 || password.length < 5)){
					ret = "密码长度应为5到16个字符之间！";
				}
				else if(nickname && nickname.length > 12 || nickname.length < 2){
					ret = "昵称长度应为2个字符到12个字符之间";
				}
				else if(email && !email_regular.test(email)){
					ret = "请输入正确的邮箱地址 例：test@test.com!";
				}
				else if(!user_group){
					ret = "请选择用户组!";
				}

				var p = {"username": param.username};
				var form_sub = Object.assign({} ,this.form_sub);
				if(!ret && !form_sub["employee_work_number"]){
					ret = "员工工号不能为空!";
				} else if (!ret && !form_sub["info_user_id"]) {
					let res = await this.$get("~/api/info_user/count?", {"employee_work_number": form_sub["employee_work_number"]});
					if(res.result){
						ret = "员工工号已存在!";
					}
				}
				
				let contact_number_phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if(form_sub.contact_number && !contact_number_phone_regular.test(form_sub.contact_number)){
					return "手机号格式错误"
				}
																						
				return ret;
			},

			submit_after(json,func){
				var form = Object.assign({} ,this.form);
				delete form.password;
				console.log("查询表单form",form)
				this.get_register(form);
			},

			get_register(form){
				var form_sub = this.form_sub;
				this.$get("~/api/user/get_obj?",form,(res)=>{
					console.log("注册表信息res",res);
					if(res.result && res.result.obj){
						form_sub.user_id = res.result.obj.user_id;
						this.submit_sub(form_sub);
					}else if(res.error){
						console.error(res.error);
						this.$toast(res.error.message,"error");
					}
				})
			},

			submit_sub(form_sub){
        		form_sub = this.events("submit_before", Object.assign({}, form_sub)) || form_sub;
				if(form_sub["info_user_id"]){
					this.$post("~/api/" + this.group_table + "/set?info_user_id=" + form_sub["info_user_id"],form_sub,(res)=>{
						console.log("提交结果：" ,res)
						if(res.result){
							this.$toast("修改成功!" ,"success");
							this.$router.go(-1);
						}else if(res.error){
							console.error(res.error);
							this.$toast(res.error.message,"error");
						}
					});
				}
				else{
					this.$post("~/api/" + this.group_table + "/add?",form_sub,(res)=>{
						console.log("提交结果：" ,res)
						if(res.result){
							this.$toast("添加成功!" ,"success");
							this.$router.go(-1);
						}else if(res.error){
							var user_id = form_sub["user_id"];
							this.$get("~/api/user/del?",{user_id});
							console.error(res.error);
							this.$toast(res.error.message,"error");
						}
					});
				}
			},

			is_view(){
				var bl = false;
				if(!bl){
					bl = this.$check_action('/info_user/table','add');
				}
				if(!bl){
					bl = this.$check_action('/info_user/table','set');
				}
				if(!bl){
					bl = this.$check_action('/info_user/view','add');
				}
				if(!bl){
					bl = this.$check_action('/info_user/view','set');
				}
				if(!bl){
					bl = this.$check_action('/info_user/view','get');
				}
				return bl;
			},

			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},
		},
		created() {
			if(!this.query.info_user_id){
				this.form_sub["employee_gender"] = this.list_employee_gender[0];
				this.form_sub["examine_state"] = "待审核";
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
