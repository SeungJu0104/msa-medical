<script setup>
import WaitingStatus from "@/views/reception/WaitingStatus.vue";

const {value, status} = defineProps({
                value: Object,
                status: Array
              })

const emit = defineEmits(['updateStatus']);

const onStatusChange = (patient, updateStatus) => {
  patient.status = updateStatus;
  emit('updateStatus', {uuid: patient.uuid, updateStatus});
}


</script>

<template>

  <div class="container" v-if="value">
    <div class="my-3">
      <template v-for="patient in value" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" v-cloak>{{patient.name}}</button>
          <WaitingStatus @update:value="(updateStatus) => onStatusChange(patient, updateStatus)" :status="status" v-model:value="patient.status"/>
        </div>
      </template>
    </div>
  </div>

</template>
