<template>
  <div class="body">
    <section class="center">
      <div class="center-inner">
        <div class="history-area">
          <VisitHistory
            v-if="store.currentView === 'visit'"
            :patientUuid="store.selectedPatientUuid"
            :name="store.name"
            @back="store.reset"/>
          <Payment
            v-if="store.currentView === 'payment'"
            :item="store.selectedItem"
            :key="store.selectedItem?.id"
            @back="store.reset"/>
        </div>
      </div>
    </section>
  </div>
</template>
  
<script setup>
import '@/assets/css/StaffMainView.css';
import VisitHistory from '@/components/diagnosis/VisitHistory.vue';
import Payment from '@/components/payment/Payment.vue';
import { usePatientViewStore } from '@/stores/patientViewStore';
const store = usePatientViewStore()

</script>
