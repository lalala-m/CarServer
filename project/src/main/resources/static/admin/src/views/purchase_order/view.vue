<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
												<el-col v-if="$check_field('get','purchase_order_number') || $check_field('add','purchase_order_number') || $check_field('set','purchase_order_number')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="购买单号" prop="purchase_order_number">
															<el-input id="purchase_order_number" v-model="form['purchase_order_number']" placeholder="请输入购买单号"
							  v-if="(form['purchase_order_id'] && $check_field('set','purchase_order_number')) || (!form['purchase_order_id'] && $check_field('add','purchase_order_number'))" :disabled="true">
				  					</el-input>
					<div v-else-if="$check_field('get','purchase_order_number')">
						{{form['purchase_order_number']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_coding') || $check_field('add','car_coding') || $check_field('set','car_coding')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车编码" prop="car_coding">
															<el-input id="car_coding" v-model="form['car_coding']" placeholder="请输入汽车编码"
							  v-if="(form['purchase_order_id'] && $check_field('set','car_coding')) || (!form['purchase_order_id'] && $check_field('add','car_coding'))" :disabled="disabledObj['car_coding_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_coding')">
						{{form['car_coding']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_name') || $check_field('add','car_name') || $check_field('set','car_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车名称" prop="car_name">
															<el-input id="car_name" v-model="form['car_name']" placeholder="请输入汽车名称"
							  v-if="(form['purchase_order_id'] && $check_field('set','car_name')) || (!form['purchase_order_id'] && $check_field('add','car_name'))" :disabled="disabledObj['car_name_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_name')">
						{{form['car_name']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_models') || $check_field('add','car_models') || $check_field('set','car_models')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车车型" prop="car_models">
															<el-input id="car_models" v-model="form['car_models']" placeholder="请输入汽车车型"
							  v-if="(form['purchase_order_id'] && $check_field('set','car_models')) || (!form['purchase_order_id'] && $check_field('add','car_models'))" :disabled="disabledObj['car_models_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_models')">
						{{form['car_models']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_brand') || $check_field('add','car_brand') || $check_field('set','car_brand')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车品牌" prop="car_brand">
															<el-input id="car_brand" v-model="form['car_brand']" placeholder="请输入汽车品牌"
							  v-if="(form['purchase_order_id'] && $check_field('set','car_brand')) || (!form['purchase_order_id'] && $check_field('add','car_brand'))" :disabled="disabledObj['car_brand_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','car_brand')">
						{{form['car_brand']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','new_and_old_car') || $check_field('add','new_and_old_car') || $check_field('set','new_and_old_car')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车新旧" prop="new_and_old_car">
															<el-input id="new_and_old_car" v-model="form['new_and_old_car']" placeholder="请输入汽车新旧"
							  v-if="(form['purchase_order_id'] && $check_field('set','new_and_old_car')) || (!form['purchase_order_id'] && $check_field('add','new_and_old_car'))" :disabled="disabledObj['new_and_old_car_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','new_and_old_car')">
						{{form['new_and_old_car']}}
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','car_price') || $check_field('add','car_price') || $check_field('set','car_price')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="汽车价格" prop="car_price">
											<el-input-number id="car_price" v-model.number="form['car_price']"
						v-if="(form['purchase_order_id'] && $check_field('set','car_price')) || (!form['purchase_order_id'] && $check_field('add','car_price'))" :disabled="disabledObj['car_price_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','car_price')">{{form['car_price']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">元</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','business_user') || $check_field('add','business_user') || $check_field('set','business_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商家用户" prop="business_user">
																<el-select v-if="(form['purchase_order_id'] && $check_field('set','business_user')) || (!form['purchase_order_id'] && $check_field('add','business_user'))" id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
							<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','business_user')" id="business_user" v-model="form['business_user']" :disabled="true">
							<el-option v-for="o in list_user_business_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','owner_user') || $check_field('add','owner_user') || $check_field('set','owner_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="车主用户" prop="owner_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_owner_user(form['owner_user']) }}
							<el-select v-if="(form['purchase_order_id'] && $check_field('set','owner_user')) || (!form['purchase_order_id'] && $check_field('add','owner_user'))" id="owner_user" v-model="form['owner_user']" :disabled="disabledObj['owner_user_isDisabled']">
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
																<el-col v-if="$check_field('get','purchase_time') || $check_field('add','purchase_time') || $check_field('set','purchase_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="购买时间" prop="purchase_time">
											<el-date-picker :disabled="disabledObj['purchase_time_isDisabled']" v-if="(form['purchase_order_id'] && $check_field('set','purchase_time')) || (!form['purchase_order_id'] && $check_field('add','purchase_time'))" id="purchase_time"
						v-model="form['purchase_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','purchase_time')">{{form['purchase_time']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','number_of_purchases') || $check_field('add','number_of_purchases') || $check_field('set','number_of_purchases')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="购买次数" prop="number_of_purchases">
											<el-select id="number_of_purchases" v-model="form['number_of_purchases']"
						v-if="(form['purchase_order_id'] && $check_field('set','number_of_purchases')) || (!form['purchase_order_id'] && $check_field('add','number_of_purchases'))">
						<el-option v-for="o in list_number_of_purchases" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','number_of_purchases')">{{form['number_of_purchases']}}</div>
										</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','purchase_quantity') || $check_field('add','purchase_quantity') || $check_field('set','purchase_quantity')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="购买数量" prop="purchase_quantity">
											<el-input-number id="purchase_quantity" v-model.number="form['purchase_quantity']"
						v-if="(form['purchase_order_id'] && $check_field('set','purchase_quantity')) || (!form['purchase_order_id'] && $check_field('add','purchase_quantity'))" :disabled="disabledObj['purchase_quantity_isDisabled']"></el-input-number>
					<span v-else-if="$check_field('get','purchase_quantity')">{{form['purchase_quantity']}}</span>
									<span style="font-size: 13px;color: #999;margin-left: 5px;">辆</span>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','total_amount') || $check_field('add','total_amount') || $check_field('set','total_amount')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="合计金额" prop="total_amount">
															<el-input id="total_amount" v-model="form['total_amount']" placeholder="请输入合计金额"
							  v-if="(form['purchase_order_id'] && $check_field('set','total_amount')) || (!form['purchase_order_id'] && $check_field('add','total_amount'))"  @focus="set_total_amount()" :disabled="disabledObj['total_amount_isDisabled']">
				  				  	<template #append>元</template>
				  					</el-input>
					<div v-else-if="$check_field('get','total_amount')">
						{{form['total_amount']}}
				  				  		<span>元</span>
				  					</div>
														</el-form-item>
			</el-col>
																<el-col v-if="$check_field('get','purchase_notes') || $check_field('add','purchase_notes') || $check_field('set','purchase_notes')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="购买备注" prop="purchase_notes">
											<el-input type="textarea" id="purchase_notes" v-model="form['purchase_notes']" placeholder="请输入购买备注"
						v-if="(form['purchase_order_id'] && $check_field('set','purchase_notes')) || (!form['purchase_order_id'] && $check_field('add','purchase_notes'))" :disabled="disabledObj['purchase_notes_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','purchase_notes')">{{form['purchase_notes']}}</div>
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
	
	
	
	
		
		
	
	
	
		
		
		
		</el-row>
						<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/purchase_order/view','set') || $check_action('/purchase_order/view','add')">
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
				field: "purchase_order_id",
				url_add: "~/api/purchase_order/add?",
				url_set: "~/api/purchase_order/set?",
				url_get_obj: "~/api/purchase_order/get_obj?",
				url_get_list: "~/api/purchase_order/get_list?",
				url_upload: "~/api/purchase_order/upload?",

				query: {
					"purchase_order_id": 0,
				},

				form: {
								"purchase_order_number": this.$get_stamp(), // 购买单号
										"car_coding":  '', // 汽车编码
										"car_name":  '', // 汽车名称
										"car_models":  '', // 汽车车型
										"car_brand":  '', // 汽车品牌
										"new_and_old_car":  '', // 汽车新旧
										"car_price":  0, // 汽车价格
										"business_user": 0, // 商家用户
										"owner_user": 0, // 车主用户
										"purchase_time":  '', // 购买时间
										"number_of_purchases":  '', // 购买次数
										"purchase_quantity":  0, // 购买数量
										"total_amount":  '', // 合计金额
										"purchase_notes":  '', // 购买备注
										"examine_state": "未审核",
							"examine_reply": "",
							"purchase_order_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"purchase_order_number_isDisabled": false,
										"car_coding_isDisabled": false,
										"car_name_isDisabled": false,
										"car_models_isDisabled": false,
										"car_brand_isDisabled": false,
										"new_and_old_car_isDisabled": false,
					          			"car_price_isDisabled": false,
										"business_user_isDisabled": false,
										"owner_user_isDisabled": false,
										"purchase_time_isDisabled": false,
										"number_of_purchases_isDisabled": false,
					          			"purchase_quantity_isDisabled": false,
										"total_amount_isDisabled": false,
										"purchase_notes_isDisabled": false,
										},

																																																								// 用户列表
				list_user_business_user: [],
														// 用户列表
				list_user_owner_user: [],
						// 用户组
				group_user_owner_user: "",
																							// 购买次数选项列表
				list_number_of_purchases: ['1'],
																												

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
					
	
					
	
					
	
					
	
										set_total_amount(){
				this.form.total_amount = parseFloat(this.form.car_price) * parseFloat(this.form.purchase_quantity)
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
									if(form != null){
						if("examine_state" in form){
							delete form.examine_state
						}
											if("examine_reply" in form){
							delete form.examine_reply
						}
										}
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
										        if (this.form["purchase_time"] && JSON.stringify(this.form["purchase_time"]).indexOf("-")===-1){
            this.form["purchase_time"] = this.$toTime(parseInt(this.form["purchase_time"]),"yyyy-MM-dd hh:mm:ss")
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
																																																																													if(this.form["purchase_time"]=="0000-00-00 00:00:00"){
				  this.form["purchase_time"] = null;
				}
				if(parseInt(this.form["purchase_time"]) > 9999){
					this.form["purchase_time"] = this.$toTime(parseInt(this.form["purchase_time"]),"yyyy-MM-dd hh:mm:ss")
				}
																																								
			},

																																																																																																														async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.purchase_order_id){
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
								if(!this.form.purchase_order_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																																													  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  														let message_inform = {
								title: '购买订单',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条购买订单数据',
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
					
																																																																																																																												if (!param.purchase_time){
					return "购买时间不能为空";
				}
																																																																											return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/purchase_order/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/purchase_order/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/purchase_order/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/purchase_order/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/purchase_order/view','get');
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
																																	this.get_list_user_business_user();
										this.get_list_user_owner_user();
					this.get_group_user_owner_user();
													if(!this.query.purchase_order_id){
			this.form["number_of_purchases"] = this.list_number_of_purchases[0];
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
