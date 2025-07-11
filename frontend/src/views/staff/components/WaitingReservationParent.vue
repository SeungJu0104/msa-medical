<script setup>
import '@/css/waitingreservaion.css'
import {useRouter} from "vue-router";
import {onMounted} from "vue";
import WaitingListPatientList from "@/shared/components/WaitingListPatientList.vue";

const router = useRouter();

const linkReservationList = () => {

  router.push({name: 'reservationList'});

}

const linkWaitingList = () => {

  router.push({name: 'reception'});

}

onMounted (() => {

  router.push({name: 'reservationList'});

});


</script>

<template>

  <div class="container">
    <div class="my-3 waitingAndReservation">
      <div id="reservationList" @click="linkReservationList">예약</div>
      <div id="waitingList" @click="linkWaitingList">대기</div>
    </div>
    <div class="scroll">
      <WaitingListPatientList/>
    </div>
  </div>

</template>