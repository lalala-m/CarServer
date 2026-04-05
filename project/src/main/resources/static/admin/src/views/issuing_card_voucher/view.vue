<template>
	<el-main class="bg edit_wrap comtable_e">
		<iframe :srcdoc="iframeContent" id='printIframe' width="600" height="400" style="display: none;"></iframe>
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','card_code') || $check_field('add','card_code') || $check_field('set','card_code')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="卡券编码" prop="card_code">
															<el-input id="card_code" v-model="form['card_code']" placeholder="请输入卡券编码"
							  v-if="(form['issuing_card_voucher_id'] && $check_field('set','card_code')) || (!form['issuing_card_voucher_id'] && $check_field('add','card_code'))" :disabled="true">
				  					</el-input>
					<div v-else-if="$check_field('get','card_code')">
						{{form['card_code']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','manager_user') || $check_field('add','manager_user') || $check_field('set','manager_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="经理用户" prop="manager_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_manager_user(form['manager_user']) }}
							<el-select v-if="(form['issuing_card_voucher_id'] && $check_field('set','manager_user')) || (!form['issuing_card_voucher_id'] && $check_field('add','manager_user'))" id="manager_user" v-model="form['manager_user']" :disabled="disabledObj['manager_user_isDisabled']">
								<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','manager_user')" id="manager_user" v-model="form['manager_user']" :disabled="true">
								<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="manager_user" v-model="form['manager_user']" :disabled="disabledObj['manager_user_isDisabled']">
							<el-option v-for="o in list_user_manager_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																			</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owner_user') || $check_field('add','owner_user') || $check_field('set','owner_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主用户" prop="owner_user">
																<el-select v-if="(form['issuing_card_voucher_id'] && $check_field('set','owner_user')) || (!form['issuing_card_voucher_id'] && $check_field('add','owner_user'))" id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
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
																<el-col v-if="$check_field('get','license_plate_number') || $check_field('add','license_plate_number') || $check_field('set','license_plate_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车牌号码" prop="license_plate_number">
											<el-cascader id="license_plate_number" v-model="form['license_plate_number']" :options="license_plate_number_tree" :props="license_plate_number_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择车牌号码" 
      					v-if="(form['issuing_card_voucher_id'] && $check_field('set', 'license_plate_number')) || (!form['issuing_card_voucher_id'] && $check_field('add', 'license_plate_number'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','license_plate_number')">{{form['license_plate_number']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','financial_user') || $check_field('add','financial_user') || $check_field('set','financial_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="财务用户" prop="financial_user">
																<el-select v-if="(form['issuing_card_voucher_id'] && $check_field('set','financial_user')) || (!form['issuing_card_voucher_id'] && $check_field('add','financial_user'))" id="financial_user" v-model="form['financial_user']" :disabled="disabledObj['financial_user_isDisabled']">
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
											<el-cascader id="card_name" v-model="form['card_name']" :options="card_name_tree" :props="card_name_cascader" :show-all-levels="false" clearable 
      					placeholder="请选择卡券名称" @change="select_card_name_field"
      					v-if="(form['issuing_card_voucher_id'] && $check_field('set', 'card_name')) || (!form['issuing_card_voucher_id'] && $check_field('add', 'card_name'))">
   		 			</el-cascader>
					<div v-else-if="$check_field('get','card_name')">{{form['card_name']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','effective_period') || $check_field('add','effective_period') || $check_field('set','effective_period')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="有效期限" prop="effective_period">
											<el-input id="effective_period" v-model="form['effective_period']"
						v-if="(form['issuing_card_voucher_id'] && $check_field('set','effective_period')) || (!form['issuing_card_voucher_id'] && $check_field('add','effective_period'))" :disabled="true">
									</el-input>
					<div v-else-if="$check_field('get','effective_period')">
						{{form['effective_period']}}
									</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','number_of_uses') || $check_field('add','number_of_uses') || $check_field('set','number_of_uses')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="使用次数" prop="number_of_uses">
											<el-select id="number_of_uses" v-model="form['number_of_uses']"
						v-if="(form['issuing_card_voucher_id'] && $check_field('set','number_of_uses')) || (!form['issuing_card_voucher_id'] && $check_field('add','number_of_uses'))">
						<el-option v-for="o in list_number_of_uses" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','number_of_uses')">{{form['number_of_uses']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','issuing_time') || $check_field('add','issuing_time') || $check_field('set','issuing_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="发放时间" prop="issuing_time">
											<el-date-picker :disabled="disabledObj['issuing_time_isDisabled']" v-if="(form['issuing_card_voucher_id'] && $check_field('set','issuing_time')) || (!form['issuing_card_voucher_id'] && $check_field('add','issuing_time'))" id="issuing_time"
						v-model="form['issuing_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','issuing_time')">{{form['issuing_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','write_off_status') || $check_field('add','write_off_status') || $check_field('set','write_off_status')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="核销状态" prop="write_off_status">
											<el-select id="write_off_status" v-model="form['write_off_status']"
						v-if="(form['issuing_card_voucher_id'] && $check_field('set','write_off_status')) || (!form['issuing_card_voucher_id'] && $check_field('add','write_off_status'))">
						<el-option v-for="o in list_write_off_status" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','write_off_status')">{{form['write_off_status']}}</div>
										</el-form-item>
			</el-col>
											<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核状态" prop="examine_state">
					<el-select id="examine_state" v-model="form['examine_state']"
						v-if="(form['examine_state'] && $check_examine()) || (!form['examine_state'] && $check_examine())" :disabled="true">
						<el-option key="未审核" label="未审核" value="未审核"></el-option>
						<el-option key="已通过" label="已通过" value="已通过"></el-option>
						<el-option key="未通过" label="未通过" value="未通过"></el-option>
					</el-select>
					<div v-else>{{form["examine_state"]}}</div>
				</el-form-item>
			</el-col>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核回复" prop="examine_reply">
					<el-input id="examine_reply" v-model="form['examine_reply']" placeholder="请输入审核回复"
						v-if="(form['examine_reply'] && $check_examine()) || (!form['examine_reply'] && $check_examine())" :disabled="true"></el-input>
					<div v-else>{{form["examine_reply"]}}</div>
				</el-form-item>
			</el-col>
	
	
	
	
		
		
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="!form['source_user_id'] && 1 > 0">
			  <el-form-item label="卡券核销限次">
				<el-input id="limit_times" v-model="form['write_off_information_limit_times']" placeholder="卡券核销限制次数，0为不限"
						  v-if="$check_option('/issuing_card_voucher/view','can_limits')"
				></el-input>
				<div v-else-if="$check_action('/issuing_card_voucher/view','get')" v-html="form['write_off_information_limit_times']"></div>
			  </el-form-item>
			</el-col>
			
	
	
		
		
		
		</el-row>
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/issuing_card_voucher/view','set') || $check_action('/issuing_card_voucher/view','add')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
					<el-button v-if="!!query.issuing_card_voucher_id"  type="primary" @click="sendprint">打印</el-button>
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
				field: "issuing_card_voucher_id",
				url_add: "~/api/issuing_card_voucher/add?",
				url_set: "~/api/issuing_card_voucher/set?",
				url_get_obj: "~/api/issuing_card_voucher/get_obj?",
				url_get_list: "~/api/issuing_card_voucher/get_list?",
				url_upload: "~/api/issuing_card_voucher/upload?",

				query: {
					"issuing_card_voucher_id": 0,
				},

				form: {
								"card_code": this.$get_stamp(), // 卡券编码
										"manager_user": 0, // 经理用户
										"owner_user": 0, // 车主用户
										"license_plate_number":  '', // 车牌号码
										"financial_user": 0, // 财务用户
										"card_name":  '', // 卡券名称
										"effective_period":  '', // 有效期限
										"number_of_uses":  '', // 使用次数
										"issuing_time":  '', // 发放时间
										"write_off_status":  '', // 核销状态
										"examine_state": "未审核",
							"examine_reply": "",
							"issuing_card_voucher_id": 0, // ID
													"write_off_information_limit_times": 1, // 卡券核销限制次数
															"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"card_code_isDisabled": false,
										"manager_user_isDisabled": false,
										"owner_user_isDisabled": false,
										"license_plate_number_isDisabled": false,
										"financial_user_isDisabled": false,
										"card_name_isDisabled": false,
										"effective_period_isDisabled": false,
										"number_of_uses_isDisabled": false,
										"issuing_time_isDisabled": false,
										"write_off_status_isDisabled": false,
										},

														// 用户列表
				list_user_manager_user: [],
						// 用户组
				group_user_manager_user: "",
														// 用户列表
				list_user_owner_user: [],
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
				list_user_financial_user: [],
																card_name_cascader: {
					value: 'card_name',
					label: 'card_name',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
				// 商品分类树形数据
				card_name_tree: [],
						// 卡券名称选项列表
				list_card_name: [""],
																							// 使用次数选项列表
				list_number_of_uses: ['1'],
																							// 核销状态选项列表
				list_write_off_status: ['待核销'],
							

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
																																				];
				let formArrs = [
									{ label: '卡券编码', key: 'card_code', value: '' },
										{ label: '经理用户', key: 'manager_user', value: '' },
										{ label: '车主用户', key: 'owner_user', value: '' },
										{ label: '车牌号码', key: 'license_plate_number', value: '' },
										{ label: '财务用户', key: 'financial_user', value: '' },
										{ label: '卡券名称', key: 'card_name', value: '' },
										{ label: '有效期限', key: 'effective_period', value: '' },
										{ label: '使用次数', key: 'number_of_uses', value: '' },
										{ label: '发放时间', key: 'issuing_time', value: '' },
										{ label: '核销状态', key: 'write_off_status', value: '' },
										{label: '审核状态', key: 'examine_state', value: ''},
							{label: '审核回复', key: 'examine_reply', value: ''},
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
					/**
			 * 获取经理用户用户组
			 */
			async get_group_user_manager_user() {
							this.form["manager_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=经理用户");
				if(json.result && json.result.obj){
					this.group_user_manager_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_manager_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_manager_user.source_table+"/get_obj?"
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
												_this.form["manager_user"] = id
									_this.disabledObj['manager_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "manager_user" && arr[i] !== "create_by") {
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
					if (!this.query.issuing_card_voucher_id && this.license_plate_number_tree.length > 0) {
										this.form["license_plate_number"] = this.license_plate_number_tree[0]["license_plate_number"];
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
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
			 * 获取卡券名称列表
			 */
						async get_list_card_name() {
			 	let param = {}
			  			  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  			  				  				var json = await this.$get("~/api/card_type/get_list?",param);
				if(json.result && json.result.list){
					if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
						json.result.list = json.result.list.filter(item => item.type == 1);
					}
			  					const hasFatherId = json.result.list.some(item => 'father_id' in item);
					if (hasFatherId) {
						this.card_name_tree = this.build_card_name_tree(json.result.list, 0);
					} else {
						this.card_name_tree = json.result.list;
					}
					if (!this.query.issuing_card_voucher_id && this.card_name_tree.length > 0) {
										this.form["card_name"] = this.card_name_tree[0]["card_name"];
												this.select_card_name_field(this.form["card_name"])
															}
			  				}
				else if(json.error){
					console.error(json.error);
				}
			},
								select_card_name_field(v){
				this.$get('~/api/card_type/get_obj?card_name='+v,{},(res)=>{
					if(res.result && res.result.obj){
																																																												this.form.effective_period = res.result.obj.effective_period;
																																						}else{
						console.error(res.error);
					}
				})
			},
									build_card_name_tree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
					const children = this.build_card_name_tree(list, item.card_type_id);
					if (children.length > 0) {
						item.children = children;
					}
					tree.push(item);
					}
				}
				return tree;
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
									        if (this.form["issuing_time"] && JSON.stringify(this.form["issuing_time"]).indexOf("-")===-1){
            this.form["issuing_time"] = this.$toTime(parseInt(this.form["issuing_time"]),"yyyy-MM-dd hh:mm:ss")
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
																
			},

																																																																																		async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.issuing_card_voucher_id){
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
								if(!this.form.issuing_card_voucher_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  														let message_inform = {
								title: '发放卡券',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条发放卡券数据',
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
																															return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/issuing_card_voucher/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/issuing_card_voucher/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/issuing_card_voucher/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/issuing_card_voucher/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/issuing_card_voucher/view','get');
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
					this.get_group_user_manager_user();
										this.get_list_user_owner_user();
									this.get_list_license_plate_number();
									this.get_list_user_financial_user();
									this.get_list_card_name();
												if(!this.query.issuing_card_voucher_id){
			this.form["number_of_uses"] = this.list_number_of_uses[0];
		}
										if(!this.query.issuing_card_voucher_id){
			this.form["write_off_status"] = this.list_write_off_status[0];
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
