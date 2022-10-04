<template>
  <div>
    <div class="info">请求信息</div>
    <div>
      <el-form
        ref="testForm"
        label-position="left"
        :rules="rules"
        :model="testForm"
        label-width="100px"
      >
        <el-form-item label="url" prop="url">
          <el-col :span="12">
            <el-input v-model="testForm.url" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="secret" prop="secret">
          <el-col :span="6">
            <el-input v-model="testForm.secret"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="type" prop="type">
          <el-col :span="3">
            <el-select v-model="testForm.type">
              <el-option
                v-for="(item, index) in typeArr"
                :key="index"
                :label="item.type"
                :value="item.typeID"
              ></el-option>
            </el-select>
          </el-col>
        </el-form-item>
		<div v-show="testForm.type === 'WY'">
          <el-form-item label="id" prop="id">
            <el-col :span="6">
              <el-input v-model="testForm.id"></el-input>
            </el-col>
          </el-form-item>
        </div>
        <div v-show="testForm.type === 'JY'">
          <el-form-item label="gt" prop="gt">
            <el-col :span="6">
              <el-input v-model="testForm.gt"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="challenge" prop="challenge">
            <el-col :span="6">
              <el-input v-model="testForm.challenge"></el-input>
            </el-col>
          </el-form-item>
        </div>
		<div v-show="testForm.type === 'TX' || testForm.type === 'TX_XCX'">
          <el-form-item label="aid" prop="aid">
            <el-col :span="6">
              <el-input v-model="testForm.aid"></el-input>
            </el-col>
          </el-form-item>
        </div>
		<div v-show="testForm.type === 'WY' || testForm.type === 'JY'">
			<el-form-item label="referer" prop="referer">
			  <el-col :span="12">
				<el-input v-model="testForm.referer"></el-input>
			  </el-col>
			</el-form-item>
			<el-form-item label="ua" prop="ua">
			  <el-col :span="12">
				<el-input v-model="testForm.ua"></el-input>
			  </el-col>
			</el-form-item>
		</div>
        <el-form-item label="tjUsername" prop="tjUsername">
          <el-col :span="6">
            <el-input v-model="testForm.tjUsername"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="tjPassword" prop="tjPassword">
          <el-col :span="6">
            <el-input v-model="testForm.tjPassword"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="ip" prop="ip">
          <el-col :span="6">
            <el-input v-model="testForm.ip"></el-input>
          </el-col>
        </el-form-item>
        <div v-show="testForm.type === 'WY'">
          <el-form-item label="extraData" prop="extraData">
            <el-col :span="12">
              <el-input v-model="testForm.extraData"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="count" prop="count">
            <el-col :span="3">
              <el-input type="number" v-model="testForm.count"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="forSleepTime(s)" prop="forSleepTime">
            <el-col :span="3">
              <el-input
                type="number"
                v-model="testForm.forSleepTime"
              ></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="discernDelay(s)" prop="discernDelay">
            <el-col :span="3">
              <el-input
                type="number"
                v-model="testForm.discernDelay"
              ></el-input>
            </el-col>
          </el-form-item>
        </div>
        <div v-show="testForm.type === 'JY'">
          <el-form-item label="gtServer" prop="gtServer">
            <el-col :span="12">
              <el-input v-model="testForm.gtServer"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="otherOffset" prop="otherOffset">
            <el-col :span="12">
              <el-switch v-model="testForm.otherOffset"></el-switch>
            </el-col>
          </el-form-item>
          <el-form-item label="ofType" prop="ofType">
            <el-col :span="3">
              <el-select v-model="testForm.ofType">
                <el-option
                  v-for="(item, index) in jy_of_type_arr"
                  :key="index"
                  :label="item.name"
                  :value="item.code"
                ></el-option>
              </el-select>
            </el-col>
		  </el-form-item>
        </div>
		<el-form-item label="操作">
			<el-col :span="3">
			  <el-button
				:loading="sending"
				style="margin-left: 10px"
				@click="sendDiscern()"
				type="primary"
				size="medium"
				>{{ sendDiscernButtonText }}</el-button
			  >
			</el-col>
	  </el-form-item>
      </el-form>
    </div>
	
    <el-divider></el-divider>
    <div class="info">响应信息</div>
    <div>
      <el-input
        type="textarea"
        :autosize="{ minRows: 5, maxRows: 10 }"
        placeholder="请求响应内容"
        v-model="respData"
      >
      </el-input>
    </div>
  </div>
