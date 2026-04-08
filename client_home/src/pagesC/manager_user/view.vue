<template>
  <view class="page_diy_view page_manager_user_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>经理用户详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <uni-forms-item label="头像" name="avatar">
          <view class="from-img" v-if="form_user['avatar']">
            <image :src="$fullImgUrl(form_user['avatar'])" @click="change_avatar()" />
          </view>
          <view class="from-img" v-else-if="!form_user['avatar']">
            <view class="add-img" @click="change_avatar()">
              <text>+</text>
            </view>
          </view>
          <uni-icons style="display: none" class="forward" type="forward" id="form_user_img_avatar"></uni-icons>
        </uni-forms-item>

        <uni-forms-item label="账号" name="username">
          <span v-if="obj_user.username">{{ obj_user.username }}</span>
          <uni-easyinput v-else type="text" v-model="form_user.username" placeholder="请输入账号" />
        </uni-forms-item>

        <uni-forms-item v-if="!obj_user.password" label="密码" name="password">
          <uni-easyinput type="password" v-model="form_user.password" placeholder="请输入密码" />
        </uni-forms-item>

        <uni-forms-item label="昵称" name="nickname">
          <uni-easyinput type="text" v-model="form_user.nickname" placeholder="请输入昵称" />
        </uni-forms-item>

        <uni-forms-item label="邮箱" name="email">
          <uni-easyinput type="text" v-model="form_user.email" placeholder="请输入邮箱" />
        </uni-forms-item>

        <uni-forms-item label="状态" name="state">
          <uni-data-select
            v-model="form_user.state"
            :localdata="list_user_state"
            :disabled="user_group !== '管理员'"
			:clear="user_group=='管理员'"
			 style="width: 100%;"
          ></uni-data-select>
        </uni-forms-item>

              <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','employee_work_number') || ($check_field('add','employee_work_number') || $check_field('set','employee_work_number'))" label="员工工号" name="employee_work_number">
                      <uni-easyinput type="text" v-model="form['employee_work_number']" v-if="(form['manager_user_id'] && $check_field('set','employee_work_number')) || (!form['manager_user_id'] && $check_field('add','employee_work_number'))" :disabled="disabledObj['employee_work_number_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','employee_work_number')">
            {{ form['employee_work_number'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','employee_name') || ($check_field('add','employee_name') || $check_field('set','employee_name'))" label="员工姓名" name="employee_name">
                      <uni-easyinput type="text" v-model="form['employee_name']" v-if="(form['manager_user_id'] && $check_field('set','employee_name')) || (!form['manager_user_id'] && $check_field('add','employee_name'))" :disabled="disabledObj['employee_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','employee_name')">
            {{ form['employee_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','employee_gender') || ($check_field('add','employee_gender') || $check_field('set','employee_gender'))" label="员工性别" name="employee_gender">
              <view class="filter-item" @click="showFilter('employee_gender')" v-if="(form['manager_user_id'] && $check_field('set','employee_gender')) || (!form['manager_user_id'] && $check_field('add','employee_gender'))">
            {{ filter_text.employee_gender || '请选择员工性别' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'employee_gender')">
            {{ form['employee_gender'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','contact_number') || ($check_field('add','contact_number') || $check_field('set','contact_number'))" label="联系号码" name="contact_number">
              <uni-easyinput type="number" v-model="form['contact_number']" v-if="(form['manager_user_id'] && $check_field('set','contact_number')) || (!form['manager_user_id'] && $check_field('add','contact_number'))" :disabled="disabledObj['contact_number_isDisabled']" />
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get','contact_number')">
            {{ form['contact_number'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','two_dimensional_code') || ($check_field('add','two_dimensional_code') || $check_field('set','two_dimensional_code'))" label="二维码" name="two_dimensional_code">
              <!-- 修改权限 -->
          <view class="from-img" v-if="form['two_dimensional_code'] && $check_field('set', 'two_dimensional_code')">
            <image v-if="disabledObj['two_dimensional_code_isDisabled']" :src="$fullImgUrl(form['two_dimensional_code'])" />
            <image v-if="!disabledObj['two_dimensional_code_isDisabled']" :src="$fullImgUrl(form['two_dimensional_code'])" @click="change_img('two_dimensional_code')" />
          </view>
          <!-- 添加权限 -->
          <view class="from-img" v-else-if="!form['two_dimensional_code'] && ($check_field('add','two_dimensional_code') || $check_field('set','two_dimensional_code'))">
            <view v-if="disabledObj['two_dimensional_code_isDisabled']" class="add-img">
              <text>+</text>
            </view>
            <view v-if="!disabledObj['two_dimensional_code_isDisabled']" class="add-img" @click="change_img('two_dimensional_code')">
              <text>+</text>
            </view>
          </view>
          <!-- 查询权限 -->
          <view class="from-img" v-else-if="$check_field('get', 'two_dimensional_code')">
            <image :src="$fullImgUrl(form['two_dimensional_code'])" />
          </view>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/manager_user/view','set') || ($check_action('/manager_user/view','add') || $check_option('/manager_user/table','examine'))">
        <view class="me-btn btn-submit" @click="submit_()"> 提交 </view>
        <view class="me-btn btn-cancel" @click="cancel()">取消</view>
      </view>
      <view class="form_button" v-else>
        <view class="me-btn btn-cancel" @click="cancel()">返回</view>
      </view>
    </view>
                    <!--分类选择器-->
        <uni-popup ref="filterPopup" type="bottom" background-color="#fff">
          <view class="filter-popup">
            <view class="popup-header">
              <text class="popup-title">{{ popupTitle }}</text>
              <text class="popup-close" @click="hideFilter">取消</text>
            </view>
            <picker-view class="cascader-picker" :value="cascaderIndex" @change="cascaderPickerChange" :key="cascaderColumns.length" 
              indicator-style="height: 50px;">
              <picker-view-column v-for="(column, colIndex) in cascaderColumns" :key="colIndex">
                <view class="picker-item" :class="{ active: cascaderIndex[colIndex] === 0 }">全部</view>
                <view v-for="(item, index) in column" :key="index" class="picker-item"
                  :class="{ active: cascaderIndex[colIndex] === index + 1 }">
                                                                                                                                                                                                                                                                                          {{ item.employee_gender }}
                                  </view>
              </picker-view-column>
            </picker-view>

            <view class="popup-footer">
              <button class="confirm-btn" @click="confirmFilter">确定</button>
            </view>
          </view>
        </uni-popup>
                      </view>
</template>

<script>
import mixin from '@/libs/mixins/page.js';
                    
					import { uploadFilePathApi, uploadFileApi } from '@/api/common.js';
export default {
  mixins: [mixin],
  components: {
  					  },
  data() {
    return {
	  today: new Date().toISOString().split('T')[0],
      field_user: "user_id",
      url_add_user: "~/api/user/register?",
      url_set_user: "~/api/user/set?",
      url_get_obj_user: "~/api/user/get_obj?",
      url_upload_user: "~/api/user/upload?",
      form_user:{
        user_id: 0,
        username: '',
        nickname: '',
        password: '',
        avatar: '',
        // phone: '',
        email: '',
        user_group: "经理用户",
        // phone_state: 0,
        // email_state: 0,
        state: 1,
          },
      obj_user: {
        user_id: 0,
        username: '',
        nickname: '',
        password: '',
        avatar: '',
        // phone: '',
        email: '',
        user_group: "经理用户",
        // phone_state: 0,
        // email_state: 0,
        state: 1,
        },
      query_user: {
        "user_id": 0,
      },
      list_state: [{value:0,text:"未认证"},{value:1,text:"审核中"},{value:2,text:"已认证"}],
      list_user_state: [{value: 1,text: "可用"},{value: 2,text: "禁用"}],
      group_table: "manager_user",
      is_password: true,
        field: "manager_user_id",
      url_add: "~/api/manager_user/add?",
      url_set: "~/api/manager_user/set?",
      url_get_obj: "~/api/manager_user/get_obj?",
      url_upload: "~/api/manager_user/upload?",
      employee_work_number: null,
	      employee_name: null,
	      employee_gender: null,
	      contact_number: null,
	      two_dimensional_code: null,
						                          query: {
        "manager_user_id": 0,
      },
      oldForm: {},
      form: {
          "employee_work_number":  '', // 员工工号
            "employee_name":  '', // 员工姓名
            "employee_gender":  '', // 员工性别
            "contact_number":  '', // 联系号码
            "two_dimensional_code":  '', // 二维码
          "user_id": 0,
        "manager_user_id": 0, // ID
        "create_by": 0, // 创建人
      },
      disabledObj:{
          "employee_work_number_isDisabled": false,
            "employee_name_isDisabled": false,
            "employee_gender_isDisabled": false,
            "contact_number_isDisabled": false,
            "two_dimensional_code_isDisabled": false,
        },
                                  // 员工性别选项列表
      list_employee_gender: [],
                        					      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/manager_user/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  			    employee_gender: '',
	  	  	        },
      fieldCallbacks: {
  	  	  	  	  	        },
					    };
  },
  computed: {
    aiForm() {
      let form = {
                                                                                  };
      return form;
    },
					    					  },
  methods: {
					        	        	        	        	        	        					submit_after(json, func) {
            let currentPath = '';
            // #ifdef H5
            currentPath = this.$route.fullPath;
            // #endif
                
            // #ifndef H5
            const pages = getCurrentPages();
            const currentPage = pages[pages.length - 1];
            currentPath = currentPage.route || currentPage.$page.fullPath;
            // #endif
            let skip_Path = currentPath.replace('/view', '/table')
            const navigate = () => {
				      // #ifdef H5
				      this.$router.push(skip_Path);
				      // #endif
				      // #ifndef H5
				      uni.navigateTo({
				          url: skip_Path
				      });
				      // #endif
				    }
    						if(!this.form.manager_user_id){
    				                      		                      		                      		                      		                      		                      		                      		                      		        							                  				                                        		                      		                      		        							                                        		                      		                      		        							                                        		                      		                      		                      		                      		        							                                        		                      		                      		                      		        							                  				                                        		                      		                      		                      		                                  							setTimeout(navigate, 800);
						}else{
							navigate();
						}
					},
    /**
     * 提交前验证事件
     * @param {Object} 请求参数
     * @return {String} 验证成功返回null, 失败返回错误提示
     */
    async submit_check(param) {
                                                                          let contact_number_phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if(param.contact_number && !contact_number_phone_regular.test(param.contact_number)){
        return "手机号格式错误"
      }
                                          return null;
    },
    changeLog(v, value) {
      this.form[value] = v;
    },
    handleUploadClick(key,index) {
      this.uploadKey = key;
      if(key == 'checkForm'){
        this.$refs['XeUpload'+index][0].upload('file', {});
      }else {
        this.$refs.XeUpload.upload('file', {});
      }
    },
    handleUploadCallback(e) {
      if (e.type == 'success') {
        try {
          if (this.uploadKey == 'checkForm') {
            this.checkForm.file = [{
              name: e.data[0].response.result.url.split('/upload/')[1],
              url: e.data[0].response.result.url,
            }];
          } else {
            this[this.uploadKey] = e.data[0].response.result.url;
          }
          uni.showToast({ title: '上传成功',icon: 'success',duration: 2000 });

        } catch (error) {
          uni.showToast({ title: '处理上传数据失败',icon: 'none',duration: 3000 });
        }
      } else {
        console.log('上传失败: ', e);
      }
    },
    uploadFile_(type, fileType) {
      if (fileType === 'video') {
        uni.chooseVideo({
          sourceType: ['album', 'camera'],
          success: (res) => {
            this.successChoose(res.tempFilePath, type);
          },
          fail: (err) => {
            console.error('选择视频失败:', err);
            uni.showToast({
              title: '选择视频失败',
              icon: 'none'
            });
          }
        });
        return;
      }
      if (fileType === 'audio') {
        // #ifdef H5
        const input = document.createElement('input');
        input.type = 'file';
        input.style.display = 'none';
        input.accept = 'audio/*';
        input.onchange = (e) => {
          if (e.target.files[0]) {
            const url = URL.createObjectURL(e.target.files[0]);
            this.successChoose(url, type);
            input.remove();
          }
        };
        document.body.appendChild(input);
        input.click();
        return;
        // #endif
        // #ifdef APP-PLUS
        plus.gallery.pick((e) => {
          this.successChoose(e.files[0], type);
        }, null, { filter: 'none', multiple: false });
        return;
        // #endif
        // #ifdef MP-WEIXIN
        wx.chooseMessageFile({
          count: 1,
          success: (res) => {
            this.successChoose(res.tempFiles[0].path, type);
          }
        });
        return;
        // #endif
      }
    },
    successChoose(filePath, type) {
      uni.uploadFile({
        url: this.$fullUrl('~/api/manager_user/upload?'),
        filePath,
        name: 'file',
        success: (uploadFileRes) => {
          if (uploadFileRes.data.error) {
            uni.showToast({ title: uploadFileRes.data.error.message, icon: 'none' });
          } else {
            this[type] = JSON.parse(uploadFileRes.data).result.url;
          }
        },
      });
    },
              close_(type) {
      if (type == 'employee_work_number') this['employee_work_number'] = this.form['employee_work_number'] = "";
      if (type == 'employee_name') this['employee_name'] = this.form['employee_name'] = "";
      if (type == 'employee_gender') this['employee_gender'] = this.form['employee_gender'] = "";
      if (type == 'contact_number') this['contact_number'] = this.form['contact_number'] = "";
      if (type == 'two_dimensional_code') this['two_dimensional_code'] = this.form['two_dimensional_code'] = "";
    },
    submit_() {
        if (this['employee_work_number'] !== null) this.form['employee_work_number'] = this['employee_work_number']
          if (this['employee_name'] !== null) this.form['employee_name'] = this['employee_name']
          if (this['employee_gender'] !== null) this.form['employee_gender'] = this['employee_gender']
          if (this['contact_number'] !== null) this.form['contact_number'] = this['contact_number']
          if (this['two_dimensional_code'] !== null) this.form['two_dimensional_code'] = this['two_dimensional_code']
        if(this.form.extra !== null) this.form.extra = JSON.stringify(this.form.extra)

      console.log(this.form)
      if(!this.form.manager_user_id){
				this.form.create_by = this.user.user_id;
			}
      this.submit();
    },
    /**
     * 上传文件
     * @param {Object} param文件参数
     */
    change_file(key_name) {
      var _self = this;
      // 选择图像方法
      uni.chooseFile({
        count: 1,
        sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album'], //从相册选择
        success: (file) => {
          const tempFilePaths = file.tempFilePaths;
          const filePath = tempFilePaths[0];
          uploadFilePathApi(
            '/manager_user/upload',
            filePath,
            undefined,
            {
              i_want_to_customize: 'test',
            },
            (task) => {
              task.onProgressUpdate((res) => {
                this.percent = res.progress;
                console.log('上传进度' + res.progress);
                console.log('已经上传的数据长度' + res.totalBytesSent);
                console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
              });
            }
          ).then((res) => {
            const filename = res.result.url;
            this.form[key_name] = filename;
          });
        },
        error: function (e) {
          console.log(e);
        },
      });
    },
    /**
     * 上传图片
     * @param {Object} param文件参数
     */
    change_img(key_name) {
      var _self = this;
      _self.upload_img_flag = false;
      // 选择图像方法
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
        // sourceType: ['album'], //从相册选择
        success: (file) => {
          const tempFilePaths = file.tempFilePaths;
          const filePath = tempFilePaths[0];
          uploadFilePathApi('/manager_user/upload',filePath,undefined,undefined,
            (task) => {
              task.onProgressUpdate((res) => {
                this.percent = res.progress;
                console.log('上传进度' + res.progress);
                console.log('已经上传的数据长度' + res.totalBytesSent);
                console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
              });
            }
          ).then((res) => {
            const filename = res.result.url;
            this.form[key_name] = filename;
          });
        },
        error: function (e) {
          console.log(e);
        },
      });
    },
    /**
     * 获取对象后获取缓存表单
     * @param {Object} json
     * @param {Object} func
     */
    get_obj_before(param) {
      var form = uni.db.get('form');
      if (form) {
        delete form.examine_state;
        delete form.examine_reply;
        this.obj = uni.push(this.obj, form);
        this.form = uni.push(this.form, form);
      }
      var arr = [];
      for (let key in form) {
        arr.push(key);
      }
      for (var i = 0; i < arr.length; i++) {
        this.disabledObj[arr[i] + '_isDisabled'] = true;
		if(arr[i] === "source_table"){
			this.form['source_table'] = form[arr[i]]
		}
		if(arr[i] === "source_id"){
			this.form['source_id'] = form[arr[i]]
		}
		if(arr[i] === "source_user_id"){
			this.form['source_user_id'] = form[arr[i]]
		}
      }
          if (this['employee_work_number'] !== null) this.form['employee_work_number'] = this['employee_work_number']
              if (this['employee_name'] !== null) this.form['employee_name'] = this['employee_name']
              if (this['employee_gender'] !== null) this.form['employee_gender'] = this['employee_gender']
              if (this['contact_number'] !== null) this.form['contact_number'] = this['contact_number']
              if (this['two_dimensional_code'] !== null) this.form['two_dimensional_code'] = this['two_dimensional_code']
          uni.db.del('form');

      return param;
    },
        
          
        /**
     * 获取员工性别列表
     */
    async get_list_employee_gender() {
          this.list_employee_gender = ['男','女'].map((o) => ({ 
        employee_gender: o, 
        children: [] 
      }));
      if(!this.form["manager_user_id"]) {
                  this.form_sub["employee_gender"] = this.list_employee_gender[0].employee_gender;
              }
            },
                    
          
          
      // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	                case 'employee_gender':
          this.popupTitle = '选择员工性别';
          this.initCascader(this.list_employee_gender);
          break;
        	        	        	      }
      this.$refs.filterPopup.open();
    },
	initCascader(list) {
		this.cascaderList = list;
		const maxDepth = Math.max(1, this.getMaxDepth(list));
		this.cascaderColumns = Array(maxDepth).fill().map(() => []);
		this.cascaderIndex = Array(maxDepth).fill(0);
		this.cascaderColumns[0] = list;
		// 递归设置后续列数据
		this.updateCascaderColumns(0);
	},
	// 计算最大深度
	getMaxDepth(list, depth = 1) {
		if (!list || list.length === 0) return depth;
		let max = depth;
		for (let item of list) {
			if (item.children && item.children.length > 0) {
				const childDepth = this.getMaxDepth(item.children, depth + 1);
				max = Math.max(max, childDepth);
			}
		}
		return max;
	},
	// 更新级联列数据
	updateCascaderColumns(level) {
		// 更新当前层级的下一个层级
		const nextLevel = level + 1;
		if (nextLevel >= this.cascaderColumns.length) return;
		
		const currentList = this.cascaderColumns[level];
		const currentIndex = this.cascaderIndex[level];

		if (currentIndex > 0 && currentList[currentIndex - 1]?.children) {
			this.cascaderColumns[nextLevel] = currentList[currentIndex - 1].children;
		} else {
			this.cascaderColumns[nextLevel] = [];
		}
		this.$forceUpdate();
			// 递归更新后续层级
		this.updateCascaderColumns(nextLevel);
	},
	// 选择器变化
	cascaderPickerChange(e) {
		const value = e.detail.value;
		// 找出变化的层级
		let changedLevel = 0;
		for (let i = 0; i < value.length; i++) {
			if (value[i] !== this.cascaderIndex[i]) {
				changedLevel = i;
				break;
			}
		}
		// 更新索引
		this.cascaderIndex = value;
		this.updateCascaderColumns(changedLevel);

		// 重置后续级别的索引为0
		for (let i = changedLevel + 1; i < this.cascaderIndex.length; i++) {
			this.cascaderIndex[i] = 0;
		}
	},
	// 确认选择
	confirmFilter() {
		let selectedText = '';
		let queryValue = '';

		const selected = [];
    let fieldValue = '';
		for (let i = 0; i < this.cascaderColumns.length; i++) {
			if (this.cascaderIndex[i] > 0) {
				const item = this.cascaderColumns[i][this.cascaderIndex[i] - 1];
				selected.push(item);
			}
		}

		if (selected.length > 0) {
			const lastItem = selected[selected.length - 1];
																																													              selectedText = selected.map(item => item.employee_gender).join(' / ');
                fieldValue = lastItem.employee_gender;
          }else{
			selectedText = '';
      fieldValue = '';
		}


		// 更新显示文本和查询条件
		this.filter_text[this.currentFilterType] = selectedText;
    this.form[this.currentFilterType] = fieldValue;

    this.callFieldCallback(this.currentFilterType, fieldValue);
  		this.hideFilter();
	},
	hideFilter() {
		this.$refs.filterPopup.close();
	},
  callFieldCallback(fieldType, value) {
    const callbackName = this.fieldCallbacks[fieldType];
    if (callbackName && typeof this[callbackName] === 'function') {
      this[callbackName](value);
    }
  },
      /**
     * 获取对象之后
     * @param {Object} json
     * @param {Object} func
     */
    async get_obj_after(json, func) {
      let user_id = this.form.user_id;
      this.$get('~/api/user/get_obj', { user_id }, (res) => {
        if (res.result && res.result.obj) {
          let o = res.result.obj;
          delete o['create_time'];
          delete o['login_time'];
          this.form_user = o;
          this.obj_user = o;
          this.is_password = this.obj_user.password ? false : true;
          this.query_user.user_id = user_id;
        } else if (res.error) {
          console.log(res.error);
          console.log('获取不到相关信息');
        }
      });
      if (json.result.obj.extra) {
        this.form.extra = JSON.parse(json.result.obj.extra);
      }
                                                                          if (json.result.obj.employee_gender) {
        this.filter_text.employee_gender = json.result.obj.employee_gender;
      }
                                                        
      if (json.result.obj.create_by) {
        this.form.create_by = json.result.obj.create_by;
            }
      this.oldForm = JSON.parse(JSON.stringify(this.form));
    },
    /**
     * 修改头像
     * @param {Object} param文件参数
     */
    change_avatar() {
      var _self = this;
      _self.upload_img_flag = false;
      // 选择图像方法
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album'], //从相册选择

        success: (file) => {
          const tempFilePaths = file.tempFilePaths;
          const filePath = tempFilePaths[0];
          uploadFilePathApi(
            undefined,
            filePath,
            undefined,
            undefined,
            (task) => {
              task.onProgressUpdate((res) => {
                this.percent = res.progress;
                console.log('上传进度' + res.progress);
                console.log('已经上传的数据长度' + res.totalBytesSent);
                console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
              });
            }
          ).then((res) => {
            const filename = res.result.url;
            this.form_user.avatar = filename;
          });
        },
        error: function (e) {
          console.log(e);
        },
      });
    },
    async submit(param, func) {
      if (!param) {
        param = this.form_user;
      }
      var pm = this.events('submit_before', Object.assign({}, param)) || param;
      var msg = await this.events('submit_check', pm);
      var ret;
      if (msg) {
        this.$toast(msg, 'danger');
      } else {
        ret = this.events('submit_main', pm, func);
      }
      return ret;
    },

    // 提交前校验
    async submit_check(param) {
      var ret = null;

      var email_regular = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      var { username, password, nickname, user_group, email } = param;

      var confirm_password = this.confirm_password;

      console.log(
        '表单校验username ,password ,email ,nickname ,user_group',
        username,
        password,
        email,
        nickname,
        user_group
      );

      if (!username) {
        ret = '账号不能为空';
      } else if (username.length > 16 || username.length < 5) {
        ret = '账号长度应为5到16个字符之间！';
      } else if (!password) {
        ret = '密码不能为空!';
      } else if (this.is_password && (password.length > 16 || password.length < 5)) {
        ret = '密码长度应为5到16个字符之间！';
      } else if ((nickname && nickname.length > 12) || nickname.length < 2) {
        ret = '昵称长度应为2个字符到12个字符之间';
      } else if (email && !email_regular.test(email)) {
        ret = '请输入正确的邮箱地址 例：test@test.com!';
      }
      else if (!user_group) {
        ret = '请选择用户组!';
      }

      var p = { username: param.username };

      let form_sub = Object.assign({}, this.form);
          if(!ret && !form_sub["employee_work_number"]){
        ret = "员工工号不能为空!";
      } else if (!ret && !form_sub["manager_user_id"]) {
        let res = await this.$get("~/api/manager_user/count", {"employee_work_number": form_sub["employee_work_number"]});
        if(res.result){
          ret = "员工工号已存在!";
        }
      }
                                                                          let contact_number_phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if(form_sub.contact_number && !contact_number_phone_regular.test(form_sub.contact_number)){
        return "手机号格式错误"
      }
                                          return ret;
    },

    submit_main(param, func) {
      var url = this.url;
      if (url) {
        if (this.field_user) {
          var id = param[this.field_user];
          if (id) {
            var q = {
              method: 'set',
            };
            q[this.field_user] = id;
            url = this.toUrl(q, url);
          } else {
            url += 'method=add';
          }
        } else {
          url += 'method=submit';
        }
      } else if (this.url_submit) {
        url = this.url_submit;
      } else if (this.field_user) {
        var id = param[this.field_user];
        if (id) {
          url = this.url_set_user;
        } else {
          url = this.url_add_user;
        }
      }

      // console.log('提交', url);
      if (url) {
        var _this = this;
        this.$post(this.$toUrl(this.query_user, url), param, function (json, status) {
          if (json.result === 0 || json.result) {
            _this.events('submit_after', json, func);
          } else if (json.error) {
            _this.$toast(json.error.message, 'danger');
          } else {
            _this.$toast('服务器连接失败！', 'danger');
          }
        });
      }
    },

    // 提交成功后
    submit_after(json, func) {
      var form = Object.assign({}, this.form_user);
      console.log('查询表单form', form);
      this.get_register(form);
    },

    /**
     * 获取注册表信息
     * @param {Object} form
     * @param {Object} table
     */
    get_register(form) {
      var form = this.form;
      var form_user = this.form_user;
      delete form_user.password;
      if (!form['manager_user_id']) {
        delete form_user.user_id;
      }
      this.$get('~/api/user/get_obj', form_user, (res) => {
        console.log('注册表信息res', res);
        if (res.result && res.result.obj) {
          form.user_id = res.result.obj.user_id;
          this.submit_sub(form);
        } else if (res.error) {
          console.error(res.error);
          this.$toast(res.error.message, 'error');
        }
      });
    },

    submit_sub(form_sub) {
      if (form_sub['manager_user_id']) {
        // 提交事件
        this.$post('~/api/' + this.group_table + '/set?manager_user_id=' + form_sub['manager_user_id'], form_sub, (res) => {
            console.log('提交结果：', res);
            if (res.result) {
              this.$toast('修改成功!', 'success');
              uni.navigateBack({
                delta: 1,
              });
            } else if (res.error) {
              console.error(res.error);
              this.$toast(res.error.message, 'error');
            }
          }
        );
      } else {
        // 提交事件
        this.$post('~/api/' + this.group_table + '/add?', form_sub, (res) => {
          console.log('提交结果：', res);
          if (res.result) {
            this.$toast('添加成功!', 'success');
            uni.navigateBack({
              delta: 1,
            });
          } else if (res.error) {
            var user_id = form_sub['user_id'];
            this.$get('~/api/user/del', { user_id });
            console.error(res.error);
            this.$toast(res.error.message, 'error');
          }
        });
      }
    },
    
    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/manager_user/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/manager_user/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/manager_user/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/manager_user/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/manager_user/view', 'get');
        console.log(bl ? '你有视图查询权限视作有查询权限' : '你没有视图查询权限');
      }

      console.log(
        bl
          ? '具有当前页面的查看权，请注意这不代表你有字段的查看权'
          : '无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行'
      );

      return bl;
    },
                      },
  created() {
              this.get_list_employee_gender();
              },
};
</script>

<style lang="scss" scoped>
  @import 'styles/pagesC/index.scss';
  .detailed_address {
    width: 100%;
    background-color: #f7f6f6;
    border: 1px solid #eaeaea;
    border-radius: 5px;
    padding: 8px;
    min-height: 35px;
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

  .check-item-list .check-item-li .check-item .check-item-title>p:nth-child(1) {
    font-weight: bold;
    margin-bottom: 10px;
    text-align: left;
    font-size: 16px;
  }

  .check-item-list .check-item-li .check-item .check-item-title>p:nth-child(2) {
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
    content: '';
    position: absolute;
    right: 0;
    top: 0;
    border: 15px solid #409eff;
    border-bottom-color: transparent;
    border-left-color: transparent;
  }

  .check-item-list .check-item-li .check-item-complete:after {
    content: '';
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

  .ql-container {
    border: 1px solid #d9d9d9;
    border-radius: 4px;
  }

  .face-content {
    height: 460rpx !important;
    width: 500rpx;
    background-color: transparent !important;
  }

  .face-content .face-img {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .face-content .face-img .img-wrap {
    width: 100%;
    height: 100%;
  }

  .face-content .face-img .add-img {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 32px;
  }

  .face-content button {
    font-size: 14rpx;
    margin-top: 5rpx;
  }

  .account_login .face-content {
    background-color: #ffffff;
  }

  .face-content .face-camera {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  // 编辑器样式
  .editor-item ::v-deep .uni-forms-item__content {
    border: 1px solid #e4e4e4;
  }
.filter-item {
  border: 1px solid #e5e5e5;
  color: #6a6a6a;
  text-align: center;
  margin-bottom: 10px;
  width: 100%;
  height: 36px;
  line-height: 36px;
  border-radius: 4px;
}

.filter-popup {
	background: #fff;
	border-radius: 16px 16px 0 0;
	height: 60vh;
	display: flex;
	flex-direction: column;
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 16px 20px;
	border-bottom: 1px solid #f0f0f0;
}

.popup-title {
	font-size: 18px;
	font-weight: 600;
	color: #333;
}

.popup-close {
	font-size: 16px;
	color: #666;
}

.single-picker,
.cascader-picker {
	flex: 1;
	height: 300px;
}

.picker-item {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 50px;
	font-size: 16px;
	color: #666;
}

.picker-item.active {
	color: #0079fe;
	font-weight: 600;
}

.popup-footer {
	padding: 16px 20px;
	border-top: 1px solid #f0f0f0;
}

.confirm-btn {
	width: 100%;
	background: #0079fe;
	color: #fff;
	border: none;
	border-radius: 8px;
	padding: 12px;
	font-size: 16px;
}
.related-notes-list {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  box-sizing: border-box;
}

.related-notes-item {
  position: relative;
  width: 160rpx;
  height: 160rpx;
  margin-right: 30rpx;
  margin-bottom: 30rpx;
  flex-shrink: 0;
}

.related-notes-image {
  width: 100% !important;
  height: 100% !important;
  border-radius: 10rpx;
  display: block;
}

.related-notes-remove {
  position: absolute;
  top: -20rpx;
  right: -20rpx;
  width: 44rpx;
  height: 44rpx;
  line-height: 44rpx;
  border-radius: 50%;
  background-color: #F56C6C;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32rpx;
  cursor: pointer;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.2);
}

.related-notes-add {
  width: 160rpx;
  height: 160rpx;
  border: 2rpx dashed #ccc;
  border-radius: 10rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #999;
  cursor: pointer;
  background-color: #fafafa;
  margin-bottom: 30rpx;
  flex-shrink: 0;
  box-sizing: border-box;
}

.related-notes-add text {
  font-size: 28rpx;
  margin-top: 10rpx;
}

.related-notes-add::before {
  content: "+";
  font-size: 60rpx;
  line-height: 50rpx;
  color: #ccc;
}
</style>
