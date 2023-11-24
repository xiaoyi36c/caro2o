<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="goodsName">
        <el-input
          v-model="queryParams.params.keyword"
          placeholder="请输入关键字(数量,品牌,描述,名称)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          @click="$router.push('item/barn')"
          v-hasPermi="['item:goods:edit']"
        >仓库管理</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="$router.push({ path: 'item/sort'})"
          v-hasPermi="['item:goods:edit']"
        >分类管理</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" border :data="goodsList" @selection-change="handleSelectionChange" style="font-size: 12px">
      <el-table-column label="序号" align="center" prop="id" width="100px" />
      <el-table-column label="封面" align="center" >
        <template slot-scope="scope">
          <img :src="scope.row.goodsCover" alt="" style="width: 60px; height: 40px;">
        </template>
      </el-table-column>
      <el-table-column label="物品名称" align="center" prop="goodsName" />
      <el-table-column label="分类" align="center" prop="categoryId" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="数量" align="center" prop="spec" />
      <el-table-column label="描述" align="center" show-overflow-tooltip prop="goodsDesc" />
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
        <el-form-item label="分类" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入分类" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            ref='upload'
            accept=".jpg,.png,"
            class="upload-demo"
            drag
            action=""
            :on-change="handleChange"
            :on-remove="handleRemove"
            :auto-upload="false"
            :limit="1"
            :file-list="fileList">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
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
import {addGoods, addOrUpdateGoods, delGoods, getGoods, listGoods, updateGoods} from "@/api/item/goods";

export default {
  name: "Goods",
  data() {
    return {
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
      // 物品信息表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsName: null,
        categoryId: null,
        brand: null,
        spec: null,
        goodsDesc: null,
        params: {}, // 搜索参数
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
      if (['jpg', 'png'].indexOf(ext) < 0) {
        this.$modal.msgError("文件格式错误")
        this.$refs.upload.clearFiles()
        return;
      }
      //在添加文件时，手动将文件对象添加到数组中
      this.fileList = fileList;
    },
    /** 查询物品信息列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        let data = response.rows;
        // 处理数据 , 将图片转为url
        data = data.map(item => {
          let blob = new Blob([item.goodsCover], {type: 'image/jpg + image/png + image/jpeg'});
          item.goodsCover = URL.createObjectURL(blob);
         return item;
        });
        this.goodsList = data;
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
          //检查用户是否添加文件
          if (this.fileList.length === 0) {
            this.$modal.msgError("请先选择图片文件");
            return;
          }
          if (this.form.id === null){
            this.form.id = -1
          }
          //封装需要提交的数据
          let formData = new FormData();
          formData.append("id", this.form.id);
          formData.append("goodsName", this.form.goodsName);
          formData.append("categoryId", 1);
          formData.append("brand", this.form.brand);
          formData.append("goodsDesc", this.form.goodsDesc);
          formData.append("pictureFile", this.fileList[0].raw);//文件的数据
          // 发请求
          addOrUpdateGoods(formData).then(() => {
            this.$modal.msgSuccess("成功")
            this.open = false;
            this.getList();
          })
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
