<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import RegPatientByStaff from "@/staff/views/RegPatientByStaff.vue";



</script>

<template>
  <div class="d-flex">
    <div class="leftSide">
      <StaffMenuBar/>
    </div>
    <div class="mainSide">
      <router-view/>
    </div>
  </div>

</template>
<style>
.mainSide {
  flex: 1;
}
</style>