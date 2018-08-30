// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Login from './Login';
import router from './router';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import './common/comon.css';
import echarts from 'echarts';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.prototype.$echarts = echarts; 
Vue.config.productionTip = true
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
  el: '#login',
  router,
  components: { Login },
  template: '<Login/>'
})
