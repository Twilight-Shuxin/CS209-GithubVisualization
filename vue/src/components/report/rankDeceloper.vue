<template>
  <div class="com-container">
    <div class="com-chart" ref="rankRef"></div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Rank',
  data() {
    return {
      repoName:"aa",
      chartInstance: null,
      allData: null,
      startValue: 0,
      endValue: 5,
    }
  },
  created() {
  },
  computed: {
    ...mapState(['theme'])
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
    // 初始化图表的方法
    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.rankRef, this.theme)

      const initOption = {
        title: {
          text: '▎Developers Top6',
          left: 20,
          top: 20
        },
        grid: {
          top: '40%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        tooltip: {
          show: true
        },
        xAxis: {
          type: 'category',
          textStyle:{
            fontSize:1
          }
        },
        yAxis: {
          value: 'value'
        },
        series: [
          {
            type: 'bar',
            label: {
              show: true,
              position: 'top',
              color: 'white',
              rotate: 30
            }
          }
        ]
      }
      this.chartInstance.setOption(initOption)
    },
    async getData() {
      const { data: res } = await this.$http.get(`/repo/`+this.repoName+`/contributor`)
      this.allData = res.content
      this.allData.sort((a, b) => b.commitCnt - a.commitCnt)
      this.updateChart()
    },
    updateChart() {
      const colorArr = [
        ['#0BA82C', '#4FF778'],
        ['#2E72BF', '#23E5E5'],
        ['#5052EE', '#AB6EE5']
      ]
      const developerInfo = this.allData.map(item => item.name)
      const valueArr = this.allData.map(item => item.commitCnt)

      const dataOption = {
        xAxis: {
          data: developerInfo
        },
        dataZoom: {
          show: false,
          startValue: this.startValue,
          endValue: this.endValue
        },
        series: [
          {
            data: valueArr,
            itemStyle: {
              color: arg => {
                let targetColorArr = null
                if (arg.value > 300) {
                  targetColorArr = colorArr[0]
                } else if (arg.value > 200) {
                  targetColorArr = colorArr[1]
                } else {
                  targetColorArr = colorArr[2]
                }
                return new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: targetColorArr[0] },
                  { offset: 1, color: targetColorArr[1] }
                ])
              }
            }
          }
        ]
      }
      this.chartInstance.setOption(dataOption)
    },
    screenAdapter() {
      const titleFontSzie = (this.$refs.rankRef.offsetWidth / 100)*3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSzie
          }
        },
        series: [
          {
            barWidth: titleFontSzie,
            itemStyle: {
              barBorderRadius: [titleFontSzie / 2, titleFontSzie / 2, 0, 0]
            }
          }
        ]
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },
  }
}
</script>

<style lang="less" scoped></style>
