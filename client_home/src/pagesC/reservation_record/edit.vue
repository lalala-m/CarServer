<template>
  <view class="page_diy_edit page_reservation_record_edit" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>预约记录</tn-nav-bar>
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
              <view v-if="$check_field('set', 'booking_code') || $check_field('add', 'booking_code') ||  $check_field('get', 'booking_code')" class="custom-from-item">
          <view class="label">
            <text>
              预约编码
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['booking_code'] && $check_field('set','booking_code')) || (!form['booking_code'] && $check_field('add','booking_code'))">
				<input type="text" id="form_booking_code" v-model="form['booking_code']" placeholder="请输入预约编码" :disabled="true" />
							</view>
            <text v-else-if="$check_field('get','booking_code')">{{ form['booking_code'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'service_code') || $check_field('add', 'service_code') ||  $check_field('get', 'service_code')" class="custom-from-item">
          <view class="label">
            <text>
              服务编码
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['service_code'] && $check_field('set','service_code')) || (!form['service_code'] && $check_field('add','service_code'))">
				<input type="text" id="form_service_code" v-model="form['service_code']" placeholder="请输入服务编码" :disabled="disabledObj['service_code_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','service_code')">{{ form['service_code'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'service_name') || $check_field('add', 'service_name') ||  $check_field('get', 'service_name')" class="custom-from-item">
          <view class="label">
            <text>
              服务名称
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['service_name'] && $check_field('set','service_name')) || (!form['service_name'] && $check_field('add','service_name'))">
				<input type="text" id="form_service_name" v-model="form['service_name']" placeholder="请输入服务名称" :disabled="disabledObj['service_name_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','service_name')">{{ form['service_name'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'service_price') || $check_field('add', 'service_price') ||  $check_field('get', 'service_price')" class="custom-from-item">
          <view class="label">
            <text>
              服务价格
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['service_price'] && $check_field('set','service_price')) || (!form['service_price'] && $check_field('add','service_price'))">
				<input type="text" id="form_service_price" v-model="form['service_price']" placeholder="请输入服务价格" :disabled="disabledObj['service_price_isDisabled']" />
				<text class="units">元</text>			</view>
            <text v-else-if="$check_field('get','service_price')">{{ form['service_price'] }}</text>
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
                <view v-if="$check_field('set', 'owners_name') || $check_field('add', 'owners_name') ||  $check_field('get', 'owners_name')" class="custom-from-item">
          <view class="label">
            <text>
              车主姓名
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['owners_name'] && $check_field('set','owners_name')) || (!form['owners_name'] && $check_field('add','owners_name'))">
				<input type="text" id="form_owners_name" v-model="form['owners_name']" placeholder="请输入车主姓名" :disabled="disabledObj['owners_name_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','owners_name')">{{ form['owners_name'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'contact_number') || $check_field('add', 'contact_number') ||  $check_field('get', 'contact_number')" class="custom-from-item">
          <view class="label">
            <text>
              联系号码
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['contact_number'] && $check_field('set','contact_number')) || (!form['contact_number'] && $check_field('add','contact_number'))">
				<input type="text" id="form_contact_number" v-model="form['contact_number']" placeholder="请输入联系号码" :disabled="disabledObj['contact_number_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','contact_number')">{{ form['contact_number'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'appointment_date') || $check_field('add', 'appointment_date') ||  $check_field('get', 'appointment_date')" class="custom-from-item">
          <view class="label">
            <text>
              预约日期
            </text>
          </view>
              <!-- 日期 -->
          <view class="content">
            <uni-datetime-picker :disabled="disabledObj['appointment_date_isDisabled']" type="date"
                     id="form_appointment_date" v-model="form['appointment_date']" placeholder="请输入预约日期"
                     v-if="(form['appointment_date'] && $check_field('set', 'appointment_date')) || (!form['appointment_date'] && $check_field('add', 'appointment_date'))" />
            <text v-else-if="$check_field('get', 'appointment_date')">
              {{form['appointment_date']}}
            </text>
          </view>
	        </view>
                    <view v-if="$check_field('set', 'appointment_period') || $check_field('add', 'appointment_period') ||  $check_field('get', 'appointment_period')" class="custom-from-item">
          <view class="label">
            <text>
              预约时段
            </text>
          </view>
              <!-- 选项 -->
          <view class="content">
            <view class="filter-item" @click="showFilter('appointment_period')" v-if="(form['appointment_period'] && $check_field('set','appointment_period')) || (!form['appointment_period'] && $check_field('add','appointment_period'))">
              {{ filter_text.appointment_period || '请选择预约时段' }}
            </view>
            <text v-else-if="$check_field('get','appointment_period')">{{ form['appointment_period'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'number_of_appointments') || $check_field('add', 'number_of_appointments') ||  $check_field('get', 'number_of_appointments')" class="custom-from-item">
          <view class="label">
            <text>
              预约次数
            </text>
          </view>
              <!-- 选项 -->
          <view class="content">
            <view class="filter-item" @click="showFilter('number_of_appointments')" v-if="(form['number_of_appointments'] && $check_field('set','number_of_appointments')) || (!form['number_of_appointments'] && $check_field('add','number_of_appointments'))">
              {{ filter_text.number_of_appointments || '请选择预约次数' }}
            </view>
            <text v-else-if="$check_field('get','number_of_appointments')">{{ form['number_of_appointments'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'delivery_time') || $check_field('add', 'delivery_time') ||  $check_field('get', 'delivery_time')" class="custom-from-item">
          <view class="label">
            <text>
              交车时间
            </text>
          </view>
              <!-- 日期 -->
          <view class="content">
            <uni-datetime-picker :disabled="disabledObj['delivery_time_isDisabled']" type="date"
                     id="form_delivery_time" v-model="form['delivery_time']" placeholder="请输入交车时间"
                     v-if="(form['delivery_time'] && $check_field('set', 'delivery_time')) || (!form['delivery_time'] && $check_field('add', 'delivery_time'))" />
            <text v-else-if="$check_field('get', 'delivery_time')">
              {{form['delivery_time']}}
            </text>
          </view>
	        </view>
                    <view v-if="$check_field('set', 'sales_consultant') || $check_field('add', 'sales_consultant') ||  $check_field('get', 'sales_consultant')" class="custom-from-item">
          <view class="label">
            <text>
              销售顾问
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['sales_consultant'] && $check_field('set','sales_consultant')) || (!form['sales_consultant'] && $check_field('add','sales_consultant'))">
				<input type="text" id="form_sales_consultant" v-model="form['sales_consultant']" placeholder="请输入销售顾问" :disabled="disabledObj['sales_consultant_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','sales_consultant')">{{ form['sales_consultant'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'license_plate_number') || $check_field('add', 'license_plate_number') ||  $check_field('get', 'license_plate_number')" class="custom-from-item">
          <view class="label">
            <text>
              车牌号码
            </text>
          </view>
              <!-- 选项 -->
          <view class="content">
            <view class="filter-item" @click="showFilter('license_plate_number')" v-if="(form['license_plate_number'] && $check_field('set','license_plate_number')) || (!form['license_plate_number'] && $check_field('add','license_plate_number'))">
              {{ filter_text.license_plate_number || '请选择车牌号码' }}
            </view>
            <text v-else-if="$check_field('get','license_plate_number')">{{ form['license_plate_number'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'frame_number') || $check_field('add', 'frame_number') ||  $check_field('get', 'frame_number')" class="custom-from-item">
          <view class="label">
            <text>
              车架号
            </text>
          </view>
              <!-- 仅查看 -->
          <view class="content" v-if="$check_field('get', 'frame_number')">
            <text v-if="$check_field('get','frame_number')">
              {{ form['frame_number'] }}            </text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'car_type') || $check_field('add', 'car_type') ||  $check_field('get', 'car_type')" class="custom-from-item">
          <view class="label">
            <text>
              汽车类型
            </text>
          </view>
              <!-- 仅查看 -->
          <view class="content" v-if="$check_field('get', 'car_type')">
            <text v-if="$check_field('get','car_type')">
              {{ form['car_type'] }}            </text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'appointment_remarks') || $check_field('add', 'appointment_remarks') ||  $check_field('get', 'appointment_remarks')" class="custom-from-item">
          <view class="label">
            <text>
              预约备注
            </text>
          </view>
              <!-- 多文本 -->
          <view class="content">
            <textarea id="form_appointment_remarks" v-model="form['appointment_remarks']" v-if="(form['appointment_remarks'] && $check_field('set','appointment_remarks')) || (!form['appointment_remarks'] && $check_field('add','appointment_remarks'))" :disabled="disabledObj['appointment_remarks_isDisabled']"/>
            <text v-else-if="$check_field('get','appointment_remarks')">{{ form['appointment_remarks'] }}</text>
          </view>
            </view>
                <view v-if="$check_field('set', 'sales_manager') || $check_field('add', 'sales_manager') ||  $check_field('get', 'sales_manager')" class="custom-from-item">
          <view class="label">
            <text>销售经理</text>
          </view>
          <view class="content">
            <uni-data-select
                    id="form_sales_manager" v-model="form['sales_manager']"
                    :localdata="list_user_sales_manager"
                    :clear="!disabledObj['sales_manager_isDisabled']"
                    :disabled="disabledObj['sales_manager_isDisabled']"
                    v-if="(form['sales_manager'] && $check_field('set', 'sales_manager')) || (!form['sales_manager'] && $check_field('add', 'sales_manager'))" style="width: 100%;">
            </uni-data-select>
            <text v-else-if="$check_field('get', 'sales_manager')">
              {{form['sales_manager']}}
            </text>
          </view>
        </view>
            <view v-if="$check_field('set', 'account_manager') || $check_field('add', 'account_manager') ||  $check_field('get', 'account_manager')" class="custom-from-item">
          <view class="label">
            <text>客户经理</text>
          </view>
          <view class="content">
            <uni-data-select
                    id="form_account_manager" v-model="form['account_manager']"
                    :localdata="list_user_account_manager"
                    :clear="!disabledObj['account_manager_isDisabled']"
                    :disabled="disabledObj['account_manager_isDisabled']"
                    v-if="(form['account_manager'] && $check_field('set', 'account_manager')) || (!form['account_manager'] && $check_field('add', 'account_manager'))" style="width: 100%;">
            </uni-data-select>
            <text v-else-if="$check_field('get', 'account_manager')">
              {{form['account_manager']}}
            </text>
          </view>
        </view>
          <view class="form-footer">
                  <view v-if="$check_option('/reservation_record/table','pay')" class="me-btn" @click="openModel">提交</view>
          <view v-else class="me-btn" @click="submitForm()">提交</view>
                </view>
      </view>
																																																												      <view class="pay-model" v-if="payModalVisible">
        <view class="mask" @click="payModalVisible = false"></view>
        <view class="pay-model-inn">
          <view v-if="pay_step === 1">
            <view class="pay-info">
    		            		            		                <view class="pay-info-item">
                <view class="pay-info-title">支付内容</view>
                <view class="pay-info-content">{{ pay_data.service_name }}</view>
              </view>
                		                      <view class="pay-info-item">
                <view class="pay-info-title">订单金额</view>
                <view class="pay-info-content">¥{{ pay_data.service_price }}</view>
              </view>
          		            		            		            		            		            		            		            		            		            		            		            		            		            		                              <view class="pay-info-item pay-amount">
                <view class="pay-info-title">实付金额</view>
                <view class="pay-info-content pay-info-amount">¥{{ pay_data.amount_paid }}</view>
              </view>
            </view>
            <view class="pay-type">
              <view class="pay-type-title">支付方式</view>
              <view class="pay-type-list">
                <view v-for="(item, index) in pay_source" :key="index"
                  :class="['pay-type-item', pay_obj.payActiveName === item.name ? 'pay-type-item-active' : '']"
                  @click="pay_obj.payActiveName = item.name">
                  <view class="pay-type-content">
                    <image class="pay-type-img" :src="item.img" mode="aspectFit"></image>
                    <view class="pay-type-name">{{ item.name }}</view>
                  </view>
                    </view>
              </view>
            </view>
            <view class="pay-btn-wrap">
              <button class="me-btn" type="primary" @click="confirmPayStep">确认</button>
            </view>
          </view>
          <view v-if="pay_step === 2">
            <view class="pay-op">
              <image v-if="pay_obj.payActiveName === '微信支付'" :src="pay_source[0].pay_img" mode="widthFix" class="pay-image"></image>
              <image v-if="pay_obj.payActiveName === '支付宝支付'" :src="pay_source[1].pay_img" mode="widthFix" class="pay-image"></image>
              <view v-if="pay_obj.payActiveName === '网银支付'" class="pay-ebank">
                <view class="pay-ebank-item">
                  <view class="pay-ebank-title">请输入网银账号：</view>
                  <input class="pay-ebank-input" v-model="pay_obj.account" placeholder="请输入网银账号" />
                </view>
                <view class="pay-ebank-item">
                  <view class="pay-ebank-title">请输入支付密码，6位数字：</view>
                  <input class="pay-ebank-input" placeholder="请输入密码" v-model="pay_obj.password" type="password"
                    maxlength="6" />
                </view>
              </view>
            </view>
            <view class="pay-btn-wrap">
              <button class="me-btn me-btn-plain" @click="pay_step = 1">其他支付方式</button>
              <button class="me-btn" type="primary" @click="submit('pay')">确认支付</button>
            </view>
          </view>
        </view>
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
																		export default {
  mixins: [mixin],
    components: {
																		  },
  data() {
    return {
      url_get_obj: '~/api/reservation_record/get_obj?',
      url_add: '~/api/reservation_record/add?',
      url_set: '~/api/reservation_record/set?',
	    today: new Date().toISOString().split('T')[0],
      extra_info: {},
      // 查询条件
      query: {
        reservation_record_id: 0,
          booking_code: "",
            service_code: "",
            service_name: "",
            service_price: 0,
            owner_user: 0,
            owners_name: "",
            contact_number: "",
            appointment_date: "",
            appointment_period: "",
            number_of_appointments: "",
            delivery_time: "",
            sales_consultant: "",
            license_plate_number: "",
            frame_number: "",
            car_type: "",
            appointment_remarks: "",
            sales_manager: 0,
            account_manager: 0,
        },
      obj: {
        reservation_record_id: 0,
          booking_code: this.$get_stamp(), // 预约编码
            service_code:  '', // 服务编码
            service_name:  '', // 服务名称
            service_price:  0 , // 服务价格
            owner_user: 0, // 车主用户
            owners_name:  '', // 车主姓名
            contact_number:  '', // 联系号码
            appointment_date: this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            appointment_period:  '', // 预约时段
            number_of_appointments:  '', // 预约次数
            delivery_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            sales_consultant:  '', // 销售顾问
            license_plate_number:  '', // 车牌号码
            frame_number:  '', // 车架号
            car_type:  '', // 汽车类型
            appointment_remarks:  '', // 预约备注
            sales_manager: 0, // 销售经理
            account_manager: 0, // 客户经理
        },
																		      // 表单字段
      form: {
        reservation_record_id: 0,
          booking_code: this.$get_stamp(), // 预约编码
            service_code:  '', // 服务编码
            service_name:  '', // 服务名称
            service_price:  0 , // 服务价格
            owner_user: 0, // 车主用户
            owners_name:  '', // 车主姓名
            contact_number:  '', // 联系号码
            appointment_date: this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            appointment_period:  '', // 预约时段
            number_of_appointments:  '', // 预约次数
            delivery_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd"),
            sales_consultant:  '', // 销售顾问
            license_plate_number:  '', // 车牌号码
            frame_number:  '', // 车架号
            car_type:  '', // 汽车类型
            appointment_remarks:  '', // 预约备注
            sales_manager: 0, // 销售经理
            account_manager: 0, // 客户经理
        },
      disabledObj: {
          booking_code_isDisabled: false,
            service_code_isDisabled: false,
            service_name_isDisabled: false,
                owner_user_isDisabled: false,
            owners_name_isDisabled: false,
            contact_number_isDisabled: false,
            appointment_date_isDisabled: false,
            appointment_period_isDisabled: false,
            number_of_appointments_isDisabled: false,
            delivery_time_isDisabled: false,
            sales_consultant_isDisabled: false,
            license_plate_number_isDisabled: false,
            frame_number_isDisabled: false,
            car_type_isDisabled: false,
            appointment_remarks_isDisabled: false,
            sales_manager_isDisabled: false,
            account_manager_isDisabled: false,
        },
																		                                        	        	        	        	        	          // 用户列表
      list_user_owner_user: [],
        	        	        	                      list_appointment_period: [],
    	                      list_number_of_appointments: [],
    	        	        	                      list_license_plate_number: [],
    	        	        	        	        	          // 用户列表
      list_user_sales_manager: [],
        	          // 用户列表
      list_user_account_manager: [],
          field: 'reservation_record_id',
      table_key: 'reservation_record',

      model: false,
      paywayact: 0,
      // 支付方式
      payway: [
        { name: '微信', url: '/static/img/wx.png', id: 1 },
        { name: '支付宝', url: '/static/img/aplipay.png', id: 2 },
        {
          name: '其他',
          url: 'https://dzgj.yuyuebus.com/static/front/dist/static/img/icon-question.png',
          id: 3,
        },
      ],
      pay_obj: {
        payActiveName: '微信支付',
        account: '',
        password: '',
        id: '',
      },
      pay_qrcode: '',
        payModalVisible: false,
      pay_step: 1,
      pay_data: {},
      pay_source: [
        {
          name: "微信支付",
          img: "/static/icon/wechat.svg",
          pay_img: "/static/img/wx.png"
        },
        {
          name: "支付宝支付",
          img: "/static/icon/alipay.svg",
          pay_img: "/static/img/aplipay.png"
        },
        {
          name: "网银支付",
          img: "/static/icon/ebank.svg"
        },
          ],
  																		      uploadKey: '',
      uploadOptions: {
        url: this.$fullUrl('~/api/reservation_record/upload')
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
						if(!this.form.reservation_record_id){
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
                                                                                                                                                      if (!param.appointment_date){
        return "预约日期不能为空";
      }
                                                                      if (!param.delivery_time){
        return "交车时间不能为空";
      }
                                                                                                                              let appointment_remarks_SensitiveWords = await this.filterSensitiveWords(param.appointment_remarks)
      if(appointment_remarks_SensitiveWords.length > 0){
        return '预约备注中包含敏感词: ' + appointment_remarks_SensitiveWords.join(',')
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
    openModel() {
        this.pay_obj.id = this.form.reservation_record_id;
      this.pay_data = JSON.parse(JSON.stringify(this.form));
                                                                this.pay_data.amount_paid = this.form.service_price;
                                                                                                                                                                                                                            this.payModalVisible = true;
      this.pay_step = 1;
      },
    choosepayway(idx) {
      switch (idx) {
        case 0:
          this.pay_obj.payActiveName = '微信';
          break;
        case 1:
          this.pay_obj.payActiveName = '支付宝';
          break;
        case 2:
          this.pay_obj.payActiveName = '网银';
          break;
      }
      this.paywayact = idx;
    },
      confirmPayStep() {
      if (['微信支付', '支付宝支付', '网银支付'].includes(this.pay_obj.payActiveName)) {
        this.pay_step = 2;
      }
        },
      closemodel() {
      //关闭选择支付方式
        this.payModalVisible = false;
      this.pay_step = 1;
      this.pay_obj.account = "";
      this.pay_obj.password = "";
      },

    async submit(payTyep) {
      if (this.extra_info && Object.keys(this.extra_info).length > 0) {
        this.form.extra = JSON.stringify(this.extra_info);
      }
      let param = undefined;
      let func = undefined;
      if (!param) {
        param = JSON.parse(JSON.stringify(this.form));
      }
      param.create_by = this.userInfo.user_id;
              let name = this.pay_obj.payActiveName;
        let payType = name.endsWith("支付") ? name.slice(0, -2) : name;
        if (payTyep == "pay") {
          param.pay_state = '已支付';
          param.pay_type = payType;
                                                                                                                                                                                                                                                                                                                                                              delete param.amount_paid;
                }
            var pm = this.events("submit_before", Object.assign({}, param)) || param;
      var msg = await this.events("submit_check", pm);
      var ret;
      if (msg) {
        this.$toast(msg, "danger");
      } else {
        if (pm["product_album"]) {
          pm["product_album"] = JSON.stringify(pm["product_album"]);
        }
        if (pm["customization_requirements"]) {
          pm["customization_requirements"] = JSON.stringify(
            pm["customization_requirements"]
          );
        }
                                                                                                                                                                                                ret = this.events("submit_main", pm, func);
      }
      return ret;
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
          this.form.appointment_period = this.list_appointment_period[0].time_period;
          this.filter_text.appointment_period = this.list_appointment_period[0].time_period;
          this.callFieldCallback('appointment_period', this.list_appointment_period[0].time_period);
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
        this.form.number_of_appointments = this.list_number_of_appointments[0].number_of_appointments;
        this.filter_text.number_of_appointments = this.list_number_of_appointments[0].number_of_appointments;
                },
                        	                    	                    	                        /**
    * 获取车牌号码列表
    */
    async get_list_license_plate_number() {
                    let param = {}
                                                                                                                      var user_group = this.userInfo.user_group;
                          let sqlwhere = "(";
                                                if(user_group=="车主用户"){
                    sqlwhere+= "owner_user = " + this.userInfo.user_id + " or ";
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
          this.form.license_plate_number = this.list_license_plate_number[0].license_plate_number;
          this.filter_text.license_plate_number = this.list_license_plate_number[0].license_plate_number;
          this.callFieldCallback('license_plate_number', this.list_license_plate_number[0].license_plate_number);
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
            url: _self.$fullUrl('~/api/reservation_record/upload?'),
            filePath: tempFilePaths[0],
            name: 'file',
            formData: {
              reservation_record: 'test',
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
                                      if (this.form["appointment_date"] && JSON.stringify(this.form["appointment_date"]).indexOf("-")===-1) {
        this.form["appointment_date"] = this.$toTime(parseInt(this.form["appointment_date"]), "yyyy-MM-dd")
      }
                      if (this.form["delivery_time"] && JSON.stringify(this.form["delivery_time"]).indexOf("-")===-1) {
        this.form["delivery_time"] = this.$toTime(parseInt(this.form["delivery_time"]), "yyyy-MM-dd")
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
                                this.get_user_session_owner_user();
        this.get_list_user_owner_user();
                          this.get_list_appointment_period();
            this.get_list_number_of_appointments();
                        this.get_list_license_plate_number();
                                  this.get_list_user_sales_manager();
            this.get_list_user_account_manager();
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
.filter-item {
  border: 1px solid #e5e5e5;
  color: #6a6a6a;
  text-align: center;
  width: 100%;
  height: 36px;
  line-height: 36px;
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
.pay-model {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1000;
  
  .mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }
  
  .pay-model-inn {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background: #fff;
    border-radius: 20rpx 20rpx 0 0;
    padding: 40rpx 30rpx;
    box-sizing: border-box;
    max-height: 80vh;
    overflow-y: auto;
  }
}
.pay-info {
  .pay-info-item {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 30rpx;
    font-size: 28rpx;
    
    .pay-info-title {
      color: #666;
      width: 80px;
    }
    
    .pay-info-content {
      color: #999;
      text-align: right;
    }
  }
  
  .pay-amount {
    margin-top: 40rpx;
    padding-top: 30rpx;
    border-top: 1rpx solid #eee;
    border-bottom: 1rpx solid #eee;
    padding-bottom: 30rpx;
    
    .pay-info-amount {
      font-weight: bold;
      font-size: 36rpx;
      color: #f5222d;
    }
  }
}
.pay-type {
  margin-top: 40rpx;
  
  .pay-type-title {
    color: #999;
    margin-bottom: 20rpx;
    font-size: 28rpx;
  }
  
  .pay-type-list {
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    gap: 20rpx;
  }
  
  .pay-type-item {
    background: #f8f8f8;
    border: 2rpx solid #f8f8f8;
    border-radius: 10rpx;
    padding: 30rpx 20rpx;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    .pay-type-content{
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .pay-type-img {
      width: 40rpx;
      height: 40rpx;
      margin-right: 20px;
    }
    
    .pay-type-name {
      font-size: 28rpx;
    }
    
    .pay-type-remark {
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .pay-type-item-active {
    border-color: #2c70f6;
    background: #e8f0ff;
  }
}
.pay-btn-wrap {
  margin-top: 40rpx;
  display: flex;
  gap: 20rpx;
  
  .me-btn {
    flex: 1;
    height: 80rpx;
    line-height: 80rpx;
    border-radius: 10rpx;
    
    &.me-btn-plain {
      background: #fff;
      border: 1rpx solid #ddd;
      color: #333;
    }
  }
}
.pay-ebank {
  .pay-ebank-item {
    margin-top: 40rpx;
    
    .pay-ebank-title {
      margin-bottom: 20rpx;
      color: #666;
    }
    
    .pay-ebank-input {
      border: 1rpx solid #ddd;
      border-radius: 10rpx;
      padding: 20rpx;
      font-size: 28rpx;
      height: 50px;
    }
  }
}

.pay-image {
  width: 96%;
  margin: 0 auto;
  display: block;
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
