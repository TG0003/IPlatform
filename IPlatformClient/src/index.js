/*
 * @Author: why
 * @Date: 2022-01-08 21:29:54
 * @LastEditTime: 2022-07-06 00:24:29
 * @LastEditors: _t
 * @Description:
 */
import Vue from 'vue';
import ElementUI from 'element-ui';
import APP from './APP.vue';
import router from './router/index.route';
import store from './store/index.store';
import dateUtils from './util/dateUtils';

Vue.use(ElementUI);
Vue.use(dateUtils);
new Vue({
  el: '#app',
  router,
  store,
  render: (h) => h(APP),
});
