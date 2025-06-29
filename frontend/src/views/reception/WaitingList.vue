<script setup>
  import {useWaitingListStore} from "@/stores/waitingListStore.js";
  import {onBeforeMount, onMounted, onUnmounted, ref} from "vue";
  import WaitingListDoctorName from "@/views/reception/WaitingListDoctorName.vue";
  import WaitingListPatientList from "@/views/reception/WaitingListPatientList.vue";

  const waitingListStore = useWaitingListStore();
  const waitingList = ref();
  const receptionStatusList = ref();

  const getReceptionStatusList = async () => {
    await waitingListStore.getReceptionStatusList();
    receptionStatusList.value = waitingListStore.receptionStatusList;
    console.log(receptionStatusList.value);
  }

  onBeforeMount(async () => {

    await waitingListStore.promiseAll();
    waitingList.value = waitingListStore.waitingList;

    await getReceptionStatusList();
    receptionStatusList.value = waitingListStore.receptionStatusList;

  })

  onMounted(() => {
    // 웹소켓 연결부

  })

  onUnmounted(() => {
    // 웹 소켓 연결 해제부

  })


</script>

<template>
  <template v-for="list in waitingList">
    <WaitingListDoctorName :value="list.doctor"/>
    <WaitingListPatientList :value="list.patientList" :status="receptionStatusList"/>
  </template>
</template>
