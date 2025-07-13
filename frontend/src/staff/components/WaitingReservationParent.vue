<script setup>
import '@/css/staff/waitingreservaion.css'
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

  <div class="container">
    <div class="my-3 waitingAndReservation">
      <div id="reservationList" @click="list('reservationList')">예약</div>
      <div id="waitingList" @click="list('waitingList')">대기</div>
      <div id="PaymentList" @click="list('paymentList')">결재</div>
    </div>
    <div class="scroll">
      <ReservationListByStaff v-if="showList.reservationList"/>
      <WaitingList v-if="showList.waitingList" />
      <PaymentList v-if="showList.paymentList" />
    </div>
  </div>

</template>