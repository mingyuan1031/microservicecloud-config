import Vue from 'vue'
import Router from 'vue-router'
/*import HelloWorld from '@/components/HelloWorld'*/
import reservation from '@/page/index'
import logo from '@/page/logo'
import subscribe from '@/page/subscribe'
Vue.use(Router)  

export default new Router({ 

  routes: [ 
    {
      path: '/',
   
      name: 'reservation',
      component: reservation,
      meta:{
      	title:"预约-新零售"
      }
    },
     {
      path: '/logo',
      name: 'logo',
      component: logo,
      meta:{
      	title:"登录"
      }
    },
   
    {
      path: '/user/subscribe',
      name: 'subscribe',
      component: subscribe,
      meta:{
      	title:"我的预约"
      }
    },
    
    //subscribeshow
    
   
  
  ]
})
