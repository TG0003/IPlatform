/*
 * @Author: _t
 * @Date: 2022-06-24 13:28:52
 * @LastEditTime: 2022-07-05 23:50:15
 * @LastEditors: _t
 * @Description:
 */
import Vue from 'vue';
import VueRouter from 'vue-router';
import home from '../pages/home.vue';
import aboutMe from '../pages/aboutMe.vue';
import usage from '../pages/usage.vue';
import price from '../pages/price.vue';
import login from '../pages/login.vue';
import register from '../pages/register.vue';
import userInfo from '../pages/userInfo.vue';
import discernDetail from '../pages/discernDetail.vue';
import pay from '../pages/pay.vue';
import payDetail from '../pages/payDetail.vue';
import paySuccess from '../pages/paySuccess.vue';
import testOnline from '../pages/testOnline.vue';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    { path: '/', component: home },
    { path: '/usage', component: usage },
    { path: '/price', component: price },
    { path: '/testOnline', component: testOnline },
    {
      path: '/me',
      component: aboutMe,
      redirect: '/me/userInfo',
      children: [
        { path: 'userInfo', component: userInfo },
        { path: 'discernDetail', component: discernDetail },
        { path: 'pay', component: pay },
        { path: 'payDetail', component: payDetail },
        { path: 'paySuccess', name: 'paySuccess', component: paySuccess },
      ],
    },
    { path: '/login', component: login },
    { path: '/register', component: register },
    { path: '*', redirect: '/' },
  ],
});
export default router;
