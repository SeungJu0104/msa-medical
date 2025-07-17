<script setup>
import "@/assets/css/WaitingListDoctorName.css";

defineProps({
  value: Object,
  count: Number
})

</script>

<template>
  <div class="doctor-header">
    <span class="doctor-name">{{ value.name }}</span>
    <span class="patient-count">{{ count }}명</span>
  </div>
</template>
