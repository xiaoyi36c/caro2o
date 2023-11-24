<template>
  <div class="app-container">
    <el-form-item label="上级分类" prop="parentId">
      <treeselect v-model="form.parentId" :options="sortOptions"
                  :normalizer="normalizer" placeholder="请选择上级分类id" />
    </el-form-item>
  </div>
</template>

<script>

</script>
