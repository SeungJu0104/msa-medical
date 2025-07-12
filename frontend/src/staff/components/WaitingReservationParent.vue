<script setup>
import '@/css/staff/waitingreservaion.css'
import {useRouter} from "vue-router";
import {reactive} from "vue";
import ReservationListByStaff from "@/staff/views/ReservationListByStaff.vue";
import WaitingList from "@/reception/components/WaitingList.vue";

const router = useRouter();

const showList = reactive({
  reservationList: true,
  waitingList: false
});

const list = (selectedKey) => {

  for (const key in showList) {
    showList[key] = (key === selectedKey);
  }

}

</script>

<template>

  <div class="container">
    <div class="my-3 waitingAndReservation">
      <div id="reservationList" @click="list('reservationList')">예약</div>
      <div id="waitingList" @click="list('waitingList')">대기</div>
    </div>
    <div class="scroll">
      <ReservationListByStaff v-if="showList.reservationList"/>
      <WaitingList v-if="showList.waitingList" />
    </div>
  </div>

</template>