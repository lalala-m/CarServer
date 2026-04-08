<template>
  <view class="page_search" :style="{ paddingTop: vuex_custom_bar_height + 'px' }">
    <tn-nav-bar>搜索结果</tn-nav-bar>
    <view class="search-wrapper">
      <Search
        v-model="keyword"
        placeholder="搜索"
        @cancel="cancel"
        @search="debounceSearch"
        @input="debounceSearch"
      />
    </view>

    <view class="search-list" v-if="searchList.length > 0">
      <view class="search-item-wrapper" v-for="(item, index) in searchList" :key="index">
        <view class="search-item-common" @click="navHandle(item)">
          <view class="title text-ellipsis-2">{{ item.title }} </view>
          <view v-if="item.matched_fields && item.matched_fields.length" class="matched-fields">
            <view v-for="(field, fieldIndex) in item.matched_fields" :key="fieldIndex" class="field-item">
              <text class="field-name">{{ field.field }}：</text>
              <text class="field-value">{{ field.value }}</text>
            </view>
          </view>
          <view class="content">
            <view class="content-left" v-if="item.img">
              <image :src="$fullImgUrl(item.img) || '/static/img/default.png'" mode="scaleToFill" />
            </view>
            <view class="content-right">
              <view class="details text-ellipsis-2"> {{ item.description }}</view>
              <view class="datetime"> {{ item.create_time }}</view>
            </view>
          </view>
        </view>
      </view>
    </view>
    <view class="search-list-empty" v-else> </view>
  </view>
</template>

