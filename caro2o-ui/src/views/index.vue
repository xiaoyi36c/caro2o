<template>
  <div class="app-container home">
    <h1>欢迎进入系统: {{ name }}</h1>
    <h1>e 店邦汽车门店管理平台：{{ version }}</h1>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  data() {
    return {
      // 版本号
      version: "3.8.6"
    };
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
}

</script>



