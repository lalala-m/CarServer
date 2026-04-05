<!-- 评分优化开始 -->
<template>
  <view class="charts-box">
    <qiun-data-charts type="bar" :opts="opts" :chartData="chartData" />
  </view>
</template>

<script>
import qiunDataCharts from './qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
export default {
  components: {
    qiunDataCharts,
  },
  props: {
    obj: {
      type: Object,
      default: () => {}
    },
    sourcetable: {
      type: String,
      default: ''
    },
    sourcefield: {
      type: String,
      default: ''
    },
    refresh: {
      type: Boolean,
      default: false
    },
    goods_id: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      score_num: 0,
      score_list: [],
      starCounts: [0, 0, 0, 0, 0],
      chartData: {},
      initChartTimer: null,
      //您可以通过修改 config-ucharts.js 文件中下标为 ['column'] 的节点来配置全局默认参数，如都是默认参数，此处可以不传 opts 。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
      opts: {
        legend: {
          show: false // 确保不显示图例
        },
        padding: [15,60,0,5],
        xAxis: {
          disabled: true,
          // 对应 ECharts 的 xAxis 配置
          disableGrid: true, // 不显示网格线（对应 splitLine.show: false）
          format: "percentage"
        },
        yAxis: {
        },
        extra: {
          bar: {
            type: "stack",
            seriesGap: 2,
            width: 25, // 柱子宽度
            linearType: "none", // 不使用渐变色
            barBorderRadius: 0 // 直角柱子
          },
          tooltip: {
            showBox: true,
            showArrow: true,
          },
          label: {
            show: true,
            position: "right", // 标签在右侧
            fontColor: "#000",
            fontSize: 12,
            offsetX: 5, // 微调位置
          }
        }
      },
    };
  },
  created() {
    this.initChartTimer && clearTimeout(this.initChartTimer);
    this.initChartTimer = setTimeout(() => {
      this.getOwnScore();
    }, 300);
  },
  watch: {
    refresh: {
      handler(val) {
        if (val) {
          this.initChartTimer && clearTimeout(this.initChartTimer);
          this.initChartTimer = setTimeout(() => {
            this.getOwnScore();
          }, 300);
        }
      }
    }
  },
  methods: {
    // 查询评分列表
    async getOwnScore() {
      const url = "~/api/score/get_list";
      const params = {
        like: 0,
        source_table: this.sourcetable,
        source_field: this.sourcefield,
        source_id: this.goods_id ? this.obj[this.goods_id] : this.obj[this.sourcefield],
      };
      const res = await this.$get(url, params);
      console.log(res);
      this.score_list = res.result.list;
      this.starCounts = [0, 0, 0, 0, 0];
      this.score_list.forEach((item) => {
        this.starCounts[item.score_num - 1]++
      });
      const total = this.score_list.length;
      const series = total ? this.starCounts.map(count => Number((count / total).toFixed(2))): this.starCounts;
      
      this.$emit("scorePersonTotal", total);

      const chartData = {
        categories: ["1星", "2星", "3星", "4星", "5星"],
        series: [{
          name: '占比',
          data: series,
          color: '#ffd596',
          format: "percentage"
        }]
      };
      this.chartData = JSON.parse(JSON.stringify(chartData));
    },
  },
};
</script>

<style scoped>
/* 请根据实际需求修改父元素尺寸，组件自动识别宽高 */
.charts-box {
  width: 100%;
  height: 200px;
  /* margin-top: 30px; */
}
</style>
<!-- 评分优化结束 -->
