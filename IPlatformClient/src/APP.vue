<!--
 * @Author: _t
 * @Date: 2022-06-24 13:28:52
 * @LastEditTime: 2022-08-18 14:07:36
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="20">
          <span class="leftMenu">
            <el-menu :default-active="activeIndex" router mode="horizontal">
              <el-menu-item>CommCode2.0</el-menu-item>
              <template v-for="item in navMenuData">
                <el-menu-item :index="item.index" :key="item.index">{{
                  item.name
                }}</el-menu-item>
              </template>
            </el-menu>
          </span>
        </el-col>
        <el-col :span="4">
          <span class="rightMenu">
            <el-menu
              class="lg"
              v-if="!isLogin"
              :default-active="activeIndex2"
              router
              mode="horizontal"
            >
              <template v-for="item in userNavMenuData">
                <el-menu-item :index="item.index" :key="item.index">{{
                  item.name
                }}</el-menu-item>
              </template>
            </el-menu>
            <user-me v-else></user-me>
          </span>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>
<script>
import $ from "jquery";
import userMe from "./component/userMe.vue";
import API from "./common/api.js";
import { APP_CONFIG } from "./common/pageConfig.js";
export default {
  name: "commCode2Client",
  components: {
    userMe,
  },
  data() {
    return {
      activeIndex: "/", //组件属性，不是该页面属性，会从该页面传递属性到组件处理
      activeIndex2: null,
      navMenuData: [
        { index: "/", name: "" },
        { index: "/usage", name: "" },
        { index: "/price", name: "" },
        { index: "/testOnline", name: "" },
        { index: "/me", name: "" },
      ],
      userNavMenuData: [
        { index: "/login", name: "登录" },
        { index: "/register", name: "注册" },
      ],
    };
  },
  computed: {
    isLogin() {
      this.$store.commit("changeUserToken", localStorage.getItem("token")); //解决同步延时问题（刷新同步响应慢点）
      return this.$store.state.token;
    },
  },
  watch: {
    "$route.path": function (newVal, oldVal) {
      this.handleRoutePathChange();
    },
  },
  methods: {
    init() {
      API.getConfig(APP_CONFIG)
        .then((resp) => {
          let data = JSON.parse(resp.data);
          this.navMenuData.forEach((v, i) => {
            v.name = data.title[i];
          });
          if (data.bulletin) {
            this.$alert(data.bulletin.content, data.bulletin.title, {
              confirmButtonText: "收到",
              callback: (action) => {},
            });
          }
        })
        .catch((err) => {});
    },
    remActive() {
      let ac = $(".el-header .is-active");
      ac.removeClass("is-active");
      ac.blur(); //焦点不退问题
    },
    handleRoutePathChange() {
      this.remActive();
      let pathName = this.$route.path;
      //已登录
      if (
        this.isLogin &&
        this.userNavMenuData.some((v) => v.index === pathName)
      ) {
        //默认导航
        this.activeIndex = "/";
        this.$router.push("/");
      } else {
        let i = -1;
        let y = -1;
        if (
          this.navMenuData.some((v) => {
            i++;
            if (pathName === v.index) {
              return true;
            }
            return v.index !== "/" && pathName.indexOf(v.index) > -1;
          })
        ) {
          this.activeIndex = this.navMenuData[i].index;
          $($(".leftMenu .el-menu-item")[i + 1]).addClass("is-active"); //不会自动添加active样式问题，尽管index已经指向
        } else if (
          this.userNavMenuData.some((v) => {
            y++;
            return v.index === pathName;
          })
        ) {
          this.activeIndex2 = pathName;
          $($(".rightMenu .el-menu-item")[y]).addClass("is-active");
        }
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
.el-header {
  background-color: white;
  padding: 0px;
}
.rightMenu {
  float: right;
}
.rightMenu .el-menu-item.is-active {
  border-bottom: 0;
}
.rightMenu .el-menu-item {
  -webkit-transition: none;
  transition: none;
}
</style>