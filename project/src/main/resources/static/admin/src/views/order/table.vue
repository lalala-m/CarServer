<template>
	<el-main class="bg table_wrap order">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="12" :lg="6" class="el_form_search_wrap">
					<el-form-item label="订单号">
						<el-input v-model="query.order_number"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="12" :lg="6" class="el_form_search_wrap">
					<el-form-item label="商品名称">
						<el-input v-model="query.title"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="12" :lg="6" class="el_form_search_wrap">
					<el-form-item label="联系人姓名">
						<el-input v-model="query.contact_name"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="12" :lg="6" class="el_form_search_wrap">
					<el-form-item label="状态">
						<el-select v-model="query.state">
							<el-option :key="-1" :value="0" :label="'无'"></el-option>
							<el-option v-for="(o,i) in list_state" :key="i" :value="o" :label="o">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				</el-row>
				<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1 btns">
						
							<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
							<el-button @click="reset()" class="search_btn_reset">重置</el-button>
							<el-button v-if="$check_action('/order/table','del')" class="float-right search_btn_del" type="danger"
							 @click="delInfo()">删除</el-button>
							<el-button @click="export_db('dataTable')">导出</el-button>
					</el-col>
					
				</el-col>

			</el-row>
		</el-form>
		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe id="dataTable">

			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>

			<el-table-column fixed prop="title" label="商品名称" width="120">
			</el-table-column>

			<el-table-column prop="order_number" label="订单号" min-width="200">
			</el-table-column>

			<el-table-column prop="img" label="商品图片" min-width="110">
				<template slot-scope="scope">
					<div class="demo-image__preview">
						<el-image style="width: 100px; height: 100px" :src="$fullUrl(scope.row.img)"
							:preview-src-list="[$fullUrl(scope.row.img)]">
							<div slot="error" class="image-slot">
								<img src="/img/error.png" style="width: 90px; height: 90px" />
							</div>
						</el-image>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="norms" label="规格" sortable min-width="100">
			</el-table-column>
			<el-table-column prop="price" label="价格" sortable min-width="100">
			</el-table-column>

			<el-table-column prop="price_ago" label="原价" sortable min-width="100">
			</el-table-column>

			<el-table-column prop="num" label="购买数量" sortable min-width="120">
			</el-table-column>

			<el-table-column prop="buy_type" label="购买方式" min-width="120">
				<template slot-scope="scope">
					<div >
						{{ scope.row.buy_type != null && scope.row.buy_type == 2?'积分兑换':'全额购买' }}
					</div>
				</template>
			</el-table-column>

			<el-table-column prop="price_count" label="总价" sortable min-width="100">
				<template slot-scope="scope">
					<div >
						{{ scope.row.buy_type == null? !!scope.row.integral? Number(scope.row.integral)*scope.row.num+'积分':scope.row.price_count+'元': scope.row.buy_type == 2? Number(scope.row.integral)*scope.row.num+'积分':scope.row.price_count+'元' }}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="contact_name" label="联系人名称" sortable min-width="120">
			</el-table-column>

			<el-table-column prop="contact_address" label="联系人地址" min-width="180">
			</el-table-column>

			<el-table-column prop="state" label="订单状态" min-width="120">
				<template slot-scope="scope">
					<div v-if="scope.row.state === '待退款'">
						<el-button type="danger" @click="set_state(scope.row)" round>待退款</el-button>
					</div>
					<div v-else-if="scope.row.state === '待付款'" style="flex-direction: column;">
						<el-tag type="warning">待付款</el-tag>
					</div>
					<div v-else-if="scope.row.state === '待发货'" style="flex-direction: column;">
						<el-tag type="warning">待发货</el-tag>
					</div>
					<div v-else-if="scope.row.state === '待签收'" style="flex-direction: column;">
						<el-tag type="warning">待签收</el-tag>
					</div>
					<div v-else-if="scope.row.state === '已完成'" style="flex-direction: column;">
						<el-tag type="success">已完成</el-tag>
					</div>
					<div v-else-if="scope.row.state === '售后退款'" style="flex-direction: column;">
						<el-tag type="danger">售后退款</el-tag>
					</div>
					<div v-else-if="scope.row.state === '已取消'" style="flex-direction: column;">
						<el-tag type="info">已取消</el-tag><span class="cancel-reason">{{scope.row.desc}}</span>
					</div>
					<div v-else>
						<el-tag>{{scope.row.state}}</el-tag>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="user_id" label="买家" sortable min-width="100">
				<template slot-scope="scope">
					<div>
						{{list_user.getVal("nickname",{"user_id":scope.row.user_id})}}
					</div>
				</template>
			</el-table-column>

      <el-table-column prop="remark" label="备注" min-width="180">
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

			<!-- 操作 -->
			<el-table-column fixed="right" label="操作" min-width="200">
				<template slot-scope="scope">
					<div class="view_a">
						<button class="e-button el-button--small is-plain el-button--primary"
							@click="goTo_('./view?' + field + '=' + scope.row[field])" size="small">
						<span>详情</span>
						</button>
						<button v-if="scope.row['state'] == '待发货' && scope.row['delivery_state'] == '未配送' " class="e-button el-button--small is-plain el-button--primary"
							@click="addLogi(scope.row)" size="small">
							<span>配送</span>
						</button>
					</div>
				</template>
			</el-table-column>
			<!-- /操作 -->

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->

		<el-dialog title="退款审批" :visible.sync="refund_view" width="30%" :before-close="handleClose">

			<span>
				<el-form ref="form" :model="form" label-width="80px">
					<el-form-item label="退款状态">
						<el-select v-model="form.state">
							<el-option label="待退款" value="待退款" :key="1"></el-option>
							<el-option label="已退款" value="已退款" :key="2"></el-option>
							<el-option label="已拒绝" value="已拒绝" :key="3"></el-option>
						</el-select>
					</el-form-item>
				</el-form>
			</span>

			<span slot="footer" class="dialog-footer">
				<el-button @click="refund_view = false; form = {state:''}">取消</el-button>
				<el-button type="primary" @click="toSet()">提交</el-button>
			</span>

		</el-dialog>
		<el-dialog :visible.sync="logiDialogVisible" title="订单配送" width="400px" center>
			<el-form :model="logiForm" label-width="auto" style="max-width: 600px">
				<el-form-item label="配送方式">
				<el-radio-group v-model="logiForm.courier_type">
					<el-radio :label="1">商家配送</el-radio>
					<el-radio :label="2" v-if="Array.isArray(courierList) && courierList.length > 0">其他配送</el-radio>
				</el-radio-group>
				</el-form-item>
				<el-form-item label="配送员" v-if="logiForm.courier_type === 2">
					<el-select v-model="logiForm.courier_id" placeholder="请选择配送员">
						<el-option v-for="item in courierList" :key="item.user_id" :label="item.nickname" :value="item.user_id"/>
					</el-select>
				</el-form-item>
				<el-form-item label="配送详情">
					<el-input v-model="logiForm.courier_detail" type="textarea" />
				</el-form-item>
			</el-form>
			<div class="dialog-footer">
				<el-button @click="logiDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="logiFormSubmit"> 确认 </el-button>
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
				url_get_list: "~/api/order/get_list",
				url_set: "~/api/order/set?",
				url_del: "~/api/order/del?",

				// 字段ID
				field: "order_id",

				refund_view: false,

				form: {
					state: ""
				},

				query: {
					size: 10,
					page: 1,
					state: "",
					order_number: "",
					name: "",
					contact_name: "",
				},

				// 数据
				list: [],

				// 获取用户信息
				list_user: [],

				// 状态组
				list_state: ["待付款", "待发货", "配送中", "待签收","已完成","已取消"],
				logiDialogVisible: false,
				logiForm: {
					courier_type: 1,
					courier_id: null,
					courier_name: null,
					courier_detail: null,
				},
				courierList: null,
			}
		},
		methods: {
			goTo_(url) {
				this.$router.push(url)
			},
			get_list_before(){
        if (this.user_group!=='管理员'){
          if (this.$check_option('/goods/table', 'seller')){
            this.url_get_list = "~/api/order/get_business_order_list?user_id="+this.user.user_id;
          }else {
            this.url_get_list = "~/api/order/get_list?user_id="+this.user.user_id;
          }
        }
			},
			/**
			 * 确认关闭
			 * @param {Object} done
			 */
			handleClose(done) {
				var _this = this;
				this.$confirm('确认关闭？')
					.then(_ => {
						_this.form = {
							state: ""
						};
						done();
					})
					.catch(_ => {});
			},

			/**
			 * 修改状态
			 */
			toSet() {
				this.refund_view = false;
				this.set({
					state: this.form.state
				}, {
					order_id: this.form.order_id
				});
				this.form = {
					state: ""
				};
				this.get_list();
			},

			/**
			 * @description 显示退款窗口
			 * @param {Object} o 对象集
			 */
			set_state(o) {
				this.form = Object.assign({}, o);
				this.refund_view = 1;
			},

			/**
			 * @description 删除
			 * @param {Object} index
			 * @param {Object} rows
			 */
			deleteRow(index, rows) {
				rows.splice(index, 1);
			},

			/**
			 * @description 表格样式
			 */
			table_class({
				row,
				column,
				rowIndex,
				columnIndex
			}) {
				return "table_class";
			},

			/**
			 * 获取用户
			 */
			async get_list_user() {
				var json = await this.$get("~/api/user/get_list?");
				if(json.result){
					this.list_user = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			// 配送
			addLogi(data) {
				this.addLogiData = data;
				this.getCourierList();
				this.logiDialogVisible = true;
			},

			async getCourierList() {
				let params = {
					"path": "/logistics_delivery/table",
				}
				const res = await this.$get("~/api/auth/get_list",params);
				if(res.result){
					let data = res.result.list.filter(item => {
						try {
							const options = typeof item.option === 'string' ? JSON.parse(item.option) : item.option;
							return options && options.delivery === true;
						} catch (e) {
							return false;
						}
					});
					const json = await this.$get("~/api/user/get_list");
					const userGroups = data.map(item => item.user_group);
					this.courierList = json.result.list.filter((item) => {
						return userGroups.includes(item.user_group);
					});
				}
				
			},

			logiFormSubmit() {
				var today = new Date();
				var currentDate = today.toStr("yyyy-MM-dd");
				let courier_name = null;
				if (this.logiForm.courier_type === 1) {
					courier_name = "商家配送";
				} else if (this.logiForm.courier_type === 2) {
					courier_name = this.courierList.filter((item) => {
					return item.user_id === this.logiForm.courier_id;
					})[0].nickname;
				}

				var postData = {
					order_number: this.addLogiData.order_number,
					product_name: this.addLogiData.title,
					purchase_quantity: this.addLogiData.num,
					total_transaction_amount: this.addLogiData.price_count,
					the_date_of_issuance: currentDate,
					delivery_number: Number(Math.random().toString().substr(3, 12) + Date.now()).toString(36),
					shipping_address: this.addLogiData.contact_address,
					delivery_status: "配送中",
					signing_status: "配送中",
					logistics_delivery_id: 0,
					contact_name: this.addLogiData.contact_name,
					merchant_id: this.addLogiData.merchant_id,
					ordinary_users: this.addLogiData.user_id,
					courier_type: this.logiForm.courier_type,
					courier_id: this.logiForm.courier_type === 1 ? this.addLogiData.merchant_id : this.logiForm.courier_id,
					courier_name,
					courier_detail: this.logiForm.courier_detail,
				};
				this.$post("~/api/logistics_delivery/add?", postData).then(
					(json, status) => {
					if (json.result) {
						this.$post(
						"~/api/order/set?order_id=" + this.addLogiData.order_id,
						{
							delivery_state: "配送中",
							state: "配送中",
						}
						).then((json_order, status) => {
						if (json_order.result) {
							this.$toast("添加成功！", "success");
										let message_inform = {
							title: `物流更新`,
							type: '通知',
							content: '订单 #' + postData.order_number + ' 已发货，配送单号：' + postData.delivery_number,
							state: 1,
							user_id: this.addLogiData.user_id
						}
						this.$post("~/api/message_inform/add",message_inform)
											this.search();
							setTimeout(() => {
							this.$router.push("/logistics_delivery/table");
							}, 2000);
						} else if (json_order.error) {
							layer.msg(json_order.error.message);
						}
						});
					} else if (json.error) {
						this.$toast(json.error.message, "danger");
					}
					}
				);
				this.logiDialogVisible = false;
			},
			// 导出
			export_db(tableid) {
				try {
					// 获取原始表格
					const originalTable = document.getElementById(tableid);
					if (!originalTable) {
						this.$message.error('找不到表格元素');
						return;
					}

					// 手动构建导出表格（避免重复数据）
					const exportTable = document.createElement('table');
					exportTable.setAttribute('border', '1');
					exportTable.style.borderCollapse = 'collapse';

					// 复制表头（只复制第一个）
					const originalThead = originalTable.querySelector('thead');
					if (originalThead) {
						const thead = document.createElement('thead');
						const originalHeaderRow = originalThead.querySelector('tr');
						if (originalHeaderRow) {
							const headerRow = originalHeaderRow.cloneNode(true);
							thead.appendChild(headerRow);
							exportTable.appendChild(thead);
						}
					}

					// 复制表体数据（只复制第一个tbody）
					const originalTbody = originalTable.querySelector('tbody');
					if (originalTbody) {
						const tbody = document.createElement('tbody');

						// 获取所有数据行
						const originalRows = originalTbody.querySelectorAll('tr');
						originalRows.forEach(row => {
							const newRow = row.cloneNode(true);

							// 处理图片尺寸
							const images = newRow.querySelectorAll('img, .el-image__inner');
							images.forEach(img => {
								img.style.width = '60px';
								img.style.height = '60px';
								img.style.objectFit = 'cover';
								img.style.display = 'block';
								img.style.margin = '0 auto';

								// 设置HTML属性
								img.setAttribute('width', '60');
								img.setAttribute('height', '60');
							});

							tbody.appendChild(newRow);
						});

						exportTable.appendChild(tbody);
					}
					const htmlContent = `
			<html xmlns:o="urn:schemas-microsoft-com:office:office" 
				xmlns:x="urn:schemas-microsoft-com:office:excel" 
				xmlns="http://www.w3.org/TR/REC-html40">
			<head>
				<meta charset="UTF-8">
				<style>
					table { border-collapse: collapse; }
					td, th { border: 1px solid #000; padding: 5px; text-align: center; }
					img { width: 60px !important; height: 60px !important; object-fit: cover !important; display: block !important; margin: 0 auto !important; }
				</style>
			</head>
			<body>${exportTable.outerHTML}</body>
			</html>`;

					const blob = new Blob([htmlContent], {
						type: 'application/vnd.ms-excel;charset=utf-8'
					});

					const link = document.createElement('a');
					link.href = URL.createObjectURL(blob);
					link.download = '订单数据.xls';
					link.click();

					// 清理
					setTimeout(() => {
						URL.revokeObjectURL(link.href);
					}, 1000);

				} catch (error) {
					console.error('导出失败:', error);
					this.$message.error('导出失败');
				}
			}
		},
		created() {
			this.get_list_user();
		}
	}
</script>

<style type="text/css">
	.refund_view {
		top: 0;
	}

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

	.float-right {
		float: right;
	}

	.mr-1 {
		margin-right: 1rem;
	}

	.el-table .table_class {
		background: rgba(150, 150, 150, 0.1);
		text-align: center;
	}

	.text_center {
		text-align: center;
	}

	.float-right {
		float: right;
	}
	.cancel-reason{
		display: block;
		font-size: 12px;
		color: #999;
	}
</style>
