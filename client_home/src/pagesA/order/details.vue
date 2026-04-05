<template>
  <view id="page_order_details" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar back-url="/pagesA/order/list">订单详情</tn-nav-bar>
    <view class="page_order_details" id="order_details">
      <!-- 订单细节模块(开始) -->

      <view v-if="$check_action('/order/details', 'get')">
        <view class="order-state">
          {{ showState }}
        </view>
          <view class="contact-info" >
          <view class="info">
            <uni-icons class="icon-address" size="16" type="location-filled"> </uni-icons>
            <text class="name">{{ contact_name }}</text>
            <text class="phone"> {{ contact_phone }}</text>
          </view>
          <text class="address-info">{{ contact_address }}</text>
          <view class="addr-btn" v-if="state === '待付款' || state === '待发货'" @click="addrDialogHandle">修改地址</view>
        </view>
  
        <view class="goods-list" v-for="(obj, idx) in list" :key="idx">
          <view
            @click="$navTo('/pagesA/order/details?order_number=' + o.order_number)"
            class="goods-item"
          >
            <view class="goods-img">
              <image
                :src="$fullImgUrl(obj.img) || '../../static/img/default.png'"
                mode="scaleToFill"
              >
              </image>
            </view>
            <view class="goods-info">
              <view class="title text-ellipsis-2">
                {{ obj.title }}
              </view>
              <view class="total-info">
			  				<view class="price" v-if="integral == undefined"> ￥{{ obj.price }} </view>
				<view class="price" v-else>{{ integral }}积分</view>
			                  <view class="num"> ×{{ obj.num }} </view>
              </view>
              <view v-if="obj.norms" class="norms"> 规格：{{ obj.norms }} </view>
            </view>
          </view>
        </view>

        <view class="cell total">
          <view class="label" v-if="state === '待付款' || state === '已取消'">需付</view>
          <view class="label" v-else>实付</view>
		  <view v-if="integral == undefined" class="value">￥{{ sum_price }}</view>
		  <view v-else class="value">{{ integral_total }}积分</view>
        </view>
        <view class="cell order_no">
          <view class="label">订单编号：</view><view class="value">{{ order_number }}</view>
        </view>
        <view class="cell create_time">
          <view class="label">下单时间：</view><view class="value">{{ create_time }}</view>
        </view>
        <view class="cell remark border-none">
          <view class="label">订单备注：</view>
          <view class="value">
            <view class="textarea-wrap" v-if="state === '待付款'">
              <tn-input
                v-model="remark"
                type="textarea"
                :border="true"
                :height="100"
                :autoHeight="true"
              />
            </view>
            <text v-else>{{ remark }}</text>
          </view>
        </view>
		<view class="pay-wrap" v-if="$check_action('/order/details', 'set')">
		  <view class="me-btn btn-pay" v-if="state === '待付款'" @click="goto_pay()">
			{{
			  integral != undefined ? '兑换' : '去支付'
			}}
		  </view>
		</view>
      </view>
    </view>
	<view class="goods_recommend">
		<view class="title">商品推荐</view>
		<view class="goods_item" @click="toGoodsDetail(recommend_goods)">
			<view class="goods-img">
				<image :src="$fullImgUrl(recommend_goods.img) || '../../static/img/default.png'" mode="scaleToFill">
				</image>
			</view>
			<view class="goods-info">
				<view class="title text-ellipsis-2">
					{{ recommend_goods.title }}
				</view>
				<view class="desc text-ellipsis-2">
					{{ recommend_goods.description }}
				</view>
				<view class="price-wrapper">
				      <view class="price"> ￥{{ recommend_goods.price | keepTwoNum }} </view>
				      <view class="price_ago"> ￥{{ recommend_goods.price_ago | keepTwoNum }} </view>
				</view>
				<view class="info">
					<view class="sales">销量<text>{{ recommend_goods.sales }}</text></view>
					<view class="inventory">库存<text>{{ recommend_goods.inventory }}</text></view>
				</view>
			</view>
		</view>
	</view>
    <tn-modal v-model="showAddrDialog" :custom="true">
      <view class="addr-dialog">
        <view class="addr-title">修改地址</view>
        <radio-group @change="radioChange">
          <view class="addr-container-content" v-for="item in addrList" :key="item.id">
            <radio :value="item.address_id" :checked="item.address_id === currentAddr">
              <view class="addr-container-item">
              <view class="addr-info">
                <view class="addr-name">{{ item.name }}</view>
                <view class="addr-phone">{{ item.phone }}</view>
              </view>
              <view class="addr-detail">{{ item.address }}</view>
            </view>
            </radio>
          </view>
        </radio-group>
        <view class="addr-container-btn">
          <view class="addr-link" @click="toAddr">管理我的地址</view>
          <view class="addr-btns">
            <tn-button backgroundColor="#0079fe" fontColor="#0079fe" plain border
              @click="showAddrDialog = false">取消</tn-button>
            <tn-button backgroundColor="#0079fe" fontColor="#fff" @click="addrChangeHandle">确认</tn-button>
          </view>
        </view>
      </view>
    </tn-modal>
    <!-- 订单细节模块(结束) -->
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
        <view v-if="payway[paywayact].id == 3" class="inclued_pay_image">
          <view style="margin-bottom: 20rpx">
            <view style="width: 100%; margin: 0 auto; margin-top: 30px" class="account">
              <view style="width: 80%; margin: 0 auto">
                <view style="line-height: 40px">请输入网银账号：</view>
              </view>
              <view style="width: 80%; margin: 0 auto">
                <uni-easyinput v-model="pay_obj.account" placeholder="请输入网银账号" />
              </view>
            </view>
            <view style="width: 100%; margin: 0 auto; margin-top: 20px" class="password">
              <view style="width: 80%; margin: 0 auto">
                <view style="line-height: 40px">请输入支付密码，6位数字：</view>
              </view>
              <view style="width: 80%; margin: 0 auto">
                <uni-easyinput
                  placeholder="请输入密码"
                  type="password"
                  v-model="pay_obj.password"
                  maxlength="6"
                />
              </view>
            </view>
          </view>
        </view>
	        <view v-else class="inclued_pay_image">
            <image mode="widthFix" class="pay_image" :src="payway[paywayact].url"></image
          ></view>
          <view class="tn-flex tn-flex-col-center tn-flex-row-center">
            <button class="me-btn assure-btn" type="primary" @click="submit_pay">
              确认支付
            </button></view
          >
        </view>
      </view>
    </view>

  </view>
