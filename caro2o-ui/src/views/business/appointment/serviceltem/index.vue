<template>
  <div class="app-container">
    <!--    模糊查询-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="服务项名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入服务项名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否套餐" prop="carPackage">
        <el-select v-model="queryParams.carPackage" placeholder="请选择是否套餐" clearable>
          <el-option
            v-for="dict in dict.type.bus_is_car_package"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务分类" prop="serviceCatalog">
        <el-select v-model="queryParams.serviceCatalog" placeholder="请选择服务分类" clearable>
          <el-option
            v-for="dict in dict.type.bus_service_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in dict.type.bus_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上架状态" prop="saleStatus">
        <el-select v-model="queryParams.saleStatus" placeholder="请选择上架状态" clearable>
          <el-option
            v-for="dict in dict.type.bus_sale_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['business:serviceitem:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload2"
          size="mini"
          :disabled="!startAudit"
          @click="handleAudit"
          v-hasPermi="['business:serviceitem:export']"
        >发起审核
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--列表-->
    <el-table v-loading="loading" :data="serviceitemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="服务项名称" align="center" prop="name"/>
      <el-table-column label="服务项原价" align="center" prop="originalPrice"/>
      <el-table-column label="服务项折扣价" align="center" prop="discountPrice"/>
      <el-table-column label="是否套餐" align="center" prop="carPackage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_is_car_package" :value="scope.row.carPackage"/>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="info" show-overflow-tooltip/>
      <el-table-column label="服务分类" align="center" prop="serviceCatalog">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_service_type" :value="scope.row.serviceCatalog"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上架状态" align="center" prop="saleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_sale_status" :value="scope.row.saleStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            :disabled="scope.row.saleStatus === 1 || scope.row.auditStatus === 1"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:serviceitem:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.saleStatus? 'el-icon-bottom':'el-icon-top'"
            :disabled="scope.row.auditStatus === 0 || scope.row.auditStatus === 1 || scope.row.auditStatus === 3"
            @click="handleSaleStatus(scope.row)"
            v-hasPermi="['business:serviceitem:handlesalestatus']"
          >{{ scope.row.saleStatus ? '下架' : "上架" }}
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

    <!-- 添加或修改服务项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="服务项名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入服务项名称"/>
        </el-form-item>
        <el-form-item label="服务项原价" prop="originalPrice">
          <el-input v-model="form.originalPrice" placeholder="请输入服务项原价"/>
        </el-form-item>
        <el-form-item label="服务项折扣价" prop="discountPrice">
          <el-input v-model="form.discountPrice" placeholder="请输入服务项折扣价"/>
        </el-form-item>
        <el-form-item label="备注信息" prop="info">
          <el-input v-model="form.info" placeholder="请输入备注信息"/>
        </el-form-item>
        <el-form-item label="服务分类" prop="serviceCatalog">
          <el-select v-model="form.serviceCatalog" placeholder="请选择服务分类" style="width: 340px">
            <el-option
              v-for="dict in dict.type.bus_service_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否套餐" prop="carPackage">
          <el-radio-group v-model="form.carPackage">
            <el-radio
              v-for="dict in dict.type.bus_is_car_package"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 发起审核对话框 -->
    <el-dialog :title="audit.title" :visible.sync="audit.open" width="500px" append-to-body>
      <el-form ref="form" :model="audit.form" :rules="audit.rules" label-width="120px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="audit.form.serviceItem.name" disabled placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="原价" prop="originalPrice">
          <el-input v-model="audit.form.serviceItem.originalPrice" disabled placeholder="请输入原价"/>
        </el-form-item>
        <el-form-item label="折扣价" prop="discountPrice">
          <el-input v-model="audit.form.serviceItem.discountPrice" disabled placeholder="请输入折扣价"/>
        </el-form-item>

        <el-form-item label="审核人(店长)" prop="shopManagerId">
          <el-select v-model="audit.form.shopManagerId" placeholder="请选择店长" style="width: 340px">
            <el-option
              v-for="manager in audit.form.shopManagers"
              :key="manager.userId"
              :label="manager.nickName"
              :value="parseInt(manager.userId)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人(财务)" prop="financialId" v-if="audit.form.serviceItem.discountPrice >= audit.form.LimitDiscountPrice ">
          <el-select v-model="audit.form.financialId" placeholder="请选择财务" style="width: 340px">
            <el-option
              v-for="manager in audit.form.financials"
              :key="parseInt(manager.userId)"
              :label="manager.nickName"
              :value="parseInt(manager.userId)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="备注信息" prop="info">
          <el-input v-model="audit.form.info" placeholder="请输入备注信息"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleStartAudit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listServiceitem,
  getServiceitem,
  addServiceitem,
  updateServiceitem, updateSaleStatus, getAuditInfo
} from "@/api/business/appointment/serviceitem";
import { addAuditInfo} from "@/api/business/audit/info"
export default {
  name: "Serviceitem",
  dicts: ['bus_is_car_package', 'bus_audit_status', 'bus_service_type', 'bus_sale_status'],
  data() {
    return {
      //发起审核按钮
      startAudit: false,
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
      // 服务项表格数据
      serviceitemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        carPackage: null,
        serviceCatalog: null,
        auditStatus: null,
        saleStatus: null
      },
      // 表单参数
      form: {},
      //审核表单
      audit: {
        title: "发起审核",
        open: false,
        form: {
          serviceItem: {},
          shopManagers: [],
          financials: [],
          shopManagerId: undefined,
          financialId: undefined,
          LimitDiscountPrice: 0,
          info: "",
        },
        rules: {
          shopManagerId: [
            {required: true, message: "请选择店长", trigger: 'change'}
          ],
          financialId: [
            {required: true, message: "请选择店长", trigger: 'change'}
          ]
        }
      },
      ///当前选择的服务项
      currentItem: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "服务项名称不能为空", trigger: "blur"}
        ],
        originalPrice: [
          {required: true, message: "服务项原价不能为空", trigger: "blur"}
        ],
        discountPrice: [
          {required: true, message: "服务项折扣价不能为空", trigger: "blur"}
        ],
        carPackage: [
          {required: true, message: "是否套餐不能为空", trigger: "change"}
        ],
        serviceCatalog: [
          {required: true, message: "服务分类不能为空", trigger: "change"}
        ],
        auditStatus: [
          {required: true, message: "审核状态不能为空", trigger: "blur"}
        ],
        saleStatus: [
          {required: true, message: "上架状态不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  }
  ,
  methods: {
    //发起审核弹窗确定按钮
    handleStartAudit(){
      //准备数据
      let data = {
        serviceItemId: this.audit.form.serviceItem.id,
        shopManagerId: this.audit.form.shopManagerId,
        financialId: this.audit.form.financialId,
        info: this.audit.form.info,
      }
      //发请求
      addAuditInfo(data).then(res => {
        this.audit.open = false;
        this.getList();
        //跳转到审核信息页面
        this.$router.push("/audit/info")
        this.$modal.msgSuccess(`${this.currentItem.name}开启审核成功`)
      })
    },
    //发起审核弹窗
    handleAudit() {
      getAuditInfo(this.currentItem.id).then(res => {
        this.audit.form = res.data;
      })
      this.audit.open = true
    },
    /** 查询服务项列表 */
    getList() {
      this.loading = true;
      listServiceitem(this.queryParams).then(response => {
        this.serviceitemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
    ,
    // 取消按钮
    cancel() {
      this.open = false;
      this.audit.open = false;
      this.audit.form.info = null;
      this.reset();
    }
    ,
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        originalPrice: null,
        discountPrice: null,
        carPackage: 0,
        info: null,
        createTime: null,
        serviceCatalog: null,
        auditStatus: null,
        saleStatus: null
      };
      this.resetForm("form");
    }
    ,
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    }
    ,
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    }
    ,
    // 多选框选中数据
    handleSelectionChange(selection) {
      //检查选择服务项的数量
      if (selection.length === 1 && [0, 3].indexOf(selection[0].auditStatus) >= 0) {
        //检查服务项的状态（初始化）
        this.startAudit = true;
        this.currentItem = selection[0]
      } else {
        this.startAudit = false;
        this.currentItem = {};
      }
    }
    ,
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务项";
    }
    ,
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getServiceitem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改服务项";
      });
    }
    ,
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateServiceitem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServiceitem(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    }
    ,
    /** 删除按钮操作 */
    handleSaleStatus(row) {
      const id = row.id;
      this.$modal.confirm(`是否确认${row.saleStatus ? "下架" : '上架'} ${row.name}`).then(function () {
        return updateSaleStatus(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(`${row.saleStatus ? "下架" : '上架'}成功`);
      }).catch(() => {
      });
    }
    ,
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/serviceitem/export', {
        ...this.queryParams
      }, `serviceitem_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
