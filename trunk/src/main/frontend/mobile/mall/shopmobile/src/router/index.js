import Vue from 'vue'
import Router from 'vue-router'
/*import HelloWorld from '@/components/HelloWorld'*/
import index from '@/page/index'
import logo from '@/page/logo'
import trolley from '@/page/trolley'
import trolleytwo from '@/page/trolleytwo'
import user from '@/page/user'
import all from '@/page/all' 
import goodstypeall from '@/page/goodstypeall' 

/*import seekall from '@/page/seekall' */
import seek from '@/page/seek'
import gooditem from '@/page/good/gooditem'
import usertalk from '@/page/good/usertalk'
/*orderdetails*/
//paymenthistory
import  paymenta from '@/page/payment/paymenta'
import  orderpaymenta from '@/page/payment/orderpaymenta'
import  goodpaymenta from '@/page/payment/goodpaymenta'
import  succeed from '@/page/payment/succeed'
import  loser from '@/page/payment/loser'
import  site from '@/page/user/site'
import  addsite from '@/page/user/addsite'
import  amend from '@/page/user/amend'

import  ordersite from '@/page/user/ordersite'
import  orderaddsite from '@/page/user/orderaddsite'
import  orderamend from '@/page/user/orderamend'

import order from '@/page/user/order'
import orderdetails from '@/page/user/orderdetails'
import commentend from '@/page/user/comment'
import subscribe from '@/page/user/subscribe'
import paymenthistory from '@/page/user/paymenthistory'
import usermsg from '@/page/user/usermsg'
import exploit from '@/page/user/exploit'
import card from '@/page/user/mingpian_wm'
import mapitem from '@/page/user/shangjia_map'
import myshare from '@/page/user/myshare'
import mistake from '@/page/error/mistake'
import nointernet from '@/page/error/nointernet'

import share from '@/page/share/share'
import sharedetails from '@/page/share/sharedetails'
import shareissue from '@/page/share/shareissue'

import videoshow from '@/page/module/video'
import subscribeshow from '@/page/module/subscribeshow'
import join from '@/page/module/join'
import brandstory from '@/page/module/brandStory'


Vue.use(Router)  

