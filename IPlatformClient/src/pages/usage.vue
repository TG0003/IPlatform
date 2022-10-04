<!--
 * @Author: wanghuayu
 * @Date: 2022-01-09 13:23:57
 * @LastEditTime: 2022-07-17 00:34:08
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <span class="cardHeaderText">HTTP</span>
      </div>
      <span>参数说明</span>
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item v-for="(item,index) in confData.paramExplains" :key="item.type" :title="item.type" :name="index">
          <el-table
            :data="item.explain || []"
            stripe
            border
          >
            <el-table-column prop="paramName" label="参数名"></el-table-column>
            <el-table-column prop="isNeed" label="是否必须"></el-table-column>
            <el-table-column prop="desc" label="说明"> </el-table-column>
          </el-table>
        </el-collapse-item>
      </el-collapse>
      <span>接入示例</span>
      <pre class="codeStyle">
    <code class="codeStyle">
{{confData.useDemoCode}}
    </code>
  </pre>
      <span>返回示例</span>
      <pre class="codeStyle">
    <code class="codeStyle">
{{confData.retDemoCode}}
    </code>
  </pre>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <span class="cardHeaderText">各语言示例</span>
      </div>
    </el-card>
  </div>
</template>
<script>
import API from "../common/api.js";
import { USAGE_CONFIG } from "../common/pageConfig.js";
export default {
  data() {
    return {
      activeName: 0,
      confData: {},
    };
  },
  methods: {
    init() {
      API.getConfig(USAGE_CONFIG)
        .then((resp) => {
          let data = JSON.parse(resp.data);
          this.confData = data;
        })
        .catch((err) => {});
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style scoped>
.text {
  font-size: 14px;
}
.box-card {
  margin-bottom: 13px;
}
.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.cardHeaderText {
  font-weight: bold;
}
.codeStyle {
  background-color: #282a36;
  color: #f8f8f2;
  padding: 0.5em;
  overflow-x: auto;
  display: block;
}
.el-collapse {
  margin-top: 13px;
  margin-bottom: 13px;
}
</style>