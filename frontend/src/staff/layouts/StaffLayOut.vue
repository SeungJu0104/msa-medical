<script setup>

import StaffMenuBar from "@/staff/components/StaffMenuBar.vue";
import Search from "@/staff/views/Search.vue";
import {ref} from "vue";
import WaitingReservationParent from "@/staff/components/WaitingReservationParent.vue";
import Header from "@/staff/components/Header.vue";
import Footer from "@/patient/layouts/Footer.vue";
import ChatRooms from "@/components/chat/ChatRooms.vue";
import "@/assets/css/StaffLayOut.css";
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
