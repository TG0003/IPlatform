<!--
 * @Author: _t
 * @Date: 2022-06-29 14:57:19
 * @LastEditTime: 2022-07-06 17:31:35
 * @LastEditors: _t
 * @Description: 
-->
<template>
  <div>
    <el-empty
      v-if="!tableData || tableData.length <= 0"
      description="暂无数据"
    ></el-empty>
    <el-table v-else :data="tableData" stripe border style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="识别ID">
              <span>{{ props.row.no }}</span>
            </el-form-item>
            <el-form-item label="识别时间">
              <span>{{
                $dateUtils.formatDate(
                  new Date(props.row.discernTime),
                  "yyyy-MM-dd hh:mm:ss"
                )
              }}</span>
            </el-form-item>
            <el-form-item label="类型">
              <span>{{ props.row.type }}</span>
            </el-form-item>
            <el-form-item label="ID">
              <span>{{ props.row.captchaId }}</span>
            </el-form-item>
            <el-form-item label="REFERER">
              <span>{{ props.row.captchaReferer }}</span>
            </el-form-item>
            <el-form-item label="识别信息">
              <span>{{ props.row.captchaInfo }}</span>
            </el-form-item>
            <el-form-item label="图鉴ID">
              <span>{{ props.row.tjId }}</span>
            </el-form-item>
            <el-form-item label="识别结果">
              <span>{{ props.row.result }}</span>
            </el-form-item>
            <el-form-item label="识别状态">
              <span>{{ props.row.resultState }}</span>
            </el-form-item>
            <el-form-item label="识别IP">
              <span>{{ props.row.discernIp }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        prop="no"
        label="识别ID"
        min-width="40px"
      ></el-table-column>
      <el-table-column
        prop="discernTime"
        label="识别时间"
        min-width="40px"
        :formatter="$dateUtils.tableDateFormat"
      >
      </el-table-column>
      <el-table-column prop="type" label="类型" min-width="30px">
      </el-table-column>
      <el-table-column prop="captchaId" label="ID"> </el-table-column>
      <el-table-column prop="resultSate" label="识别状态" min-width="20px">
        <template slot-scope="props">
          <span class="discernFail" v-if="props.row.resultState === '失败'">{{
            props.row.resultState
          }}</span>
          <span v-else>{{ props.row.resultState }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="discernIp" label="识别IP" min-width="40px">
      </el-table-column>
    </el-table>
    <el-pagination
      :hide-on-single-page="true"
      :page-size="pageInfo.pageSize"
      @current-change="handleCurrentChange"
      layout="total, prev, pager, next"
      :total="total"
    >
    </el-pagination>
  </div>
</template>
<script>
import API from "../common/api.js";
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageInfo: {
        pageNum: 1,
        pageSize: 15,
      },
    };
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.pageInfo.pageNum=currentPage;
      this.refreshTableData();
    },
    refreshTableData() {
      API.getDiscernList(this.pageInfo)
        .then((resp) => {
          let data = resp.data;
          this.tableData = data.data;
          this.total = data.total;
        })
        .catch((err) => {
        });
    },
    init() {
      this.refreshTableData();
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.el-table .discernFail {
  color: red;
}
</style>