<script>
import Search from '@/components/businessCp/search.vue';
import * as HomeApi from '@/api/home';
import { debounce } from '@/utils';
export default {
  components: {
    Search,
  },
  data() {
    return {
      keyword: '',
      searchList: [],
      searchParams: {
        search_type: '', // 搜索类型
        multi_field: false, // 是否多字段搜索
      }
    };
  },
  onLoad(options) {
    // 接收页面参数
    if (options.keyword) {
      this.keyword = options.keyword;
    }
    if (options.search_type) {
      this.searchParams.search_type = options.search_type;
    }
    if (options.multi_field) {
      this.searchParams.multi_field = options.multi_field === 'true';
    }
    
    // 如果有初始关键词，自动搜索
    if (this.keyword) {
      this.search();
    }
  },
  mounted() {},
  methods: {
    //防抖模式
    debounceSearch: debounce(
      function () {
        this.search();
      },
      500,
      true
    ),
    // 多字段搜索商品
    async searchGoodsMultiField(keyword) {
      try {
        // 获取所有商品数据
        const params = {
          page: 1,
          size: 1000, // 获取足够多的数据进行前端过滤
          like: 1
        };
        
        const res = await HomeApi.getHomeGoodsSearchListApi(params);
        if (res.result && res.result.list) {
          const allGoods = res.result.list;
          
          // 前端多字段过滤
          const filteredList = this.filterGoodsByMultipleFields(allGoods, keyword);
          
          // 处理匹配字段信息
          const processedList = this.processGoodsSearchResult(filteredList, keyword);
          
          return processedList.map(item => ({
            search_type: 'goods',
            api_source: 'goods_multi_field',
            ...item
          }));
        }
        return [];
      } catch (error) {
        console.error('商品多字段搜索失败:', error);
        return [];
      }
    },
    
    // 过滤商品数据（多字段搜索）
    filterGoodsByMultipleFields(list, keyword) {
      if (!keyword) return list;

      return list.filter(item => {
        // 搜索title
        if (item.title && item.title.toLowerCase().includes(keyword.toLowerCase())) {
          return true;
        }

        // 搜索description
        if (item.description && item.description.toLowerCase().includes(keyword.toLowerCase())) {
          return true;
        }

        // 搜索customize_field
        if (item.customize_field) {
          try {
            const customFields = JSON.parse(item.customize_field);
            const hasMatch = customFields.some(field =>
              field.field_value && field.field_value.toString().toLowerCase().includes(keyword.toLowerCase())
            );
            if (hasMatch) {
              return true;
            }
          } catch (e) {
            console.error("解析customize_field失败:", e);
          }
        }

        return false;
      });
    },

    // 处理商品搜索结果，标记匹配字段
    processGoodsSearchResult(list, keyword) {
      return list.map((item) => {
        let matchedFields = [];

        // 检查各个字段是否匹配
        if (item.title && item.title.toLowerCase().includes(keyword.toLowerCase())) {
          matchedFields.push({
            field: "标题",
            value: item.title,
            matched: true
          });
        }
        if (item.description && item.description.toLowerCase().includes(keyword.toLowerCase())) {
          matchedFields.push({
            field: "描述",
            value: item.description,
            matched: true
          });
        }
        if (item.customize_field) {
          try {
            const customFields = JSON.parse(item.customize_field);
            customFields.forEach(fieldObj => {
              if (fieldObj.field_value && fieldObj.field_value.toString().toLowerCase().includes(keyword.toLowerCase())) {
                matchedFields.push({
                  field: fieldObj.field_name || "自定义字段",
                  value: fieldObj.field_value.toString(),
                  matched: true
                });
              }
            });
          } catch (e) {
            console.error("解析customize_field失败:", e);
          }
        }

        // 添加匹配字段信息
        item.matched_fields = matchedFields;
        return item;
      });
    },
    async search() {
      // 如果是商品多字段搜索
      if (this.searchParams.search_type === 'goods' && this.searchParams.multi_field && this.keyword) {
        const goodsResults = await this.searchGoodsMultiField(this.keyword);
        this.searchList = goodsResults;
        return;
      }
      let params = {
        like: 1,
        page: 1,
        size: 10,
      };
      if (this.keyword) {
        params['title'] = this.keyword;
      }
      // 使用对象结构化的方式，更清晰地标识每个API
      const apiPromises = {
        goods: HomeApi.getHomeGoodsSearchListApi(params),
        articles: HomeApi.getHomeArticleListApi(params),
        forums: HomeApi.getHomeForumListApi(params),
                                              car_information_car_name: HomeApi.get_car_information_car_name_ListApi(params),
                                  car_information_car_models: HomeApi.get_car_information_car_models_ListApi(params),
                                  car_information_new_and_old_car: HomeApi.get_car_information_new_and_old_car_ListApi(params),
                                                                                                maintenance_project_bundle: HomeApi.get_maintenance_project_bundle_ListApi(params),
                                                                                                                    boutique_center_service_name: HomeApi.get_boutique_center_service_name_ListApi(params),
                        boutique_center_service_type: HomeApi.get_boutique_center_service_type_ListApi(params),
                                                                                              online_mall_commodity_specifications: HomeApi.get_online_mall_commodity_specifications_ListApi(params),
                    };
      // 使用Promise.allSettled获取所有结果，包括失败的
      Promise.allSettled(Object.values(apiPromises)).then((results) => {
        let list = [];
        const apiKeys = Object.keys(apiPromises);
        
        results.forEach((result, index) => {
          const apiName = apiKeys[index];
          
          if (result.status === 'fulfilled' && result.value) {
            const data = result.value;
            console.log(`${apiName} API 返回数据:`, data);
            
            // 根据API名称处理不同的数据
            switch(apiName) {
                        case 'goods':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'goods',
                      api_source: apiName,
                      ...item,
                    });
                  });
                }
                break;
                                  case 'articles':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'article',
                      api_source: apiName,
                      ...item,
                    });
                  });
                }
                break;
                                  case 'forums':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'forum',
                      api_source: apiName,
                      ...item,
                    });
                  });
                }
                break;
          									                                  case 'car_information_car_name':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'car_name',
                      api_source: apiName,
                      title: item.car_name,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                                        case 'car_information_car_models':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'car_models',
                      api_source: apiName,
                      title: item.car_models,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                                        case 'car_information_new_and_old_car':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'new_and_old_car',
                      api_source: apiName,
                      title: item.new_and_old_car,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                                                                        			                        case 'maintenance_project_bundle':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'project_bundle',
                      api_source: apiName,
                      title: item.project_bundle,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                                                                        								                                  case 'boutique_center_service_name':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'service_name',
                      api_source: apiName,
                      title: item.service_name,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                              case 'boutique_center_service_type':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'service_type',
                      api_source: apiName,
                      title: item.service_type,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
                                                    							                                  case 'online_mall_commodity_specifications':
                if(data.result && data.result.list) {
                  data.result.list.forEach((item) => {
                    list.push({
                      search_type: 'commodity_specifications',
                      api_source: apiName,
                      title: item.commodity_specifications,
                      description: null,
                      ...item,
                    });
                  });
                }
                break;
            	            }
          } else if (result.status === 'rejected') {
            console.error(`${apiName} API 调用失败:`, result.reason);
          }
        });
        
        this.searchList = list;
      });
    },
    cancel() {
      this.searchList = [];
    },
    navHandle(o) {
      if (o.search_type == 'article') {
        this.$navTo('/pages/article/details?article_id' + '=' + o.article_id);
      }
      if (o.search_type == 'forum') {
        this.$navTo('/pagesA/forum/details?forum_id' + '=' + o.forum_id);
      }
      if (o.search_type == 'goods') {
        this.$navTo('/pagesA/goods/details?goods_id' + '=' + o.goods_id);
      }
                                              if (o.search_type == 'car_name') {
          this.$navTo('/pagesC/car_information/details?car_information_id' + '=' + o.car_information_id);
        }
                                  if (o.search_type == 'car_models') {
          this.$navTo('/pagesC/car_information/details?car_information_id' + '=' + o.car_information_id);
        }
                                  if (o.search_type == 'new_and_old_car') {
          this.$navTo('/pagesC/car_information/details?car_information_id' + '=' + o.car_information_id);
        }
                                                                                                if (o.search_type == 'project_bundle') {
          this.$navTo('/pagesC/maintenance/details?maintenance_id' + '=' + o.maintenance_id);
        }
                                                                                                                    if (o.search_type == 'service_name') {
          this.$navTo('/pagesC/boutique_center/details?boutique_center_id' + '=' + o.boutique_center_id);
        }
                        if (o.search_type == 'service_type') {
          this.$navTo('/pagesC/boutique_center/details?boutique_center_id' + '=' + o.boutique_center_id);
        }
                                                                                              if (o.search_type == 'commodity_specifications') {
          this.$navTo('/pagesC/online_mall/details?online_mall_id' + '=' + o.online_mall_id);
        }
                  },
  },
};
</script>

<style lang="scss" scoped>
.matched-fields {
  margin: 10rpx 0;
  padding: 20rpx;
  background: #f8f9fa;
  border-radius: 8rpx;
}

.field-item {
  margin-bottom: 8rpx;
  font-size: 24rpx;
  line-height: 1.4;
}

.field-name {
  color: #666;
  font-weight: bold;
  margin-right: 10rpx;
}

.field-value {
  color: #333;
}
</style>
