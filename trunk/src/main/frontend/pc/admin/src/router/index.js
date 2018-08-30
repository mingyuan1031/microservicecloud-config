import Vue from 'vue';
import Router from 'vue-router';
import goodslist from '@/components/goods/goodslist';
import upload from '@/components/goods/upload';
import goodssort from '@/components/goods/goodssort';
import Productspecification from '@/components/goods/Productspecification';
import Brandlist from '@/components/goods/Brandlist';
import Commentlist from '@/components/goods/Commentlist';
import orderlist from '@/components/order/Orderlist';
import orderdetail from '@/components/order/orderdetail';
import invoice from '@/components/order/Invoice';
import refund from '@/components/order/Refund';
import returngoods from '@/components/order/Returngoods';
import orderlog from '@/components/order/Orderlog';
import addgoods from '@/components/goods/addgoods/addgoods';
import memberlist from '@/components/members/memberlist';
import mallinformation from '@/components/mall/mallinformation';
import administratorlist from '@/components/administrator/administratorlist';
import membersrank from '@/components/statistical/membersrank';
import salesituation from '@/components/statistical/salesituation';
import saledetails from '@/components/statistical/saledetails';
import reservations from '@/components/appointment/reservations';
import workbench from '@/components/workbench/workbench';
import mallmenu from '@/components/mall/mallmenu';
import newdata from '@/components/news/newdata';
import userinformation from '@/components/userinformation/userinformation';
import layout from '@/components/bone/layout';
import login from '@/components/bone/login';
import quickshare from '@/components/quickshare/quickshare';
Vue.use(Router);
export default new Router({
  routes: [
    {
      path: '/',
      name: 'layout',
      component: layout,
      meta:{keepAlive:true},
      redirect: '/mallinformation',
      children:[
                    { path: '/goodslist', component: goodslist},
                    { path: '/goodssort', component: goodssort},
                    { path: '/workbench', component: workbench,name:'workbench'},
                    {
      path: '/productspecification',
      name: 'productspecification',
      component: Productspecification
    },
    {
      path: '/brandlist',
      name: 'Brandlist',
      component: Brandlist
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
     {
      path: '/newdata',
      name: 'newdata',
      component: newdata
    },
    {
      path: '/quickshare',
      name: 'quickshare',
      component: quickshare
    },
    {
      path: '/Commentlist',
      name: 'Commentlist',
      component: Commentlist
    },
    {
      path: '/orderlist',
      name: 'orderlist',
      component: orderlist
    },
    {
      path: '/orderdetail',
      name: 'orderdetail',
      component: orderdetail
    },
    {
      path: '/invoice',
      name: 'invoice',
      component: invoice
    },
    {
      path: '/refund',
      name: 'refund',
      component: refund
    },
    {
      path: '/returngoods',
      name: 'returngoods',
      component: returngoods
    },
    {
      path: '/orderlog',
      name: 'orderlog',
      component: orderlog
    },
    {
      path: '/addgoods',
      name: 'addgoods',
      component: addgoods
    },
    {
      path: '/memberlist',
      name: 'memberlist',
      component: memberlist
    },
     {
    	path: '/mallinformation',
      name: 'mallinformation',
      component: mallinformation
    },
    {
    	path: '/administratorlist',
      name: 'administratorlist',
      component: administratorlist
    },
    {
    	path: '/membersrank',
      name: 'membersrank',
      component: membersrank
    },
     {
    	path: '/salesituation',
      name: 'salesituation',
      component: salesituation
    },
       {
    	path: '/saledetails',
      name: 'saledetails',
      component: saledetails
    },
     {
    	path: '/reservations',
      name: 'reservations',
      component: reservations
    },
    {
    	path: '/userinformation',
      name: 'userinformation',
      component: userinformation
    },
    {
    	path: '/mallmenu',
      name: 'mallmenu',
      component: mallmenu
    }
     ]
    },
    {
      path: '/upload',
      name: 'upload',
      component: upload
    }
  ]
})
