<template>
	<el-main class="bg edit_wrap goods_type">
		<el-form ref="form" :model="form" status-icon label-width="80px">
			<el-row class="row_e">
				<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
					<el-form-item label="父级分类">
						<el-cascader v-model="form.father_id" :options="type_options" :props="cascader_props"
							:show-all-levels="false" clearable placeholder="请选择父级分类"></el-cascader>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
					<el-form-item label="分类名称">
						<el-input v-model="form.name"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
					<el-form-item label="图标" prop="icon">
						<el-upload class="avatar-uploader" drag accept="image/gif, image/jpeg, image/png, image/jpg"
							action="" :http-request="uploadImg" :show-file-list="false">
							<img v-if="form.icon" :src="$fullUrl(form.icon)" class="avatar">
							<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
				</el-col>

				<!-- <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="描述">
					<el-input type="textarea" :rows="10" v-model="form.desc" placeholder="请输入内容"></el-input>
				</el-form-item>
			</el-col> -->

			</el-row>

			<el-col :xs="24" :sm="24" :lg="24" class="el_form_btn_warp">
				<el-form-item>
					<el-button style="width: 100%; float: left;" type="primary" v-if="user_group == '管理员'"
						@click="submit()">提交</el-button>
					<el-button style="width: 100%; float: right;" @click="cancel()">取消</el-button>
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
			field: "type_id",
			url_add: "~/api/goods_type/add?",
			url_set: "~/api/goods_type/set?",
			url_get_list: "~/api/goods_type/get_list?",
			url_get_obj: "~/api/goods_type/get_obj?",
			url_upload: "~/api/goods_type/upload?",

			query: {
				type_id: 0
			},

			form: {
				type_id: 0,
				name: "",
				desc: "",
				icon: "",
				father_id: 0
			},
			// 分类选项数据
			type_options: [],
			// 级联选择器配置
			cascader_props: {
				value: 'type_id',
				label: 'name',
				children: 'children',
				emitPath: false,
				checkStrictly: true,
				expandTrigger: 'hover'
			}

		}
	},
	methods: {
		/**
		 * 上传图标
		 * @param {Object} param
		 */
		uploadImg(param) {
			this.uploadFile(param.file, "icon");
		},
		/**
   * 获取分类树形数据
   */
		async get_type_tree() {
			try {
				const res = await this.$get(this.url_get_list, {
					page: 1,
					size: 1000
				});

				if (res.result && res.result.list) {
					// 构建树形结构，限制最多2级
					this.type_options = this.buildTree(res.result.list, 0, 0);
				}
			} catch (error) {
				console.error('获取分类列表失败:', error);
			}
		},
		/**
		 * 构建树形结构
		 * @param {Array} list 分类列表
		 * @param {Number} fatherId 父级ID
		 * @param {Number} level 当前层级
		 * @returns {Array} 树形结构
		 */
		buildTree(list, fatherId, level) {
			if (level >= 2) return []; // 限制最多2级

			const tree = [];
			for (let item of list) {
				// 排除自身及其子级，避免循环引用
				if (item.father_id === fatherId && item.type_id !== this.form.type_id) {
					const children = this.buildTree(list, item.type_id, level + 1);
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
		 * 编辑时获取对象数据后的处理
		 */
		async get_obj_after(json) {
			if (json && json.result && json.result.obj && json.result.obj.father_id !== undefined) {
				father_id = json.result.obj.father_id;
			}
			 if (father_id !== undefined && father_id !== null && father_id !== 0) {
				this.form.father_id = father_id;
				// 重新获取分类树
				await this.get_type_tree();
			} else {
				this.form.father_id = 0;
			}
		}
	},
	created() {
		// 获取分类树形数据
		this.get_type_tree();
	}
}
</script>

<style>
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
</style>
