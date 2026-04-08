<template>
  <view class="page_diy_view page_employee_performance_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>员工绩效详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','performance_title') || ($check_field('add','performance_title') || $check_field('set','performance_title'))" label="绩效标题" name="performance_title">
                      <uni-easyinput type="text" v-model="form['performance_title']" v-if="(form['employee_performance_id'] && $check_field('set','performance_title')) || (!form['employee_performance_id'] && $check_field('add','performance_title'))" :disabled="disabledObj['performance_title_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','performance_title')">
            {{ form['performance_title'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','employee_name') || ($check_field('add','employee_name') || $check_field('set','employee_name'))" label="员工姓名" name="employee_name">
                      <uni-easyinput type="text" v-model="form['employee_name']" v-if="(form['employee_performance_id'] && $check_field('set','employee_name')) || (!form['employee_performance_id'] && $check_field('add','employee_name'))" :disabled="disabledObj['employee_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','employee_name')">
            {{ form['employee_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','evaluation_time') || ($check_field('add','evaluation_time') || $check_field('set','evaluation_time'))" label="评估时间" name="evaluation_time">
              <uni-datetime-picker v-if="(form['employee_performance_id'] && $check_field('set', 'evaluation_time')) || (!form['employee_performance_id'] && $check_field('add', 'evaluation_time'))" @change="changeLog($event, 'evaluation_time')" v-model="form['evaluation_time']" type="datetime" :disabled="disabledObj['evaluation_time_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'evaluation_time')">
            {{ form['evaluation_time'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_singular') || ($check_field('add','service_singular') || $check_field('set','service_singular'))" label="服务单数" name="service_singular">
                      <uni-easyinput type="text" v-model="form['service_singular']" v-if="(form['employee_performance_id'] && $check_field('set','service_singular')) || (!form['employee_performance_id'] && $check_field('add','service_singular'))" :disabled="disabledObj['service_singular_isDisabled']">
                    	<template #right><text style="margin-right: 24rpx;">单</text></template>
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_singular')">
            {{ form['service_singular'] }}单          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','quality_of_service') || ($check_field('add','quality_of_service') || $check_field('set','quality_of_service'))" label="服务质量" name="quality_of_service">
              <view class="filter-item" @click="showFilter('quality_of_service')" v-if="(form['employee_performance_id'] && $check_field('set','quality_of_service')) || (!form['employee_performance_id'] && $check_field('add','quality_of_service'))">
            {{ filter_text.quality_of_service || '请选择服务质量' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'quality_of_service')">
            {{ form['quality_of_service'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','working_attitude') || ($check_field('add','working_attitude') || $check_field('set','working_attitude'))" label="工作态度" name="working_attitude">
              <view class="filter-item" @click="showFilter('working_attitude')" v-if="(form['employee_performance_id'] && $check_field('set','working_attitude')) || (!form['employee_performance_id'] && $check_field('add','working_attitude'))">
            {{ filter_text.working_attitude || '请选择工作态度' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'working_attitude')">
            {{ form['working_attitude'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','performance_level') || ($check_field('add','performance_level') || $check_field('set','performance_level'))" label="绩效等级" name="performance_level">
              <view class="filter-item" @click="showFilter('performance_level')" v-if="(form['employee_performance_id'] && $check_field('set','performance_level')) || (!form['employee_performance_id'] && $check_field('add','performance_level'))">
            {{ filter_text.performance_level || '请选择绩效等级' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'performance_level')">
            {{ form['performance_level'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','assessment_results') || ($check_field('add','assessment_results') || $check_field('set','assessment_results'))" label="评估结果" name="assessment_results">
                      <uni-easyinput type="text" v-model="form['assessment_results']" v-if="(form['employee_performance_id'] && $check_field('set','assessment_results')) || (!form['employee_performance_id'] && $check_field('add','assessment_results'))" :disabled="disabledObj['assessment_results_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','assessment_results')">
            {{ form['assessment_results'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','work_suggestion') || ($check_field('add','work_suggestion') || $check_field('set','work_suggestion'))" label="工作建议" name="work_suggestion">
              <uni-easyinput type="textarea" v-model="form['work_suggestion']" v-if="(form['employee_performance_id'] && $check_field('set', 'work_suggestion')) || (!form['employee_performance_id'] && $check_field('add', 'work_suggestion'))" :disabled="disabledObj['work_suggestion_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'work_suggestion')">
            {{ form['work_suggestion'] }}
          </text>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/employee_performance/view','set') || ($check_action('/employee_performance/view','add') || $check_option('/employee_performance/table','examine'))">
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          {{ item.quality_of_service || item.working_attitude || item.performance_level }}
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
      field: "employee_performance_id",
      url_add: "~/api/employee_performance/add?",
      url_set: "~/api/employee_performance/set?",
      url_get_obj: "~/api/employee_performance/get_obj?",
      url_upload: "~/api/employee_performance/upload?",
      performance_title: null,
	      employee_name: null,
	      evaluation_time: null,
	      service_singular: null,
	      quality_of_service: null,
	      working_attitude: null,
	      performance_level: null,
	      assessment_results: null,
	      work_suggestion: null,
										                                          query: {
        "employee_performance_id": 0,
      },
      oldForm: {},
      form: {
          "performance_title":  '', // 绩效标题
            "employee_name":  '', // 员工姓名
            "evaluation_time": this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            "service_singular":  0 , // 服务单数
            "quality_of_service":  '', // 服务质量
            "working_attitude":  '', // 工作态度
            "performance_level":  '', // 绩效等级
            "assessment_results":  '', // 评估结果
            "work_suggestion":  '', // 工作建议
          "employee_performance_id": 0, // ID
        "create_by": 0, // 创建人
      },
      disabledObj:{
          "performance_title_isDisabled": false,
            "employee_name_isDisabled": false,
            "evaluation_time_isDisabled": false,
                "quality_of_service_isDisabled": false,
            "working_attitude_isDisabled": false,
            "performance_level_isDisabled": false,
            "assessment_results_isDisabled": false,
            "work_suggestion_isDisabled": false,
        },
                                                  // 服务质量选项列表
      list_quality_of_service: [],
                          // 工作态度选项列表
      list_working_attitude: [],
                          // 绩效等级选项列表
      list_performance_level: [],
                        									      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/employee_performance/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  	  	  			    quality_of_service: '',
	  			    working_attitude: '',
	  			    performance_level: '',
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
    						if(!this.form.employee_performance_id){
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
                                                  if (!param.evaluation_time){
        return "评估时间不能为空";
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
        url: this.$fullUrl('~/api/employee_performance/upload?'),
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
      if (type == 'performance_title') this['performance_title'] = this.form['performance_title'] = "";
      if (type == 'employee_name') this['employee_name'] = this.form['employee_name'] = "";
      if (type == 'evaluation_time') this['evaluation_time'] = this.form['evaluation_time'] = "";
      if (type == 'service_singular') this['service_singular'] = this.form['service_singular'] = "";
      if (type == 'quality_of_service') this['quality_of_service'] = this.form['quality_of_service'] = "";
      if (type == 'working_attitude') this['working_attitude'] = this.form['working_attitude'] = "";
      if (type == 'performance_level') this['performance_level'] = this.form['performance_level'] = "";
      if (type == 'assessment_results') this['assessment_results'] = this.form['assessment_results'] = "";
      if (type == 'work_suggestion') this['work_suggestion'] = this.form['work_suggestion'] = "";
    },
    submit_() {
        if (this['performance_title'] !== null) this.form['performance_title'] = this['performance_title']
          if (this['employee_name'] !== null) this.form['employee_name'] = this['employee_name']
          if (this['evaluation_time'] !== null) this.form['evaluation_time'] = this['evaluation_time']
          if (this['service_singular'] !== null) this.form['service_singular'] = this['service_singular']
          if (this['quality_of_service'] !== null) this.form['quality_of_service'] = this['quality_of_service']
          if (this['working_attitude'] !== null) this.form['working_attitude'] = this['working_attitude']
          if (this['performance_level'] !== null) this.form['performance_level'] = this['performance_level']
          if (this['assessment_results'] !== null) this.form['assessment_results'] = this['assessment_results']
          if (this['work_suggestion'] !== null) this.form['work_suggestion'] = this['work_suggestion']
        if(this.form.extra !== null) this.form.extra = JSON.stringify(this.form.extra)

      console.log(this.form)
      if(!this.form.employee_performance_id){
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
            '/employee_performance/upload',
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
          uploadFilePathApi('/employee_performance/upload',filePath,undefined,undefined,
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
          if (this['performance_title'] !== null) this.form['performance_title'] = this['performance_title']
              if (this['employee_name'] !== null) this.form['employee_name'] = this['employee_name']
              if (this.form["evaluation_time"] && JSON.stringify(this.form["evaluation_time"]).indexOf("-")===-1) {
        this.form["evaluation_time"] = this.$toTime(parseInt(this.form["evaluation_time"]), "yyyy-MM-dd hh:mm:ss")
      }
              if (this['service_singular'] !== null) this.form['service_singular'] = this['service_singular']
              if (this['quality_of_service'] !== null) this.form['quality_of_service'] = this['quality_of_service']
              if (this['working_attitude'] !== null) this.form['working_attitude'] = this['working_attitude']
              if (this['performance_level'] !== null) this.form['performance_level'] = this['performance_level']
              if (this['assessment_results'] !== null) this.form['assessment_results'] = this['assessment_results']
              if (this['work_suggestion'] !== null) this.form['work_suggestion'] = this['work_suggestion']
          uni.db.del('form');

      return param;
    },
        
          
          
          
        /**
     * 获取服务质量列表
     */
    async get_list_quality_of_service() {
          this.list_quality_of_service = ['优','良','中','差'].map((o) => ({ 
        quality_of_service: o, 
        children: [] 
      }));
      if(!this.form["employee_performance_id"]) {
                  this.form["quality_of_service"] = this.list_quality_of_service[0].quality_of_service;
              }
            },
                    
        /**
     * 获取工作态度列表
     */
    async get_list_working_attitude() {
          this.list_working_attitude = ['优','良','中','差'].map((o) => ({ 
        working_attitude: o, 
        children: [] 
      }));
      if(!this.form["employee_performance_id"]) {
                  this.form["working_attitude"] = this.list_working_attitude[0].working_attitude;
              }
            },
                    
        /**
     * 获取绩效等级列表
     */
    async get_list_performance_level() {
          this.list_performance_level = ['S','A','B','C','D'].map((o) => ({ 
        performance_level: o, 
        children: [] 
      }));
      if(!this.form["employee_performance_id"]) {
                  this.form["performance_level"] = this.list_performance_level[0].performance_level;
              }
            },
                    
          
          
      // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	                case 'quality_of_service':
          this.popupTitle = '选择服务质量';
          this.initCascader(this.list_quality_of_service);
          break;
        	                case 'working_attitude':
          this.popupTitle = '选择工作态度';
          this.initCascader(this.list_working_attitude);
          break;
        	                case 'performance_level':
          this.popupTitle = '选择绩效等级';
          this.initCascader(this.list_performance_level);
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
																																																																																	              selectedText = selected.map(item => item.quality_of_service || item.working_attitude || item.performance_level).join(' / ');
                fieldValue = lastItem.quality_of_service || lastItem.working_attitude || lastItem.performance_level;
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
                                                          if (this.form["evaluation_time"] && JSON.stringify(this.form["evaluation_time"]).indexOf("-")===-1) {
        this.form["evaluation_time"] = this.$toTime(parseInt(this.form["evaluation_time"]), "yyyy-MM-dd hh:mm:ss")
      }
                                                                          if (json.result.obj.quality_of_service) {
        this.filter_text.quality_of_service = json.result.obj.quality_of_service;
      }
                                  if (json.result.obj.working_attitude) {
        this.filter_text.working_attitude = json.result.obj.working_attitude;
      }
                                  if (json.result.obj.performance_level) {
        this.filter_text.performance_level = json.result.obj.performance_level;
      }
                                                        
      if (json.result.obj.create_by) {
        this.form.create_by = json.result.obj.create_by;
            }
      this.oldForm = JSON.parse(JSON.stringify(this.form));
    },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/employee_performance/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/employee_performance/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/employee_performance/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/employee_performance/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/employee_performance/view', 'get');
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
                      this.get_list_quality_of_service();
          this.get_list_working_attitude();
          this.get_list_performance_level();
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
