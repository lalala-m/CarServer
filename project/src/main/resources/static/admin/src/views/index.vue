<template>
	<div class="page_root" id="root_index">
		<div class="warp">
			<div class="container-fluid">
				<el-row>
<!--					<el-col :span="4">-->
<!--						<div_label bg_color="bg_red" icon="el-icon-message" :url="url_message_count" unit="条"-->
<!--								  title="留言信息"></div_label>-->
<!--					</el-col>-->
					<!--<el-col :span="4">
						<div_label bg_color="bg_purple" icon="el-icon-user-solid" :url="url_user_count" unit="人"
								  title="用户数量"></div_label>
					</el-col>-->
<!--					<el-col :span="4">-->
<!--						<div_label bg_color="bg_green" icon="el-icon-view" :url="url_article_hits" unit="次"-->
<!--								  title="文章浏览量"></div_label>-->
<!--					</el-col>-->
				</el-row>

				<el-row>
					<el-col :class="'home_user'" :span="8">
						<div class="card info">
							<div class="UserInfo">
								<el-row class="UserInfo-row" style="padding: 0 10px;">
								<el-col :span="8"
									><img :src="$fullUrl(user.avatar)" alt=""
								/></el-col>
								<el-col :span="12">
									<p>用户昵称：{{ user.nickname || user.username }}</p>
									<p>用户身份：{{ user_group }}</p>
								</el-col>
								</el-row>
								<div class="UserInfo-btn" style="padding: 0 10px;">
								<el-button @click="$router.push('/user/info')"
									>修改资料</el-button
								>
								<el-button @click="$router.push('/user/password')"
									>修改密码</el-button
								>
								<el-button @click="sign_out_fun">退出</el-button>
								</div>
								<p class="logotime">
								登录时间：{{
									$toTime(user.login_time, "yyyy-MM-dd hh:mm:ss")
								}}
								</p>
							</div>
						</div>
          			</el-col>
					<el-col :class="'home_overview'" v-if="user_group == '管理员'" :span="8">
						<div class="statistics card">
							<el-row>
								<el-col :span="4" v-for="(item, index) in dataScreen" :key="index">
									<div class="sumList">
										<p>{{ item.value }}</p>
										<p>{{ item.label }}</p>
										<p><i class="el-icon-position"></i></p>
									</div>
								</el-col>
							</el-row>
						</div>
					</el-col>
        		</el-row>
        		<el-row>
					<quickMenu />
        		</el-row>
        <el-row>
																																	<el-col v-if="$check_figure('/order/table')" :span="8">
						<div v-loading="line_obj_shop_money.loading" class="card chart">
							<el-date-picker
									v-model="line_obj_shop_money.month"
									:default-time="['00:00:00', '23:59:59']"
									align="right"
									end-placeholder="结束日期"
									start-placeholder="开始日期"
									type="datetimerange"
									format="yyyy-MM-dd HH:mm:ss"
									@change="changeMonth($event, line_obj_shop_money)">
							</el-date-picker>
							<span v-if="!line_obj_shop_money.loading" style="margin-left: 20px;display: inline!important;">总销售金额：{{line_obj_shop_money.total}}</span>
							<newLineChart :color="['#fff000']" v-if="line_obj_shop_money.values.length > 0" id="line_obj_shop_money"
										  :timeout="0" :title="'商品销售金额统计'" :vm="line_obj_shop_money" style="margin-top: 20px;height: 300px">
							</newLineChart>
						</div>
					</el-col>
					<el-col v-if="$check_figure('/order/table')" :span="8">
						<div v-loading="bar_obj_shop_num.loading" class="card chart">
							<el-date-picker
									v-model="bar_obj_shop_num.month"
									:default-time="['00:00:00', '23:59:59']"
									align="right"
									end-placeholder="结束日期"
									start-placeholder="开始日期"
									type="datetimerange"
									format="yyyy-MM-dd HH:mm:ss"
									@change="changeMonth($event, bar_obj_shop_num)">
							</el-date-picker>
							<span v-if="!bar_obj_shop_num.loading" style="margin-left: 20px">总销售数量：{{bar_obj_shop_num.total}}</span>
							<newBarChart v-if="bar_obj_shop_num.values.length > 0" id="bar_obj_shop_num"
										 :timeout="0" :title="'商品销售数量统计'" :vm="bar_obj_shop_num" style="margin-top: 20px;height: 300px">
							</newBarChart>
						</div>
					</el-col>
					<el-col v-if="$check_figure('/order/table')" :span="8">
						<div v-loading="circle_obj_shop_type.loading" class="card chart">
							<el-date-picker 
								v-model="circle_obj_shop_type.month"
								:default-time="['00:00:00', '23:59:59']" 
								align="right" 
								end-placeholder="结束日期"
								start-placeholder="开始日期" 
								type="datetimerange" 
								format="yyyy-MM-dd HH:mm:ss"
								@change="changeMonthForType($event, circle_obj_shop_type)">
							</el-date-picker>
							<doughnutChart v-if="circle_obj_shop_type.values.length > 0" id="circle_obj_shop_type"
								:title="'商品销售分类统计'" :list="circle_obj_shop_type.values" :type="商品类型" 
								style="margin-top: 20px;height: 300px">
							</doughnutChart>
							<div v-else style="height: 80%;display: flex;justify-content: center;align-items: center;">暂无数据</div>
						</div>
					</el-col>
					
					<el-col :span="24">
						<div class="screen-container">
							<iframe ref="dynamicIframe" v-if="iframeSrc" :src="iframeSrc" frameborder="0" :style="{ width: '100%', border: 'none', height: 'auto' }" @load="adjustIframeHeight"></iframe>
						</div>
					</el-col>
                                                        				</el-row>
	<el-row style="padding: 8px;">
	  <schedule />
	</el-row>

			</div>
		</div>
	</div>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import newLineChart2 from "@/components/charts/new_line_chart2";
	import quickMenu from "@/components/diy/quick_menu.vue";
	import doughnutChart from "@/components/charts/doughnut_chart";
	import newBarChart from "@/components/charts/new_bar_chart";
	import newLineChart from "@/components/charts/new_line_chart";
	import schedule from "@/components/charts/schedule.vue"
	import div_label from "@/components/div_label.vue";
	export default {
		mixins: [mixin],
		name: "Home",
		components: {
			newLineChart2,
			quickMenu,
			doughnutChart,
			newBarChart,
			newLineChart,
			schedule,
			div_label
		},
		data() {
			return {
				activeName: "third",
				iframeSrc: '',
				line_obj_shop_money: {
					type: "money",
					names: ["销售金额"],
					xAxis: [],
					values: [],
					month: [],
					loading: false,
					total: 0
				},
				bar_obj_shop_num: {
					type: "num",
					names: ["销售数量"],
					xAxis: [],
					values: [],
					month: [],
					loading: false,
					total: 0
				},
				circle_obj_shop_type: {
					type: "category",
					names: [],
					values: [],
					month: [],
					loading: false,
					total: 0,
				},
				url_user_count: "~/api/user/count?",
				url_article_hits: "~/api/article/sum?field=hits",
				dataScreen: null,
				sumList: [
									{
						label: "访问总数",
						value: "111",
						icon: "Position",
						field: "hits",
					},
					{
						label: "轮播图数",
						value: "111",
						icon: "Position",
						field: "slides",
					},
										{
						label: "收藏数",
						value: "111",
						icon: "Position",
						field: "collect",
					},
															{
						label: "评论数",
						value: "111",
						icon: "Position",
						field: "comment",
					},
															{
						label: "点赞数",
						value: "111",
						icon: "Position",
						field: "praise",
					},
															{
						label: "公告数",
						value: "111",
						icon: "Position",
						field: "notice",
					},
													],
																												month_list: [
					"一月",
					"二月",
					"三月",
					"四月",
					"五月",
					"六月",
					"七月",
					"八月",
					"九月",
					"十月",
					"十一月",
					"十二月",
				],
				now_month: new Date().getMonth() + 1,
				monthDateList: [],
		  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  				  							};
		},
		created() {
			this.getMonthDateList();
			this.get_list_count();
			this.get_order_month_some_price(null, this.line_obj_shop_money)
			this.get_order_month_some_price(null, this.bar_obj_shop_num)
			this.get_shop_type_statistics(null, this.circle_obj_shop_type);
			this.initScreenIframe()
																														},
		methods: {
			initScreenIframe() {
				let href = window.location.href;
				if (href.indexOf('/admin/dist/index.html') !== -1) {
					href = href.split('/admin/dist/index.html')[0]
					href = href + '/admin/dist/screen/main.html'
				} else {
					href = window.location.origin + '/screen/main.html'
				}
				this.iframeSrc = href + '?user_id=' + this.$store.state.user.user_id + "&user_group=" + this.$store.state.user.user_group
			},
			adjustIframeHeight() {
				const iframe = this.$refs.dynamicIframe;
				try {
					const contentHeight = iframe.contentDocument.body.scrollHeight;
					iframe.style.height = contentHeight + 'px';
					// 监听内部 DOM 变化（动态内容）
					const observer = new MutationObserver(() => {
						const newHeight = iframe.contentDocument.body.scrollHeight;
						iframe.style.height = newHeight + 'px';
					});

					observer.observe(iframe.contentDocument.body, {
						childList: true, // 监听子元素变化
						subtree: true, // 监听所有后代元素
						attributes: true, // 监听属性变化
					});

					// 在组件销毁时停止监听（避免内存泄漏）
					this.$once('hook:beforeDestroy', () => {
						observer.disconnect();
					});
				} catch (e) {
					console.error("无法访问DOM，可能是跨域限制", e);
				}
			},
			async get_nickname(list,flag){
				if (flag) {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i],
								null,
								(json) => {
									if (json.result) {
										list[i] = json.result.obj.nickname;
									}
								});
					}
				}else {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i].name,
								null,
								(json) => {
									if (json.result) {
										list[i].name = json.result.obj.nickname;
									}
								});
					}
				}
			},
			// 获取指定日期的数据
			async get_order_month_some_price(date, o) {

				function getDateBetween(start, end) {
					var result = [];
					//使用传入参数的时间
					var startTime = new Date(start);
					var endTime = new Date(end);
					while (endTime - startTime >= 0) {
						let year = startTime.getFullYear();
						let month = startTime.getMonth();
						month = month < 9 ? '0' + (month + 1) : month + 1;
						let day = startTime.getDate().toString().length === 1 ? "0" + startTime.getDate() : startTime.getDate();
						//加入数组
						result.push(year + "-" + month + "-" + day);
						//更新日期
						startTime.setDate(startTime.getDate() + 1);
					}
					return result;
				}

				const now = new Date();
				const nowYear = now.getFullYear();
				const nowMonth = now.getMonth() + 1;
				const nowDate = now.getDate();
				let days = []
				if(date){
					days = getDateBetween(date[0], date[1])
				}else{
					o.month[0] = new Date(nowYear, nowMonth - 1, 1, 0, 0, 0)
					o.month[1] = new Date(nowYear, nowMonth - 1, nowDate, 23, 59, 59)
					days = getDateBetween(o.month[0], o.month[1])
				}

				o.xAxis = []
				o.values = []
				o.loading = true
				let list = {
					result: [],
					total: 0
				}
				let field = o.type === "money" ? "price_count" : "num"
				for (let i = 0; i < days.length; i++) {
					const t = new Date(days[i])
                	const time = t.getFullYear() + "-" + (t.getMonth()+1) + "-" + t.getDate()
					o.xAxis.push((t.getMonth()+1) + "-" + t.getDate())
					await this.get_order_sum_price_sub(list, time, i, field)
				}
				o.loading = false
				o.values = list.result
				o.total = list.total
			},
			// 获取某一天的销售数量
			async get_order_sum_price_sub(list, date, index, field) {
				let time = new Date(date).toStr("yyyy-MM-dd");
				var $where = ""
				if (this.user.user_group != "管理员")
						$where = '&merchant_id=' + this.user.user_id
				const json = await this.$get(
						`~/api/order/sum?field=${field}&create_time_min=${time} 00:00:00&create_time_max=${time} 23:59:59&state=已完成` + $where,
						null,
				);
				list.result[index] = []
				if (json.result) {
					list.total += json.result
					list.result[index].push(json.result)
				} else {
					list.result[index].push(0)
				}
			},

			// 选择日期
			changeMonth(e, o) {
				this.get_order_month_some_price(e, o)
			},
			// 获取商品销售类型统计
			async get_shop_type_statistics(date, o) {
				const now = new Date();
				const nowYear = now.getFullYear();
				const nowMonth = now.getMonth() + 1;
				const nowDate = now.getDate();

				let create_time_min, create_time_max;

				if (date && date.length === 2) {
					// 修复日期格式化
					create_time_min = new Date(date[0]).toStr("yyyy-MM-dd hh:mm:ss");
					create_time_max = new Date(date[1]).toStr("yyyy-MM-dd hh:mm:ss");
				} else {
					create_time_min = nowYear + '-' + nowMonth.toString().padStart(2, "0") + '-01 00:00:00';
					create_time_max = nowYear + '-' + nowMonth.toString().padStart(2, "0") + '-' + nowDate + ' 23:59:59';
					o.month = [new Date(create_time_min), new Date(create_time_max)];
				}

				o.loading = true;
				
				try {
					var $where = ""
					if (this.user.user_group != "管理员")
					$where = '&merchant_id=' + this.user.user_id
					
					const json = await this.$get(
					`~/api/order/list_group?groupby=type&create_time_min=${create_time_min}&create_time_max=${create_time_max}&state=已完成` + $where, 
					null
					);
					
					if (json.result && json.result.list) {
					o.values = json.result.list.map(function (item) {
						return {
						value: item.count,
						name: item.type || '未知类型'
						};
					});
					o.total = o.values.reduce(function (sum, item) {
						return sum + item.value;
					}, 0);
					} else {
					o.values = [];
					o.total = 0;
					}
				} catch (error) {
					console.error("获取商品类型统计失败:", error);
					o.values = [];
					o.total = 0;
				}
				
				o.loading = false;
			},
			changeMonthForType(e, o) {
				this.get_shop_type_statistics(e, o);
			},
			// 数据预览模块
			get_list_count() {
				this.dataScreen = [...this.sumList, ...this.auth()];
				for (let i = 0; i < this.dataScreen.length; i++) {
					let url;
					let obj = this.dataScreen[i];
					if (
						obj.field != "normal_user" &&
						obj.field !== "freeze_user" &&
						obj.field !== "admin_user"
					) {
						url = `~/api/${obj.field}/count`;
						this.$get(url).then((json) => {
							if (json.result !== null) {
								obj.value = json.result;
							}
						});
					} else {
						// 正常账户，冻结账户数据获取
						let data = {};
						url = `~/api/user/count`;
						if (obj.field === "normal_user") {
							data = {
								state: 1,
							};
						} else if (obj.field === "freeze_user") {
							data = {
								state: 2,
							};
						} else {
							data = {
								user_group: "管理员",
							};
						}
						this.$get(url, data).then((json) => {
							if (json.result !== null) {
								obj.value = json.result;
							}
						});
					}
				}
			},
			sign_out_fun() {
				this.$get("~/api/user/quit", {}, (res) => {
					this.$store.commit("quit");
					this.$router.push("/login");
				});
			},
			auth() {
				// 从localStorage获取并解析权限数据
				let tables = this.$store.state.web.auth;
				let lt = [];
				let site_list = [];
				let IconArr = [
					"Edit",
					"Document",
					"Menu",
					"Location",
					"Setting",
					"Search",
				];
																																																																																															site_list.push("test_drive_record")
																			site_list.push("purchase_order")
																												site_list.push("maintenance_order")
																																					site_list.push("write_off_information")
																																																							site_list.push("reservation_record")
																																														site_list.push("inbound_record")
																			site_list.push("outbound_record")
																															
				// 初始化表格列表并添加额外项
				let list = [
											"article",
					"article_type",
					"comment",
								"goods", // 移除原数组中的空元素
					"order",
									"logistics_delivery",
															"forum",
					"forum_type",
											"notice",
																];
																																																																																																																																																																																																																																																												list.push("online_mall");
																																	list.push("owner_user");
																list.push("business_user");
																list.push("financial_user");
																list.push("manager_user");
																list.push("sales_manager");
																																																																																																																																																																												// 处理表格权限数据
				for (let i = 0; i < tables.length; i++) {
					let o = tables[i];
					if (o.path && o.path.indexOf("/table") !== -1) {
						// 添加path存在性检查
						if (list.indexOf(o.table_name) === -1) {
							o.has_add = site_list.indexOf(o.table_name) === -1;
							o.Icon = IconArr[parseInt(Math.random() * IconArr.length)];
							lt.push({
								label: o.mod_name,
								value: "111",
								icon: o.Icon,
								field: o.table_name,
							});
						}
					}
				}
				return lt; // 返回处理后的权限列表
			},
			// 获取当年1-6月最大，最小日期
			getMonthDateList() {
				// 将Vue3的month_list.value替换为Vue2的this.month_list
				let monthList =
					this.month_list.slice(this.now_month - 6, this.now_month).length >= 6
						? this.month_list.slice(this.now_month - 6, this.now_month)
						: this.month_list
								.slice(
									this.month_list.length -
										(6 - this.month_list.slice(0, this.now_month).length),
									this.month_list.length
								)
								.concat(this.month_list.slice(0, this.now_month));

				// 将Vue3的monthDateList.value替换为Vue2的this.monthDateList
				this.monthDateList = [];

				if (this.now_month >= 5) {
					let index = 0;
					const currentYear = new Date().getFullYear();
					for (let i = this.now_month - 5; i <= this.now_month; i++) {
						let maxDay = new Date(currentYear, i, 0).getDate();
						this.monthDateList.push({
							name: index,
							month: monthList[index],
							create_time_min:
								currentYear +
								"-" +
								i.toString().padStart(2, "0") +
								"-01 00:00:00",
							create_time_max:
								currentYear +
								"-" +
								i.toString().padStart(2, "0") +
								"-" +
								maxDay +
								" 23:59:59",
						});
						index++;
					}
				} else {
					const currentYear = new Date().getFullYear();
					const lastYear = currentYear - 1;
					let index = 0;
					// 循环去年
					for (let i = 12 - (6 - (this.now_month + 1)); i <= 12; i++) {
						let maxDay = new Date(lastYear, i, 0).getDate();
						this.monthDateList.push({
							name: index,
							month: monthList[index],
							create_time_min:
								lastYear + "-" + i.toString().padStart(2, "0") + "-01 00:00:00",
							create_time_max:
								lastYear +
								"-" +
								i.toString().padStart(2, "0") +
								"-" +
								maxDay +
								" 23:59:59",
						});
						index++;
					}
					// 循环今年
					for (let i = 1; i <= this.now_month; i++) {
						let maxDay = new Date(currentYear, i, 0).getDate();
						this.monthDateList.push({
							name: index,
							month: monthList[index],
							create_time_min:
								currentYear +
								"-" +
								i.toString().padStart(2, "0") +
								"-01 00:00:00",
							create_time_max:
								currentYear +
								"-" +
								i.toString().padStart(2, "0") +
								"-" +
								maxDay +
								" 23:59:59",
						});
						index++;
					}
				}
			},
															  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  		  				},
		computed:{
			recognitionHeight(){
				return "830px"
			},
			recognitionUrl(){
				return "https://www.faceplusplus.com.cn/${model.filter.recognitionType}/"
			}
		}
	};
