<script setup>
import {onMounted, ref} from "vue";
import {reception} from "@/util/reception.js";
import {useWaitingListStore} from "@/stores/waitingListStore.js";
import WaitingStatus from "@/views/reception/WaitingStatus.vue";

const props = defineProps({
                value: Object,
                status: Array
              })

console.log(props.value);

const cancelReception = (uuid) => {
  const response = reception.cancelReception(uuid);
}

// 취소되면 emit으로 부모 컴포넌트에 전달.


</script>

<template>

  <div class="container">
    <div class="my-3">
      <template v-for="patient in value" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" v-cloak>{{patient.name}}</button>
          <WaitingStatus :status="status" :value="patient.status"/>
          <button class="btn btn-primary" type="submit" @click="cancelReception(patient.uuid)">취소</button>
        </div>
      </template>
    </div>
  </div>

</template>
