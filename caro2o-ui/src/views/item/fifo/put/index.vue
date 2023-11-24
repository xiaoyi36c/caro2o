<template>
  <div class="app-container">
    <el-container>
      <el-header height='260px' >
        <el-aside width="40%" style="background-color: transparent">
          <el-card>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
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
                <el-date-picker clearable w
                                style="width: 221px"
                                v-model="form.operateDate"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择入库时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" style="width: 221px"/>
              </el-form-item>
            </el-form>
          </el-card>
        </el-aside>

        <el-main style="background-color: transparent">
          <el-card>
          </el-card>
        </el-main>

      </el-header>

      <el-main>


        <el-card>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addDetail"
            v-hasPermi="['item:bill:add']"
          >添加明细</el-button>
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
            <el-table-column label="小计" align="center">
              <template slot-scope="scope">
                {{scope.row.price * scope.row.amounts}}
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['flow:info:remove']"
                >删除
                </el-button>
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
        id: '1',
        goodsId: '王小虎',
      },
        {
          id: '2',
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
    //删除
    handleDelete(row){
      // 查找对象在数组中的索引
      let indexToDelete = this.billsList.findIndex(obj => obj.id === row.id);

      // 如果找到了对象，则删除
      if (indexToDelete !== -1) {
        this.billsList.splice(indexToDelete, 1);
      }
    },
    //添加明细
    addDetail(){
      console.log("滚滚滚")
    },
    // 取消按钮,回跳页面
    cancel() {
      this.billsList = []
      let url = "plug"
      //js实现跳转
      const obj = {path: "/item/fifo/fifo/put"};
      this.$tab.closeOpenPage(obj).then(() => {
        this.$router.push(url);
      })
    },
    //确定
    submitForm(){
      console.log("滚滚滚")
    },
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

