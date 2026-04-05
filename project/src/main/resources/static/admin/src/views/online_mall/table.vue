<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">


				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="名称">
						<el-input v-model="query.cart_title"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="分类">
						<el-cascader v-model="selectedCategories" :options="list_goods_cart_type" :props="cascaderProps"
							:show-all-levels="false" clearable collapse-tags placeholder="请选择分类"
							@change="handleCategoryChange"></el-cascader>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="上架状态">
						<el-select v-model="query.list_status">
							<el-option label="全部" value=""></el-option>
							<el-option label="上架" value="1"></el-option>
							<el-option label="下架" value="0"></el-option>
						</el-select>
					</el-form-item>
				</el-col>

										<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="商品规格">
									<el-input v-model="query.commodity_specifications"></el-input>
								</el-form-item>
				</el-col>
				</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						
						<el-button v-if="$check_action('/online_mall/table','add') || $check_action('/online_mall/view','add')" @click="$router.push('./view?')">添加</el-button>

						

														<el-button v-if="$check_action('/online_mall/table','del') || $check_action('/online_mall/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                  </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="online_mall_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column fixed prop="cart_title" label="标题" sortable width="100">
	
			</el-table-column>

			<el-table-column prop="cart_img" label="封面图" min-width="110">
				<template slot-scope="scope">
					<el-image style="width: 100px; height: 100px" :src="$fullUrl(scope.row.cart_img)" :preview-src-list="[$fullUrl(scope.row.cart_img)]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</template>
			</el-table-column>

			<el-table-column prop="cart_type" label="产品分类" min-width="180">
			</el-table-column>

			<el-table-column prop="cart_price" label="规格" min-width="550">
				<template slot-scope="scope">
					<div class="sku-title">{{scope.row.sku ? '多规格' : '单规格'}}</div>
					<div v-if="scope.row.sku">
						<div v-for="(o, i) in JSON.parse(scope.row.sku)" :key="i">
							<span class="sku-item sku-name">{{ o.sku_title }}</span>
							<span class="sku-item"><span>卖价：</span>{{ o.sku_price }}</span>
							<span class="sku-item"><span>原价：</span>{{ o.sku_price_ago }}</span>
								<span class="sku-item" v-if="o.sku_integral"><span>积分：</span>{{ o.sku_integral }}</span>
									<span class="sku-item" v-if="o.sku_inventory >= 0"><span>库存：</span>{{ o.sku_inventory }}</span>
						</div>
					</div>
					<div v-else>
						<span class="sku-item"><span>卖价：</span>{{ scope.row.cart_price }}</span>
						<span class="sku-item"><span>原价：</span>{{ scope.row.cart_price_ago }}</span>
							<span class="sku-item" v-if="scope.row.cart_integral"><span>积分：</span>{{ scope.row.cart_integral }}</span>
								<span class="sku-item" v-if="scope.row.cart_inventory >= 0"><span>库存：</span>{{ scope.row.cart_inventory }}</span>
					</div>
				</template>
			</el-table-column>

			<el-table-column prop="cart_description" label="描述" min-width="200">
			</el-table-column>
			<el-table-column prop="list_status" label="上架状态" min-width="120">
				<template slot-scope="scope">
					<el-tag type="success" v-if="scope.row.list_status == 1">上架</el-tag>
					<el-tag type="danger" v-else>下架</el-tag>
				</template>
			</el-table-column>
				<el-table-column prop="business_user" @sort-change="$sortChange" label="商家用户" 				v-if="$check_field('get','business_user')" min-width="300">
						<template slot-scope="scope">
					{{ get_user_business_user(scope.row['business_user']) }}
				</template>
					</el-table-column>
					<el-table-column prop="commodity_specifications" @sort-change="$sortChange" label="商品规格" 				v-if="$check_field('get','commodity_specifications')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["commodity_specifications"] }}
									</template>
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/online_mall/table','set') || $check_action('/online_mall/view','set') || $check_action('/online_mall/view','get')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/online_mall/table','set') || $check_action('/online_mall/view','set') || $check_action('/online_mall/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
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
				<!-- 库存提醒弹窗 -->
		<el-dialog title="库存提醒" :visible.sync="inventoryDialogVisible" width="500px">
			<div style="max-height: 400px; overflow-y: auto;">
				<div v-html="inventoryMessage"></div>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="inventoryDialogVisible = false">确定</el-button>
			</span>
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
				url_get_list: "~/api/online_mall/get_list",
				url_del: "~/api/online_mall/del?",

				// 字段ID
				field: "online_mall_id",
																											// 查询
				query: {
					"size":  7,
					"page": 1,
									"commodity_specifications": "",
								cart_title:"",
					cart_type:"",
					"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 商品分类
				list_goods_cart_type:[],
				inventoryDialogVisible: false,
    			inventoryMessage: '',
				cascaderProps: {
					value: 'name',
					label: 'name',
					children: 'children',
					expandTrigger: 'hover',
				},
				selectedCategories: [],
				// 数据
				list: [],
										// 用户列表
				list_user_business_user: [],
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
			 * 获取商品分类
			 */
			async get_goods_cart_type() {
				var json = await this.$get("~/api/goods_type/get_list?");
				if(json.result){
					this.list_goods_cart_type = this.buildSimpleTree(json.result.list);
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			buildSimpleTree(list) {
				if (!list || list.length === 0) return [];
				const map = {};
				const roots = [];
				// 创建映射
				list.forEach(item => {
					map[item.type_id] = {
						...item,
						name: item.name || item.type_name,
						children: []
					};
				});

				// 构建树
				list.forEach(item => {
					const node = map[item.type_id];
					if (item.father_id && map[item.father_id]) {
						map[item.father_id].children.push(node);
					} else {
						roots.push(node);
					}
				});

				// 清理空的children数组
				const cleanEmptyChildren = (nodes) => {
					nodes.forEach(node => {
						if (node.children && node.children.length === 0) {
							delete node.children; // 删除空的children，这样就不会显示箭头
						} else if (node.children) {
							cleanEmptyChildren(node.children);
						}
					});
				};

				cleanEmptyChildren(roots);
				return roots;
			},
			handleCategoryChange(value) {
				if (value && value.length > 0) {
					this.query.cart_type = value[value.length - 1];
				} else {
					this.query.cart_type = '';
				}
			},
			reset() {
				Object.keys(this.query).forEach(key => {
					if (Array.isArray(this.query[key])) {
						this.query[key] = [];
					} else if (typeof this.query[key] === 'string') {
						this.query[key] = '';
					} else if (typeof this.query[key] === 'number') {
						this.query[key] = 0;
					}
				});

				this.query.size = 7;
				this.query.page = 1;
				this.query.orderby = `create_time desc`;

				this.selectedCategories = [];
				this.get_list();
			},
			// 检查库存方法
			checkInventory() {
				let lowInventoryItems = [];

				this.list.forEach((item) => {
					// 处理sku字段（可能是字符串需要转换）
					let skuArray = [];
					if (item.sku) {
					if (typeof item.sku === 'string') {
						try {
						skuArray = JSON.parse(item.sku);
						} catch (e) {
						console.error('解析sku失败:', e);
						skuArray = [];
						}
					} else if (Array.isArray(item.sku)) {
						skuArray = item.sku;
					}
					}
					
					// 多规格商品库存检查
					if (skuArray.length > 0) {
					const lowInventorySkus = skuArray.filter(sku => {
						const inventory = parseInt(sku.sku_inventory);
						return inventory < 5 && inventory >= 0;
					});
					
					if (lowInventorySkus.length > 0) {
						lowInventoryItems.push({
						product: item,
						lowSkus: lowInventorySkus
						});
					}
					} 
					// 单规格商品的库存检查
					else if (item.cart_inventory < 5 && item.cart_inventory >= 0) {
					lowInventoryItems.push({
						product: item,
						isSingle: true
					});
					}
				});

				if (lowInventoryItems.length > 0) {
					let message = `<p style="margin-bottom: 16px;"><strong>有${lowInventoryItems.length}个产品库存低于5：</strong></p>`;
					
					lowInventoryItems.forEach((lowItem, index) => {
					const product = lowItem.product;
					let num = index + 1;
					message += `<p style="margin: 8px 0;">`+num+`. `+product.cart_title;
					
					if (lowItem.isSingle) {
						message += ` - 库存: `+product.cart_inventory+`件</p>`;
					} else {
						message += ` - 低库存规格:</p><ul style="margin: 5px 0; padding-left: 20px; color: #E6A23C;">`;
						lowItem.lowSkus.forEach(sku => {
						message += `<li>`+sku.sku_title+`: 库存`+sku.sku_inventory+`件</li>`;
						});
						message += `</ul>`;
					}
					});
					
					message += `<p style="margin-top: 16px; color: #F56C6C;"><strong>请尽快增加库存！</strong></p>`;
					
					this.inventoryMessage = message;
					this.inventoryDialogVisible = true;
				}
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
					await this.delAll(list ,async (list)=>{
						var bl = true;
						for(var i=0; i < list.length; i++){
							var form = {
								source_table:"online_mall",
								source_field:this.field,
								source_id:list[i][this.field]
							}
							var res = await this.$get("~/api/goods/del?",form);
							if(res.result){
								console.log("删除成功" + i);
							}
							else{
								console.log("删除失败" + i);
								bl = false;
								break;
							}
						}
						if(bl){
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
							this.get_list();
						}
					});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
	},
		watch: {
		  list() {
					this.$nextTick(() => {
				this.checkInventory();
			});
			  }
		},
		created() {
			this.get_goods_cart_type();
							this.get_list_user_business_user();
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
	.sku-title{
		font-weight: bold;
	}
	.sku-item{
		color: #777;
		margin-right: 10px;
	}
	.sku-name{
		color: #fcca16;
		border: 1px solid #fcca16;
		background: #fcca1617;
		border-radius: 2px;
		min-width: 20px;
		display: inline-block;
		text-align: center;
		line-height: 18px;
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
