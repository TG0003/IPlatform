<!--
 * @Author: wanghuayu
 * @Date: 2022-01-09 13:00:02
 * @LastEditTime: 2022-07-06 17:32:11
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <div class="notice">
    <el-alert type="warning" show-icon :closable="false">
      <template slot="title">
        <div class="iconSize">{{ notice.title }}</div>
        <template v-for="item in notice.repeatTimes">
          <div class="iconSize" :key="item">
            {{ item + "：" + notice.msg }}
          </div>
        </template>
      </template>
    </el-alert>
    <el-button type="primary" @click="toUsage()" style="margin-top: 10px" plain
      >我已了解，立即接入</el-button
    >
    <div class="divider">
      <el-divider content-position="center"
        ><span class="dividerTitle">我们的优势</span></el-divider
      >
    </div>
    <el-row class="adventage">
      <el-col
        :span="3"
        v-for="(item, index) in ourAdventages"
        :key="index"
        :offset="index === 0?0:(index % 6 === 0?0:1)"
      >
        <el-card
          class="card"
          shadow="hover"
          :body-style="{ padding: '0px', height: '180px' }"
        >
          <img :src="item.imageSrc+'?t='+Math.random()" class="image" />
          <div style="padding: 5px">
            <span class="adventageTitle">{{ item.title }}</span>
            <div class="bottom clearfix">
              <span class="adventageDesc">{{ item.desc }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import API from "../common/api.js";
import { HOME_CONFIG } from "../common/pageConfig.js";
export default {
  data() {
    return {
      ourAdventages: [],
      notice: {},
    };
  },
  methods: {
    init() {
      API.getConfig(HOME_CONFIG)
        .then((resp) => {
          let data = JSON.parse(resp.data);
          this.notice = data.notice;
		  this.ourAdventages = data.ourAdventages;
        })
        .catch((err) => {
        });
    },
    toUsage() {
      this.$router.push("/usage");
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style scoped>
.iconSize {
  font-size: 16px;
  padding: 10px;
  font-weight: bold;
}

.image {
  width: 100%;
  height: 110px;
  display: block;
}
.divider {
  margin: 30px 0;
}
.bottom {
  margin-top: 8px;
  line-height: 12px;
}
.dividerTitle {
  font-weight: bold;
  font-size: 18px;
}
.adventage .adventageTitle {
  font-weight: bold;
}
.adventage .adventageDesc {
  font-size: 13px;
  color: #999;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.el-col {
  margin-bottom: 15px;
}
</style>
