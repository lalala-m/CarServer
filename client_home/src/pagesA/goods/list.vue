<template>
  <view id="page_goods_list" class="page_goods_list" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>产品展示</tn-nav-bar>

    <view class="search-wrapper">
      <Search
        v-model="query.title"
        placeholder="搜索产品"
        @input="(val) => inputValue(val, 'title')"
        @search="search_"
        @cancel="cancel"
      />
    </view>
    <view class="goods-wrapper">
      <scroll-view scroll-y class="left-aside">
        <!-- :class="{ active: index === selected }" -->
        <view
          v-for="(item, index) in types"
          :key="item.id"
          class="f-item"
          :class="{ active: index === selected }"
          @click="searchType(item, index)"
        >
		  <image v-if="item.icon" :src="$fullImgUrl(item.icon)" mode="aspectFit" class="icon"></image>
          {{ item.text }}
        </view>
      </scroll-view>
      <scroll-view scroll-with-animation scroll-y class="right-aside" @scrolltolower="lowerBottom">
	  	<view class="goods-type-sub" v-if="types_sub.length > 0">
			<view v-for="(item, index) in types_sub" :key="item.type_id" class="f-item" :class="{ active: index === selected_sub }" @click="searchTypeSub(item, index)">
				<image v-if="item.icon" :src="$fullImgUrl(item.icon)" mode="aspectFit" class="icon"></image>
				{{ item.text }}
			</view>
		</view>
        <view class="goods-list">
			<view v-if="groupedProductList.length > 0">
				<view v-for="group in groupedProductList" :key="group.category" class="goods-group">
					<view class="group-title">
						<image v-if="group.icon" :src="$fullImgUrl(group.icon)" mode="aspectFit" class="icon"></image>
						{{ group.category }}
					</view>
					<view class="guess-section">
						<view v-for="(item, index) in group.goods" :key="index" class="guess-item" @click="goodsClickFn('/pagesA/goods/details?goods_id=' + item[vm.goods_id], item)">
							<view class="image-wrapper">
								<image :src="$fullImgUrl(item[vm.img])" mode="aspectFill"></image>
							</view>
							<text class="title clamp text-ellipsis-2">{{ item[vm.title] }}</text>
							<text class="title2 clamp text-ellipsis-2">{{ item.description }}</text>
							<view class="price-wrapper">
											<view class="price"> 
									￥{{ item[vm.price] | keepTwoNum }}
									<text v-if="item[vm.integral] != null && item[vm.integral] != 0">/{{ item[vm.integral] }}<i style="font-style: normal;">积分</i></text>
								</view>
											<view class="price_ago"> ￥{{ item[vm.price_ago] | keepTwoNum }} </view>
							</view>
																																																																																																	<view class="info">
								<view class="sales">销量<text>{{ item[vm.sales] }}</text></view>
								<view class="inventory">库存<text>{{ item[vm.inventory] }}</text></view>
							</view>
																																																																																																</view>
					</view>
				</view>
			</view>
			<view v-else>
				<view class="guess-section">
																																																																																								<view
				v-for="(item, index) in list"
				:key="index"
				class="guess-item"
				@click="goodsClickFn('/pagesA/goods/details?goods_id=' + item[vm.goods_id], item)"
				>
													<view class="image-wrapper">
					<image :src="$fullImgUrl(item[vm.img])" mode="aspectFill"></image>
				</view>
				<text class="title clamp text-ellipsis-2">{{ item[vm.title] }}</text>
				<text class="title2 clamp text-ellipsis-2">{{ item.description }}</text>
				<view class="price-wrapper">
								<view class="price"> 
									￥{{ item[vm.price] | keepTwoNum }}
									<text v-if="item[vm.integral] != null && item[vm.integral] != 0">/{{ item[vm.integral] }}<i style="font-style: normal;">积分</i></text>
								</view>
								<view class="price_ago"> ￥{{ item[vm.price_ago] | keepTwoNum }} </view>
				</view>
																																																																																														<view class="info">
					<view class="sales">销量<text>{{ item[vm.sales] }}</text></view>
					<view class="inventory">库存<text>{{ item[vm.inventory] }}</text></view>
				</view>
				<!--<view class="operation">
					<view class="add_cart" v-if="$check_cart_page('/' + item.source_table + '/details')" @click.native.stop="add_cart(item)">
						<image src="@/static/img/cart_b.png"></image>
					</view>
				</view>-->
																																																																																														</view>
			</view>
          
          </view>
																																																																																										          <view class="medicine-title">
            {{ no_data ? '没有更多数据了～' : '下拉加载更多' }}
          </view>
        </view>
      </scroll-view>
    </view>
    </view>
</template>

<script>
import Search from '@/components/businessCp/search.vue';
import { setGoodsApi } from '@/api/goods';
import { getCartListApi, setCartApi, addCartApi } from '@/api/cart';

