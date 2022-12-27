<template>
  <div class="com-container">
    <div class="com-chart" ref="stockRef"></div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Stock',
  data() {
    return {
      repoName:"",
      chartInstance: null,
      allData: [
        {
          "name":"Open",
          "sales": 0,
          "stock": 0
        },
        {
          "name":"Closed",
          "sales": 0,
          "stock": 0
        }
      ],
      centerArr: [
        ['34%', '60%'],
        ['66%', '60%'],
      ],
      colorArr: [
        ['#4FF778', '#0BA82C'],
        ['#E5DD45', '#E8B11C'],
        ['#E8821C', '#E55445'],
        ['#5052EE', '#AB6EE5'],
        ['#23E5E5', '#2E72BF'],
      ],
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
      this.chartInstance = this.$echarts.init(this.$refs.stockRef, this.theme)
      const initOption = {
        title: {
          text: '▎Open vs Closed Issue',
          left: 20,
          top: 20,
        },
      }
      this.chartInstance.setOption(initOption)
    },
    async getData() {

      const { data: res1 } = await this.$http.get(`/repo/`+this.repoName+`/issue?state=open`)
      this.allData.at(0).sales = res1.totalElements
      this.allData.at(1).stock = res1.totalElements
      const { data: res2 } = await this.$http.get(`/repo/`+this.repoName+`/issue?state=closed`)
      this.allData.at(1).sales = res2.totalElements
      this.allData.at(0).stock = res2.totalElements
      this.updateChart()
    },
    // 更新图表配置项
    updateChart() {
      // 需要显示的原始数据   包含0，不好含5
      const showData = this.allData
      // 真实显示的数据
      let seriesArr = showData.map((item, index) => {
        return {
          type: 'pie',
          center: this.centerArr[index],
          hoverAnimation: false,
          labelLine: {
            show: false,
          },
          label: {
            position: 'center',
            color: this.colorArr[index][0],
          },
          data: [
            {
              name: item.name + '\n\n' + item.sales,
              value: item.sales,
              itemStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 1, 0, 0, [
                  { offset: 0, color: this.colorArr[index][0] },
                  { offset: 1, color: this.colorArr[index][1] },
                ]),
              },
              tooltip: {
                formatter: `${item.name} <br/>Number：{c} <br/>Percentage：{d}%`,
              },
            },
            {
              value: item.stock,
              itemStyle: {
                color: '#bbb',
              },
              tooltip: {
                formatter: `${item.name} <br/>Number：{c} <br>Percentage：{d}%`,
              },
            },
          ],
        }
      })
      const dataOption = {
        tooltip: {
          trigger: 'item',
        },
        series: seriesArr,
      }
      this.chartInstance.setOption(dataOption)
    },
    screenAdapter() {
      const titleFontSize = (this.$refs.stockRef.offsetWidth / 100) * 3.6
      const innerRadius = titleFontSize * 2.8
      const outerRadius = innerRadius * 1.2
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSize,
          },
        },
        series: [
          {
            type: 'pie',
            radius: [outerRadius, innerRadius],
            label: {
              fontSize: titleFontSize / 1.2,
            },
          },
          {
            type: 'pie',
            radius: [outerRadius, innerRadius],
            label: {
              fontSize: titleFontSize / 1.2,
            },
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
</style>
