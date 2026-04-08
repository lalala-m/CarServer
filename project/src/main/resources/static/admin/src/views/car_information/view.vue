<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','car_coding') || $check_field('add','car_coding') || $check_field('set','car_coding')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车编码" prop="car_coding">
															<el-input id="car_coding" v-model="form['car_coding']" placeholder="请输入汽车编码"
							  v-if="(form['car_information_id'] && $check_field('set','car_coding')) || (!form['car_information_id'] && $check_field('add','car_coding'))" :disabled="disabledObj['car_coding_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_coding')">
						{{form['car_coding']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_name') || $check_field('add','car_name') || $check_field('set','car_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车名称" prop="car_name">
															<el-input id="car_name" v-model="form['car_name']" placeholder="请输入汽车名称"
							  v-if="(form['car_information_id'] && $check_field('set','car_name')) || (!form['car_information_id'] && $check_field('add','car_name'))" :disabled="disabledObj['car_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_name')">
						{{form['car_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_condition_picture') || $check_field('add','car_condition_picture') || $check_field('set','car_condition_picture')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车况图片" prop="car_condition_picture">
											<el-upload :disabled="disabledObj['car_condition_picture_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_car_condition_picture"
						:show-file-list="false" v-if="(form['car_information_id'] && $check_field('set','car_condition_picture')) || (!form['car_information_id'] && $check_field('add','car_condition_picture'))">
						<img id="car_condition_picture" v-if="form['car_condition_picture']" :src="$fullUrl(form['car_condition_picture'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','car_condition_picture')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['car_condition_picture'])" :preview-src-list="[$fullUrl(form['car_condition_picture'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_models') || $check_field('add','car_models') || $check_field('set','car_models')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车车型" prop="car_models">
											<el-cascader id="car_models" v-model="form['car_models']" :options="car_models_tree" :props="car_models_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择汽车车型" 
      					v-if="(form['car_information_id'] && $check_field('set', 'car_models')) || (!form['car_information_id'] && $check_field('add', 'car_models'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','car_models')">{{form['car_models']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_brand') || $check_field('add','car_brand') || $check_field('set','car_brand')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车品牌" prop="car_brand">
											<el-cascader id="car_brand" v-model="form['car_brand']" :options="car_brand_tree" :props="car_brand_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择汽车品牌" 
      					v-if="(form['car_information_id'] && $check_field('set', 'car_brand')) || (!form['car_information_id'] && $check_field('add', 'car_brand'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','car_brand')">{{form['car_brand']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','new_and_old_car') || $check_field('add','new_and_old_car') || $check_field('set','new_and_old_car')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车新旧" prop="new_and_old_car">
											<el-select id="new_and_old_car" v-model="form['new_and_old_car']"
						v-if="(form['car_information_id'] && $check_field('set','new_and_old_car')) || (!form['car_information_id'] && $check_field('add','new_and_old_car'))">
						<el-option v-for="o in list_new_and_old_car" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','new_and_old_car')">{{form['new_and_old_car']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_price') || $check_field('add','car_price') || $check_field('set','car_price')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车价格" prop="car_price">
											<el-input-number id="car_price" v-model.number="form['car_price']"
						v-if="(form['car_information_id'] && $check_field('set','car_price')) || (!form['car_information_id'] && $check_field('add','car_price'))" :disabled="disabledObj['car_price_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','car_price')">{{form['car_price']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">元</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_status') || $check_field('add','car_status') || $check_field('set','car_status')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车状态" prop="car_status">
											<el-select id="car_status" v-model="form['car_status']"
						v-if="(form['car_information_id'] && $check_field('set','car_status')) || (!form['car_information_id'] && $check_field('add','car_status'))">
						<el-option v-for="o in list_car_status" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','car_status')">{{form['car_status']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','business_user') || $check_field('add','business_user') || $check_field('set','business_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商家用户" prop="business_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_business_user(form['business_user']) }}
							<el-select v-if="(form['car_information_id'] && $check_field('set','business_user')) || (!form['car_information_id'] && $check_field('add','business_user'))" id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
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
																<el-col v-if="$check_field('get','store_location') || $check_field('add','store_location') || $check_field('set','store_location')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="门店位置" prop="store_location">
															<el-input id="store_location" v-model="form['store_location']" placeholder="请输入门店位置"
							  v-if="(form['car_information_id'] && $check_field('set','store_location')) || (!form['car_information_id'] && $check_field('add','store_location'))" :disabled="disabledObj['store_location_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','store_location')">
						{{form['store_location']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_configuration') || $check_field('add','car_configuration') || $check_field('set','car_configuration')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车配置" prop="car_configuration">
											<el-input type="textarea" id="car_configuration" v-model="form['car_configuration']" placeholder="请输入汽车配置"
						v-if="(form['car_information_id'] && $check_field('set','car_configuration')) || (!form['car_information_id'] && $check_field('add','car_configuration'))" :disabled="disabledObj['car_configuration_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','car_configuration')">{{form['car_configuration']}}</div>
										</el-form-item>
			</el-col>
													<el-col v-if="$check_field('get','car_specificss') || $check_field('add','car_specificss') || $check_field('set','car_specificss')" :xs="24" :sm="24" :lg="24" class="el_form_editor_warp">
							<el-form-item label="汽车详情" prop="car_specificss">
								<!-- <quill-editor v-model.number="form['car_specificss']"
						v-if="(form['car_information_id'] && $check_field('set','car_specificss')) || (!form['car_information_id'] && $check_field('add','car_specificss')) ">
					</quill-editor> -->
					<ai_editor
						v-if="(form['car_information_id'] && $check_field('set','car_specificss')) || (!form['car_information_id'] && $check_field('add','car_specificss'))"
						class="editor el_form_editor_warp"
						:details="form.car_specificss"
						:form="form"
						@blur_comment="blur_comment_car_specificss"
						@update_comment="update_comment_car_specificss"
						:publish_flag="false" />
					<div v-else-if="$check_field('get','car_specificss')" v-html="form['car_specificss']"></div>
				</el-form-item>
			</el-col>
						
	
	
	
		
		
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="试驾限次">
				<el-input id="limit_times" v-model="form['test_drive_record_limit_times']" placeholder="试驾限制次数，0为不限"
						  v-if="$check_option('/car_information/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/car_information/view','get')" v-html="form['test_drive_record_limit_times']"></div>
			  </el-form-item>
			</el-col>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 0 > 0">
			  <el-form-item label="购买限次">
				<el-input id="limit_times" v-model="form['purchase_order_limit_times']" placeholder="购买限制次数，0为不限"
						  v-if="$check_option('/car_information/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/car_information/view','get')" v-html="form['purchase_order_limit_times']"></div>
			  </el-form-item>
			</el-col>
			
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/car_information/view','set') || $check_action('/car_information/view','add')">
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
				field: "car_information_id",
				url_add: "~/api/car_information/add?",
				url_set: "~/api/car_information/set?",
				url_get_obj: "~/api/car_information/get_obj?",
				url_get_list: "~/api/car_information/get_list?",
				url_upload: "~/api/car_information/upload?",

				query: {
					"car_information_id": 0,
					like: 0,
				},

				form: {
								"car_coding":  '', // 汽车编码
										"car_name":  '', // 汽车名称
										"car_condition_picture":  '', // 车况图片
										"car_models":  '', // 汽车车型
										"car_brand":  '', // 汽车品牌
										"new_and_old_car":  '', // 汽车新旧
										"car_price":  0, // 汽车价格
										"car_status":  '', // 汽车状态
										"business_user": 0, // 商家用户
										"store_location":  '', // 门店位置
										"car_configuration":  '', // 汽车配置
										"car_specificss":  '', // 汽车详情
												"car_information_id": 0, // ID
													"test_drive_record_limit_times": 0, // 试驾限制次数
							"purchase_order_limit_times": 0, // 购买限制次数
															"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"car_coding_isDisabled": false,
										"car_name_isDisabled": false,
										"car_condition_picture_isDisabled": false,
										"car_models_isDisabled": false,
										"car_brand_isDisabled": false,
										"new_and_old_car_isDisabled": false,
					          			"car_price_isDisabled": false,
										"car_status_isDisabled": false,
										"business_user_isDisabled": false,
										"store_location_isDisabled": false,
										"car_configuration_isDisabled": false,
										"car_specificss_isDisabled": false,
										},

																														car_models_cascader: {
					value: 'model_name',
					label: 'model_name',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				car_models_tree: [],
						// 汽车车型选项列表
				list_car_models: [""],
																car_brand_cascader: {
					value: 'brand_name',
					label: 'brand_name',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				car_brand_tree: [],
						// 汽车品牌选项列表
				list_car_brand: [""],
																// 汽车新旧选项列表
				list_new_and_old_car: ['新车','二手车'],
																							// 汽车状态选项列表
				list_car_status: ['空闲中','试驾中'],
														// 用户列表
				list_user_business_user: [],
						// 用户组
				group_user_business_user: "",
																												

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
			 * 上传车况图片
			 * @param {Object} param 图片参数
			 */
			upload_car_condition_picture(param){
									this.uploadFile(param.file, "car_condition_picture");
								},
		
	
					
							/**
			 * 获取汽车车型列表
			 */
						async get_list_car_models() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/vehicle_class_nameification/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.car_models_tree = this.build_car_models_tree(json.result.list, 0);
					} else {
						this.car_models_tree = json.result.list;
					}
					if (!this.query.car_information_id && this.car_models_tree.length > 0) {
										this.form["car_models"] = this.car_models_tree[0]["model_name"];
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
												build_car_models_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_car_models_tree(list, item.vehicle_class_nameification_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			
					
							/**
			 * 获取汽车品牌列表
			 */
						async get_list_car_brand() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/brand_class_nameification/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.car_brand_tree = this.build_car_brand_tree(json.result.list, 0);
					} else {
						this.car_brand_tree = json.result.list;
					}
					if (!this.query.car_information_id && this.car_brand_tree.length > 0) {
										this.form["car_brand"] = this.car_brand_tree[0]["brand_name"];
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
												build_car_brand_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_car_brand_tree(list, item.brand_class_nameification_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
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
				if(!this.form.car_information_id){
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
								if(!this.form.car_information_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '汽车信息',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条汽车信息数据',
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
					bl = this.$check_action('/car_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/car_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/car_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/car_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/car_information/view','get');
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
																																																													blur_comment_car_specificss(data) {
				this.form['car_specificss'] = data;
			},
			update_comment_car_specificss(data) {
				this.form['car_specificss'] = data;
			},
							},
		created() {
																this.get_list_car_models();
								this.get_list_car_brand();
								if(!this.query.car_information_id){
			this.form["new_and_old_car"] = this.list_new_and_old_car[0];
		}
										if(!this.query.car_information_id){
			this.form["car_status"] = this.list_car_status[0];
		}
							this.get_list_user_business_user();
					this.get_group_user_business_user();
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
