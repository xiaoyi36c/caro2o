<template>
  <div class="app-container">
    <el-container>
      <el-header height='260px'>
        <el-card style="width: 60%">
          <el-form ref="form" :model="form" :rules="rules" label-width="200px">
            <el-form-item label="仓库" prop="storeId">
              <el-select v-model="form.storeId" placeholder="请选择仓库" clearable>
                <el-option
                  v-for="dict in barnList"
                  :key="dict.id"
                  :label="dict.storeName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="入库时间" prop="operateDate" >
              <el-date-picker clearable
                              style="width: 60%"
                              v-model="form.operateDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择入库时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" style="width: 60%"/>
            </el-form-item>
          </el-form>
        </el-card>


      </el-header>

      <el-main>
        <el-card>
          <el-table :data="billsList">
            <el-table-column label="序号" align="center" prop="id"/>
            <el-table-column label="物品" align="center" prop="goodsId"/>
            <el-table-column label="价格" align="center">
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  v-model="scope.row.price"
                  clearable
                  style="width: 150px"
                  @change="checkPrice()"
                />
              </template>
            </el-table-column>
            <el-table-column label="数量" align="center">
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  v-model="scope.row.amounts"
                  clearable
                  style="width: 150px"
                  @change="checkAmounts()"
                />
              </template>
            </el-table-column>
            <el-table-column label="小计">
              <template slot-scope="scope">
                {{scope.row.price * scope.row.amounts}}
              </template>
            </el-table-column>
          </el-table>
        </el-card>

      </el-main>

      <el-row>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="submitForm"
            v-hasPermi="['item:bill:add']"
          >确 定</el-button>

          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            @click="cancel"
            v-hasPermi="['item:bill:edit']"
          >取 消</el-button>
      </el-row>
    </el-container>
  </div>
</template>

<script>

import {listBarnAll} from "@/api/item/barn";

export default {
  name: "Bills",
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库单据明细表格数据
      billsList:  [{
        id: '2016-05-02',
        goodsId: '王小虎',
      },
        {
          id: '2016-05-02',
          goodsId: '王虎',
        }],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //仓库列表
      barnList: [],


      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {

    listBarnAll().then(response => {
      this.barnList = response.rows;
    });
  },
  methods: {
    //价格修改
    checkPrice(){
      if (this.discountAmount > this.statement.totalAmount) {
        this.$modal.alert("优惠金额不能大于总消费金额")
        this.discountAmount = 0
      }
    },
    //数量修改
    checkAmounts() {
      if (this.discountAmount > this.statement.totalAmount) {
        this.$modal.alert("优惠金额不能大于总消费金额")
        this.discountAmount = 0
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //确定
    submitForm(){},
    // 表单重置
    reset() {
      this.form = {
        id: null,
        goodsId: null,
        amounts: 0,
        price: 0,
        billId: null,
        state: null
      };
      this.resetForm("form");
    },
  }
};
</script>