</script>

<style scoped="scoped">
	.chart {
		display: block;
		width: 100%;
		height: 400px;
		padding: 1rem;
		position: relative;
	}

	.el-col {
		padding: 0.5rem;
	}

	.card {
		overflow: hidden;
	}

	.iframe_box ,.iframe_box_change{
		width: 100%;
		height: 1180px;
		position: relative;
		margin-top: 25px;
	}
	.iframe_box_change{
		height: 580px;
		padding-top: 50px;
	}
	.iframe_box	.iframe_box_content, .iframe_box_change .iframe_box_content{
		width: 100%;
		height: 100%;
	}
	.iframe_box_top{
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100px;
		font-size: 25px;
		line-height: 100px;
		background: #fff;
		z-index: 999;
		padding-left: 50px;
	}
	#iframe_box_face div::before {
		content: '';
		width: 100px;
		position: absolute;
		top: 154px;
		right: 129px;
		z-index: 999;
		height: 20px;
		background-color: #FFFFFF;
	}

	#iframe_box_face>h1 {
		margin-top: 100px;
		margin-bottom: 20px;
	}
	.UserInfo p {
		line-height: 30px;
	}

	.UserInfo img {
		width: 100px;
		height: 100px;
	}

	.UserInfo .logotime {
		text-align: center;
		font-size: 14px;
		line-height: 30px;
		border-top: 1px #d9d9d9 solid;
		margin: 10px 0;
	}

	.statistics {
		width: 100%;
		height: 240px;
		overflow-y: auto;
		background-color: #fff;
	}

	.statistics .sumList {
		text-align: center;
		font-size: 12px;
		color: gray;
		border-radius: 8px;
		padding: 5px;
		border: 1px solid gainsboro;
	}
	.home_user .card {
		min-height: 240px;
	}
	.progress-item {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 20px;
  }

  .progress-item .progress-item-content {
    width: 65%;
    display: flex;
    flex-direction: column;
    height: 80px;
    margin-left: 10px;
    font-size: 14px;
  }

  .progress-item .progress-item-content > p {
    margin-bottom: 5px;
  }

  .progress-item .progress-item-content .progress-item-content-progress {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: auto;
  }
</style>
