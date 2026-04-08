<template>
  <view class="page_diy_view page_supplier_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>供应厂商详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','name_of_manufacturer') || ($check_field('add','name_of_manufacturer') || $check_field('set','name_of_manufacturer'))" label="厂家名称" name="name_of_manufacturer">
                      <uni-easyinput type="text" v-model="form['name_of_manufacturer']" v-if="(form['supplier_id'] && $check_field('set','name_of_manufacturer')) || (!form['supplier_id'] && $check_field('add','name_of_manufacturer'))" :disabled="disabledObj['name_of_manufacturer_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','name_of_manufacturer')">
            {{ form['name_of_manufacturer'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','contact_phone') || ($check_field('add','contact_phone') || $check_field('set','contact_phone'))" label="联系电话" name="contact_phone">
              <uni-easyinput type="number" v-model="form['contact_phone']" v-if="(form['supplier_id'] && $check_field('set','contact_phone')) || (!form['supplier_id'] && $check_field('add','contact_phone'))" :disabled="disabledObj['contact_phone_isDisabled']" />
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get','contact_phone')">
            {{ form['contact_phone'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','manufacturer_address') || ($check_field('add','manufacturer_address') || $check_field('set','manufacturer_address'))" label="厂家地址" name="manufacturer_address">
                      <uni-easyinput type="text" v-model="form['manufacturer_address']" v-if="(form['supplier_id'] && $check_field('set','manufacturer_address')) || (!form['supplier_id'] && $check_field('add','manufacturer_address'))" :disabled="disabledObj['manufacturer_address_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','manufacturer_address')">
            {{ form['manufacturer_address'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','main_products') || ($check_field('add','main_products') || $check_field('set','main_products'))" label="主营产品" name="main_products">
              <uni-easyinput type="textarea" v-model="form['main_products']" v-if="(form['supplier_id'] && $check_field('set', 'main_products')) || (!form['supplier_id'] && $check_field('add', 'main_products'))" :disabled="disabledObj['main_products_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'main_products')">
            {{ form['main_products'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','manufacturer_profile') || ($check_field('add','manufacturer_profile') || $check_field('set','manufacturer_profile'))" label="厂家简介" name="manufacturer_profile">
              <uni-easyinput type="textarea" v-model="form['manufacturer_profile']" v-if="(form['supplier_id'] && $check_field('set', 'manufacturer_profile')) || (!form['supplier_id'] && $check_field('add', 'manufacturer_profile'))" :disabled="disabledObj['manufacturer_profile_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'manufacturer_profile')">
            {{ form['manufacturer_profile'] }}
          </text>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/supplier/view','set') || ($check_action('/supplier/view','add') || $check_option('/supplier/table','examine'))">
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
      field: "supplier_id",
      url_add: "~/api/supplier/add?",
      url_set: "~/api/supplier/set?",
      url_get_obj: "~/api/supplier/get_obj?",
      url_upload: "~/api/supplier/upload?",
      name_of_manufacturer: null,
	      contact_phone: null,
	      manufacturer_address: null,
	      main_products: null,
	      manufacturer_profile: null,
						                          query: {
        "supplier_id": 0,
      },
      oldForm: {},
      form: {
          "name_of_manufacturer": null, // 厂家名称
            "contact_phone": null, // 联系电话
            "manufacturer_address":  '', // 厂家地址
            "main_products":  '', // 主营产品
            "manufacturer_profile":  '', // 厂家简介
          "supplier_id": 0, // ID
        "create_by": 0, // 创建人
      },
      disabledObj:{
          "name_of_manufacturer_isDisabled": false,
            "contact_phone_isDisabled": false,
            "manufacturer_address_isDisabled": false,
            "main_products_isDisabled": false,
            "manufacturer_profile_isDisabled": false,
        },
                                        					      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/supplier/upload')
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
    						if(!this.form.supplier_id){
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
          if (!param.name_of_manufacturer){
        return "厂家名称不能为空";
      }
                              if (!param.contact_phone){
        return "联系电话不能为空";
      }
              let contact_phone_phone_regular = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if(param.contact_phone && !contact_phone_phone_regular.test(param.contact_phone)){
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
        url: this.$fullUrl('~/api/supplier/upload?'),
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
      if (type == 'name_of_manufacturer') this['name_of_manufacturer'] = this.form['name_of_manufacturer'] = "";
      if (type == 'contact_phone') this['contact_phone'] = this.form['contact_phone'] = "";
      if (type == 'manufacturer_address') this['manufacturer_address'] = this.form['manufacturer_address'] = "";
      if (type == 'main_products') this['main_products'] = this.form['main_products'] = "";
      if (type == 'manufacturer_profile') this['manufacturer_profile'] = this.form['manufacturer_profile'] = "";
    },
    submit_() {
        if (this['name_of_manufacturer'] !== null) this.form['name_of_manufacturer'] = this['name_of_manufacturer']
          if (this['contact_phone'] !== null) this.form['contact_phone'] = this['contact_phone']
          if (this['manufacturer_address'] !== null) this.form['manufacturer_address'] = this['manufacturer_address']
          if (this['main_products'] !== null) this.form['main_products'] = this['main_products']
          if (this['manufacturer_profile'] !== null) this.form['manufacturer_profile'] = this['manufacturer_profile']
        if(this.form.extra !== null) this.form.extra = JSON.stringify(this.form.extra)

      console.log(this.form)
      if(!this.form.supplier_id){
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
            '/supplier/upload',
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
          uploadFilePathApi('/supplier/upload',filePath,undefined,undefined,
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
          if (this['name_of_manufacturer'] !== null) this.form['name_of_manufacturer'] = this['name_of_manufacturer']
              if (this['contact_phone'] !== null) this.form['contact_phone'] = this['contact_phone']
              if (this['manufacturer_address'] !== null) this.form['manufacturer_address'] = this['manufacturer_address']
              if (this['main_products'] !== null) this.form['main_products'] = this['main_products']
              if (this['manufacturer_profile'] !== null) this.form['manufacturer_profile'] = this['manufacturer_profile']
          uni.db.del('form');

      return param;
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
                                                                                                                        
      if (json.result.obj.create_by) {
        this.form.create_by = json.result.obj.create_by;
            }
      this.oldForm = JSON.parse(JSON.stringify(this.form));
    },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/supplier/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/supplier/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/supplier/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/supplier/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/supplier/view', 'get');
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
