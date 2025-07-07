<script setup>

import {reactive, ref} from "vue";
  import {customFetch} from "@/util/customFetch.js";
  import {ENDPOINTS} from "@/util/endpoints.js";
  import {common} from "@/util/common.js";

  const searchVal = reactive({
    input: ''
  });

  const searchRes = ref();
  const emit = defineEmits(['searchRes']);

  const search = async () => {

    try {

      const response = await customFetch(ENDPOINTS.staff.search(searchVal.input));

      if(response.status === 200) {

        searchRes.value = response.data?.list;
        console.log(searchRes.value);

        emit('searchRes', searchRes.value);

      }

    } catch (err) {

      common.errMsg(err);

    }

  }

</script>

<template>

  <div class="container">
    <div class="input-group mb-3">
      <input class="form-control form-control-lg" type="text" v-model="searchVal.input" minlength="1" maxlength="6" placeholder="환자명"/>
      <button class="btn btn-outline-secondary" type="button" @click="search">
        <img src="@/assets/search.png" alt="검색" style="width: 20px; height: 20px;" />
      </button>
    </div>
  </div>

</template>