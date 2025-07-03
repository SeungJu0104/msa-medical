<script setup>

import WaitingListDoctorName from "@/shared/components/WaitingListDoctorName.vue";
import WaitingListPatientList from "@/shared/components/WaitingListPatientList.vue";
import {computed, onBeforeMount, onMounted, onUnmounted, ref} from "vue";
import {useReservationListStore} from "@/stores/reservationListStore.js";
import {patientMethods} from "@/reservation/util/reservation.js";


const reservationListStore = useReservationListStore();
const reservationStatusList = ref();
const fullReservationList = ref();

// 상태 변경 시 동작하는 함수
const handleUpdateStatus = async ({uuid, updateStatus}) => {

    await patientMethods.updateReservationStatus({uuid, updateStatus});




  // 변경 사항 알리는 웹소켓 구현


  // 성공이면 다시 예약 테이블 가져오기
  await reservationListStore.promiseAll();
  fullReservationList.value = reservationListStore.reservationList;

}

// 의료진이 예약 리스트에서 이름을 누른 환자 UUID 가져오는 함수
const getPatientInfo = ({uuid}) => {

}

onBeforeMount(async () => {

  await Promise.all([

    reservationListStore.promiseAll(),
    reservationListStore.getReservationStatusList()

  ]);

  fullReservationList.value = reservationListStore.reservationList;
  reservationStatusList.value = reservationListStore.reservationStatusList;

  console.log(fullReservationList.value);
  console.log(reservationStatusList.value);

});


onMounted(() => {
  // 웹소켓 연결부

})

onUnmounted(() => {
  // 웹 소켓 연결 해제부

})

</script>

<template>
  <template v-for="list in fullReservationList" :key="list.doctor?.uuid">
    <div v-if="list.isEmpty">
      <span>대기 환자가 없습니다.</span>
    </div>
    <div v-else>
    <WaitingListDoctorName :value="list.doctor"/>
    <WaitingListPatientList
        @updateStatus="handleUpdateStatus"
        @getPatientInfo="getPatientInfo"
        :value="list.patientList"
        :status="reservationStatusList"/>
    </div>
  </template>

</template>