</template>
<script>
import API from "../common/api.js";
import $ from "jquery";
import { PRICE_CONFIG } from "../common/pageConfig.js";
export default {
  name: "testOnline",
  data() {
    var checkIp = (rule, value, callback) => {
      if (
        value &&
        !value.match(
          /^[0-9]{2,3}\.[0-9]{2,3}\.[0-9]{2,3}\.[0-9]{2,3}:[0-9]{2,10}$/
        )
      ) {
        callback(new Error("ip格式不合法"));
      }
      callback();
    };
    var checkCount = (rule, value, callback) => {
      if (value) {
        if (Number(value) >= 1 && Number(value) <= 30) {
          this.testForm.count = parseInt(Number(value));
          callback();
        }
        callback(new Error("count最小为1，最大为30"));
      } else {
        callback();
      }
    };
    var checkForSleepTime = (rule, value, callback) => {
      if (value) {
        if (Number(value) >= 0 && Number(value) <= 3) {
          this.testForm.forSleepTime = parseInt(Number(value));
          callback();
        }
        callback(new Error("forSleepTime最小为0，最大为3"));
      } else {
        callback();
      }
    };
    var checkDiscernDelay = (rule, value, callback) => {
      if (value) {
        if (Number(value) >= 0 && Number(value) <= 5) {
          this.testForm.discernDelay = parseInt(Number(value));
          callback();
        }
        callback(new Error("discernDelay最小为0，最大为5"));
      } else {
        callback();
      }
    };
    return {
      sending: false,
      typeArr: [],
      respData: null,
      sendDiscernButtonText: "发送",
      jy_of_type_arr: [
        { name: "slide", code: "slide" },
        { name: "click", code: "click" },
      ],
      testForm: {
        url: "/interpret",
        secret: this.$store.state.userInfo.data
          ? this.$store.state.userInfo.data.secret
          : null,
        id: null,
        referer: null,
        type: null,
        tjUsername: "",
        tjPassword: "",
        ua: null,
        ip: null,
        extraData: null,
        count: null,
        forSleepTime: null,
        discernDelay: null,
        gt: null,
        challenge: null,
        gtServer: null,
        otherOffset: false,
        ofType: null,
		aid:null
      },
      rules: null,
      rulesWy: {
        url: [{ required: true, message: "请输入url地址", tigger: "blur" }],
        secret: [
          { required: true, message: "请输入秘钥secret", tigger: "blur" },
        ],
        id: [{ required: true, message: "请输入验证码id", tigger: "blur" }],
        referer: [
          { required: true, message: "请输入验证码referer", tigger: "blur" },
        ],
        type: [
          { required: true, message: "验证码类型不能为空", tigger: "blur" },
        ],
        ip: [{ validator: checkIp, tigger: "blur" }],
        count: [{ validator: checkCount, tigger: "blur" }],
        forSleepTime: [{ validator: checkForSleepTime, tigger: "blur" }],
        discernDelay: [{ validator: checkDiscernDelay, tigger: "blur" }],
      },
      rulesJy: {
        url: [{ required: true, message: "请输入url地址", tigger: "blur" }],
        secret: [
          { required: true, message: "请输入秘钥secret", tigger: "blur" },
        ],
        gt: [{ required: true, message: "请输入验证码gt", tigger: "blur" }],
        challenge: [
          { required: true, message: "请输入验证码challenge", tigger: "blur" },
        ],
        referer: [
          { required: true, message: "请输入验证码referer", tigger: "blur" },
        ],
        type: [
          { required: true, message: "验证码类型不能为空", tigger: "blur" },
        ],
        ip: [{ validator: checkIp, tigger: "blur" }],
      },
	  rulesTx: {
        url: [{ required: true, message: "请输入url地址", tigger: "blur" }],
        secret: [
          { required: true, message: "请输入秘钥secret", tigger: "blur" },
        ],
        aid: [{ required: true, message: "请输入验证码aid", tigger: "blur" }],
        type: [
          { required: true, message: "验证码类型不能为空", tigger: "blur" },
        ],
        ip: [{ validator: checkIp, tigger: "blur" }],
      }
    };
  },
  watch: {
    "testForm.type": function (newVal, oldVal) {
      if(newVal === 'WY'){
      	this.rules = this.rulesWy;
      }else if(newVal === 'JY'){
      	this.rules = this.rulesJy;
      }else if(newVal === 'TX' || newVal === 'TX_XCX'){
      	this.rules = this.rulesTx;
      }
    },
  },
  methods: {
    init() {
      API.getConfig(PRICE_CONFIG)
        .then((resp) => {
          let data = JSON.parse(resp.data);
          this.typeArr = data.captchaTypeList || [];
          if (this.typeArr.length > 0) {
            this.testForm.type = this.typeArr[0].typeID;
          }
        })
        .catch((err) => {});
    },
    sendDiscern() {
      this.$refs.testForm.validate((valid) => {
        if (valid) {
          this.sending = true;
          this.sendDiscernButtonText = "发送中";
          $.ajax({
            url: this.testForm.url,
            type: "POST",
            data: JSON.stringify(this.testForm),
            contentType: "application/json",
            dataType: "json",
            complete: (XMLHttpRequest, textStatus) => {
              this.sending = false;
              this.sendDiscernButtonText = "发送";
            },
            success: (data) => {
              this.respData = JSON.stringify(data);
              this.$notify({
                title: "通知",
                message: "请求识别成功",
              });
            },
            error: (XMLHttpRequest, textStatus) => {
              this.$message.error(textStatus);
            },
          });
        }
      });
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style scoped>
.info {
  margin-bottom: 15px;
}
</style>