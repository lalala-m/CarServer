<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :rules="rules" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
	            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
                <el-form-item label="封面图" prop="cart_avatar">
                    <el-upload class="avatar-uploader" drag accept="image/gif, image/jpeg, image/png, image/jpg"
                               action="" :http-request="upload_cart_img" :show-file-list="false">
                        <img id="cart_img" v-if="form.cart_img" :src="$fullUrl(form.cart_img)" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="12" :lg="16" class="el_form_item_warp">
			<el-form-item label="主图" prop="cart_avatar" class="mul-upload-container">
				<el-upload
				class="avatar-uploader upload-container"
				action=""
				list-type="picture-card" 
				multiple 
				drag
				accept="image/gif, image/jpeg, image/png, image/jpg"
				:on-preview="handlePictureCardPreview"
				:http-request="upload_cart_img_1"
				:limit="5"
				:file-list="cartImgList"
				ref="upload"
				:on-exceed="uploadExceed" 
				:on-remove="handleRemove" 
				:class="{ 'hide-upload': cartImgList.length >= 5 }"
				>
				<i class="el-icon-plus"></i>
				</el-upload>
			</el-form-item>
			</el-col>

            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" style="clear: both;">
                <el-form-item label="名称" prop="cart_title">
                    <el-input v-model="form.cart_title" placeholder="请输入名称"></el-input>
                </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="12" :lg="16" class="el_form_item_warp">
                <el-form-item label="描述" prop="cart_description">
                    <el-input v-model="form.cart_description" placeholder="请输入描述"></el-input>
                </el-form-item>
            </el-col>

			<el-col class="el_form_item_warp">
				<el-form-item label="规格设置" prop="cart_price_ago">
					<el-radio-group v-model="skuStatus" @input="skuStatusChange">
					<el-radio :label="0">单规格</el-radio>
					<el-radio :label="1">多规格</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-table v-if="skuStatus === 1" :data="skuData" border style="width: calc(100% - 120px);margin-left: 120px;">
					<el-table-column prop="sku_title" label="规格名称" align="center">
						<template slot-scope="scope">
							<el-input v-model="scope.row.sku_title" />
						</template>
					</el-table-column>
					<el-table-column prop="sku_price" label="卖价" align="center">
						<template slot-scope="scope">
							<el-input v-model="scope.row.sku_price" />
						</template>
					</el-table-column>
					<el-table-column prop="sku_price_ago" label="原价" align="center">
						<template slot-scope="scope">
							<el-input v-model="scope.row.sku_price_ago" />
						</template>
					</el-table-column>
					<el-table-column prop="sku_inventory" label="库存" align="center">
						<template slot-scope="scope">
							<el-input v-model="scope.row.sku_inventory" />
						</template>
					</el-table-column>
									<el-table-column prop="sku_integral" label="积分" align="center">
						<template slot-scope="scope">
							<el-input v-model="scope.row.sku_integral" />
						</template>
					</el-table-column>
													<el-table-column label="操作" align="center">
						<template slot-scope="scope">
							<el-button type="text" v-if="scope.$index > 0" @click="delSku(scope.$index)" icon="el-icon-delete"
							style="color:#D9001B">
							</el-button>
						</template>
					</el-table-column>
				</el-table>
				<el-button class="add-sku-btn" type="text" v-if="skuStatus === 1" @click="addSku" style="margin-left: 120px;">
					<i class="el-icon-plus"></i>
					<span>增加规格</span>
				</el-button>
			</el-col>
            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="skuStatus === 0">
                <el-form-item label="原价" prop="cart_price_ago">
                    <el-input-number v-model="form.cart_price_ago"></el-input-number>
                </el-form-item>
            </el-col>

					<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="skuStatus === 0">
				<el-form-item label="积分" prop="cart_integral">
					<el-input-number v-model="form.cart_integral"></el-input-number>
				</el-form-item>
			</el-col>
		
            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="skuStatus === 0">
                <el-form-item label="卖价" prop="cart_price">
                    <el-input-number v-model="form.cart_price"></el-input-number>
                </el-form-item>
            </el-col>

		
            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp" v-if="skuStatus === 0">
                <el-form-item label="库存" prop="cart_inventory">
                    <el-input-number v-model="form.cart_inventory" placeholder="请输入库存"></el-input-number>
                </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
                <el-form-item label="分类" prop="cart_type">
                    <el-cascader v-model="form.cart_type" :options="list_cart_goods_type_tree" :props="cascader_props" :show-all-levels="false" clearable placeholder="请选择分类" style="width: 100%;"></el-cascader>
                </el-form-item>
            </el-col>
												<el-col v-if="$check_field('get','business_user') || $check_field('add','business_user') || $check_field('set','business_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商家用户" prop="business_user">
																					<div v-if="user_group !== '管理员'">
							{{ get_user_session_business_user(form['business_user']) }}
							<el-select v-if="(form['online_mall_id'] && $check_field('set','business_user')) || (!form['online_mall_id'] && $check_field('add','business_user'))" id="business_user" v-model="form['business_user']" :disabled="disabledObj['business_user_isDisabled']">
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
																<el-col v-if="$check_field('get','commodity_specifications') || $check_field('add','commodity_specifications') || $check_field('set','commodity_specifications')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="商品规格" prop="commodity_specifications">
															<el-input id="commodity_specifications" v-model="form['commodity_specifications']" placeholder="请输入商品规格"
							  v-if="(form['online_mall_id'] && $check_field('set','commodity_specifications')) || (!form['online_mall_id'] && $check_field('add','commodity_specifications'))" :disabled="disabledObj['commodity_specifications_isDisabled']">
				  					</el-input>
					<div v-else-if="$check_field('get','commodity_specifications')">
						{{form['commodity_specifications']}}
				  					</div>
														</el-form-item>
			</el-col>
									
	
	
	
		
		
	
	
	
	            <el-col :xs="24" :sm="24" :lg="24" class="el_form_editor_warp">
                <el-form-item label="正文" prop="cart_content">
                    <quill-editor v-model="form.cart_content">
                    </quill-editor>
                </el-form-item>
            </el-col>
			<el-col v-show="$check_option('/goods/table', 'seller')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="上架状态" prop="product_label">
					<el-switch v-model="form.list_status" :active-value="1" :inactive-value="0" />
				</el-form-item>
       		</el-col>
		
		
		
		</el-row>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/online_mall/view','set') || $check_action('/online_mall/view','add')">
					<el-button type="primary" @click="submitCheckNew">提交</el-button>
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
				field: "online_mall_id",
				url_add: "~/api/online_mall/add?",
				url_set: "~/api/online_mall/set?",
				url_get_obj: "~/api/online_mall/get_obj?",
				url_get_list: "~/api/online_mall/get_list?",
				url_upload: "~/api/online_mall/upload?",

				query: {
					"online_mall_id": 0,
					like: 0,
				},

				form: {
								"business_user": 0, // 商家用户
										"commodity_specifications":  '', // 商品规格
							                    cart_title: "",
                    cart_img: "",
                    cart_description: "",
							cart_integral:0,
		                    cart_price_ago: 0,
                    cart_price: 0,
                    cart_inventory: 0,
		                    cart_type: '',
                    cart_content: "",
                    cart_img_1: "",
                    cart_img_2: "",
                    cart_img_3: "",
                    cart_img_4: "",
                    cart_img_5: "",
						"online_mall_id": 0, // ID
																		"create_by": this.$store.state.user.user_id,
				},
				disabledObj:{
								"business_user_isDisabled": false,
										"commodity_specifications_isDisabled": false,
										},

							// 用户列表
				list_user_business_user: [],
						// 用户组
				group_user_business_user: "",
														                list_cart_goods_type: [],
				list_cart_goods_type_tree: [], // 树形分类数据
				cascader_props: {
					value: 'name', // 使用分类名称作为值
					label: 'name',
					children: 'children',
					expandTrigger: 'hover',
					emitPath: false // 只返回最后一级的值
				},
				goods_form:{
					goods_id: 0,
					title: "",
					img: "",
					description: "",
					price_ago: 0,
					price: 0,
					inventory: 0,
						integral:0,
						type: '',
					content: "",
					img_1: "",
					img_2: "",
					img_3: "",
					img_4: "",
					img_5: "",
					source_table: "",
					source_field: "",
					source_id: 0,
					user_id:this.$store.state.user.user_id
				},
				rules:{
					"cart_title": [
						{required: true, message: '请输入标题', trigger: 'blur'},
					],
					"cart_description": [
						{required: true, message: '请输入描述', trigger: 'blur'},
					],
					"cart_price_ago": [
						{required: true, message: '请输入原价', trigger: 'blur'},
					],
						"cart_integral": [
						{required: true, message: '请输入积分', trigger: 'blur'},
					],
						"cart_price": [
						{required: true, message: '请输入卖价', trigger: 'blur'},
					],
					"cart_inventory": [
						{required: true, message: '请输入库存', trigger: 'blur'},
					],
					"cart_type": [
						{required: true, message: '请选择分类', trigger: 'change'},
					],
					"cart_content": [
						{required: true, message: '请输入正文', trigger: 'blur'},
					]
				},
				cartImgList: [],
				previewVisible: false,
				previewImageUrl: undefined,
				// 规格
				skuData: [],
				skuStatus: 0,
				skuDataDefault: [
					{
					sku_title: null,
					sku_price: null,
					sku_price_ago: null,
					sku_inventory: null,
					sku_integral: null,
					sku_group_price: null,
					sku_group_people: null,
					},
				],


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
																				let cartImgList = [];
				for (let i = 1; i <= 5; i++) {
					const field = `cart_img_${i}`;
					if (this.form[field]) {
						cartImgList.push({
							uid: Date.now() + i,
							name: `主图`+ i,
							url: this.$fullUrl(this.form[field])
						});
					}
				}
				this.cartImgList = cartImgList;
				this.form.sku = json.result.obj.sku;

				let mallSku = JSON.parse(json.result.obj.sku);

				this.skuStatus = mallSku.length ? 1 : 0;
				this.skuStatus === 1 && this.setSkuDataDefault();

			},

																										async submit(param, func){
								if (!param) {
					param = this.form;
				}
				if(!this.form.online_mall_id){
					param.create_by = this.$store.state.user.user_id;
				}
												param.sku = this.skuStatus ? JSON.stringify(this.skuData) : null;
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
								if(!this.form.online_mall_id){
						  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  							  		  																											  		  							  		  							  		  																				  		  							  		  							  		  																				  		  							  		  							  		  							  		  							  		  																				  		  							  		  							  		  							  		  																											  		  							  		  							  		  							  		  														let message_inform = {
								title: '网上商城',
								type: '消息',
								content: this.$store.state.user.username + '-' + this.$store.state.user.nickname + '提交了一条网上商城数据',
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
					bl = this.$check_action('/online_mall/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/online_mall/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/online_mall/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/online_mall/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/online_mall/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			submitCheckNew(){
				let that=this;
				if (!this.form.cart_img) {
					this.$toast("请上传封面图", "error");
					return false;
				}
				if (this.cartImgList.length < 1) {
					this.$toast("请至少上传一张主图", "error");
					return false;
				}
				this.$refs["form"].validate((valid) => {
					if (valid) {
						// 使用mixin里的submit方法
						that.events("submit")
					} else {
						console.error('error 提交失败!!');
					}
				});
			},
            /**
             * 获取商品分类
             */
            async get_cart_goods_type() {
                var res = await this.$get("~/api/goods_type/get_list?");
                if (res.result && res.result.list) {
					this.list_cart_goods_type = res.result.list;
					// 构建树形结构
					this.list_cart_goods_type_tree = this.buildTree(res.result.list, 0);
				}
            },
			/**
			* 构建树形结构
			*/
			buildTree(list, fatherId) {
				const tree = [];
				for (let item of list) {
					if (item.father_id === fatherId) {
						const children = this.buildTree(list, item.type_id);
						tree.push({
							type_id: item.type_id,
							name: item.name,
							children: children.length > 0 ? children : undefined
						});
					}
				}
				return tree;
			},
            /**
             * 上传封面图
             * @param {Object} param
             */
            upload_cart_img(param) {
                this.uploadFile(param.file, "cart_img");
            },
			/**
			* 处理图片删除
			*/
			handleRemove(file, fileList) {
				this.cartImgList = [...fileList];
				this.updateFormImages();
			},
			/**
			* 根据当前图片列表更新表单中的图片字段
			*/
			updateFormImages() {
				for (let i = 1; i <= 5; i++) {
					this.form[`cart_img_${i}`] = "";
				}
				this.cartImgList.forEach((item, index) => {
					if (index < 5) {
						let relativeUrl = "";
						if (item.response && item.response.result && item.response.result.url) {
							relativeUrl = item.response.result.url;
						} else if (item.url) {
							relativeUrl = this.$fullUrl(item.url, true);
						}
						this.form[`cart_img_` + (index + 1)] = relativeUrl;
					}
				});
			},
            /**
             * 上传文件
             * @param {Object} param
             */
            upload_cart_img_1(param) {
				const file = param.file;
				const formData = new FormData();
				formData.append("file", file);
				this.$post(this.url_upload, formData, (res) => {
					if (res.result && res.result.url) {
						const newFileObj = {
							uid: file.uid || Date.now(),
							name: file.name,
							url: this.$fullUrl(res.result.url),
							response: { result: { url: res.result.url } }
						};
						this.cartImgList.push(newFileObj);
						this.updateFormImages();
					} else {
						console.error("上传图片失败:", res);
						this.$message.error("上传图片失败" );
					}
				}, (error) => {
					console.error("上传请求失败:", error);
					this.$message.error("上传请求失败");
				});
			},

            /**
             * 上传文件
             * @param {Object} param
             */
            upload_cart_img_2(param) {
                this.uploadFile(param.file, "cart_img_2");
            },

            /**
             * 上传文件
             * @param {Object} param
             */
            upload_cart_img_3(param) {
                this.uploadFile(param.file, "cart_img_3");
            },

            /**
             * 上传文件
             * @param {Object} param
             */
            upload_cart_img_4(param) {
                this.uploadFile(param.file, "cart_img_4");
            },

            /**
             * 上传文件
             * @param {Object} param
             */
            upload_cart_img_5(param) {
                this.uploadFile(param.file, "cart_img_5");
            },

            submit_after(){
                console.log(this.form[this.field])
                if (this.form[this.field]){
                    this.submit_goods(this.form[this.field],"set");
                } else {
                    let query_form = {
                        cart_title:this.form.cart_title,
                        cart_description:this.form.cart_description
                    }
                    this.$get("~/api/online_mall/get_obj?",query_form,(res)=>{
                        console.log("商品息res",res);
                        if(res.result && res.result.obj){
                            this.submit_goods(res.result.obj[this.field],"add");
                        }else if(res.error){
                            console.error(res.error);
                            this.$toast(res.error.message,"error");
                        }
                    })
                }
            },
            submit_goods(source_id,add_flag){
				let customize_field = []
							customize_field.push({"field_name":"商家用户","field_value":this.form.business_user,"type":"uid"});
									customize_field.push({"field_name":"商品规格","field_value":this.form.commodity_specifications});
							if (this.form.sku) {
				 						customize_field.push({"field_name":"sku","field_value":this.form.sku,"type":"sku"});
									}
                this.goods_form = {
                    goods_id: 0,
                    title: this.form.cart_title,
                    img: this.form.cart_img,
                    description: this.form.cart_description,
                    price_ago: this.form.cart_price_ago,
						integral: this.form.cart_integral,
	                    price: this.form.cart_price,
	                    inventory: this.form.cart_inventory,
                    type: this.form.cart_type,
                    content: this.form.cart_content,
                    img_1: this.form.cart_img_1,
                    img_2: this.form.cart_img_2,
                    img_3: this.form.cart_img_3,
                    img_4: this.form.cart_img_4,
                    img_5: this.form.cart_img_5,
                    source_table: "online_mall",
                    source_field: this.field,
                    source_id: source_id,
					customize_field: JSON.stringify(customize_field),
					user_id:this.$store.state.user.user_id,
					list_status: this.form.list_status,
                }
				if (this.skuStatus === 1) {
					// 取最低价
					const minPrice = Math.min(...this.skuData.map(item => parseInt(item.sku_price)));
					this.goods_form.price = minPrice;
					// 取最低价的原价
					const minPriceItem = this.skuData.find(item => parseFloat(item.sku_price) === minPrice);
					if (minPriceItem) {
						this.goods_form.price_ago = parseFloat(minPriceItem.sku_price_ago);
					}
						// 取最低积分
					const minIntegral = Math.min(...this.skuData.map(item => parseInt(item.sku_integral)));
					this.goods_form.integral = minIntegral;
							// 取总库存，负库存的不统计
					this.goods_form.inventory = this.skuData.reduce((total, item) => {
						const stock = parseInt(item.sku_inventory);
						return total + (stock > 0 ? stock : 0);
					}, 0);
				}
                if (add_flag==='set'){
                    delete this.goods_form.goods_id;
					delete this.goods_form.user_id;
                    // 提交事件
                    this.$post("~/api/goods/set?source_table=online_mall&source_field=" + this.field + "&source_id=" + source_id,this.goods_form,(res)=>{
                        console.log("提交结果：" ,res)
                        if(res.result){
                            this.$toast("修改成功!" ,"success");
							this.$router.push("/online_mall/table");
                        }else if(res.error){
                            console.error(res.error);
                            this.$toast(res.error.message,"error");
                        }
                    });
                }else {
                    // 提交事件
                    this.$post("~/api/goods/add?",this.goods_form,(res)=>{
                        console.log("提交结果：" ,res)
                        if(res.result){
                            this.$toast("添加成功!" ,"success");
							this.$router.push("/online_mall/table");
                        }else if(res.error){
                            console.error(res.error);
                            this.$toast(res.error.message,"error");
                        }
                    });
                }
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
			skuStatusChange(val) {
				if (val === 1 && !this.skuData.length) {
					this.setSkuDataDefault();
				}
			},
			setSkuDataDefault() {
				if (this.form.sku) {
					this.skuData = JSON.parse(this.form.sku);
					console.log(this.skuData);
				} else {
					this.skuData.push(JSON.parse(JSON.stringify(this.skuDataDefault[0])));
				}
			},
			addSku() {
				this.skuData.push(JSON.parse(JSON.stringify(this.skuDataDefault[0])));
			},
			delSku(idx) {
				this.skuData.splice(idx, 1);
			},
													},
		created() {
					this.get_list_user_business_user();
					this.get_group_user_business_user();
									            this.get_cart_goods_type();
		},
		mounted(){
			this.$refs["form"].resetFields();
		}
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
