<script setup>
import "@/assets/css/ReservationListByStaff.css";
import { reception } from "@/reception/util/reception.js";
import WaitingListDoctorName from "@/shared/components/WaitingListDoctorName.vue";
import WaitingListPatientList from "@/shared/components/WaitingListPatientList.vue";
import { useWaitingListStore } from "@/stores/waitingListStore.js";
import { getStompClient, subscribeChannel } from "@/util/stompMethod.js";
import dayjs from "dayjs";
import { onBeforeMount, onMounted, ref } from "vue";

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
  <div class="reception-container">
    <div class="date-nav">
      <span class="date-display" @click="toggleCalendar">{{today.format("M월 D일")}}</span>
    </div>
    <div class="wr-card-list">
      <div v-for="list in waitingList" :key="list.doctor?.uuid" class="wr-card">
        <WaitingListDoctorName :value="list.doctor" :count="list.patientList?.length || 0" />
        <div class="wr-card-body">
          <div v-if="!list.patientList || list.patientList.length < 1" class="no-patient">
            <span>대기 환자가 없습니다.</span>
          </div>
          <div v-else>
            <WaitingListPatientList
              @updateStatus="handleUpdateStatus"
              :value="list.patientList"
              :status="receptionStatusList"
              :date="today"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>