<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce">
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="店面名称" prop="store_name">
					<el-input v-model="form.store_name" placeholder="请输入店面名称" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="店面地址" prop="store_address">
					<el-input v-model="form.store_address" placeholder="请输入店面地址" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="联系电话" prop="store_phone">
					<el-input v-model="form.store_phone" placeholder="请输入联系电话" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="负责人" prop="store_manager">
					<el-input v-model="form.store_manager" placeholder="请输入负责人" :disabled="isView"></el-input>
				</el-form-item>
			</el-col>
		</el-row>

		<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
			<el-form-item v-if="$check_action('/store/view','set') || $check_action('/store/view','add')">
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
		data() {
			return {
				field: "store_id",
				url_add: "~/api/store/add?",
				url_set: "~/api/store/set?",
				url_get_obj: "~/api/store/get_obj?",
				group_table: "store",

				query: {
					"store_id": 0,
				},

				obj: {
					store_id: 0,
					store_name: '',
					store_address: '',
					store_phone: '',
					store_manager: '',
				},

				form: {
					store_id: 0,
					store_name: '',
					store_address: '',
					store_phone: '',
					store_manager: '',
				},
			}
		},
		computed: {
			isView() {
				return this.$route.query.action === 'view';
			},
		},
		methods: {
			is_view(){
				var bl = false;
				if(!bl){
					bl = this.$check_action('/store/table','add');
				}
				if(!bl){
					bl = this.$check_action('/store/table','set');
				}
				if(!bl){
					bl = this.$check_action('/store/view','add');
				}
				if(!bl){
					bl = this.$check_action('/store/view','set');
				}
				if(!bl){
					bl = this.$check_action('/store/view','get');
				}
				return bl;
			},
			async submit_check(param){
				var ret = null;
				var {store_name} = param;

				if(!store_name){
					ret = "店面名称不能为空";
				}

				return ret;
			},
			cancel(){
				this.$router.go(-1);
			},
		},
		created() {},
	}
</script>

<style scoped>
	.bg {
		background: white;
	}
	.edit_wrap {
		padding: 20px;
	}
	.row_ce {
		display: flex;
		flex-wrap: wrap;
	}
	.el_form_item_warp {
		padding: 0 10px;
		box-sizing: border-box;
	}
	.el_form_btn_warp {
		padding: 20px 10px;
		box-sizing: border-box;
	}
</style>
