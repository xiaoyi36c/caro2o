<template>
  <div class="app-container">
    <el-container>
      <el-main>
        <!--        客户信息方块-->
        <el-card class="box-card" style="margin-top: -12px">
          <!--          客户信息-->
          <el-descriptions title="客户信息">
            <el-descriptions-item label="企业名称">{{ customer.customerName }}</el-descriptions-item>
            <el-descriptions-item label="法定代表人">{{ customer.legalLeader }}</el-descriptions-item>
            <el-descriptions-item label="成立时间">{{ customer.registerDate }}</el-descriptions-item>
            <el-descriptions-item label="所属行业">{{ customer.industry }}</el-descriptions-item>
            <el-descriptions-item label="所属省份">
              <dict-tag :options="dict.type.china_sheng" :value="customer.province"/>
            </el-descriptions-item>
            <el-descriptions-item label="经营状态">
              <dict-tag :options="dict.type.client_customer_tinyint" :value="customer.openState"/>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>

        <el-card class="box-card" style="margin-top: 10px">
          <!--  联系人-->
          <el-descriptions title="客户信息联系方式"/>
          <el-table border :data="contact" style="font-size: 12px">
            <el-table-column label="联系人名字" align="center" prop="linkman"/>
            <el-table-column label="性别" align="center" prop="gender">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.client_contact_sex" :value="scope.row.gender"/>
              </template>
            </el-table-column>
            <el-table-column label="年龄" align="center" prop="age"/>
            <el-table-column label="联系人电话" align="center" prop="phone"/>
            <el-table-column label="职位" align="center" prop="position"/>
            <el-table-column label="任职状态" align="center" prop="positionState">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.client_contact_status" :value="scope.row.positionState"/>
              </template>
            </el-table-column>
            <el-table-column label="部门" align="center" prop="department"/>
          </el-table>

          <pagination
            :total="queryParams.total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-card>
      </el-main>

      <el-aside width="40%" style="margin-left: -30px">
        <!--拜访记录-->
        <el-card class="box-card">
          <el-table :data="recordList" >
          <el-table-column label="联系人" align="center" prop="linkmanId"/>
          <el-table-column label="拜访方式" align="center" prop="visitType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.see_way" :value="scope.row.visitType"/>
            </template>
          </el-table-column>
          <el-table-column label="拜访原因" align="center" prop="visitReason"/>
          <el-table-column label="交流内容" align="center" prop="content"/>
          <el-table-column label="拜访时间" align="center" prop="visitDate">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.visitDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          </el-table>
          <pagination
            :total="requeryParams.total"
            :page.sync="requeryParams.pageNum"
            :limit.sync="requeryParams.pageSize "
            @pagination="getRecList"
          />
        </el-card>

        <!--合同信息-->
        <el-card class="box-card">
          <!--          列表-->

          <!--          分页-->
        </el-card>
      </el-aside>
    </el-container>
  </div>
</template>

<script>


import {getCustomer} from "@/api/client/customer";
import {getPhone, listContact} from "@/api/client/contact";
import {getRec, listRecord} from "@/api/client/record";

export default {
  name: "CustomerInfo",
  dicts: ['china_sheng','client_customer_tinyint','client_contact_sex','client_contact_status','see_way'],
  data() {
    return {
      //客户信息
      customer:{},
      //联系信息
      contact:[] ,
      //拜访信息
      recordList:[],
      // 客户联系分页
      queryParams: {
        total: 0,
        pageNum: 1,
        pageSize: 5,
        params: {}, // 搜索参数
        positionState: null,
      },
      //拜访记录分页
      requeryParams: {
        total:0,
        pageNum: 1,
        pageSize:3,
      },
    };
  },
  created() {
    this.getList();
    this.getRecList();
    //根据客户信息id查询客户信息
    let id = this.$route.params.Id;
    getCustomer(id).then(res =>{
      this.customer = res.data;
    });
    //根据客户信息id去查询联系信息
    getPhone(id).then(res=>{
      this.contact = res.data;
    });
    //根据客户id去查询拜访记录
    getRec(id).then(res=>{
      this.recordList = res.data
    });

  },
  methods: {
    /** 查询客户信息列表 */
    getList() {
      listContact(this.queryParams).then(response => {
        this.contact = response.rows;
        this.queryParams.total = response.total;
      });
    },
    getRecList() {
      listRecord(this.requeryParams).then(response => {
        this.recordList = response.rows;
        this.requeryParams.total = response.total;
      });
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
