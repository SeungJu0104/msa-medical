<script setup>
import WaitingStatus from "@/shared/components/WaitingStatus.vue";
import dayjs from "dayjs";
import {reactive} from "vue";
import { inject } from 'vue'
import "@/assets/css/waitingreservaion.css"

  const selectedPatientUuid = inject('selectedPatientUuid')
  const selectedDoctorUuid = inject('selectedDoctorUuid')

  const {value, status, date} = defineProps({
                  value: Object,
                  status: Array
  })

  const emit = defineEmits(['updateStatus', 'getPatientInfo']);

  const onStatusChange = (patient, updateStatus) => {

    emit('updateStatus', {
      uuid: patient.uuid,
      patientUuid: patient.patientUuid,
      doctorUuid: patient.doctorUuid,
      updateStatus: updateStatus
    });

  }

</script>

<template>

  <div class="patient-item" v-for="patient in value" :key="patient.uuid">
    <div class="patient-info" > 
      <span class="patient-name" v-cloak>{{ patient.name }}님</span>
      <span class="patient-meta" v-cloak v-if="patient.reservationDate">{{ dayjs(patient.reservationDate).format("HH:mm") }}</span>
      <WaitingStatus
        @update:value="(updateStatus) => onStatusChange(patient, updateStatus)"
        :status="status"
        :value="patient.status"
        :date="dayjs(patient.reservationDate)"
      />
    </div>
  </div>

</template>
