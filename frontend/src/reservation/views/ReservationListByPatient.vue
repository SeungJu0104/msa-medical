<script setup>

import {computed, onMounted, reactive, ref} from "vue";
import dayjs from "dayjs";
import {patientMethods} from "@/reservation/util/reservation.js";
import {useUserStore} from "@/stores/userStore.js";
import {common} from "@/util/common.js";
import {errorMessage} from "@/util/errorMessage.js";

  // 선택한 목록(들)
  const selectedListByPatient = new Set();
  const reservationList = ref();
  const userInfo = computed(() => useUserStore().user);

  const checked = (e) => {

    if (e.target.checked) {
      selectedListByPatient.add(e.target.value);
    } else {
      selectedListByPatient.delete(e.target.value);
    }

    console.log(selectedListByPatient);

  }

  const reservationListPerPatient = async () => {

    reservationList.value = await patientMethods.getReservationListPerPatient(userInfo.value.uuid);

  }

  const cancelReservation = async () => {

    if(selectedListByPatient.size === 0) {
      common.alertError(errorMessage.reservation.noDataForCancel);
      return;
    }

    await patientMethods.cancelReservation(selectedListByPatient);
    selectedListByPatient.clear();
    await reservationListPerPatient();

  }

  onMounted(
      reservationListPerPatient
  )

</script>

<template>

  <div class="container">

    <div>

      <template v-for="reservation in reservationList" :key="reservation.uuid">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" @change="checked" :value="reservation.uuid">
          <span v-cloak>{{reservation.name}}</span>
          <span v-cloak>{{dayjs(reservation.reservationDate).format('MM-DD')}}</span>
        </div>
      </template>

      <button type="button" class="btn btn-outline-danger" @click="cancelReservation">취소</button>
    </div>

  </div>

</template>
