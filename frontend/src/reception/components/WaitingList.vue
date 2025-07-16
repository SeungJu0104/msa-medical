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
import "@/assets/css/ReservationListByStaff.css";
import dayjs from "dayjs";

  const waitingListStore = useWaitingListStore();
  const waitingList = ref();
  const receptionStatusList = ref();
  const today = dayjs(new Date);
  let client;

  const refreshWaitingList = async () => {
    await Promise.all([
      waitingListStore.promiseAll(),
      waitingListStore.getReceptionStatusList()
    ]);

    waitingList.value = waitingListStore.waitingList;
    receptionStatusList.value = waitingListStore.receptionStatusList;

  };

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
  <div class="container">
    <div class="date-nav">
      <span class="date-display" @click="toggleCalendar">{{today.format("M월 D일")}}</span>
    </div>
    <div class="card-list">
      <div v-for="list in waitingList" :key="list.doctor?.uuid" class="card">
        <WaitingListDoctorName :value="list.doctor" :count="list.patientList?.length || 0" />
        <div class="card-body">
          <div v-if="!list.patientList || list.patientList.length < 1" class="no-patient">
            <span>대기 환자가 없습니다.</span>
          </div>
          <div v-else>
            <WaitingListPatientList
              @updateStatus="handleUpdateStatus"
              :value="list.patientList"
              :status="receptionStatusList"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>