<script setup>

import {computed, onMounted, reactive, ref} from "vue";
import dayjs from "dayjs";
import {patientMethods} from "@/reservation/util/reservation.js";
import {useUserStore} from "@/stores/userStore.js";

  // 선택한 목록(들)
  const selectedListByPatient = new Set();
  const reservationList = ref();
  const userInfo = computed(() => useUserStore().user);

  const checked = (uuid, e) => {

    if(e.target.checked) {
      selectedListByPatient.add(uuid);
    }else{
      selectedListByPatient.delete(uuid);
    }

    console.log(selectedListByPatient);

  }

  const reservationListPerPatient = async () => {

    reservationList.value = await patientMethods.getReservationListPerPatient(userInfo.value.uuid);

  }

  const cancelReservation = async () => {

    await patientMethods.cancelReservation(selectedListByPatient);

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
          <input class="form-check-input" type="checkbox" @change="checked(reservation.uuid)" value="reservation.uuid">
          <span v-cloak>{{reservation.name}}</span>
          <span v-cloak>{{dayjs(reservation.reservationDate).format('MM-DD')}}</span>
        </div>
      </template>

      <button type="button" class="btn btn-outline-danger" @click="cancelReservation">취소</button>
    </div>

  </div>

</template>
