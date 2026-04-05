<template>
  <view id="page_diy_details" class="page_diy_details page_boutique_center_details" :style="{ paddingTop: vuex_custom_bar_height + 'px', paddingBottom: vuex_safe_area_bottom + 'px',}">
    <tn-nav-bar>精品中心</tn-nav-bar>
    <view class="page_diy page_boutique_center" id="boutique_center_details">
                <view class="warp cover_wrapper" v-if="$check_field('get', 'service_cover')">
        <image class="diy_img diy-image" :src="$fullImgUrl(obj['service_cover'])"/>
      </view>
      
																							      <view class="warp info-wrapper">
        <view class="container-fluid">
          <view class="row">
              <view v-if="$check_field('get', 'service_code')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>服务编码</span>
                </view>
                    <view class="diy_field diy_text">
                  <span>
                                          {{ obj["service_code"] }}                                      </span>
                </view>
                </view>
                <view v-if="$check_field('get', 'service_name')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>服务名称</span>
                </view>
                    <view class="diy_field diy_text">
                  <span>
                                          {{ obj["service_name"] }}                                      </span>
                </view>
                </view>
                <view v-if="$check_field('get', 'service_type')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>服务类型</span>
                </view>
                    <view class="diy_field diy_text">
                  <span>
                                          {{ obj["service_type"] }}                                      </span>
                </view>
                </view>
                <view v-if="$check_field('get', 'service_price')" class="info-item  field_text ">
                <view class="diy_title">
                  <span>服务价格</span>
                </view>
                    <view class="diy_field diy_number">
                    <span>
                                              {{ obj['service_price'] }}元                                          </span>
                </view>
                </view>
                  <view v-if="$check_field('get', 'service_introduction')" class="info-item field_text ">
                <view class="diy_title">
                  <span>服务简介</span>
                </view>
                    <view class="diy_field diy_desc">
                  <span>
                      {{ obj["service_introduction"] }}
                  </span>
                </view>
                </view>
                <view v-if="$check_field('get', 'service_specificss')" class="info-item field_text ">
                <view class="diy_title">
                  <span>服务详情</span>
                </view>
                    <view class="diy_field diy_html">
                  <rich-text class="rich_text" :nodes="obj['service_specificss']"></rich-text>
                </view>
                </view>
            </view>
        </view>
        <view class="row count">
              <view class="praise view">
              <span>点赞</span>
              <span>{{ obj["praise_len"] }}</span>
            </view>
  
  
              <view class="hits view">
              <span>点击数</span>
              <span>{{ obj["hits"] }}</span>
            </view>
          </view>
      </view>

	<view class="kb-row">


        <view class="kb-wrap">
                  <view class="me-btn" v-if="$check_action('/reservation_record/edit', 'add') && !reservation_record_limit && !reservation_record_status_limit" @click="to_form('/pagesC/reservation_record/edit')">
            在线预约
          </view>
            </view>
    	</view>


      <!-- 评论区列表 -->
      <view class="comment-title">评论区</view>
      <list_comment :list="list_comment" @refresh="get_comment" :self="isSelf"></list_comment>

      <view :style="'height: 100rpx'"></view>
      <view class="footer" :style="{ paddingBottom: vuex_safe_area_bottom + 'px' }">
        <view class="footer-wrap">
              <view class="go-comment" @click="goComment('/pagesB/comment/edit?source_table=boutique_center&source_field=boutique_center_id&source_id=' + obj['boutique_center_id'])">
            评论点什么...
          </view>
    
              <view class="more">
                    <view class="more-item praise" @click="change_praise()">
              <text class="iconfont icon-praise iconfont--active" v-if="state_praise"></text>
              <text class="iconfont icon-praise" v-else></text>
              <text>点赞</text>
            </view>
        
                    <view class="more-item collect" @click="change_collect()">
              <text :class="{iconfont: true, 'icon-collect': true, 'iconfont--active': state_collect,}"></text>
              <text>收藏</text>
            </view>
        
                  </view>
            </view>
      </view>


    </view>
  </view>
</template>

