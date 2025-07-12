<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import RegPatientByStaff from "@/staff/views/RegPatientByStaff.vue";



</script>

<template>
  <div class="container">
    <div class="leftSide">
      <StaffMenuBar/>
    </div>
    <div class="mainSide">
      <router-view/>
    </div>
    <div class="rightSide">

    </div>
  </div>

</template>