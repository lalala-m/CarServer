<template>
  <view class="page_diy_edit page_purchase_order_edit" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>购买订单</tn-nav-bar>
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
              <view v-if="$check_field('set', 'purchase_order_number') || $check_field('add', 'purchase_order_number') ||  $check_field('get', 'purchase_order_number')" class="custom-from-item">
          <view class="label">
            <text>
              购买单号
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['purchase_order_number'] && $check_field('set','purchase_order_number')) || (!form['purchase_order_number'] && $check_field('add','purchase_order_number'))">
				<input type="text" id="form_purchase_order_number" v-model="form['purchase_order_number']" placeholder="请输入购买单号" :disabled="true" />
							</view>
            <text v-else-if="$check_field('get','purchase_order_number')">{{ form['purchase_order_number'] }}</text>
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
                    <view v-if="$check_field('set', 'car_brand') || $check_field('add', 'car_brand') ||  $check_field('get', 'car_brand')" class="custom-from-item">
          <view class="label">
            <text>
              汽车品牌
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['car_brand'] && $check_field('set','car_brand')) || (!form['car_brand'] && $check_field('add','car_brand'))">
				<input type="text" id="form_car_brand" v-model="form['car_brand']" placeholder="请输入汽车品牌" :disabled="disabledObj['car_brand_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','car_brand')">{{ form['car_brand'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'new_and_old_car') || $check_field('add', 'new_and_old_car') ||  $check_field('get', 'new_and_old_car')" class="custom-from-item">
          <view class="label">
            <text>
              汽车新旧
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['new_and_old_car'] && $check_field('set','new_and_old_car')) || (!form['new_and_old_car'] && $check_field('add','new_and_old_car'))">
				<input type="text" id="form_new_and_old_car" v-model="form['new_and_old_car']" placeholder="请输入汽车新旧" :disabled="disabledObj['new_and_old_car_isDisabled']" />
							</view>
            <text v-else-if="$check_field('get','new_and_old_car')">{{ form['new_and_old_car'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'car_price') || $check_field('add', 'car_price') ||  $check_field('get', 'car_price')" class="custom-from-item">
          <view class="label">
            <text>
              汽车价格
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['car_price'] && $check_field('set','car_price')) || (!form['car_price'] && $check_field('add','car_price'))">
				<input type="text" id="form_car_price" v-model="form['car_price']" placeholder="请输入汽车价格" :disabled="disabledObj['car_price_isDisabled']" />
				<text class="units">元</text>			</view>
            <text v-else-if="$check_field('get','car_price')">{{ form['car_price'] }}</text>
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
                <view v-if="$check_field('set', 'purchase_time') || $check_field('add', 'purchase_time') ||  $check_field('get', 'purchase_time')" class="custom-from-item">
          <view class="label">
            <text>
              购买时间
            </text>
          </view>
              <!-- 日长 -->
          <view class="content">
            <uni-datetime-picker :disabled="disabledObj['purchase_time_isDisabled']" type="datetime" id="form_purchase_time"
                                 v-model="form['purchase_time']" placeholder="请输入保质时间" v-if="
                    (form['purchase_time'] && $check_field('set', 'purchase_time')) ||
                    (!form['purchase_time'] && $check_field('add', 'purchase_time'))
                    " />
            <text v-else-if="$check_field('get', 'purchase_time')">{{ form['purchase_time'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'number_of_purchases') || $check_field('add', 'number_of_purchases') ||  $check_field('get', 'number_of_purchases')" class="custom-from-item">
          <view class="label">
            <text>
              购买次数
            </text>
          </view>
              <!-- 选项 -->
          <view class="content">
            <view class="filter-item" @click="showFilter('number_of_purchases')" v-if="(form['number_of_purchases'] && $check_field('set','number_of_purchases')) || (!form['number_of_purchases'] && $check_field('add','number_of_purchases'))">
              {{ filter_text.number_of_purchases || '请选择购买次数' }}
            </view>
            <text v-else-if="$check_field('get','number_of_purchases')">{{ form['number_of_purchases'] }}</text>
          </view>
            </view>
                    <view v-if="$check_field('set', 'purchase_quantity') || $check_field('add', 'purchase_quantity') ||  $check_field('get', 'purchase_quantity')" class="custom-from-item">
          <view class="label">
            <text>
              购买数量
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['purchase_quantity'] && $check_field('set','purchase_quantity')) || (!form['purchase_quantity'] && $check_field('add','purchase_quantity'))">
				<input type="text" id="form_purchase_quantity" v-model="form['purchase_quantity']" placeholder="请输入购买数量" :disabled="disabledObj['purchase_quantity_isDisabled']" />
				<text class="units">辆</text>			</view>
            <text v-else-if="$check_field('get','purchase_quantity')">{{ form['purchase_quantity'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'total_amount') || $check_field('add', 'total_amount') ||  $check_field('get', 'total_amount')" class="custom-from-item">
          <view class="label">
            <text>
              合计金额
            </text>
          </view>
              <!-- 文本 -->
                  <view class="content">
			<view class="input-block" v-if="(form['total_amount'] && $check_field('set','total_amount')) || (!form['total_amount'] && $check_field('add','total_amount'))">
				<input type="text" id="form_total_amount" v-model="form['total_amount']" placeholder="请输入合计金额" @focus="set_total_amount()" :disabled="disabledObj['total_amount_isDisabled']" />
				<text class="units">元</text>            </view>
			<text v-else-if="$check_field('get','total_amount')">{{ form['total_amount'] }}</text>
          </view>
                    </view>
                    <view v-if="$check_field('set', 'purchase_notes') || $check_field('add', 'purchase_notes') ||  $check_field('get', 'purchase_notes')" class="custom-from-item">
          <view class="label">
            <text>
              购买备注
            </text>
          </view>
              <!-- 多文本 -->
          <view class="content">
            <textarea id="form_purchase_notes" v-model="form['purchase_notes']" v-if="(form['purchase_notes'] && $check_field('set','purchase_notes')) || (!form['purchase_notes'] && $check_field('add','purchase_notes'))" :disabled="disabledObj['purchase_notes_isDisabled']"/>
            <text v-else-if="$check_field('get','purchase_notes')">{{ form['purchase_notes'] }}</text>
          </view>
            </view>
              <view v-if="user_group === '管理员' || $check_examine()" class="custom-from-item">
          <view class="label">
            <text> 审核状态 </text>
          </view>
          <view class="content ">
            <uni-data-select v-model="form['examine_state']" :localdata="list_examine_state"></uni-data-select>
            <text style="margin-left: 10rpx;">
              {{ form['examine_state'] }}
            </text>
          </view>
        </view>
        <view v-if="user_group === '管理员' || $check_examine()" class="custom-from-item">
          <view class="label">
            <text>审核回复 </text>
          </view>
          <view class="content">
            <textarea v-model="form['examine_reply']"> </textarea>
          </view>
        </view>
        <view class="form-footer">
                  <view v-if="$check_option('/purchase_order/table','pay')" class="me-btn" @click="openModel">提交</view>
          <view v-else class="me-btn" @click="submitForm()">提交</view>
                </view>
      </view>
																																												      <view class="pay-model" v-if="!!model">
        <view class="mask" @click="closemodel"></view>
        <view class="pay-model-inn">
          <view class="pay_tab">
            <view
              @click="choosepayway(idx)"
              v-for="(obj, idx) in payway"
              :key="idx"
              :class="['pay_tab_one', paywayact == idx ? 'pay_tab_one_act' : '']"
              >{{ obj.name }}</view
            >
          </view>
          <view v-if="payway[paywayact].id !== 3" class="inclued_pay_image">
            <image mode="widthFix" class="pay_image" :src="payway[paywayact].url"></image>
          </view>
          <view v-else class="inclued_pay_image">
            <view>
              <view style="width: 80%; margin: 0 auto; margin-top: 30px" class="account">
                <view style="width: 80%; margin: 0 auto">
                  <view style="line-height: 40px">请输入网银账号：</view>
                </view>
                <view style="width: 80%; margin: 0 auto">
                  <input v-model="pay_obj.account" placeholder="请输入网银账号" />
                </view>
              </view>
              <view style="width: 80%; margin: 0 auto; margin-top: 20px" class="password">
                <view style="width: 80%; margin: 0 auto">
                  <view style="line-height: 40px">请输入支付密码，6位数字：</view>
                </view>
                <view style="width: 80%; margin: 0 auto">
                  <input
                    placeholder="请输入密码"
                    type="password"
                    v-model="pay_obj.password"
                    maxlength="6"
                  />
                </view>
              </view>
            </view>
          </view>
          <view>
            <button class="me-btn" type="primary" @click="submitForm('pay')">确认支付</button>
            <button class="me-btn" type="info" style="margin-top: 10px;" @click="submitForm('notPay')">暂不支付</button>
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                {{ item.number_of_purchases }}
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
      url_get_obj: '~/api/purchase_order/get_obj?',
      url_add: '~/api/purchase_order/add?',
      url_set: '~/api/purchase_order/set?',
	    today: new Date().toISOString().split('T')[0],
      extra_info: {},
      // 查询条件
      query: {
        purchase_order_id: 0,
          purchase_order_number: "",
            car_coding: "",
            car_name: "",
            car_models: "",
            car_brand: "",
            new_and_old_car: "",
            car_price: 0,
            business_user: 0,
            owner_user: 0,
            purchase_time: "",
            number_of_purchases: "",
            purchase_quantity: 0,
            total_amount: "",
            purchase_notes: "",
        },
      obj: {
        purchase_order_id: 0,
          purchase_order_number: this.$get_stamp(), // 购买单号
            car_coding:  '', // 汽车编码
            car_name:  '', // 汽车名称
            car_models:  '', // 汽车车型
            car_brand:  '', // 汽车品牌
            new_and_old_car:  '', // 汽车新旧
            car_price:  0 , // 汽车价格
            business_user: 0, // 商家用户
            owner_user: 0, // 车主用户
            purchase_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            number_of_purchases:  '', // 购买次数
            purchase_quantity:  0 , // 购买数量
            total_amount:  '', // 合计金额
            purchase_notes:  '', // 购买备注
          examine_state: "未审核",
        examine_reply: "",
      },
														      // 表单字段
      form: {
        purchase_order_id: 0,
          purchase_order_number: this.$get_stamp(), // 购买单号
            car_coding:  '', // 汽车编码
            car_name:  '', // 汽车名称
            car_models:  '', // 汽车车型
            car_brand:  '', // 汽车品牌
            new_and_old_car:  '', // 汽车新旧
            car_price:  0 , // 汽车价格
            business_user: 0, // 商家用户
            owner_user: 0, // 车主用户
            purchase_time: this.$toTime(new Date().getTime(), "yyyy-MM-dd hh:mm:ss"),
            number_of_purchases:  '', // 购买次数
            purchase_quantity:  0 , // 购买数量
            total_amount:  '', // 合计金额
            purchase_notes:  '', // 购买备注
          examine_state: "未审核",
        examine_reply: "",
      },
      disabledObj: {
          purchase_order_number_isDisabled: false,
            car_coding_isDisabled: false,
            car_name_isDisabled: false,
            car_models_isDisabled: false,
            car_brand_isDisabled: false,
            new_and_old_car_isDisabled: false,
                business_user_isDisabled: false,
            owner_user_isDisabled: false,
            purchase_time_isDisabled: false,
            number_of_purchases_isDisabled: false,
                total_amount_isDisabled: false,
            purchase_notes_isDisabled: false,
        },
														                                	        	        	        	        	        	        	        	          // 用户列表
      list_user_business_user: [],
        	          // 用户列表
      list_user_owner_user: [],
        	                      list_number_of_purchases: [],
    	        	        	        	          field: 'purchase_order_id',
      table_key: 'purchase_order',

      list_examine_state: [
        { value: "未审核", text: "未审核" },
        { value: "已通过", text: "已通过" },
        { value: "未通过", text: "未通过" },
      ],
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
        payActiveName: '微信',
        account: '',
        password: '',
        id: '',
      },
      pay_qrcode: '',
  														      uploadKey: '',
      uploadOptions: {
        url: this.$fullUrl('~/api/purchase_order/upload')
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
	  	  	  	        },
      fieldCallbacks: {
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
						if(!this.form.purchase_order_id){
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
                                                                                                                                                                                              if (!param.purchase_time){
        return "购买时间不能为空";
      }
                                                                                                          let purchase_notes_SensitiveWords = await this.filterSensitiveWords(param.purchase_notes)
      if(purchase_notes_SensitiveWords.length > 0){
        return '购买备注中包含敏感词: ' + purchase_notes_SensitiveWords.join(',')
      }
          return null;
    },
    openModel() {
        this.model = true
      // 返回顶部
      uni.pageScrollTo({
        scrollTop: 0,
        duration: 0
      });
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
      closemodel() {
      //关闭选择支付方式
        this.model = false;
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
                	                        /**
    * 获取购买次数列表
    */
    async get_list_number_of_purchases() {
              this.list_number_of_purchases = ['1'].map((o) => ({ 
          number_of_purchases: o, 
          children: [] 
        }));
        this.form.number_of_purchases = this.list_number_of_purchases[0].number_of_purchases;
        this.filter_text.number_of_purchases = this.list_number_of_purchases[0].number_of_purchases;
                },
                        	                    	                    	                                        set_total_amount(){
        this.form.total_amount = parseFloat(this.form.car_price) * parseFloat(this.form.purchase_quantity)
        let r = /^\+?[1-9][0-9]*$/; // 正整数
        if(!r.test(this.form.total_amount) ){
          this.form.total_amount = this.form.total_amount.toFixed(2);
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
            url: _self.$fullUrl('~/api/purchase_order/upload?'),
            filePath: tempFilePaths[0],
            name: 'file',
            formData: {
              purchase_order: 'test',
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
	        	        	        	        	        	        	        	        	        	        	                case 'number_of_purchases':
          this.popupTitle = '选择购买次数';
          this.initCascader(this.list_number_of_purchases);
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
																																																																																																												      			  selectedText = selected.map(item => item.number_of_purchases).join(' / ');
                fieldValue = lastItem.number_of_purchases;
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
                                              if (this.form["purchase_time"] && JSON.stringify(this.form["purchase_time"]).indexOf("-")===-1) {
        this.form["purchase_time"] = this.$toTime(parseInt(this.form["purchase_time"]), "yyyy-MM-dd hh:mm:ss")
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
              this.get_list_number_of_purchases();
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
