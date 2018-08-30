// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Mall from './Mall'
import router from './router'
import axios from 'axios'
Vue.prototype.axios = axios
import Swiper from 'swiper'
 import VueResource from 'vue-resource'
import'./assets/css/mod.css'
import'./assets/css/swiper.min.css'
import'./assets/layui/css/layui.css'


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
  el: '#mall',
  router,
  components: { Mall },
  template: '<Mall/>'
})
 