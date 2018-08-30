// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Admin from './Admin';
import router from './router';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import './common/comon.css';
import echarts from 'echarts';
import axios from 'axios';
import VueAxios from 'vue-axios';
require('bootstrap');
import 'bootstrap/dist/js/bootstrap.bundle.min'
import 'summernote'
import 'summernote/dist/lang/summernote-zh-CN'
import 'summernote/dist/summernote.css'
import VueSummernote from 'vue-summernote'
Vue.prototype.$echarts = echarts; 
Vue.config.productionTip = true
/*Vue.use(VueSummernote, {
  dialogsFade: true
})*/
Vue.use(iView);
Vue.use(VueAxios,axios);
router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    next();
});

let _ = require("lodash");
router.afterEach(route => {
    iView.LoadingBar.finish();
});
/* eslint-disable no-new */
new Vue({
  el: '#admin',
  router,
  components: { Admin },
  template: '<Admin/>'
})

//预加载数据

