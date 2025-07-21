<script setup>

import "@/assets/css/StaffLayOut.css";
import ChatRooms from "@/components/chat/ChatRooms.vue";
import Footer from "@/patient/layouts/Footer.vue";
import Header from "@/staff/components/Header.vue";
import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import WaitingReservationParent from "@/staff/components/WaitingReservationParent.vue";
import Search from "@/staff/views/Search.vue";
import { ref } from "vue";
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
      <div class="rightSide">
        <ChatRooms/>
      </div>
    </div>
    <Footer/>
  </div>
</template>
