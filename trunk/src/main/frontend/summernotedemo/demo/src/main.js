// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'bootstrap/dist/js/bootstrap.bundle.min'
import 'bootstrap/dist/css/bootstrap.css'
import 'font-awesome/css/font-awesome.css'
import 'summernote'
import 'summernote/dist/lang/summernote-zh-CN'
import 'summernote/dist/summernote.css'
//import VueSummernote from 'vue-summernote'
require('bootstrap');
require('bootstrap/dist/css/bootstrap.min.css');
require('summernote/dist/summernote.css');
Vue.config.productionTip = false
/*Vue.use(VueSummernote, {
  dialogsFade: true
})*/
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
