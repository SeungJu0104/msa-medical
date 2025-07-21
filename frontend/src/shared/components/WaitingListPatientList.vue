<script setup>
import "@/assets/css/waitingreservaion.css";
import WaitingStatus from "@/shared/components/WaitingStatus.vue";
import { usePatientViewStore } from '@/stores/patientViewStore';
import dayjs from "dayjs";
import { computed } from "vue";
const store = usePatientViewStore()

  const {value, status, date} = defineProps({
      value: Object,
      status: Array
  })

  const emit = defineEmits(['updateStatus', 'getUuid']);

  const onStatusChange = (patient, updateStatus) => {

    emit('updateStatus', {
      uuid: patient.uuid,
      patientUuid: patient.patientUuid,
      doctorUuid: patient.doctorUuid,
      updateStatus: updateStatus
    });

  }

  const getUuid = (patient) => {

    store.setVisitView({
        patientUuid: patient.patientUuid,
        name : patient.name
    })

  }

  const sortedPatients = computed(() => {

    return [...value].sort((a, b) => {
      if (!a.slot) return 1;
      if (!b.slot) return -1;
      return new Date(a.slot) - new Date(b.slot);
    });
    
  });


</script>

<template>

  <div class="patient-item" v-for="patient in sortedPatients" :key="patient.uuid">
    <div class="patient-info" > 
      <span class="patient-name" @click="getUuid(patient)"v-cloak>{{ patient.name }}님</span>
      <span class="patient-meta" v-cloak v-if="patient?.slot">{{ dayjs(patient.slot).format("HH:mm") }}</span>
      <WaitingStatus
        @update:value="(updateStatus) => onStatusChange(patient, updateStatus)"
        :status="status"
        :value="patient.status"
        :date="dayjs(patient.slot)"
      />
    </div>
  </div>

</template>
