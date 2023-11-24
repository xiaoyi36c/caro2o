<template>
  <div class="app-container">

      <el-card class="box-card" style="margin-top: -12px">
      <el-form ref="form" :model="form" label-width="380px">
      <el-form-item label="仓库" prop="storeId">
        <el-input disabled v-model="form.storeId" placeholder="请输入仓库" />
      </el-form-item>
      <el-form-item label="业务时间" prop="busiDate">
        <el-date-picker clearable
                        disabled
                        v-model="form.busiDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择入库时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input disabled v-model="form.remark" type="textarea" placeholder="请输入内容" />
      </el-form-item>
      </el-form>
      </el-card>

    <el-table :data="billsList">
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="物品" align="center" prop="goodsId" />
      <el-table-column label="数量" align="center" prop="amounts" />
      <el-table-column label="金额" align="center" prop="price" />
      <el-table-column label="小计" align="center" prop="subtotal">
        <template slot-scope="scope">
          <span>{{ scope.row.price * scope.row.amounts }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {listBills} from "@/api/item/bills";

export default {
  name: "Bills",
  data() {
    return {
      // 总条数
      total: 0,
      // 出入库单据明细表格数据
      billsList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询出入库单据明细列表 */
    getList() {
      this.loading = true;
      listBills(this.queryParams).then(response => {
        this.billsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
};
</script>
