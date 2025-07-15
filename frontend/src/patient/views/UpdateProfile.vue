<template>
  <div class="d-flex flex-column justify-content-center align-items-center mb-5">
    <h2 class="fw-bold mt-5">회원정보 수정</h2>
    <SectionDivider />
    <UpdateProfileForm />
  </div>
</template>

<script setup>
import SectionDivider from '../components/SectionDivider.vue';
import UpdateProfileForm from '../components/UpdateProfileForm.vue';
</script>
