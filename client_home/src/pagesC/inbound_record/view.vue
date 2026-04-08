<template>
  <view class="page_diy_view page_inbound_record_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>入库记录详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','consumables_code') || ($check_field('add','consumables_code') || $check_field('set','consumables_code'))" label="耗材编码" name="consumables_code">
                      <uni-easyinput type="text" v-model="form['consumables_code']" v-if="(form['inbound_record_id'] && $check_field('set','consumables_code')) || (!form['inbound_record_id'] && $check_field('add','consumables_code'))" :disabled="disabledObj['consumables_code_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','consumables_code')">
            {{ form['consumables_code'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','consumables_name') || ($check_field('add','consumables_name') || $check_field('set','consumables_name'))" label="耗材名称" name="consumables_name">
                      <uni-easyinput type="text" v-model="form['consumables_name']" v-if="(form['inbound_record_id'] && $check_field('set','consumables_name')) || (!form['inbound_record_id'] && $check_field('add','consumables_name'))" :disabled="disabledObj['consumables_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','consumables_name')">
            {{ form['consumables_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','consumables_specifications') || ($check_field('add','consumables_specifications') || $check_field('set','consumables_specifications'))" label="耗材规格" name="consumables_specifications">
                      <uni-easyinput type="text" v-model="form['consumables_specifications']" v-if="(form['inbound_record_id'] && $check_field('set','consumables_specifications')) || (!form['inbound_record_id'] && $check_field('add','consumables_specifications'))" :disabled="disabledObj['consumables_specifications_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','consumables_specifications')">
            {{ form['consumables_specifications'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','financial_user') || ($check_field('add','financial_user') || $check_field('set','financial_user'))" label="财务用户" name="financial_user">
              <uni-data-select
              id="form_financial_user"
              v-model="form['financial_user']"
              :localdata="list_user_financial_user"
              :clear="!disabledObj['financial_user_isDisabled']"
              :disabled="disabledObj['financial_user_isDisabled']"
              v-if="(form['inbound_record_id'] && $check_field('set', 'financial_user')) || (!form['inbound_record_id'] && $check_field('add', 'financial_user'))"
			   style="width: 100%;"
          ></uni-data-select>
          <uni-data-select
              v-model="form['financial_user']"
              :localdata="list_user_financial_user"
              :clear="false"
              :disabled="true"
              v-else-if="$check_field('get', 'financial_user')"
              id="financial_user"
			  style="width: 100%;"
          ></uni-data-select>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','inbound_time') || ($check_field('add','inbound_time') || $check_field('set','inbound_time'))" label="入库时间" name="inbound_time">
              <uni-datetime-picker v-if="(form['inbound_record_id'] && $check_field('set', 'inbound_time')) || (!form['inbound_record_id'] && $check_field('add', 'inbound_time'))" @change="changeLog($event, 'inbound_time')" v-model="form['inbound_time']" type="datetime" :disabled="disabledObj['inbound_time_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'inbound_time')">
            {{ form['inbound_time'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','inbound_quantity') || ($check_field('add','inbound_quantity') || $check_field('set','inbound_quantity'))" label="入库数量" name="inbound_quantity">
                      <uni-easyinput type="text" v-model="form['inbound_quantity']" v-if="(form['inbound_record_id'] && $check_field('set','inbound_quantity')) || (!form['inbound_record_id'] && $check_field('add','inbound_quantity'))" :disabled="disabledObj['inbound_quantity_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','inbound_quantity')">
            {{ form['inbound_quantity'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','inbound_notes') || ($check_field('add','inbound_notes') || $check_field('set','inbound_notes'))" label="入库备注" name="inbound_notes">
              <uni-easyinput type="textarea" v-model="form['inbound_notes']" v-if="(form['inbound_record_id'] && $check_field('set', 'inbound_notes')) || (!form['inbound_record_id'] && $check_field('add', 'inbound_notes'))" :disabled="disabledObj['inbound_notes_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'inbound_notes')">
            {{ form['inbound_notes'] }}
          </text>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/inbound_record/view','set') || ($check_action('/inbound_record/view','add') || $check_option('/inbound_record/table','examine'))">
        <view class="me-btn btn-submit" @click="submit_()"> 提交 </view>
        <view class="me-btn btn-cancel" @click="cancel()">取消</view>
      </view>
      <view class="form_button" v-else>
        <view class="me-btn btn-cancel" @click="cancel()">返回</view>
      </view>
    </view>
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
      field: "inbound_record_id",
      url_add: "~/api/inbound_record/add?",
      url_set: "~/api/inbound_record/set?",
      url_get_obj: "~/api/inbound_record/get_obj?",
      url_upload: "~/api/inbound_record/upload?",
      consumables_code: null,
	      consumables_name: null,
	      consumables_specifications: null,
	      financial_user: null,
	      inbound_time: null,
	      inbound_quantity: null,
	      inbound_notes: null,
								                                  query: {
        "inbound_record_id": 0,
      },
      oldForm: {},
      form: {
          "consumables_code":  '', // 耗材编码
            "consumables_name":  '', // 耗材名称
            "consumables_specifications":  '', // 耗材规格
            "financial_user": 0, // 财务用户
            "inbound_time": this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            "inbound_quantity":  0 , // 入库数量
            "inbound_notes":  '', // 入库备注
          "inbound_record_id": 0, // ID
        "create_by": 0, // 创建人
      },
      disabledObj:{
          "consumables_code_isDisabled": false,
            "consumables_name_isDisabled": false,
            "consumables_specifications_isDisabled": false,
            "financial_user_isDisabled": false,
            "inbound_time_isDisabled": false,
                "inbound_notes_isDisabled": false,
        },
                                      // 用户列表
      list_user_financial_user: [],
                  // 用户组
      group_user_financial_user: "",
                                    							      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/inbound_record/upload')
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
    						if(!this.form.inbound_record_id){
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
                                                                                          if (!param.inbound_time){
        return "入库时间不能为空";
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
        url: this.$fullUrl('~/api/inbound_record/upload?'),
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
      if (type == 'consumables_code') this['consumables_code'] = this.form['consumables_code'] = "";
      if (type == 'consumables_name') this['consumables_name'] = this.form['consumables_name'] = "";
      if (type == 'consumables_specifications') this['consumables_specifications'] = this.form['consumables_specifications'] = "";
      if (type == 'financial_user') this['financial_user'] = this.form['financial_user'] = "";
      if (type == 'inbound_time') this['inbound_time'] = this.form['inbound_time'] = "";
      if (type == 'inbound_quantity') this['inbound_quantity'] = this.form['inbound_quantity'] = "";
      if (type == 'inbound_notes') this['inbound_notes'] = this.form['inbound_notes'] = "";
    },
    submit_() {
        if (this['consumables_code'] !== null) this.form['consumables_code'] = this['consumables_code']
          if (this['consumables_name'] !== null) this.form['consumables_name'] = this['consumables_name']
          if (this['consumables_specifications'] !== null) this.form['consumables_specifications'] = this['consumables_specifications']
          if (this['financial_user'] !== null) this.form['financial_user'] = this['financial_user']
          if (this['inbound_time'] !== null) this.form['inbound_time'] = this['inbound_time']
          if (this['inbound_quantity'] !== null) this.form['inbound_quantity'] = this['inbound_quantity']
          if (this['inbound_notes'] !== null) this.form['inbound_notes'] = this['inbound_notes']
        if(this.form.extra !== null) this.form.extra = JSON.stringify(this.form.extra)

      console.log(this.form)
      if(!this.form.inbound_record_id){
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
            '/inbound_record/upload',
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
          uploadFilePathApi('/inbound_record/upload',filePath,undefined,undefined,
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
          if (this['consumables_code'] !== null) this.form['consumables_code'] = this['consumables_code']
              if (this['consumables_name'] !== null) this.form['consumables_name'] = this['consumables_name']
              if (this['consumables_specifications'] !== null) this.form['consumables_specifications'] = this['consumables_specifications']
              if (this['financial_user'] !== null) this.form['financial_user'] = this['financial_user']
              if (this.form["inbound_time"] && JSON.stringify(this.form["inbound_time"]).indexOf("-")===-1) {
        this.form["inbound_time"] = this.$toTime(parseInt(this.form["inbound_time"]), "yyyy-MM-dd hh:mm:ss")
      }
              if (this['inbound_quantity'] !== null) this.form['inbound_quantity'] = this['inbound_quantity']
              if (this['inbound_notes'] !== null) this.form['inbound_notes'] = this['inbound_notes']
          uni.db.del('form');

      return param;
    },
        
          
          
          /**
     * 获取财务用户用户列表
     */
    async get_list_user_financial_user() {
      var json = await this.$get("~/api/user/get_list?user_group=财务用户");
      if(json.result && json.result.list){
        json.result.list.map((o) => this.list_user_financial_user.push({value:o.user_id,text:o.nickname + '-' + o.username}));
      }
      else if(json.error){
        console.error(json.error);
      }
    },
        /**
     * 获取财务用户用户组
     */
    async get_group_user_financial_user() {
      this.form["financial_user"] = this.user.user_id;
      var json = await this.$get("~/api/user_group/get_obj?name=财务用户");
      if(json.result && json.result.obj){
        this.group_user_financial_user = json.result.obj;
        this.get_user_session_financial_user(this.form['financial_user'])
      }
      else if(json.error){
        console.error(json.error);
      }
    },
    get_user_session_financial_user(id){
      var _this = this;
      var user_id = {"user_id":_this.user.user_id}
      var url = "~/api/"+_this.group_user_financial_user.source_table+"/get_obj"
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
          if(_this.user.user_group == '财务用户'){
            _this.form["financial_user"] = _this.user.user_id
          }
          _this.disabledObj['financial_user' + '_isDisabled'] = true
          for (var i=0;i<arr.length;i++){
            if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
              for (var j = 0; j < arrForm.length; j++) {
                if (arr[i] === arrForm[j]) {
                  if (arr[i] !== "financial_user" && arr[i] !== "create_by") {
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
     * 获取对象之后
     * @param {Object} json
     * @param {Object} func
     */
    async get_obj_after(json, func) {
      if (json.result.obj.extra) {
        this.form.extra = JSON.parse(json.result.obj.extra);
      }
                                                                                                          if (this.form["inbound_time"] && JSON.stringify(this.form["inbound_time"]).indexOf("-")===-1) {
        this.form["inbound_time"] = this.$toTime(parseInt(this.form["inbound_time"]), "yyyy-MM-dd hh:mm:ss")
      }
                                                                        
      if (json.result.obj.create_by) {
        this.form.create_by = json.result.obj.create_by;
            }
      this.oldForm = JSON.parse(JSON.stringify(this.form));
    },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/inbound_record/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/inbound_record/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/inbound_record/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/inbound_record/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/inbound_record/view', 'get');
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
                    this.get_list_user_financial_user();
              this.get_group_user_financial_user();
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
