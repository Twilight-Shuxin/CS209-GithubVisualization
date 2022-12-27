<template>
  <div>
  <div class="query">
    <el-input v-model="researchData.searchRepo" clearable placeholder="Repo"
              @clear="fetchInfo"></el-input>
    <el-input v-model="researchData.searchItem" clearable placeholder="Item"
              @clear="fetchInfo"></el-input>
    <el-button slot="append" icon="el-icon-search" @click="fetchInfo"></el-button>
  </div>
  <json-viewer :value="allData" copyable theme="my-awesome-json-theme"></json-viewer>
  </div>

</template>

<script>

export default {
  name: "research",
  data() {
    return {
      researchData:{
        "searchRepo":"gsantner-markor",
        "searchItem":"issue"
      },
      allData:null
    };
  },
  methods: {
    async fetchInfo() {
      const {data: res1} = await this.$http.get(`/repo/` + this.researchData.searchRepo + `/`+this.researchData.searchItem)
      this.allData = res1
    }
  },
}
</script>

<style scoped>

</style>
