<template>
  <div class="app-container">


    <el-table v-loading="loading" :data="billsList">
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="物品" align="center" prop="goodsId" />
      <el-table-column label="数量" align="center" prop="amounts" />
      <el-table-column label="金额" align="center" prop="price" />
      <el-table-column label="小计" align="center" prop="billId" />
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
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出入库单据明细表格数据
      billsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        goodsId: null,
        amounts: null,
        price: null,
        billId: null,
        state: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
  }
};
</script>
