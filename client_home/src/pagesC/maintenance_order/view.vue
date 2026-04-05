<template>
  <view class="page_diy_view page_maintenance_order_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>保养订单详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','project_bundle') || ($check_field('add','project_bundle') || $check_field('set','project_bundle'))" label="项目套餐" name="project_bundle">
                      <uni-easyinput type="text" v-model="form['project_bundle']" v-if="(form['maintenance_order_id'] && $check_field('set','project_bundle')) || (!form['maintenance_order_id'] && $check_field('add','project_bundle'))" :disabled="disabledObj['project_bundle_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','project_bundle')">
            {{ form['project_bundle'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','project_price') || ($check_field('add','project_price') || $check_field('set','project_price'))" label="项目价格" name="project_price">
                      <uni-easyinput type="text" v-model="form['project_price']" v-if="(form['maintenance_order_id'] && $check_field('set','project_price')) || (!form['maintenance_order_id'] && $check_field('add','project_price'))" :disabled="disabledObj['project_price_isDisabled']">
                    	<template #right><text style="margin-right: 24rpx;">元</text></template>
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','project_price')">
            {{ form['project_price'] }}元          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','manager_user') || ($check_field('add','manager_user') || $check_field('set','manager_user'))" label="经理用户" name="manager_user">
              <uni-data-select
              id="form_manager_user"
              v-model="form['manager_user']"
              :localdata="list_user_manager_user"
              :clear="!disabledObj['manager_user_isDisabled']"
              :disabled="disabledObj['manager_user_isDisabled']"
              v-if="(form['maintenance_order_id'] && $check_field('set', 'manager_user')) || (!form['maintenance_order_id'] && $check_field('add', 'manager_user'))"
			   style="width: 100%;"
          ></uni-data-select>
          <uni-data-select
              v-model="form['manager_user']"
              :localdata="list_user_manager_user"
              :clear="false"
              :disabled="true"
              v-else-if="$check_field('get', 'manager_user')"
              id="manager_user"
			  style="width: 100%;"
          ></uni-data-select>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','owner_user') || ($check_field('add','owner_user') || $check_field('set','owner_user'))" label="车主用户" name="owner_user">
              <uni-data-select
              id="form_owner_user"
              v-model="form['owner_user']"
              :localdata="list_user_owner_user"
              :clear="!disabledObj['owner_user_isDisabled']"
              :disabled="disabledObj['owner_user_isDisabled']"
              v-if="(form['maintenance_order_id'] && $check_field('set', 'owner_user')) || (!form['maintenance_order_id'] && $check_field('add', 'owner_user'))"
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
              <uni-forms-item class="" v-if="$check_field('get','financial_user') || ($check_field('add','financial_user') || $check_field('set','financial_user'))" label="财务用户" name="financial_user">
              <uni-data-select
              id="form_financial_user"
              v-model="form['financial_user']"
              :localdata="list_user_financial_user"
              :clear="!disabledObj['financial_user_isDisabled']"
              :disabled="disabledObj['financial_user_isDisabled']"
              v-if="(form['maintenance_order_id'] && $check_field('set', 'financial_user')) || (!form['maintenance_order_id'] && $check_field('add', 'financial_user'))"
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
              <uni-forms-item class="" v-if="$check_field('get','purchase_time') || ($check_field('add','purchase_time') || $check_field('set','purchase_time'))" label="购买时间" name="purchase_time">
              <uni-datetime-picker v-if="(form['maintenance_order_id'] && $check_field('set', 'purchase_time')) || (!form['maintenance_order_id'] && $check_field('add', 'purchase_time'))" @change="changeLog($event, 'purchase_time')" v-model="form['purchase_time']" type="datetime" :disabled="disabledObj['purchase_time_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'purchase_time')">
            {{ form['purchase_time'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','number_of_purchases') || ($check_field('add','number_of_purchases') || $check_field('set','number_of_purchases'))" label="购买次数" name="number_of_purchases">
              <view class="filter-item" @click="showFilter('number_of_purchases')" v-if="(form['maintenance_order_id'] && $check_field('set','number_of_purchases')) || (!form['maintenance_order_id'] && $check_field('add','number_of_purchases'))">
            {{ filter_text.number_of_purchases || '请选择购买次数' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'number_of_purchases')">
            {{ form['number_of_purchases'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','purchase_notes') || ($check_field('add','purchase_notes') || $check_field('set','purchase_notes'))" label="购买备注" name="purchase_notes">
              <uni-easyinput type="textarea" v-model="form['purchase_notes']" v-if="(form['maintenance_order_id'] && $check_field('set', 'purchase_notes')) || (!form['maintenance_order_id'] && $check_field('add', 'purchase_notes'))" :disabled="disabledObj['purchase_notes_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'purchase_notes')">
            {{ form['purchase_notes'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','order_status') || ($check_field('add','order_status') || $check_field('set','order_status'))" label="订单状态" name="order_status">
              <view class="filter-item" @click="showFilter('order_status')" v-if="(form['maintenance_order_id'] && $check_field('set','order_status')) || (!form['maintenance_order_id'] && $check_field('add','order_status'))">
            {{ filter_text.order_status || '请选择订单状态' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'order_status')">
            {{ form['order_status'] }}
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
      <view class="form-footer" v-if="$check_action('/maintenance_order/view','set') || ($check_action('/maintenance_order/view','add') || $check_option('/maintenance_order/table','examine'))">
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {{ item.number_of_purchases || item.order_status }}
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
      field: "maintenance_order_id",
      url_add: "~/api/maintenance_order/add?",
      url_set: "~/api/maintenance_order/set?",
      url_get_obj: "~/api/maintenance_order/get_obj?",
      url_upload: "~/api/maintenance_order/upload?",
      project_bundle: null,
	      project_price: null,
	      manager_user: null,
	      owner_user: null,
	      financial_user: null,
	      purchase_time: null,
	      number_of_purchases: null,
	      purchase_notes: null,
	      order_status: null,
										                                          query: {
        "maintenance_order_id": 0,
      },
      form: {
          "project_bundle":  '', // 项目套餐
            "project_price":  '', // 项目价格
            "manager_user": 0, // 经理用户
            "owner_user": 0, // 车主用户
            "financial_user": 0, // 财务用户
            "purchase_time": this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            "number_of_purchases":  '', // 购买次数
            "purchase_notes":  '', // 购买备注
            "order_status":  '', // 订单状态
          "examine_state": "未审核",
        "examine_reply": "",
        "maintenance_order_id": 0, // ID
      },
      disabledObj:{
          "project_bundle_isDisabled": false,
            "project_price_isDisabled": false,
            "manager_user_isDisabled": false,
            "owner_user_isDisabled": false,
            "financial_user_isDisabled": false,
            "purchase_time_isDisabled": false,
            "number_of_purchases_isDisabled": false,
            "purchase_notes_isDisabled": false,
            "order_status_isDisabled": false,
        },
                              // 用户列表
      list_user_manager_user: [],
                          // 用户列表
      list_user_owner_user: [],
                  // 用户组
      group_user_owner_user: "",
                          // 用户列表
      list_user_financial_user: [],
                                      // 购买次数选项列表
      list_number_of_purchases: [],
                                  // 订单状态选项列表
      list_order_status: [],
              list_examine_state:[{value:"未审核",text:"未审核"},{value:"已通过",text:"已通过"},{value:"未通过",text:"未通过"}],
									      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/maintenance_order/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  	  	  	  	  			    number_of_purchases: '',
	  	  			    order_status: '',
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
						if(!this.form.maintenance_order_id){
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
                                                                                                              if (!param.purchase_time){
        return "购买时间不能为空";
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
        url: this.$fullUrl('~/api/maintenance_order/upload?'),
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
      if (type == 'project_bundle') this['project_bundle'] = this.form['project_bundle'] = "";
      if (type == 'project_price') this['project_price'] = this.form['project_price'] = "";
      if (type == 'manager_user') this['manager_user'] = this.form['manager_user'] = "";
      if (type == 'owner_user') this['owner_user'] = this.form['owner_user'] = "";
      if (type == 'financial_user') this['financial_user'] = this.form['financial_user'] = "";
      if (type == 'purchase_time') this['purchase_time'] = this.form['purchase_time'] = "";
      if (type == 'number_of_purchases') this['number_of_purchases'] = this.form['number_of_purchases'] = "";
      if (type == 'purchase_notes') this['purchase_notes'] = this.form['purchase_notes'] = "";
      if (type == 'order_status') this['order_status'] = this.form['order_status'] = "";
    },
    submit_() {
        if (this['project_bundle'] !== null) this.form['project_bundle'] = this['project_bundle']
          if (this['project_price'] !== null) this.form['project_price'] = this['project_price']
          if (this['manager_user'] !== null) this.form['manager_user'] = this['manager_user']
          if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
          if (this['financial_user'] !== null) this.form['financial_user'] = this['financial_user']
          if (this['purchase_time'] !== null) this.form['purchase_time'] = this['purchase_time']
          if (this['number_of_purchases'] !== null) this.form['number_of_purchases'] = this['number_of_purchases']
          if (this['purchase_notes'] !== null) this.form['purchase_notes'] = this['purchase_notes']
          if (this['order_status'] !== null) this.form['order_status'] = this['order_status']
        console.log(this.form)
      if(!this.form.maintenance_order_id){
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
            '/maintenance_order/upload',
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
          uploadFilePathApi('/maintenance_order/upload',filePath,undefined,undefined,
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
          if (this['project_bundle'] !== null) this.form['project_bundle'] = this['project_bundle']
              if (this['project_price'] !== null) this.form['project_price'] = this['project_price']
              if (this['manager_user'] !== null) this.form['manager_user'] = this['manager_user']
              if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
              if (this['financial_user'] !== null) this.form['financial_user'] = this['financial_user']
              if (this.form["purchase_time"] && JSON.stringify(this.form["purchase_time"]).indexOf("-")===-1) {
        this.form["purchase_time"] = this.$toTime(parseInt(this.form["purchase_time"]), "yyyy-MM-dd hh:mm:ss")
      }
              if (this['number_of_purchases'] !== null) this.form['number_of_purchases'] = this['number_of_purchases']
              if (this['purchase_notes'] !== null) this.form['purchase_notes'] = this['purchase_notes']
              if (this['order_status'] !== null) this.form['order_status'] = this['order_status']
          uni.db.del('form');

      return param;
    },
        
          
          /**
     * 获取经理用户用户列表
     */
    async get_list_user_manager_user() {
      var json = await this.$get("~/api/user/get_list?user_group=经理用户");
      if(json.result && json.result.list){
        json.result.list.map((o) => this.list_user_manager_user.push({value:o.user_id,text:o.nickname + '-' + o.username}));
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
     * 获取购买次数列表
     */
    async get_list_number_of_purchases() {
          this.list_number_of_purchases = ['1'].map((o) => ({ 
        number_of_purchases: o, 
        children: [] 
      }));
      if(!this.form["maintenance_order_id"]) {
                  this.form["number_of_purchases"] = this.list_number_of_purchases[0].value;
              }
            },
                    
          
        /**
     * 获取订单状态列表
     */
    async get_list_order_status() {
          this.list_order_status = ['待完成','进行中','已完成'].map((o) => ({ 
        order_status: o, 
        children: [] 
      }));
      if(!this.form["maintenance_order_id"]) {
                  this.form["order_status"] = this.list_order_status[0].value;
              }
            },
                    
      // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	        	                case 'number_of_purchases':
          this.popupTitle = '选择购买次数';
          this.initCascader(this.list_number_of_purchases);
          break;
        	        	                case 'order_status':
          this.popupTitle = '选择订单状态';
          this.initCascader(this.list_order_status);
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
																																																																													              selectedText = selected.map(item => item.number_of_purchases || item.order_status).join(' / ');
                fieldValue = lastItem.number_of_purchases || lastItem.order_status;
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
                                                                                                                                  if (this.form["purchase_time"] && JSON.stringify(this.form["purchase_time"]).indexOf("-")===-1) {
        this.form["purchase_time"] = this.$toTime(parseInt(this.form["purchase_time"]), "yyyy-MM-dd hh:mm:ss")
      }
                                                  if (json.result.obj.number_of_purchases) {
        this.filter_text.number_of_purchases = json.result.obj.number_of_purchases;
      }
                                                          if (json.result.obj.order_status) {
        this.filter_text.order_status = json.result.obj.order_status;
      }
            },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/maintenance_order/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/maintenance_order/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/maintenance_order/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/maintenance_order/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/maintenance_order/view', 'get');
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
                this.get_list_user_manager_user();
                this.get_list_user_owner_user();
              this.get_group_user_owner_user();
                this.get_list_user_financial_user();
                  this.get_list_number_of_purchases();
              this.get_list_order_status();
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
