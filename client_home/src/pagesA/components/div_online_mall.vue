<template>
  <view class="diy_details diy_div_online_mall">
    <view v-if="Object.keys(obj).length !== 0" class="warp">
      <view class="container">
        <view class="row">
          <view v-if="$check_field('get', 'business_user')" class="col-12 col-md-6">
            <view class="diy_title">
              <span>商家用户:</span>
            </view>
              <view class="diy_field diy_uid">
              <text>
                {{ get_user_business_user(obj['business_user']) }}
              </text>
            </view>
            </view>
          <view v-if="$check_field('get', 'commodity_specifications')" class="col-12 col-md-6">
            <view class="diy_title">
              <span>商品规格:</span>
            </view>
              <view class="diy_field diy_text">
              <text>
                {{obj["commodity_specifications"]}}
              </text>
            </view>
            </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import mixin from '@/libs/mixins/page.js';
export default {
  mixins: [mixin],
  props: {
    obj_goods: {
      type: Object,
      default() {
        return {};
      },
    },
    query: {
      type: Object,
      default() {
        return {
          online_mall_id: 0,
        };
      },
    },
  },
  data() {
    return {
      obj_goods_type: {},
      obj: {},
        // 用户列表
      list_user_business_user: [],
        };
  },
  methods: {
    async get_obj_goods_type() {
      var res = await this.$get('/goods_type/get_obj', {
        name: this.obj_goods.type,
      });

      if (res.result.obj) {
        this.obj_goods_type = res.result.obj;
      } else {
        console.log('没有请求到商品分类');
      }
    },
    async get_obj_by_goods() {
      var { source_table, source_field } = this.obj_goods_type;
      var { source_id } = this.obj_goods;
      var query = {};
      query[source_field] = source_id;
      this.$get('/' + source_table + '/get_obj', {}, (res) => {
        if (res.result.obj) {
          this.obj = res.result.obj;
        } else {
          console.log('没有请求到商品分类');
        }
      });
    },
    async get_obj_by_id() {
      var res = await this.$get('/online_mall/get_obj', {
        online_mall_id: this.query.online_mall_id,
      });

      if (res.result && res.result.obj) {
        this.obj = res.result.obj;
      } else {
        console.log('没有请求到商品分类');
      }
    },
          /**
         * 获取商家用户用户列表
         */
        async get_list_user_business_user() {
          var json = await this.$get('/user/get_list?user_group=商家用户');
          if(json.result && json.result.list){
            this.list_user_business_user = json.result.list;
          }
          else if(json.error){
            console.error(json.error);
          }
        },
        get_user_business_user(id){
          let obj = this.list_user_business_user;
          let ret = "";
          for(let i=0;i<obj.length;i++){
            if(obj[i].user_id==id){
              ret = obj[i].nickname+"-"+obj[i].username;
            }
          }
          return ret;
        },
      },
  created() {
        this.get_list_user_business_user();
      },
  async onLoad() {
    if (Object.keys(this.obj_goods).length !== 0) {
      await this.get_obj_goods_type();
      await this.get_obj_by_goods();
    } else if (this.query['online_mall_id'] !== 0) {
      await this.get_obj_by_id();
    }
  },
};
</script>
<style lang="scss" scoped>
  @import 'styles/components/index.scss';
</style>