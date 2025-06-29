<script setup>
  import {useWaitingListStore} from "@/stores/waitingListStore.js";
  import {onBeforeMount, onMounted, onUnmounted, ref} from "vue";
  import WaitingListDoctorName from "@/views/reception/WaitingListDoctorName.vue";
  import WaitingListPatientList from "@/views/reception/WaitingListPatientList.vue";
  import {reception} from "@/util/reception.js";

  const waitingListStore = useWaitingListStore();
  const waitingList = ref();
  const receptionStatusList = ref();

  const handleUpdateStatus = async ({uuid, updateStatus}) => {

    await reception.updateReceptionStatus({uuid, updateStatus});

    // 변경 사항 알리는 웹소켓 구현

  }

  onBeforeMount(async () => {
    await Promise.all([
      waitingListStore.promiseAll(),
      waitingListStore.getReceptionStatusList()
    ]);

    waitingList.value = waitingListStore.waitingList;
    receptionStatusList.value = waitingListStore.receptionStatusList;

    console.log(receptionStatusList.value);
  });


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
    <WaitingListPatientList @updateStatus="handleUpdateStatus" :value="list.patientList" :status="receptionStatusList"/>
  </template>
</template>
