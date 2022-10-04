<!--
 * @Author: _t
 * @Date: 2022-06-30 13:58:58
 * @LastEditTime: 2022-07-06 17:31:58
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <div>
    <el-alert
      :title="confData.mark && confData.mark.title"
      type="success"
      :description="confData.mark && confData.mark.msg"
    >
    </el-alert>

    <el-table :data="confData.captchaTypeList || []" stripe border style="width: 100%">
      <el-table-column prop="type" label="类型"></el-table-column>
      <el-table-column prop="typeID" label="类型ID"></el-table-column>
      <el-table-column prop="desc" label="描述"> </el-table-column>
      <el-table-column prop="price" label="价格"> </el-table-column>
    </el-table>
  </div>
</template>
<script>
import API from "../common/api.js";
import {PRICE_CONFIG} from '../common/pageConfig.js';
export default {
  data() {
    return {
      confData: {}
    };
  },
  methods: {
    init(){
        API.getConfig(PRICE_CONFIG).then(resp=>{
            let data = JSON.parse(resp.data);
            this.confData = data;
        }).catch(err=>{});
    },
  },
  mounted() {
    this.init();
  },
};
</script>