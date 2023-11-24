<template>
  <div>
    <el-card>
      <button @click="$router.go(-1)" class="el-button el-button--primary el-button--medium">
        <i class="el-icon-arrow-left"></i>
        <span>返回</span>
      </button>
    <el-table  :data="data" border style="padding-top: 20px">
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.stock_bill_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="物品" align="center" prop="goodsName" />
      <el-table-column label="操作时间" align="center" prop="busiDate" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="数量" align="center" prop="amounts" />
      <el-table-column label="仓库名字" align="center" prop="storeName" />
    </el-table>
    </el-card>

  </div>



</template>

<script>


import {subsidiary} from "@/api/item/goods";

export default {
  name: "Subsidiary",
  dicts: ['stock_bill_type'],
  data() {
    return {
      data: [],
    };
  },
  created() {
    // 从路由中获取参数
    let goodsId = this.$route.params.id;
    subsidiary(goodsId).then(res => {
      console.log(res)
      this.data = res.data;
    });

  },
  methods: {
  }
}
</script>