</template>

<script>
import { getOrderListApi } from '@/api/order';
export default {
  filters: {
    //过滤器 保留两位
    keepTwoNum: function (value) {
      value = Number(value);
      return value.toFixed(2);
    },
  },
  data() {
    return {
      query: {
        order_number: '',
      },
      contact_name: '',
      contact_phone: '',
      contact_address: '',
      showAddrDialog: false,
      addrList: null,
      currentAddr: null,
      order_number: '',
      create_time: '',
      state: '',
      remark: '',
      paywayact: 0,
      model: false,
      pay_obj: {
        payActiveName: '微信',
        account: '',
        password: '',
        id: '',
      },
      showState: '',
      list: [],
	  // 支付方式
	  payway: [
	    { name: "微信", url: "/static/img/wx.png", id: 1 },
	    { name: "支付宝", url: "/static/img/aplipay.png", id: 2 },
	    { name: "其他", url: "", id: 3 },
	  ],
	  integral: undefined,
	  integralHave: 0,
    issuer_id: "",
	  recommend_goods: [],
    };
  },
  onLoad(options) {
    this.query.order_number = options.order_number;
    this.issuer_id = options.issuer_id
    this.getOrderList();
	if (options.integral != undefined) {
	  this.integral = Number(options.integral);
	  this.integralHave = Number(this.$store.state.app.userInfo.integral);
	}
  },
  methods: {
      toGoodsDetail(goods) {
        this.$router.push({
          path: '/pagesA/goods/details',
          query: {
            goods_id: goods.goods_id,
          }
        })
      },
    /**
     * 获取订单列表数据
     */
    getOrderList() {
      getOrderListApi(this.query)
        .then((res) => {
          this.list = res.result.list;
          let {
            contact_name,
            contact_phone,
            contact_address,
            order_number,
            create_time,
            state,
            remark,
            delivery_type,
            pickup_code,
          } = this.list[0];
          this.contact_name = contact_name;
          this.contact_phone = contact_phone;
          this.contact_address = contact_address;
          this.order_number = order_number;
          if (create_time && JSON.stringify(create_time).indexOf('-') === -1) {
            this.create_time = this.$toTime(create_time, 'yyyy-MM-dd hh:mm:ss');
          } else {
            this.create_time = create_time;
          }
          this.state = state;
          this.showState = state;
          this.remark = remark;
          this.get_list_after();
        })
        .catch(() => {});
    },
    choosepayway(idx) {
      this.paywayact = idx;
    },
	// 获取推荐商品
	async get_recommend_goods(){
		// 获取订单第一个商品的分类
		let type = this.list[0].type;
		await this.$get('~/api/goods/get_list', {
			orderby: 'hits desc'
		}, (json) => {
			let types_goods = json.result.list;
			for(var i = 0;i < types_goods.length;i ++){
				if(types_goods[i].goods_id != this.list[0].goods_id && types_goods[i].type == this.list[0].type){
					this.recommend_goods = types_goods[i];
					break;
				}
			}
			// 如果同类下没有可推荐商品，推荐热度最高的
			if(this.recommend_goods.length == 0){
				for(var i = 0;i < types_goods.length;i ++){
					if(types_goods[i].goods_id != this.list[0].goods_id){
						this.recommend_goods = types_goods[i];
						break;
					}
				}
			}
		})
	},
    async get_list_after() {
	  this.get_recommend_goods();
    },
    closemodel() {
      //关闭选择支付方式
      this.model = false;
    },
    /**
     * 打开支付
     */
    goto_pay() {
	  //打开选择支付方式
	  let _this = this;
	  if (_this.integral != undefined) {
	    //积分支付
	    if (_this.integralHave < _this.integral_total) {
	      //钱包支付
	      _this.$toast('积分不足，兑换失败');
	      return false;
	    }
	    _this.$post(
	      '~/api/order/set?order_number=' + _this.order_number,
	      {
	            state: '待发货',
    	        delivery_state: '未配送',
    	        remark: _this.remark,
	        buy_type: 2,
	      },
	      (res) => {
	        if (res.result) {
	          console.log(res.result);
	          _this.$get('~/api/order/get_list', { order_number: _this.order_number }, (json) => {
	            if (json.result && json.result.list) {
	              //修改数量
	              for (let i = 0; i < json.result.list.length; i++) {
	                _this.update_order_price_count(json.result.list[i]);
	                _this.update_goods_inventory(json.result.list[i]);
	              }
	            }
	          });
	          _this.$toast('兑换成功');
	          let paramsUserSet = {
	            integral: _this.integralHave - _this.integral_total,
	          };

	          _this.$post(
	            '~/api/user/set?user_id=' + _this.$store.state.app.userInfo.user_id,
	            paramsUserSet,
	            (res) => {
	              _this.$router.push('/pagesA/order/list');
	            }
	          );
	        }
	      }
	    );
	  } else {
	    this.model = true;
			  }
    },
    async update_goods_inventory(o) {
      await this.$get('~/api/goods/get_obj', { goods_id: o.goods_id }, (json) => {
        if (json.result && json.result.obj) {
          if (o.norms) {
            try {
              let list_fields = JSON.parse(json.result.obj.customize_field);
              let updatedFieldValue = null; // 用于保存更新后的 field_value（数组）

              list_fields = list_fields.map(item => {
                if (item.type === 'sku') {
                  try {
                    const fieldValue = typeof item.field_value === 'string' ? JSON.parse(item.field_value) : item.field_value;

                    if (Array.isArray(fieldValue)) {
                      const newFieldValue = fieldValue.map(subItem => {
                        if (subItem.sku_title === o.norms) {
                          if(subItem.sku_inventory > 0){
                            const newInventory = Number(subItem.sku_inventory) - Number(o.num);
                            return {
                              ...subItem,
                              sku_inventory: newInventory > 0 ? newInventory : 0
                            };
                          }
                        }
                        return subItem;
                      });
                      updatedFieldValue = newFieldValue;
                      return {
                        ...item,
                        field_value: JSON.stringify(newFieldValue)
                      };
                    }
                  } catch (e) {
                    console.error('解析 field_value 失败:', e);
                  }
                }
                return item;
              });
              if (!updatedFieldValue) {
                console.warn('未找到匹配的 SKU 规格:', o.norms);
                return;
              }
              this.$post('~/api/goods/set?goods_id=' + o.goods_id, {
                customize_field: JSON.stringify(list_fields)
              }).then(res => {
                if (res.result) {
                  if (json.result.obj.source_table && json.result.obj.source_field && json.result.obj.source_id) {
                    this.$post(`~/api/${json.result.obj.source_table}/set?${json.result.obj.source_field}=${json.result.obj.source_id}`, {
                      sku: JSON.stringify(updatedFieldValue)
                    }).then(res_sub => {
                      console.log('源表更新成功:', res_sub);
                    }).catch(err => {
                      console.error('源表更新失败:', err);
                    });
                  }
                  const minInventoryItems = updatedFieldValue.filter(subItem =>
                    subItem.sku_title === o.norms && Number(subItem.sku_inventory) <= 5
                  );

                  if (minInventoryItems.length > 0) {
                    let message_inform = {
                      title: `商品库存不足`,
                      type: "通知",
                      content: `商品ID ${json.result.obj.goods_id} 的规格 "${o.norms}" 库存不足5件，请尽快补货`,
                      state: 1,
                      user_id: json.result.obj.user_id,
                    };
                    this.$post("~/api/message_inform/add", message_inform);
                  }
                } else {
                  console.error('更新 goods 失败:', res.error);
                }
              }).catch(err => {
                console.error('请求失败:', err);
              });

            } catch (e) {
              console.error('解析 customize_field 失败:', e);
            }
          } else {
            if(json.result.obj.inventory > 0){ // 启用库存
                            let sum = Number(json.result.obj.inventory) - Number(o.num)
                          let inventory = { inventory: json.result.obj.inventory - o.num };
              let data = {
                inventory: json.result.obj.inventory - o.num,
                sales: json.result.obj.sales + o.num
              }
              this.$post('~/api/goods/set?goods_id=' + o.goods_id, data, (res) => {
                if (
                  res.result &&
                  json.result.obj.source_table &&
                  json.result.obj.source_field &&
                  json.result.obj.source_id
                ) {
                  let inventory_sub = { cart_inventory: inventory.inventory };
                  this.$post(
                    '~/api/' +
                      json.result.obj.source_table +
                      '/set?' +
                      json.result.obj.source_field +
                      '=' +
                      json.result.obj.source_id,
                    inventory_sub,
                    (res_sub) => {
                      console.log(res_sub);
                    }
                  );
              }
                              if(sum<=5){
                  let message_inform = {
                    title: '商品库存不足',
                    type: '通知',
                    content: `商品ID${json.result.obj.goods_id}的测试商品库存不足5件，请尽快进行补货`,
                    state: 1,
                    user_id: json.result.obj.user_id
                  }
                  this.$post("~/api/message_inform/add",message_inform)
                }
                          });
            }
          }
        }
      });
    },
    /**
     * 支付
     */
    submit_pay() {
      var _this = this;
	  if( _this.payway[_this.paywayact].id == 3){
	    const passwordRegex = /^\d{6}$/;
	   if(!_this.pay_obj.account){
	    return this.$toast('请输入网银账号');
	   }else if(!passwordRegex.test(_this.pay_obj.password)){
	    return this.$toast('网银密码格式为6位数字');
	   }
	  }

      let params = {
        state: '待发货',
        remark: _this.remark,
      }
      this.$post(
        '~/api/order/set?order_number=' + this.order_number,
        params,
        (res) => {
          if (res.result) {
            //修改数量
            for (let i = 0; i < _this.list.length; i++) {
              _this.update_goods_inventory(_this.list[i]);
            }

                          let message_inform = {
                title: '用户支付订单成功',
                type: '通知',
                content: `订单 #${_this.query.order_number} 已支付成功，金额 ¥${_this.sum_price}。请在24小时内发货！`,
                state: 1,
                user_id: _this.issuer_id
              }
              this.$post("~/api/message_inform/add",message_inform)
                        _this.$toast('支付成功');
			let paramsUserSet = {}
				paramsUserSet.integral = parseInt(this.$store.state.app.userInfo.integral)+parseInt(_this.sum_price)
					this.$post(
			  '~/api/user/set?user_id=' + _this.$store.state.app.userInfo.user_id,
			  paramsUserSet,
			  (res) => {
			    setTimeout(() => {
			      _this.$nav('/pagesA/order/list?order_number='+this.order_number);
			    }, 700);
			  }
			);
          } else if (res.error) {
            _this.$toast(res.error.message);
            console.log(res.error);
          }
        }
      );
    },
    addrDialogHandle() {
      this.getAddr();
      this.showAddrDialog = true;
    },
    getAddr() {
      this.$get('~/api/address/get_list?user_id=' + this.userInfo.user_id, {}).then((res) => {
        if (res.result.count) {
          this.addrList = res.result.list;
          this.addrList.forEach((item) => {
            if (
              item.address === this.contact_address &&
              item.name === this.contact_name &&
              item.phone === this.contact_phone
            ) {
              this.currentAddr = item.address_id;
            }
          });
        }
      });
    },
    toAddr() {
      uni.navigateTo({
        url: 'pagesA/user/address',
      });
      this.showAddrDialog = false;
    },
    radioChange(evt) {
      this.currentAddr = Number(evt.detail.value);
    },
    addrChangeHandle() {
      this.showAddrDialog = false;

      let currentAddrList = this.addrList.filter((item) => {
        return item.address_id === this.currentAddr;
      });
      currentAddrList.forEach((item) => {
        this.contact_address = item.address;
        this.contact_name = item.name;
        this.contact_phone = item.phone;
      });

      this.$post('~/api/order/set?order_number=' + this.order_number, {
        contact_address: this.contact_address,
        contact_name: this.contact_name,
        contact_phone: this.contact_phone,
      });
    },
  },
  computed: {
	integral_total() {
	  //计算总积分
	  let integral_total = 0;
	  this.list.map((o) => (integral_total = o.num * this.integral));
	  return integral_total;
	},
    sum_price() {
      var sum_price = 0;
      this.list.map((o) => (sum_price += parseFloat(o.price_count)));
      return sum_price.toFixed(2);
    },
    /**
     * 计算原价
     */
    sum_price_ago() {
      var sum_price_ago = 0;
      this.list.map((o) => (sum_price_ago += parseFloat(o.price) * parseInt(o.num)));
      return sum_price_ago.toFixed(2);
    },
  },
};
</script>
<style lang="scss" scoped>
@import 'styles/pagesA/index.scss';
.contact-info {
  position: relative;
  .addr-btn {
    position: absolute;
    right: 0;
    top: 11px;
    font-size: 12px;
    color: #0079fe;
  }
}
.addr-dialog{
  .addr-title{
    font-weight: bold;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
  }
  .addr-container-content{
    margin-bottom: 15px;
    .addr-container-item{
      width: calc(100% - 40px);
      margin-left: 15px;
      font-size: 12px;
      .addr-info{
        display: flex;
        .addr-name{
          font-weight: bold;
          margin-right: 15px;
        }
      }
      .addr-detail{
        margin-top: 3px;
        color: #999;
      }
    }
  }
  .addr-container-btn{
    display: flex;
    align-items: center;
    justify-content: space-between;
    .addr-link{
      font-size: 12px;
      color: #0079fe;
    }
    button{
      font-size: 12px !important;
      margin-left: 5px;
      height: 30px !important;
    }
  }
}
.goods_recommend{
	width: 92%;
	border: 1px solid #f2f2f2;
	margin: 20px 4%;
	padding: 4%;
	border-radius: 5px;
	> .title{
		font-weight: bold;
		margin-bottom: 15px;
	}
	.goods_item{
		position: relative;
		display: flex;
		.goods-img{
			width: 110px;
			height: 110px;
			image{
				width: 100%;
				height: 100%;
				object-fit: cover;
			}
		}
		.goods-info{
			width: calc(100% - 110px);
			padding-left: 15px;
			position: relative;
			height: 110px;
			.desc{
				font-size: 12px;
				margin-top: 5px;
				color: #999;
			}
			.price-wrapper{
				margin-top: 10px;
				.price{
					display: inline;
					color: #dc3545;
					font-size: 16px;
				}
				.price_ago{
					display: inline;
					margin-left: 10px;
					font-size: 12px;
					text-decoration: line-through;
					color: #999;
				}
			}
			.info{
				display: flex;
				position: absolute;
				left: 15px;
				bottom: 0;
				width: 100%;
				font-size: 12px;
				color: #999;
				view{
					margin-right: 15px;
				}
			}
		}
	}
}
</style>
