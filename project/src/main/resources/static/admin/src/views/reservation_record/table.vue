<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



													<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','service_name')">
					<el-form-item label="服务名称">
									<el-input v-model="query.service_name"></el-input>
								</el-form-item>
				</el-col>
															<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','owners_name')">
					<el-form-item label="车主姓名">
									<el-input v-model="query.owners_name"></el-input>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','contact_number')">
					<el-form-item label="联系号码">
									<el-input v-model="query.contact_number"></el-input>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','appointment_date')">
					<el-form-item label="预约日期">
									<el-date-picker v-model="query.appointment_date" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
								</el-form-item>
				</el-col>
																					<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','license_plate_number')">
					<el-form-item label="车牌号码">
									<el-cascader v-model="query.license_plate_number" :options="list_license_plate_number" :props="license_plate_number_cascader"
							:show-all-levels="false" clearable collapse-tags placeholder="请选择车牌号码"
							@change="handle_license_plate_number_change"></el-cascader>
								</el-form-item>
				</el-col>
												<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','car_type')">
					<el-form-item label="汽车类型">
									<el-input v-model="query.car_type"></el-input>
								</el-form-item>
				</el-col>
																<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="支付状态">
						<el-select v-model="query.pay_state">
							<el-option value="">全部</el-option>
							<el-option value="未支付">未支付</el-option>
							<el-option value="已支付">已支付</el-option>
							</el-select>
					</el-form-item>
				</el-col>

	</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

														<el-button v-if="$check_action('/reservation_record/table','del') || $check_action('/reservation_record/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                                                                                                  </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="reservation_record_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="booking_code" @sort-change="$sortChange" label="预约编码" 				v-if="$check_field('get','booking_code')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["booking_code"] }}
									</template>
					</el-table-column>
					<el-table-column prop="service_code" @sort-change="$sortChange" label="服务编码" 				v-if="$check_field('get','service_code')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["service_code"] }}
									</template>
					</el-table-column>
					<el-table-column prop="service_name" @sort-change="$sortChange" label="服务名称" 				v-if="$check_field('get','service_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["service_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="service_price" @sort-change="$sortChange" label="服务价格(元)" 				v-if="$check_field('get','service_price')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["service_price"] }}
									</template>
					</el-table-column>
					<el-table-column prop="owner_user" @sort-change="$sortChange" label="车主用户" 				v-if="$check_field('get','owner_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_owner_user(scope.row['owner_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="owners_name" @sort-change="$sortChange" label="车主姓名" 				v-if="$check_field('get','owners_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["owners_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="contact_number" @sort-change="$sortChange" label="联系号码" 				v-if="$check_field('get','contact_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["contact_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="appointment_date" @sort-change="$sortChange" label="预约日期" 				v-if="$check_field('get','appointment_date')" min-width="300">
		                <template slot-scope="scope">
									                	{{ $toTime(scope.row["appointment_date"],"yyyy-MM-dd") }}
														                </template>
					</el-table-column>
					<el-table-column prop="appointment_period" @sort-change="$sortChange" label="预约时段" 				v-if="$check_field('get','appointment_period')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["appointment_period"] }}
									</template>
					</el-table-column>
					<el-table-column prop="number_of_appointments" @sort-change="$sortChange" label="预约次数" 				v-if="$check_field('get','number_of_appointments')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["number_of_appointments"] }}
									</template>
					</el-table-column>
					<el-table-column prop="delivery_time" @sort-change="$sortChange" label="交车时间" 				v-if="$check_field('get','delivery_time')" min-width="300">
		                <template slot-scope="scope">
									                	{{ $toTime(scope.row["delivery_time"],"yyyy-MM-dd") }}
														                </template>
					</el-table-column>
					<el-table-column prop="sales_consultant" @sort-change="$sortChange" label="销售顾问" 				v-if="$check_field('get','sales_consultant')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["sales_consultant"] }}
									</template>
					</el-table-column>
					<el-table-column prop="license_plate_number" @sort-change="$sortChange" label="车牌号码" 				v-if="$check_field('get','license_plate_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["license_plate_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="frame_number" @sort-change="$sortChange" label="车架号" 				v-if="$check_field('get','frame_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["frame_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_type" @sort-change="$sortChange" label="汽车类型" 				v-if="$check_field('get','car_type')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_type"] }}
									</template>
					</el-table-column>
					<el-table-column prop="appointment_remarks" @sort-change="$sortChange" label="预约备注" 				v-if="$check_field('get','appointment_remarks')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["appointment_remarks"] }}
									</template>
					</el-table-column>
					<el-table-column prop="sales_manager" @sort-change="$sortChange" label="销售经理" 				v-if="$check_field('get','sales_manager')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_sales_manager(scope.row['sales_manager']) }}
				</template>
					</el-table-column>
					<el-table-column prop="account_manager" @sort-change="$sortChange" label="客户经理" 				v-if="$check_field('get','account_manager')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_account_manager(scope.row['account_manager']) }}
				</template>
					</el-table-column>
	
			<el-table-column label="支付状态" prop="pay_state" min-width="100">
			</el-table-column>

			<el-table-column label="支付类型" prop="pay_type" min-width="100">
			</el-table-column>
			<el-table-column prop="extra" @sort-change="$sortChange" label="信息" min-width="300" v-if="hasExtraData" >
				<template slot-scope="scope">
					<div v-for="(value, key) in JSON.parse(scope.row.extra || '{}')" :key="key">{{ key }}：{{ value }}</div>
				</template>
			</el-table-column>
            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
            </el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
			</el-table-column>







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/reservation_record/table','set') || $check_action('/reservation_record/view','set') || $check_action('/reservation_record/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/reservation_record/table','set') || $check_action('/reservation_record/view','set') || $check_action('/reservation_record/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
						<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="openPayModal(scope.row)" v-if="scope.row.pay_state==='未支付' && ($check_pay('/reservation_record/table')) ">
						<span>支付</span>
					</el-button>
					</div>
				</template>
			</el-table-column>

		</el-table>
																				<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->
																																																																																<el-dialog title="支付" :visible.sync="payModalVisible" width="500px">
			<div class="pay_step pay_step_one" v-if="pay_step == 1">
				<div class="pay_info">
																		<div class="pay_info_item">
						<div class="pay_info_item_title">支付内容</div>
						<div class="pay_info_item_content">{{ pay_data.service_name }}</div>
					</div>
														<div class="pay_info_item">
						<div class="pay_info_item_title">订单金额</div>
						<div class="pay_info_item_content">¥{{ pay_data.service_price }}</div>
					</div>
																																																																															<div class="pay_info_item pay_amount">
						<div class="pay_info_item_title">实付金额</div>
						<div class="pay_info_item_content pay_info_amount">¥{{ pay_data.amount_paid }}</div>
					</div>
				</div>
				<div class="pay_type">
					<div class="pay_type_title">支付方式</div>
					<div class="pay_type_list">
						<div class="pay_type_item" v-for="item in pay_source" :class="{'pay_type_item_active': pay_obj.payActiveName == item.name}" @click="pay_obj.payActiveName = item.name">
							<div class="pay_type_img"><img :src="item.img" alt=""></div>
							<div class="pay_type_name">{{ item.name }}</div>
								<!--<div class="pay_type_remark" v-if="item.name == '积分兑换'">可用 {{ user.integral }}</div>-->
						</div>
					</div>
				</div>
				<div class="pay_btn">
					<el-button class="e-button el-button--primary" @click="confirmPayStep()">
						<span>确认</span>
					</el-button>
				</div>
			</div>
			<div class="pay_step pay_step_two" v-if="pay_step == 2">
				<div class="pay_op">
					<el-image v-if="pay_obj.payActiveName == '微信支付'" :src="pay_source[0].pay_img"></el-image>
					<el-image v-if="pay_obj.payActiveName == '支付宝支付'" :src="pay_source[1].pay_img"></el-image>
					<div class="pay_ebank" v-if="pay_obj.payActiveName == '网银支付'">
						<div class="pay_ebank_item">
							<div class="pay_ebank_title">请输入网银账号：</div>
							<el-input class="pay_ebank_input" v-model="pay_obj.account" placeholder="请输入网银账号"></el-input>
						</div>
						<div class="pay_ebank_item">
							<div class="pay_ebank_title">请输入支付密码，6位数字：</div>
							<el-input class="pay_ebank_input" placeholder="请输入密码" v-model="pay_obj.password" show-password maxlength="6"></el-input>
						</div>
					</div>
				</div>
				<div class="pay_btn">
					<el-button class="e-button is-plain el-button--primary" @click="pay_step = 1">
						<span>其他支付方式</span>
					</el-button>
					<el-button class="e-button el-button--primary" @click="submitPay()">
						<span>确认支付</span>
					</el-button>
				</div>
			</div>
		</el-dialog>
			<el-dialog title="重要提醒" :visible.sync="showModal" width="400px" :before-close="closeModal">
			<div style="text-align: center">
				<p style="margin-bottom: 16px; font-size: 16px; color: #333;">
				当前有数据达到预警值！
				</p>
				<p style="color: #666;">{{ message }}</p>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="closeModal">取消</el-button>
				<el-button type="primary" @click="closeModal">确定</el-button>
			</div>
		</el-dialog>


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
				statisticsData: {
      				total: 0,
                                    				},
																																									license_plate_number_cascader: {
					value: 'license_plate_number',
					label: 'license_plate_number',
					children: 'children',
					emitPath: false,
					expandTrigger: 'hover',
				},
																	// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/reservation_record/get_list",
				url_del: "~/api/reservation_record/del?",

				// 字段ID
				field: "reservation_record_id",
																																// 查询
				query: {
					"size":  7,
					"page": 1,
										"service_name": "",
													"owners_name": "",
											"contact_number": "",
											"appointment_date": "",
															"license_plate_number": "",
												"car_type": "",
											"pay_state":"",
					"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 数据
				list: [],
				pay_step: 1,
				pay_data: {},
				pay_source: [
					{
						name: "微信支付",
						img: require('../../../public/icon/wechat.svg'),
						pay_img: require('../../../public/wx.png')
					},
					{
						name: "支付宝支付",
						img: require('../../../public/icon/alipay.svg'),
						pay_img: require('../../../public/alipay.png')
					},
					{
						name: "网银支付",
						img: require('../../../public/icon/ebank.svg')
					},
						
				],
				payModalVisible:false,
				pay_obj:{
					payActiveName:"微信支付",
					account:"",
					password:"",
					id:""
				},
																																		// 用户列表
				list_user_owner_user: [],
																				// 车牌号码列表
				list_license_plate_number: [""],
																// 用户列表
				list_user_sales_manager: [],
									// 用户列表
				list_user_account_manager: [],
					message: '',
				// 控制展开状态
				isExpandAll: true,
				// 存储展开的行
				expandKeys: [],
				prevSelection: [],
			}
		},
		methods: {
				previewFile(fileName) {
					if(!fileName){
						return;
					}
					const fileExtension = fileName.split('.').pop().toLowerCase();
					if(fileExtension == 'docx' || fileExtension == 'xlsx' || fileExtension == 'pptx') {
						this.$router.push('/filePreview?url=' + fileName + '&type=' + fileExtension);
						return;
					}
					window.open(this.$fullUrl(fileName), '_blank');
				},
				showPreview(file) {
					if(!file){
						return false;
					}
					let fileType = file.split('.').pop().toLowerCase();
					if(fileType == 'docx' || fileType == 'xlsx' || fileType == 'pptx') {
						return true;
					}else {
						return false;
					}
				},
																																							// 关闭弹框
			closeModal(){
				this.showModal = false;
				},
			/**
			 * @description 获取到列表事件
			 * @param {Object} res 响应结果
			 */
			get_list_after: function get_list_after(res, func, url) {
				let _this = this
																																																																																																			},

			get_list_before(param){
				var user_group = this.user.user_group;
				if(user_group != "管理员"){
						let sqlwhere = "(";
																														if(user_group=="车主用户"){
						sqlwhere+= "owner_user = " + this.user.user_id + " or ";
					}
																																																																				if(user_group=="销售经理"){
						sqlwhere+= "sales_manager = " + this.user.user_id + " or ";
					}
													if(user_group=="经理用户"){
						sqlwhere+= "account_manager = " + this.user.user_id + " or ";
					}
										if (sqlwhere.length>1){
						sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
						sqlwhere += ")";
						param["sqlwhere"] = sqlwhere;
					}
					}
				return param;
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
						this.list_license_plate_number = this.build_license_plate_number_tree(json.result.list, 0);
					} else {
						this.list_license_plate_number = json.result.list;
					}
			  				}else if (json.error){
					console.log(json.error);
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
			handle_license_plate_number_change(value) {
				if (value) {
					this.query.license_plate_number = value;
				} else {
					this.query.license_plate_number = '';
				}
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
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
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
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
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
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
					openPayModal(v){
				this.pay_data = v
																this.pay_data.amount_paid = v.service_price
																																																		this.pay_obj.id = v.reservation_record_id
				this.payModalVisible = true
			},
			confirmPayStep() {
				if (this.pay_obj.payActiveName == "微信支付" || this.pay_obj.payActiveName == "支付宝支付" || this.pay_obj.payActiveName == "网银支付") {
					this.pay_step = 2
				}
				},
			submitPay(){
									let message_inform1 = {
						title: '用户支付订单成功',
						type: '消息',
						content: this.user.username + '-' + this.user.nickname + '成功支付了',
						state: 1,
						user_id: '9999'
					}
								let _this = this;
				if(_this.pay_obj.payActiveName == "网银"){
					if(_this.pay_obj.account == ""){
						_this.$toast("请输入网银账号", 'danger');
						return false
					}
					if(_this.pay_obj.password.length < 6){
						_this.$toast("请输入6位数的支付密码", 'danger');
						return false
					}
				}
				let url = this.$toUrl(this.query, "~/api/reservation_record/set?reservation_record_id="+this.pay_obj.id);
				let name = this.pay_obj.payActiveName;
            	let payType = name.endsWith("支付") ? name.slice(0, -2) : name;
				let param = {"pay_state":"已支付","pay_type":payType}
																																																											this.$post(url, param, function(json, status) {
					console.log("提交结果：" ,json);
					if (json.result) {
							_this.$toast('支付成功！', 'success');
						_this.payModalVisible = false;
													_this.$post("~/api/message_inform/add",message_inform1)
												_this.get_list();
					} else if (json.error) {
						_this.$toast(json.error.message, 'danger');
					}
				});
				setTimeout(() => {
					this.pay_obj.payActiveName = "微信支付"
					this.pay_step = 1
				}, 1000);
			},
			async delInfo() {
				var list = this.selection;
				if (list.length === 0) {
					this.$message({
						type: 'info',
						message: '选择对象不能为空!'
					});
					return;
				}
				for (let i = 0; i < list.length; i++) {
					let type = list[i];
					let res
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		}
				this.$confirm('删除后数据将无法恢复，请确认是否删除？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async () => {
              		await this.delAll(list);
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
	},
		created() {
															this.get_list_user_owner_user();
																			// 初始化车牌号码列表
			this.get_list_license_plate_number();
															this.get_list_user_sales_manager();
								this.get_list_user_account_manager();
			},
		computed: {
			hasExtraData() {
				return this.list.some(item => item.extra && item.extra.trim() !== '');
			}
		}
	}
</script>

<style type="text/css">
	.bg {
		background: white;
	}

	.form.p_4 {
		padding: 1rem;
	}

	.form .el-input {
		width: initial;
	}

	.mt {
		margin-top: 1rem;
	}

	.text_center {
		text-align: center;
	}

	.float-right {
		float: right;
	}


	.modal_wrap{
		width: 100vw;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
		background: rgba(0,0,0,0.5);
		z-index: 9999999999;
	}
	.el-date-editor .el-range-separator{
		width: 10% !important;
	}
	.num_range {
	  display: flex;
	  align-items: center;
	}

	.num_range_span {
	  font-size: 28px;
	}
	td > .cell > a, td > .cell > span, td > .cell {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 3;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.expand-all {
		margin-top: 10px;
	}
	.el-table__indent, .el-table__placeholder {
		display: inline-block !important;
	}
.pay_info .pay_info_item{
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-bottom: 10px;
}
.pay_info .pay_info_item .pay_info_item_content{
	color: #999;
}
.pay_info .pay_amount{
	margin-top: 100px;
	padding: 10px 0;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
}
.pay_info .pay_info_item .pay_info_amount{
	font-weight: bold;
	font-size: 16px;
	color: red;
}
.pay_type .pay_type_title{
	color: #999;
	margin-bottom: 10px;
}
.pay_type .pay_type_list{
	display: grid;
	gap: 10px;
	grid-template-columns: repeat(3, 1fr);
}
.pay_type .pay_type_item{
	background: #f1f1f1;
	border: 1px solid #f1f1f1;
	border-radius: 5px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 20px 10px;
}
.pay_type .pay_type_item .pay_type_img{
	width: 30px;
	height: 30px;
	margin-bottom: 10px;
}
.pay_type .pay_type_item .pay_type_img img{
	width: 100%;
	height: 100%;
	object-fit: contain;
}
.pay_type .pay_type_item .pay_type_remark{
	font-size: 12px;
	color: #999;
}
.pay_type .pay_type_item_active{
	border: 1px solid #2c70f6;
}
.pay_btn{
	display: flex;
	align-items: center;
	justify-content: space-between;
}
.pay_btn button{
	width: 100%;
	margin-top: 20px;
	height: 42px;
	line-height: 42px;
	padding: 0;
}
.pay_ebank_title{
	margin: 15px 0 10px 0;
}
.route-map-dialog {
  display: flex;
  height: 500px;
}

.route-map-left {
  flex: 1;
  position: relative;
  height: 100%;
  background: #f5f5f5;
}

.route-map-container {
  width: 100%;
  height: 100%;
}

.route-map-loading {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
  color: #409eff;
  font-size: 14px;
}

.route-map-right {
  width: 320px;
  padding-left: 20px;
  overflow-y: auto;
  border-left: 1px solid #eee;
}

.route-map-info-row {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.route-map-info-label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.route-map-info-value {
  color: #666;
  line-height: 1.5;
  font-size: 14px;
}
</style>
