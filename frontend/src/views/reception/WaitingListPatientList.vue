<script setup>
import {onMounted, ref} from "vue";
import {reception} from "@/util/reception.js";

  const patientList = ref();

  const getWaitingList = async () => {
    console.log("a");
    patientList.value = await reception.getWaitingList;
    console.log(patientList.value);
  }

  onMounted(
      getWaitingList
  )
</script>

<template>

  <div class="container">
    <div class="my-3">
      <template v-for="patient in patientList" :key="patient.uuid">
        <div>
          <button class="btn btn-primary" type="submit" v-cloak>{{patient.name}}</button>
          <input class="btn btn-primary" type="button" value="대기">
        </div>

      </template>
    </div>
  </div>

</template>
