<script setup>
import {onMounted, reactive} from "vue";
import {reception} from "@/util/reception.js";

const doctorName = reactive({

  name: null

});

const getDoctorName = async () => {

  doctorName.name = await reception.getDoctorName();

}

onMounted(
    getDoctorName
)

</script>

<template>

  <div class="container">
    <div class="my-3">
      <span v-cloak>{{doctorName.name}}</span>
    </div>
    <div class="my-3">

    </div>
  </div>

</template>
