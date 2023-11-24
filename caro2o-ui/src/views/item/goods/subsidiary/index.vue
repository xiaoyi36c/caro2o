<template>
  <div>




  </div>



</template>

<script>


import {subsidiary} from "@/api/item/goods";

export default {
  name: "Subsidiary",
  data() {
    return {
      data: [],
    };
  },
  created() {
    // 从路由中获取参数
    let goodsId = this.$route.params.id;
    subsidiary(goodsId).then(res => {
      console.log(res)
      this.data = res.data;
    });

  },
  methods: {
  }
}
</script>
