<!--
 * @Author: _t
 * @Date: 2022-06-25 15:40:33
 * @LastEditTime: 2022-09-02 12:41:35
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <el-form
    v-loading.fullscreen.lock="fullscreenLoading"
    ref="loginForm"
    :rules="rules"
    :model="form"
    label-width="80px"
  >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="form.password" show-password></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="verifyCode">
      <el-col :span="6">
        <el-input v-model="form.verifyCode"></el-input>
      </el-col>
      <el-col :span="6">
        <img style="width: 200px; height: 30px" @click="captchaV=Math.random()" :src="'/captcha?openId='+form.openId+'&v='+captchaV" />
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
      <el-button @click="toRegister">立即创建</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import API from "../common/api.js";
export default {
  data() {
    return {
      fullscreenLoading: false,
      captchaV:Math.random(),
      form: {
        username: null,
        password: null,
        openId:new Date().getTime(),
        verifyCode:null
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", tigger: "blur" },
          {
            min: 5,
            max: 10,
            message: "长度在 5 到 10 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", tigger: "blur" },
          {
            min: 5,
            max: 10,
            message: "长度在 5 到 10 个字符",
            trigger: "blur",
          },
        ],
        verifyCode: [
          { required: true, message: "请输入验证码", tigger: "blur" },
          {
            min: 5,
            max: 8,
            message: "验证码长度不合法",
            trigger: "blur",
          },
        ]
      },
    };
  },
  methods: {
    onSubmit() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.fullscreenLoading = true;
          API.login(this.form)
            .then((response) => {
              this.fullscreenLoading = false;
              this.$router.push("/");
              this.$message.success("登录成功");
            })
            .catch((err) => {
              this.fullscreenLoading = false;
            });
        }
      });
    },
    toRegister() {
      this.$router.push("/register");
    },
  },
};
</script>