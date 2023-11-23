<template>
  <div class="app-container">
    <!--    查询-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="拜访方式" prop="visitType">
        <el-select v-model="queryParams.visitType" placeholder="请选择拜访方式" clearable>
          <el-option
            v-for="dict in dict.type.see_way"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="拜访原因" prop="visitReason">
        <el-input
          v-model="queryParams.visitReason"
          placeholder="请输入拜访原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拜访时间">
        <el-date-picker
          v-model="daterangeVisitDate"
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
    <!--按钮-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['client:record:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--列表-->
    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="客户" align="center" prop="customerId"/>
      <el-table-column label="联系人" align="center" prop="linkmanId"/>
      <el-table-column label="拜访方式" align="center" prop="visitType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.see_way" :value="scope.row.visitType"/>
        </template>
      </el-table-column>
      <el-table-column label="拜访原因" align="center" prop="visitReason"/>
      <el-table-column label="交流内容" align="center" prop="content"/>
      <el-table-column label="拜访时间" align="center" prop="visitDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="录入人" align="center" prop="inputUser"/>
      <el-table-column label="录入时间" align="center" prop="inputTime" width="180"/>
    </el-table>
    <!--    分页-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改拜访信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="拜访客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="请选择联系人">
            <el-option
              v-for="dept in seeClient"
              :key="dept.id"
              :label="dept.customerName"
              :value="dept.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户联系人" prop="linkmanId">
          <el-select v-model="form.linkmanId" placeholder="请选择联系人">
            <el-option
              v-for="dept in clientLinkman"
              :key="dept.id"
              :label="dept.linkman"
              :value="dept.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拜访方式" prop="visitType">
          <el-select v-model="form.visitType" placeholder="请选择拜访方式">
            <el-option
              v-for="dict in dict.type.see_way"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拜访原因" prop="visitReason">
          <el-input v-model="form.visitReason" placeholder="请输入拜访原因"/>
        </el-form-item>
        <el-form-item label="拜访时间" prop="visitDate">
          <el-date-picker clearable
                          v-model="form.visitDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择拜访时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交流内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容"/>
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
import {listRecord, getRecord, delRecord, addRecord, updateRecord, getClientLinkman} from "@/api/client/record";

export default {
  name: "Record",
  dicts: ['see_way'],
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
      // 拜访时间时间范围
      daterangeVisitDate: [],
      //拜访客户
      seeClient: [],
      //客户联系人
      clientLinkman: [],
      // 总条数
      total: 0,
      // 拜访信息表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visitType: null,
        visitReason: null,
        visitDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerId:[
          {required:true,message:"请输入交流内容",trigger:"blur"},
        ],
        linkmanId:[
          {required:true,message:"请输入交流内容",trigger:"blur"},
        ],
        visitType:[
          {required:true,message:"请输入交流内容",trigger:"change"},
        ],
        visitDate:[
          {required:true,message:"请选择拜访时间",trigger:"change"},
        ],
        visitReason:[
          {required:true,message:"请输入拜访原因",trigger:"blur"},
          // {pattern: /^\d+(\.\d{1,2})?$/, message: '请输入正数且只能保留两位小数'},
          { min:0, max:100, message: '请不要超过100位数', trigger: 'blur' },
        ],
        content:[
          {required:true,message:"请输入交流内容",trigger:"blur"},
          { min:0, max:1000, message: '请不要超过1000位数', trigger: 'blur' },
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询拜访信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeVisitDate && '' != this.daterangeVisitDate) {
        this.queryParams.params["beginVisitDate"] = this.daterangeVisitDate[0];
        this.queryParams.params["endVisitDate"] = this.daterangeVisitDate[1];
      }
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        customerId: null,
        linkmanId: null,
        visitType: null,
        visitReason: null,
        content: null,
        visitDate: null,
        inputUser: null,
        inputTime: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getClientLinkman().then(response => {
        this.seeClient = response.data.seeClient;
        this.clientLinkman = response.data.clientLinkman;
      });
      this.open = true;
      this.title = "添加拜访信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改拜访信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除拜访信息编号为"' + ids + '"的数据项？').then(function () {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('client/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
