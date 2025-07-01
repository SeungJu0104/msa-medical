<script setup>

import WaitingListDoctorName from "@/common/components/WaitingListDoctorName.vue";
import WaitingListPatientList from "@/common/components/WaitingListPatientList.vue";
import {computed, onBeforeMount, onMounted, onUnmounted, reactive, ref} from "vue";
import {useReservationListStore} from "@/stores/reservationListStore.js";
import {patientMethods} from "@/reservation/util/reservation.js";
import VueDatepicker from "@vuepic/vue-datepicker";
import dayjs from "dayjs";
import '@vuepic/vue-datepicker/dist/main.css'


const reservationListStore = useReservationListStore();
const reservationStatusList = ref();
const fullReservationList = ref();
const today = dayjs();
const selectedDate = reactive({
  date: dayjs()
});
const minDate = today.toISOString();
const maxDate = today.add(6, 'day').toISOString();
const showCalendar = ref(false);

// 날짜 증감에 따른 동작 함수들
const setBeforeDate = () => {

  selectedDate.date.subtract(1, 'day');

}

const setAfterDate = () => {

  selectedDate.date.add(1, 'day');

}

const toggleCalendar = () => {

  showCalendar.value = !showCalendar.value;

}


// 상태 변경 시 동작하는 함수
const handleUpdateStatus = async ({uuid, updateStatus}) => {

    await patientMethods.updateReservationStatus({uuid, updateStatus});




  // 변경 사항 알리는 웹소켓 구현


  // 성공이면 다시 예약 테이블 가져오기
  await reservationListStore.promiseAll(minDate);
  fullReservationList.value = reservationListStore.reservationList;

}

// 의료진이 예약 리스트에서 이름을 누른 환자 UUID 가져오는 함수
const getPatientInfo = ({uuid}) => {

}

const disabledWeekends = (date) => {
  return dayjs(date).toDate().getDay() === 0;
};

const handleDate = (date) => {

  selectedDate.date = date;

}

onBeforeMount(async () => {

  await Promise.all([

    reservationListStore.promiseAll(minDate),
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

  <div class="container">
  <h1 @click="setBeforeDate">&lt;</h1><div @click="toggleCalendar">{{selectedDate.date.format("M월 D일")}}</div><h1 @click="setAfterDate">&gt;</h1>
  <template v-if="showCalendar" @blur="toggleCalendar">
    <VueDatepicker
        :model-value = "selectedDate.date"
        :format="'yyyy-MM-dd'"
        :min-date="minDate"
        :max-date="maxDate"
        :disabled-dates="disabledWeekends"
        :enable-time-picker="false"  :input-class="'form-control'"
        :esc-close = "false"
        :space-confirm = "false"
        @update:model-value = "handleDate"
        prevent-min-max-navigation
    />
  </template>
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
  </div>
</template>
