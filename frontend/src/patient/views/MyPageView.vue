<template>
  <div class="d-flex flex-column justify-content-center align-items-center mb-5">
    <h2 class="fw-bold mt-5">마이페이지</h2>
    <SectionDivider />
    <Profile />
  </div>
</template>

<script setup>
import Profile from '../components/Profile.vue';
import SectionDivider from '../components/SectionDivider.vue';
</script>
