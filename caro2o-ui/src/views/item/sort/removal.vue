<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <ul>
        <el-form-item label="移动分类" prop="parentId1" style="display: inline-block;">
          <treeselect aria-label="移动分类" style="width: 200px" v-model="queryParams.parentId1" :options="sortOptions" :normalizer="normalizer" placeholder="请选择上级分类" />
        </el-form-item>
      </ul>
      <ul>
        <el-form-item label="父类分类" prop="parentId2" style="display: inline-block;">
          <treeselect aria-label="父类分类"
                      style="width: 200px"
                      v-model="queryParams.parentId2"
                      :options="sortOptions"
                      :normalizer="normalizer"
                      placeholder="请选择上级分类" />
        </el-form-item>
      </ul>
      <ul>
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="putIn"
            v-hasPermi="['sort:sort:add']"
          >立刻提交</el-button>
          <el-button
            icon="el-icon-plus"
            @click="
            turnoff"
            v-hasPermi="['sort:sort:add']"
          >关闭</el-button>
      </ul>


    </el-form>


  </div>
</template>

<script>
import { listSort, putPuIn } from "@/api/item/sort";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

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
        parentId1: null,
        parentId2: null

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
    /** 提交 */
    putIn(){
      let id1 = this.queryParams.parentId1;
      let id2 = this.queryParams.parentId2;
      putPuIn(id1,id2);
      this.turnoff();


    },
    /** 关闭页面跳转 */
    turnoff() {
      //js实现跳转
      const obj = {path: "item/sort/removal"};
      this.$tab.closeOpenPage(obj).then(() => {
        this.$router.push("/item/sort");
      })

    },
    /** 查询物品分类信息列表 */
    getList() {
      this.loading = true;
      listSort(this.queryParams).then(response => {
        this.sortList = this.handleTree(response.data, "id", "parentId1");
        this.sortList = this.handleTree(response.data, "id", "parentId2");
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
  }
};
</script>
