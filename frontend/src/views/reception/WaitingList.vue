<script setup>
  import {useWaitingListStore} from "@/stores/waitingListStore.js";
  import {onBeforeMount, ref} from "vue";
  import WaitingListPerDoctor from "@/views/reception/WaitingListPerDoctor.vue";
  import WaitingListDoctorName from "@/views/reception/WaitingListDoctorName.vue";
  import WaitingListPatientList from "@/views/reception/WaitingListPatientList.vue";

  const waitingListStore = useWaitingListStore();
  const waitingList = ref();
  const role = 'R003'; // Role 더미 데이터

  onBeforeMount(async () => {

    // if(localStorage.getItem('role') === 'R002') {
    if(role === 'R002') {
      waitingList.value = await waitingListStore.waitingListByDoctor();
    } else {
      waitingList.value = await waitingListStore.waitingListByNurse();
    }

    console.log(waitingList.value);

    // 웹소켓 연결부
    // socket = new WebSocket('wss://your-server.com/ws')
    //
    // socket.onmessage = (event) => {
    //   const message = JSON.parse(event.data)
    //
    //   if (message.type === 'refresh-doctor-data') {
    //     doctorStore.fetchDoctors() // ✅ 핀리아 상태 자동 업데이트
    //   }
    // }

  })


</script>

<template>
  <template v-for="list in waitingList">
    <WaitingListDoctorName :value="list.doctor"/>
    <WaitingListPatientList :value="list.patientList"/>
  </template>
</template>
