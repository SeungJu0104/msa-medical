<template>
  <div class="body">
    <aside class="left"></aside>
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
import VisitHistory from '@/components/diagnosis/VisitHistory.vue';
import Payment from '@/components/payment/Payment.vue';
import { usePatientViewStore } from '@/stores/patientViewStore';
import '@/assets/css/StaffMainView.css';
const store = usePatientViewStore()

</script>
