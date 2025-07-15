<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import Search from "@/staff/views/Search.vue";
import {ref} from "vue";
import WaitingReservationParent from "@/staff/components/WaitingReservationParent.vue";
import Header from "@/staff/components/Header.vue";

const modalOpen = ref(false)

</script>

<template>
  <div class="layout-container">
    <Header/>
    <div class="d-flex wrapper" >
      <div class="leftSide">
          <StaffMenuBar @open="modalOpen = true"/>
          <WaitingReservationParent />
      </div>
      <div class="mainSide">
        <router-view/>
        <Search v-if="modalOpen" @close="modalOpen = false"/>
      </div>
    </div>
  </div>
</template>
<style>
.mainSide {
  flex: 1;
}

.leftSide {
  display: flex;              /* 가로 정렬 */
  flex-direction: row;        /* 기본값이지만 명시하면 더 명확함 */
  gap: 5px;                 /* 컴포넌트 사이 간격 */
  align-items: flex-start;    /* 높이 맞추기 (필요에 따라 center) */
  margin-right: 5px;
  height: 90vh;
  min-width: 350px;
}

.layout-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}


</style>