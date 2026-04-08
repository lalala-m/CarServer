<template>
	<el-main class="bg table_wrap comtabel_t">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">



							<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','consumables_code')">
					<el-form-item label="耗材编码">
									<el-input v-model="query.consumables_code"></el-input>
								</el-form-item>
				</el-col>
									<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap" v-if="$check_field('get','consumables_name')">
					<el-form-item label="耗材名称">
									<el-input v-model="query.consumables_name"></el-input>
								</el-form-item>
				</el-col>
													</el-row>
	<el-row class="rows row2">

		<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">

				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">

										<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
																						

														<el-button v-if="$check_action('/inventory_information/table','del') || $check_action('/inventory_information/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
								
				</el-col>
		</el-col>
	</el-row >
	<div class="statistics-info" v-if="statisticsData.total > 0">
                                    </div>

		</el-form>
				<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable" row-key="inventory_information_id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			:default-expand-all="isExpandAll" ref="tableRef">
					<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
				<el-table-column prop="consumables_code" @sort-change="$sortChange" label="耗材编码" 				v-if="$check_field('get','consumables_code')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["consumables_code"] }}
									</template>
					</el-table-column>
					<el-table-column prop="consumables_name" @sort-change="$sortChange" label="耗材名称" 				v-if="$check_field('get','consumables_name')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["consumables_name"] }}
									</template>
					</el-table-column>
					<el-table-column prop="consumables_inventory" @sort-change="$sortChange" label="耗材库存" 				v-if="$check_field('get','consumables_inventory')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["consumables_inventory"] }}
						                <el-tag v-if="scope.row._hasWarning_consumables_inventory"  type="danger" size="small" style="margin-left: 8px;">
                	<i class="el-icon-warning" style="margin-right: 2px;"></i>预警
                </el-tag>
            			</template>
					</el-table-column>
					<el-table-column prop="consumables_specifications" @sort-change="$sortChange" label="耗材规格" 				v-if="$check_field('get','consumables_specifications')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["consumables_specifications"] }}
									</template>
					</el-table-column>
					<el-table-column prop="introduction_of_consumables" @sort-change="$sortChange" label="耗材简介" 				v-if="$check_field('get','introduction_of_consumables')" min-width="300">
					<template slot-scope="scope">
							{{ scope.row["introduction_of_consumables"] }}
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







			<el-table-column fixed="right" label="操作" min-width="200" v-if="$check_action('/inventory_information/table','set') || $check_action('/inventory_information/view','set') || $check_action('/inventory_information/view','get')
						|| $check_action('/inbound_record/table','add') || $check_action('/inbound_record/view','add')
						|| $check_action('/outbound_record/table','add') || $check_action('/outbound_record/view','add')
						" >


				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="$check_action('/inventory_information/table','set') || $check_action('/inventory_information/view','set') || $check_action('/inventory_information/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
						<!--跨表按钮-->
										<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="to_table(scope.row,'/inbound_record/view')" v-if="($check_action('/inbound_record/table','add') || $check_action('/inbound_record/view','add')) && !scope.row['inbound_record_limit'] && !scope.row['inbound_record_status_limit']">
						<span>入库</span>
					</el-button>
													<el-button class="e-button el-button--small is-plain el-button--primary" style="margin: 5px !important;" size="small" @click="to_table(scope.row,'/outbound_record/view')" v-if="($check_action('/outbound_record/table','add') || $check_action('/outbound_record/view','add')) && !scope.row['outbound_record_limit'] && !scope.row['outbound_record_status_limit']">
						<span>出库</span>
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
				url_get_list: "~/api/inventory_information/get_list",
				url_del: "~/api/inventory_information/del?",

				// 字段ID
				field: "inventory_information_id",
																																// 查询
				query: {
					"size":  7,
					"page": 1,
								"consumables_code": "",
											"consumables_name": "",
											"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},

				// 数据
				list: [],
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
									
				
												_this.list.map((item) => {
					let param = {
						source_table: "inventory_information",
						source_id: item.inventory_information_id,
						source_user_id: _this.user.user_id
					};
					if(item.inbound_record_limit_times > 0){
						_this.$get("~/api/inbound_record/count?",param,(result)=>{
							if(result){
								if(result.result >= item.inbound_record_limit_times){
									_this.$set(item,'inbound_record_limit',true);
								}else{
									_this.$set(item,'inbound_record_limit',false);
								}
							}
						})
					}else{
						_this.$set(item,'inbound_record_limit',false);
					}
					Object.assign(item, param)
				})
								_this.list.map((item) => {
					let param = {
						source_table: "inventory_information",
						source_id: item.inventory_information_id,
						source_user_id: _this.user.user_id
					};
					if(item.outbound_record_limit_times > 0){
						_this.$get("~/api/outbound_record/count?",param,(result)=>{
							if(result){
								if(result.result >= item.outbound_record_limit_times){
									_this.$set(item,'outbound_record_limit',true);
								}else{
									_this.$set(item,'outbound_record_limit',false);
								}
							}
						})
					}else{
						_this.$set(item,'outbound_record_limit',false);
					}
					Object.assign(item, param)
				})
																																									},


													open_tip() {
				const h = this.$createElement;

				var message = "";
				var list = this.list;
				// 清空预警标记
				list.forEach(item => {
					this.$set(item, '_hasWarning', false);
															this.$set(item, '_hasWarning_consumables_inventory', false);
													});

				var ifs = [
								{
					title: "耗材库存",
					factor: "consumables_inventory",
							start: -1,
					end: 5,
							type: "数内",
					idx: 0
				},
							];
				for (var n = 0; n < ifs.length; n++) {
					var o = ifs[n];
					o.idx = 0;
					for (var i = 0; i < list.length; i++) {
						var lt = list[i];
						var hasWarning = false;
						if (o.type == "数内") {
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
									hasWarning = true;
								}
							} else if (o.start || o.start === 0) {
								if (lt[o.factor] > o.start) {
									hasWarning = true;
								}
							} else if (o.end || o.end === 0) {
								if (lt[o.factor] < o.end) {
									hasWarning = true;
								}
							}
						} else if (o.type == "数外") {
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
									hasWarning = true;
								}
							} else if (o.start || o.start === 0) {
								if (lt[o.factor] < o.start) {
									hasWarning = true;
								}
							} else if (o.end || o.end === 0) {
								if (lt[o.factor] > o.end) {
									hasWarning = true;
								}
							}
						} else if (o.type == "日内") {
							let currentDate = new Date(lt[o.factor]).getTime();
							let startDate = new Date(o.start).getTime();
							let endDate = new Date(o.end).getTime();
							if (o.start && o.end) {
								if (currentDate > startDate && currentDate < endDate) {
									hasWarning = true;
								}
							} else if (o.start) {
								if (currentDate < startDate) {
									hasWarning = true;
								}
							} else if (o.end) {
								if (currentDate > endDate) {
									hasWarning = true;
								}
							}
						} else if (o.type == "日外") {
							let currentDate = new Date(lt[o.factor]).getTime();
							let startDate = new Date(o.start).getTime();
							let endDate = new Date(o.end).getTime();
							if (o.start && o.end) {
								if (currentDate < startDate || currentDate > endDate) {
									hasWarning = true;
								}
							} else if (o.start) {
								if (currentDate < startDate) {
									hasWarning = true;
								}
							} else if (o.end) {
								if (currentDate > endDate) {
									hasWarning = true;
								}
							}
						}
						if (hasWarning) {
							o.idx = o.idx + 1;
							this.$set(lt, '_hasWarning', true);
							this.$set(lt, '_hasWarning_' + o.factor, true); 
						}
					}

					if (o["idx"]) {
						message += o.title;
						if (o["type"] == "数内") {
							if (o.start || o.start === 0) {
								message += "大于" + o.start;
							}
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								message += "并且";
							}
							if (o.end || o.end === 0) {
								message += "小于" + o.end;
							}
						} else if (o["type"] == "数外") {
							if (o.start || o.start === 0) {
								message += "小于" + o.start;
							}
							if ((o.start || o.start === 0) || (o.end || o.end === 0)) {
								message += "或者";
							}
							if (o.end || o.end === 0) {
								message += "大于" + o.end;
							}
						} else if (o["type"] == "日内") {
							if (o.start) {
								message += "在" + o.start + "之后";
							}
							if (o.start && o.end) {
								message += "并且";
							}
							if (o.end) {
								message += "在" + o.end + "之前";
							}
						} else if (o["type"] == "日外") {
							if (o.start) {
								message += "在" + o.start + "之前";
							}
							if (o.start || o.end) {
								message += "或者";
							}
							if (o.end) {
								message += "在" + o.end + "之后";
							}
						}
						message += "的有" + o["idx"] + "条，";
					}
				}

				if (message) {
					message += "请尽快处理。";
					this.showModal = true;
					this.message = message
				}
			},
			/**
			 * 页数变更
			 * @param {Object} page
			 */
			handleCurrentChange(page) {
			    this.query.page = page;
			    this.get_list();
					setTimeout(() => {
					this.open_tip();
				}, 1000)
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
													setTimeout(() => {
				this.open_tip();
			}, 1000)
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
