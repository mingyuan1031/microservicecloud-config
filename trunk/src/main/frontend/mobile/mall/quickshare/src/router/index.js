import Vue from 'vue'
import Router from 'vue-router'
/*import HelloWorld from '@/components/HelloWorld'*/

import logo from '@/page/logo'
import myshare from '@/page/user/myshare'
import share from '@/page/share/share'
import sharedetails from '@/page/share/sharedetails'
import shareissue from '@/page/share/shareissue'


Vue.use(Router)  

export default new Router({ 

  routes: [ 
  {
      path: '/',
      name: 'share', 
      component: share,
       meta:{
      	title:"快享-新零售"
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
      path: '/user/myshare',
      name: 'myshare', 
      component: myshare,//myshare
       meta:{
      	title:"我的快享-新零售"
      }
    },
    
    
    {
      path: '/share/sharedetails/:shareid',
      name: 'sharedetails', 
      component: sharedetails,
       meta:{
      	title:"用户分享-新零售" 
      }
    },
    {
      path: '/share/shareissue',
      name: 'shareissue', 
      component: shareissue,
       meta:{
      	title:"发布快享-新零售" 
      }
    },
    
    
    //subscribeshow
    
   
  
  ]
})
