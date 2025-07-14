<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import RegPatientByStaff from "@/staff/views/RegPatientByStaff.vue";
import Search from "@/staff/views/Search.vue";
import {ref} from "vue";

const modalOpen = ref(false)

</script>

<template>
  <div class="d-flex wrpper" >
    <div class="leftSide">
      <StaffMenuBar @open="modalOpen = true"/>


    </div>
    <div class="mainSide">
      <router-view/>
      <Search v-if="modalOpen" @close="modalOpen = false"/>
    </div>
  </div>

</template>
<style>
.mainSide {
  flex: 1;
}

</style>