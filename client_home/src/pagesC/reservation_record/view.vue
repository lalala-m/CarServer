<template>
  <view class="page_diy_view page_reservation_record_view" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>预约记录详情</tn-nav-bar>
    <view id="page_diy_view" class="page-from">
      <uni-forms :modelValue="form" v-if="is_view()" labelWidth="70px">
        <template v-if="form['extra']">
          <uni-forms-item class="" v-for="(value, key) in form['extra']" :key="key" :label="key" :name="key">
            <text>{{ value }}</text>
          </uni-forms-item>
        </template>
            <uni-forms-item class="" v-if="$check_field('get','booking_code') || ($check_field('add','booking_code') || $check_field('set','booking_code'))" label="预约编码" name="booking_code">
                      <uni-easyinput type="text" v-model="form['booking_code']" v-if="(form['reservation_record_id'] && $check_field('set','booking_code')) || (!form['reservation_record_id'] && $check_field('add','booking_code'))"  :disabled="true">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','booking_code')">
            {{ form['booking_code'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_code') || ($check_field('add','service_code') || $check_field('set','service_code'))" label="服务编码" name="service_code">
                      <uni-easyinput type="text" v-model="form['service_code']" v-if="(form['reservation_record_id'] && $check_field('set','service_code')) || (!form['reservation_record_id'] && $check_field('add','service_code'))" :disabled="disabledObj['service_code_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_code')">
            {{ form['service_code'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_name') || ($check_field('add','service_name') || $check_field('set','service_name'))" label="服务名称" name="service_name">
                      <uni-easyinput type="text" v-model="form['service_name']" v-if="(form['reservation_record_id'] && $check_field('set','service_name')) || (!form['reservation_record_id'] && $check_field('add','service_name'))" :disabled="disabledObj['service_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_name')">
            {{ form['service_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','service_price') || ($check_field('add','service_price') || $check_field('set','service_price'))" label="服务价格" name="service_price">
                      <uni-easyinput type="text" v-model="form['service_price']" v-if="(form['reservation_record_id'] && $check_field('set','service_price')) || (!form['reservation_record_id'] && $check_field('add','service_price'))" :disabled="disabledObj['service_price_isDisabled']">
                    	<template #right><text style="margin-right: 24rpx;">元</text></template>
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','service_price')">
            {{ form['service_price'] }}元          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','owner_user') || ($check_field('add','owner_user') || $check_field('set','owner_user'))" label="车主用户" name="owner_user">
              <uni-data-select
              id="form_owner_user"
              v-model="form['owner_user']"
              :localdata="list_user_owner_user"
              :clear="!disabledObj['owner_user_isDisabled']"
              :disabled="disabledObj['owner_user_isDisabled']"
              v-if="(form['reservation_record_id'] && $check_field('set', 'owner_user')) || (!form['reservation_record_id'] && $check_field('add', 'owner_user'))"
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
              <uni-forms-item class="" v-if="$check_field('get','owners_name') || ($check_field('add','owners_name') || $check_field('set','owners_name'))" label="车主姓名" name="owners_name">
                      <uni-easyinput type="text" v-model="form['owners_name']" v-if="(form['reservation_record_id'] && $check_field('set','owners_name')) || (!form['reservation_record_id'] && $check_field('add','owners_name'))" :disabled="disabledObj['owners_name_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','owners_name')">
            {{ form['owners_name'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','contact_number') || ($check_field('add','contact_number') || $check_field('set','contact_number'))" label="联系号码" name="contact_number">
                      <uni-easyinput type="text" v-model="form['contact_number']" v-if="(form['reservation_record_id'] && $check_field('set','contact_number')) || (!form['reservation_record_id'] && $check_field('add','contact_number'))" :disabled="disabledObj['contact_number_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','contact_number')">
            {{ form['contact_number'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','appointment_date') || ($check_field('add','appointment_date') || $check_field('set','appointment_date'))" label="预约日期" name="appointment_date">
              <uni-datetime-picker v-if="(form['reservation_record_id'] && $check_field('set', 'appointment_date')) || (!form['reservation_record_id'] && $check_field('add', 'appointment_date'))" v-model="form['appointment_date']" type="date" :disabled="disabledObj['appointment_date_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'appointment_date')">
            {{ form['appointment_date'] }}
          </text>
	            </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','appointment_period') || ($check_field('add','appointment_period') || $check_field('set','appointment_period'))" label="预约时段" name="appointment_period">
              <view class="filter-item" @click="showFilter('appointment_period')" v-if="(form['reservation_record_id'] && $check_field('set','appointment_period')) || (!form['reservation_record_id'] && $check_field('add','appointment_period'))">
            {{ filter_text.appointment_period || '请选择预约时段' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'appointment_period')">
            {{ form['appointment_period'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','number_of_appointments') || ($check_field('add','number_of_appointments') || $check_field('set','number_of_appointments'))" label="预约次数" name="number_of_appointments">
              <view class="filter-item" @click="showFilter('number_of_appointments')" v-if="(form['reservation_record_id'] && $check_field('set','number_of_appointments')) || (!form['reservation_record_id'] && $check_field('add','number_of_appointments'))">
            {{ filter_text.number_of_appointments || '请选择预约次数' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'number_of_appointments')">
            {{ form['number_of_appointments'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','delivery_time') || ($check_field('add','delivery_time') || $check_field('set','delivery_time'))" label="交车时间" name="delivery_time">
              <uni-datetime-picker v-if="(form['reservation_record_id'] && $check_field('set', 'delivery_time')) || (!form['reservation_record_id'] && $check_field('add', 'delivery_time'))" v-model="form['delivery_time']" type="date" :disabled="disabledObj['delivery_time_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'delivery_time')">
            {{ form['delivery_time'] }}
          </text>
	            </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','sales_consultant') || ($check_field('add','sales_consultant') || $check_field('set','sales_consultant'))" label="销售顾问" name="sales_consultant">
                      <uni-easyinput type="text" v-model="form['sales_consultant']" v-if="(form['reservation_record_id'] && $check_field('set','sales_consultant')) || (!form['reservation_record_id'] && $check_field('add','sales_consultant'))" :disabled="disabledObj['sales_consultant_isDisabled']">
                      </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get','sales_consultant')">
            {{ form['sales_consultant'] }}          </text>
                        </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','license_plate_number') || ($check_field('add','license_plate_number') || $check_field('set','license_plate_number'))" label="车牌号码" name="license_plate_number">
              <view class="filter-item" @click="showFilter('license_plate_number')" v-if="(form['reservation_record_id'] && $check_field('set','license_plate_number')) || (!form['reservation_record_id'] && $check_field('add','license_plate_number'))">
            {{ filter_text.license_plate_number || '请选择车牌号码' }}
          </view>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'license_plate_number')">
            {{ form['license_plate_number'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','frame_number') || ($check_field('add','frame_number') || $check_field('set','frame_number'))" label="车架号" name="frame_number">
              <uni-easyinput type="text" v-model="form['frame_number']" v-if="(form['reservation_record_id'] && $check_field('set', 'frame_number')) || (!form['reservation_record_id'] && $check_field('add', 'frame_number'))" :disabled="true">
				  </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'frame_number')">
            {{ form['frame_number'] }}          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','car_type') || ($check_field('add','car_type') || $check_field('set','car_type'))" label="汽车类型" name="car_type">
              <uni-easyinput type="text" v-model="form['car_type']" v-if="(form['reservation_record_id'] && $check_field('set', 'car_type')) || (!form['reservation_record_id'] && $check_field('add', 'car_type'))" :disabled="true">
				  </uni-easyinput>
		  <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'car_type')">
            {{ form['car_type'] }}          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','appointment_remarks') || ($check_field('add','appointment_remarks') || $check_field('set','appointment_remarks'))" label="预约备注" name="appointment_remarks">
              <uni-easyinput type="textarea" v-model="form['appointment_remarks']" v-if="(form['reservation_record_id'] && $check_field('set', 'appointment_remarks')) || (!form['reservation_record_id'] && $check_field('add', 'appointment_remarks'))" :disabled="disabledObj['appointment_remarks_isDisabled']"/>
          <!-- 仅查看 -->
          <text v-else-if="$check_field('get', 'appointment_remarks')">
            {{ form['appointment_remarks'] }}
          </text>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','sales_manager') || ($check_field('add','sales_manager') || $check_field('set','sales_manager'))" label="销售经理" name="sales_manager">
              <uni-data-select
              id="form_sales_manager"
              v-model="form['sales_manager']"
              :localdata="list_user_sales_manager"
              :clear="!disabledObj['sales_manager_isDisabled']"
              :disabled="disabledObj['sales_manager_isDisabled']"
              v-if="(form['reservation_record_id'] && $check_field('set', 'sales_manager')) || (!form['reservation_record_id'] && $check_field('add', 'sales_manager'))"
			   style="width: 100%;"
          ></uni-data-select>
          <uni-data-select
              v-model="form['sales_manager']"
              :localdata="list_user_sales_manager"
              :clear="false"
              :disabled="true"
              v-else-if="$check_field('get', 'sales_manager')"
              id="sales_manager"
			  style="width: 100%;"
          ></uni-data-select>
                </uni-forms-item>
              <uni-forms-item class="" v-if="$check_field('get','account_manager') || ($check_field('add','account_manager') || $check_field('set','account_manager'))" label="客户经理" name="account_manager">
              <uni-data-select
              id="form_account_manager"
              v-model="form['account_manager']"
              :localdata="list_user_account_manager"
              :clear="!disabledObj['account_manager_isDisabled']"
              :disabled="disabledObj['account_manager_isDisabled']"
              v-if="(form['reservation_record_id'] && $check_field('set', 'account_manager')) || (!form['reservation_record_id'] && $check_field('add', 'account_manager'))"
			   style="width: 100%;"
          ></uni-data-select>
          <uni-data-select
              v-model="form['account_manager']"
              :localdata="list_user_account_manager"
              :clear="false"
              :disabled="true"
              v-else-if="$check_field('get', 'account_manager')"
              id="account_manager"
			  style="width: 100%;"
          ></uni-data-select>
                </uni-forms-item>
        </uni-forms>
      <view class="form-footer" v-if="$check_action('/reservation_record/view','set') || ($check_action('/reservation_record/view','add') || $check_option('/reservation_record/table','examine'))">
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                {{ item.time_period || item.number_of_appointments || item.license_plate_number }}
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
      field: "reservation_record_id",
      url_add: "~/api/reservation_record/add?",
      url_set: "~/api/reservation_record/set?",
      url_get_obj: "~/api/reservation_record/get_obj?",
      url_upload: "~/api/reservation_record/upload?",
      booking_code: null,
	      service_code: null,
	      service_name: null,
	      service_price: null,
	      owner_user: null,
	      owners_name: null,
	      contact_number: null,
	      appointment_date: null,
	      appointment_period: null,
	      number_of_appointments: null,
	      delivery_time: null,
	      sales_consultant: null,
	      license_plate_number: null,
	      frame_number: null,
	      car_type: null,
	      appointment_remarks: null,
	      sales_manager: null,
	      account_manager: null,
																			                                                                              query: {
        "reservation_record_id": 0,
      },
      form: {
          "booking_code": this.$get_stamp(), // 预约编码
            "service_code":  '', // 服务编码
            "service_name":  '', // 服务名称
            "service_price":  0 , // 服务价格
            "owner_user": 0, // 车主用户
            "owners_name":  '', // 车主姓名
            "contact_number":  '', // 联系号码
            "appointment_date": this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            "appointment_period":  '', // 预约时段
            "number_of_appointments":  '', // 预约次数
            "delivery_time": this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            "sales_consultant":  '', // 销售顾问
            "license_plate_number":  '', // 车牌号码
            "frame_number":  '', // 车架号
            "car_type":  '', // 汽车类型
            "appointment_remarks":  '', // 预约备注
            "sales_manager": 0, // 销售经理
            "account_manager": 0, // 客户经理
          "reservation_record_id": 0, // ID
      },
      disabledObj:{
          "booking_code_isDisabled": false,
            "service_code_isDisabled": false,
            "service_name_isDisabled": false,
                "owner_user_isDisabled": false,
            "owners_name_isDisabled": false,
            "contact_number_isDisabled": false,
            "appointment_date_isDisabled": false,
            "appointment_period_isDisabled": false,
            "number_of_appointments_isDisabled": false,
            "delivery_time_isDisabled": false,
            "sales_consultant_isDisabled": false,
            "license_plate_number_isDisabled": false,
            "frame_number_isDisabled": false,
            "car_type_isDisabled": false,
            "appointment_remarks_isDisabled": false,
            "sales_manager_isDisabled": false,
            "account_manager_isDisabled": false,
        },
                                              // 用户列表
      list_user_owner_user: [],
                  // 用户组
      group_user_owner_user: "",
                                                      // 预约时段选项列表
      list_appointment_period: [],
                          // 预约次数选项列表
      list_number_of_appointments: [],
                                          // 车牌号码选项列表
      list_license_plate_number: [],
                                              // 用户列表
      list_user_sales_manager: [],
                          // 用户列表
      list_user_account_manager: [],
            																		      uploadKey: '',
      uploadOptions: {
        url: this.$fullImgUrl('~/api/reservation_record/upload')
      },
      currentFilterType: '', // 当前筛选类型
      popupTitle: '',
      // 选择器数据
      cascaderList: [],
      cascaderColumns: [[], [], []],
      cascaderIndex: [0, 0, 0],
      // 显示文本
      filter_text: {
  	  	  	  	  	  	  	  	  			    appointment_period: '',
	  			    number_of_appointments: '',
	  	  	  			    license_plate_number: '',
	  	  	  	  	  	        },
      fieldCallbacks: {
  	  	  	  	  	  	  	  	  	  	  	  	  			    license_plate_number: 'select_license_plate_number_field',
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
						if(!this.form.reservation_record_id){
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
                                                                                                                                                      if (!param.appointment_date){
        return "预约日期不能为空";
      }
                                                                      if (!param.delivery_time){
        return "交车时间不能为空";
      }
                                                                                                                                                                	  let field = {
				  service_code: param.service_code,
				  appointment_date: param.appointment_date,
				  appointment_period: param.appointment_period,
			  };
	  let res = await this.$get("~/api/reservation_record/get_list", field);
	  if (res && res.result.count > 0) {
		for(var i = 0;i < res.result.list.length;i ++){
		  if(res.result.list[i].reservation_record_id != param.reservation_record_id){
						  return "该服务在该日期时段下，已被预约！";
					  }else{
			return null;
		  }
		}
	  }else{
		return null;
	  }
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
        url: this.$fullUrl('~/api/reservation_record/upload?'),
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
      if (type == 'booking_code') this['booking_code'] = this.form['booking_code'] = "";
      if (type == 'service_code') this['service_code'] = this.form['service_code'] = "";
      if (type == 'service_name') this['service_name'] = this.form['service_name'] = "";
      if (type == 'service_price') this['service_price'] = this.form['service_price'] = "";
      if (type == 'owner_user') this['owner_user'] = this.form['owner_user'] = "";
      if (type == 'owners_name') this['owners_name'] = this.form['owners_name'] = "";
      if (type == 'contact_number') this['contact_number'] = this.form['contact_number'] = "";
      if (type == 'appointment_date') this['appointment_date'] = this.form['appointment_date'] = "";
      if (type == 'appointment_period') this['appointment_period'] = this.form['appointment_period'] = "";
      if (type == 'number_of_appointments') this['number_of_appointments'] = this.form['number_of_appointments'] = "";
      if (type == 'delivery_time') this['delivery_time'] = this.form['delivery_time'] = "";
      if (type == 'sales_consultant') this['sales_consultant'] = this.form['sales_consultant'] = "";
      if (type == 'license_plate_number') this['license_plate_number'] = this.form['license_plate_number'] = "";
      if (type == 'frame_number') this['frame_number'] = this.form['frame_number'] = "";
      if (type == 'car_type') this['car_type'] = this.form['car_type'] = "";
      if (type == 'appointment_remarks') this['appointment_remarks'] = this.form['appointment_remarks'] = "";
      if (type == 'sales_manager') this['sales_manager'] = this.form['sales_manager'] = "";
      if (type == 'account_manager') this['account_manager'] = this.form['account_manager'] = "";
    },
    submit_() {
        if (this['booking_code'] !== null) this.form['booking_code'] = this['booking_code']
          if (this['service_code'] !== null) this.form['service_code'] = this['service_code']
          if (this['service_name'] !== null) this.form['service_name'] = this['service_name']
          if (this['service_price'] !== null) this.form['service_price'] = this['service_price']
          if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
          if (this['owners_name'] !== null) this.form['owners_name'] = this['owners_name']
          if (this['contact_number'] !== null) this.form['contact_number'] = this['contact_number']
          if (this['appointment_date'] !== null) this.form['appointment_date'] = this['appointment_date']
          if (this['appointment_period'] !== null) this.form['appointment_period'] = this['appointment_period']
          if (this['number_of_appointments'] !== null) this.form['number_of_appointments'] = this['number_of_appointments']
          if (this['delivery_time'] !== null) this.form['delivery_time'] = this['delivery_time']
          if (this['sales_consultant'] !== null) this.form['sales_consultant'] = this['sales_consultant']
          if (this['license_plate_number'] !== null) this.form['license_plate_number'] = this['license_plate_number']
          if (this['frame_number'] !== null) this.form['frame_number'] = this['frame_number']
          if (this['car_type'] !== null) this.form['car_type'] = this['car_type']
          if (this['appointment_remarks'] !== null) this.form['appointment_remarks'] = this['appointment_remarks']
          if (this['sales_manager'] !== null) this.form['sales_manager'] = this['sales_manager']
          if (this['account_manager'] !== null) this.form['account_manager'] = this['account_manager']
        console.log(this.form)
      if(!this.form.reservation_record_id){
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
            '/reservation_record/upload',
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
          uploadFilePathApi('/reservation_record/upload',filePath,undefined,undefined,
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
          if (this['booking_code'] !== null) this.form['booking_code'] = this['booking_code']
              if (this['service_code'] !== null) this.form['service_code'] = this['service_code']
              if (this['service_name'] !== null) this.form['service_name'] = this['service_name']
              if (this['service_price'] !== null) this.form['service_price'] = this['service_price']
              if (this['owner_user'] !== null) this.form['owner_user'] = this['owner_user']
              if (this['owners_name'] !== null) this.form['owners_name'] = this['owners_name']
              if (this['contact_number'] !== null) this.form['contact_number'] = this['contact_number']
              if (this.form["appointment_date"] && JSON.stringify(this.form["appointment_date"]).indexOf("-")===-1) {
        this.form["appointment_date"] = this.$toTime(parseInt(this.form["appointment_date"]), "yyyy-MM-dd")
      }
              if (this['appointment_period'] !== null) this.form['appointment_period'] = this['appointment_period']
              if (this['number_of_appointments'] !== null) this.form['number_of_appointments'] = this['number_of_appointments']
              if (this.form["delivery_time"] && JSON.stringify(this.form["delivery_time"]).indexOf("-")===-1) {
        this.form["delivery_time"] = this.$toTime(parseInt(this.form["delivery_time"]), "yyyy-MM-dd")
      }
              if (this['sales_consultant'] !== null) this.form['sales_consultant'] = this['sales_consultant']
              if (this['license_plate_number'] !== null) this.form['license_plate_number'] = this['license_plate_number']
              if (this['frame_number'] !== null) this.form['frame_number'] = this['frame_number']
              if (this['car_type'] !== null) this.form['car_type'] = this['car_type']
              if (this['appointment_remarks'] !== null) this.form['appointment_remarks'] = this['appointment_remarks']
              if (this['sales_manager'] !== null) this.form['sales_manager'] = this['sales_manager']
              if (this['account_manager'] !== null) this.form['account_manager'] = this['account_manager']
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
     * 获取预约时段列表
     */
    async get_list_appointment_period() {
              let param = {}
                                                                                                                                                                                                                                                                                                                                                                                                                                            var json = await this.$get("~/api/period_class_nameification/get_list",param);
      if(json.result && json.result.list){
        if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
          json.result.list = json.result.list.filter(item => item.type == 1);
        }
              const hasFatherId = json.result.list.some(item => 'father_id' in item);
        if (hasFatherId) {
          this.list_appointment_period = this.buildTree(json.result.list, 0, 'period_class_nameification_id');
        }else{
          this.list_appointment_period = json.result.list.map(item => ({
            time_period: item.time_period,
            children: []
          }));
        }
        if(!this.form["reservation_record_id"]) {
                      this.form["appointment_period"] = this.list_appointment_period[0].time_period;
                    this.filter_text.appointment_period = this.list_appointment_period[0].time_period;
          this.callFieldCallback('appointment_period', this.list_appointment_period[0].time_period);
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
                    
        /**
     * 获取预约次数列表
     */
    async get_list_number_of_appointments() {
          this.list_number_of_appointments = ['1'].map((o) => ({ 
        number_of_appointments: o, 
        children: [] 
      }));
      if(!this.form["reservation_record_id"]) {
                  this.form["number_of_appointments"] = this.list_number_of_appointments[0].value;
              }
            },
                    
          
          
        /**
     * 获取车牌号码列表
     */
    async get_list_license_plate_number() {
              let param = {}
                                                                                                                      var user_group = this.user.user_group;
                          let sqlwhere = "(";
                                                if(user_group=="车主用户"){
                    sqlwhere+= "owner_user = " + this.user.user_id + " or ";
                  }
                                                                                                                                                                    if (sqlwhere.length>1){
                sqlwhere = sqlwhere.substr(0,sqlwhere.length-4);
                sqlwhere += ")";
                param["sqlwhere"] = sqlwhere;
              }
                                                                                                                                                                                                                                                                                                                                                      var json = await this.$get("~/api/vehicle_information/get_list",param);
      if(json.result && json.result.list){
        if (json.result.list.length > 0 && 'type' in json.result.list[0]) {
          json.result.list = json.result.list.filter(item => item.type == 1);
        }
              const hasFatherId = json.result.list.some(item => 'father_id' in item);
        if (hasFatherId) {
          this.list_license_plate_number = this.buildTree(json.result.list, 0, 'vehicle_information_id');
        }else{
          this.list_license_plate_number = json.result.list.map(item => ({
            license_plate_number: item.license_plate_number,
            children: []
          }));
        }
        if(!this.form["reservation_record_id"]) {
                      this.form["license_plate_number"] = this.list_license_plate_number[0].license_plate_number;
                    this.filter_text.license_plate_number = this.list_license_plate_number[0].license_plate_number;
          this.callFieldCallback('license_plate_number', this.list_license_plate_number[0].license_plate_number);
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
            select_license_plate_number_field(v){
      if (v) {
        this.$get('~/api/vehicle_information/get_obj?license_plate_number=' + v, {}, (res) => {
          if (res.result && res.result.obj) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this.form.frame_number = res.result.obj.frame_number;
                                                                      this.form.car_type = res.result.obj.car_type;
                                                                                                                                          } else {
            console.error(res.error);
          }
        })
      }
    },
                
          
          
          
          /**
     * 获取销售经理用户列表
     */
    async get_list_user_sales_manager() {
      var json = await this.$get("~/api/user/get_list?user_group=销售经理");
      if(json.result && json.result.list){
        json.result.list.map((o) => this.list_user_sales_manager.push({value:o.user_id,text:o.nickname + '-' + o.username}));
      }
      else if(json.error){
        console.error(json.error);
      }
    },
          
          /**
     * 获取经理用户用户列表
     */
    async get_list_user_account_manager() {
      var json = await this.$get("~/api/user/get_list?user_group=经理用户");
      if(json.result && json.result.list){
        json.result.list.map((o) => this.list_user_account_manager.push({value:o.user_id,text:o.nickname + '-' + o.username}));
      }
      else if(json.error){
        console.error(json.error);
      }
    },
          
      // 显示筛选弹窗
    showFilter(type) {
      this.currentFilterType = type;
      switch (type) {
	        	        	        	        	        	        	        	        	                case 'appointment_period':
          this.popupTitle = '选择预约时段';
          this.initCascader(this.list_appointment_period);
          break;
        	                case 'number_of_appointments':
          this.popupTitle = '选择预约次数';
          this.initCascader(this.list_number_of_appointments);
          break;
        	        	        	                case 'license_plate_number':
          this.popupTitle = '选择车牌号码';
          this.initCascader(this.list_license_plate_number);
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
																																																																																																																																																              selectedText = selected.map(item => item.time_period || item.number_of_appointments || item.license_plate_number).join(' / ');
                fieldValue = lastItem.time_period || lastItem.number_of_appointments || lastItem.license_plate_number;
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
                                                                                                                                                                                      if (this.form["appointment_date"] && JSON.stringify(this.form["appointment_date"]).indexOf("-")===-1) {
        this.form["appointment_date"] = this.$toTime(parseInt(this.form["appointment_date"]),"yyyy-MM-dd")
      }
                                              if (json.result.obj.appointment_period) {
        this.filter_text.appointment_period = json.result.obj.appointment_period;
      }
                                  if (json.result.obj.number_of_appointments) {
        this.filter_text.number_of_appointments = json.result.obj.number_of_appointments;
      }
                      if (this.form["delivery_time"] && JSON.stringify(this.form["delivery_time"]).indexOf("-")===-1) {
        this.form["delivery_time"] = this.$toTime(parseInt(this.form["delivery_time"]),"yyyy-MM-dd")
      }
                                                                      if (json.result.obj.license_plate_number) {
        this.filter_text.license_plate_number = json.result.obj.license_plate_number;
      }
                                                                                                                                    },

    is_view() {
      var bl = this.user_group == '管理员';

      if (!bl) {
        bl = this.$check_action('/reservation_record/table', 'add');
        console.log(bl ? '你有表格添加权限视作有添加权限' : '你没有表格添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/reservation_record/table', 'set');
        console.log(bl ? '你有表格添加权限视作有修改权限' : '你没有表格修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/reservation_record/view', 'add');
        console.log(bl ? '你有视图添加权限视作有添加权限' : '你没有视图添加权限');
      }
      if (!bl) {
        bl = this.$check_action('/reservation_record/view', 'set');
        console.log(bl ? '你有视图修改权限视作有修改权限' : '你没有视图修改权限');
      }
      if (!bl) {
        bl = this.$check_action('/reservation_record/view', 'get');
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
                          this.get_list_appointment_period();
          this.get_list_number_of_appointments();
                  this.get_list_license_plate_number();
                        this.get_list_user_sales_manager();
                this.get_list_user_account_manager();
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
