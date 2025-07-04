<template>
  <div>마이페이지</div>
  <Profile />
  <RouterLink :to="{ name: 'updateProfile' }">수정</RouterLink>
</template>

<script setup>
import Profile from '../components/Profile.vue';
</script>
