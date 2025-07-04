<script setup>
import WaitingStatus from "@/shared/components/WaitingStatus.vue";
import dayjs from "dayjs";
import {reactive} from "vue";

const {value, status, date} = defineProps({
                value: Object,
                status: Array,
                date: dayjs
})
const emit = defineEmits(['updateStatus', 'getPatientInfo']);

const onStatusChange = (patient, updateStatus) => {
  patient.status = updateStatus;
  emit('updateStatus', {
    uuid: patient.uuid,
    patientUuid: patient.patientUuid,
    doctorUuid: patient.doctorUuid,
    updateStatus});
}

const getPatientInfo = (patient) => {
  emit('getPatientInfo', {uuid: patient.uuid});
}

</script>

<template>

  <div class="container">
    <div class="my-3">
      <template v-for="patient in value" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" @click="getPatientInfo(patient)" v-cloak>{{patient.name}}</button>
          <span v-cloak v-if="patient.reservationDate">
            {{ dayjs(patient.reservationDate).format("YYYY-MM-DD HH:mm:ss") }}
          </span>
          <WaitingStatus
              @update:value="(updateStatus) => onStatusChange(patient, updateStatus)"
              :status="status"
              :value="patient.status"
              :key="patient.uuid + '-' + patient.status"
          />
        </div>
      </template>
    </div>
  </div>

</template>
