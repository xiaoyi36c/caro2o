<template>
  <div class="app-container">
    <el-container>
      <el-header height='260px'>
        <el-aside width="100%"  style="background-color: transparent">
          <el-card>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
              <el-form-item label="仓库" prop="storeId">
                <el-select v-model="form.storeId" placeholder="请选择仓库" clearable style="width: 240px">
                  <el-option
                    v-for="dict in barnList"
                    :key="dict.id"
                    :label="dict.storeName"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="出库时间" prop="operateDate">
                <el-date-picker clearable w
                                style="width: 240px"
                                v-model="form.operateDate"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择入库时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" style="width: 240px"/>
              </el-form-item>
            </el-form>
          </el-card>
        </el-aside>
      </el-header>

      <el-main>
        <el-card>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="openDialog = true"
            v-hasPermi="['item:bill:add']"
          >添加明细
          </el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addDetail2"
            v-hasPermi="['item:bill:add']"
          >添加明细到列表
          </el-button>
          <el-table :data="billsList">
            <el-table-column label="序号" align="center" prop="id"/>
            <el-table-column label="物品" align="center" prop="goodsName"/>
            <el-table-column label="价格" align="center">
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  v-model="scope.row.price"
                  clearable
                  style="width: 150px"
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
                />
              </template>
            </el-table-column>
            <el-table-column label="小计" align="center">
              <template slot-scope="scope">
                {{ scope.row.price * scope.row.amounts }}
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

      <!--打开窗口 显示Goods组件-->
      <el-dialog :title="title" :visible.sync="openDialog" width="60%" append-to-body>
        <Goods   @submit="submitForm"/>
      </el-dialog>


      <el-row>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="submitForm"
          v-hasPermi="['item:bill:add']"
        >确 定
        </el-button>

        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="cancel"
          v-hasPermi="['item:bill:edit']"
        >取 消
        </el-button>
      </el-row>
    </el-container>
  </div>
</template>

<script>

import {listBarnAll,outK} from "@/api/item/barn";
// 引用组件
import Goods from "@/views/item/fifo/goods/index.vue";


function Person(id, goodsName) {
  this.id = id;
  this.goodsName = goodsName;
}

function Qo(operateDate, remark, storeId) {
  this.operateDate = operateDate;
  this.remark = remark;
  this.storeId = storeId;
  this.billsList = []; // 新添加的集合，可以是数组或其他集合类型
}
// 在 Qo 对象的原型上添加方法，用于操作集合
Qo.prototype.addToCollection = function(item) {
  this.billsList.push(item);
};
export default {
  name: "Bills",
  components: {
    Goods
  },
  data() {
    return {

      // 显示窗口
      openDialog: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库单据明细表格数据
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
    handleDelete(row) {
      // 查找对象在数组中的索引
      let indexToDelete = this.billsList.findIndex(obj => obj.id === row.id);

      // 如果找到了对象，则删除
      if (indexToDelete !== -1) {
        this.billsList.splice(indexToDelete, 1);
      }
    },
    //添加明细到列表
    addDetail2(){
      // 获取本地存储中的数据
      const storedData = localStorage.getItem('rowData');
// 解析 JSON 字符串
      const rowData = JSON.parse(storedData);
// 在控制台打印数据（实际应用中可用于将数据渲染到页面）
      const newItem = new Person(rowData.id, rowData.goodsName);
      this.billsList.push(newItem)
// 清除本地存储中的数据，确保下次使用时不会再次获取
      localStorage.removeItem('rowData');
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
    submitForm() {
      if (this.billsList.length <= 0) {
        this.$modal.msgError("请先加东西");
        return;
      }
      for (const list of this.billsList) {
        if (list.price === undefined || list.amounts === undefined){
          this.$modal.msgError("请先写数量和价格");
          return;
        }
      }
      const myQo = new Qo(this.form.operateDate, this.form.remark, this.form.storeId);
      myQo.addToCollection(this.billsList);
      outK(myQo).then(res=>{
        this.$modal.msgSuccess("出库成功");
      })

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

