import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import echarts from 'echarts'
import ElementUI from 'element-ui'
import JsonViewer from 'vue-json-viewer';
Vue.use(JsonViewer);
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)

import './assets/css/global.less'

import './assets/font/iconfont.css'

axios.defaults.baseURL = 'http://127.0.0.1:2345'

Vue.prototype.$http = axios

Vue.prototype.$echarts = echarts
import './assets/lib/theme/chalk'
import './assets/lib/theme/vintage'
import './assets/lib/theme/westeros'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