<script>
  import list_comment from '@/components/diy/list_comment.vue';
  import mixin from '@/libs/mixins/page.js';

  export default {
    mixins: [mixin],
    components: {
      list_comment,
    },
    data() {
      return {
        url_get_obj: '~/api/boutique_center/get_obj?',
        field: 'boutique_center_id',
        query: {
                boutique_center_id: 0,
        },
        // 商品详情初始化
        obj: {
          boutique_center_id: 0,
              service_code: "",
                  service_name: "",
                  service_type: "",
                  service_price: 0,
                  service_cover: "",
                  service_introduction: "",
                  service_specificss: "",
              hits: 0,
          praise_len: 0,
          collect_len: 0,
					  
		  reservation_record_limit_times: 0,	// 在线预约限制次数
				  source_table: '', // 来源表名
		  source_field: '', // 来源字段名
		  source_user_id: 0, // 来源用户ID
	      create_by: 0, // 创建用户ID
        },
        // 点赞状态
        state_praise: false,
        // 点赞
        praise: 0,
        // 收藏
        collect: 0,
        // 收藏状态
        state_collect: false,
        // 评论列表初始化
        list_comment: [],
              							reservation_record_limit: false,
				reservation_record_status_limit: false,
			      };
    },
    methods: {
    // #ifdef MP-WEIXIN
    preview_file(flie) {
      uni.downloadFile({
        url: flie,
        success: function (res) {
          var filePath = res.tempFilePath;
          uni.openDocument({
            filePath: filePath,
            showMenu: true,
            success: function (res) {
              console.log('打开文档成功');
            }
          });
        }
      });
    },
    // #endif
      goComment(path) {
        if (this.$check_action('/comment/list', 'add')) {
          this.$navTo(path);
        } else {
          this.$toast('暂无权限');
        }
      },
      openUrl(url) {
        uni.navigateTo({
          url: `/pages/webview/webview?url=${url}`,
        });
      },
      /**
       * 获取点赞
       * @param {Object} obj
       */
      get_praise(obj) {
        var user_id = this.user.user_id;

        this.obj.praise_len = 0;

        this.$get(
            '~/api/praise/count',
            {
              source_table: 'boutique_center',
              source_field: 'boutique_center_id',
              source_id: obj['boutique_center_id'],
            },
            (res) => {
              if (res.result || res.result === 0) {
                this.praise = res.result;
                this.obj.praise_len = res.result;
                console.log('点赞数：', res.result);
              } else if (res.error) {
                this.$toast(res.error.message);
                console.error(res.error);
              }
            }
        );

        this.$get(
            '~/api/praise/count',
            {
              source_table: 'boutique_center',
              source_field: 'boutique_center_id',
              source_id: obj['boutique_center_id'],
              user_id,
            },
            (res) => {
              if (res.result || res.result === 0) {
                this.state_praise = res.result ? true : false;
                console.log('点赞状态：', this.state_praise);
              } else if (res.error) {
                this.$toast(res.error.message);
                console.error(res.error);
              }
            }
        );
      },

      /**
       * 改变点赞数
       */
      change_praise() {
        var user_id = this.userInfo.user_id;

        var query = {
          source_table: 'boutique_center',
          source_field: 'boutique_center_id',
          source_id: this.obj['boutique_center_id'],
          user_id,
        };

        var _this = this;
        _this.obj.praise_len = parseInt(_this.obj.praise_len);
        // 点赞状态
        if (this.state_praise) {
          this.state_praise = false;
          this.$get('~/api/praise/del', query, (res) => {
            if (res.result) {
              var praise_len = _this.obj.praise_len - 1;
              this.$post('~/api/boutique_center/set?boutique_center_id=' + _this.obj['boutique_center_id'],
                  {praise_len},(res) => {
                    if (res.result) {
                      _this.obj.praise_len = praise_len;
                      console.log('添加点赞数状态：', res.result);
                    } else if (res.error) {
                      console.error(res.error);
                    }
                  }
              );
              this.$toast('取消点赞');
            } else if (res.error) {
              this.$toast(res.error.message);
              console.error(res.error);
            }
          });
        } else {
                                                                      query.title = this.obj.service_name
                                                                                                                                            query.img = this.obj.service_cover
                                                                                                    this.state_praise = true;
          this.$post('~/api/praise/add?', query, (res) => {
            if (res.result) {
              var praise_len = _this.obj.praise_len + 1;
              this.$post('~/api/boutique_center/set?boutique_center_id=' + _this.obj['boutique_center_id'],
                  {praise_len},(res) => {
                    if (res.result) {
                      _this.obj.praise_len = praise_len;
                      console.log('添加点赞数状态：', res.result);
                    } else if (res.error) {
                      console.error(res.error);
                    }
                  }
              );
                          this.$toast('点赞成功');
            } else if (res.error) {
              this.$toast(res.error.message);
              console.error(res.error);
            }
          });
        }
      },
      /**
       * 初始化收藏状态
       * @param {Object} obj
       */
      get_collect(obj) {
        var user_id = this.user.user_id;

        var query = {
          source_table: 'boutique_center',
          source_field: 'boutique_center_id',
          source_id: obj['boutique_center_id'],
          user_id,
        };

        this.$get('~/api/collect/count', query, (res) => {
          if (res.result || res.result === 0) {
            var bl = res.result ? true : false;
            this.state_collect = bl;
            console.log('收藏状态：' + bl);
          } else if (res.error) {
            console.error(res.error);
          }
        });
      },

      /**
       * 改变收藏状态
       */
      change_collect() {
        if (!this.$check_action('/collect/list', 'add')) {
          this.$toast('暂无权限');
          return;
        }
        var user_id = this.user.user_id;

        var query = {
          source_table: 'boutique_center',
          source_field: 'boutique_center_id',
          source_id: this.obj['boutique_center_id'],
          user_id,
        };

        // 收藏状态
		var _this = this;
        if (this.state_collect) {
          this.state_collect = false;
          this.$get('~/api/collect/del', query, (res) => {
            if (res.result) {
			  var collect_len = _this.obj.collect_len - 1;
			  this.$post('~/api/boutique_center/set?boutique_center_id=' + _this.obj['boutique_center_id'],
			      {collect_len},(res) => {
			        if (res.result) {
			          _this.obj.collect_len = collect_len;
			          console.log('添加收藏数状态：', res.result);
			        } else if (res.error) {
			          console.error(res.error);
			        }
			      }
			  );
              this.$toast('取消收藏');
            } else if (res.error) {
              this.$toast(res.error.message);
              console.error(res.error);
            }
          });
        } else {
          this.state_collect = true;
                      query.title = this.obj.service_name
                                          query.img = this.obj.service_cover
                              this.$post('~/api/collect/add?', query, (res) => {
            if (res.result) {
			  var collect_len = _this.obj.collect_len + 1;
			  this.$post('~/api/boutique_center/set?boutique_center_id=' + _this.obj['boutique_center_id'],
			      {collect_len},(res) => {
			        if (res.result) {
			          _this.obj.collect_len = collect_len;
			          console.log('添加收藏数状态：', res.result);
			        } else if (res.error) {
			          console.error(res.error);
			        }
			      }
			  );
                          this.$toast('收藏成功');
            } else if (res.error) {
              this.$toast(res.error.message);
              console.error(res.error);
            }
          });
        }
      },
      /**
       * 获取评论
       * @param {Object} obj
       */
      get_comment(obj) {
		if(obj == undefined){
			obj = this.obj
		}
        var query = {
          source_table: 'boutique_center',
          source_field: 'boutique_center_id',
          source_id: obj['boutique_center_id'],
          orderby: 'create_time desc',
          reply_to_id: '0',
        };
        this.$get('/comment/get_list', query, (json) => {
          if (json.result) {
            var list_comment = json.result.list;
            list_comment.map((o) => {
              o.list_reply = [];
            });
            this.add_reply(obj, list_comment).then((list) => {
              this.list_comment = list;
            });
          }
        });
      },
      /**
       * @param { Array } list 评论列表
       * 添加回复到评论列表
       */
      add_reply(obj, list) {
        return new Promise((resolve) => {
          for (let idx = 0; idx < list.length; idx++) {
            const obj = list[idx];
            this.$get('/comment/get_list',
                {
                  source_table: 'boutique_center',
                  source_field: 'boutique_center_id',
                  source_id: obj['boutique_center_id'],
                  orderby: 'create_time desc',
                  reply_to_id: obj.comment_id,
                },
                (res) => {
                  if (res.result) {
                    obj.list_reply = res.result.list;
                  }
                }
            );
          }
          resolve(list);
        }).catch((e) => {
        });
      },
      /**
       * 添加访问量
       */
      add_hits(obj) {
        obj['hits'] = obj['hits'] + 1;
        var hits = obj['hits'];
        this.$post(
            '~/api/boutique_center/set?boutique_center_id=' + obj['boutique_center_id'],
            {
              hits,
            },
            (res) => {
              if (res.result) {
                console.log('添加访问量状态：', res.result);
                var body = {
                  source_table: 'boutique_center',
                  source_field: 'boutique_center_id',
                  source_id: this.obj.boutique_center_id,
                  user_id: this.userInfo.user_id,
                };
                this.$post('~/api/hits/add?', body, (res) => {
                  console.log(res);
                });
              } else if (res.error) {
                console.error(res.error);
              }
            }
        );
      },
      /**
       * 获取对象之后
       * @param {Object} json 结果对象
       */
      get_obj_after(json) {
        // 判断是否获取到数据
        if (this.obj) {
          var obj = this.obj;
          // 获取点赞数
          this.get_praise(obj);
          // 初始化收藏状态
          this.get_collect(obj);
          // 获取评论
          this.get_comment(obj);
          // 增加点击数
          this.add_hits(obj);
					  // 获取在线预约限制
		  this.get_reservation_record_limit(json.result.obj);
		    // 判断状态限制
    this.get_obj_status_limit();
	                                    }
      },
                                                        			  get_reservation_record_limit(obj){
	  	let param = {
	  		source_table: "boutique_center",
	  		source_id: obj.boutique_center_id,
	  		source_user_id: this.user.user_id
	  	};
      let limit = Number(obj.reservation_record_limit_times);
	  	if(limit > 0){
	  		this.$get("~/api/reservation_record/count",param,(result)=>{
	  			if(result){
	  				if(result.result >= limit){
	  					this.reservation_record_limit = true;
	  				}else{
	  					this.reservation_record_limit = false;
	  				}
	  			}
	  		})
	  	}else{
	  		this.reservation_record_limit = false;
	  	}
		this.obj.source_table = param.source_table;
		this.obj.source_id = param.source_id;
		this.obj.source_user_id = param.source_user_id;
	  },
	  get_obj_status_limit(json, func) {
			},
	    },
    created() {
                                },

    mounted() {
    },
    onShow() {
    },
    computed: {
      isSelf() {
        return (this.obj.create_by === this.userInfo.user_id) || this.userInfo.user_group == '管理员';
      }
    },
  };
