<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','booking_code') || $check_field('add','booking_code') || $check_field('set','booking_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="预约编码" prop="booking_code">
															<el-input id="booking_code" v-model="form['booking_code']" placeholder="请输入预约编码"
							  v-if="(form['reservation_record_id'] && $check_field('set','booking_code')) || (!form['reservation_record_id'] && $check_field('add','booking_code'))" :disabled="true">
				  					</el-input>
					<div v-else-if="$check_field('get','booking_code')">
						{{form['booking_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_code') || $check_field('add','service_code') || $check_field('set','service_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务编码" prop="service_code">
															<el-input id="service_code" v-model="form['service_code']" placeholder="请输入服务编码"
							  v-if="(form['reservation_record_id'] && $check_field('set','service_code')) || (!form['reservation_record_id'] && $check_field('add','service_code'))" :disabled="disabledObj['service_code_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','service_code')">
						{{form['service_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_name') || $check_field('add','service_name') || $check_field('set','service_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务名称" prop="service_name">
															<el-input id="service_name" v-model="form['service_name']" placeholder="请输入服务名称"
							  v-if="(form['reservation_record_id'] && $check_field('set','service_name')) || (!form['reservation_record_id'] && $check_field('add','service_name'))" :disabled="disabledObj['service_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','service_name')">
						{{form['service_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','service_price') || $check_field('add','service_price') || $check_field('set','service_price')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="服务价格" prop="service_price">
											<el-input-number id="service_price" v-model.number="form['service_price']"
						v-if="(form['reservation_record_id'] && $check_field('set','service_price')) || (!form['reservation_record_id'] && $check_field('add','service_price'))" :disabled="disabledObj['service_price_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','service_price')">{{form['service_price']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">元</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owner_user') || $check_field('add','owner_user') || $check_field('set','owner_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主用户" prop="owner_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_owner_user(form['owner_user']) }}
							<el-select v-if="(form['reservation_record_id'] && $check_field('set','owner_user')) || (!form['reservation_record_id'] && $check_field('add','owner_user'))" id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
								<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','owner_user')" id="owner_user" v-model="form['owner_user']" :disabled="true">
								<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
							<el-option v-for="o in list_user_owner_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owners_name') || $check_field('add','owners_name') || $check_field('set','owners_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主姓名" prop="owners_name">
															<el-input id="owners_name" v-model="form['owners_name']" placeholder="请输入车主姓名"
							  v-if="(form['reservation_record_id'] && $check_field('set','owners_name')) || (!form['reservation_record_id'] && $check_field('add','owners_name'))" :disabled="disabledObj['owners_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','owners_name')">
						{{form['owners_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','contact_number') || $check_field('add','contact_number') || $check_field('set','contact_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="联系号码" prop="contact_number">
															<el-input id="contact_number" v-model="form['contact_number']" placeholder="请输入联系号码"
							  v-if="(form['reservation_record_id'] && $check_field('set','contact_number')) || (!form['reservation_record_id'] && $check_field('add','contact_number'))" :disabled="disabledObj['contact_number_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','contact_number')">
						{{form['contact_number']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','appointment_date') || $check_field('add','appointment_date') || $check_field('set','appointment_date')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="预约日期" prop="appointment_date">
											<el-date-picker :disabled="disabledObj['appointment_date_isDisabled']" v-if="(form['reservation_record_id'] && $check_field('set','appointment_date')) || (!form['reservation_record_id'] && $check_field('add','appointment_date'))" id="appointment_date"
						v-model="form['appointment_date']" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
					</el-date-picker>
					<div v-else-if="$check_field('get','appointment_date')">{{form['appointment_date']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','appointment_period') || $check_field('add','appointment_period') || $check_field('set','appointment_period')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="预约时段" prop="appointment_period">
											<el-cascader id="appointment_period" v-model="form['appointment_period']" :options="appointment_period_tree" :props="appointment_period_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择预约时段" 
      					v-if="(form['reservation_record_id'] && $check_field('set', 'appointment_period')) || (!form['reservation_record_id'] && $check_field('add', 'appointment_period'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','appointment_period')">{{form['appointment_period']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','number_of_appointments') || $check_field('add','number_of_appointments') || $check_field('set','number_of_appointments')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="预约次数" prop="number_of_appointments">
											<el-select id="number_of_appointments" v-model="form['number_of_appointments']"
						v-if="(form['reservation_record_id'] && $check_field('set','number_of_appointments')) || (!form['reservation_record_id'] && $check_field('add','number_of_appointments'))">
						<el-option v-for="o in list_number_of_appointments" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','number_of_appointments')">{{form['number_of_appointments']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','delivery_time') || $check_field('add','delivery_time') || $check_field('set','delivery_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="交车时间" prop="delivery_time">
											<el-date-picker :disabled="disabledObj['delivery_time_isDisabled']" v-if="(form['reservation_record_id'] && $check_field('set','delivery_time')) || (!form['reservation_record_id'] && $check_field('add','delivery_time'))" id="delivery_time"
						v-model="form['delivery_time']" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
					</el-date-picker>
					<div v-else-if="$check_field('get','delivery_time')">{{form['delivery_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','sales_consultant') || $check_field('add','sales_consultant') || $check_field('set','sales_consultant')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="销售顾问" prop="sales_consultant">
															<el-input id="sales_consultant" v-model="form['sales_consultant']" placeholder="请输入销售顾问"
							  v-if="(form['reservation_record_id'] && $check_field('set','sales_consultant')) || (!form['reservation_record_id'] && $check_field('add','sales_consultant'))" :disabled="disabledObj['sales_consultant_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','sales_consultant')">
						{{form['sales_consultant']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','license_plate_number') || $check_field('add','license_plate_number') || $check_field('set','license_plate_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车牌号码" prop="license_plate_number">
											<el-cascader id="license_plate_number" v-model="form['license_plate_number']" :options="license_plate_number_tree" :props="license_plate_number_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择车牌号码" @change="select_license_plate_number_field"
      					v-if="(form['reservation_record_id'] && $check_field('set', 'license_plate_number')) || (!form['reservation_record_id'] && $check_field('add', 'license_plate_number'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','license_plate_number')">{{form['license_plate_number']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','frame_number') || $check_field('add','frame_number') || $check_field('set','frame_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车架号" prop="frame_number">
											<el-input id="frame_number" v-model="form['frame_number']"
						v-if="(form['reservation_record_id'] && $check_field('set','frame_number')) || (!form['reservation_record_id'] && $check_field('add','frame_number'))" :disabled="true">
									</el-input>
					<div v-else-if="$check_field('get','frame_number')">
						{{form['frame_number']}}
									</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_type') || $check_field('add','car_type') || $check_field('set','car_type')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车类型" prop="car_type">
											<el-input id="car_type" v-model="form['car_type']"
						v-if="(form['reservation_record_id'] && $check_field('set','car_type')) || (!form['reservation_record_id'] && $check_field('add','car_type'))" :disabled="true">
									</el-input>
					<div v-else-if="$check_field('get','car_type')">
						{{form['car_type']}}
									</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','appointment_remarks') || $check_field('add','appointment_remarks') || $check_field('set','appointment_remarks')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="预约备注" prop="appointment_remarks">
											<el-input type="textarea" id="appointment_remarks" v-model="form['appointment_remarks']" placeholder="请输入预约备注"
						v-if="(form['reservation_record_id'] && $check_field('set','appointment_remarks')) || (!form['reservation_record_id'] && $check_field('add','appointment_remarks'))" :disabled="disabledObj['appointment_remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','appointment_remarks')">{{form['appointment_remarks']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','sales_manager') || $check_field('add','sales_manager') || $check_field('set','sales_manager')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="销售经理" prop="sales_manager">
																<el-select v-if="(form['reservation_record_id'] && $check_field('set','sales_manager')) || (!form['reservation_record_id'] && $check_field('add','sales_manager'))" id="sales_manager" v-model="form['sales_manager']" :disabled="disabledObj['sales_manager_isDisabled']">
							<el-option v-for="o in list_user_sales_manager" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','sales_manager')" id="sales_manager" v-model="form['sales_manager']" :disabled="true">
							<el-option v-for="o in list_user_sales_manager" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','account_manager') || $check_field('add','account_manager') || $check_field('set','account_manager')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="客户经理" prop="account_manager">
																<el-select v-if="(form['reservation_record_id'] && $check_field('set','account_manager')) || (!form['reservation_record_id'] && $check_field('add','account_manager'))" id="account_manager" v-model="form['account_manager']" :disabled="disabledObj['account_manager_isDisabled']">
							<el-option v-for="o in list_user_account_manager" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','account_manager')" id="account_manager" v-model="form['account_manager']" :disabled="true">
							<el-option v-for="o in list_user_account_manager" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/reservation_record/view','set') || $check_action('/reservation_record/view','add')">
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
				field: "reservation_record_id",
				url_add: "~/api/reservation_record/add?",
				url_set: "~/api/reservation_record/set?",
				url_get_obj: "~/api/reservation_record/get_obj?",
				url_get_list: "~/api/reservation_record/get_list?",
				url_upload: "~/api/reservation_record/upload?",

				query: {
					"reservation_record_id": 0,
					like: 0,
				},

				form: {
								"booking_code": this.$get_stamp(), // 预约编码
										"service_code":  '', // 服务编码
										"service_name":  '', // 服务名称
										"service_price":  0, // 服务价格
										"owner_user": 0, // 车主用户
										"owners_name":  '', // 车主姓名
										"contact_number":  '', // 联系号码
										"appointment_date":  '', // 预约日期
										"appointment_period":  '', // 预约时段
										"number_of_appointments":  '', // 预约次数
										"delivery_time":  '', // 交车时间
										"sales_consultant":  '', // 销售顾问
										"license_plate_number":  '', // 车牌号码
										"frame_number":  '', // 车架号
										"car_type":  '', // 汽车类型
										"appointment_remarks":  '', // 预约备注
										"sales_manager": 0, // 销售经理
										"account_manager": 0, // 客户经理
												"reservation_record_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"booking_code_isDisabled": false,
										"service_code_isDisabled": false,
										"service_name_isDisabled": false,
					          			"service_price_isDisabled": false,
										"owner_user_isDisabled": false,
										"owners_name_isDisabled": false,
										"contact_number_isDisabled": false,
										"appointment_date_isDisabled": false,
										"appointment_period_isDisabled": false,
										"number_of_appointments_isDisabled": false,
										"delivery_time_isDisabled": false,
										"sales_consultant_isDisabled": false,
										"license_plate_number_isDisabled": false,
										"frame_number_isDisabled": false,
										"car_type_isDisabled": false,
										"appointment_remarks_isDisabled": false,
										"sales_manager_isDisabled": false,
										"account_manager_isDisabled": false,
										},

																																			// 用户列表
				list_user_owner_user: [],
						// 用户组
				group_user_owner_user: "",
																																					appointment_period_cascader: {
					value: 'time_period',
					label: 'time_period',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				appointment_period_tree: [],
						// 预约时段选项列表
				list_appointment_period: [""],
																// 预约次数选项列表
				list_number_of_appointments: ['1'],
																														license_plate_number_cascader: {
					value: 'license_plate_number',
					label: 'license_plate_number',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				license_plate_number_tree: [],
						// 车牌号码选项列表
				list_license_plate_number: [""],
																																			// 用户列表
				list_user_sales_manager: [],
														// 用户列表
				list_user_account_manager: [],
							

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
					/**
			 * 获取车主用户用户组
			 */
			async get_group_user_owner_user() {
							this.form["owner_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=车主用户");
				if(json.result && json.result.obj){
					this.group_user_owner_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_owner_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_owner_user.source_table+"/get_obj?"
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
												_this.form["owner_user"] = id
									_this.disabledObj['owner_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "owner_user" && arr[i] !== "create_by") {
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
			 * 获取预约时段列表
			 */
						async get_list_appointment_period() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/period_class_nameification/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.appointment_period_tree = this.build_appointment_period_tree(json.result.list, 0);
					} else {
						this.appointment_period_tree = json.result.list;
					}
					if (!this.query.reservation_record_id && this.appointment_period_tree.length > 0) {
										this.form["appointment_period"] = this.appointment_period_tree[0]["time_period"];
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
												build_appointment_period_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_appointment_period_tree(list, item.period_class_nameification_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			
					
	
					
	
					
	
					
							/**
			 * 获取车牌号码列表
			 */
						async get_list_license_plate_number() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  					var user_group = this.user.user_group;
								let sqlwhere = "(";
				  										if(user_group=="车主用户"){
						sqlwhere+= "owner_user = " + this.user.user_id + " or ";
					}
									  									  									  									  									  					if (sqlwhere.length>1){
						sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
						sqlwhere += ")";
						param["sqlwhere"] = sqlwhere;
					}
											  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/vehicle_information/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.license_plate_number_tree = this.build_license_plate_number_tree(json.result.list, 0);
					} else {
						this.license_plate_number_tree = json.result.list;
					}
					if (!this.query.reservation_record_id && this.license_plate_number_tree.length > 0) {
										this.form["license_plate_number"] = this.license_plate_number_tree[0]["license_plate_number"];
												this.select_license_plate_number_field(this.form["license_plate_number"])
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
								select_license_plate_number_field(v){
				this.$get('~/api/vehicle_information/get_obj?license_plate_number='+v,{},(res)=>{
					if(res.result && res.result.obj){
																																																																																																													this.form.frame_number = res.result.obj.frame_number;
																											this.form.car_type = res.result.obj.car_type;
																																						}else{
						console.error(res.error);
					}
				})
			},
									build_license_plate_number_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_license_plate_number_tree(list, item.vehicle_information_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
			},
			
					
	
					
	
					
	
					
	
				/**
			 * 获取销售经理用户列表
			 */
			async get_list_user_sales_manager() {
                var json = await this.$get("~/api/user/get_list?user_group=销售经理");
                if(json.result && json.result.list){
                    this.list_user_sales_manager = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_sales_manager(id){
				var obj = this.list_user_sales_manager.getObj({"user_id":id});
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
			 * 获取经理用户用户列表
			 */
			async get_list_user_account_manager() {
                var json = await this.$get("~/api/user/get_list?user_group=经理用户");
                if(json.result && json.result.list){
                    this.list_user_account_manager = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_account_manager(id){
				var obj = this.list_user_account_manager.getObj({"user_id":id});
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
								        if (this.form["appointment_date"] && JSON.stringify(this.form["appointment_date"]).indexOf("-")===-1){
          this.form["appointment_date"] = this.$toTime(parseInt(this.form["appointment_date"]),"yyyy-MM-dd")
        }
				        if (this.form["delivery_time"] && JSON.stringify(this.form["delivery_time"]).indexOf("-")===-1){
          this.form["delivery_time"] = this.$toTime(parseInt(this.form["delivery_time"]),"yyyy-MM-dd")
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
																																																														if(this.form["appointment_date"]=="0000-00-00"){
				  this.form["appointment_date"] = null;
				}
				if(parseInt(this.form["appointment_date"]) > 9999){
					this.form["appointment_date"] = this.$toTime(parseInt(this.form["appointment_date"]),"yyyy-MM-dd")
				}
																													if(this.form["delivery_time"]=="0000-00-00"){
				  this.form["delivery_time"] = null;
				}
				if(parseInt(this.form["delivery_time"]) > 9999){
					this.form["delivery_time"] = this.$toTime(parseInt(this.form["delivery_time"]),"yyyy-MM-dd")
				}
																																																															
			},

																																																																																																																																										async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.reservation_record_id){
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
								if(!this.form.reservation_record_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																																							  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '预约记录',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条预约记录数据',
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
					
																																																																																																		if (!param.appointment_date){
					return "预约日期不能为空";
				}
																																													if (!param.delivery_time){
					return "交车时间不能为空";
				}
																																																																																																																		let field = {
										service_code: param.service_code,
										appointment_date: param.appointment_date,
										appointment_period: param.appointment_period,
									};
				let res = await this.$get("~/api/reservation_record/get_list?", field);
				if (res && res.result.count > 0) {
					for(var i = 0;i < res.result.list.length;i ++){
						if(res.result.list[i].reservation_record_id != param.reservation_record_id){
														return "该服务在该日期时段下，已被预约！";
													}
					}
				}
					return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/reservation_record/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/reservation_record/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/reservation_record/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/reservation_record/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/reservation_record/view','get');
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
																					this.get_list_user_owner_user();
					this.get_group_user_owner_user();
																					this.get_list_appointment_period();
								if(!this.query.reservation_record_id){
			this.form["number_of_appointments"] = this.list_number_of_appointments[0];
		}
														this.get_list_license_plate_number();
																					this.get_list_user_sales_manager();
										this.get_list_user_account_manager();
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
