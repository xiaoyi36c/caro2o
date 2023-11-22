<template>
  <div class="app-container">
    <el-container>
      <el-main>
        <!--        客户信息方块-->
        <el-card class="box-card" style="margin-top: -12px">
          <!--          客户信息-->
          <el-descriptions title="客户信息">
            <el-descriptions-item label="客户姓名">{{ statement.customerName }}</el-descriptions-item>
            <el-descriptions-item label="联系方式">{{ statement.customerPhone }}</el-descriptions-item>
            <el-descriptions-item label="车牌号码">{{ statement.licensePlate }}</el-descriptions-item>
            <el-descriptions-item label="汽车类型">{{ statement.carSeries }}</el-descriptions-item>
            <el-descriptions-item label="服务类型">
              <dict-tag :options="dict.type.bus_service_type" :value="statement.serviceType"/>
            </el-descriptions-item>
            <el-descriptions-item label="到店时间">{{ statement.actualArrivalTime }}</el-descriptions-item>
            <el-descriptions-item label="总消费金额">{{ statement.totalAmount }}</el-descriptions-item>
            <el-descriptions-item label="实付金额">{{ statement.totalAmount - discountAmount }}</el-descriptions-item>
            <el-descriptions-item label="优惠金额">{{discountAmount }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <el-card class="box-card" style="margin-top: 10px">
          <!--          客户服务列表-->
          <el-table :data="statementItemList">
            <el-table-column label="名称" align="center" prop="itemName"/>
            <el-table-column label="折扣价" align="center" prop="itemPrice"/>
            <el-table-column label="数量" align="center" prop="itemQuantity"/>
          </el-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import {
  listServiceitem,
} from "@/api/business/appointment/serviceitem";
import {getStatement, saveStatementItem, getStatementItems, doPaid} from "@/api/business/appointment/statement";

export default {
  name: "StatementItem",
  dicts: ['bus_is_car_package', 'bus_service_type'],
  data() {
    return {
      // 遮罩层
      loading: false,
      serviceitemList: [],
      statementItemList: [],
      discountAmount: 0,
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        carPackage: null,
        serviceCatalog: null,
        saleStatus: 1
      },
      //客户信息
      statement: {}
    };
  },
  created() {
    this.getList();
    //根据结算单的id查询客户信息
    let statementId = this.$route.params.statementId;
    getStatement(statementId).then(res => {
      let statement = res.data;
      this.statement = statement;
      this.discountAmount = this.statement.discountAmount;
    });
    //根据结算单的id查询结算单明细
    getStatementItems(statementId).then(res => {
      this.statementItemList = res.data;
    })
  },
  methods: {
    /** 支付按钮*/
    doPay(){
      doPaid(this.statement.id).then(res => {
        //支付成功提示
        //先关闭当前页面，在跳转到已支付页面
      })
    },
    /** 保存按钮 */
    saveItem() {
      if (this.statementItemList.length === 0) {
        this.$modal.msgWarning("请先添加明细再保存")
        return;
      }
      let params = {
        statementId: this.statement.id,
        discountAmount: this.discountAmount,
        items: this.statementItemList
      }
      saveStatementItem(params).then(res => {
        this.$modal.msgSuccess("保存成功");
      })
    },
    /** 优惠金额判断*/
    checkDiscountAmount() {
      if (this.discountAmount > this.statement.totalAmount) {
        this.$modal.alert("优惠金额不能大于总消费金额")
        this.discountAmount = 0
      }
    },
    /**
     * 加数量
     */
    addItemQuantity(row) {
      row.itemQuantity++;
      //加钱
      this.statement.totalAmount += row.itemPrice;
    },
    /**
     *减数量
     */
    reduceItemQuantity(row) {
      // 当数量为1时将当前数据从数组中删除
      if (row.itemQuantity === 1) {
        //获取当前元素在数组中的素引
        for (const index in this.statementItemList) {
          if (this.statementItemList[index].itemId === row.itemId) {
            this.statementItemList.splice(index, 1)
            //减钱
            this.statement.totalAmount -= row.itemPrice;
            return;
          }
        }
      }
      row.itemQuantity--;
      //减钱
      this.statement.totalAmount -= row.itemPrice;
    },
    /** 加服务按钮 */
    selectItem(row) {
      //将row转换成statementItem,添加到statementItemList数组中
      //判断当前服务项是否存在于明细列表中
      for (const index in this.statementItemList) {
        if (this.statementItemList[index].itemId === row.id) {
          //存在
          this.statementItemList[index].itemQuantity++;
          // 修改总金额
          this.statement.totalAmount += this.statementItemList[index].itemPrice;
          return;
        }
      }
      //不存在
      let statementItem = {
        itemName: row.name,
        itemPrice: row.discountPrice,
        itemQuantity: 1,
        itemId: row.id,
        statementId: this.statement.id,
      }
      this.statementItemList.push(statementItem)
      // 修改总金额
      this.statement.totalAmount += row.discountPrice;
    },
    /** 查询服务项列表 */
    getList() {
      this.loading = true;
      listServiceitem(this.queryParams).then(response => {
        this.serviceitemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
};
</script>

<style>
aside {
  background: transparent;
}

.box-card {
  margin-bottom: 10px;
}
</style>
