<template>
  <div class="com-container">
    <div class="com-chart" ref="hotRef"></div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Hot',
  data() {
    return {
      repoName:"",
      chartInstance: null,
      allData: null,
      currentIndex: 0,
      titleFontSize: null,
    }
  },
  created() {
    this.getData()
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
  destroyed() {
    window.removeEventListener('resize', this.screenAdapter)
  },
  methods: {
    getRepo(){
      this.repoName=localStorage.getItem("repoName")
    },
    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.hotRef, this.theme)
      const initOption = {
        title: {
          text: '▎Commit By Day of The Week',
          left: 20,
          top: 20,
        },
        legend: {
          top: '15%',
          icon: 'circle',
        },
        series: [
          {
            type: 'pie',
            label: {
              show: true,
              formatter:`{b}{d}%`
            },
            emphasis: {
              labelLine: {
                show: true,
              },
            },
          },
        ],
      }
      this.chartInstance.setOption(initOption)
    },
    async getData() {
      const { data: res } = await this.$http.get(`/repo/`+this.repoName+`/weekly_commit_summary`)
      this.allData = res.records
      this.updateChart()
    },
    updateChart() {
      const legenDateArr = this.allData.map(item => item.weekday)
      const seriesDataArr = this.allData
      const dataOption = {
        legend: {
          data: legenDateArr,
        },
        series: [
          {
            data: seriesDataArr,
          },
        ],
      }
      this.chartInstance.setOption(dataOption)
    },
    screenAdapter() {
      this.titleFontSize = (this.$refs.hotRef.offsetWidth / 100) * 3.6

      const adapterOption = {
        title: {
          textStyle: {
            fontSize: this.titleFontSize,
          },
        },
        legend: {
          itemWidth: this.titleFontSize,
          itemHeight: this.titleFontSize,
          itemGap: this.titleFontSize / 2,
          textStyle: {
            fontSize: this.titleFontSize / 1.2,
          },
        },
        series: [
          {
            radius: this.titleFontSize * 4.5,
            center: ['50%', '60%'],
          },
        ],
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },
  },
}
</script>

<style lang="less" scoped>
.com-container {
  i {
    z-index: 999;
    position: absolute;
    transform: translateY(-50%);
    top: 50%;
    cursor: pointer;
  }
  i.icon-left {
    left: 10%;
  }
  i.icon-right {
    right: 10%;
  }
  .cate-name {
    position: absolute;
    right: 10%;
    bottom: 20px;
    z-index: 999;
  }
}
</style>
