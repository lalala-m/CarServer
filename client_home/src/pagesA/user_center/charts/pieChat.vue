<template>
  <view class="charts-box">
    <qiun-data-charts :canvas2d="true" type="ring" :chartData="chartData" :opts="opts" />
    <view class="legend-pager" v-if="legendItems.length">
      <view class="legend-list">
        <view class="legend-item" v-for="(it, i) in pagedLegend" :key="i" :class="{ 'is-hidden': it.hidden }" @click="toggleLegend(it)">
          <view class="dot" :style="{ backgroundColor: it.color }"></view>
          <text class="legend-text">{{ it.name }}（{{ it.value }}）</text>
        </view>
      </view>
      <view class="legend-controls" v-if="totalPages > 1">
        <button class="btn" @click="prevLegendPage" :disabled="currentLegendPage===1">上一页</button>
        <text class="page-info">{{ currentLegendPage }} / {{ totalPages }}</text>
        <button class="btn" @click="nextLegendPage" :disabled="currentLegendPage===totalPages">下一页</button>
      </view>
    </view>
  </view>
</template>
<script>
import qiunDataCharts from '../../components/qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
export default {
  components: { qiunDataCharts },
  props: { id: String, list: Array, title: String, pageSize: { type: Number, default: 10 }, showLabel: { type: Boolean, default: true } },
  computed: {
    chartData(){
      // 依据隐藏列表过滤并为每个扇区绑定固定颜色，保证图例与图形一致
      const base = this.baseData;
      const hidden = this.hiddenNames;
      const data = base.filter(d => !hidden.includes(d.name));
      // 至少保留一个扇区以避免绘制错误
      const safeData = data.length ? data : base.slice(0, 1);
      return { series: [{ data: safeData }] };
    },
    baseData(){
      const list = this.list || [];
      const colors = this.colors;
      const MAX = 50; // 扇区最多 50，图例分页展示更细
      let data = list.map((i, idx)=>({ name: i.name, value: i.value, color: colors[idx % colors.length] }));
      if (data.length > MAX) {
        const sorted = [...data].sort((a,b)=>Number(b.value||0)-Number(a.value||0));
        const top = sorted.slice(0, MAX);
        const others = sorted.slice(MAX);
        const sumOthers = others.reduce((s,i)=>s + Number(i.value||0), 0);
        data = [...top, { name: '其他', value: sumOthers, color: colors[top.length % colors.length] }];
      }
      return data;
    },
    opts(){
      const showLabel = this.showLabel;
      return {
        rotate: false,
        rotateLock: false,
        color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
        padding: showLabel ? [34,16,22,16] : [5,5,5,5],
        enableScroll: false,
        legend: { show: false },
        title: { name: "", fontSize: 0, color: "" },
        subtitle: { name: "", fontSize: 0, color: "" },
        dataLabel: showLabel,
        extra: {
          ring: {
            ringWidth: 46,
            activeOpacity: 0.5,
            activeRadius: 10,
            // 轻微旋转，减少正上方标签重叠
            offsetAngle: showLabel ? -8 : 0,
            labelWidth: showLabel ? 18 : 0,
            border: false,
            borderWidth: 3,
            borderColor: "#FFFFFF"
          }
        }
      }
    },
    colors(){
      return this.opts.color || ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"];
    },
    legendItems(){
      const hidden = this.hiddenNames;
      return this.baseData.map(i=>({ ...i, hidden: hidden.includes(i.name) }));
    },
    totalPages(){
      return Math.max(1, Math.ceil(this.legendItems.length / this.pageSize));
    },
    pagedLegend(){
      const start = (this.currentLegendPage - 1) * this.pageSize;
      return this.legendItems.slice(start, start + this.pageSize);
    }
  },
  data(){
    return { currentLegendPage: 1, hiddenNames: [] };
  },
  watch: {
    list(){ this.currentLegendPage = 1; }
  },
  methods: {
    nextLegendPage(){ if (this.currentLegendPage < this.totalPages) this.currentLegendPage++; },
    prevLegendPage(){ if (this.currentLegendPage > 1) this.currentLegendPage--; },
    toggleLegend(it){
      const idx = this.hiddenNames.indexOf(it.name);
      if (idx >= 0) {
        // 取消隐藏
        this.hiddenNames.splice(idx, 1);
      } else {
        // 防止全部隐藏
        const visibleCount = this.baseData.length - this.hiddenNames.length;
        if (visibleCount <= 1) {
          return uni.showToast({ title: '至少保留一个分类', icon: 'none' });
        }
        this.hiddenNames.push(it.name);
      }
    }
  }
}
</script>
<style scoped>
.legend-pager{ margin-top: 10px; }
.legend-list{ display:flex; flex-wrap:wrap; padding: 0 20px; }
.legend-item{ width:50%; display:flex; align-items:center; margin:4px 0; font-size:12px; color:#333; cursor: pointer; }
.legend-item.is-hidden{ opacity:0.5; }
.dot{ width:12px; height:12px; border-radius:50%; margin-right:6px; }
.legend-controls{ display:flex; align-items:center; justify-content:center; gap:10px; margin-top:6px; }
.btn{ padding:4px 10px; font-size:12px; }
.page-info{ font-size:12px; color:#666; }
</style>