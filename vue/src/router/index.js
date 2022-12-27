import Vue from 'vue'
import VueRouter from 'vue-router'

import visualization from "views/visualization"
import homePage from "@/views/homePage";
import research from "@/views/research";

Vue.use(VueRouter)

const routes = [{
    path: '/',
    redirect: '/home'
  },
  {
    path: '/visualization',
    component: visualization
  },
  {
    path: '/home',
    component: homePage
  },
  {
    path: "/research",
    component: research
  }
]

const router = new VueRouter({
  routes
})

export default router
