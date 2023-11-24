<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="goodsName">
        <el-input
          v-model="queryParams.params.keyword"
          placeholder="请输入物品名称/描述/品牌/数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="分类" prop="categoryId" style="display: inline-block;">
        <treeselect aria-label="分类" style="width: 200px" v-model="queryParams.categoryId" :options="sortOptions" :normalizer="normalizer" placeholder="请选择分类" />
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
          v-hasPermi="['item:goods:add']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="$router.push(`/item/barn`)"
          v-hasPermi="['item:goods:edit']"
        >仓库管理</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="$router.push(`/item/sort`)"
          v-hasPermi="['item:goods:edit']"
        >分类管理</el-button>
      </el-col>


      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="物品名称" align="center" prop="goodsName" />
      <el-table-column label="封面" align="center" prop="goodsCover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.goodsCover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="分类" align="center" prop="categoryId" >
        <template slot-scope="scope">
          <span v-if="!scope.row.categoryId">顶级分类</span>
          <span v-for="item in sortLists" v-if="parseInt(item.id) === parseInt(scope.row.categoryId)">{{ item.categoryName }}</span>
        </template>

      </el-table-column>
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="数量" align="center" prop="spec" />
      <el-table-column label="描述" align="center" prop="goodsDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['item:goods:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['item:goods:remove']"
          >删除</el-button>
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

    <!-- 添加或修改物品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="封面" prop="goodsCover">
          <image-upload v-model="form.goodsCover"/>
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <treeselect v-model="form.categoryId" :options="sortOptions"
                      :normalizer="normalizer" placeholder="请选择分类" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="数量" prop="spec">
          <el-input v-model="form.spec" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="描述" prop="goodsDesc">
          <el-input v-model="form.goodsDesc" placeholder="请输入描述" />
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
import { listGoods, getGoods, delGoods, addGoods, updateGoods } from "@/api/item/goods";
import Treeselect from "@riophae/vue-treeselect";
import {listSort} from "@/api/item/sort";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Goods",
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      sortOptions: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物品信息表格数据
      goodsList: [],
      sortList: [],
      sortLists: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsName: null,
        goodsCover: null,
        categoryId: null,
        brand: null,
        spec: null,
        goodsDesc: null,
        params: {}
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
    /** 查询物品分类信息列表 */
    /** 查询物品分类信息下拉树结构 */
    listSort().then(response => {
      this.sortLists = response.data;
    });
    this.getTreeselect();

  },
  methods: {
    /** 查询物品信息列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 查询物品分类信息列表 */
    getSortList() {
      listSort().then(response => {
        this.sortList = this.handleTree(response.data, "id", "parentId");
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
        goodsName: null,
        goodsCover: null,
        categoryId: null,
        brand: null,
        spec: null,
        goodsDesc: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGoods(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGoods(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除物品信息编号为"' + ids + '"的数据项？').then(function() {
        return delGoods(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('item/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
