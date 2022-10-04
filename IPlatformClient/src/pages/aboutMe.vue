<!--
 * @Author: _t
 * @Date: 2022-06-24 13:28:52
 * @LastEditTime: 2022-07-06 17:31:00
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <el-container style="border: 1px solid #eee">
    <el-aside style="height:300px" class="abMe" width="150px">
      <el-row>
        <el-col>
          <el-menu :default-active="activeIndex" router>
            <template v-for="item in navData">
              <el-menu-item :index="item.index" :key="item.index">
                <span slot="title">{{ item.name }}</span>
              </el-menu-item>
            </template>
          </el-menu>
        </el-col>
      </el-row>
    </el-aside>
    <el-main><router-view></router-view></el-main>
  </el-container>
</template>
<script>
import API from "../common/api.js";
import {ABOUT_ME_CONFIG} from '../common/pageConfig.js';
export default {
  data() {
    return {
      activeIndex: "/me/userInfo",
      navData: [
        { index: "/me/userInfo", name: "" },
        { index: "/me/discernDetail", name: "" },
        { index: "/me/pay", name: "" },
        { index: "/me/payDetail", name: "" }
      ],
    };
  },
  watch: {
    "$route.path"(newVal, oldVal) {
      this.handleRoutePathChange();
    },
  },
  methods: {
    init(){
        API.getConfig(ABOUT_ME_CONFIG).then(resp=>{
            let data = JSON.parse(resp.data);
            this.navData.forEach((v,i)=>{
                v.name = data.menuTitle[i];
            });
        }).catch(err=>{});
    },
    handleRoutePathChange() {
      let pathName = this.$route.path;
      if (this.navData.some((v) => v.index === pathName)) {
        this.activeIndex = pathName;
      }
    },
  },
  mounted() {
    this.init();
    this.handleRoutePathChange();
  },
};
</script>
<style scoped>
</style>