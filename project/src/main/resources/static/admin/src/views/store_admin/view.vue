<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce">
			<!-- 基本信息 -->
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="员工工号" prop="employee_work_number">
					<el-input v-model="form_sub.employee_work_number" placeholder="请输入员工工号" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="员工姓名" prop="employee_name">
					<el-input v-model="form_sub.employee_name" placeholder="请输入员工姓名" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="性别" prop="employee_gender">
					<el-select v-model="form_sub.employee_gender" placeholder="请选择性别" style="width: 100%;" :disabled="isView">
						<el-option label="男" value="男"></el-option>
						<el-option label="女" value="女"></el-option>
					</el-select>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="联系号码" prop="contact_number">
					<el-input v-model="form_sub.contact_number" placeholder="请输入联系号码" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="所属门店" prop="store_id">
					<el-select v-model="form_sub.store_id" placeholder="请选择门店" style="width: 100%;" :disabled="isView">
						<el-option label="总部" :value="0"></el-option>
						<el-option v-for="store in storeList" :key="store.store_id" :label="store.store_name" :value="store.store_id"></el-option>
					</el-select>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核状态" prop="examine_state">
					<el-tag :type="getExamineStateType(form_sub.examine_state)">{{ form_sub.examine_state || '待审核' }}</el-tag>
				</el-form-item>
			</el-col>

			<!-- 账号信息 -->
			<el-col v-if="!obj.username" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="用户名" prop="username">
					<el-input v-model="form.username" placeholder="请输入用户名"></el-input>
				</el-form-item>
			</el-col>
			<el-col v-if="obj.username" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="用户名" prop="username">
					<span>{{obj.username}}</span>
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
				<el-form-item label="用户组" prop="user_group">
					<el-input value="4S店管理员" disabled></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="账号状态" prop="state">
					<el-tag :type="form.state === 1 ? 'success' : 'info'">{{ form.state === 1 ? '启用' : '禁用' }}</el-tag>
					<el-button v-if="!isView && isEdit" size="small" :type="form.state === 1 ? 'warning' : 'success'" style="margin-left: 10px;" @click="toggleState">
						{{ form.state === 1 ? '禁用' : '启用' }}
					</el-button>
					<el-button v-if="!isView && isEdit && form_sub.examine_state === '待审核'" size="small" type="warning" style="margin-left: 10px;" @click="openExamineDialog">
						审核
					</el-button>
				</el-form-item>
			</el-col>
		</el-row>

		<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
			<el-form-item v-if="$check_action('/store_admin/view','set') || $check_action('/store_admin/view','add')">
				<el-button type="primary" @click="submit()">提交</el-button>
				<el-button @click="cancel()">取消</el-button>
			</el-form-item>
			<el-form-item v-else>
				<el-button @click="cancel()">返回</el-button>
			</el-form-item>
		</el-col>
		</el-form>

		<!-- 审核弹窗 -->
		<el-dialog title="审核信息管理员" :visible.sync="examineDialogVisible" width="500px">
			<el-form :model="examineFormData" label-width="100px">
				<el-form-item label="审核操作">
					<el-radio-group v-model="examineFormData.action">
						<el-radio label="pass">通过</el-radio>
						<el-radio label="reject">拒绝</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="审核备注">
					<el-input type="textarea" v-model="examineFormData.remark" placeholder="请输入审核备注"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="examineDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="submitExamine">确定</el-button>
			</div>
		</el-dialog>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";
	export default {
		mixins: [mixin],
		data() {
			return {
				field: "user_id",
				url_add: "~/api/user/register?",
				url_set: "~/api/user/set?",
				url_get_obj: "~/api/user/get_obj?",
				url_upload: "~/api/user/upload?",
				group_table: "store_admin",
				is_password: true,

				query: {
					"user_id": 0,
				},

				obj: {
					user_id: 0,
					username: '',
					nickname: '',
					password: '',
					user_group: "4S店管理员",
					state: 1,
				},

				form: {
					user_id: 0,
					username: '',
					nickname: '',
					password: '',
					user_group: "4S店管理员",
					state: 1,
				},

				form_sub: {
					"employee_work_number": '',
					"employee_name": '',
					"employee_gender": '男',
					"contact_number": '',
					"store_id": 0,
					"examine_state": '待审核',
					"store_admin_id": 0,
					"user_id": 0
				},

				storeList: [],
				examineDialogVisible: false,
				examineFormData: {
					action: 'pass',
					remark: ''
				}
			}
		},
		computed: {
			isView() {
				return this.$route.query.action === 'view';
			},
			isEdit() {
				return this.$route.query.store_admin_id;
			}
		},
		methods: {
			getExamineStateType(state) {
				switch(state) {
					case '待审核': return 'warning';
					case '已通过': return 'success';
					case '已拒绝': return 'danger';
					default: return 'info';
				}
			},
			loadStoreList() {
				this.$get("~/api/store/get_list", {
					size: 1000,
					page: 1
				}, (json) => {
					if (json.result) {
						this.storeList = json.result.list || [];
					}
				});
			},
			load(obj) {
				Object.assign(this.obj, obj);
				this.form.username = obj.username;
				this.form.nickname = obj.nickname;
				this.form.state = obj.state;
				this.form.user_id = obj.user_id;
			},
			get_obj_before(param) {
				var form = "";
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							if(key === dbKey){
								this.form[key] = form[dbKey]
							}
						})
					})
				}
				return param;
			},
			get_obj_after(json, func) {
				if (json.result && json.result.obj) {
					const obj = json.result.obj;
				}
				var user = json.result.obj;
				this.is_password = user.password ? false : true;
				var user_id = user.user_id;

				this.$get("~/api/" + this.group_table + "/get_obj?", {user_id}, (res) => {
					if(res.result && res.result.obj){
						var o = res.result.obj;
						delete o["create_time"];
						delete o["update_time"];
						this.form_sub = res.result.obj;
					}else if(res.error){
						console.log(res.error);
						console.log("获取不到相关信息");
					}
				});
			},
			is_view(){
				var bl = false;
				if(!bl){
					bl = this.$check_action('/store_admin/table','add');
				}
				if(!bl){
					bl = this.$check_action('/store_admin/table','set');
				}
				if(!bl){
					bl = this.$check_action('/store_admin/view','add');
				}
				if(!bl){
					bl = this.$check_action('/store_admin/view','set');
				}
				if(!bl){
					bl = this.$check_action('/store_admin/view','get');
				}
				return bl;
			},
			async submit_check(param){
				var ret = null;
				var {username, password, nickname, user_group} = param;

				if(!username){
					ret = "账号不能为空";
				}
				else if(username.length > 16 || username.length < 5){
					ret = "账号长度应为5到16个字符之间！";
				}
				else if(!password && this.is_password){
					ret = "密码不能为空!";
				}
				else if(this.is_password && (password.length > 16 || password.length < 5)){
					ret = "密码长度应为5到16个字符之间！";
				}
				else if(nickname && (nickname.length > 12 || nickname.length < 2)){
					ret = "昵称长度应为2个字符到12个字符之间";
				}

				if(!ret && !this.form_sub["employee_work_number"]){
					ret = "员工工号不能为空!";
				}

				if(!ret && this.form_sub["contact_number"]){
					let phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
					if(!phone_regular.test(this.form_sub["contact_number"])){
						return "手机号格式错误";
					}
				}

				return ret;
			},
			submit_after(json, func){
				var form = Object.assign({}, this.form);
				delete form.password;
				this.get_register(form);
			},
			get_register(form){
				var form_sub = this.form_sub;
				this.$get("~/api/user/get_obj?", form, (res) => {
					if(res.result && res.result.obj){
						form_sub.user_id = res.result.obj.user_id;
						this.submit_sub(form_sub);
					}else if(res.error){
						console.error(res.error);
						this.$toast(res.error.message, "error");
					}
				});
			},
			submit_sub(form_sub){
				if(form_sub[this.group_table + "_id"]){
					this.$post("~/api/" + this.group_table + "/set?" + this.group_table + "_id=" + form_sub[this.group_table + "_id"], form_sub, (res) => {
						if(res.result){
							this.$toast("修改成功!", "success");
							this.$router.go(-1);
						}else if(res.error){
							console.error(res.error);
							this.$toast(res.error.message, "error");
						}
					});
				}
				else{
					this.$post("~/api/" + this.group_table + "/add?", form_sub, (res) => {
						if(res.result){
							this.$toast("添加成功!", "success");
							this.$router.go(-1);
						}else if(res.error){
							var user_id = form_sub["user_id"];
							if(user_id){
								this.$get("~/api/user/del?", {user_id});
							}
							console.error(res.error);
							this.$toast(res.error.message, "error");
						}
					});
				}
			},
			cancel(){
				this.$router.go(-1);
			},
			toggleState() {
				const newState = this.form.state === 1 ? 0 : 1;
				this.$post("~/api/user/set?", {
					user_id: this.form.user_id,
					state: newState
				}, (json) => {
					if (json.result) {
						this.form.state = newState;
						this.$message({ type: 'success', message: newState === 1 ? '已启用' : '已禁用' });
					} else {
						this.$message({ type: 'error', message: json.error?.message || '操作失败' });
					}
				});
			},
			openExamineDialog() {
				this.examineFormData = { action: 'pass', remark: '' };
				this.examineDialogVisible = true;
			},
			submitExamine() {
				const state = this.examineFormData.action === 'pass' ? '已通过' : '已拒绝';
				this.$post("~/api/" + this.group_table + "/set?", {
					[this.group_table + "_id"]: this.$route.query.store_admin_id,
					examine_state: state
				}, (json) => {
					if (json.result) {
						if (this.examineFormData.action === 'pass' && this.form.user_id) {
							this.$post("~/api/user/set?", {
								user_id: this.form.user_id,
								state: 1
							});
						}
						this.form_sub.examine_state = state;
						this.$message({ type: 'success', message: '审核完成' });
						this.examineDialogVisible = false;
					} else {
						this.$message({ type: 'error', message: json.error?.message || '审核失败' });
					}
				});
			}
		},
		created() {
			this.loadStoreList();
			if(!this.$route.query.store_admin_id){
				this.form_sub["employee_gender"] = '男';
				this.form_sub["examine_state"] = "待审核";
			}
		}
	}
</script>

<style scoped>
	.bg {
		background: white;
	}
	.edit_wrap {
		padding: 20px;
	}
	.row_ce {
		display: flex;
		flex-wrap: wrap;
	}
	.el_form_item_warp {
		padding: 0 10px;
		box-sizing: border-box;
	}
	.el_form_btn_warp {
		padding: 20px 10px;
		box-sizing: border-box;
	}
</style>
