<template>
  <div class="screen-container" :style="containerStyle">
    <header class="screen-header">
      <span class="title" v-text="repoName"></span>
    </header>
    <div class="screen-body">
      <section class="screen-left">
        <div id="left-top" :class="{ fullscreen: fullScreenStatus.trend }">
          <el-row>
            <el-header class="variable1">Total Developer: </el-header>
            <el-header class="variable2" v-text="dataForm.developerNumber"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Total Release: </el-header>
            <el-header class="variable2" v-text="dataForm.releaseNumber"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Open Issue Number: </el-header>
            <el-header class="variable2" v-text="dataForm.openIssueNumber"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Closed Issue Number: </el-header>
            <el-header class="variable2" v-text="dataForm.closedIssueNumber"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Average Issue Solving Time: </el-header>
            <el-header class="variable2" v-text="dataForm.issueAvg"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Max Issue Solving Time: </el-header>
            <el-header class="variable2" v-text="dataForm.issueTimeMax"></el-header>
          </el-row>
          <el-row>
            <el-header class="variable1">Min Issue Solving Time: </el-header>
            <el-header class="variable2" v-text="dataForm.issueTimeMin"></el-header>
          </el-row>
          </div>
      </section>
      <section class="screen-middle">
        <div id="middle-top" :class="{ fullscreen: fullScreenStatus.map }">
          <Trend ref="trend"></Trend>
          <div class="resize">
            <span @click="changeSize('trend')" :class="['iconfont', fullScreenStatus.trend ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
        <div id="middle-middle" :class="{ fullscreen: fullScreenStatus.rank }">
          <Rank ref="rank"></Rank>
          <div class="resize">
            <span @click="changeSize('rank')" :class="['iconfont', fullScreenStatus.rank ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
        <div id="middle-bottom" :class="{ fullscreen: fullScreenStatus.trend2 }">
          <!-- 商家分布图表 -->
          <Trend2 ref="trend2"></Trend2>
          <div class="resize">
            <span @click="changeSize('trend2')" :class="['iconfont', fullScreenStatus.trend2 ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
      </section>
      <section class="screen-right">
        <div id="right-top" :class="{ fullscreen: fullScreenStatus.hot }">
          <Hot ref="hot"></Hot>
          <div class="resize">
            <span @click="changeSize('hot')" :class="['iconfont', fullScreenStatus.hot ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
        <div id="right-middle" :class="{ fullscreen: fullScreenStatus.stock }">
          <Stock ref="stock"></Stock>
          <div class="resize">
            <span @click="changeSize('stock')" :class="['iconfont', fullScreenStatus.stock ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
        <div id="right-bottom" :class="{ fullscreen: fullScreenStatus.cloud }">
          <Cloud ref="stock"></Cloud>
          <div class="resize">
            <span @click="changeSize('cloud')" :class="['iconfont', fullScreenStatus.cloud ? 'icon-compress-alt' : 'icon-expand-alt']"></span>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import Hot from '@/components/report/Hot.vue'
import Rank from '@/components/report/rankDeceloper.vue'
import Stock from '@/components/report/Stock.vue'
import Trend from '@/components/report/Trend.vue'
import RecentCommit from "@/components/report/RecentCommit"
import Trend2 from "@/components/report/Trend2";
import Cloud from "@/components/report/Cloud";

import { mapState } from 'vuex'
import { getThemeValue } from 'utils/theme_utils'

