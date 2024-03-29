import Vue from 'vue'
import Router from 'vue-router'
import test from '@/pc/test'
import PcIndex from '@/pc/PcIndex/PcIndex'
import testUpload from '@/pc/testUpload'


Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/',
  routes: [
    {
      base: '/',
      path: '/',
      redirect: '/PcIndex',
    },
    {
      base: '/',
      path: '/Pc',
      name: 'Pc',
      component: resolve => require(['@/pc/Pc/Pc.vue'], resolve),
      children: [
        {
          path: '/PcIndex',
          name: 'PcIndex',
          component: resolve => require(['@/pc/PcIndex/PcIndex.vue'], resolve)
        },
        {
          path: '/PcDetail',
          name: 'PcDetail',
          component: resolve => require(['@/pc/PcDetail/PcDetail.vue'], resolve)
        }
      ]
    },
    {
      path: '/PcPreview',
      name: 'PcPreview',
      component: resolve => require(['@/pc/PcPreview/PcPreview.vue'], resolve),
    },
    {
      path: '/test',
      name: 'test',
      component: test
    },
    {
      path: '/testUpload',
      name: 'testUpload',
      component: testUpload
    },
    {
      path: '/testArray',
      name: 'testArray',
      component: resolve => require(['@/pc/testArray.vue'], resolve)
    },
    /*------------------pc 分隔符  mb---------------------*/
  ]
})
