<template>
  <view class="page_diy_view page_vehicle_information_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>车辆信息详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','owner_user') || ($check_field('add','owner_user') || $check_field('set','owner_user'))" label="车主用户" name="owner_user">
              <uni-data-select
              id="form_owner_user"
              v-model="form['owner_user']"
              :localdata="list_user_owner_user"
              :clear="!disabledObj['owner_user_isDisabled']"
              :disabled="disabledObj['owner_user_isDisabled']"
              v-if="(form['vehicle_information_id'] && $check_field('set', 'owner_user')) || (!form['vehicle_information_id'] && $check_field('add', 'owner_user'))"
			   style="width: 100%;"
          ></uni-data-select>
          <uni-data-select
              v-model="form['owner_user']"
              :localdata="list_user_owner_user"
              :clear="false"
              :disabled="true"
              v-else-if="$check_field('get', 'owner_user')"
              id="owner_user"
			  style="width: 100%;"
          ></uni-data-select>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','license_plate_number') || ($check_field('add','license_plate_number') || $check_field('set','license_plate_number'))" label="车牌号码" name="license_plate_number">
                      <uni-easyinput type="text" v-model="form['license_plate_number']" v-if="(form['vehicle_information_id'] && $check_field('set','license_plate_number')) || (!form['vehicle_information_id'] && $check_field('add','license_plate_number'))" :disabled="disabledObj['license_plate_number_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','license_plate_number')">
            {{ form['license_plate_number'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','car_type') || ($check_field('add','car_type') || $check_field('set','car_type'))" label="汽车类型" name="car_type">
              <view class="filter-item" @click="showFilter('car_type')" v-if="(form['vehicle_information_id'] && $check_field('set','car_type')) || (!form['vehicle_information_id'] && $check_field('add','car_type'))">
            {{ filter_text.car_type || '请选择汽车类型' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'car_type')">
            {{ form['car_type'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','frame_number') || ($check_field('add','frame_number') || $check_field('set','frame_number'))" label="车架号" name="frame_number">
                      <uni-easyinput type="text" v-model="form['frame_number']" v-if="(form['vehicle_information_id'] && $check_field('set','frame_number')) || (!form['vehicle_information_id'] && $check_field('add','frame_number'))" :disabled="disabledObj['frame_number_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','frame_number')">
            {{ form['frame_number'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','vehicle_remarks') || ($check_field('add','vehicle_remarks') || $check_field('set','vehicle_remarks'))" label="车辆备注" name="vehicle_remarks">
              <uni-easyinput type="textarea" v-model="form['vehicle_remarks']" v-if="(form['vehicle_information_id'] && $check_field('set', 'vehicle_remarks')) || (!form['vehicle_information_id'] && $check_field('add', 'vehicle_remarks'))" :disabled="disabledObj['vehicle_remarks_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'vehicle_remarks')">
            {{ form['vehicle_remarks'] }}
          </text>
                </uni-forms-item>
          <uni-forms-item label="审核状态" name="examine_state">
          <uni-data-select
              v-model="form['examine_state']"
              :localdata="list_examine_state"
              v-if="(form['examine_state'] && $check_examine()) || (!form['examine_state'] && $check_examine())"
			  style="width: 100%;"
          ></uni-data-select>
          <text v-else>{{form["examine_state"]}}</text>
        </uni-forms-item>
        <uni-forms-item label="审核回复" name="examine_reply">
          <uni-easyinput type="text" placeholder="请输入审核回复" v-model="form['examine_reply']"
                         v-if="(form['examine_reply'] && $check_examine()) || (!form['examine_reply'] && $check_examine())" />
          <!-- 仅查看 -->
          <text v-else>{{form["examine_reply"]}}</text>
        </uni-forms-item>
      </uni-forms>
      <view class="form-footer" v-if="$check_action('/vehicle_information/view','set') || ($check_action('/vehicle_information/view','add') || $check_option('/vehicle_information/table','examine'))">
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
                                                                                                                                                                                                                                                                                          {{ item.model_name }}
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
      field: "vehicle_information_id",
      url_add: "~/api/vehicle_information/add?",
      url_set: "~/api/vehicle_information/set?",
      url_get_obj: "~/api/vehicle_information/get_obj?",
      url_upload: "~/api/vehicle_information/upload?",
      owner_user: null,
	      license_plate_number: null,
	      car_type: null,
	      frame_number: null,
	      vehicle_remarks: null,
						                          query: {
        "vehicle_information_id": 0,
      },
      form: {
          "owner_user": 0, // 车主用户
            "license_plate_number":  '', // 车牌号码
            "car_type":  '', // 汽车类型
            "frame_number":  '', // 车架号
            "vehicle_remarks":  '', // 车辆备注
          "examine_state": "未审核",
        "examine_reply": "",
        "vehicle_information_id": 0, // ID
      },
      disabledObj:{
          "owner_user_isDisabled": false,
            "license_plate_number_isDisabled": false,
            "car_type_isDisabled": false,
            "frame_number_isDisabled": false,
            "vehicle_remarks_isDisabled": false,
        },
              // 用户列表
      list_user_owner_user: [],
                  // 用户组
      group_user_owner_user: "",
                                      // 汽车类型选项列表
      list_car_type: [],
                              list_examine_state:[{value:"未审核",text:"未审核"},{value:"已通过",text:"已通过"},{value:"未通过",text:"未通过"}],
					      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/vehicle_information/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  			    car_type: '',
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
						if(!this.form.vehicle_information_id){
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
        url: this.$fullUrl('~/api/vehicle_information/upload?'),
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
      if (type == 'owner_user') this['owner_user'] = this.form['owner_user'] = "";
      if (type == 'license_plate_number') this['license_plate_number'] = this.form['license_plate_number'] = "";
      if (type == 'car_type') this['car_type'] = this.form['car_type'] = "";
      if (type == 'frame_number') this['frame_number'] = this.form['frame_number'] = "";
      if (type == 'vehicle_remarks') this['vehicle_remarks'] = this.form['vehicle_remarks'] = "";
    },
    submit_() {
        if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
          if (this['license_plate_number'] !== null) this.form['license_plate_number'] = this['license_plate_number']
          if (this['car_type'] !== null) this.form['car_type'] = this['car_type']
          if (this['frame_number'] !== null) this.form['frame_number'] = this['frame_number']
          if (this['vehicle_remarks'] !== null) this.form['vehicle_remarks'] = this['vehicle_remarks']
        console.log(this.form)
      if(!this.form.vehicle_information_id){
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
            '/vehicle_information/upload',
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
          uploadFilePathApi('/vehicle_information/upload',filePath,undefined,undefined,
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
          if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
              if (this['license_plate_number'] !== null) this.form['license_plate_number'] = this['license_plate_number']
              if (this['car_type'] !== null) this.form['car_type'] = this['car_type']
              if (this['frame_number'] !== null) this.form['frame_number'] = this['frame_number']
              if (this['vehicle_remarks'] !== null) this.form['vehicle_remarks'] = this['vehicle_remarks']
          uni.db.del('form');

      return param;
    },
        /**
     * 获取车主用户用户列表
     */
    async get_list_user_owner_user() {
      var json = await this.$get("~/api/user/get_list?user_group=车主用户");
      if(json.result && json.result.list){
        json.result.list.map((o) => this.list_user_owner_user.push({value:o.user_id,text:o.nickname + '-' + o.username}));
      }
      else if(json.error){
        console.error(json.error);
      }
    },
        /**
     * 获取车主用户用户组
     */
    async get_group_user_owner_user() {
      this.form["owner_user"] = this.user.user_id;
      var json = await this.$get("~/api/user_group/get_obj?name=车主用户");
      if(json.result && json.result.obj){
        this.group_user_owner_user = json.result.obj;
        this.get_user_session_owner_user(this.form['owner_user'])
      }
      else if(json.error){
        console.error(json.error);
      }
    },
    get_user_session_owner_user(id){
      var _this = this;
      var user_id = {"user_id":_this.user.user_id}
      var url = "~/api/"+_this.group_user_owner_user.source_table+"/get_obj"
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
          if(_this.user.user_group == '车主用户'){
            _this.form["owner_user"] = _this.user.user_id
          }
          _this.disabledObj['owner_user' + '_isDisabled'] = true
          for (var i=0;i<arr.length;i++){
            if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
              for (var j = 0; j < arrForm.length; j++) {
                if (arr[i] === arrForm[j]) {
                  if (arr[i] !== "owner_user" && arr[i] !== "create_by") {
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
          
          
        /**
     * 获取汽车类型列表
     */
    async get_list_car_type() {
              let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                            var json = await this.$get("~/api/vehicle_class_nameification/get_list",param);
      if(json.result && json.result.list){
        if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
          json.result.list = json.result.list.filter(item => item.type == 1);
        }
              const hasFatherId = json.result.list.some(item => 'father_id' in item);
        if (hasFatherId) {
          this.list_car_type = this.buildTree(json.result.list, 0, 'vehicle_class_nameification_id');
        }else{
          this.list_car_type = json.result.list.map(item => ({
            model_name: item.model_name,
            children: []
          }));
        }
        if(!this.form["vehicle_information_id"]) {
                      this.form["car_type"] = this.list_car_type[0].model_name;
                    this.filter_text.car_type = this.list_car_type[0].model_name;
          this.callFieldCallback('car_type', this.list_car_type[0].model_name);
        }
            }
      else if(json.error){
        console.error(json.error);
      }
        },
          buildTree(list, fatherId, type) {
        const tree = [];
        for (let item of list) {
          if (item.father_id === fatherId) {
            const children = this.buildTree(list, item[type], type);
            item.children = children.length > 0 ? children : [];
            tree.push(item);
          }
        }
        return tree;
      },
                    
          
          
      // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	                case 'car_type':
          this.popupTitle = '选择汽车类型';
          this.initCascader(this.list_car_type);
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
																																													              selectedText = selected.map(item => item.model_name).join(' / ');
                fieldValue = lastItem.model_name;
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
      if (json.result.obj.extra) {
        this.form.extra = JSON.parse(json.result.obj.extra);
      }
                                                                          if (json.result.obj.car_type) {
        this.filter_text.car_type = json.result.obj.car_type;
      }
                                                            },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/vehicle_information/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/vehicle_information/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/vehicle_information/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/vehicle_information/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/vehicle_information/view', 'get');
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
        this.get_list_user_owner_user();
              this.get_group_user_owner_user();
                  this.get_list_car_type();
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
