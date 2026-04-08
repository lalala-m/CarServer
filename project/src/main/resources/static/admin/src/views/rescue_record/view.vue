<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','rescue_code') || $check_field('add','rescue_code') || $check_field('set','rescue_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="救援编码" prop="rescue_code">
															<el-input id="rescue_code" v-model="form['rescue_code']" placeholder="请输入救援编码"
							  v-if="(form['rescue_record_id'] && $check_field('set','rescue_code')) || (!form['rescue_record_id'] && $check_field('add','rescue_code'))" :disabled="disabledObj['rescue_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','rescue_code')">
						{{form['rescue_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','business_user') || $check_field('add','business_user') || $check_field('set','business_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商家用户" prop="business_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_business_user(form['business_user']) }}
							<el-select v-if="(form['rescue_record_id'] && $check_field('set','business_user')) || (!form['rescue_record_id'] && $check_field('add','business_user'))" id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
								<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','business_user')" id="business_user" v-model="form['business_user']" :disabled="true">
								<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
							<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','rescue_user') || $check_field('add','rescue_user') || $check_field('set','rescue_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="救援用户" prop="rescue_user">
																<el-select v-if="(form['rescue_record_id'] && $check_field('set','rescue_user')) || (!form['rescue_record_id'] && $check_field('add','rescue_user'))" id="rescue_user" v-model="form['rescue_user']" :disabled="disabledObj['rescue_user_isDisabled']">
							<el-option v-for="o in list_user_rescue_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','rescue_user')" id="rescue_user" v-model="form['rescue_user']" :disabled="true">
							<el-option v-for="o in list_user_rescue_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owners_name') || $check_field('add','owners_name') || $check_field('set','owners_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主姓名" prop="owners_name">
											<el-cascader id="owners_name" v-model="form['owners_name']" :options="owners_name_tree" :props="owners_name_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择车主姓名" @change="select_owners_name_field"
      					v-if="(form['rescue_record_id'] && $check_field('set', 'owners_name')) || (!form['rescue_record_id'] && $check_field('add', 'owners_name'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','owners_name')">{{form['owners_name']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','contact_number') || $check_field('add','contact_number') || $check_field('set','contact_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="联系号码" prop="contact_number">
											<el-input id="contact_number" v-model="form['contact_number']"
						v-if="(form['rescue_record_id'] && $check_field('set','contact_number')) || (!form['rescue_record_id'] && $check_field('add','contact_number'))" :disabled="true">
									</el-input>
					<div v-else-if="$check_field('get','contact_number')">
						{{form['contact_number']}}
									</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','audit_status') || $check_field('add','audit_status') || $check_field('set','audit_status')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="审核状态" prop="audit_status">
											<el-select id="audit_status" v-model="form['audit_status']"
						v-if="(form['rescue_record_id'] && $check_field('set','audit_status')) || (!form['rescue_record_id'] && $check_field('add','audit_status'))">
						<el-option v-for="o in list_audit_status" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','audit_status')">{{form['audit_status']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','maintenance_personnel') || $check_field('add','maintenance_personnel') || $check_field('set','maintenance_personnel')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="维修人员" prop="maintenance_personnel">
															<el-input id="maintenance_personnel" v-model="form['maintenance_personnel']" placeholder="请输入维修人员"
							  v-if="(form['rescue_record_id'] && $check_field('set','maintenance_personnel')) || (!form['rescue_record_id'] && $check_field('add','maintenance_personnel'))" :disabled="disabledObj['maintenance_personnel_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','maintenance_personnel')">
						{{form['maintenance_personnel']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','maintenance_time') || $check_field('add','maintenance_time') || $check_field('set','maintenance_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="维修时间" prop="maintenance_time">
											<el-date-picker :disabled="disabledObj['maintenance_time_isDisabled']" v-if="(form['rescue_record_id'] && $check_field('set','maintenance_time')) || (!form['rescue_record_id'] && $check_field('add','maintenance_time'))" id="maintenance_time"
						v-model="form['maintenance_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','maintenance_time')">{{form['maintenance_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','scene_picture') || $check_field('add','scene_picture') || $check_field('set','scene_picture')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="现场图片" prop="scene_picture">
											<el-upload :disabled="disabledObj['scene_picture_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_scene_picture"
						:show-file-list="false" v-if="(form['rescue_record_id'] && $check_field('set','scene_picture')) || (!form['rescue_record_id'] && $check_field('add','scene_picture'))">
						<img id="scene_picture" v-if="form['scene_picture']" :src="$fullUrl(form['scene_picture'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','scene_picture')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['scene_picture'])" :preview-src-list="[$fullUrl(form['scene_picture'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','maintenance') || $check_field('add','maintenance') || $check_field('set','maintenance')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="维修情况" prop="maintenance">
											<el-input type="textarea" id="maintenance" v-model="form['maintenance']" placeholder="请输入维修情况"
						v-if="(form['rescue_record_id'] && $check_field('set','maintenance')) || (!form['rescue_record_id'] && $check_field('add','maintenance'))" :disabled="disabledObj['maintenance_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','maintenance')">{{form['maintenance']}}</div>
										</el-form-item>
			</el-col>
									
	
	
				<el-col
					v-if="$check_action('/rescue_record/view', 'set') || $check_action('/rescue_record/view', 'add')"
					:xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="当前位置" prop="location_address">
					<el-input style="width: 80%" id="location_address" v-model="form['location_address']"
							  :disabled="true"></el-input>
					<el-button size="mini" @click="getLongitudeLatitude()">定位</el-button>
				</el-form-item>
			</el-col>
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/rescue_record/view','set') || $check_action('/rescue_record/view','add')">
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
				field: "rescue_record_id",
				url_add: "~/api/rescue_record/add?",
				url_set: "~/api/rescue_record/set?",
				url_get_obj: "~/api/rescue_record/get_obj?",
				url_get_list: "~/api/rescue_record/get_list?",
				url_upload: "~/api/rescue_record/upload?",

				query: {
					"rescue_record_id": 0,
					like: 0,
				},

				form: {
								"rescue_code":  '', // 救援编码
										"business_user": 0, // 商家用户
										"rescue_user": 0, // 救援用户
										"owners_name":  '', // 车主姓名
										"contact_number":  '', // 联系号码
										"audit_status":  '', // 审核状态
										"maintenance_personnel":  '', // 维修人员
										"maintenance_time":  '', // 维修时间
										"scene_picture":  '', // 现场图片
										"maintenance":  '', // 维修情况
												"rescue_record_id": 0, // ID
							"location_address": "", // 定位地址
					"location_lng": "", // 定位地址经度
					"location_lat": "", // 定位地址纬度
																	"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"rescue_code_isDisabled": false,
										"business_user_isDisabled": false,
										"rescue_user_isDisabled": false,
										"owners_name_isDisabled": false,
										"contact_number_isDisabled": false,
										"audit_status_isDisabled": false,
										"maintenance_personnel_isDisabled": false,
										"maintenance_time_isDisabled": false,
										"scene_picture_isDisabled": false,
										"maintenance_isDisabled": false,
										},

														// 用户列表
				list_user_business_user: [],
						// 用户组
				group_user_business_user: "",
														// 用户列表
				list_user_rescue_user: [],
																owners_name_cascader: {
					value: 'owners_name',
					label: 'owners_name',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				owners_name_tree: [],
						// 车主姓名选项列表
				list_owners_name: [""],
																							// 审核状态选项列表
				list_audit_status: ['待审核','已审核'],
																																			

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
					/**
			 * 获取商家用户用户组
			 */
			async get_group_user_business_user() {
							this.form["business_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=商家用户");
				if(json.result && json.result.obj){
					this.group_user_business_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_business_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_business_user.source_table+"/get_obj?"
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
												_this.form["business_user"] = id
									_this.disabledObj['business_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "business_user" && arr[i] !== "create_by") {
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
			async get_list_user_rescue_user() {
                var json = await this.$get("~/api/user/get_list?user_group=车主用户");
                if(json.result && json.result.list){
                    this.list_user_rescue_user = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_rescue_user(id){
				var obj = this.list_user_rescue_user.getObj({"user_id":id});
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
			 * 获取车主姓名列表
			 */
						async get_list_owners_name() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/owner_user/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.owners_name_tree = this.build_owners_name_tree(json.result.list, 0);
					} else {
						this.owners_name_tree = json.result.list;
					}
					if (!this.query.rescue_record_id && this.owners_name_tree.length > 0) {
										this.form["owners_name"] = this.owners_name_tree[0]["owners_name"];
												this.select_owners_name_field(this.form["owners_name"])
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
								select_owners_name_field(v){
				this.$get('~/api/owner_user/get_obj?owners_name='+v,{},(res)=>{
					if(res.result && res.result.obj){
																																														this.form.contact_number = res.result.obj.contact_number;
																																																				}else{
						console.error(res.error);
					}
				})
			},
									build_owners_name_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_owners_name_tree(list, item.owner_user_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			
					
	
					
	
					
	
					
	
							/**
			 * 上传现场图片
			 * @param {Object} param 图片参数
			 */
			upload_scene_picture(param){
									this.uploadFile(param.file, "scene_picture");
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
								        if (this.form["maintenance_time"] && JSON.stringify(this.form["maintenance_time"]).indexOf("-")===-1){
            this.form["maintenance_time"] = this.$toTime(parseInt(this.form["maintenance_time"]),"yyyy-MM-dd hh:mm:ss")
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
																																																													if(this.form["maintenance_time"]=="0000-00-00 00:00:00"){
				  this.form["maintenance_time"] = null;
				}
				if(parseInt(this.form["maintenance_time"]) > 9999){
					this.form["maintenance_time"] = this.$toTime(parseInt(this.form["maintenance_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																								
			},

																																																																																		async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.rescue_record_id){
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
								if(!this.form.rescue_record_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '救援记录',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条救援记录数据',
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
					
																																																																																																		if (!param.maintenance_time){
					return "维修时间不能为空";
				}
																																																	return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/rescue_record/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/rescue_record/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/rescue_record/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/rescue_record/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/rescue_record/view','get');
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
			getLongitudeLatitude() {
				let _this = this;
				_this.form.location_address = "广东省深圳市南山区科技园";
				_this.form.location_lng = "113.946782";
				_this.form.location_lat = "22.556481";
				//如果该对象存在，那么地理位置服务可用。
				if ('geolocation' in navigator) {
					/* 地理位置服务可用 */
					let options = {
						enableHighAccuracy: true, //布尔值，表示系统是否使用最高精度来表示结果，注意，这会导致较慢的响应时间或者增加电量消耗（比如对于支持gps的移动设备来说）。如果值为false ，设备会通过更快响应以及/或者使用更少的电量等方法来尽可能的节约资源。默认值fasle
						timeout: 5000, //它表明的是设备必须在多长时间（单位毫秒）内返回一个位置。默认直到获取到位置才会返回值。
						maximumAge: 0 //表明可以返回多长时间（即最长年龄，单位毫秒）内的可获取的缓存位置。如果设置为 0, 说明设备不能使用一个缓存位置，而且必须去获取一个真实的当前位置。默认0
					}
					function success(position) {
						//position.coords (只读) 返回一个定义了当前位置的Coordinates对象。
						//position.timestamp (只读) 返回一个时间戳DOMTimeStamp， 这个时间戳表示获取到的位置的时间。
						let lat = position.coords.latitude //当前位置的纬度
						let lng = position.coords.longitude //当前位置精度
						let location=lng+","+lat;
						let url = "http://restapi.amap.com/v3/geocode/regeo?key=bcafb11917e1c0c24268fa0e3c69aa5b&location="+location;
						_this.$axios.get(url, {
						}).then((res) => {
							if (res.status===200){
								let data = res.data;
								if (data.status==='1'){
									_this.form.location_address = data.regeocode.formatted_address;
									_this.form.location_lng = JSON.stringify(lng);
									_this.form.location_lat = JSON.stringify(lat);
								}else {
									console.log(data.info)
								}
							}
						}).catch((res) => {
							console.log(res);
						});
					}
					function error(err) {
						let errorType = ['您拒绝共享位置信息', '获取不到位置信息', '获取位置信息超时']
						console.log(errorType[err.code - 1])
					}
					navigator.geolocation.getCurrentPosition(success, error, options)
				} else {
					/* 地理位置服务不可用 */
					console.log('无法获取您的位置，请检查定位是否开启或刷新重试')
				}
			},
																																																					},
		created() {
									this.get_list_user_business_user();
					this.get_group_user_business_user();
										this.get_list_user_rescue_user();
									this.get_list_owners_name();
												if(!this.query.rescue_record_id){
			this.form["audit_status"] = this.list_audit_status[0];
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
