<!--
 * @Author: _t
 * @Date: 2022-06-29 14:57:19
 * @LastEditTime: 2022-07-06 17:31:23
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
      <el-table-column
        prop="no"
        label="订单号"
        min-width="40px"
      ></el-table-column>
      <el-table-column
        prop="payNum"
        label="充值金额"
        min-width="40px"
      ></el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="40px"
        :formatter="$dateUtils.tableDateFormat"
      >
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
      this.pageInfo.pageNum = currentPage;
      this.refreshTableData();
    },
    refreshTableData() {
      API.getPayList(this.pageInfo)
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