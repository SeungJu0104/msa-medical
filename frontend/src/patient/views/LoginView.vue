<template>
  <div class="d-flex flex-column justify-content-center align-items-center mb-5">
    <h2 class="fw-bold mt-5">로그인</h2>
    <SectionDivider />
    <LoginForm />
  </div>
</template>

<script setup>
import LoginForm from '@/patient/components/LoginForm.vue';
import SectionDivider from '../components/SectionDivider.vue';
</script>
