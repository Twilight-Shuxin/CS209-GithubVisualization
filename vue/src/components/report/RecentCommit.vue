<template>
  <div className="com-container">
    <div className="com-chart" ref="trendRef"></div>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'RecentCommit',
  data() {
    return {
      repoName:"aa",
      chartInstance: null,
      allData: null,
      showMenu: false,
      activeName: 'map',
      titleFontSize: 0,
      value: ''
    }
  },
  created() {
  },
  computed: {
    ...mapState(['theme']),
  },
  mounted() {
    this.getRepo()
    this.initChart()
    this.getData()
    window.addEventListener('resize', this.screenAdapter)
    this.screenAdapter()
  },
  methods: {
    getRepo(){
      this.repoName=localStorage.getItem("repoName")
    },
    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.trendRef, this.theme)
      const initOption = {
        title: {
          text: '▎Recent Year Commit',
          left: 20,
          top: 20
        },
        grid: {
          left: '3%',
          top: '35%',
          right: '4%',
          bottom: '1%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'center',
          top: '18%',
          icon: 'circle'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false
        },
        yAxis: {
          type: 'value'
        }
      }
      this.chartInstance.setOption(initOption)
    },
    async getData() {
      const {data: res} = await this.$http.get('/trend')
      this.allData = res
      this.updateChart()
    },
    updateChart() {
      const colorArr1 = ['rgba(11, 168, 44, 0.5)', 'rgba(44, 110, 255, 0.5)', 'rgba(22, 242, 217, 0.5)', 'rgba(254, 33, 30, 0.5)', 'rgba(250, 105, 0, 0.5)']
      const commit = ["aa", "bb", "cc"]
      const valueArr = [4, 5, 6]
      const dataOption = {
        xAxis: {
          data: commit
        },
        series: [
          {
            name: "number",
            type: "line",
            stack: "总量",
            color: colorArr1[2],
            data: valueArr,
          },
        ],
      }
      this.chartInstance.setOption(dataOption)
    },
    screenAdapter() {
      this.titleFontSize = (this.$refs.trendRef.offsetWidth / 100) * 3.6
      const adapterOption = {
        legend: {
          itemWidth: this.titleFontSize,
          itemHeight: this.titleFontSize,
          itemGap: this.titleFontSize,
          textStyle: {
            fontSize: this.titleFontSize / 1.3
          }
        }
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },
  }
}
</script>

<style lang="less" scoped>
.title {
  position: absolute;
  left: 50px;
  top: 20px;
  z-index: 999;
  color: white;
  cursor: pointer;

  .before-icon {
    position: absolute;
    left: -20px;
  }

  .title-icon {
    margin-left: 10px;
  }
}
</style>
