<!--
 * @Author: _t
 * @Date: 2022-06-29 13:44:12
 * @LastEditTime: 2022-07-06 17:30:35
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <el-descriptions class="margin-top" :column="2" border>
    <el-descriptions-item>
      <template slot="label"> 用户名 </template>
      {{ userInfo.username }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label"> 秘钥 </template>
      <el-tooltip
        class="item"
        effect="dark"
        content="接口调用凭证，请妥善保管，切勿泄露，最好定时刷新"
        placement="top-start"
      >
        <span>{{ userInfo.secret }}</span>
      </el-tooltip>
      <el-link
        :underline="false"
        @click="copySecret()"
        title="复制"
        style="margin-left: 10px"
        class="el-icon-document-copy"
      ></el-link>
      <el-link
        :underline="false"
        @click="refreshSecret()"
        title="刷新"
        style="margin-left: 10px"
        class="el-icon-refresh"
      ></el-link>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label"> 余额 </template>
      {{ userInfo.balance
      +'点'}}<el-button
        @click="toPay()"
        size="mini"
        style="margin-left: 30px"
        type="primary"
        >去充值</el-button
      >
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label"> 类型 </template>
      {{ userInfo.type }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label"> 创建时间 </template>
      {{
        $dateUtils.formatDate(
          new Date(userInfo.createTime),
          "yyyy-MM-dd hh:mm:ss"
        )
      }}
    </el-descriptions-item>
  </el-descriptions>
</template>
<script>
import API from "../common/api.js";
export default {
  data() {
    return {};
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo.data || {};
    },
  },
  methods: {
    init() {
      API.getUserInfo()
        .then((resp) => {})
        .catch((err) => {
        });
    },
    copySecret() {
      if (navigator.clipboard) {
        navigator.clipboard.writeText(this.$store.state.userInfo.data.secret);
        this.$message.success("复制成功");
      } else {
        this.$message.error("复制失败，请手动复制");
      }
    },
    refreshSecret() {
      API.refreshSecret()
        .then((resp) => {this.$message.success("刷新成功");})
        .catch((err) => {
        });
    },
    toPay() {
      this.$router.push("pay");
    },
  },
  mounted() {
    this.init();
  },
};
</script>
