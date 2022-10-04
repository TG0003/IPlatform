/*
 * @Author: _t
 * @Date: 2022-06-26 15:43:56
 * @LastEditTime: 2022-07-06 00:25:51
 * @LastEditors: _t
 * @Description:
 */
import qs from 'qs';
import request from './request.js';

const API = {
  login(data) {
    return request({
      url: '/login',
      method: 'post',
      data: qs.stringify(data),
    });
  },
  register(data) {
    return request({
      url: '/register',
      method: 'post',
      data: qs.stringify(data),
    });
  },
  getConfig(confName) {
    return request({
      url: '/getConfig',
      method: 'get',
      params: { configName: confName },
    });
  },
  refreshSecret() {
    return request({
      url: '/refreshSecret',
      method: 'post',
    });
  },
  getUserInfo() {
    return request({
      url: '/userInfo',
      method: 'post',
    });
  },
  pay(data) {
    return request({
      url: '/pay',
      method: 'post',
      data: qs.stringify(data),
    });
  },
  getPayList(data) {
    return request({
      url: '/payList',
      method: 'post',
      data: qs.stringify(data),
    });
  },
  getDiscernList(data) {
    return request({
      url: '/discernList',
      method: 'post',
      data: qs.stringify(data),
    });
  },
};
export default API;
