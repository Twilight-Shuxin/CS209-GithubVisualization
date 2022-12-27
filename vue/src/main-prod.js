import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import echarts from 'echarts'

import './assets/css/global.less'

import './assets/font/iconfont.css'



Vue.prototype.$http = axios

Vue.prototype.$echarts = echarts
import './assets/lib/theme/chalk'
import './assets/lib/theme/westeros'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
