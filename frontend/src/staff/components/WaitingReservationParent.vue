<script setup>
import '@/assets/css/waitingreservaion.css'
import {useRouter} from "vue-router";
import {reactive} from "vue";

import PaymentList from '@/components/payment/PaymentList.vue';
import ReservationListByStaff from '../views/ReservationListByStaff.vue';
import WaitingList from '@/reception/components/WaitingList.vue';

const router = useRouter();

const showList = reactive({
  reservationList: true,
  waitingList: false,
  paymentList : false,
});

const list = (selectedKey) => {

  for (const key in showList) {
    showList[key] = (key === selectedKey);
  }

}

</script>

<template>

  <div class="d-flex">
    <div class="scroll">
      <div class=" waitingAndReservation">
        <div id="reservationList" @click="list('reservationList')">예약</div>
        <div id="waitingList" @click="list('waitingList')">대기</div>
        <div id="PaymentList" @click="list('paymentList')">결재</div>
      </div>
      <ReservationListByStaff v-if="showList.reservationList"/>
      <WaitingList v-if="showList.waitingList" />
      <PaymentList v-if="showList.paymentList" />
    </div>
  </div>

</template>