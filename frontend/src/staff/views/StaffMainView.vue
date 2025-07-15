<template>
    <div class="body">
    <aside class="left">
    </aside>
    <section class="center">
      <div class="center-inner">
    <div class="history-area" v-if="selectedPatientUuid">
    <VisitHistory
     v-if="selectedPatientUuid"
        :patientUuid="selectedPatientUuid"
        :doctorUuid="selectedDoctorUuid"
        @back="goBack"
      />
    </div>
    </div>
    </section>
    
    <aside class="right">
      <ChatRooms/>
    </aside>
  </div>

</template>
  
<script setup>
import ChatRooms from '@/components/chat/ChatRooms.vue';
import WaitingReservationParent from '../components/WaitingReservationParent.vue';
import StaffMenuBar from '../components/StaffMenuBar.vue';
import VisitHistory from '@/components/diagnosis/VisitHistory.vue';
import { provide, ref } from 'vue'

const selectedPatientUuid = ref(null)
const selectedDoctorUuid = ref(null)

provide('selectedPatientUuid', selectedPatientUuid)
provide('selectedDoctorUuid', selectedDoctorUuid)

const goBack = () => {
  selectedPatientUuid.value = null
  selectedDoctorUuid.value = null
  
}

</script>

<style scoped>
  .body {
    display: flex;
    align-items: stretch;
    margin: 0;
    padding: 0;
  }
  
  /* .left {
    width:10vw;
    background: #f0f0f0;
  } */
  
  .center {
    flex: 1;
    text-align: center;
    background: #e0e0ff;
    height: 100vh;
  }
  
  .right {
    width: 300px;
    background: #ffe0e0;
    height: 100vh;
    display: flex;
    flex-direction: column; /* 수직 정렬 기준 설정 */
    justify-content: flex-start; /* 위로 붙이기 */
  }
  .center-inner {
  display: flex;
  gap: 20px;
  align-items: flex-start;
  padding: 20px;
}

.history-area {
  padding: 1rem;
  flex: 1;
  background-color: #f9f9f9;
  padding: 16px;
  border-radius: 8px;
  min-height: 90vh;
  overflow-y: auto;
}
  /* .history-area {
  flex: 1;
  min-width: 300px;
  background-color: #f9f9f9;
  padding: 16px;
  border-radius: 8px;
} */
</style>