export default new Router({ 
	base:'/?#',
  routes: [ 
    {
      path: '/',
      name: 'index',
      component: index,
      meta:{
      	title:"首页"
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
      path: '/trolley',
      name: 'trolley',
      component: trolley,
       meta:{
      	title:"购物车",
      	
      }
    },
    {
      path: '/trolleytwo',
      name: 'trolleytwo',
      component: trolleytwo,
       meta:{
      	title:"购物车two"
      }
    },
    {
      path: '/user',
      name: 'user', 
      component: user,
       meta:{
      	title:"个人中心"
      }
    },
    {
      path: '/all/:name',
      name: 'all', 
      component: all,
       meta:{
      	title:"搜索-全部"
      }
    },
    
    {
      path: '/goodstypeall',
      name: 'goodstypeall', 
      component: goodstypeall,
       meta:{
      	title:"搜索-分类"
      }
    },
    
   
    
    
    {
      path: '/seek',
      name: 'seek', 
      component: seek,
       meta:{
      	title:"搜索"
      }
    },
     {
      path: '/gooditem/:gooditemid',
      name: 'gooditem', 
      component: gooditem,
       meta:{
      	title:"商品详情" //goodpaymenta
      }
    },
    
    {
      path: '/goodpaymenta',
      name: 'goodpaymenta', 
      component: goodpaymenta,
       meta:{
      	title:"确认订单", //
      /*	keepAlive:false,*/
      }
    },
    
     {
      path: '/succeed',
      name: 'succeed', 
      component: succeed,
       meta:{
      	title:"支付成功提醒", //
      /*	keepAlive:false,*/
      }
    },
    
    {
      path: '/loser',
      name: 'loser', 
      component: loser,
       meta:{
      	title:"支付结果提醒", //
      /*	keepAlive:false,*/
      }
    },
    
    {
      path: '/usertalk/:goodid',
      name: 'usertalk', 
      component: usertalk,
      meta:{
      title:"商品评论"
      }
    },
    { 
      path: '/paymenta', 
       base: '/?',
      name: 'paymenta', 
    
      component: paymenta,
       meta:{
      	title:"支付详情"
      }
    },
    {
      path: '/user/site',
      name: 'site', 
      component: site,
       meta:{
      	title:"地址管理"
      }
    },
    
    {
      path: '/user/ordersite',
      name: 'ordersite', 
      component: ordersite,
       meta:{
      	title:"地址管理"
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
    {
      path: '/user/paymenthistory',
      name: 'paymenthistory', 
      component: paymenthistory,
       meta:{ 
      	title:"支付记录"
      }
    },
    {
      path: '/user/usermsg',
      name: 'usermsg', 
      component: usermsg,
       meta:{
      	title:"个人资料"
      }
    },
    {
      path: '/user/myshare',
      name: 'myshare', 
      component: myshare,//myshare
       meta:{
      	title:"我的快享"
      }
    },
    {
      path: '/user/siet/addsite',
      name: 'addsite', 
      component: addsite,
       meta:{
      	title:"添加地址",
      	
      }
    },
     {
      path: 'amend/:siteid',
      name: 'amend', 
      component: amend,
       meta:{
      	title:"修改地址",
      	 
      }
    },
    {
      path: '/user/orderaddsite/addsite',
      name: 'orderaddsite', 
      component: orderaddsite,
       meta:{
      	title:"订单添加地址",
      	
      }
    },
     {
      path: 'orderamend/:siteid',
      name: 'orderamend', 
      component: orderamend,
       meta:{
      	title:"订单修改地址",
      	 
      }
    },
    
    
    {
      path: '/order/:orderid',
      name: 'order', 
      component: order,
       meta:{
      	title:"订单管理"
      }
    },
    {
      path: '/orderpaymenta',
      name: 'orderpaymenta', 
      component: orderpaymenta,
       meta:{
      	title:"订单支付"//orderpaymenta
      }
    },
    {
      path: '/orderdetails/:ordergoodsid',
      name: 'orderdetails', 
      component: orderdetails,
       meta:{
      	title:"订单详情"
      }
    },
    
    {
      path: '/commentend',
      name: 'commentend', 
      component: commentend,
       meta:{
      	title:"订单评论"
      }
    },
    {
      path: '/exploit',
      name: 'exploit', 
      component: exploit,
       meta:{
      	title:"开发提示"
      }
    },
    {
      path: '/card',
      name: 'card', 
      component: card,
       meta:{
      	title:"商品名片"
      }
    },
     {
      path: '/mapitem',
      name: 'mapitem', 
      component: mapitem,
       meta:{
      	title:"商家地图"
      }
    },
    {
      path: '/mistake',
      name: 'mistake', 
      component: mistake,
       meta:{
      	title:"404页面不存在"
      }
    },
     {
      path: '/nointernet',
      name: 'nointernet', 
      component: nointernet,
       meta:{
      	title:"网络错误"
      }
    },
    {
      path: '/share',
      name: 'share', 
      component: share,
       meta:{
      	title:"快享"
      }
    },
    {
      path: '/share/sharedetails/:shareid',
      name: 'sharedetails', 
      component: sharedetails,
       meta:{
      	title:"用户分享" 
      }
    },
    {
      path: '/share/shareissue',
      name: 'shareissue', 
      component: shareissue,
       meta:{
      	title:"发布快享" 
      }
    },
    {
      path: '/videoshow',
      name: 'videoshow', 
      component: videoshow,
       meta:{
      	title:"可视" 
      }
    },

    {
      path: '/subscribeshow',
      name: 'subscribeshow', 
      component: subscribeshow,
       meta:{
      	title:"预约" 
      }
    },
    
    {
      path: '/join',
      name: 'join', 
      component: join,
       meta:{
      	title:"加入我们" 
      }
    },
    
    {
      path: '/brandstory',
      name: 'brandstory', 
      component: brandstory,
       meta:{
      	title:"品牌故事" 
      }
    },
    
    //
    
    
   
  
   
  
  ]
})
