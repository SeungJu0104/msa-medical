<script setup>
import {onMounted, ref} from "vue";
import {reception} from "@/util/reception.js";

defineProps({
  value: Object
})

const cancelReception = (uuid) => {
  const response = reception.cancelReception(uuid);
}

</script>

<template>

  <div class="container">
    <div class="my-3">
      <template v-for="patient in value" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" v-cloak>{{patient.name}}</button>
          <input class="btn btn-primary" type="button" value="대기">
          <button class="btn btn-primary" type="submit" @click="cancelReception(patient.uuid)">취소</button>
        </div>
      </template>
    </div>
  </div>

</template>
