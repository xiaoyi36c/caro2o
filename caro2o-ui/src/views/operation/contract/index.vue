<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同名称" prop="contractName">
        <el-input
          v-model="queryParams.contractName"
          placeholder="请输入合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态 " prop="auditState">
        <el-select v-model="queryParams.auditState" placeholder="请选择审核状态 " clearable>
          <el-option
            v-for="dict in dict.type.audit_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['system:contract:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="客户" align="center" prop="customerId">
        <template slot-scope="scope">
          <span v-for="customer in options" v-if="customer.id == scope.row.customerId">
            {{customer.customerName}}
          </span>
        </template>

      </el-table-column>
      <el-table-column label="合同名称" align="center" prop="contractName"/>
      <el-table-column label="合同编码" align="center" prop="contractCode"/>
      <el-table-column label="合同金额" align="center" prop="amounts"/>
      <el-table-column label="合同生效开始时间" align="center" prop="startDate" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同生效结束时间" align="center" prop="endDate" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电子附件" align="center">
        <template slot-scope="scope">
          <button type="button" class="el-button el-button--primary el-button--mini"
                  @click="download(scope.row)">
            <i class="el-icon-download el-icon--right"></i>
            <span>下载</span>
          </button>
        </template>

      </el-table-column>
      <el-table-column label="是否盖章" align="center" prop="affixSealState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.affix_seal_state" :value="scope.row.affixSealState"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态 " align="center" prop="auditState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.audit_state" :value="scope.row.auditState"/>
        </template>
      </el-table-column>
      <el-table-column label="是否作废" align="center" prop="nullifyState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.nullify_state" :value="scope.row.nullifyState"/>
        </template>
      </el-table-column>
      <el-table-column label="录入人" align="center" prop="inputUser"/>
      <el-table-column label="录入时间" align="center" prop="inputTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inputTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.auditState != 1"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:contract:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.affixSealState === 0"
            @click="handleStamp(scope.row)"
            v-hasPermi="['system:contract:edit']"
          >确认盖章</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.auditState === 0"
            @click="handleApprove(scope.row)"
            v-hasPermi="['system:contract:edit']"
          >审核通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.auditState === 0"
            @click="handleFail(scope.row)"
            v-hasPermi="['system:contract:edit']"
          >审核不通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.nullifyState === 0 && scope.row.auditState === 1"
            @click="handleCancellation(scope.row)"
            v-hasPermi="['system:contract:remove']"
          >作废</el-button>
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

    <!-- 添加或修改合同信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户" prop="customerId">
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.customerName"
              :label="item.label"
              :value="item.customerName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入合同名称" />
        </el-form-item>
        <el-form-item label="合同编码" prop="contractCode">
          <el-input v-model="form.contractCode" placeholder="请输入合同编码" />
        </el-form-item>
        <el-form-item label="合同金额" prop="amounts">
          <el-input v-model="form.amounts" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="合同生效开始时间" prop="startDate">
          <el-date-picker clearable
                          v-model="form.startDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择合同生效开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同生效结束时间" prop="endDate">
          <el-date-picker clearable
                          v-model="form.endDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择合同生效结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="电子附件" prop="appendix">
          <el-input v-model="form.appendix" type="textarea" placeholder="请输入内容" />
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
import {
  listContract,
  getContract,
  delContract,
  addContract,
  updateContract,
  putApprove,
  putContract,
  contractDownload
} from "@/api/operation/contract";
import {listCustomer} from "@/api/client/customer";

export default {
  name: "Contract",
  dicts: ['audit_state','nullify_state','affix_seal_state'],
  data() {
    return {
      options: [],
      value:'',
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
      // 合同信息表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 电子附件时间范围
      daterangeInputTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: null,
        auditState: null,
        inputTime: null
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

    /** 客户的查询 */
    listCustomer().then(res =>{
      this.options = res.rows
    })
  },
  methods: {
    /** 下载附件 */
    download(row) {
      this.$modal.confirm('是否确认下载此附件？').then(function () {
      }).then(() => {
        contractDownload(row.id).then(response => {
          // 将二进制流转为blob
          const blob = new Blob([response], {type: 'application/octet-stream'});

          const downloadLink = document.createElement('a');
          const objectURL = URL.createObjectURL(blob);

          downloadLink.href = objectURL;
          downloadLink.download = `客户${row.contractName} 的合同文件.txt`

          document.body.appendChild(downloadLink);
          downloadLink.click();

          document.body.removeChild(downloadLink);
          URL.revokeObjectURL(objectURL);

          this.$modal.msgSuccess("下载成功");
        });
        this.getList();
      }).catch(() => {
      });
    },


    /** 作废 */
    handleCancellation(row) {
      this.$modal.confirm('是否确认作废此合同？').then(function() {
        return putContract(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("作废成功");
      }).catch(() => {});
    },
    /** 审批不通过 */
    handleFail(row) {
      this.$modal.confirm('是否确认不通过此合同？').then(function() {
        return putApprove(row.id,"2");
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("通过成功");
      }).catch(() => {});
    },
    /** 审批通过*/
    handleApprove(row) {
      this.$modal.confirm('是否确认通过此合同？').then(function() {
        return putApprove(row.id,"1");
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("通过成功");
      }).catch(() => {});
    },
    /** 查询合同信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeInputTime && '' != this.daterangeInputTime) {
        this.queryParams.params["beginInputTime"] = this.daterangeInputTime[0];
        this.queryParams.params["endInputTime"] = this.daterangeInputTime[1];
      }
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
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
        contractName: null,
        contractCode: null,
        amounts: null,
        startDate: null,
        endDate: null,
        appendix: null,
        affixSealState: null,
        auditState: null,
        nullifyState: null,
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
      this.title = "添加合同信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContract(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改合同信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除合同信息编号为"' + ids + '"的数据项？').then(function() {
        return delContract(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/contract/export', {
        ...this.queryParams
      }, `contract_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
