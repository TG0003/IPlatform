<!--
 * @Author: _t
 * @Date: 2022-06-30 13:48:43
 * @LastEditTime: 2022-07-06 17:31:26
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <el-result icon="success" :title="orderNo+'支付成功，支付金额：'+payNum+'元'" :subTitle="confData.successTip">
    <template slot="extra">
      <el-button type="primary" @click="back()" size="medium">返回</el-button>
	  <el-button v-if="orderNo" type="success" @click="copyOrderId()" size="medium">复制订单号</el-button>
    </template>
  </el-result>
</template>
<script>
import API from "../common/api.js";
import {PAY_SUCCESS_CONFIG} from '../common/pageConfig.js';
export default {
  data() {
    return {
      confData:{},
      orderNo:null,
      payNum:null
    };
  },
  methods: {
    init(){
        API.getConfig(PAY_SUCCESS_CONFIG).then(resp=>{
            let data = JSON.parse(resp.data);
            this.confData = data;
        }).catch(err=>{});
    },
    back() {
        this.$router.back();
    },
	copyOrderId(){
		if (navigator.clipboard) {
			navigator.clipboard.writeText(this.orderNo);
			this.$message.success("复制成功");
		} else {
			this.$message.error("复制失败，请手动复制");
		}
	}
  },
  mounted() {
    this.init();
    this.orderNo = this.$route.params.orderNo;
    this.payNum = this.$route.params.payNum;
  },
};
</script>