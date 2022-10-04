/*
 * @Author: _t
 * @Date: 2022-06-29 15:46:06
 * @LastEditTime: 2022-07-06 00:24:10
 * @LastEditors: _t
 * @Description:
 */

function padLeftZero(str) {
  return (`00${str}`).substr(str.length);
}
const formatDate = (date, fmt) => {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (`${date.getFullYear()}`).substr(4 - RegExp.$1.length));
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
  };
  for (const k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      const str = `${o[k]}`;
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
    }
  }
  return fmt;
};
const tableDateFormat = (row, column, cellValue, index) =>
  formatDate(new Date(cellValue), 'yyyy-MM-dd hh:mm:ss');
export default (Vue) => {
  // 添加全局API
  Vue.prototype.$dateUtils = {
    formatDate,
    tableDateFormat,
  };
};
