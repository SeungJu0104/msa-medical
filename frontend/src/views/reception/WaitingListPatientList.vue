<script setup>
import {onBeforeMount, onMounted, ref} from "vue";
import {reception} from "@/util/reception.js";
import WaitingStatus from "@/views/reception/WaitingStatus.vue";

const {value, status} = defineProps({
                value: Object,
                status: Array
              })

console.log(value);

const emit = defineEmits(['cancel', 'updateStatus']);

const cancelReception = async (uuid) => {
  await reception.cancelReception(uuid);
  emit('cancel', uuid);
}

const onStatusChange = (patient, updateStatus) => {
  patient.status = updateStatus;
  emit('updateStatus', {uuid: patient.uuid, updateStatus});
}

// 취소되면 emit으로 부모 컴포넌트에 전달.


</script>

<template>

  <div class="container" v-if="value">
    <div class="my-3">
      <template v-for="patient in value" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" v-cloak>{{patient.name}}</button>
          <WaitingStatus @update:value="(updateStatus) => onStatusChange(patient, updateStatus)" :status="status" v-model:value="patient.status"/>
          <button class="btn btn-primary" type="submit" @click="cancelReception(patient.uuid)">취소</button>
        </div>
      </template>
    </div>
  </div>

</template>
