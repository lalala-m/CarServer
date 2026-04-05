<template>
  <view class="charts-box">
    <qiun-data-charts :canvas2d="true" type="column" :chartData="chartData" :opts="opts" :ontouch="true" />
  </view>
</template>
<script>
import qiunDataCharts from '../../components/qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
export default {
  components: { qiunDataCharts },
  props: { id: String, vm: Object, title: String },
  computed: {
    chartData(){
      const xAxis = (this.vm && this.vm.xAxis) || [];
      const names = (this.vm && this.vm.names) || [];
      const values = (this.vm && this.vm.values) || [];
      return {
        categories: xAxis,
        series: names.map((n, i)=>({ name: n, data: values.map(row=>row[i]||0) }))
      };
    },
    opts(){
      return {
        color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
        padding: [15,15,0,5],
        touchMoveLimit: 24,
        enableScroll: true,
        legend: {},
        xAxis: {
          disableGrid: true,
          scrollShow: true,
          itemCount: 4
        },
        yAxis: {
          data: [
            {
              min: 0
            }
          ]
        },
        extra: {
          column: {
            type: "group",
            width: 20,
            seriesGap: 5,
            activeBgColor: "#000000",
            activeBgOpacity: 0.08
          }
        }
      }
    }
  }
}
</script>

