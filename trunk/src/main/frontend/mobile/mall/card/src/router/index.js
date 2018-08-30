import Vue from 'vue'
import Router from 'vue-router'
/*import HelloWorld from '@/components/HelloWorld'*/

import logo from '@/page/logo'
import card from '@/page/mingpian_wm'

Vue.use(Router)  

export default new Router({ 

  routes: [ 
    {
      path: '/',
      name: 'card',
      component: card,
      meta:{
      	title:"商家名片-新零售"
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
    
    
    
    
    //subscribeshow
    
   
  
  ]
})
