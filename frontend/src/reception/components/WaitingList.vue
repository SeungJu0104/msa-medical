<script setup>
  import {useWaitingListStore} from "@/stores/waitingListStore.js";
  import {computed, onBeforeMount, onMounted, onUnmounted, ref} from "vue";
  import WaitingListDoctorName from "@/shared/components/WaitingListDoctorName.vue";
  import WaitingListPatientList from "@/shared/components/WaitingListPatientList.vue";
  import {reception} from "@/reception/util/reception.js";
import { getStompClient, sendMsg, subscribeChannel } from "@/util/stompMethod.js";
import { useUserStore } from "@/stores/userStore.js";
import { getAccessToken } from "@/auth/accessToken.js";
import { createWebSocketModuleRunnerTransport } from "vite/module-runner";
import VisitHistory from "@/components/diagnosis/VisitHistory.vue";
  import {useRouter} from "vue-router";

  const waitingListStore = useWaitingListStore();
  const waitingList = ref();
  const receptionStatusList = ref();
  const selectedPatientUuid = ref(null)
  const selectedDoctorUuid = ref(null)
  let client;


  const refreshWaitingList = async () => {
    await Promise.all([
      waitingListStore.promiseAll(),
      waitingListStore.getReceptionStatusList()
    ]);

    waitingList.value = waitingListStore.waitingList;
    receptionStatusList.value = waitingListStore.receptionStatusList;

  };

  // 의료진이 대기 리스트에서 이름을 누른 환자 UUID 가져오는 함수
  const getPatientInfo = ({patientUuid,doctorUuid}) => {

    selectedPatientUuid.value = patientUuid;
    selectedDoctorUuid.value = doctorUuid;
   
  }

  // 상태 변경 시 동작하는 함수
  const handleUpdateStatus = async (patient) => {

    await reception.updateReceptionStatus(patient);

  }

  onBeforeMount(async () => {
    await refreshWaitingList()
  });

  const statusSub = (client) => {
    setTimeout(() => {
      subscribeChannel(client, `/sub/status`, () => {
        refreshWaitingList();
      });
    }, 100); 
};
  onMounted(() => {
    client = getStompClient((client) => {
      statusSub(client)
    });
  })

</script>

<template>
  <div class="container-horizontal">
    <div class="waiting-area">
  <template v-for="list in waitingList" :key="list.doctor?.uuid">
      <WaitingListDoctorName
          :value="list.doctor"/>
        <WaitingListPatientList
          @updateStatus="handleUpdateStatus"
          @getPatientInfo="getPatientInfo"
          :value="list.patientList"
          :status="receptionStatusList"/>
    </template>
  </div>

  </div>
</template>
<style scoped>
.vertical-divider {
  width: 1px;
  background-color: #ccc;
  align-self: stretch;
 
}

.container-horizontal {
  display: flex;
  gap: 20px;
}

.waiting-area {
  flex: 1;
  min-width:300px;
}


</style>
