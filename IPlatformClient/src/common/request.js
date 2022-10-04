/*
 * @Author: _t
 * @Date: 2022-06-26 15:44:07
 * @LastEditTime: 2022-07-06 17:29:03
 * @LastEditors: _t
 * @Description:
 */
import axios from 'axios';
import { Message } from 'element-ui';
import router from '../router/index.route';
import store from '../store/index.store';

const service = axios.create({
  timeout: 60000,
});
service.interceptors.request.use((config) => {
  config.headers.token = localStorage.getItem('token');
  return config;
});
service.interceptors.response.use((response) => {
  const res = response.data;
  if (res.code === 200) {
    const re = /"secret":"(.*?)"/;
    const m = re.exec(JSON.stringify(res));
    if (m) {
      const token = m[1];
      localStorage.setItem('token', token);
      store.commit('changeUserToken', token);
      localStorage.setItem('userInfo', JSON.stringify(res));
      store.commit('changeUserInfo', res);
    }
    return Promise.resolve(res);
  }
  Message.error(res.message);
  if (res.message && res.message.indexOf('token无效') > -1) {
    router.push('/login');
  }
  return Promise.reject(res);
}, (error) => {
  let errMsg = error.message;
  if (error.response) {
    errMsg += `:${error.response.data}`;
  }
  Message.error(errMsg);
  return Promise.reject(error);
});
export default service;
