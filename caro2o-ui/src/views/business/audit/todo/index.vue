<template>
  <div class="app-container">
    <!--列表-->
    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column label="套餐名称" align="center" prop="serviceItemName"/>
      <el-table-column label="套餐备注" align="center" prop="serviceItemInfo"/>
      <el-table-column label="套餐价格" align="center" prop="serviceItemPrice"/>
      <el-table-column label="审核备注" align="center" prop="info"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carpackage_audit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-claim"
            @click="handleAudit(scope.row)"
            v-hasPermi="['audit:info:remove']"
          >审批
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-date"
            @click="handleHistory(scope.row)"
            v-hasPermi="['audit:info:edit']"
          >审批历史
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleProgress(scope.row)"
            v-hasPermi="['audit:info:edit']"
          >进度查看
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

    <!-- 添加或修改审核信息对话框 -->
    <el-dialog :title="audit.title" :visible.sync="audit.open" width="500px" append-to-body>
      <el-form ref="form" :model="audit.form" :rules="audit.rules" label-width="80px">

        <el-form-item label="审批意见" prop="passed">
          <el-select v-model="audit.form.passed" placeholder="请选择审核人">
            <el-option label="同意" :value="true"/>
            <el-option label="拒绝" :value="false"/>
          </el-select>
        </el-form-item>

        <el-form-item label="批注" prop="info">
          <el-input v-model="audit.form.info" placeholder="请输入审核备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAuditForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看审批历史界面 -->
    <el-dialog :title="historyFile.title" :visible.sync="historyFile.open" width="80%" top="5vh" append-to-body
               class="scrollbar">
      <el-table
        :data="historyFile.historyData"
        style="width: 100%">
        <el-table-column
          prop="name"
          label="名称">
        </el-table-column>
        <el-table-column
          prop="startTime"
          label="开始时间">
        </el-table-column>
        <el-table-column
          prop="endTime"
          label="结束时间">
        </el-table-column>
        <el-table-column
          prop="elapsedTime"
          label="耗时">
        </el-table-column>
        <el-table-column
          prop="info"
          label="批注">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看进度界面 -->
    <el-dialog :title="resourceDiagram.title" :visible.sync="resourceDiagram.open" width="80%" top="5vh" append-to-body
               class="scrollbar">
      <img :src="resourceDiagram.url">
    </el-dialog>
  </div>
</template>

<script>
import {audit, getHistory, getProgress, listInfo} from "@/api/business/audit/todo";
import {getResourceDiagram, getResourceDiagramSVG} from "@/api/business/flow/info";

export default {
  name: "Info",
  dicts: ['carpackage_audit_status'],
  data() {
    return {
      //查看进度弹窗
      resourceDiagram: {
        title: "进度查看",
        open: false,
        url: ""
      },
      //历史审批
      historyFile: {
        title: "审批历史",
        open: false,
        historyData: [{
          name: '',
          startTime: '',
          endTime: '',
          elapsedTime: '',
          info: '',
        },],
      },
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 审核信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 审核备注时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      audit: {
        title: "审批",
        open: false,
        rules: {
          passed: [
            {required: true, message: "请选择审批意见", trigger: "change"}
          ],
          info: [
            {required: true, message: "请输入审批批注", trigger: "burl"}
          ]
        },
        form: {
          passed: true,
          info: undefined,
          id: undefined
        },
      },
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //进度查看
    handleProgress(row) {
      getProgress(row.instanceId).then(res => {
        let blob = new Blob([res], {type: "image/svg+xml"});
        let url = URL.createObjectURL(blob);
        this.resourceDiagram.url = url;
      })
      this.resourceDiagram.open = true;
    },
    //审批历史
    handleHistory(row) {
      getHistory(row.instanceId).then(res => {
        this.historyFile.historyData = res.data;
      })
      this.historyFile.open = true;
    },
    //审核按钮
    handleAudit(row) {
      this.audit.open = true;
      this.audit.form.id = row.id
    },
    /** 查询审核代办信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.audit.open = false
      this.historyFile.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        serviceItemId: null,
        serviceItemName: null,
        serviceItemInfo: null,
        serviceItemPrice: null,
        instanceId: null,
        creatorId: null,
        info: null,
        status: null,
        createTime: null
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
      this.daterangeCreateTime = [];
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
      this.title = "添加审核信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改审核信息";
      });
    },
    /** 提交审批按钮 */
    submitAuditForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          audit(this.audit.form).then(res => {
            this.$modal.msgSuccess("审批成功");
            this.audit.open = false;
            this.getList();
          })
        }
      });
    },
    /** 撤销按钮操作 */
    handleDelete(row) {
      const ids = row.id
      this.$modal.confirm('是否确认撤销审核信息编号为"' + ids + '"的数据项？').then(function () {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('audit/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
