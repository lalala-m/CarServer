<template>
  <view class="page_diy_view page_boutique_center_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>精品中心详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','service_code') || ($check_field('add','service_code') || $check_field('set','service_code'))" label="服务编码" name="service_code">
                      <uni-easyinput type="text" v-model="form['service_code']" v-if="(form['boutique_center_id'] && $check_field('set','service_code')) || (!form['boutique_center_id'] && $check_field('add','service_code'))" :disabled="disabledObj['service_code_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_code')">
            {{ form['service_code'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_name') || ($check_field('add','service_name') || $check_field('set','service_name'))" label="服务名称" name="service_name">
                      <uni-easyinput type="text" v-model="form['service_name']" v-if="(form['boutique_center_id'] && $check_field('set','service_name')) || (!form['boutique_center_id'] && $check_field('add','service_name'))" :disabled="disabledObj['service_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_name')">
            {{ form['service_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_type') || ($check_field('add','service_type') || $check_field('set','service_type'))" label="服务类型" name="service_type">
              <view class="filter-item" @click="showFilter('service_type')" v-if="(form['boutique_center_id'] && $check_field('set','service_type')) || (!form['boutique_center_id'] && $check_field('add','service_type'))">
            {{ filter_text.service_type || '请选择服务类型' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'service_type')">
            {{ form['service_type'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_price') || ($check_field('add','service_price') || $check_field('set','service_price'))" label="服务价格" name="service_price">
                      <uni-easyinput type="text" v-model="form['service_price']" v-if="(form['boutique_center_id'] && $check_field('set','service_price')) || (!form['boutique_center_id'] && $check_field('add','service_price'))" :disabled="disabledObj['service_price_isDisabled']">
                    	<template #right><text style="margin-right: 24rpx;">元</text></template>
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_price')">
            {{ form['service_price'] }}元          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_cover') || ($check_field('add','service_cover') || $check_field('set','service_cover'))" label="服务封面" name="service_cover">
              <!-- 修改权限 -->
          <view class="from-img" v-if="form['service_cover'] && $check_field('set', 'service_cover')">
            <image v-if="disabledObj['service_cover_isDisabled']" :src="$fullImgUrl(form['service_cover'])" />
            <image v-if="!disabledObj['service_cover_isDisabled']" :src="$fullImgUrl(form['service_cover'])" @click="change_img('service_cover')" />
          </view>
          <!-- 添加权限 -->
          <view class="from-img" v-else-if="!form['service_cover'] && ($check_field('add','service_cover') || $check_field('set','service_cover'))">
            <view v-if="disabledObj['service_cover_isDisabled']" class="add-img">
              <text>+</text>
            </view>
            <view v-if="!disabledObj['service_cover_isDisabled']" class="add-img" @click="change_img('service_cover')">
              <text>+</text>
            </view>
          </view>
          <!-- 查询权限 -->
          <view class="from-img" v-else-if="$check_field('get', 'service_cover')">
            <image :src="$fullImgUrl(form['service_cover'])" />
          </view>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_introduction') || ($check_field('add','service_introduction') || $check_field('set','service_introduction'))" label="服务简介" name="service_introduction">
              <uni-easyinput type="textarea" v-model="form['service_introduction']" v-if="(form['boutique_center_id'] && $check_field('set', 'service_introduction')) || (!form['boutique_center_id'] && $check_field('add', 'service_introduction'))" :disabled="disabledObj['service_introduction_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'service_introduction')">
            {{ form['service_introduction'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="editor-item" v-if="$check_field('get','service_specificss') || ($check_field('add','service_specificss') || $check_field('set','service_specificss'))" label="服务详情" name="service_specificss">
              <sp-editor
            v-if="(form['boutique_center_id'] && $check_field('set', 'service_specificss')) || (!form['boutique_center_id'] && $check_field('add', 'service_specificss'))"
            :toolbar-config="{
              excludeKeys: ['direction', 'date', 'lineHeight', 'letterSpacing', 'listCheck','export','video'],
              iconSize: '18px'
            }"
            @init="initEditor_service_specificss"
            @input="inputOver_service_specificss"
            @upinImage="upinImage_service_specificss"
          ></sp-editor>
          <text v-else-if="$check_field('get', 'service_specificss')" v-html="form['service_specificss']">
          </text>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/boutique_center/view','set') || ($check_action('/boutique_center/view','add') || $check_option('/boutique_center/table','examine'))">
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
                                                                                                                                                                                                                                                                                                                                                                      {{ item.service_type }}
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
      field: "boutique_center_id",
      url_add: "~/api/boutique_center/add?",
      url_set: "~/api/boutique_center/set?",
      url_get_obj: "~/api/boutique_center/get_obj?",
      url_upload: "~/api/boutique_center/upload?",
      service_code: null,
	      service_name: null,
	      service_type: null,
	      service_price: null,
	      service_cover: null,
	      service_introduction: null,
	      service_specificss: null,
								                                editorIns_service_specificss: null,
        query: {
        "boutique_center_id": 0,
      },
      form: {
          "service_code":  '', // 服务编码
            "service_name":  '', // 服务名称
            "service_type":  '', // 服务类型
            "service_price":  0 , // 服务价格
            "service_cover":  '', // 服务封面
            "service_introduction":  '', // 服务简介
            "service_specificss":  '', // 服务详情
          "boutique_center_id": 0, // ID
      },
      disabledObj:{
          "service_code_isDisabled": false,
            "service_name_isDisabled": false,
            "service_type_isDisabled": false,
                "service_cover_isDisabled": false,
            "service_introduction_isDisabled": false,
            "service_specificss_isDisabled": false,
        },
                                  // 服务类型选项列表
      list_service_type: [],
                                        							      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/boutique_center/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  			    service_type: '',
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
						if(!this.form.boutique_center_id){
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
        url: this.$fullUrl('~/api/boutique_center/upload?'),
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
      if (type == 'service_code') this['service_code'] = this.form['service_code'] = "";
      if (type == 'service_name') this['service_name'] = this.form['service_name'] = "";
      if (type == 'service_type') this['service_type'] = this.form['service_type'] = "";
      if (type == 'service_price') this['service_price'] = this.form['service_price'] = "";
      if (type == 'service_cover') this['service_cover'] = this.form['service_cover'] = "";
      if (type == 'service_introduction') this['service_introduction'] = this.form['service_introduction'] = "";
      if (type == 'service_specificss') this['service_specificss'] = this.form['service_specificss'] = "";
    },
    submit_() {
        if (this['service_code'] !== null) this.form['service_code'] = this['service_code']
          if (this['service_name'] !== null) this.form['service_name'] = this['service_name']
          if (this['service_type'] !== null) this.form['service_type'] = this['service_type']
          if (this['service_price'] !== null) this.form['service_price'] = this['service_price']
          if (this['service_cover'] !== null) this.form['service_cover'] = this['service_cover']
          if (this['service_introduction'] !== null) this.form['service_introduction'] = this['service_introduction']
          if (this['service_specificss'] !== null) this.form['service_specificss'] = this['service_specificss']
        console.log(this.form)
      if(!this.form.boutique_center_id){
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
            '/boutique_center/upload',
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
          uploadFilePathApi('/boutique_center/upload',filePath,undefined,undefined,
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
          if (this['service_code'] !== null) this.form['service_code'] = this['service_code']
              if (this['service_name'] !== null) this.form['service_name'] = this['service_name']
              if (this['service_type'] !== null) this.form['service_type'] = this['service_type']
              if (this['service_price'] !== null) this.form['service_price'] = this['service_price']
              if (this['service_cover'] !== null) this.form['service_cover'] = this['service_cover']
              if (this['service_introduction'] !== null) this.form['service_introduction'] = this['service_introduction']
              if (this['service_specificss'] !== null) this.form['service_specificss'] = this['service_specificss']
          uni.db.del('form');

      return param;
    },
        
          
        /**
     * 获取服务类型列表
     */
    async get_list_service_type() {
              let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                            var json = await this.$get("~/api/service_class_nameification/get_list",param);
      if(json.result && json.result.list){
        if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
          json.result.list = json.result.list.filter(item => item.type == 1);
        }
              const hasFatherId = json.result.list.some(item => 'father_id' in item);
        if (hasFatherId) {
          this.list_service_type = this.buildTree(json.result.list, 0, 'service_class_nameification_id');
        }else{
          this.list_service_type = json.result.list.map(item => ({
            service_type: item.service_type,
            children: []
          }));
        }
        if(!this.form["boutique_center_id"]) {
                      this.form["service_type"] = this.list_service_type[0].service_type;
                    this.filter_text.service_type = this.list_service_type[0].service_type;
          this.callFieldCallback('service_type', this.list_service_type[0].service_type);
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
	        	        	                case 'service_type':
          this.popupTitle = '选择服务类型';
          this.initCascader(this.list_service_type);
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
																																																											              selectedText = selected.map(item => item.service_type).join(' / ');
                fieldValue = lastItem.service_type;
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
                                                                          if (json.result.obj.service_type) {
        this.filter_text.service_type = json.result.obj.service_type;
      }
                                                                                                            },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/boutique_center/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/boutique_center/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/boutique_center/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/boutique_center/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/boutique_center/view', 'get');
        console.log(bl ? '你有视图查询权限视作有查询权限' : '你没有视图查询权限');
      }

      console.log(
        bl
          ? '具有当前页面的查看权，请注意这不代表你有字段的查看权'
          : '无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行'
      );

      return bl;
    },
                            inputOver_service_specificss(e) {
    this.form['service_specificss'] = e.html
  },
  initEditor_service_specificss(editor) {
    this.editorIns_service_specificss = editor // 保存编辑器实例
    // 保存编辑器实例后，可以在此处获取后端数据，并赋值给编辑器初始化内容
    this.preRender_service_specificss()
  },
  preRender_service_specificss() {
    setTimeout(() => {
      // 异步获取后端数据后，初始化编辑器内容
      this.editorIns_service_specificss.setContents({
        html: this.form['service_specificss']
      })
    }, 1000)
  },
  async upinImage_service_specificss(tempFiles, editorCtx) {
    uni.showLoading({
      title: '上传中请稍后',
      mask: true
    })

    // 获取文件路径
    const filePath = tempFiles[0].path || tempFiles[0].tempFilePath;
    uni.uploadFile({
      url: this.$fullUrl('~/api/boutique_center/upload'),
      filePath: filePath,
      name: 'file', // 后端接收的字段名
      success: (uploadRes) => {
        uni.hideLoading();
        try {
          const res = JSON.parse(uploadRes.data);
          
          // 上传成功后插入图片到编辑器
          if (res && res.result && res.result.url) {
            editorCtx.insertImage({
              src: this.$fullImgUrl(res.result.url),
              width: '80%',
              success: function () {
                console.log('图片插入成功');
              }
            });
          }
        } catch (error) {
          console.error('解析上传结果失败:', error);
          uni.showToast({
            title: '上传失败',
            icon: 'error'
          });
        }
      },
      fail: (error) => {
        console.error('上传失败:', error);
        uni.hideLoading();
        uni.showToast({
          title: '上传失败',
          icon: 'error'
        });
      }
    });
  },
      },
  created() {
              this.get_list_service_type();
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