export default {
  name: 'ScreenPage',
  components: {
    RecentCommit,
    Hot,
    Rank,
    Stock,
    Trend,
    Trend2,
    Cloud
  },
  data() {
    return {
      repoName:"aa",
      fullScreenStatus: {
        trend: false,
        seller: false,
        map: false,
        rank: false,
        hot: false,
        stock: false,
        commit:false,
        trend2:false,
        cloud:false
      },
      dataForm:{
        developerNumber:"100",
        releaseNumber:"12",
        openIssueNumber:"50",
        closedIssueNumber:"30",
        issueAvg:"0 years 0 mons 35 days 11 hours 0 mins 0.912991 secs",
        issueTimeMax:"0 years 0 mons 35 days 11 hours 0 mins 0.912991 secs",
        issueTimeMin:"0 years 0 mons 35 days 11 hours 0 mins 0.912991 secs",
      }
    }
  },
  mounted() {
    this.getRepo()
    this.getData()
  },
  created() {
    this.currentTime()
  },
  computed: {
    ...mapState(['theme']),
    containerStyle() {
      return {
        backgroundColor: getThemeValue(this.theme).backgroundColor,
        color: getThemeValue(this.theme).titleColor,
      }
    },
    titleColor() {
      return {
        color: getThemeValue(this.theme).titleColor,
      }
    },
  },
  methods: {
    getRepo(){
      this.repoName=localStorage.getItem("repoName")
    },
    async getData() {
      const { data: res1 } = await this.$http.get(`/repo/`+this.repoName+`/contributor`)
      this.dataForm.developerNumber = res1.totalElements
      const { data: res2 } = await this.$http.get(`/repo/`+this.repoName+`/release`)
      this.dataForm.releaseNumber = res2.totalElements
      const { data: res3 } = await this.$http.get(`/repo/`+this.repoName+`/issue?state=open`)
      this.dataForm.openIssueNumber = res3.totalElements
      const { data: res4 } = await this.$http.get(`/repo/`+this.repoName+`/issue?state=closed`)
      this.dataForm.closedIssueNumber = res4.totalElements
      const { data: res5 } = await this.$http.get(`/repo/`+this.repoName+`/avg_resolve_time`)
      this.dataForm.issueAvg = res5.value
      const { data: res6 } = await this.$http.get(`/repo/`+this.repoName+`/max_resolve_time`)
      this.dataForm.issueTimeMax = res6.value
      const { data: res7 } = await this.$http.get(`/repo/`+this.repoName+`/min_resolve_time`)
      this.dataForm.issueTimeMin = res7.value
    },
    changeSize(chartName) {
      this.fullScreenStatus[chartName] = !this.fullScreenStatus[chartName]
      this.$nextTick(() => {
        this.$refs[chartName].screenAdapter()
      })
    },
  },
}
</script>
<style lang="less" scoped>
// 全屏样式的定义
.fullscreen {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
  margin: 0 !important;
  z-index: 9999;
}

.screen-container {
  width: 100%;
  height: 100%;
  padding: 0 20px;
  background-color: #161522;
  color: #fff;
  box-sizing: border-box;
}
.screen-header {
  width: 100%;
  height: 64px;
  font-size: 20px;
  position: relative;
  > div {
    img {
      width: 100%;
    }
  }
  .title {
    position: absolute;
    left: 50%;
    top: 50%;
    font-size: 20px;
    transform: translate(-50%, -50%);
  }
  .title-right {
    display: flex;
    align-items: center;
    position: absolute;
    right: 0px;
    top: 50%;
    transform: translateY(-80%);
  }
  .qiehuan {
    width: 28px;
    height: 21px;
    cursor: pointer;
  }
  .datetime {
    font-size: 15px;
    margin-left: 10px;
  }
  .logo {
    position: absolute;
    left: 0px;
    top: 50%;
    transform: translateY(-80%);
    a {
      text-decoration: none;
    }
  }
}
.screen-body {
  width: 100%;
  height: 100%;
  display: flex;
  margin-top: 10px;
  .screen-left {
    height: 100%;
    width: 27.6%;
    #left-top {
      height: 53%;
      position: relative;
    }
    #left-bottom {
      height: 31%;
      margin-top: 25px;
      position: relative;
    }
  }
  .screen-middle {
    height: 100%;
    width: 41.5%;
    margin-left: 1.6%;
    margin-right: 1.6%;
    #middle-top  {
      width: 100%;
      height: 30%;
      position: relative;
    }
    #middle-middle {
      margin-top: 25px;
      width: 100%;
      height: 23%;
      position: relative;
    }
    #middle-bottom {
      margin-top: 25px;
      width: 100%;
      height: 30%;
      position: relative;
    }
  }
  .screen-right {
    height: 100%;
    width: 27.6%;
    #right-top {
      height: 25%;
      position: relative;
    }
    #right-middle {
      height: 20%;
      margin-top: 25px;
      position: relative;
    }
    #right-bottom {
      height: 30%;
      margin-top: 25px;
      position: relative;
    }
  }
}
.resize {
  position: absolute;
  right: 20px;
  top: 20px;
  cursor: pointer;
}
</style>

<style scoped>

.variable1 {
  display: inline-block;
  position: center;
  top: -5px;
  font-size: 20px;
  font-weight: 600;
}

.variable2 {
  display: inline-block;
  position: center;
  font-size: 18px;
  font-weight: 300;
}

</style>