</script>

<style lang="scss" scoped>
  @import 'styles/pagesC/index.scss';
  .score_statistics_chart {
    display: flex;
    padding: 0 10px;
  }
  .score_num_text {
    font-size: 24px;
    margin-right: 5px;
  }
  .score_person_num {
    font-size: 12px;
    color: #6195bd;
    text-align: left;
  }
  .user_box {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .user_avatar {
    width: 40px !important;
    height: 40px !important;
    border-radius: 100%;
    margin-right: 10px
  }

  .user_info {
    display: flex;
    flex-direction: column;
    margin-right: 20px;
  }

  .user_nickname {
    font-size: 16px;
    color: #333;
    margin-bottom: 5px;
  }
  .user_fans {
    font-size: 12px;
    color: #999;
  }

  .item-btn {
    height: 26px;
    padding: 0 20px;
    border-radius: 4px;
    margin-right: 6px;
  }
  .item-btn-radius {
    height: 26px;
    padding: 0 10px;
    border-radius: 26px;
    margin-right: 6px;
    font-size: 12px;
  }
  .plain {
    color: #0079fe;
    background-color: transparent;
    border: 1px solid #0079fe;
  }

  .chek-box {
    width: 100%;
    margin-top: 20rpx;
    padding: 1.5rpx 1rpx;
    box-sizing: border-box;
  }

  .check-item-list .check-item-li .check-item {
    width: 100%;
    height: 80rpx;
    padding: 0 10rpx;
    margin: 10rpx 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    border: 1rpx solid #ccc;
  }

  .check-item-list .check-item-li .check-item .image-container {
    width: 70rpx;
    height: 70rpx;
    margin-right: 20rpx;
  }

  .check-item-list .check-item-li .check-item .check-item-title > p:nth-child(1) {
    font-size: 20rpx;
  }

  .check-item-list .check-item-li .check-item .check-item-title > div {
    margin-top: 10rpx;
  }

  .check-item-list .check-item-li .check-item-form {
    padding: 10rpx 0;
  }

  .check-item-list .check-item-li .check-item-form form .el-form-item {
    margin-bottom: 15rpx;
  }

  .check-item-complete {
    border: 1px solid #007bff !important;
    overflow: hidden;
    position: relative;

    &::before {
      content: '';
      position: absolute;
      right: 0;
      top: 0;
      border: 15rpx solid #007bff;
      border-bottom-color: transparent;
      border-left-color: transparent;
    }

    &::after {
      content: '';
      width: 8rpx;
      height: 4rpx;
      position: absolute;
      right: 3rpx;
      top: 3rpx;
      border: 2rpx solid #fff;
      border-bottom-color: transparent;
      border-left-color: transparent;
      transform: rotate(135deg);
    }
  }

  .check-box .check-box-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 15rpx;
  }
  .check-box-desc img{
    width: 100%;
  }
  .check-progress {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: start;
  }
</style>