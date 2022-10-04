/*
 * @Author: _t
 * @Date: 2022-06-27 00:30:43
 * @LastEditTime: 2022-07-06 00:24:57
 * @LastEditors: _t
 * @Description:
 */
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
const store = new Vuex.Store({
  state() {
    return {
      token: null,
      userInfo: {},
    };
  },
  mutations: {
    changeUserToken(state, token) {
      state.token = token;
    },
    changeUserInfo(state, info) {
      state.userInfo = info;
    },
  },
});
export default store;
