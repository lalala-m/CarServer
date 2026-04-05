<template>
  <view class="page_diy_edit page_test_drive_record_edit" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>试驾记录</tn-nav-bar>
    <view class="page-custom " id="page_diy_edit" ref="scroll">
      <view class="custom-from">
        <view class="custom-from-item" v-for="(value, key) in extra_info" :key="key" v-if="extra_info">
          <view class="label">
            <text>{{ key }}</text>
          </view>
          <view class="content">
            <text>{{ value }}</text>
          </view>
        </view>
              <view v-if="$check_field('set', 'test_drive_code') || $check_field('add', 'test_drive_code') ||  $check_field('get', 'test_drive_code')" class="custom-from-item">
          <view class="label">
            <text>
              试驾编码
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['test_drive_code'] && $check_field('set','test_drive_code')) || (!form['test_drive_code'] && $check_field('add','test_drive_code'))">
				<input type="text" id="form_test_drive_code" v-model="form['test_drive_code']" placeholder="请输入试驾编码" :disabled="true" />
							</view>
            <text v-else-if="$check_field('get','test_drive_code')">{{ form['test_drive_code'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'car_coding') || $check_field('add', 'car_coding') ||  $check_field('get', 'car_coding')" class="custom-from-item">
          <view class="label">
            <text>
              汽车编码
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['car_coding'] && $check_field('set','car_coding')) || (!form['car_coding'] && $check_field('add','car_coding'))">
				<input type="text" id="form_car_coding" v-model="form['car_coding']" placeholder="请输入汽车编码" :disabled="disabledObj['car_coding_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','car_coding')">{{ form['car_coding'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'car_name') || $check_field('add', 'car_name') ||  $check_field('get', 'car_name')" class="custom-from-item">
          <view class="label">
            <text>
              汽车名称
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['car_name'] && $check_field('set','car_name')) || (!form['car_name'] && $check_field('add','car_name'))">
				<input type="text" id="form_car_name" v-model="form['car_name']" placeholder="请输入汽车名称" :disabled="disabledObj['car_name_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','car_name')">{{ form['car_name'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'car_models') || $check_field('add', 'car_models') ||  $check_field('get', 'car_models')" class="custom-from-item">
          <view class="label">
            <text>
              汽车车型
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['car_models'] && $check_field('set','car_models')) || (!form['car_models'] && $check_field('add','car_models'))">
				<input type="text" id="form_car_models" v-model="form['car_models']" placeholder="请输入汽车车型" :disabled="disabledObj['car_models_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','car_models')">{{ form['car_models'] }}</text>
          </view>
                    </view>
                <view v-if="$check_field('set', 'business_user') || $check_field('add', 'business_user') ||  $check_field('get', 'business_user')" class="custom-from-item">
          <view class="label">
            <text>商家用户</text>
          </view>
          <view class="content">
            <uni-data-select
                    id="form_business_user" v-model="form['business_user']"
                    :localdata="list_user_business_user"
                    :clear="!disabledObj['business_user_isDisabled']"
                    :disabled="disabledObj['business_user_isDisabled']"
                    v-if="(form['business_user'] && $check_field('set', 'business_user')) || (!form['business_user'] && $check_field('add', 'business_user'))" style="width: 100%;">
            </uni-data-select>
            <text v-else-if="$check_field('get', 'business_user')">
              {{form['business_user']}}
            </text>
          </view>
        </view>
            <view v-if="$check_field('set', 'owner_user') || $check_field('add', 'owner_user') ||  $check_field('get', 'owner_user')" class="custom-from-item">
          <view class="label">
            <text>车主用户</text>
          </view>
          <view class="content">
            <uni-data-select
                    id="form_owner_user" v-model="form['owner_user']"
                    :localdata="list_user_owner_user"
                    :clear="!disabledObj['owner_user_isDisabled']"
                    :disabled="disabledObj['owner_user_isDisabled']"
                    v-if="(form['owner_user'] && $check_field('set', 'owner_user')) || (!form['owner_user'] && $check_field('add', 'owner_user'))" style="width: 100%;">
            </uni-data-select>
            <text v-else-if="$check_field('get', 'owner_user')">
              {{form['owner_user']}}
            </text>
          </view>
        </view>
                <view v-if="$check_field('set', 'test_drive_time') || $check_field('add', 'test_drive_time') ||  $check_field('get', 'test_drive_time')" class="custom-from-item">
          <view class="label">
            <text>
              试驾时间
            </text>
          </view>
              <!-- 日长 -->
          <view class="content">
            <uni-datetime-picker :disabled="disabledObj['test_drive_time_isDisabled']" type="datetime" id="form_test_drive_time"
                                 v-model="form['test_drive_time']" placeholder="请输入保质时间" v-if="
                    (form['test_drive_time'] && $check_field('set', 'test_drive_time')) ||
                    (!form['test_drive_time'] && $check_field('add', 'test_drive_time'))
                    " />
            <text v-else-if="$check_field('get', 'test_drive_time')">{{ form['test_drive_time'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'test_drive_remarks') || $check_field('add', 'test_drive_remarks') ||  $check_field('get', 'test_drive_remarks')" class="custom-from-item">
          <view class="label">
            <text>
              试驾备注
            </text>
          </view>
              <!-- 多文本 -->
          <view class="content">
            <textarea id="form_test_drive_remarks" v-model="form['test_drive_remarks']" v-if="(form['test_drive_remarks'] && $check_field('set','test_drive_remarks')) || (!form['test_drive_remarks'] && $check_field('add','test_drive_remarks'))" :disabled="disabledObj['test_drive_remarks_isDisabled']"/>
            <text v-else-if="$check_field('get','test_drive_remarks')">{{ form['test_drive_remarks'] }}</text>
          </view>
            </view>
              <view class="form-footer">
                  <view class="me-btn" @click="submitForm()">提交</view>
                </view>
      </view>
    </view>
                                  </view>
</template>

<script>
import mixin from '@/libs/mixins/page.js';
								export default {
  mixins: [mixin],
    components: {
								  },
  data() {
    return {
      url_get_obj: '~/api/test_drive_record/get_obj?',
      url_add: '~/api/test_drive_record/add?',
      url_set: '~/api/test_drive_record/set?',
	    today: new Date().toISOString().split('T')[0],
      extra_info: {},
      // 查询条件
      query: {
        test_drive_record_id: 0,
          test_drive_code: "",
            car_coding: "",
            car_name: "",
            car_models: "",
            business_user: 0,
            owner_user: 0,
            test_drive_time: "",
            test_drive_remarks: "",
        },
      obj: {
        test_drive_record_id: 0,
          test_drive_code: this.$get_stamp(), // 试驾编码
            car_coding:  '', // 汽车编码
            car_name:  '', // 汽车名称
            car_models:  '', // 汽车车型
            business_user: 0, // 商家用户
            owner_user: 0, // 车主用户
            test_drive_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            test_drive_remarks:  '', // 试驾备注
        },
								      // 表单字段
      form: {
        test_drive_record_id: 0,
          test_drive_code: this.$get_stamp(), // 试驾编码
            car_coding:  '', // 汽车编码
            car_name:  '', // 汽车名称
            car_models:  '', // 汽车车型
            business_user: 0, // 商家用户
            owner_user: 0, // 车主用户
            test_drive_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            test_drive_remarks:  '', // 试驾备注
        },
      disabledObj: {
          test_drive_code_isDisabled: false,
            car_coding_isDisabled: false,
            car_name_isDisabled: false,
            car_models_isDisabled: false,
            business_user_isDisabled: false,
            owner_user_isDisabled: false,
            test_drive_time_isDisabled: false,
            test_drive_remarks_isDisabled: false,
        },
								                    	        	        	        	        	          // 用户列表
      list_user_business_user: [],
        	          // 用户列表
      list_user_owner_user: [],
        	        	          field: 'test_drive_record_id',
      table_key: 'test_drive_record',

								      uploadKey: '',
      uploadOptions: {
        url: this.$fullUrl('~/api/test_drive_record/upload')
      },
                      payTyep: null,
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
            const goBack = () => {
              // #ifdef H5
              if (this.$router) {
                this.$router.go(-1);
                return;
              }
              // #endif
              uni.navigateBack({ delta: 1 });
            };
						if(!this.form.test_drive_record_id){
  			    					    					    					    					    					    					    					    					    																					this.$post("~/api/car_information/set?car_coding=" + this.form.car_coding, { car_status: "试驾中" }, (res) => {
								
							}).catch(() => {
								console.log("修改状态1出错")
							})
																					        		  						      																																													                              this.$toast('提交成功');
							setTimeout(goBack, 1000);
						}else{
							goBack();
						}
					},
    /**
     * 提交前验证事件
     * @param {Object} 请求参数
     * @return {String} 验证成功返回null, 失败返回错误提示
     */
    async submit_check(param) {
                                                                                                                                  if (!param.test_drive_time){
        return "试驾时间不能为空";
      }
                                              let test_drive_remarks_SensitiveWords = await this.filterSensitiveWords(param.test_drive_remarks)
      if(test_drive_remarks_SensitiveWords.length > 0){
        return '试驾备注中包含敏感词: ' + test_drive_remarks_SensitiveWords.join(',')
      }
          return null;
    },
    	                    	                    	                    	                    	        /**
    * 获取商家用户用户列表
    */
    async get_list_user_business_user() {
        var json = await this.$get("~/api/user/get_list?user_group=商家用户");
        if(json.result && json.result.list){
          json.result.list.map((o) => this.list_user_business_user.push({value:o.user_id,text:o.nickname + '-' + o.username}));
        }
        else if(json.error){
          console.error(json.error);
        }
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
            async get_user_session_owner_user(){
        var _this = this;
        var json = await this.$get("~/api/user_group/get_obj?name=车主用户");
        if(json.result && json.result.obj){
          var source_table = json.result.obj.source_table;
          var user_id = _this.userInfo.user_id;
          if (user_id){
            var url = "~/api/"+source_table+"/get_obj"
            this.$get(url, { user_id: _this.userInfo.user_id }, function (res) {
              if (res.result && res.result.obj) {
                var arr = []
                for (let key in res.result.obj) {
                  arr.push(key)
                }
                var arrForm = []
                for (let key in _this.form) {
                  arrForm.push(key)
                }
                _this.form["owner_user"] = user_id
                _this.disabledObj['owner_user' + '_isDisabled'] = true
                for (var i=0;i<arr.length;i++){
                  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
                    for (var j = 0; j < arrForm.length; j++) {
                      if (arr[i] === arrForm[j]) {
                        if (arr[i] !== "owner_user") {
                          _this.form[arrForm[j]] = res.result.obj[arr[i]]
                          _this.disabledObj[arrForm[j] + '_isDisabled'] = true
                          break;
                        }
                      }
                    }
                  }
                }
              }
            });
          }
        }
        else if(json.error){
          console.error(json.error);
        }
    },
                	                    	                    // 自动计算精度处理
		toFixed: function(num){
			if (!isNaN(num)) {
				return ((num + '').indexOf('.') == -1) ? num : num.toFixed(2);
			}
		},
    change_img(key_name) {
      var _self = this;
      _self.upload_img_flag = false;
      // 选择图像方法
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
        // sourceType: ['album'], //从相册选择
        success: function (res) {
          const tempFilePaths = res.tempFilePaths;
          const uploadTask = uni.uploadFile({
            url: _self.$fullUrl('~/api/test_drive_record/upload?'),
            filePath: tempFilePaths[0],
            name: 'file',
            formData: {
              test_drive_record: 'test',
            },
            header: {
              'x-auth-token': _self.$store.state.app.userInfo.token,
            },
            success: function (uploadFileRes) {
              var filename = JSON.parse(uploadFileRes.data).result.url;
              var img_url = filename;
              _self.form[key_name] = img_url;
            },
          });

          uploadTask.onProgressUpdate(function (res) {
            _self.percent = res.progress;
            console.log('上传进度' + res.progress);
            console.log('已经上传的数据长度' + res.totalBytesSent);
            console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
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
                                  if (this.form["test_drive_time"] && JSON.stringify(this.form["test_drive_time"]).indexOf("-")===-1) {
        this.form["test_drive_time"] = this.$toTime(parseInt(this.form["test_drive_time"]), "yyyy-MM-dd hh:mm:ss")
      }
              uni.db.del('form');
      return param;
    },
	
																
    /**
     * 获取对象后获取缓存表单
     * @param {Object} json
     * @param {Object} func
     */
    get_obj_after(json, func) {
      var form = uni.db.get('form');
      var obj = Object.assign({}, form, this.obj);
      if (form) {
        this.obj = uni.push(this.obj, obj);
      }
      if (form) {
        this.form = uni.push(this.form, form);
      }
                                                                                          if (func) {
        func(json);
      }
    },

                                    handleUploadClick(key) {
      this.uploadKey = key;
      this.$refs.XeUpload.upload('file', {});
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
                    close_(type) {
                                    },
    submitForm(payTyep) {
    this.payTyep = payTyep
                                      if (this.extra_info && Object.keys(this.extra_info).length > 0) {
        this.form.extra = JSON.stringify(this.extra_info);
      }
      setTimeout(() => {
        this.submit(payTyep);
      }, 500);
    },
  },
  onLoad(e) {
    if(e.info) {
      this.extra_info = JSON.parse(decodeURIComponent(decodeURIComponent(e.info)));
    }
                                  this.get_list_user_business_user();
          this.get_user_session_owner_user();
        this.get_list_user_owner_user();
                },
};
</script>

<style lang="scss" scoped>
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
  .editor-item {
    flex: 1;
    min-height: 400px;
    border: 1px solid #e4e4e4;
    max-height: 500px;
  }
.content.audio, .content.video, .content.file {
  flex-direction: column;
  justify-content: start;
  width: calc(100% - 80px);
  align-items: start !important;
  .file-url {
    word-break: break-all;
    width: 100%;
  }
}
.pay-model .pay-model-inn {
	z-index: 9;
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
