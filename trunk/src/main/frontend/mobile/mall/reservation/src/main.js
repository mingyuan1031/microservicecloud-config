// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Reservation from './Reservation'
import router from './router'
import axios from 'axios'
Vue.prototype.axios = axios

 import VueResource from 'vue-resource'; 
require ('./assets/css/mod.css')
require ('./assets/css/swiper.min.css')
require ('./assets/layui/css/layui.css')


Vue.use(VueResource)
 
Vue.config.productionTip = false

router.beforeEach((to, form, next) => {
	if(to.meta.title){
	
		document.title=to.meta.title
	}
	
	next()
})

/* eslint-disable no-new */
new Vue({
  el: '#reservation',
  router,
  components: { Reservation },
  template: '<Reservation/>'
})
 