export default {
  components: {
    Search,
  },
  filters: {
    //过滤器 保留两位
    keepTwoNum: function (value) {
      value = Number(value);
      return value.toFixed(2);
    },
  },
  data() {
    return {
      list: [],
      selected: 0,
      query: {
        title: '',
        page: 1,
        size: 10,
		list_status: 1,
		like: 1,
        type: '',
        orderby: 'hits desc',
      },
      // 分类
      types: [{ value: '', text: '全部' }],
	  types_sub: [],
	  selected_sub: 0,
	  goodsTypes: [],
	  groupedProductList: [],
      vm: {
        goods_id: 'goods_id',
        img: 'img',
        title: 'title',
        price: 'price',
		integral: 'integral',
        price_ago: 'price_ago',
        sales: 'sales',
        hits: 'hits',
        source_table: 'source_table',
        description: 'description',
		inventory: 'inventory',
      },
      no_data: false,
																															                                                      	        };
  },
	computed: {
																																},
                                                        watch: {
  	list: {
  		handler(val) {
			  		},
  		deep: true
  	},
  },
      methods: {
                                                      		        /**
     * 触底加载
     */
    lowerBottom() {
      if (this.no_data) {
        return;
      }
      this.query.page++;
      this.get_list();
    },
    /**
     * 跳转商品详情
     */
    goodsClickFn(url, o) {
      setGoodsApi(o.goods_id, {
        hits: parseInt(o.hits) + 1,
      }).then((res) => {
        if (res.result) {
          uni.navigateTo({
            url,
          });
        } else {
          this.$toast(res.error.message);
        }
      });
    },
    /**
     * 获取商品列表
     */
	get_list() {
		let params = {
			page: this.query.page,
			size: 100,
			list_status: 1,
		};
		this.$get('/goods/get_list', params, (res) => {
			if (this.query.page === 1) {
				this.list = [];
				this.no_data = false;
			}
			let goodsList = res.result.list;
			if (this.query.type && this.query.type !== '') {
				// 获取当前分类及其所有子分类的名称列表
				const categoryNames = this.getCategoryAndChildrenNames(this.query.type);
				goodsList = goodsList.filter(item =>
					categoryNames.includes(item.type)
				);
			}
			this.list = this.list.concat(goodsList);
			if (this.selected_sub > 0 && this.types_sub[this.selected_sub]) {
				this.groupGoodsByThirdLevel();
			}
  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  				  				if (this.list.length >= res.result.count) {
				this.no_data = true;
			}
		});
	},
	/**
	 * 按三级分类分组商品
	 */
	groupGoodsByThirdLevel() {
		const currentSubCategory = this.types_sub[this.selected_sub];
		if (!currentSubCategory) return;
		// 获取当前二级分类的所有三级子分类
		const thirdLevelCategories = this.goodsTypes.filter(cat =>
			cat.father_id === currentSubCategory.type_id
		);

		// 如果没有三级分类，直接显示
		if (thirdLevelCategories.length === 0) {
  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  				  			}
		// 按三级分类分组
		const groupedGoods = {};
		// 初始化分组
		thirdLevelCategories.forEach(cat => {
			groupedGoods[cat.name] = [];
		});
		// 未分组的商品
		groupedGoods['其他'] = [];
		// 分组商品
		this.list.forEach(goods => {
			let found = false;
			for (const catName in groupedGoods) {
				if (catName !== '其他' && this.isGoodsInCategory(goods, catName)) {
					groupedGoods[catName].push(goods);
					found = true;
					break;
				}
			}
			if (!found) {
				groupedGoods['其他'].push(goods);
			}
		});
		this.displayGroupedGoods(groupedGoods);
	},

	/**
	* 判断商品是否属于某个分类（包括子分类）
	 */
	isGoodsInCategory(goods, categoryName) {
		const categoryNames = this.getCategoryAndChildrenNames(categoryName);
		return categoryNames.includes(goods.type);
	},
	/**
	 * 显示分组后的商品
	 */
	displayGroupedGoods(groupedGoods) {
		this.groupedProductList = [];

		for (const categoryName in groupedGoods) {
			if (groupedGoods[categoryName].length > 0) {
				const categoryInfo = this.goodsTypes.find(cat => cat.name === categoryName);
				const categoryIcon = categoryInfo ? categoryInfo.icon : '';
				this.groupedProductList.push({
					category: categoryName,
					icon: categoryIcon,
					goods: groupedGoods[categoryName]
				});
			}
		}
		setTimeout(() => {
			this.getScore();
		}, 500);
	},
	/**
	 * 获取分类及其所有子分类的名称
	 */
	getCategoryAndChildrenNames(categoryName) {
		const allCategories = this.goodsTypes;

		// 找到当前分类
		const currentCategory = allCategories.find(cat => cat.name === categoryName);
		if (!currentCategory) return [categoryName];

		// 递归获取所有子分类
		const getAllChildren = (fatherId) => {
			const children = allCategories.filter(cat => cat.father_id === fatherId);
			let result = [];

			children.forEach(child => {
				result.push(child.name);
				result = result.concat(getAllChildren(child.type_id));
			});

			return result;
		};
		// 返回当前分类及其所有子分类的名称
		return [currentCategory.name].concat(getAllChildren(currentCategory.type_id));
	},

    /**
     * 添加商品过滤
     */
    get_goods_type(params) {
      this.$get('/goods_type/get_list', {}, (res) => {
        if (res.result) {
          this.goodsTypes = res.result.list;
          let list = res.result.list;
          if (params.father_id == 0) {
            // 显示一级分类
            this.types = [{ value: '', text: '全部' }];
            list.filter(obj => obj.father_id === 0).map((obj) => {
              this.types.push({ type_id: obj.type_id, value: obj.name, text: obj.name, icon: obj.icon });
            });
          } else {
            // 显示子分类
            this.types_sub = [{ value: '', text: '全部' }];
            list.filter(obj => obj.father_id === params.father_id).map((obj) => {
              this.types_sub.push({ type_id: obj.type_id, value: obj.name, text: obj.name, icon: obj.icon });
            });
          }
        }
      });
    },
	/**
	 * 添加购物车
	 */
	add_cart(obj) {
	  var { title, img, price, price_ago, num_buy: num, description, goods_id, type } = obj;
	  var body = {
	    title,
	    img,
	    price,
	    price_ago,
	    num,
	    price_count: price,
	    description,
	    goods_id,
	    type,
	    user_id: this.userInfo.user_id,
	  };
	  body.num = 1;
	  getCartListApi({
	    goods_id: obj.goods_id,
	    user_id: body.user_id,
	  }).then((res) => {
	    if (res.result.count) {
	      var { cart_id, num, price, price_count } = res.result.list[0];
	      num += 1;
	      price_count += price;
	
	      setCartApi(cart_id, {
	        num,
	        price,
	        price_count,
	      }).then((res) => {
	        this.$toast('已加入购物车', 'success');
	        console.log('更改值');
	      });
	    } else {
	      addCartApi(body).then((res) => {
	        this.$toast('已加入购物车', 'success');
	      });
	    }
	  });
	},

    /**
     * 搜索
     */

    search_() {
      // this.query.page = 1;
      // this.get_list();
	  uni.navigateTo({
		url: `/pages/search/index?keyword=${this.query.title}&search_type=goods&multi_field=true`
	  });
    },
    /**
     * 取消搜索
     */
    cancel() {
      this.query.title = '';
      this.search_();
    },
    // 改变分类标签
    searchType(v, index) {
      this.selected = index;
      this.query.type = this.types[index]['value'];
      this.query.page = 1;
      this.selected_sub = 0;
	  this.groupedProductList = [];
      if (index !== 0) {
        // 获取子分类
        this.get_goods_type({ father_id: this.types[index]['type_id'] });
      } else {
        this.types_sub = [];
      }
      this.get_list();
    },
    searchTypeSub(v, index) {
      this.selected_sub = index;
      this.query.page = 1;
      this.groupedProductList = [];
      if (index === 0) {
        this.query.type = this.types[this.selected]['value'];
        this.get_list();
      } else {
        this.query.type = this.types_sub[index]['value'];
        this.get_list();
      }
    },
	inputValue(val, key) {
	  this.query[key] = val;
	},
																															  },
  // mounted() {
  //   this.get_goods_type();
  //   this.get_list();
  // },
	onShow() {
		this.get_goods_type({ father_id: 0 });
		this.get_list();
																																},
};
</script>

<style lang="scss" scoped>
@import 'styles/pagesA/index.scss';
.goods-type-sub {
	display: flex;
	flex-wrap: wrap;

	.f-item {
		font-size: 13px;
		background: #fff;
		border: 1px solid #e7e7e7;
		margin: 5rpx;
		padding: 5rpx 10rpx;
		display: flex;
		align-items: center;

		.icon {
			width: 14px;
			height: 14px;
			margin-right: 5px;
		}
	}

	.f-item.active {
		color: #0079fe;
	}
}

.left-aside {
	.f-item {
		display: flex;
		flex-direction: column;
		align-items: center;

		.icon {
			width: 20px;
			height: 20px;
			margin-bottom: 5px;
		}
	}
}
.group-title{
	font-size: 13px;
	color: #666;
	padding: 10px 5px;
	display: flex;
	align-items: center;
	background: #fff;
	.icon{
		width: 14px;
		height: 14px;
		margin-right: 5px;
	}
}
																															</style>
