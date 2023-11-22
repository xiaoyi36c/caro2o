<template>
  <div class="app-container">
    <!--    查询-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程类型" prop="bpmnType">
        <el-select v-model="queryParams.bpmnType" placeholder="请选择流程类型" clearable>
          <el-option
            v-for="dict in dict.type.bus_process_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部署时间">
        <el-date-picker
          v-model="daterangeDeployTime"
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
          v-hasPermi="['flow:info:add']"
        >流程定义部署
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--列表-->
    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column label="流程名称" align="center" prop="bpmnLabel"/>
      <el-table-column label="流程类型" align="center" prop="bpmnType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_process_type" :value="scope.row.bpmnType"/>
        </template>
      </el-table-column>
      <el-table-column label="流程定义key" align="center" prop="processDefinitionKey"/>
      <el-table-column label="部署时间" align="center" prop="deployTime" width="180">
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version"/>
      <el-table-column label="描述信息" align="center" prop="info" show-overflow-tooltip/>
      <el-table-column label="流程文件" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="showResourceFile(scope.row)"
            v-hasPermi="['flow:info:remove']"
          >查看
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程图片" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="showResourceDiagram(scope.row)"
            v-hasPermi="['flow:info:remove']"
          >查看
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['flow:info:remove']"
          >撤销
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

    <!-- 添加或修改流程定义明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="流程类型" prop="bpmnType">
          <el-select v-model="form.bpmnType" placeholder="请选择流程类型" style="width: 100%">
            <el-option
              v-for="dict in dict.type.bus_process_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="流程定义文件">
          <el-upload
            ref='upload'
            accept=".xml,.bpmn,.zip,.rar,.bpmn20"
            class="upload-demo"
            action=""
            :on-change="handleChange"
            :on-remove="handleRemove"
            :auto-upload="false"
            :limit="1"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xml/bpmn/zip/rar文件，且不超过5m</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述信息" prop="info">
          <el-input v-model="form.info" type="textarea" placeholder="请输入描述信息"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看流程文件界面 -->
    <el-dialog :title="resourceFile.title" :visible.sync="resourceFile.open" width="80%" top="5vh" append-to-body
               class="scrollbar">
      <pre><code class="hljs" v-html="highlightedCode(resourceFile.code)"></code></pre>
    </el-dialog>
    <!-- 查看流程图界面 -->
    <el-dialog :title="resourceDiagram.title" :visible.sync="resourceDiagram.open" width="80%"  top="5vh" append-to-body
               class="scrollbar">
      <img :src="resourceDiagram.url">
    </el-dialog>
  </div>
</template>

<script>
import {
  listInfo,
  getInfo,
  deploy,
  delInfo,
  getResource,
  getResourceDiagram,
  getResourceDiagramSVG
} from "@/api/business/flow/info";
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github-gist.css";

hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));

export default {
  name: "Info",
  dicts: ['bus_process_type'],
  data() {
    return {
      //查看流程文件弹窗
      resourceFile: {
        title: "查看流程文件",
        open: false,
        code: '<hello>hello world</hello>',
      },
      //查看流程它弹窗
      resourceDiagram: {
        title: "查看流程文件",
        open: false,
        url: ""
      },
      //上传的文件列表
      fileList: [],
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
      // 流程定义明细表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 描述信息时间范围
      daterangeDeployTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bpmnType: null,
        deployTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bpmnType: [
          {required: true, message: "流程类型不能为空", trigger: "change"}
        ],
        info: [
          {required: true, message: "描述信息不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //打开查看流程图对话框
    showResourceDiagram(row) {
      getResourceDiagram(row.id,"diagram").then(res => {
        if (res.type === "application/json") {
          getResourceDiagramSVG(row.id).then(res => {
            let blob = new Blob([res],{type:"image/svg+xml"});
            let url = URL.createObjectURL(blob);
            this.resourceDiagram.url = url;
          })
        }else {
          // let blob = new Blob([res],{type:"image/png"});
          let url = URL.createObjectURL(res);
          this.resourceDiagram.url = url;
        }
      })
      this.resourceDiagram.open = true;
    },
    //打开查看流程文件对话框
    showResourceFile(row) {
      getResource(row.id,"file").then(res => {
        this.resourceFile.code = res;
      })
      this.resourceFile.open = true;
    },
    /**高亮显示*/
    highlightedCode(code) {
      const result = hljs.highlight('xml', code || "", true);
      return result.value || '&nbsp;';
    },
    //删除上传文件
    handleRemove() {
      this.fileList = [];
    },
    //添加上传文件
    handleChange(file, fileList) {
      this.$refs.upload.clearFiles()
      //检查文件的格式
      //2.zip
      let name = file.name;
      //先获取到最后一个点的索引
      let index = name.lastIndexOf(".");
      //从name中截取从index之后开始的内容
      let ext = name.substring(index + 1);
      if (['xml', 'bpmn', 'zip', 'rar'].indexOf(ext) < 0) {
        this.$modal.msgError("文件格式错误")
        this.$refs.upload.clearFiles()
        return;
      }
      //在添加文件时，手动将文件对象添加到数组中
      this.fileList = fileList;
    },
    /** 查询流程定义明细列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDeployTime && '' != this.daterangeDeployTime) {
        this.queryParams.params["beginDeployTime"] = this.daterangeDeployTime[0];
        this.queryParams.params["endDeployTime"] = this.daterangeDeployTime[1];
      }
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
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
        bpmnType: null,
        info: null
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
      this.daterangeDeployTime = [];
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
      this.title = "流程定义部署";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程定义明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          //检查用户是否添加文件
          if (this.fileList.length === 0) {
            this.$modal.msgError("请先选择流程定义文件");
            return;
          }
          //封装需要提交的数据
          let formData = new FormData();
          formData.append("bpmnType", this.form.bpmnType);
          formData.append("info", this.form.info);
          formData.append("bpmnFile", this.fileList[0].raw);//文件的数据
          // 发请求
          deploy(formData).then(() => {
            this.$modal.msgSuccess("流程定义部署成功")
            this.open = false;
            this.getList();
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认撤销流程定义编号为"' + ids + '"的数据项？').then(function () {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flow/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
