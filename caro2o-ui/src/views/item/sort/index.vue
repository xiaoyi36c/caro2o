<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="关键字" prop="categoryName">
        <el-input
          v-model="queryParams.params.keyword"
          placeholder="请输入名称/描述"
          clearable
          @keyup.enter.native="handleQuery"/>
      </el-form-item>

      <el-form-item label="上级分类" prop="parentId" style="display: inline-block;">
        <treeselect aria-label="上级分类" style="width: 200px" v-model="queryParams.parentId" :options="sortOptions" :normalizer="normalizer" placeholder="请选择上级分类" />
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
          v-hasPermi="['sort:sort:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="clickRemoval"
          v-hasPermi="['sort:sort:add']"
        >分类迁移</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="sortList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称" prop="categoryName" />
      <el-table-column label="描述" align="center" prop="categoryDesc" />
      <el-table-column label="上级分类" align="center" prop="parentId">

        <template slot-scope="scope">
          <span v-if="!scope.row.parentId">顶级分类</span>
          <span v-for="item in sortLists" v-if="parseInt(item.id) === parseInt(scope.row.parentId)">{{ item.categoryName }}</span>
        </template>

      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sort:sort:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['sort:sort:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sort:sort:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改物品分类信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="描述" prop="categoryDesc">
          <el-input v-model="form.categoryDesc" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="上级分类" prop="parentId">
          <treeselect v-model="form.parentId" :options="sortOptions"
                      :normalizer="normalizer" placeholder="请选择上级分类id" />
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
import { listSort, getSort, delSort, addSort, updateSort } from "@/api/item/sort";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {doCreateStatement} from "@/api/business/appointment/info";

export default {
  name: "Sort",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 物品分类信息表格数据
      sortList: [],
      sortLists: [], // 物品分类信息列表
      // 物品分类信息树选项
      sortOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        categoryName: null,
        params: {},
        categoryDesc: null,
        parentId: null
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
    listSort().then(response => {
      this.sortLists = response.data;
    });
    this.getTreeselect();

  },
  methods: {
    /** 物品迁移页面跳转 */
    clickRemoval() {
        //js实现跳转
        this.$router.push("item/sort/removal");
    },
    /** 查询物品分类信息列表 */
    getList() {
      this.loading = true;
      listSort(this.queryParams).then(response => {
        this.sortList = this.handleTree(response.data, "id", "parentId");
        this.loading = false;
      });
    },
    /** 转换物品分类信息数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.categoryName,
        children: node.children
      };
    },
	/** 查询物品分类信息下拉树结构 */
    getTreeselect() {
      listSort().then(response => {
        this.sortOptions = [];
        const data = { id: 0, categoryName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "id", "parentId");
        this.sortOptions.push(data);
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
        categoryName: null,
        categoryDesc: null,
        busiPath: null,
        parentId: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加物品分类信息";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.parentId;
      }
      getSort(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品分类信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSort(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSort(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除物品分类信息编号为"' + row.id + '"的数据项？').then(function() {
        return delSort(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
