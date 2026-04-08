<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



													<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','car_name')">
					<el-form-item label="汽车名称">
									<el-input v-model="query.car_name"></el-input>
								</el-form-item>
				</el-col>
															<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','new_and_old_car')">
					<el-form-item label="汽车新旧">
									<el-input v-model="query.new_and_old_car"></el-input>
								</el-form-item>
				</el-col>
																		<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','purchase_time')">
					<el-form-item label="购买时间">
									<el-date-picker v-model="query.purchase_time" type="datetimerange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间"></el-date-picker>
								</el-form-item>
				</el-col>
																			<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="审核状态">
						<el-select v-model="query.examine_state">
							<el-option value="">全部</el-option>
							<el-option value="未审核">未审核</el-option>
								<el-option value="已通过">已通过</el-option>
							<el-option value="未通过">未通过</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="支付状态">
						<el-select v-model="query.pay_state">
							<el-option value="">全部</el-option>
							<el-option value="未支付">未支付</el-option>
							<el-option value="已支付">已支付</el-option>
								<el-option value="已退回">已退回</el-option>
							</el-select>
					</el-form-item>
				</el-col>

	</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

										<el-button v-if="$check_option('/purchase_order/table', 'examine')" @click="batchInfo()" class="examine_btn_state">批量审核</el-button>
														<el-button v-if="$check_action('/purchase_order/table','del') || $check_action('/purchase_order/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                                                                          </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="purchase_order_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="purchase_order_number" @sort-change="$sortChange" label="购买单号" 				v-if="$check_field('get','purchase_order_number')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["purchase_order_number"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_coding" @sort-change="$sortChange" label="汽车编码" 				v-if="$check_field('get','car_coding')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_coding"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_name" @sort-change="$sortChange" label="汽车名称" 				v-if="$check_field('get','car_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_models" @sort-change="$sortChange" label="汽车车型" 				v-if="$check_field('get','car_models')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_models"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_brand" @sort-change="$sortChange" label="汽车品牌" 				v-if="$check_field('get','car_brand')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_brand"] }}
									</template>
					</el-table-column>
					<el-table-column prop="new_and_old_car" @sort-change="$sortChange" label="汽车新旧" 				v-if="$check_field('get','new_and_old_car')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["new_and_old_car"] }}
									</template>
					</el-table-column>
					<el-table-column prop="car_price" @sort-change="$sortChange" label="汽车价格(元)" 				v-if="$check_field('get','car_price')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["car_price"] }}
									</template>
					</el-table-column>
					<el-table-column prop="business_user" @sort-change="$sortChange" label="商家用户" 				v-if="$check_field('get','business_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_business_user(scope.row['business_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="owner_user" @sort-change="$sortChange" label="车主用户" 				v-if="$check_field('get','owner_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_owner_user(scope.row['owner_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="purchase_time" @sort-change="$sortChange" label="购买时间" 				v-if="$check_field('get','purchase_time')" min-width="300">
						<template slot-scope="scope">
									                	{{ $toTime(scope.row["purchase_time"],"yyyy-MM-dd hh:mm:ss") }}
																		</template>
					</el-table-column>
					<el-table-column prop="number_of_purchases" @sort-change="$sortChange" label="购买次数" 				v-if="$check_field('get','number_of_purchases')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["number_of_purchases"] }}
									</template>
					</el-table-column>
					<el-table-column prop="purchase_quantity" @sort-change="$sortChange" label="购买数量(辆)" 				v-if="$check_field('get','purchase_quantity')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["purchase_quantity"] }}
									</template>
					</el-table-column>
					<el-table-column prop="total_amount" @sort-change="$sortChange" label="合计金额(元)" 				v-if="$check_field('get','total_amount')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["total_amount"] }}
									</template>
					</el-table-column>
					<el-table-column prop="purchase_notes" @sort-change="$sortChange" label="购买备注" 				v-if="$check_field('get','purchase_notes')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["purchase_notes"] }}
									</template>
					</el-table-column>
				<el-table-column label="审核状态" prop="examine_state" min-width="100">
				<template slot-scope="scope">
					<span v-if="scope.row['examine_state'] == '未审核'" style="color: red;">未审核</span>
					<span v-else-if="scope.row['examine_state'] == '审核中'" style="color: orange;">审核中</span>
					<span v-else-if="scope.row['examine_state'] == '已通过'" style="color: green;">已通过</span>
					<span v-else-if="scope.row['examine_state'] == '未通过'" style="color: gray;">未通过</span>
				</template>
			</el-table-column>
			<el-table-column label="审核回复" prop="examine_reply" min-width="200">
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/purchase_order/table','set') || $check_action('/purchase_order/view','set') || $check_action('/purchase_order/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/purchase_order/table','set') || $check_action('/purchase_order/view','set') || $check_action('/purchase_order/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
						<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="openPayModal(scope.row)" v-if="scope.row.pay_state==='未支付' && ($check_pay('/purchase_order/table'))  && scope.row['examine_state'] != '未通过'">
						<span>支付</span>
					</el-button>
						<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="changeSigning(scope.row, scope.$index)" v-if="$check_option('/purchase_order/table', 'examine') && (scope.row['examine_state'] == '未审核' || scope.row['examine_state'] == '审核中') ">
						<span>审核</span>
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
		<el-dialog title="审核" :visible.sync="dialogVisible" width="30%" :show-close="true">
			<el-form ref="verifyForm" :rules="rules" :model="verifyItem">
				<el-form-item label="审核状态" prop="examine_state">
					<el-radio-group v-model="verifyItem.examine_state">
						<el-radio label="已通过" value="已通过"></el-radio>
						<el-radio label="未通过" value="未通过"></el-radio>
					</el-radio-group>
				</el-form-item>
								<el-form-item label="审核回复" prop="reply">
					<el-input type="textarea" placeholder="请填写审核回复" v-model="verifyItem.examine_reply"></el-input>
				</el-form-item>
							</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="assureVerify">确 定</el-button>
			</span>
		</el-dialog>
																																																													<el-dialog
				title="支付"
				:visible.sync="payModalVisible"
				width="500px"
			>
			<el-tabs v-model="pay_obj.payActiveName" :stretch="true">
				<el-tab-pane label="微信" name="微信">
					<div style="width: 80%;margin: 0 auto;">
						<div style="width: 60%;margin: 30px auto;">
							<el-image style="width: 100%;" :src="require('../../../public/wx.png')"
									  :preview-src-list="[require('../../../public/wx.png')]">
								<div slot="error" class="image-slot">
									<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
								</div>
							</el-image>
						</div>
						<div style="width: 80%;margin: 0 auto;">
							<div style="width: 100%;background-color: #409EFF;color: white;font-size: 14px;text-align: center;line-height: 40px;cursor: pointer" @click="submitPay()">确认支付</div>
						</div>
					</div>
				</el-tab-pane>
				<el-tab-pane label="支付宝" name="支付宝">
					<div style="width: 80%;margin: 0 auto;">
						<div style="width: 60%;margin: 30px auto;">
							<el-image style="width: 100%;" :src="require('../../../public/alipay.png')"
									  :preview-src-list="[require('../../../public/alipay.png')]">
								<div slot="error" class="image-slot">
									<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
								</div>
							</el-image>
						</div>
						<div style="width: 80%;margin: 0 auto;">
							<div style="width: 100%;background-color: #409EFF;color: white;font-size: 14px;text-align: center;line-height: 40px;cursor: pointer" @click="submitPay()">确认支付</div>
						</div>
					</div>
				</el-tab-pane>
				<el-tab-pane label="网银" name="网银">
					<div style="width: 80%;margin: 0 auto;margin-top: 30px;">
						<div style="width: 80%;margin: 0 auto;">
							<div style="line-height: 40px">请输入网银账号：</div>
						</div>
						<div style="width: 80%;margin: 0 auto;">
							<el-input v-model="pay_obj.account" placeholder="请输入网银账号"></el-input>
						</div>
					</div>
					<div style="width: 80%;margin: 0 auto;margin-top: 20px;">
						<div style="width: 80%;margin: 0 auto;">
							<div style="line-height: 40px">请输入支付密码，6位数字：</div>
						</div>
						<div style="width: 80%;margin: 0 auto;">
							<el-input placeholder="请输入密码" v-model="pay_obj.password" show-password maxlength="6"></el-input>
						</div>
					</div>
					<div style="width: 80%;margin: 0 auto;margin-top: 40px;">
						<div style="width: 100%;background-color: #409EFF;color: white;font-size: 14px;text-align: center;line-height: 40px;cursor: pointer" @click="submitPay()">确认支付</div>
					</div>
				</el-tab-pane>
			</el-tabs>
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
																																	// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/purchase_order/get_list",
				url_del: "~/api/purchase_order/del?",

				// 字段ID
				field: "purchase_order_id",
				dialogVisible: false,
				verifyIdx: 0,
				verifyItem: {},
				rules: {
				  "examine_state": [
				    { required: true, message: '请选择审核状态', trigger: 'change' },
				  ],
				},
																																// 查询
				query: {
					"size":  7,
					"page": 1,
										"car_name": "",
													"new_and_old_car": "",
														"purchase_time": "",
												"examine_state":"",
					"pay_state":"",
					"login_time": "",
					"create_time": "",
					"orderby": `CASE WHEN examine_state = "未审核" THEN 1 ELSE 2 END,create_time desc`
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
					payActiveName:"微信",
					account:"",
					password:"",
					id:""
				},
				batchAllState: false,
				batchAllList: [],
																																				// 用户列表
				list_user_business_user: [],
									// 用户列表
				list_user_owner_user: [],
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
			// 审核弹窗
			changeSigning(query, index) {
			  let beforeQuery=JSON.parse(JSON.stringify(query));
			  this.verifyIdx = index;
			  this.verifyItem = beforeQuery;
				this.verifyItem.examine_state = "";
			  this.batchAllState = false;
			  this.batchAllList = [];
			  this.dialogVisible = true;
			},
			batchAll(list) {
				this.batchAllState = true;
					this.batchAllList = list.filter(item => item.examine_state === "未审核");
					this.dialogVisible = true;
			},
			assureVerify() {
				//审核
				let _this = this;
				let examineForm = this.$refs["verifyForm"];
				examineForm.validate(async (valid) => {
					if (valid) {
						let apiState = true;
						if (this.batchAllState) {
							if(this.batchAllList.length == 0){
								_this.$toast("请选择要操作的数据", "danger");
								return;
							}
							for (var i = 0; i < this.batchAllList.length; i++) {
								let url = "~/api/purchase_order/set?purchase_order_id=" +  this.batchAllList[i]["purchase_order_id"];
									let pay_state = _this.verifyItem.examine_state === "未通过" && this.batchAllList[i]["pay_state"] === "已支付" ? "已退回" : this.batchAllList[i]["pay_state"];
									let json = await _this.$post(url,{
									'examine_state': _this.verifyItem.examine_state,
										'examine_reply': _this.verifyItem.examine_reply,
											'pay_state': pay_state,
									  }
								)
									if (json.error) {
									apiState = false;
									_this.$toast(json.error.message, "danger");
									break;
								}
									let message_inform = {
									title: '审核结果',
									type: '通知',
									content: '你在购买订单下提交的内容，审核结果为：' + _this.verifyItem.examine_state,
									state: 1,
									user_id: _this.batchAllList[i].create_by
								}
								_this.$post("~/api/message_inform/add", message_inform)
								}
							if (apiState) {
							  _this.$toast("审核成功！", "success");
							  _this.dialogVisible = false;
							  _this.get_list();
							}
						}else{
							var url = "~/api/purchase_order/set?purchase_order_id=" + this.verifyItem['purchase_order_id']
									let pay_state = _this.verifyItem.examine_state === "未通过" && _this.verifyItem.pay_state === "已支付" ? "已退回" : _this.verifyItem.pay_state;
								_this.$post(url, {
								'examine_state': _this.verifyItem.examine_state,
									'examine_reply': _this.verifyItem.examine_reply,
										'pay_state': pay_state,
								}, (json, status) => {
								if (json.result) {
										_this.$toast('审核成功！', 'success');
									_this.dialogVisible = false;
																	let message_inform = {
										title: '审核结果',
										type: '通知',
										content: '你在购买订单下提交的内容，审核结果为：' + _this.verifyItem.examine_state,
										state: 1,
										user_id: _this.verifyItem.create_by
									}
									this.$post("~/api/message_inform/add",message_inform)
																	_this.get_list();
								} else if (json.error) {
									_this.$toast(json.error.message, 'danger');
								}
							})
						}

					}
				})
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
																																													if(user_group=="商家用户"){
						sqlwhere+= "business_user = " + this.user.user_id + " or ";
					}
													if(user_group=="车主用户"){
						sqlwhere+= "owner_user = " + this.user.user_id + " or ";
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
															openPayModal(v){
				this.pay_data = v
																																																this.pay_obj.id = v.purchase_order_id
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
				let url = this.$toUrl(this.query, "~/api/purchase_order/set?purchase_order_id="+this.pay_obj.id);
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
					this.pay_obj.payActiveName = "微信"
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
																					this.get_list_user_business_user();
								this.get_list_user_owner_user();
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
