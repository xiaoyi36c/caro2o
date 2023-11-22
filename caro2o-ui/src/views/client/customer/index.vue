<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="70px">
      <el-form-item label="关键字" prop="customerName">
        <el-input
          v-model="queryParams.customerName || queryParams.legalLeader || queryParams.industry"
          placeholder="企业名称/法定代表人/所属行业"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营状态" prop="openState">
        <el-select v-model="queryParams.openState" placeholder="请选择经营状态" clearable>
          <el-option
            v-for="dict in dict.type.client_customer_tinyint"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属地区" prop="industry">
        <el-select v-model="queryParams.province" placeholder="请选择所属地区" clearable>
          <el-option
            v-for="dict in dict.type.china_sheng"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="录入时间">
        <el-date-picker
          v-model="daterangeInputTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['client:customer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['client:customer:edit']"
        >修改</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>


      <!--表单-->
    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="企业名称" align="center" prop="customerName" />
      <el-table-column label="法定代表人" align="center" prop="legalLeader" />
      <el-table-column label="成立时间" align="center" prop="registerDate" width="180">
      </el-table-column>
      <el-table-column label="所属行业" align="center" prop="industry" />
      <el-table-column label="所属地区" align="center" prop="province">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.china_sheng" :value="scope.row.province"/>
        </template>
      </el-table-column>
      <el-table-column label="经营状态" align="center" prop="openState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.client_customer_tinyint" :value="scope.row.openState"/>
        </template>
      </el-table-column>
      <el-table-column label="录入人" align="center" prop="inputUser" />
      <el-table-column label="营销人" align="center" prop="inputUser" />
      <el-table-column label="录入时间" align="center" prop="inputTime" width="180">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['client:customer:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['client:customer:remove']"
          >详情</el-button>
        </template>
      </el-table-column>
    </el-table>

     <!--分页-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="企业名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="法定代表人" prop="legalLeader">
          <el-input v-model="form.legalLeader" placeholder="请输入法定代表人" />
        </el-form-item>
        <el-form-item label="成立时间" prop="registerDate">
          <el-date-picker clearable
                          v-model="form.registerDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择成立时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经营状态" prop="openState">
          <el-select v-model="form.openState" placeholder="请选择经营状态">
            <el-option
              v-for="dict in dict.type.client_customer_tinyint"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册资本(万元)" prop="regCapital">
          <el-input v-model="form.regCapital" placeholder="请输入注册资本" />
        </el-form-item>
        <el-form-item label="所属地区" prop="province">
          <el-select v-model="form.province" placeholder="请选择所属地区">
            <el-option
              v-for="dict in dict.type.china_sheng"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属行业" prop="industry">
          <el-input v-model="form.industry" placeholder="请输入所属行业" />
        </el-form-item>
        <el-form-item label="经营范围" prop="scope">
          <el-input v-model="form.scope" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="注册地址" prop="regAddr">
          <el-input v-model="form.regAddr" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer } from "@/api/client/customer";

export default {
  name: "Customer",
  dicts: ['client_customer_tinyint','china_sheng'],
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
      // 客户信息表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 注册地址时间范围
      daterangeInputTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerName: null,
        legalLeader: null,
        openState: null,
        province: null,
        industry: null,
        inputTime: null,
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
    /** 查询客户信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeInputTime && '' != this.daterangeInputTime) {
        this.queryParams.params["beginInputTime"] = this.daterangeInputTime[0];
        this.queryParams.params["endInputTime"] = this.daterangeInputTime[1];
      }
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows;
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
        customerName: null,
        legalLeader: null,
        registerDate: null,
        openState: null,
        province: null,
        regCapital: null,
        industry: null,
        scope: null,
        regAddr: null,
        inputTime: null,
        inputUser: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeInputTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCustomer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCustomer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除客户信息编号为"' + ids + '"的数据项？').then(function() {
        return delCustomer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('client/customer/export', {
        ...this.queryParams
      }, `customer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
