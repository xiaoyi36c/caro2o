<template>
  <div class="app-container">
    <!--    模糊查询-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户姓名" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户联系方式" prop="customerPhone" label-width="100px">
        <el-input
          v-model="queryParams.customerPhone"
          placeholder="请输入客户联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号码" prop="licensePlate">
        <el-input
          v-model="queryParams.licensePlate"
          placeholder="请输入车牌号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['business:appointment:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:appointment:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--列表-->
    <el-table v-loading="loading" :data="appointmentList" @selection-change="handleSelectionChange">
      <el-table-column label="客户姓名" align="center" prop="customerName"/>
      <el-table-column label="客户联系方式" align="center" prop="customerPhone"/>
      <el-table-column label="预约时间" align="center" prop="appointmentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.appointmentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际到店时间" align="center" prop="actualArrivalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualArrivalTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车牌号码" align="center" prop="licensePlate"/>
      <el-table-column label="汽车类型" align="center" prop="carSeries"/>
      <el-table-column label="服务类型" align="center" prop="serviceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_service_type" :value="scope.row.serviceType"/>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="info"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_appointment_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status === 0"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:appointment:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-home"
            v-if="scope.row.status === 0"
            @click="handleArrival(scope.row)"
            v-hasPermi="['business:appointment:arrival']"
          >到店
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-release"
            v-if="scope.row.status === 0"
            @click="handleCancel(scope.row)"
            v-hasPermi="['business:appointment:cancel']"
          >取消
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            v-if="scope.row.status === 1||scope.row.status === 4||scope.row.status === 5"
            @click="createStatement(scope.row)"
            v-hasPermi="['business:appointment:remove']"
          >结算单
          </el-button>
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
    <!-- 添加或修改养修信息预约对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="客户姓名" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户姓名"/>
        </el-form-item>
        <el-form-item label="联系方式" prop="customerPhone">
          <el-input v-model="form.customerPhone" placeholder="请输入客户联系方式"/>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker style="width:360px"
                          clearable
                          v-model="form.appointmentTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择预约时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车牌号码" prop="licensePlate">
          <el-input v-model="form.licensePlate" placeholder="请输入车牌号码"/>
        </el-form-item>
        <el-form-item label="汽车类型" prop="carSeries">
          <el-input v-model="form.carSeries" placeholder="请输入汽车类型"/>
        </el-form-item>
        <el-form-item label="服务类型" prop="serviceType">
          <el-select v-model="form.serviceType" placeholder="请选择服务类型" style="width:360px" clearable>
            <el-option
              v-for="dict in dict.type.bus_service_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="info">
          <el-input v-model="form.info" placeholder="请输入备注信息"/>
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
  listAppointment,
  getAppointment,
  addAppointment,
  updateAppointment, doArrival, doCancel,doCreateStatement
} from "@/api/business/appointment/info";

import
{
  validPhone,
  validLicensePlate
} from "@/utils/validate"

export default {
  name: "Appointment",
  dicts: ["bus_service_type", "bus_appointment_status"],
  data() {
    //自定义联系方式的校验规则
    let validatePhone = (rule, value, callback) => {
      if (validPhone(value)) {
        callback();
      } else {
        callback(new Error("联系方式格式不正确"))
      }
    }
    //自定义车牌号的校验规则
    let validateLicensePlate = (rule, value, callback) => {
      if (validLicensePlate(value)) {
        callback();
      } else {
        callback(new Error("车牌号格式不正确"))
      }
    }
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
      // 养修信息预约表格数据
      appointmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerName: null,
        customerPhone: null,
        licensePlate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerName: [
          {required: true, message: "请填写客户姓名", trigger: "blur"}
        ],
        customerPhone: [
          {required: true, message: "请填写客户联系方式", trigger: "blur"},
          {validator: validatePhone, trigger: "blur"}
        ],
        appointmentTime: [
          {required: true, message: "请填写预约时间", trigger: "change"}
        ],
        licensePlate: [
          {required: true, message: "请填写车牌号码", trigger: "blur"},
          {validator: validateLicensePlate, trigger: "blur"}
        ],
        carSeries: [
          {required: true, message: "请填写汽车类型", trigger: "blur"}
        ],
        serviceType: [
          {required: true, message: '请选择服务类型', trigger: 'change'}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 结算单跳转*/
    createStatement(row) {
      doCreateStatement(row.id).then(res => {
        let statement = res.data;
        let url = "/appointment/statement/item/details/" + statement.id;
        if (row.status === 1) {
          url = "/appointment/statement/item/" + statement.id;
        }
        //js实现跳转
        this.$router.push(url);
      })
    },
    /** 查询养修信息预约列表 */
    getList() {
      this.loading = true;
      listAppointment(this.queryParams).then(response => {
        this.appointmentList = response.rows;
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
        customerPhone: null,
        appointmentTime: null,
        actualArrivalTime: null,
        licensePlate: null,
        carSeries: null,
        serviceType: null,
        createTime: null,
        info: null,
        status: null
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
      this.open = true;
      this.title = "添加养修信息预约";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAppointment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改养修信息预约";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAppointment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAppointment(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 到店按钮操作 */
    handleArrival(row) {
      const id = row.id;
      this.$modal.confirm(`是否确认${row.customerName}已到店?`).then(function () {
        return doArrival(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(`${row.customerName}已到店`);
      }).catch(() => {
      });
    },
    /** 取消按钮操作 */
    handleCancel(row) {
      const id = row.id;
      this.$modal.confirm(`是否确认${row.customerName}要取消预约?`).then(function () {
        return doCancel(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(`${row.customerName}已取消预约`);
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/appointment/export', {
        ...this.queryParams
      }, `appointment_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
