<template>
  <div class="d-flex flex-column justify-content-center align-items-center mb-5">
    <h2 class="fw-bold mt-5">회원가입</h2>
    <SectionDivider />
    <RegisterForm />
  </div>
</template>

<script setup>
import RegisterForm from '@/patient/components/RegisterForm.vue';
import SectionDivider from '../components/SectionDivider.vue';
</script>
