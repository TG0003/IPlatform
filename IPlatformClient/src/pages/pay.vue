<!--
 * @Author: _t
 * @Date: 2022-06-29 14:58:00
 * @LastEditTime: 2022-07-06 17:31:41
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <div class="pay">
    <el-form ref="payForm" :rules="rules" :model="form" label-width="100px">
      <el-form-item label="选择金额：">
        <el-radio-group v-model="form.payNum">
          <el-radio-button
            v-for="item in confData.payNumList || []"
            :key="item"
            :label="item"
          ></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="自定义金额：" prop="inputPayNum">
        <el-col :span="5">
          <el-input
            v-model="form.inputPayNum"
            type="number"
            placeholder="请输入充值金额"
            ><template slot="append">元</template></el-input
          >
        </el-col>
      </el-form-item>
      <el-form-item label="当前充值：">
        {{ form.payNum
        }}<el-button
          style="margin-left: 20px"
          type="primary"
          size="small"
          @click="pay()"
          >确认充值</el-button
        ></el-form-item
      >
    </el-form>
  </div>
</template>
<script>
import API from "../common/api.js";
import { PAY_CONFIG } from "../common/pageConfig.js";
export default {
  data() {
    var checkInputPayNum = (rule, value, callback) => {
      if (Number(value) < 0) {
        callback(new Error("请输入正确的充值金额"));
      } else if (0 < Number(value) && Number(value) < 10) {
        callback(new Error("请至少充值10元"));
      } else {
        this.form.payNum = parseInt(Number(value)) + "元";
      }
      callback();
    };
    return {
      confData: {},
      form: { payNum: "0元", inputPayNum: null },
      rules: {
        inputPayNum: [{ validator: checkInputPayNum, trigger: "blur" }],
      },
    };
  },
  methods: {
    init() {
      API.getConfig(PAY_CONFIG)
        .then((resp) => {
          let data = JSON.parse(resp.data);
          this.confData = data;
        })
        .catch((err) => {
        });
    },
    pay() {
      //初始判断
      let doPay = this.form.payNum.replace("元", "");
      if (Number(doPay) < 10) {
        this.$message.error("充值金额不合法");
      } else {
        let realPayNum = parseInt(Number(doPay));
        this.$alert("你的实际充值金额：" + realPayNum, "信息", {
          confirmButtonText: "确定",
          callback: (action) => {
            if (action === "confirm") {
              API.pay({ payNum: realPayNum })
                .then((resp) => {
                  this.$router.push({
                    name: "paySuccess",
                    params: { orderNo: resp.data.no, payNum: resp.data.payNum },
                  });
                })
                .catch((err) => {
                });
            }
          },
        });
      }
    },
  },
  mounted() {
    this.init();
  },
};
</script>