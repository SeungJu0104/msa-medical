<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";



</script>

<template>
  <div class="container">
    <div class="leftSide">
      <StaffMenuBar/>
    </div>
    <div class="mainSide">
      <router-view/>
      <router-view name="modal"/>
    </div>
    <div class="rightSide">

    </div>
  </div>

</template>